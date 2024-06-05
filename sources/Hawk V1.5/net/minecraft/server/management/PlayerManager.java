package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerManager {
   private long previousTotalWorldTime;
   private static final String __OBFID = "CL_00001434";
   private final LongHashMap playerInstances = new LongHashMap();
   private final List playerInstancesToUpdate = Lists.newArrayList();
   private final int[][] xzDirectionsConst = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
   private final WorldServer theWorldServer;
   private int playerViewRadius;
   private final List playerInstanceList = Lists.newArrayList();
   private final List players = Lists.newArrayList();
   private static final Logger field_152627_a = LogManager.getLogger();

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

   public void removePlayer(EntityPlayerMP var1) {
      int var2 = (int)var1.managedPosX >> 4;
      int var3 = (int)var1.managedPosZ >> 4;

      for(int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
         for(int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
            PlayerManager.PlayerInstance var6 = this.getPlayerInstance(var4, var5, false);
            if (var6 != null) {
               var6.removePlayer(var1);
            }
         }
      }

      this.players.remove(var1);
   }

   public WorldServer getMinecraftServer() {
      return this.theWorldServer;
   }

   private boolean overlaps(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 - var3;
      int var7 = var2 - var4;
      return var6 >= -var5 && var6 <= var5 ? var7 >= -var5 && var7 <= var5 : false;
   }

   public boolean func_152621_a(int var1, int var2) {
      long var3 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
      return this.playerInstances.getValueByKey(var3) != null;
   }

   public void func_180244_a(BlockPos var1) {
      int var2 = var1.getX() >> 4;
      int var3 = var1.getZ() >> 4;
      PlayerManager.PlayerInstance var4 = this.getPlayerInstance(var2, var3, false);
      if (var4 != null) {
         var4.flagChunkForUpdate(var1.getX() & 15, var1.getY(), var1.getZ() & 15);
      }

   }

   static WorldServer access$1(PlayerManager var0) {
      return var0.theWorldServer;
   }

   public PlayerManager(WorldServer var1) {
      this.theWorldServer = var1;
      this.func_152622_a(var1.func_73046_m().getConfigurationManager().getViewDistance());
   }

   static Logger access$0() {
      return field_152627_a;
   }

   static LongHashMap access$2(PlayerManager var0) {
      return var0.playerInstances;
   }

   public void func_152622_a(int var1) {
      var1 = MathHelper.clamp_int(var1, 3, 32);
      if (var1 != this.playerViewRadius) {
         int var2 = var1 - this.playerViewRadius;
         ArrayList var3 = Lists.newArrayList(this.players);
         Iterator var4 = var3.iterator();

         while(true) {
            while(var4.hasNext()) {
               EntityPlayerMP var5 = (EntityPlayerMP)var4.next();
               int var6 = (int)var5.posX >> 4;
               int var7 = (int)var5.posZ >> 4;
               int var8;
               int var9;
               if (var2 > 0) {
                  for(var8 = var6 - var1; var8 <= var6 + var1; ++var8) {
                     for(var9 = var7 - var1; var9 <= var7 + var1; ++var9) {
                        PlayerManager.PlayerInstance var10 = this.getPlayerInstance(var8, var9, true);
                        if (!PlayerManager.PlayerInstance.access$1(var10).contains(var5)) {
                           var10.addPlayer(var5);
                        }
                     }
                  }
               } else {
                  for(var8 = var6 - this.playerViewRadius; var8 <= var6 + this.playerViewRadius; ++var8) {
                     for(var9 = var7 - this.playerViewRadius; var9 <= var7 + this.playerViewRadius; ++var9) {
                        if (!this.overlaps(var8, var9, var6, var7, var1)) {
                           this.getPlayerInstance(var8, var9, true).removePlayer(var5);
                        }
                     }
                  }
               }
            }

            this.playerViewRadius = var1;
            break;
         }
      }

   }

   static List access$3(PlayerManager var0) {
      return var0.playerInstanceList;
   }

   public boolean isPlayerWatchingChunk(EntityPlayerMP var1, int var2, int var3) {
      PlayerManager.PlayerInstance var4 = this.getPlayerInstance(var2, var3, false);
      return var4 != null && PlayerManager.PlayerInstance.access$1(var4).contains(var1) && !var1.loadedChunks.contains(PlayerManager.PlayerInstance.access$0(var4));
   }

   static List access$4(PlayerManager var0) {
      return var0.playerInstancesToUpdate;
   }

   public void updateMountedMovingPlayer(EntityPlayerMP var1) {
      int var2 = (int)var1.posX >> 4;
      int var3 = (int)var1.posZ >> 4;
      double var4 = var1.managedPosX - var1.posX;
      double var6 = var1.managedPosZ - var1.posZ;
      double var8 = var4 * var4 + var6 * var6;
      if (var8 >= 64.0D) {
         int var10 = (int)var1.managedPosX >> 4;
         int var11 = (int)var1.managedPosZ >> 4;
         int var12 = this.playerViewRadius;
         int var13 = var2 - var10;
         int var14 = var3 - var11;
         if (var13 != 0 || var14 != 0) {
            for(int var15 = var2 - var12; var15 <= var2 + var12; ++var15) {
               for(int var16 = var3 - var12; var16 <= var3 + var12; ++var16) {
                  if (!this.overlaps(var15, var16, var10, var11, var12)) {
                     this.getPlayerInstance(var15, var16, true).addPlayer(var1);
                  }

                  if (!this.overlaps(var15 - var13, var16 - var14, var2, var3, var12)) {
                     PlayerManager.PlayerInstance var17 = this.getPlayerInstance(var15 - var13, var16 - var14, false);
                     if (var17 != null) {
                        var17.removePlayer(var1);
                     }
                  }
               }
            }

            this.filterChunkLoadQueue(var1);
            var1.managedPosX = var1.posX;
            var1.managedPosZ = var1.posZ;
         }
      }

   }

   public static int getFurthestViewableBlock(int var0) {
      return var0 * 16 - 16;
   }

   private PlayerManager.PlayerInstance getPlayerInstance(int var1, int var2, boolean var3) {
      long var4 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
      PlayerManager.PlayerInstance var6 = (PlayerManager.PlayerInstance)this.playerInstances.getValueByKey(var4);
      if (var6 == null && var3) {
         var6 = new PlayerManager.PlayerInstance(this, var1, var2);
         this.playerInstances.add(var4, var6);
         this.playerInstanceList.add(var6);
      }

      return var6;
   }

   public void updatePlayerInstances() {
      long var1 = this.theWorldServer.getTotalWorldTime();
      int var3;
      PlayerManager.PlayerInstance var4;
      if (var1 - this.previousTotalWorldTime > 8000L) {
         this.previousTotalWorldTime = var1;

         for(var3 = 0; var3 < this.playerInstanceList.size(); ++var3) {
            var4 = (PlayerManager.PlayerInstance)this.playerInstanceList.get(var3);
            var4.onUpdate();
            var4.processChunk();
         }
      } else {
         for(var3 = 0; var3 < this.playerInstancesToUpdate.size(); ++var3) {
            var4 = (PlayerManager.PlayerInstance)this.playerInstancesToUpdate.get(var3);
            var4.onUpdate();
         }
      }

      this.playerInstancesToUpdate.clear();
      if (this.players.isEmpty()) {
         WorldProvider var5 = this.theWorldServer.provider;
         if (!var5.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
         }
      }

   }

   public void filterChunkLoadQueue(EntityPlayerMP var1) {
      ArrayList var2 = Lists.newArrayList(var1.loadedChunks);
      int var3 = 0;
      int var4 = this.playerViewRadius;
      int var5 = (int)var1.posX >> 4;
      int var6 = (int)var1.posZ >> 4;
      int var7 = 0;
      int var8 = 0;
      ChunkCoordIntPair var9 = PlayerManager.PlayerInstance.access$0(this.getPlayerInstance(var5, var6, true));
      var1.loadedChunks.clear();
      if (var2.contains(var9)) {
         var1.loadedChunks.add(var9);
      }

      int var10;
      for(var10 = 1; var10 <= var4 * 2; ++var10) {
         for(int var11 = 0; var11 < 2; ++var11) {
            int[] var12 = this.xzDirectionsConst[var3++ % 4];

            for(int var13 = 0; var13 < var10; ++var13) {
               var7 += var12[0];
               var8 += var12[1];
               var9 = PlayerManager.PlayerInstance.access$0(this.getPlayerInstance(var5 + var7, var6 + var8, true));
               if (var2.contains(var9)) {
                  var1.loadedChunks.add(var9);
               }
            }
         }
      }

      var3 %= 4;

      for(var10 = 0; var10 < var4 * 2; ++var10) {
         var7 += this.xzDirectionsConst[var3][0];
         var8 += this.xzDirectionsConst[var3][1];
         var9 = PlayerManager.PlayerInstance.access$0(this.getPlayerInstance(var5 + var7, var6 + var8, true));
         if (var2.contains(var9)) {
            var1.loadedChunks.add(var9);
         }
      }

   }

   class PlayerInstance {
      private short[] locationOfBlockChange;
      private final List playersWatchingChunk;
      private int flagsYAreasToUpdate;
      private long previousWorldTime;
      private final ChunkCoordIntPair currentChunk;
      final PlayerManager this$0;
      private int numBlocksToUpdate;
      private static final String __OBFID = "CL_00001435";

      static ChunkCoordIntPair access$0(PlayerManager.PlayerInstance var0) {
         return var0.currentChunk;
      }

      private void increaseInhabitedTime(Chunk var1) {
         var1.setInhabitedTime(var1.getInhabitedTime() + PlayerManager.access$1(this.this$0).getTotalWorldTime() - this.previousWorldTime);
         this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
      }

      static List access$1(PlayerManager.PlayerInstance var0) {
         return var0.playersWatchingChunk;
      }

      public void processChunk() {
         this.increaseInhabitedTime(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos));
      }

      public void flagChunkForUpdate(int var1, int var2, int var3) {
         if (this.numBlocksToUpdate == 0) {
            PlayerManager.access$4(this.this$0).add(this);
         }

         this.flagsYAreasToUpdate |= 1 << (var2 >> 4);
         if (this.numBlocksToUpdate < 64) {
            short var4 = (short)(var1 << 12 | var3 << 8 | var2);

            for(int var5 = 0; var5 < this.numBlocksToUpdate; ++var5) {
               if (this.locationOfBlockChange[var5] == var4) {
                  return;
               }
            }

            this.locationOfBlockChange[this.numBlocksToUpdate++] = var4;
         }

      }

      public void sendToAllPlayersWatchingChunk(Packet var1) {
         for(int var2 = 0; var2 < this.playersWatchingChunk.size(); ++var2) {
            EntityPlayerMP var3 = (EntityPlayerMP)this.playersWatchingChunk.get(var2);
            if (!var3.loadedChunks.contains(this.currentChunk)) {
               var3.playerNetServerHandler.sendPacket(var1);
            }
         }

      }

      public void addPlayer(EntityPlayerMP var1) {
         if (this.playersWatchingChunk.contains(var1)) {
            PlayerManager.access$0().debug("Failed to add player. {} already is in chunk {}, {}", new Object[]{var1, this.currentChunk.chunkXPos, this.currentChunk.chunkZPos});
         } else {
            if (this.playersWatchingChunk.isEmpty()) {
               this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
            }

            this.playersWatchingChunk.add(var1);
            var1.loadedChunks.add(this.currentChunk);
         }

      }

      private void sendTileToAllPlayersWatchingChunk(TileEntity var1) {
         if (var1 != null) {
            Packet var2 = var1.getDescriptionPacket();
            if (var2 != null) {
               this.sendToAllPlayersWatchingChunk(var2);
            }
         }

      }

      public void onUpdate() {
         if (this.numBlocksToUpdate != 0) {
            int var1;
            int var2;
            int var3;
            if (this.numBlocksToUpdate == 1) {
               var1 = (this.locationOfBlockChange[0] >> 12 & 15) + this.currentChunk.chunkXPos * 16;
               var2 = this.locationOfBlockChange[0] & 255;
               var3 = (this.locationOfBlockChange[0] >> 8 & 15) + this.currentChunk.chunkZPos * 16;
               BlockPos var7 = new BlockPos(var1, var2, var3);
               this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.access$1(this.this$0), var7));
               if (PlayerManager.access$1(this.this$0).getBlockState(var7).getBlock().hasTileEntity()) {
                  this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(var7));
               }
            } else {
               int var4;
               if (this.numBlocksToUpdate != 64) {
                  this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos)));

                  for(var1 = 0; var1 < this.numBlocksToUpdate; ++var1) {
                     var2 = (this.locationOfBlockChange[var1] >> 12 & 15) + this.currentChunk.chunkXPos * 16;
                     var3 = this.locationOfBlockChange[var1] & 255;
                     var4 = (this.locationOfBlockChange[var1] >> 8 & 15) + this.currentChunk.chunkZPos * 16;
                     BlockPos var8 = new BlockPos(var2, var3, var4);
                     if (PlayerManager.access$1(this.this$0).getBlockState(var8).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(var8));
                     }
                  }
               } else {
                  var1 = this.currentChunk.chunkXPos * 16;
                  var2 = this.currentChunk.chunkZPos * 16;
                  this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos), false, this.flagsYAreasToUpdate));

                  for(var3 = 0; var3 < 16; ++var3) {
                     if ((this.flagsYAreasToUpdate & 1 << var3) != 0) {
                        var4 = var3 << 4;
                        List var5 = PlayerManager.access$1(this.this$0).func_147486_a(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);

                        for(int var6 = 0; var6 < var5.size(); ++var6) {
                           this.sendTileToAllPlayersWatchingChunk((TileEntity)var5.get(var6));
                        }
                     }
                  }
               }
            }

            this.numBlocksToUpdate = 0;
            this.flagsYAreasToUpdate = 0;
         }

      }

      public PlayerInstance(PlayerManager var1, int var2, int var3) {
         this.this$0 = var1;
         this.playersWatchingChunk = Lists.newArrayList();
         this.locationOfBlockChange = new short[64];
         this.currentChunk = new ChunkCoordIntPair(var2, var3);
         var1.getMinecraftServer().theChunkProviderServer.loadChunk(var2, var3);
      }

      public void removePlayer(EntityPlayerMP var1) {
         if (this.playersWatchingChunk.contains(var1)) {
            Chunk var2 = PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            if (var2.isPopulated()) {
               var1.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
            }

            this.playersWatchingChunk.remove(var1);
            var1.loadedChunks.remove(this.currentChunk);
            if (this.playersWatchingChunk.isEmpty()) {
               long var3 = (long)this.currentChunk.chunkXPos + 2147483647L | (long)this.currentChunk.chunkZPos + 2147483647L << 32;
               this.increaseInhabitedTime(var2);
               PlayerManager.access$2(this.this$0).remove(var3);
               PlayerManager.access$3(this.this$0).remove(this);
               if (this.numBlocksToUpdate > 0) {
                  PlayerManager.access$4(this.this$0).remove(this);
               }

               this.this$0.getMinecraftServer().theChunkProviderServer.dropChunk(this.currentChunk.chunkXPos, this.currentChunk.chunkZPos);
            }
         }

      }
   }
}
