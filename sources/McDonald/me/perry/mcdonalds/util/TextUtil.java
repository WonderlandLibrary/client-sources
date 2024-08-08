// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.regex.Pattern;
import java.util.Random;

public class TextUtil
{
    public static final String BLACK;
    public static final String DARK_BLUE;
    public static final String DARK_GREEN;
    public static final String DARK_AQUA;
    public static final String DARK_RED;
    public static final String DARK_PURPLE;
    public static final String GOLD;
    public static final String GRAY;
    public static final String DARK_GRAY;
    public static final String BLUE;
    public static final String GREEN;
    public static final String AQUA;
    public static final String RED;
    public static final String LIGHT_PURPLE;
    public static final String YELLOW;
    public static final String WHITE;
    public static final String OBFUSCATED;
    public static final String BOLD;
    public static final String STRIKE;
    public static final String UNDERLINE;
    public static final String ITALIC;
    public static final String RESET;
    private static final Random rand;
    
    public static String stripColor(final String input) {
        if (input != null) {
            return Pattern.compile("(?i)§[0-9A-FK-OR]").matcher(input).replaceAll("");
        }
        return "";
    }
    
    public static String coloredString(final String string, final Color color) {
        String coloredString = string;
        switch (color) {
            case AQUA: {
                coloredString = ChatFormatting.AQUA + coloredString + ChatFormatting.RESET;
                break;
            }
            case WHITE: {
                coloredString = ChatFormatting.WHITE + coloredString + ChatFormatting.RESET;
                break;
            }
            case BLACK: {
                coloredString = ChatFormatting.BLACK + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_BLUE: {
                coloredString = ChatFormatting.DARK_BLUE + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_GREEN: {
                coloredString = ChatFormatting.DARK_GREEN + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_AQUA: {
                coloredString = ChatFormatting.DARK_AQUA + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_RED: {
                coloredString = ChatFormatting.DARK_RED + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_PURPLE: {
                coloredString = ChatFormatting.DARK_PURPLE + coloredString + ChatFormatting.RESET;
                break;
            }
            case GOLD: {
                coloredString = ChatFormatting.GOLD + coloredString + ChatFormatting.RESET;
                break;
            }
            case DARK_GRAY: {
                coloredString = ChatFormatting.DARK_GRAY + coloredString + ChatFormatting.RESET;
                break;
            }
            case GRAY: {
                coloredString = ChatFormatting.GRAY + coloredString + ChatFormatting.RESET;
                break;
            }
            case BLUE: {
                coloredString = ChatFormatting.BLUE + coloredString + ChatFormatting.RESET;
                break;
            }
            case RED: {
                coloredString = ChatFormatting.RED + coloredString + ChatFormatting.RESET;
                break;
            }
            case GREEN: {
                coloredString = ChatFormatting.GREEN + coloredString + ChatFormatting.RESET;
                break;
            }
            case LIGHT_PURPLE: {
                coloredString = ChatFormatting.LIGHT_PURPLE + coloredString + ChatFormatting.RESET;
                break;
            }
            case YELLOW: {
                coloredString = ChatFormatting.YELLOW + coloredString + ChatFormatting.RESET;
                break;
            }
        }
        return coloredString;
    }
    
    public static String cropMaxLengthMessage(final String s, final int i) {
        String output = "";
        if (s.length() >= 256 - i) {
            output = s.substring(0, 256 - i);
        }
        return output;
    }
    
    static {
        BLACK = String.valueOf(ChatFormatting.BLACK);
        DARK_BLUE = String.valueOf(ChatFormatting.DARK_BLUE);
        DARK_GREEN = String.valueOf(ChatFormatting.DARK_GREEN);
        DARK_AQUA = String.valueOf(ChatFormatting.DARK_AQUA);
        DARK_RED = String.valueOf(ChatFormatting.DARK_RED);
        DARK_PURPLE = String.valueOf(ChatFormatting.DARK_PURPLE);
        GOLD = String.valueOf(ChatFormatting.GOLD);
        GRAY = String.valueOf(ChatFormatting.GRAY);
        DARK_GRAY = String.valueOf(ChatFormatting.DARK_GRAY);
        BLUE = String.valueOf(ChatFormatting.BLUE);
        GREEN = String.valueOf(ChatFormatting.GREEN);
        AQUA = String.valueOf(ChatFormatting.AQUA);
        RED = String.valueOf(ChatFormatting.RED);
        LIGHT_PURPLE = String.valueOf(ChatFormatting.LIGHT_PURPLE);
        YELLOW = String.valueOf(ChatFormatting.YELLOW);
        WHITE = String.valueOf(ChatFormatting.WHITE);
        OBFUSCATED = String.valueOf(ChatFormatting.OBFUSCATED);
        BOLD = String.valueOf(ChatFormatting.BOLD);
        STRIKE = String.valueOf(ChatFormatting.STRIKETHROUGH);
        UNDERLINE = String.valueOf(ChatFormatting.UNDERLINE);
        ITALIC = String.valueOf(ChatFormatting.ITALIC);
        RESET = String.valueOf(ChatFormatting.RESET);
        rand = new Random();
    }
    
    public enum Color
    {
        NONE, 
        WHITE, 
        BLACK, 
        DARK_BLUE, 
        DARK_GREEN, 
        DARK_AQUA, 
        DARK_RED, 
        DARK_PURPLE, 
        GOLD, 
        GRAY, 
        DARK_GRAY, 
        BLUE, 
        GREEN, 
        AQUA, 
        RED, 
        LIGHT_PURPLE, 
        YELLOW;
    }
}
