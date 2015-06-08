<?php

include("global.php");

if(count($_POST) > 0){
    $action = filter_input(INPUT_POST, "action");
    switch($action){
        case "registration" : registerVendor(); break;
        case "add-item"     : saveVendorItem(); break;
        case "vendor-items" : getVendorItems(); break;
        case "market-vendors"   : getMarketVendors(); break;
    }
}


function getMarketVendors(){
    $marketID = filter_input(INPUT_POST, "marketID");
    $market = new Market($marketID);
    $vendors = $market->getVendors();
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo "<items>";
    for($i = 0; $i < count($vendors); $i++){
        $vendor = new Vendor($vendors[$i]);
        $vendorName = $vendor->getVendorName();
        $stallNumber = $vendor->getStallNumber();
        $data = $vendor->getVendorItems();
        for($j = 0; $j < count($data); $j++){
            $vendorItem = new VendorItem($data[$j]);
            echo "<item id='{$data[$j]}'>";
                echo "<name>{$vendorItem->getItemName()}</name>";
                echo "<price>{$vendorItem->getPrice()}</price>";
                echo "<unit>{$vendorItem->getUnit()}</unit>";
                echo "<vendor>{$vendorName}</vendor>";
                echo "<stall>{$stallNumber}</stall>";
            echo "</item>";
        }
    }
    echo "</items>";
}

function getVendorItems(){
    $appID = filter_input(INPUT_POST, "appID");
    $phoneNumber = Vendor::getPhoneNumber($appID);
    $vendorID = Vendor::getVendorID($phoneNumber);
    $vendor = new Vendor($vendorID);
    $data = $vendor->getVendorItems();
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo "<items>";
    for($i = 0; $i < count($data); $i++){
        $vendorItem = new VendorItem($data[$i]);
        echo "<item id='{$data[$i]}'>";
            echo "<name>{$vendorItem->getItemName()}</name>";
            echo "<price>{$vendorItem->getPrice()}</price>";
            echo "<unit>{$vendorItem->getUnit()}</unit>";
        echo "</item>";
    }
    echo "</items>";
}

function saveVendorItem(){
    $appID = filter_input(INPUT_POST, "appID");
    $itemID = filter_input(INPUT_POST, "itemID");
    $price = filter_input(INPUT_POST, "price");
    $unitID = filter_input(INPUT_POST, "unitID");
    $phoneNumber = Vendor::getPhoneNumber($appID);
    $vendorID = Vendor::getVendorID($phoneNumber);
    $vendor = new Vendor($vendorID);
    $vendorItemID = $vendor->addItem($itemID, $price, $unitID);
    if($vendorItemID > 0){
        echo "SUCCESS";
    }else{
        echo "FAILURE";
    }
}

function registerVendor(){
    $vendorName = filter_input(INPUT_POST, "vendorName");
    $stallNumber = filter_input(INPUT_POST, "stallNumber");
    $phoneNumber = filter_input(INPUT_POST, "phoneNumber");
    $marketID = filter_input(INPUT_POST, "marketID");
    
    $vendorID = Vendor::add($marketID, $vendorName, $stallNumber, $phoneNumber);
    $vendor = new Vendor($vendorID);
    $appID = $vendor->getAppID();
    echo $appID;
}