package net.minecraft.world;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;

public class WorldManager
  implements IWorldAccess
{
  private MinecraftServer mcServer;
  private WorldServer theWorldServer;
  private static final String __OBFID = "CL_00001433";
  
  public WorldManager(MinecraftServer p_i1517_1_, WorldServer p_i1517_2_)
  {
    this.mcServer = p_i1517_1_;
    this.theWorldServer = p_i1517_2_;
  }
  
  public void func_180442_a(int p_180442_1_, boolean p_180442_2_, double p_180442_3_, double p_180442_5_, double p_180442_7_, double p_180442_9_, double p_180442_11_, double p_180442_13_, int... p_180442_15_) {}
  
  public void onEntityAdded(Entity entityIn)
  {
    this.theWorldServer.getEntityTracker().trackEntity(entityIn);
  }
  
  public void onEntityRemoved(Entity entityIn)
  {
    this.theWorldServer.getEntityTracker().untrackEntity(entityIn);
  }
  
  public void playSound(String soundName, double x, double y, double z, float volume, float pitch)
  {
    this.mcServer.getConfigurationManager().sendToAllNear(x, y, z, volume > 1.0F ? 16.0F * volume : 16.0D, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
  }
  
  public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch)
  {
    this.mcServer.getConfigurationManager().sendToAllNearExcept(except, x, y, z, volume > 1.0F ? 16.0F * volume : 16.0D, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(soundName, x, y, z, volume, pitch));
  }
  
  public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}
  
  public void markBlockForUpdate(BlockPos pos)
  {
    this.theWorldServer.getPlayerManager().func_180244_a(pos);
  }
  
  public void notifyLightSet(BlockPos pos) {}
  
  public void func_174961_a(String p_174961_1_, BlockPos p_174961_2_) {}
  
  public void func_180439_a(EntityPlayer p_180439_1_, int p_180439_2_, BlockPos p_180439_3_, int p_180439_4_)
  {
    this.mcServer.getConfigurationManager().sendToAllNearExcept(p_180439_1_, p_180439_3_.getX(), p_180439_3_.getY(), p_180439_3_.getZ(), 64.0D, this.theWorldServer.provider.getDimensionId(), new S28PacketEffect(p_180439_2_, p_180439_3_, p_180439_4_, false));
  }
  
  public void func_180440_a(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_)
  {
    this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S28PacketEffect(p_180440_1_, p_180440_2_, p_180440_3_, true));
  }
  
  public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
  {
    Iterator var4 = this.mcServer.getConfigurationManager().playerEntityList.iterator();
    while (var4.hasNext())
    {
      EntityPlayerMP var5 = (EntityPlayerMP)var4.next();
      if ((var5 != null) && (var5.worldObj == this.theWorldServer) && (var5.getEntityId() != breakerId))
      {
        double var6 = pos.getX() - var5.posX;
        double var8 = pos.getY() - var5.posY;
        double var10 = pos.getZ() - var5.posZ;
        if (var6 * var6 + var8 * var8 + var10 * var10 < 1024.0D) {
          var5.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(breakerId, pos, progress));
        }
      }
    }
  }
}
