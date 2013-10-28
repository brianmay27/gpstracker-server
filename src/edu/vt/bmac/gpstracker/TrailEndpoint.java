package edu.vt.bmac.gpstracker;

import edu.vt.bmac.gpstracker.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

        EntityManager mgr = null;
        Cursor cursor = null;
        List<Trail> execute = null;

        try
        {
            mgr = getEntityManager();
            Query query = mgr.createQuery("select from Trail as Trail");
            if (cursorString != null && cursorString != "")
            {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null)
            {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<Trail>)query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
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
    public Trail getTrail(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        Trail trail = null;
        try
        {
            trail = mgr.find(Trail.class, id);
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
        EntityManager mgr = getEntityManager();
        try
        {
            if (containsTrail(trail))
            {
                throw new EntityExistsException("Object already exists");
            }
            mgr.persist(trail);
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
        EntityManager mgr = getEntityManager();
        try
        {
            if (!containsTrail(trail))
            {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(trail);
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
    public void removeTrail(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            Trail trail = mgr.find(Trail.class, id);
            mgr.remove(trail);
        }
        finally
        {
            mgr.close();
        }
    }


    private boolean containsTrail(Trail trail)
    {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try
        {
            Trail item = mgr.find(Trail.class, trail.getName());
            if (item == null)
            {
                contains = false;
            }
        }
        finally
        {
            mgr.close();
        }
        return contains;
    }


    private static EntityManager getEntityManager()
    {
        return EMF.get().createEntityManager();
    }

}
