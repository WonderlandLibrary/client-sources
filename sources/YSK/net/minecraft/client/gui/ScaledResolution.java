package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class ScaledResolution
{
    private int scaledWidth;
    private int scaledHeight;
    private final double scaledWidthD;
    private int scaleFactor;
    private final double scaledHeightD;
    
    public ScaledResolution(final Minecraft minecraft) {
        this.scaledWidth = minecraft.displayWidth;
        this.scaledHeight = minecraft.displayHeight;
        this.scaleFactor = " ".length();
        final boolean unicode = minecraft.isUnicode();
        int guiScale = minecraft.gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 308 + 391 - 505 + 806;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        while (this.scaleFactor < guiScale && this.scaledWidth / (this.scaleFactor + " ".length()) >= 211 + 186 - 383 + 306 && this.scaledHeight / (this.scaleFactor + " ".length()) >= 152 + 211 - 303 + 180) {
            this.scaleFactor += " ".length();
        }
        if (unicode && this.scaleFactor % "  ".length() != 0 && this.scaleFactor != " ".length()) {
            this.scaleFactor -= " ".length();
        }
        this.scaledWidthD = this.scaledWidth / this.scaleFactor;
        this.scaledHeightD = this.scaledHeight / this.scaleFactor;
        this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }
    
    public double getScaledHeight_double() {
        return this.scaledHeightD;
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getScaleFactor() {
        return this.scaleFactor;
    }
    
    public int getScaledWidth() {
        return this.scaledWidth;
    }
    
    public int getScaledHeight() {
        return this.scaledHeight;
    }
    
    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }
}
