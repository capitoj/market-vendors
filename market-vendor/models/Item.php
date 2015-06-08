<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class Item{
    protected $internal;
    protected $loaded;
    protected $itemID;
    var $db;
    
    public function __construct($itemID){
        $this->internal = array();
        $this->loaded = false;
        $this->itemID = $itemID;
        $this->db = MySQL::getInstance();
        $this->load();
    }
    
    protected function load(){
        if(!$this->loaded){
            $statement = $this->db->prepare("select *  from gm_items where item_id = :item_id");
            $statement->bindValue(":item_id", $this->itemID, PDO::PARAM_INT);
            $statement->execute();
            $this->internal = $statement->fetch(PDO::FETCH_ASSOC);
            $this->loaded = true;
        }
    }
    
    public function getName(){
        $this->load();
        return $this->internal["item_name"];
    }
    
    public static function getRowCount(){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select count(*) as count from gm_items");
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["count"];
    }
    
    public static function getRowID($index){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select item_id from gm_items limit :index, 1");
        $statement->bindValue(":index", $index, PDO::PARAM_INT);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["item_id"];
    }
}
