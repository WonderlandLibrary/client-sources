package net.minecraft.util;

import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class MouseHelper
{
    public int deltaX;
    public int deltaY;
    
    public void grabMouseCursor() {
        Mouse.setGrabbed((boolean)(" ".length() != 0));
        this.deltaX = "".length();
        this.deltaY = "".length();
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void mouseXYChange() {
        this.deltaX = Mouse.getDX();
        this.deltaY = Mouse.getDY();
    }
    
    public void ungrabMouseCursor() {
        Mouse.setCursorPosition(Display.getWidth() / "  ".length(), Display.getHeight() / "  ".length());
        Mouse.setGrabbed((boolean)("".length() != 0));
    }
}
