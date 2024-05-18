package net.minecraft.src;

public interface IWorldAccess
{
    void markBlockForUpdate(final int p0, final int p1, final int p2);
    
    void markBlockForRenderUpdate(final int p0, final int p1, final int p2);
    
    void markBlockRangeForRenderUpdate(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void playSound(final String p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    void playSoundToNearExcept(final EntityPlayer p0, final String p1, final double p2, final double p3, final double p4, final float p5, final float p6);
    
    void spawnParticle(final String p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6);
    
    void onEntityCreate(final Entity p0);
    
    void onEntityDestroy(final Entity p0);
    
    void playRecord(final String p0, final int p1, final int p2, final int p3);
    
    void broadcastSound(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void playAuxSFX(final EntityPlayer p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void destroyBlockPartially(final int p0, final int p1, final int p2, final int p3, final int p4);
}
