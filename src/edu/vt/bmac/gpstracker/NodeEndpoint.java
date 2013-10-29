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

@Api(name = "nodeendpoint", namespace = @ApiNamespace(ownerDomain = "vt.edu", ownerName = "vt.edu", packagePath = "bmac.gpstracker"))
public class NodeEndpoint
{

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listNode")
    public CollectionResponse<Node> listNode(
        @Nullable @Named("cursor") String cursorString,
        @Nullable @Named("limit") Integer limit)
    {

        PersistenceManager mgr = null;
        Cursor cursor = null;
        List<Node> execute = null;

        try
        {
            mgr = getPersistenceManager();
            Query query = mgr.newQuery(Node.class);
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

            execute = (List<Node>)query.execute();
            cursor = JDOCursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Node obj : execute)
                ;
        }
        finally
        {
            mgr.close();
        }

        return CollectionResponse.<Node> builder().setItems(execute)
            .setNextPageToken(cursorString).build();
    }


    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getNode")
    public Node getNode(@Named("id") Long id)
    {
        PersistenceManager mgr = getPersistenceManager();
        Node node = null;
        try
        {
            node = mgr.getObjectById(Node.class, id);
        }
        finally
        {
            mgr.close();
        }
        return node;
    }


    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param node the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertNode")
    public Node insertNode(Node node)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            if (containsNode(node))
            {
                throw new EntityExistsException("Object already exists");
            }
            mgr.makePersistent(node);
        }
        finally
        {
            mgr.close();
        }
        return node;
    }


    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param node the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateNode")
    public Node updateNode(Node node)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            if (!containsNode(node))
            {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.makePersistent(node);
        }
        finally
        {
            mgr.close();
        }
        return node;
    }


    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeNode")
    public void removeNode(@Named("id") Long id)
    {
        PersistenceManager mgr = getPersistenceManager();
        try
        {
            Node node = mgr.getObjectById(Node.class, id);
            mgr.deletePersistent(node);
        }
        finally
        {
            mgr.close();
        }
    }


    private boolean containsNode(Node node)
    {
        PersistenceManager mgr = getPersistenceManager();
        boolean contains = true;
        try
        {
            mgr.getObjectById(Node.class, node.getId());
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
