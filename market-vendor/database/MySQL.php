<?php

class MySQL {

    public static function getInstance(){
        return new PDO("mysql:host=localhost;dbname=market_vendors;charset=utf8", "grameen", "1234");
    }
}
