package me.enrythebest.reborn.cracked.util.misc;

import net.minecraft.src.*;

public class BlockCoord
{
    private int x;
    private int y;
    private int z;
    
    public BlockCoord(final int var1, final int var2, final int var3) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
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
        return this.getX() - RenderManager.renderPosX;
    }
    
    public double getDeltaY() {
        return this.getY() - RenderManager.renderPosY;
    }
    
    public double getDeltaZ() {
        return this.getZ() - RenderManager.renderPosZ;
    }
}
