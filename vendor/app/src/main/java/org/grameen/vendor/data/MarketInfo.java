package org.grameen.vendor.data;

/**
 * Created by Joseph Capito on 6/5/2015.
 */
public class MarketInfo {

    protected int ID;
    protected String name;
    public MarketInfo(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public int getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }
}
