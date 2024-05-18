package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class WorldManager implements IWorldAccess
{
    private MinecraftServer mcServer;
    private WorldServer theWorldServer;
    
    public WorldManager(final MinecraftServer par1MinecraftServer, final WorldServer par2WorldServer) {
        this.mcServer = par1MinecraftServer;
        this.theWorldServer = par2WorldServer;
    }
    
    @Override
    public void spawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
    }
    
    @Override
    public void onEntityCreate(final Entity par1Entity) {
        this.theWorldServer.getEntityTracker().addEntityToTracker(par1Entity);
    }
    
    @Override
    public void onEntityDestroy(final Entity par1Entity) {
        this.theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(par1Entity);
    }
    
    @Override
    public void playSound(final String par1Str, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.mcServer.getConfigurationManager().sendToAllNear(par2, par4, par6, (par8 > 1.0f) ? ((double)(16.0f * par8)) : 16.0, this.theWorldServer.provider.dimensionId, new Packet62LevelSound(par1Str, par2, par4, par6, par8, par9));
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer par1EntityPlayer, final String par2Str, final double par3, final double par5, final double par7, final float par9, final float par10) {
        this.mcServer.getConfigurationManager().sendToAllNearExcept(par1EntityPlayer, par3, par5, par7, (par9 > 1.0f) ? ((double)(16.0f * par9)) : 16.0, this.theWorldServer.provider.dimensionId, new Packet62LevelSound(par2Str, par3, par5, par7, par9, par10));
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
    }
    
    @Override
    public void markBlockForUpdate(final int par1, final int par2, final int par3) {
        this.theWorldServer.getPlayerManager().flagChunkForUpdate(par1, par2, par3);
    }
    
    @Override
    public void markBlockForRenderUpdate(final int par1, final int par2, final int par3) {
    }
    
    @Override
    public void playRecord(final String par1Str, final int par2, final int par3, final int par4) {
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.mcServer.getConfigurationManager().sendToAllNearExcept(par1EntityPlayer, par3, par4, par5, 64.0, this.theWorldServer.provider.dimensionId, new Packet61DoorChange(par2, par3, par4, par5, par6, false));
    }
    
    @Override
    public void broadcastSound(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet61DoorChange(par1, par2, par3, par4, par5, true));
    }
    
    @Override
    public void destroyBlockPartially(final int par1, final int par2, final int par3, final int par4, final int par5) {
        for (final EntityPlayerMP var7 : this.mcServer.getConfigurationManager().playerEntityList) {
            if (var7 != null && var7.worldObj == this.theWorldServer && var7.entityId != par1) {
                final double var8 = par2 - var7.posX;
                final double var9 = par3 - var7.posY;
                final double var10 = par4 - var7.posZ;
                if (var8 * var8 + var9 * var9 + var10 * var10 >= 1024.0) {
                    continue;
                }
                var7.playerNetServerHandler.sendPacketToPlayer(new Packet55BlockDestroy(par1, par2, par3, par4, par5));
            }
        }
    }
}
