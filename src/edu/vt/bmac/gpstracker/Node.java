package edu.vt.bmac.gpstracker;

import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import java.io.Serializable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.Entity;
import javax.persistence.Id;

// -------------------------------------------------------------------------
/**
 *  Used to store gps info, time and altitude.
 *
 *  @author Brian McNamara (bmac), Anthony Allen(ala2555), Chris Pan (chrisp3)
 *  @version Mar 31, 2013
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
//@Entity
public class Node implements Serializable
{
    @Persistent
    private Double lat;
    @Persistent
    private Double lon;
    @Persistent
    private Double alt;
    @Persistent
    private Float accuracy;
    @Persistent
    private Integer satalites;
    @Persistent
    private Float speed;
    @Persistent
    private Long time;
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @PrimaryKey
    private Long id;
//    @PrimaryKey
//    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
//    private String key;
//    public void setKey(String id) {
//        this.key = id;
//    }
//    public String getKey() {
//        return this.key;
//    }
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
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

}
