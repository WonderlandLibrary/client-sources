// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import me.perry.mcdonalds.features.gui.components.Component;
import me.perry.mcdonalds.features.modules.client.ClickGui;
import me.perry.mcdonalds.util.ColorUtil;
import java.awt.Color;

public class ColorManager
{
    private float red;
    private float green;
    private float blue;
    private float alpha;
    private Color color;
    
    public ColorManager() {
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.alpha = 1.0f;
        this.color = new Color(this.red, this.green, this.blue, this.alpha);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(final Color color) {
        this.color = color;
    }
    
    public int getColorAsInt() {
        return ColorUtil.toRGBA(this.color);
    }
    
    public int getColorAsIntFullAlpha() {
        return ColorUtil.toRGBA(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255));
    }
    
    public int getColorWithAlpha(final int alpha) {
        if (ClickGui.getInstance().rainbow.getValue()) {
            return ColorUtil.rainbow(Component.counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB();
        }
        return ColorUtil.toRGBA(new Color(this.red, this.green, this.blue, alpha / 255.0f));
    }
    
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.updateColor();
    }
    
    public void updateColor() {
        this.setColor(new Color(this.red, this.green, this.blue, this.alpha));
    }
    
    public void setColor(final int red, final int green, final int blue, final int alpha) {
        this.red = red / 255.0f;
        this.green = green / 255.0f;
        this.blue = blue / 255.0f;
        this.alpha = alpha / 255.0f;
        this.updateColor();
    }
    
    public void setRed(final float red) {
        this.red = red;
        this.updateColor();
    }
    
    public void setGreen(final float green) {
        this.green = green;
        this.updateColor();
    }
    
    public void setBlue(final float blue) {
        this.blue = blue;
        this.updateColor();
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
        this.updateColor();
    }
}
