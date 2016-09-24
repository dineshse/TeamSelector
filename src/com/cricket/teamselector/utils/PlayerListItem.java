/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cricket.teamselector.utils;

import java.io.Serializable;

/**
 *
 * @author zenith
 */
public class PlayerListItem implements Serializable{
    private String id;
    private String name;
   

    public PlayerListItem() {
    }

    public PlayerListItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
