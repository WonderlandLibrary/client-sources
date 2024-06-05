// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals.hud;

import java.awt.Color;

public enum SecondaryColorMode
{
    RED(new Color(125, 0, 0).getRGB()), 
    ORANGE(new Color(125, 90, 0).getRGB()), 
    GREEN(new Color(0, 155, 89).getRGB()), 
    BLUE(new Color(0, 9, 100).getRGB()), 
    PURPLE(new Color(75, 0, 125).getRGB()), 
    PINK(new Color(100, 10, 105).getRGB());
    
    private final int color;
    
    private SecondaryColorMode(final int color) {
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
}
