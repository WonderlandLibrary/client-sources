// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.elements;

public class ElementBase
{
    private int ELEMENT_POSX;
    private int ELEMENT_POSY;
    private int ELEMENT_HEIGHT;
    private int ELEMENT_WIDTH;
    
    protected ElementBase(final int HEIGHT, final int WIDTH) {
        this.ELEMENT_HEIGHT = HEIGHT;
        this.ELEMENT_WIDTH = WIDTH;
    }
    
    protected ElementBase(final int HEIGHT) {
        this.ELEMENT_HEIGHT = HEIGHT;
        this.ELEMENT_WIDTH = 104;
    }
    
    public void drawElement(final int POSX, final int POSY, final int MOUSEX, final int MOUSEY) {
    }
    
    public void mouseClicked(final int POSX, final int POSY, final int BUTTON) {
    }
    
    public void mouseMovedOrUp(final int POSX, final int POSY, final int BUTTON) {
    }
    
    public int getPosX() {
        return this.ELEMENT_POSX;
    }
    
    public void setPosX(final int POSX) {
        this.ELEMENT_POSX = POSX;
    }
    
    public int getPosY() {
        return this.ELEMENT_POSY;
    }
    
    public void setPosY(final int POSY) {
        this.ELEMENT_POSY = POSY;
    }
    
    public int getHeight() {
        return this.ELEMENT_HEIGHT;
    }
    
    public void setHeight(final int HEIGHT) {
        this.ELEMENT_HEIGHT = HEIGHT;
    }
    
    public int getWidth() {
        return this.ELEMENT_WIDTH;
    }
    
    public void setWidth(final int WIDTH) {
        this.ELEMENT_WIDTH = WIDTH;
    }
}
