// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals.hud;

import java.awt.Color;

public enum ColorMode
{
    RED(new Color(255, 0, 0).getRGB()), 
    ORANGE(new Color(255, 100, 0).getRGB()), 
    GREEN(new Color(0, 255, 66).getRGB()), 
    BLUE(new Color(0, 150, 200).getRGB()), 
    PURPLE(new Color(171, 0, 255).getRGB()), 
    PINK(new Color(255, 10, 245).getRGB());
    
    private final int color;
    
    private ColorMode(final int color) {
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
}
