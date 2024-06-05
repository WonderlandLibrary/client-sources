package net.minecraft.src;

import java.util.*;

public class RenderSorter implements Comparator
{
    private EntityLiving baseEntity;
    
    public RenderSorter(final EntityLiving par1EntityLiving) {
        this.baseEntity = par1EntityLiving;
    }
    
    public int doCompare(final WorldRenderer par1WorldRenderer, final WorldRenderer par2WorldRenderer) {
        if (par1WorldRenderer.isInFrustum && !par2WorldRenderer.isInFrustum) {
            return 1;
        }
        if (par2WorldRenderer.isInFrustum && !par1WorldRenderer.isInFrustum) {
            return -1;
        }
        final double var3 = par1WorldRenderer.distanceToEntitySquared(this.baseEntity);
        final double var4 = par2WorldRenderer.distanceToEntitySquared(this.baseEntity);
        return (var3 < var4) ? 1 : ((var3 > var4) ? -1 : ((par1WorldRenderer.chunkIndex < par2WorldRenderer.chunkIndex) ? 1 : -1));
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.doCompare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}
