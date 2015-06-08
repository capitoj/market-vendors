package org.grameen.vendor.data;

/**
 * Created by Irama on 6/5/2015.
 */
public class VendorInfo {

    protected String phoneNumber;
    protected String vendorName;
    protected String stallNumber;
    protected int marketID;
    protected String marketName;

    public VendorInfo(){

    }

    public void setName(String vendorName){
        this.vendorName = vendorName;
    }

    public String getName(){
        return this.vendorName;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setStallNumber(String stallNumber){
        this.stallNumber = stallNumber;
    }

    public String getStallNumber(){
        return this.stallNumber;
    }

    public void setMarketID(int marketID){
        this.marketID = marketID;
    }

    public int getMarketID(){
        return this.marketID;
    }

    public void setMarketName(String marketName){
        this.marketName = marketName;
    }

    public String getMarketName(){
        return this.marketName;
    }

}
