package edu.vt.bmac.gpstracker;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.OneToMany;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.Basic;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

// -------------------------------------------------------------------------
/**
 *  Used to store gps info, time and altitude.
 *
 *  @author Brian McNamara (bmac), Anthony Allen(ala2555), Chris Pan (chrisp3)
 *  @version Mar 31, 2013
 */
@PersistenceCapable
public final class Node implements Serializable
{
    private Double lat;
    private Double lon;
    private Double alt;
    private float accuracy;
    private Integer satalites;
    private float speed;
    private Long time;
/*    @Persistent
    private Trail trail;
    public void setTrail(Trail trail) {
        this.trail = trail;
    }
    public Trail getTrail() {
        return trail;
    }*/
    public Double getLat()
    {
        return lat;
    }
    public void setLat(Double lat)
    {
        this.lat = lat;
    }
    public Double getLon()
    {
        return lon;
    }
    public void setLon(Double lon)
    {
        this.lon = lon;
    }
    public Double getAlt()
    {
        return alt;
    }
    public void setAlt(Double alt)
    {
        this.alt = alt;
    }
    public float getAccuracy()
    {
        return accuracy;
    }
    public void setAccuracy(float accuracy)
    {
        this.accuracy = accuracy;
    }
    public Integer getSatalites()
    {
        return satalites;
    }
    public void setSatalites(Integer satalites)
    {
        this.satalites = satalites;
    }
    public float getSpeed()
    {
        return speed;
    }
    public void setSpeed(float speed)
    {
        this.speed = speed;
    }
    public Long getTime()
    {
        return time;
    }
    public void setTime(Long time)
    {
        this.time = time;
    }

}
