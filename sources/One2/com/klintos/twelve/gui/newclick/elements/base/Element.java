// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.base;

public abstract class Element
{
    private int width;
    private int height;
    private int posX;
    private int posY;
    protected int dragX;
    protected int dragY;
    private Panel parent;
    
    public Element(final int posX, final int posY, final int width, final int height, final Panel parent) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.parent = parent;
    }
    
    public abstract void draw(final int p0, final int p1);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    public int getPosX() {
        return this.posX;
    }
    
    public void setPosX(final int posX) {
        this.posX = posX;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public void setPosY(final int posY) {
        this.posY = posY;
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
    
    public Panel getParent() {
        return this.parent;
    }
    
    public void shiftX(final int ammount) {
        this.dragX = ammount;
    }
    
    public void shiftY(final int ammount) {
        this.dragY = ammount;
    }
    
    public void stopDragging() {
        this.setPosX(this.getPosX() + this.dragX);
        this.setPosY(this.getPosY() + this.dragY);
        this.dragX = 0;
        this.dragY = 0;
    }
}
