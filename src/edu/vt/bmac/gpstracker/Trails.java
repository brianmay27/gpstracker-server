package edu.vt.bmac.gpstracker;

import javax.persistence.ElementCollection;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Brian
 *  @version Oct 24, 2013
 */
@Entity
public class Trails implements Serializable
{
    @Id
    private String name;
    @ElementCollection
    ArrayList<Trail> trails;

    public void addTrail(Trail info) {
        Integer id = 0;
        if (!trails.isEmpty()) {
            for (Trail trail : trails) {
                if (trail.getId() >= id) {
                    id = trail.getId() + 1;
                }
            }
        }
        info.setId(id);
        trails.add(info);
    }
    public String getName() {
        return name;
    }
    public Trail getTrail(Integer id){
        Trail ret;
        for(Trail trail : trails) {
            if (trail.getId() == id)
                return trail;
        }
        return null;
    }
    public List<Trail> getTrails() {
        return trails;
    }
}
