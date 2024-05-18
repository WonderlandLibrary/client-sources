// 
// Decompiled by Procyon v0.5.30
// 

package org.jibble.pircbot;

public class Colors
{
    public static final String NORMAL = "\u000f";
    public static final String BOLD = "\u0002";
    public static final String UNDERLINE = "\u001f";
    public static final String REVERSE = "\u0016";
    public static final String WHITE = "\u000300";
    public static final String BLACK = "\u000301";
    public static final String DARK_BLUE = "\u000302";
    public static final String DARK_GREEN = "\u000303";
    public static final String RED = "\u000304";
    public static final String BROWN = "\u000305";
    public static final String PURPLE = "\u000306";
    public static final String OLIVE = "\u000307";
    public static final String YELLOW = "\u000308";
    public static final String GREEN = "\u000309";
    public static final String TEAL = "\u000310";
    public static final String CYAN = "\u000311";
    public static final String BLUE = "\u000312";
    public static final String MAGENTA = "\u000313";
    public static final String DARK_GRAY = "\u000314";
    public static final String LIGHT_GRAY = "\u000315";
    
    public static String removeColors(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 == '\u0003') {
                if (++i >= length || !Character.isDigit(s.charAt(i))) {
                    continue;
                }
                if (++i < length && Character.isDigit(s.charAt(i))) {
                    ++i;
                }
                if (i >= length || s.charAt(i) != ',') {
                    continue;
                }
                if (++i < length) {
                    if (Character.isDigit(s.charAt(i))) {
                        if (++i >= length || !Character.isDigit(s.charAt(i))) {
                            continue;
                        }
                        ++i;
                    }
                    else {
                        --i;
                    }
                }
                else {
                    --i;
                }
            }
            else if (char1 == '\u000f') {
                ++i;
            }
            else {
                sb.append(char1);
                ++i;
            }
        }
        return sb.toString();
    }
    
    public static String removeFormatting(final String s) {
        final int length = s.length();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 != '\u000f' && char1 != '\u0002' && char1 != '\u001f') {
                if (char1 != '\u0016') {
                    sb.append(char1);
                }
            }
        }
        return sb.toString();
    }
    
    public static String removeFormattingAndColors(final String s) {
        return removeFormatting(removeColors(s));
    }
}
