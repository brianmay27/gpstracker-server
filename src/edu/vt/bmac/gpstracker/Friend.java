package edu.vt.bmac.gpstracker;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

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
