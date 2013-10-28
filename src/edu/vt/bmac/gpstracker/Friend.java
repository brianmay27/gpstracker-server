package edu.vt.bmac.gpstracker;

import javax.jdo.annotations.PersistenceCapable;
import com.google.appengine.api.datastore.Blob;
import javax.persistence.Basic;
import java.util.List;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 23, 2013
 */
@Entity
public class Friend implements Serializable
{
    @Id
    private String name;
    public Friend(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
