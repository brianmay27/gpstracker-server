package edu.vt.bmac.gpstracker;

import javax.jdo.annotations.IdGeneratorStrategy;
import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Embedded;
import javax.persistence.ElementCollection;
import java.util.List;
import javax.persistence.Basic;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 25, 2013
 */
@Entity
public class Trail implements Serializable
{
    public Trail() {

    }
    @Persistent
    private String username;
    @Id
    private String name;
/*    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;*/
    private int id;
    private Long hash;
/*   @Persistent
    @Element(dependent = "true")
    @OneToMany(mappedBy = "trail" )*/
    @Basic
    private ArrayList<Node> plots;
    @Persistent
    private String time;
    @Persistent
    private String dist;
    @Persistent
    private String alt;
    @Persistent
    private String speed;
/*    public void setKey(Key key) {
        this.key = key;
    }
    public Key getKey() {
        return key;
    }*/
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public List<Node> getPlots()
    {
        return plots;
    }
    public void setPlots(ArrayList<Node> plots)
    {
        this.plots = plots;
    }
    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
    public String getDist()
    {
        return dist;
    }
    public void setDist(String dist)
    {
        this.dist = dist;
    }
    public String getAlt()
    {
        return alt;
    }
    public void setAlt(String alt)
    {
        this.alt = alt;
    }
    public String getSpeed()
    {
        return speed;
    }
    public void setSpeed(String speed)
    {
        this.speed = speed;
    }
    public Long getHash()
    {
        return hash;
    }
    public void setHash(Long hash)
    {
        this.hash = hash;
    }

}
