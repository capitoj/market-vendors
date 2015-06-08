<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Market{
    
    protected $internal;
    protected $loaded;
    protected $marketID;
    protected $loadedVendors;
    protected $internalVendors;
    var $db;
    
    public function __construct($marketID){
        $this->internal = array();
        $this->internalVendors = array();
        $this->loaded = false;
        $this->loadedVendors = false;
        $this->marketID = $marketID;
        $this->db = MySQL::getInstance();
        $this->load();
        $this->loadVendors();
    }
    
    protected function load(){
        if(!$this->loaded){
            $statement = $this->db->prepare("select * from gm_markets where market_id = :market_id");
            $statement->bindValue(":market_id", $this->marketID, PDO::PARAM_INT);
            $statement->execute();
            $this->internal = $statement->fetch(PDO::FETCH_ASSOC);
            $this->loaded = true;
        }
    }
    
    protected function loadVendors(){
        if(!$this->loadedVendors){
            $statement = $this->db->prepare("select vendor_id from gm_vendors where market_id = :market_id");
            $statement->bindValue(":market_id", $this->marketID, PDO::PARAM_INT);
            $statement->execute();
            $this->internalVendors = $statement->fetchAll(PDO::FETCH_ASSOC);
            $this->loadedVendors = true;
        }
    }
    
    public function getVendors(){
        $this->loadVendors();
        $data = array();
        for($i = 0; $i < count($this->internalVendors); $i++){
            array_push($data, $this->internalVendors[$i]["vendor_id"]);
        }
        return $data;
    }
    
    public function getName(){
        $this->load();
        return $this->internal["market"];
    }
    
    public static function getRowCount(){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select count(*) as count from gm_markets");
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["count"];
    }
    
    public static function getRowID($index){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select market_id from gm_markets limit :index, 1");
        $statement->bindValue(":index", $index , PDO::PARAM_INT);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["market_id"];
    }
}