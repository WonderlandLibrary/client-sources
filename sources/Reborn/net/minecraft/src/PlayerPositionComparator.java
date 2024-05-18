package net.minecraft.src;

import java.util.*;

public class PlayerPositionComparator implements Comparator
{
    private final ChunkCoordinates theChunkCoordinates;
    
    public PlayerPositionComparator(final ChunkCoordinates par1ChunkCoordinates) {
        this.theChunkCoordinates = par1ChunkCoordinates;
    }
    
    public int comparePlayers(final EntityPlayerMP par1EntityPlayerMP, final EntityPlayerMP par2EntityPlayerMP) {
        final double var3 = par1EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
        final double var4 = par2EntityPlayerMP.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
        return (var3 < var4) ? -1 : ((var3 > var4) ? 1 : 0);
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.comparePlayers((EntityPlayerMP)par1Obj, (EntityPlayerMP)par2Obj);
    }
}
