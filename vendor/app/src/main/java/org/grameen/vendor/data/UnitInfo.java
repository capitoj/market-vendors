package org.grameen.vendor.data;

/**
 * Created by niyetu on 6/7/15.
 */
public class UnitInfo {

    protected String unitName;
    protected int unitID;

    public UnitInfo(int unitID, String unitName){
        this.unitID = unitID;
        this.unitName = unitName;
    }

    public String getUnitName(){
        return this.unitName;
    }

    public int getUnitID(){
        return this.unitID;
    }
}
