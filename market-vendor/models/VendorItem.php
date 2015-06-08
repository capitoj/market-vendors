<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class VendorItem{
    protected $loaded;
    protected $internal;
    protected $ID;
    var $db;
    
    public function __construct($ID){
        $this->ID = $ID;
        $this->loaded = false;
        $this->internal = array();
        $this->db = MySQL::getInstance();
        $this->load();
    }
    
    protected function load(){
        if(!$this->loaded){
            $statement = $this->db->prepare("select a.item_id, c.item_name, a.vendor_id, b.vendor_name, a.price, a.unit_id, d.unit_name from gm_vendor_items as a "
                    . "inner join gm_vendors as b on a.vendor_id = b.vendor_id "
                    . "inner join gm_items as c on a.item_id = c.item_id "
                    . "inner join gm_units as d on a.unit_id = d.unit_id where a.id = :id");
            $statement->bindValue(":id", $this->ID, PDO::PARAM_INT);
            $statement->execute();
            $this->internal = $statement->fetch(PDO::FETCH_ASSOC);
            $this->loaded = true;
        }
    }
    
    public function getItemName(){
        $this->load();
        return $this->internal["item_name"];
    }
    
    public function getUnit(){
        $this->load();
        return $this->internal["unit_name"];
    }
    
    public function getPrice(){
        $this->load();
        return $this->internal["price"];
    }
}

