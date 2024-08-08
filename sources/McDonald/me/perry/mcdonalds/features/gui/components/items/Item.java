// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.gui.components.items;

import me.perry.mcdonalds.features.Feature;

public class Item extends Feature
{
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean hidden;
    
    public Item(final String name) {
        super(name);
    }
    
    public void setLocation(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
    }
    
    public void update() {
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public boolean setHidden(final boolean hidden) {
        return this.hidden = hidden;
    }
}
