<?php

include("global.php");
echo '<?xml version="1.0" encoding="UTF-8"?>';
echo "<markets>";
$count = Market::getRowCount();
for($i = 0; $i < $count; $i++){
    $rowID = Market::getRowID($i);
    $market = new Market($rowID);
    $name = $market->getName();
    echo "<market id='{$rowID}'>{$name}</market>";
}

echo "</markets>";