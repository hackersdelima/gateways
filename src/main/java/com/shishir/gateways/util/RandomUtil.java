package com.shishir.gateways.util;

import java.util.Random;

public class RandomUtil {
    public static long genRandomNumber(){
        Random rand = new Random();
        return  (long)(rand.nextDouble()*10000000000L);
    }
}
