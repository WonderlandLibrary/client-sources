package me.darkmagician6.morbid.util.misc;

public class BlockCoord
{
    private int x;
    private int y;
    private int z;
    
    public BlockCoord(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public double getDeltaX() {
        return this.getX() - bgy.b;
    }
    
    public double getDeltaY() {
        return this.getY() - bgy.c;
    }
    
    public double getDeltaZ() {
        return this.getZ() - bgy.d;
    }
}
