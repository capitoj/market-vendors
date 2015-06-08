<?php

class Unit{
    
    protected $internal;
    protected $loaded;
    protected $unitID;
    var $db;
    
    public function __construct($unitID){
        $this->unitID = $unitID;
        $this->loaded = false;
        $this->internal = array();
        $this->db = MySQL::getInstance();
        $this->load();
    }
    
    protected function load(){
        if(!$this->loaded){
            $statement = $this->db->prepare("select * from gm_units where unit_id = :unit_id");
            $statement->bindValue(":unit_id", $this->unitID, PDO::PARAM_INT);
            $statement->execute();
            $this->internal = $statement->fetch(PDO::FETCH_ASSOC);
            $this->loaded = true;
        }
    }
    
    public function getUnit(){
        $this->load();
        return $this->internal["unit_name"];
    }
    
    public static function getRowCount(){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select count(*) as count from gm_units");
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["count"];
    }
    
    public static function getRowID($index){
        $db = MySQL::getInstance();
        $statement = $db->prepare("select unit_id from gm_units limit :index, 1");
        $statement->bindValue(":index", $index , PDO::PARAM_INT);
        $statement->execute();
        $row = $statement->fetch(PDO::FETCH_ASSOC);
        return $row["unit_id"];
    }
}
