package net.minecraft.client.gui;

import net.minecraft.util.*;

public class ChatLine
{
    private final IChatComponent lineString;
    private final int updateCounterCreated;
    private final int chatLineID;
    
    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }
    
    public IChatComponent getChatComponent() {
        return this.lineString;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getChatLineID() {
        return this.chatLineID;
    }
    
    public ChatLine(final int updateCounterCreated, final IChatComponent lineString, final int chatLineID) {
        this.lineString = lineString;
        this.updateCounterCreated = updateCounterCreated;
        this.chatLineID = chatLineID;
    }
}
