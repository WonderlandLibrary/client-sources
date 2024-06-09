package me.swezedcode.client.gui.cgui.component;

import net.minecraft.client.gui.*;
import java.awt.*;

public abstract class Component
{
    protected Component parent;
    public String title;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    public abstract void draw(final int p0, final int p1, final float p2);
    
    public abstract void onUpdate(final int p0, final int p1, final float p2);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    public abstract void mouseReleased(final int p0, final int p1, final int p2);
    
    public abstract void keyTyped(final char p0, final int p1);
    
    public Rectangle MouseIsInside() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public Component getParent() {
        return this.parent;
    }
}
