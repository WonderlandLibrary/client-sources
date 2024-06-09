// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

public class StringConversions
{
    public static boolean isNumeric(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
}
