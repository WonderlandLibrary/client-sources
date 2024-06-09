// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other.animation;

import xyz.niggfaclient.utils.render.RenderUtils;

public class Animate
{
    private double x;
    private double y;
    
    public Animate(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public void animate(final double newX, final double newY) {
        this.x = RenderUtils.transition(this.x, newX, 1.0);
        this.y = RenderUtils.transition(this.y, newY, 1.0);
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
}
