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

@Api(name = "friendendpoint", namespace = @ApiNamespace(ownerDomain = "vt.edu", ownerName = "vt.edu", packagePath = "bmac.gpstracker"))
public class FriendEndpoint
{

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listFriend")
    public CollectionResponse<Friend> listFriend(
        @Nullable @Named("cursor") String cursorString,
        @Nullable @Named("limit") Integer limit)
    {

        EntityManager mgr = null;
        Cursor cursor = null;
        List<Friend> execute = null;

        try
        {
            mgr = getEntityManager();
            Query query = mgr.createQuery("select from Friend as Friend");
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

            execute = (List<Friend>)query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Friend obj : execute)
                ;
        }
        finally
        {
            mgr.close();
        }

        return CollectionResponse.<Friend> builder().setItems(execute)
            .setNextPageToken(cursorString).build();
    }


    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getFriend")
    public Friend getFriend(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        Friend friend = null;
        try
        {
            friend = mgr.find(Friend.class, id);
        }
        finally
        {
            mgr.close();
        }
        return friend;
    }


    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param friend the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertFriend")
    public Friend insertFriend(Friend friend)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            if (containsFriend(friend))
            {
                throw new EntityExistsException("Object already exists");
            }
            mgr.persist(friend);
        }
        finally
        {
            mgr.close();
        }
        return friend;
    }


    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param friend the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateFriend")
    public Friend updateFriend(Friend friend)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            if (!containsFriend(friend))
            {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(friend);
        }
        finally
        {
            mgr.close();
        }
        return friend;
    }


    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeFriend")
    public void removeFriend(@Named("id") String id)
    {
        EntityManager mgr = getEntityManager();
        try
        {
            Friend friend = mgr.find(Friend.class, id);
            mgr.remove(friend);
        }
        finally
        {
            mgr.close();
        }
    }


    private boolean containsFriend(Friend friend)
    {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try
        {
            Friend item = mgr.find(Friend.class, friend.getName());
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
