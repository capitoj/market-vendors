<?php

class Vendor{
    
    protected $internal;
    protected $loaded;
    protected $vendorID;
    protected $appID;
    var $db;
    
    public function __construct($vendorID){
        $this->vendorID = $vendorID;
        $this->loaded = false;
        $this->internal = array();
        $this->db = MySQL::getInstance();
        $this->load();
    }
    
    protected function load(){
        if(!$this->loaded){
            $statement = $this->db->prepare("select * from gm_vendors where vendor_id = :vendor_id");
            $statement->bindValue(":vendor_id", $this->vendorID, PDO::PARAM_INT);
            $statement->execute();
            $this->internal = $statement->fetch(PDO::FETCH_ASSOC);
            $this->loaded = true;
        }
    }
    
    public function getAppID(){
        $this->load();
        return $this->internal["app_id"];
    }
    
    public function getVendorName(){
        $this->load();
        return $this->internal["vendor_name"];
    }
    
    public function getStallNumber(){
        $this->load();
        return $this->internal["stall_number"];
    }
    
    public static function add($marketID, $vendorName, $stallNumber, $phoneNumber){
        if(!Vendor::hasPhoneNumber($phoneNumber)){
            $appID = Vendor::vendorAppID();
            return Vendor::save($marketID, $vendorName, $stallNumber, $phoneNumber, $appID);
        }else{
            return Vendor::getVendorID($phoneNumber);
        }
    }
    
    protected static function vendorAppID(){
        return StringUtil::random(10);
    }
    
    protected static function save($marketID, $vendorName, $stallNumber, $phoneNumber, $appID){
        $db = MySQL::getInstance();
        $statement = $db->prepare("insert into gm_vendors (market_id, vendor_name, stall_number, phone_number, app_id, date) values (:market_id, :vendor_name, :stall_number, :phone_number, :app_id, unix_timestamp())");
        $statement->bindValue(":market_id", $marketID, PDO::PARAM_INT);
        $statement->bindValue(":vendor_name", $vendorName, PDO::PARAM_STR);
        $statement->bindValue(":stall_number", $stallNumber, PDO::PARAM_INT);
        $statement->bindValue(":phone_number", $phoneNumber, PDO::PARAM_STR);
        $statement->bindValue(":app_id", $appID, PDO::PARAM_STR);
        $statement->execute();
        return $db->lastInsertId();
    }
    
    protected static function hasPhoneNumber($phoneNumber){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select count(*) as count from gm_vendors where phone_number = :phone_number");
        $statement->bindValue(":phone_number", $phoneNumber, PDO::PARAM_STR);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["count"] > 0 ? true : false;
    }
    
    public static function getVendorID($phoneNumber){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select vendor_id from gm_vendors where phone_number = :phone_number");
        $statement->bindValue(":phone_number", $phoneNumber, PDO::PARAM_STR);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["vendor_id"] > 0 ? $row["vendor_id"] : -1;
    }
    
    public static function getPhoneNumber($appID){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select phone_number from gm_vendors where app_id = :app_id");
        $statement->bindValue(":app_id", $appID, PDO::PARAM_STR);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["phone_number"];
    }
    
    public function addItem($itemID, $price, $unitID){
        $statement = $this->db->prepare("insert into gm_vendor_items (item_id, vendor_id, price, date, unit_id) values (:item_id, :vendor_id, :price, unix_timestamp(), :unit_id)");
        $statement->bindValue(":item_id", $itemID, PDO::PARAM_INT);
        $statement->bindValue(":vendor_id", $this->vendorID, PDO::PARAM_INT);
        $statement->bindValue(":price", $price, PDO::PARAM_INT);
        $statement->bindValue(":unit_id", $unitID, PDO::PARAM_INT);
        $statement->execute();
        return $this->db->lastInsertId();
    }
    
    protected function loadItems(){
        $statement = $this->db->prepare("select id from gm_vendor_items where vendor_id = :vendor_id");
        $statement->bindValue(":vendor_id", $this->vendorID, PDO::PARAM_INT);
        $statement->execute();
        $row = $statement->fetchAll(PDO::FETCH_ASSOC);
        return $row;
    }
   
    public function getVendorItems(){
        $data = array();
        $row = $this->loadItems();
        for($i = 0; $i < count($row); $i++){
            array_push($data, $row[$i]["id"]);
        }
        return $data;
    }
    
}