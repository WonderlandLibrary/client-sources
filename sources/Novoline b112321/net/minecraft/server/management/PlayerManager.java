package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager$PlayerInstance;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final WorldServer theWorldServer;
   private final List players = Lists.newArrayList();
   private final LongHashMap playerInstances = new LongHashMap();
   private final List playerInstancesToUpdate = Lists.newArrayList();
   private final List playerInstanceList = Lists.newArrayList();
   private int playerViewRadius;
   private long previousTotalWorldTime;
   private final int[][] xzDirectionsConst = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

   public PlayerManager(WorldServer var1) {
      this.theWorldServer = var1;
      this.setPlayerViewRadius(var1.getMinecraftServer().getConfigurationManager().getViewDistance());
   }

   public WorldServer getWorldServer() {
      return this.theWorldServer;
   }

   public void updatePlayerInstances() {
      long var1 = this.theWorldServer.getTotalWorldTime();
      if(var1 - this.previousTotalWorldTime > 8000L) {
         this.previousTotalWorldTime = var1;

         for(PlayerManager$PlayerInstance var4 : this.playerInstanceList) {
            var4.onUpdate();
            var4.processChunk();
         }
      } else {
         for(PlayerManager$PlayerInstance var8 : this.playerInstancesToUpdate) {
            var8.onUpdate();
         }
      }

      this.playerInstancesToUpdate.clear();
      if(this.players.isEmpty()) {
         WorldProvider var7 = this.theWorldServer.provider;
         if(!var7.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
         }
      }

   }

   public boolean hasPlayerInstance(int var1, int var2) {
      long var3 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
      return this.playerInstances.getValueByKey(var3) != null;
   }

   private PlayerManager$PlayerInstance getPlayerInstance(int var1, int var2, boolean var3) {
      long var4 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
      PlayerManager$PlayerInstance var6 = (PlayerManager$PlayerInstance)this.playerInstances.getValueByKey(var4);
      var6 = new PlayerManager$PlayerInstance(this, var1, var2);
      this.playerInstances.add(var4, var6);
      this.playerInstanceList.add(var6);
      return var6;
   }

   public void markBlockForUpdate(BlockPos var1) {
      int var2 = var1.getX() >> 4;
      int var3 = var1.getZ() >> 4;
      PlayerManager$PlayerInstance var4 = this.getPlayerInstance(var2, var3, false);
      var4.flagChunkForUpdate(var1.getX() & 15, var1.getY(), var1.getZ() & 15);
   }

   public void addPlayer(EntityPlayerMP var1) {
      int var2 = (int)var1.posX >> 4;
      int var3 = (int)var1.posZ >> 4;
      var1.managedPosX = var1.posX;
      var1.managedPosZ = var1.posZ;

      for(int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
         for(int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
            this.getPlayerInstance(var4, var5, true).addPlayer(var1);
         }
      }

      this.players.add(var1);
      this.filterChunkLoadQueue(var1);
   }

   public void filterChunkLoadQueue(EntityPlayerMP var1) {
      ArrayList var2 = Lists.newArrayList(var1.loadedChunks);
      int var3 = 0;
      int var4 = this.playerViewRadius;
      int var5 = (int)var1.posX >> 4;
      int var6 = (int)var1.posZ >> 4;
      int var7 = 0;
      int var8 = 0;
      ChunkCoordIntPair var9 = PlayerManager$PlayerInstance.access$000(this.getPlayerInstance(var5, var6, true));
      var1.loadedChunks.clear();
      if(var2.contains(var9)) {
         var1.loadedChunks.add(var9);
      }

      for(int var10 = 1; var10 <= var4 * 2; ++var10) {
         for(int var11 = 0; var11 < 2; ++var11) {
            int[] var12 = this.xzDirectionsConst[var3++ % 4];

            for(int var13 = 0; var13 < var10; ++var13) {
               var7 += var12[0];
               var8 += var12[1];
               var9 = PlayerManager$PlayerInstance.access$000(this.getPlayerInstance(var5 + var7, var6 + var8, true));
               if(var2.contains(var9)) {
                  var1.loadedChunks.add(var9);
               }
            }
         }
      }

      var3 = var3 % 4;

      for(int var17 = 0; var17 < var4 * 2; ++var17) {
         var7 += this.xzDirectionsConst[var3][0];
         var8 += this.xzDirectionsConst[var3][1];
         var9 = PlayerManager$PlayerInstance.access$000(this.getPlayerInstance(var5 + var7, var6 + var8, true));
         if(var2.contains(var9)) {
            var1.loadedChunks.add(var9);
         }
      }

   }

   public void removePlayer(EntityPlayerMP var1) {
      int var2 = (int)var1.managedPosX >> 4;
      int var3 = (int)var1.managedPosZ >> 4;

      for(int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
         for(int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
            PlayerManager$PlayerInstance var6 = this.getPlayerInstance(var4, var5, false);
            var6.removePlayer(var1);
         }
      }

      this.players.remove(var1);
   }

   private boolean overlaps(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 - var3;
      int var7 = var2 - var4;
      return var6 >= -var5 && var6 <= var5 && var7 >= -var5 && var7 <= var5;
   }

   public void updateMountedMovingPlayer(EntityPlayerMP var1) {
      int var2 = (int)var1.posX >> 4;
      int var3 = (int)var1.posZ >> 4;
      double var4 = var1.managedPosX - var1.posX;
      double var6 = var1.managedPosZ - var1.posZ;
      double var8 = var4 * var4 + var6 * var6;
      if(var8 >= 64.0D) {
         int var10 = (int)var1.managedPosX >> 4;
         int var11 = (int)var1.managedPosZ >> 4;
         int var12 = this.playerViewRadius;
         int var13 = var2 - var10;
         int var14 = var3 - var11;

         for(int var15 = var2 - var12; var15 <= var2 + var12; ++var15) {
            for(int var16 = var3 - var12; var16 <= var3 + var12; ++var16) {
               if(!this.overlaps(var15, var16, var10, var11, var12)) {
                  this.getPlayerInstance(var15, var16, true).addPlayer(var1);
               }

               if(!this.overlaps(var15 - var13, var16 - var14, var2, var3, var12)) {
                  PlayerManager$PlayerInstance var17 = this.getPlayerInstance(var15 - var13, var16 - var14, false);
                  var17.removePlayer(var1);
               }
            }
         }

         this.filterChunkLoadQueue(var1);
         var1.managedPosX = var1.posX;
         var1.managedPosZ = var1.posZ;
      }

   }

   public boolean isPlayerWatchingChunk(EntityPlayerMP var1, int var2, int var3) {
      PlayerManager$PlayerInstance var4 = this.getPlayerInstance(var2, var3, false);
      return PlayerManager$PlayerInstance.access$100(var4).contains(var1) && !var1.loadedChunks.contains(PlayerManager$PlayerInstance.access$000(var4));
   }

   public void setPlayerViewRadius(int var1) {
      var1 = MathHelper.clamp_int(var1, 3, 32);
      if(var1 != this.playerViewRadius) {
         int var2 = var1 - this.playerViewRadius;

         for(EntityPlayerMP var4 : Lists.newArrayList(this.players)) {
            int var5 = (int)var4.posX >> 4;
            int var6 = (int)var4.posZ >> 4;

            for(int var7 = var5 - var1; var7 <= var5 + var1; ++var7) {
               for(int var8 = var6 - var1; var8 <= var6 + var1; ++var8) {
                  PlayerManager$PlayerInstance var9 = this.getPlayerInstance(var7, var8, true);
                  if(!PlayerManager$PlayerInstance.access$100(var9).contains(var4)) {
                     var9.addPlayer(var4);
                  }
               }
            }
         }

         this.playerViewRadius = var1;
      }

   }

   public static int getFurthestViewableBlock(int var0) {
      return var0 * 16 - 16;
   }

   static Logger access$200() {
      return LOGGER;
   }

   static WorldServer access$300(PlayerManager var0) {
      return var0.theWorldServer;
   }

   static LongHashMap access$400(PlayerManager var0) {
      return var0.playerInstances;
   }

   static List access$500(PlayerManager var0) {
      return var0.playerInstanceList;
   }

   static List access$600(PlayerManager var0) {
      return var0.playerInstancesToUpdate;
   }
}
