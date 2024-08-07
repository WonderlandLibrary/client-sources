package net.minecraft.world;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public interface IWorldAccess
{
    void onEntityRemoved(final Entity p0);
    
    void markBlockForUpdate(final BlockPos p0);
    
    void markBlockRangeForRenderUpdate(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void spawnParticle(final int p0, final boolean p1, final double p2, final double p3, final double p4, final double p5, final double p6, final double p7, final int... p8);
    
    void onEntityAdded(final Entity p0);
    
    void playSoundToNearExcept(final EntityPlayer p0, final String p1, final double p2, final double p3, final double p4, final float p5, final float p6);
    
    void playRecord(final String p0, final BlockPos p1);
    
    void notifyLightSet(final BlockPos p0);
    
    void broadcastSound(final int p0, final BlockPos p1, final int p2);
    
    void playAuxSFX(final EntityPlayer p0, final int p1, final BlockPos p2, final int p3);
    
    void sendBlockBreakProgress(final int p0, final BlockPos p1, final int p2);
    
    void playSound(final String p0, final double p1, final double p2, final double p3, final float p4, final float p5);
}
