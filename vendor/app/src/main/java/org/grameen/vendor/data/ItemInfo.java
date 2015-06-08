package org.grameen.vendor.data;

/**
 * Created by niyetu on 6/7/15.
 */
public class ItemInfo {

    protected String itemName;
    protected int itemID;
    protected int price;
    protected String unit;
    protected int ID;
    protected String vendorName;
    protected String stallNumber;

    public ItemInfo(){

    }

    public ItemInfo(int itemID, String itemName){
        this.itemID = itemID;
        this.itemName = itemName;
    }

    public void setVendorName(String vendorName){
        this.vendorName = vendorName;
    }

    public String getVendorName(){
        return this.vendorName;
    }

    public String getItemName(){
        return this.itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public int getItemID(){
        return this.itemID;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return this.price;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getUnit(){
        return this.unit;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setStallNumber(String stallNumber){
        this.stallNumber = stallNumber;
    }

    public String getStallNumber(){
        return this.stallNumber;
    }
}
