package net.minecraft.world;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class WorldManager implements IWorldAccess {
   private static final String __OBFID = "CL_00001433";
   private WorldServer theWorldServer;
   private MinecraftServer mcServer;

   public void markBlockForUpdate(BlockPos var1) {
      this.theWorldServer.getPlayerManager().func_180244_a(var1);
   }

   public WorldManager(MinecraftServer var1, WorldServer var2) {
      this.mcServer = var1;
      this.theWorldServer = var2;
   }

   public void notifyLightSet(BlockPos var1) {
   }

   public void func_180442_a(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
   }

   public void playSoundToNearExcept(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {
      this.mcServer.getConfigurationManager().sendToAllNearExcept(var1, var3, var5, var7, var9 > 1.0F ? (double)(16.0F * var9) : 16.0D, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(var2, var3, var5, var7, var9, var10));
   }

   public void func_180440_a(int var1, BlockPos var2, int var3) {
      this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S28PacketEffect(var1, var2, var3, true));
   }

   public void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      this.mcServer.getConfigurationManager().sendToAllNearExcept(var1, (double)var3.getX(), (double)var3.getY(), (double)var3.getZ(), 64.0D, this.theWorldServer.provider.getDimensionId(), new S28PacketEffect(var2, var3, var4, false));
   }

   public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public void func_174961_a(String var1, BlockPos var2) {
   }

   public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
      this.mcServer.getConfigurationManager().sendToAllNear(var2, var4, var6, var8 > 1.0F ? (double)(16.0F * var8) : 16.0D, this.theWorldServer.provider.getDimensionId(), new S29PacketSoundEffect(var1, var2, var4, var6, var8, var9));
   }

   public void sendBlockBreakProgress(int var1, BlockPos var2, int var3) {
      Iterator var4 = this.mcServer.getConfigurationManager().playerEntityList.iterator();

      while(var4.hasNext()) {
         EntityPlayerMP var5 = (EntityPlayerMP)var4.next();
         if (var5 != null && var5.worldObj == this.theWorldServer && var5.getEntityId() != var1) {
            double var6 = (double)var2.getX() - var5.posX;
            double var8 = (double)var2.getY() - var5.posY;
            double var10 = (double)var2.getZ() - var5.posZ;
            if (var6 * var6 + var8 * var8 + var10 * var10 < 1024.0D) {
               var5.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(var1, var2, var3));
            }
         }
      }

   }

   public void onEntityRemoved(Entity var1) {
      this.theWorldServer.getEntityTracker().untrackEntity(var1);
   }

   public void onEntityAdded(Entity var1) {
      this.theWorldServer.getEntityTracker().trackEntity(var1);
   }
}
