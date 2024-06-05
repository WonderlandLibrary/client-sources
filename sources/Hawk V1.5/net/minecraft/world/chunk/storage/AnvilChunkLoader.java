package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
   private List chunksToRemove = Lists.newArrayList();
   private Object syncLockObject = new Object();
   private static final Logger logger = LogManager.getLogger();
   private final File chunkSaveLocation;
   private Set pendingAnvilChunksCoordinates = Sets.newHashSet();
   private static final String __OBFID = "CL_00000384";

   private void writeChunkNBTTags(AnvilChunkLoader.PendingChunk var1) throws IOException {
      DataOutputStream var2 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, var1.chunkCoordinate.chunkXPos, var1.chunkCoordinate.chunkZPos);
      CompressedStreamTools.write(var1.nbtTags, (DataOutput)var2);
      var2.close();
   }

   public Chunk loadChunk(World var1, int var2, int var3) throws IOException {
      NBTTagCompound var4 = null;
      ChunkCoordIntPair var5 = new ChunkCoordIntPair(var2, var3);
      Object var6 = this.syncLockObject;
      synchronized(this.syncLockObject) {
         if (this.pendingAnvilChunksCoordinates.contains(var5)) {
            for(int var8 = 0; var8 < this.chunksToRemove.size(); ++var8) {
               if (((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var8)).chunkCoordinate.equals(var5)) {
                  var4 = ((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var8)).nbtTags;
                  break;
               }
            }
         }
      }

      if (var4 == null) {
         DataInputStream var7 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, var2, var3);
         if (var7 == null) {
            return null;
         }

         var4 = CompressedStreamTools.read(var7);
      }

      return this.checkedReadChunkFromNBT(var1, var2, var3, var4);
   }

   public void saveChunk(World var1, Chunk var2) throws MinecraftException, IOException {
      var1.checkSessionLock();

      try {
         NBTTagCompound var3 = new NBTTagCompound();
         NBTTagCompound var4 = new NBTTagCompound();
         var3.setTag("Level", var4);
         this.writeChunkToNBT(var2, var1, var4);
         this.addChunkToPending(var2.getChunkCoordIntPair(), var3);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   protected void addChunkToPending(ChunkCoordIntPair var1, NBTTagCompound var2) {
      Object var3 = this.syncLockObject;
      synchronized(this.syncLockObject) {
         if (this.pendingAnvilChunksCoordinates.contains(var1)) {
            for(int var5 = 0; var5 < this.chunksToRemove.size(); ++var5) {
               if (((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var5)).chunkCoordinate.equals(var1)) {
                  this.chunksToRemove.set(var5, new AnvilChunkLoader.PendingChunk(var1, var2));
                  return;
               }
            }
         }

         this.chunksToRemove.add(new AnvilChunkLoader.PendingChunk(var1, var2));
         this.pendingAnvilChunksCoordinates.add(var1);
         ThreadedFileIOBase.func_178779_a().queueIO(this);
      }
   }

   private Chunk readChunkFromNBT(World var1, NBTTagCompound var2) {
      int var3 = var2.getInteger("xPos");
      int var4 = var2.getInteger("zPos");
      Chunk var5 = new Chunk(var1, var3, var4);
      var5.setHeightMap(var2.getIntArray("HeightMap"));
      var5.setTerrainPopulated(var2.getBoolean("TerrainPopulated"));
      var5.setLightPopulated(var2.getBoolean("LightPopulated"));
      var5.setInhabitedTime(var2.getLong("InhabitedTime"));
      NBTTagList var6 = var2.getTagList("Sections", 10);
      byte var7 = 16;
      ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
      boolean var9 = !var1.provider.getHasNoSky();

      for(int var10 = 0; var10 < var6.tagCount(); ++var10) {
         NBTTagCompound var11 = var6.getCompoundTagAt(var10);
         byte var12 = var11.getByte("Y");
         ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
         byte[] var14 = var11.getByteArray("Blocks");
         NibbleArray var15 = new NibbleArray(var11.getByteArray("Data"));
         NibbleArray var16 = var11.hasKey("Add", 7) ? new NibbleArray(var11.getByteArray("Add")) : null;
         char[] var17 = new char[var14.length];

         for(int var18 = 0; var18 < var17.length; ++var18) {
            int var19 = var18 & 15;
            int var20 = var18 >> 8 & 15;
            int var21 = var18 >> 4 & 15;
            int var22 = var16 != null ? var16.get(var19, var20, var21) : 0;
            var17[var18] = (char)(var22 << 12 | (var14[var18] & 255) << 4 | var15.get(var19, var20, var21));
         }

         var13.setData(var17);
         var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight")));
         if (var9) {
            var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight")));
         }

         var13.removeInvalidBlocks();
         var8[var12] = var13;
      }

      var5.setStorageArrays(var8);
      if (var2.hasKey("Biomes", 7)) {
         var5.setBiomeArray(var2.getByteArray("Biomes"));
      }

      NBTTagList var23 = var2.getTagList("Entities", 10);
      if (var23 != null) {
         for(int var24 = 0; var24 < var23.tagCount(); ++var24) {
            NBTTagCompound var26 = var23.getCompoundTagAt(var24);
            Entity var28 = EntityList.createEntityFromNBT(var26, var1);
            var5.setHasEntities(true);
            if (var28 != null) {
               var5.addEntity(var28);
               Entity var32 = var28;

               for(NBTTagCompound var34 = var26; var34.hasKey("Riding", 10); var34 = var34.getCompoundTag("Riding")) {
                  Entity var37 = EntityList.createEntityFromNBT(var34.getCompoundTag("Riding"), var1);
                  if (var37 != null) {
                     var5.addEntity(var37);
                     var32.mountEntity(var37);
                  }

                  var32 = var37;
               }
            }
         }
      }

      NBTTagList var25 = var2.getTagList("TileEntities", 10);
      if (var25 != null) {
         for(int var27 = 0; var27 < var25.tagCount(); ++var27) {
            NBTTagCompound var30 = var25.getCompoundTagAt(var27);
            TileEntity var33 = TileEntity.createAndLoadEntity(var30);
            if (var33 != null) {
               var5.addTileEntity(var33);
            }
         }
      }

      if (var2.hasKey("TileTicks", 9)) {
         NBTTagList var29 = var2.getTagList("TileTicks", 10);
         if (var29 != null) {
            for(int var31 = 0; var31 < var29.tagCount(); ++var31) {
               NBTTagCompound var35 = var29.getCompoundTagAt(var31);
               Block var36;
               if (var35.hasKey("i", 8)) {
                  var36 = Block.getBlockFromName(var35.getString("i"));
               } else {
                  var36 = Block.getBlockById(var35.getInteger("i"));
               }

               var1.func_180497_b(new BlockPos(var35.getInteger("x"), var35.getInteger("y"), var35.getInteger("z")), var36, var35.getInteger("t"), var35.getInteger("p"));
            }
         }
      }

      return var5;
   }

   protected Chunk checkedReadChunkFromNBT(World var1, int var2, int var3, NBTTagCompound var4) {
      if (!var4.hasKey("Level", 10)) {
         logger.error(String.valueOf((new StringBuilder("Chunk file at ")).append(var2).append(",").append(var3).append(" is missing level data, skipping")));
         return null;
      } else if (!var4.getCompoundTag("Level").hasKey("Sections", 9)) {
         logger.error(String.valueOf((new StringBuilder("Chunk file at ")).append(var2).append(",").append(var3).append(" is missing block data, skipping")));
         return null;
      } else {
         Chunk var5 = this.readChunkFromNBT(var1, var4.getCompoundTag("Level"));
         if (!var5.isAtLocation(var2, var3)) {
            logger.error(String.valueOf((new StringBuilder("Chunk file at ")).append(var2).append(",").append(var3).append(" is in the wrong location; relocating. (Expected ").append(var2).append(", ").append(var3).append(", got ").append(var5.xPosition).append(", ").append(var5.zPosition).append(")")));
            var4.setInteger("xPos", var2);
            var4.setInteger("zPos", var3);
            var5 = this.readChunkFromNBT(var1, var4.getCompoundTag("Level"));
         }

         return var5;
      }
   }

   public void chunkTick() {
   }

   private void writeChunkToNBT(Chunk var1, World var2, NBTTagCompound var3) {
      var3.setByte("V", (byte)1);
      var3.setInteger("xPos", var1.xPosition);
      var3.setInteger("zPos", var1.zPosition);
      var3.setLong("LastUpdate", var2.getTotalWorldTime());
      var3.setIntArray("HeightMap", var1.getHeightMap());
      var3.setBoolean("TerrainPopulated", var1.isTerrainPopulated());
      var3.setBoolean("LightPopulated", var1.isLightPopulated());
      var3.setLong("InhabitedTime", var1.getInhabitedTime());
      ExtendedBlockStorage[] var4 = var1.getBlockStorageArray();
      NBTTagList var5 = new NBTTagList();
      boolean var6 = !var2.provider.getHasNoSky();
      ExtendedBlockStorage[] var7 = var4;
      int var8 = var4.length;

      NBTTagCompound var9;
      for(int var10 = 0; var10 < var8; ++var10) {
         ExtendedBlockStorage var11 = var7[var10];
         if (var11 != null) {
            var9 = new NBTTagCompound();
            var9.setByte("Y", (byte)(var11.getYLocation() >> 4 & 255));
            byte[] var12 = new byte[var11.getData().length];
            NibbleArray var13 = new NibbleArray();
            NibbleArray var14 = null;

            for(int var15 = 0; var15 < var11.getData().length; ++var15) {
               char var16 = var11.getData()[var15];
               int var17 = var15 & 15;
               int var18 = var15 >> 8 & 15;
               int var19 = var15 >> 4 & 15;
               if (var16 >> 12 != 0) {
                  if (var14 == null) {
                     var14 = new NibbleArray();
                  }

                  var14.set(var17, var18, var19, var16 >> 12);
               }

               var12[var15] = (byte)(var16 >> 4 & 255);
               var13.set(var17, var18, var19, var16 & 15);
            }

            var9.setByteArray("Blocks", var12);
            var9.setByteArray("Data", var13.getData());
            if (var14 != null) {
               var9.setByteArray("Add", var14.getData());
            }

            var9.setByteArray("BlockLight", var11.getBlocklightArray().getData());
            if (var6) {
               var9.setByteArray("SkyLight", var11.getSkylightArray().getData());
            } else {
               var9.setByteArray("SkyLight", new byte[var11.getBlocklightArray().getData().length]);
            }

            var5.appendTag(var9);
         }
      }

      var3.setTag("Sections", var5);
      var3.setByteArray("Biomes", var1.getBiomeArray());
      var1.setHasEntities(false);
      NBTTagList var21 = new NBTTagList();

      Iterator var22;
      for(var8 = 0; var8 < var1.getEntityLists().length; ++var8) {
         var22 = var1.getEntityLists()[var8].iterator();

         while(var22.hasNext()) {
            Entity var23 = (Entity)var22.next();
            var9 = new NBTTagCompound();
            if (var23.writeToNBTOptional(var9)) {
               var1.setHasEntities(true);
               var21.appendTag(var9);
            }
         }
      }

      var3.setTag("Entities", var21);
      NBTTagList var24 = new NBTTagList();
      var22 = var1.getTileEntityMap().values().iterator();

      while(var22.hasNext()) {
         TileEntity var25 = (TileEntity)var22.next();
         var9 = new NBTTagCompound();
         var25.writeToNBT(var9);
         var24.appendTag(var9);
      }

      var3.setTag("TileEntities", var24);
      List var26 = var2.getPendingBlockUpdates(var1, false);
      if (var26 != null) {
         long var27 = var2.getTotalWorldTime();
         NBTTagList var28 = new NBTTagList();
         Iterator var29 = var26.iterator();

         while(var29.hasNext()) {
            NextTickListEntry var30 = (NextTickListEntry)var29.next();
            NBTTagCompound var31 = new NBTTagCompound();
            ResourceLocation var20 = (ResourceLocation)Block.blockRegistry.getNameForObject(var30.func_151351_a());
            var31.setString("i", var20 == null ? "" : var20.toString());
            var31.setInteger("x", var30.field_180282_a.getX());
            var31.setInteger("y", var30.field_180282_a.getY());
            var31.setInteger("z", var30.field_180282_a.getZ());
            var31.setInteger("t", (int)(var30.scheduledTime - var27));
            var31.setInteger("p", var30.priority);
            var28.appendTag(var31);
         }

         var3.setTag("TileTicks", var28);
      }

   }

   public boolean writeNextIO() {
      AnvilChunkLoader.PendingChunk var1 = null;
      Object var2 = this.syncLockObject;
      synchronized(this.syncLockObject) {
         if (this.chunksToRemove.isEmpty()) {
            return false;
         }

         var1 = (AnvilChunkLoader.PendingChunk)this.chunksToRemove.remove(0);
         this.pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
      }

      if (var1 != null) {
         try {
            this.writeChunkNBTTags(var1);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return true;
   }

   public AnvilChunkLoader(File var1) {
      this.chunkSaveLocation = var1;
   }

   public void saveExtraChunkData(World var1, Chunk var2) {
   }

   public void saveExtraData() {
      while(this.writeNextIO()) {
      }

   }

   static class PendingChunk {
      public final NBTTagCompound nbtTags;
      private static final String __OBFID = "CL_00000385";
      public final ChunkCoordIntPair chunkCoordinate;

      public PendingChunk(ChunkCoordIntPair var1, NBTTagCompound var2) {
         this.chunkCoordinate = var1;
         this.nbtTags = var2;
      }
   }
}
