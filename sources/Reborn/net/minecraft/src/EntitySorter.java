package net.minecraft.src;

import java.util.*;

public class EntitySorter implements Comparator
{
    private double entityPosX;
    private double entityPosY;
    private double entityPosZ;
    
    public EntitySorter(final Entity par1Entity) {
        this.entityPosX = -par1Entity.posX;
        this.entityPosY = -par1Entity.posY;
        this.entityPosZ = -par1Entity.posZ;
    }
    
    public int sortByDistanceToEntity(final WorldRenderer par1WorldRenderer, final WorldRenderer par2WorldRenderer) {
        final double var3 = par1WorldRenderer.posXPlus + this.entityPosX;
        final double var4 = par1WorldRenderer.posYPlus + this.entityPosY;
        final double var5 = par1WorldRenderer.posZPlus + this.entityPosZ;
        final double var6 = par2WorldRenderer.posXPlus + this.entityPosX;
        final double var7 = par2WorldRenderer.posYPlus + this.entityPosY;
        final double var8 = par2WorldRenderer.posZPlus + this.entityPosZ;
        return (int)((var3 * var3 + var4 * var4 + var5 * var5 - (var6 * var6 + var7 * var7 + var8 * var8)) * 1024.0);
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.sortByDistanceToEntity((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}
