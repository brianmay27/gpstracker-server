package edu.vt.bmac.gpstracker;

import edu.vt.bmac.gpstracker.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "trailendpoint", namespace = @ApiNamespace(ownerDomain = "vt.edu", ownerName = "vt.edu", packagePath = "bmac.gpstracker"))
public class TrailEndpoint
{

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listTrail")
    public CollectionResponse<Trail> listTrail(
        @Nullable @Named("cursor") String cursorString,
        @Nullable @Named("limit") Integer limit)
    {

        PersistenceManager mgr = null;
        Cursor cursor = null;
        List<Trail> execute = null;

        try
        {
            mgr = getPersistenceManager();
            Query query = mgr.newQuery(Trail.class);
            if (cursorString != null && cursorString != "")
            {
                cursor = Cursor.fromWebSafeString(cursorString);
                HashMap<String, Object> extensionMap =
                    new HashMap<String, Object>();
                extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
                query.setExtensions(extensionMap);
            }

            if (limit != null)
            {
                query.setRange(0, limit);
            }

            execute = (List<Trail>)query.execute();
            cursor = JDOCursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Trail obj : execute)
                ;
        }
        finally
        {
            mgr.close();
        }

        return CollectionResponse.<Trail> builder().setItems(execute)
            .setNextPageToken(cursorString).build();
    }


    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getTrail")
    public Trail getTrail(@Named("id") Long id)
    {
        PersistenceManager mgr = getPersistenceManager();
        Trail trail = null;
        try
        {
            trail = mgr.getObjectById(Trail.class, id);
        }
        finally
        {
            mgr.close();
        }
        return trail;
    }


    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param trail the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertTrail")
    public Trail insertTrail(Trail trail)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            if (containsTrail(trail))
            {
                throw new EntityExistsException("Object already exists");
            }
            mgr.makePersistent(trail);
        }
        finally
        {
            mgr.close();
        }
        return trail;
    }


    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param trail the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateTrail")
    public Trail updateTrail(Trail trail)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            if (!containsTrail(trail))
            {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.makePersistent(trail);
        }
        finally
        {
            mgr.close();
        }
        return trail;
    }


    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeTrail")
    public void removeTrail(@Named("id") Long id)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            Trail trail = mgr.getObjectById(Trail.class, id);
            mgr.deletePersistent(trail);
        }
        finally
        {
            mgr.close();
        }
    }


    private boolean containsTrail(Trail trail)
    {
        PersistenceManager mgr = getPersistenceManager();
        boolean contains = true;
        try
        {
            mgr.getObjectById(Trail.class, trail.getHash());
        }
        catch (javax.jdo.JDOObjectNotFoundException ex)
        {
            contains = false;
        }
        finally
        {
            mgr.close();
        }
        return contains;
    }


    private static PersistenceManager getPersistenceManager()
    {
        return PMF.get().getPersistenceManager();
    }

}
