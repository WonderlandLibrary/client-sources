// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import java.util.Random;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class MatUtils
{
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static int customRandInt(final int min, final int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
