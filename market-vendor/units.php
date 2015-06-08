<?php

include("global.php");
echo '<?xml version="1.0" encoding="UTF-8"?>';
echo "<units>";
$count = Unit::getRowCount();
for($i = 0; $i < $count; $i++){
    $rowID = Unit::getRowID($i);
    $unit = new Unit($rowID);
    $name = $unit->getUnit();
    echo "<unit id='{$rowID}'>{$name}</unit>";
}

echo "</units>";