package edu.vt.bmac.gpstracker;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jws.soap.InitParam;
import com.google.appengine.datanucleus.annotations.Unowned;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 25, 2013
 */
//@Entity
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Trail implements Serializable
{
    public Trail() {

    }
    private String username;

    private String name;
    private int id;
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    @PrimaryKey
    private Long hash;
    //@Element(types = Node.class)
//    @OneToMany( cascade = CascadeType.DETACH)
    @Unowned
    @Persistent
    private ArrayList<Node> plots;
    private String time;
    private String dist;
    private String alt;
    private String speed;
//    public void setKey(Key key) {
//        this.key = key;
//    }
//    public Key getKey() {
//        return key;
//    }
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
