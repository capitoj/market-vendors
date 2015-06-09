package org.grameen.vendor.data;

/**
 * Created by Joseph Capito on 6/7/15.
 */
public class VendorItemInfo {

    protected int itemID;
    protected String itemName;
    protected int price;
    protected int unitID;

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    public int getItemID(){
        return this.itemID;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public String getItemName(){
        return this.itemName;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return this.price;
    }

    public void setUnit(int unitID){
        this.unitID = unitID;
    }

    public int getUnitID(){
        return this.unitID;
    }
}
