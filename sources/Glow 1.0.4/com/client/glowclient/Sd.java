package com.client.glowclient;

public class sd
{
    public static boolean D(final String s) {
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public sd() {
        super();
    }
    
    public static boolean M(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
