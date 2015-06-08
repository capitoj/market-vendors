<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class StringUtil{
    
    public static function random($size){
        $shuffle = str_shuffle("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789");
        $random = substr($shuffle, 0, $size);
        return $random;
    }
}
