// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

public class RenderBox
{
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    
    public RenderBox(final double p1, final double p2, final double p3, final double p4, final double p5, final double p6) {
        this.minX = p1;
        this.minY = p2;
        this.minZ = p3;
        this.maxX = p4;
        this.maxZ = p5;
        this.maxY = p5;
    }
}
