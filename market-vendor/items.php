<?php

include("global.php");
echo '<?xml version="1.0" encoding="UTF-8"?>';
echo "<items>";
$rowCount = Item::getRowCount();
for($i = 0; $i < $rowCount; $i++){
    $rowID = Item::getRowID($i);
    $item = new Item($rowID);
    $itemName = $item->getName();
    echo "<item id='{$rowID}'>{$itemName}</item>";
}
echo "</items>";