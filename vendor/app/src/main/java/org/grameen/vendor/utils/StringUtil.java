package org.grameen.vendor.utils;

import java.util.Random;

/**
 * Created by Joseph Capito on 6/5/2015.
 */
public class StringUtil {

    public static String random(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i ++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
