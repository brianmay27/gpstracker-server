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

@Api(name = "trailsendpoint", namespace = @ApiNamespace(ownerDomain = "vt.edu", ownerName = "vt.edu", packagePath = "bmac.gpstracker"))
public class TrailsEndpoint
{

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listTrails")
    public CollectionResponse<Trails> listTrails(
        @Nullable @Named("cursor") String cursorString,
        @Nullable @Named("limit") Integer limit)
    {

        EntityManager mgr = null;
        Cursor cursor = null;
        List<Trails> execute = null;

        try
        {
            mgr = getEntityManager();
            Query query = mgr.createQuery("select from Trails as Trails");
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

            execute = (List<Trails>)query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Trails obj : execute)
                ;
        }
        finally
        {
            mgr.close();
        }

        return CollectionResponse.<Trails> builder().setItems(execute)
            .setNextPageToken(cursorString).build();
    }


    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getTrails")
    public Trails getTrails(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        Trails trails = null;
        try
        {
            trails = mgr.find(Trails.class, id);
        }
        finally
        {
            mgr.close();
        }
        return trails;
    }


    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param trails the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertTrails")
    public Trails insertTrails(Trails trails)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            if (containsTrails(trails))
            {
                throw new EntityExistsException("Object already exists");
            }
            mgr.persist(trails);
        }
        finally
        {
            mgr.close();
        }
        return trails;
    }


    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param trails the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateTrails")
    public Trails updateTrails(Trails trails)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            if (!containsTrails(trails))
            {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(trails);
        }
        finally
        {
            mgr.close();
        }
        return trails;
    }


    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeTrails")
    public void removeTrails(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            Trails trails = mgr.find(Trails.class, id);
            mgr.remove(trails);
        }
        finally
        {
            mgr.close();
        }
    }


    private boolean containsTrails(Trails trails)
    {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try
        {
            Trails item = mgr.find(Trails.class, trails.getName());
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
