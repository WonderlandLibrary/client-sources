package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$AxisDirection;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk$1;
import net.minecraft.world.chunk.Chunk$2;
import net.minecraft.world.chunk.Chunk$4;
import net.minecraft.world.chunk.Chunk$EnumCreateEntityType;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ExtendedBlockStorage[] storageArrays;
   private final byte[] blockBiomeArray;
   private final int[] precipitationHeightMap;
   private final boolean[] updateSkylightColumns;
   private boolean isChunkLoaded;
   private final World worldObj;
   private final int[] heightMap;
   public final int xPosition;
   public final int zPosition;
   private boolean isGapLightingUpdated;
   private final Map chunkTileEntityMap;
   private final ClassInheritanceMultiMap[] entityLists;
   private boolean isTerrainPopulated;
   private boolean isLightPopulated;
   private boolean field_150815_m;
   private boolean isModified;
   private boolean hasEntities;
   private long lastSaveTime;
   private int heightMapMinimum;
   private long inhabitedTime;
   private int queuedLightChecks;
   private final ConcurrentLinkedQueue tileEntityPosQueue;

   public Chunk(World var1, int var2, int var3) {
      this.storageArrays = new ExtendedBlockStorage[16];
      this.blockBiomeArray = new byte[256];
      this.precipitationHeightMap = new int[256];
      this.updateSkylightColumns = new boolean[256];
      this.chunkTileEntityMap = Maps.newHashMap();
      this.queuedLightChecks = 4096;
      this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
      this.entityLists = (ClassInheritanceMultiMap[])(new ClassInheritanceMultiMap[16]);
      this.worldObj = var1;
      this.xPosition = var2;
      this.zPosition = var3;
      this.heightMap = new int[256];

      for(int var4 = 0; var4 < this.entityLists.length; ++var4) {
         this.entityLists[var4] = new ClassInheritanceMultiMap(Entity.class);
      }

      Arrays.fill(this.precipitationHeightMap, -999);
      Arrays.fill(this.blockBiomeArray, (byte)-1);
   }

   public Chunk(World var1, ChunkPrimer var2, int var3, int var4) {
      this(var1, var3, var4);
      boolean var5 = true;
      boolean var6 = !var1.provider.getHasNoSky();

      for(int var7 = 0; var7 < 16; ++var7) {
         for(int var8 = 0; var8 < 16; ++var8) {
            for(int var9 = 0; var9 < 256; ++var9) {
               int var10 = var7 * 256 * 16 | var8 * 256 | var9;
               IBlockState var11 = var2.getBlockState(var10);
               if(var11.getBlock().getMaterial() != Material.air) {
                  int var12 = var9 >> 4;
                  if(this.storageArrays[var12] == null) {
                     this.storageArrays[var12] = new ExtendedBlockStorage(var12 << 4, var6);
                  }

                  this.storageArrays[var12].set(var7, var9 & 15, var8, var11);
               }
            }
         }
      }

   }

   public boolean isAtLocation(int var1, int var2) {
      return var1 == this.xPosition && var2 == this.zPosition;
   }

   public int getHeight(BlockPos var1) {
      return this.getHeightValue(var1.getX() & 15, var1.getZ() & 15);
   }

   public int getHeightValue(int var1, int var2) {
      return this.heightMap[var2 << 4 | var1];
   }

   public int getTopFilledSegment() {
      int var1;
      for(var1 = this.storageArrays.length - 1; this.storageArrays[var1] == null; --var1) {
         ;
      }

      return this.storageArrays[var1].getYLocation();
   }

   public ExtendedBlockStorage[] getBlockStorageArray() {
      return this.storageArrays;
   }

   protected void generateHeightMap() {
      int var1 = this.getTopFilledSegment();
      this.heightMapMinimum = Integer.MAX_VALUE;

      for(int var2 = 0; var2 < 16; ++var2) {
         for(int var3 = 0; var3 < 16; ++var3) {
            this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
            int var4 = var1 + 16;

            while(true) {
               Block var5 = this.getBlock0(var2, var4 - 1, var3);
               if(var5.getLightOpacity() != 0) {
                  this.heightMap[var3 << 4 | var2] = var4;
                  if(var4 < this.heightMapMinimum) {
                     this.heightMapMinimum = var4;
                  }
                  break;
               }

               --var4;
            }
         }
      }

      this.isModified = true;
   }

   public void generateSkylightMap() {
      int var1 = this.getTopFilledSegment();
      this.heightMapMinimum = Integer.MAX_VALUE;

      for(int var2 = 0; var2 < 16; ++var2) {
         for(int var3 = 0; var3 < 16; ++var3) {
            this.precipitationHeightMap[var2 + (var3 << 4)] = -999;

            int var4;
            for(var4 = var1 + 16; this.getBlockLightOpacity(var2, var4 - 1, var3) == 0; --var4) {
               ;
            }

            this.heightMap[var3 << 4 | var2] = var4;
            if(var4 < this.heightMapMinimum) {
               this.heightMapMinimum = var4;
            }

            if(!this.worldObj.provider.getHasNoSky()) {
               var4 = 15;
               int var5 = var1 + 16 - 1;
               int var6 = this.getBlockLightOpacity(var2, var5, var3);
               if(var4 != 15) {
                  var6 = 1;
               }

               var4 = var4 - var6;
               ExtendedBlockStorage var7 = this.storageArrays[var5 >> 4];
               var7.setExtSkylightValue(var2, var5 & 15, var3, var4);
               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + var2, var5, (this.zPosition << 4) + var3));
               --var5;
            }
         }
      }

      this.isModified = true;
   }

   private void propagateSkylightOcclusion(int var1, int var2) {
      this.updateSkylightColumns[var1 + var2 * 16] = true;
      this.isGapLightingUpdated = true;
   }

   private void recheckGaps(boolean var1) {
      this.worldObj.theProfiler.startSection("recheckGaps");
      if(this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
         for(int var2 = 0; var2 < 16; ++var2) {
            for(int var3 = 0; var3 < 16; ++var3) {
               if(this.updateSkylightColumns[var2 + var3 * 16]) {
                  this.updateSkylightColumns[var2 + var3 * 16] = false;
                  int var4 = this.getHeightValue(var2, var3);
                  int var5 = this.xPosition * 16 + var2;
                  int var6 = this.zPosition * 16 + var3;
                  int var7 = Integer.MAX_VALUE;

                  for(Object var9 : EnumFacing$Plane.HORIZONTAL) {
                     var7 = Math.min(var7, this.worldObj.getChunksLowestHorizon(var5 + ((EnumFacing)var9).getFrontOffsetX(), var6 + ((EnumFacing)var9).getFrontOffsetZ()));
                  }

                  this.checkSkylightNeighborHeight(var5, var6, var7);

                  for(Object var11 : EnumFacing$Plane.HORIZONTAL) {
                     this.checkSkylightNeighborHeight(var5 + ((EnumFacing)var11).getFrontOffsetX(), var6 + ((EnumFacing)var11).getFrontOffsetZ(), var4);
                  }

                  this.worldObj.theProfiler.endSection();
                  return;
               }
            }
         }

         this.isGapLightingUpdated = false;
      }

      this.worldObj.theProfiler.endSection();
   }

   private void checkSkylightNeighborHeight(int var1, int var2, int var3) {
      int var4 = this.worldObj.getHeight(new BlockPos(var1, 0, var2)).getY();
      if(var4 > var3) {
         this.updateSkylightNeighborHeight(var1, var2, var3, var4 + 1);
      } else if(var4 < var3) {
         this.updateSkylightNeighborHeight(var1, var2, var4, var3 + 1);
      }

   }

   private void updateSkylightNeighborHeight(int var1, int var2, int var3, int var4) {
      if(var4 > var3 && this.worldObj.isAreaLoaded(new BlockPos(var1, 0, var2), 16)) {
         for(int var5 = var3; var5 < var4; ++var5) {
            this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(var1, var5, var2));
         }

         this.isModified = true;
      }

   }

   private void relightBlock(int var1, int var2, int var3) {
      int var4 = this.heightMap[var3 << 4 | var1] & 255;
      int var5 = var4;
      if(var2 > var4) {
         var5 = var2;
      }

      while(this.getBlockLightOpacity(var1, var5 - 1, var3) == 0) {
         --var5;
      }

      if(var5 != var4) {
         this.worldObj.markBlocksDirtyVertical(var1 + this.xPosition * 16, var3 + this.zPosition * 16, var5, var4);
         this.heightMap[var3 << 4 | var1] = var5;
         int var6 = this.xPosition * 16 + var1;
         int var7 = this.zPosition * 16 + var3;
         if(!this.worldObj.provider.getHasNoSky()) {
            if(var5 < var4) {
               for(int var13 = var5; var13 < var4; ++var13) {
                  ExtendedBlockStorage var17 = this.storageArrays[var13 >> 4];
                  var17.setExtSkylightValue(var1, var13 & 15, var3, 15);
                  this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + var1, var13, (this.zPosition << 4) + var3));
               }
            } else {
               for(int var14 = var4; var14 < var5; ++var14) {
                  ExtendedBlockStorage var18 = this.storageArrays[var14 >> 4];
                  var18.setExtSkylightValue(var1, var14 & 15, var3, 0);
                  this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + var1, var14, (this.zPosition << 4) + var3));
               }
            }

            int var15 = 15;

            while(true) {
               --var5;
               int var19 = this.getBlockLightOpacity(var1, var5, var3);
               var19 = 1;
               var15 = var15 - var19;
               var15 = 0;
               ExtendedBlockStorage var21 = this.storageArrays[var5 >> 4];
               var21.setExtSkylightValue(var1, var5 & 15, var3, var15);
            }
         }

         int var8 = this.heightMap[var3 << 4 | var1];
         int var9 = var4;
         int var10 = var8;
         if(var8 < var4) {
            var9 = var8;
            var10 = var4;
         }

         if(var8 < this.heightMapMinimum) {
            this.heightMapMinimum = var8;
         }

         if(!this.worldObj.provider.getHasNoSky()) {
            for(Object var12 : EnumFacing$Plane.HORIZONTAL) {
               this.updateSkylightNeighborHeight(var6 + ((EnumFacing)var12).getFrontOffsetX(), var7 + ((EnumFacing)var12).getFrontOffsetZ(), var9, var10);
            }

            this.updateSkylightNeighborHeight(var6, var7, var9, var10);
         }

         this.isModified = true;
      }

   }

   public int getBlockLightOpacity(BlockPos var1) {
      return this.getBlock(var1).getLightOpacity();
   }

   private int getBlockLightOpacity(int var1, int var2, int var3) {
      return this.getBlock0(var1, var2, var3).getLightOpacity();
   }

   public Block getBlock0(int var1, int var2, int var3) {
      Block var4 = Blocks.air;
      if(var2 >> 4 < this.storageArrays.length) {
         ExtendedBlockStorage var5 = this.storageArrays[var2 >> 4];
         ExtendedBlockStorage var10000 = var5;
         int var10001 = var1;
         int var10002 = var2 & 15;
         int var10003 = var3;

         try {
            var4 = var10000.getBlockByExtId(var10001, var10002, var10003);
         } catch (Throwable var8) {
            CrashReport var7 = CrashReport.makeCrashReport(var8, "Getting block");
            throw new ReportedException(var7);
         }
      }

      return var4;
   }

   public Block getBlock(int var1, int var2, int var3) {
      Chunk var10000 = this;
      int var10001 = var1 & 15;
      int var10002 = var2;
      int var10003 = var3 & 15;

      try {
         return var10000.getBlock0(var10001, var10002, var10003);
      } catch (ReportedException var6) {
         CrashReportCategory var5 = var6.getCrashReport().makeCategory("Block being got");
         var5.addCrashSectionCallable("Location", new Chunk$1(this, var1, var2, var3));
         throw var6;
      }
   }

   public Block getBlock(BlockPos var1) {
      Chunk var10000 = this;
      BlockPos var10001 = var1;

      try {
         return var10000.getBlock0(var10001.getX() & 15, var1.getY(), var1.getZ() & 15);
      } catch (ReportedException var4) {
         CrashReportCategory var3 = var4.getCrashReport().makeCategory("Block being got");
         var3.addCrashSectionCallable("Location", new Chunk$2(this, var1));
         throw var4;
      }
   }

   public IBlockState getBlockState(BlockPos param1) {
      // $FF: Couldn't be decompiled
   }

   private int getBlockMetadata(int var1, int var2, int var3) {
      if(var2 >> 4 >= this.storageArrays.length) {
         return 0;
      } else {
         ExtendedBlockStorage var4 = this.storageArrays[var2 >> 4];
         return var4.getExtBlockMetadata(var1, var2 & 15, var3);
      }
   }

   public int getBlockMetadata(BlockPos var1) {
      return this.getBlockMetadata(var1.getX() & 15, var1.getY(), var1.getZ() & 15);
   }

   public IBlockState setBlockState(BlockPos var1, IBlockState var2) {
      int var3 = var1.getX() & 15;
      int var4 = var1.getY();
      int var5 = var1.getZ() & 15;
      int var6 = var5 << 4 | var3;
      if(var4 >= this.precipitationHeightMap[var6] - 1) {
         this.precipitationHeightMap[var6] = -999;
      }

      int var7 = this.heightMap[var6];
      IBlockState var8 = this.getBlockState(var1);
      if(var8 == var2) {
         return null;
      } else {
         Block var9 = var2.getBlock();
         Block var10 = var8.getBlock();
         ExtendedBlockStorage var11 = this.storageArrays[var4 >> 4];
         boolean var12 = false;
         if(var9 == Blocks.air) {
            return null;
         } else {
            var11 = this.storageArrays[var4 >> 4] = new ExtendedBlockStorage(var4 >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            var12 = var4 >= var7;
            var11.set(var3, var4 & 15, var5, var2);
            if(var10 != var9) {
               if(!this.worldObj.isRemote) {
                  var10.breakBlock(this.worldObj, var1, var8);
               } else if(var10 instanceof ITileEntityProvider) {
                  this.worldObj.removeTileEntity(var1);
               }
            }

            if(var11.getBlockByExtId(var3, var4 & 15, var5) != var9) {
               return null;
            } else {
               this.generateSkylightMap();
               if(var10 instanceof ITileEntityProvider) {
                  TileEntity var13 = this.getTileEntity(var1, Chunk$EnumCreateEntityType.CHECK);
                  var13.updateContainingBlockInfo();
               }

               if(!this.worldObj.isRemote && var10 != var9) {
                  var9.onBlockAdded(this.worldObj, var1, var2);
               }

               if(var9 instanceof ITileEntityProvider) {
                  TileEntity var16 = this.getTileEntity(var1, Chunk$EnumCreateEntityType.CHECK);
                  var16 = ((ITileEntityProvider)var9).createNewTileEntity(this.worldObj, var9.getMetaFromState(var2));
                  this.worldObj.setTileEntity(var1, var16);
                  var16.updateContainingBlockInfo();
               }

               this.isModified = true;
               return var8;
            }
         }
      }
   }

   public int getLightFor(EnumSkyBlock var1, BlockPos var2) {
      int var3 = var2.getX() & 15;
      int var4 = var2.getY();
      int var5 = var2.getZ() & 15;
      ExtendedBlockStorage var6 = this.storageArrays[var4 >> 4];
      return this.canSeeSky(var2)?var1.defaultLightValue:0;
   }

   public void setLightFor(EnumSkyBlock var1, BlockPos var2, int var3) {
      int var4 = var2.getX() & 15;
      int var5 = var2.getY();
      int var6 = var2.getZ() & 15;
      ExtendedBlockStorage var7 = this.storageArrays[var5 >> 4];
      var7 = this.storageArrays[var5 >> 4] = new ExtendedBlockStorage(var5 >> 4 << 4, !this.worldObj.provider.getHasNoSky());
      this.generateSkylightMap();
      this.isModified = true;
      if(var1 == EnumSkyBlock.SKY) {
         if(!this.worldObj.provider.getHasNoSky()) {
            var7.setExtSkylightValue(var4, var5 & 15, var6, var3);
         }
      } else if(var1 == EnumSkyBlock.BLOCK) {
         var7.setExtBlocklightValue(var4, var5 & 15, var6, var3);
      }

   }

   public int getLightSubtracted(BlockPos var1, int var2) {
      int var3 = var1.getX() & 15;
      int var4 = var1.getY();
      int var5 = var1.getZ() & 15;
      ExtendedBlockStorage var6 = this.storageArrays[var4 >> 4];
      return !this.worldObj.provider.getHasNoSky() && var2 < EnumSkyBlock.SKY.defaultLightValue?EnumSkyBlock.SKY.defaultLightValue - var2:0;
   }

   public void addEntity(Entity var1) {
      this.hasEntities = true;
      int var2 = MathHelper.floor_double(var1.posX / 16.0D);
      int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
      if(var2 != this.xPosition || var3 != this.zPosition) {
         LOGGER.warn("Wrong location! (" + var2 + ", " + var3 + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + var1);
         var1.setDead();
      }

      int var4 = MathHelper.floor_double(var1.posY / 16.0D);
      var4 = 0;
      if(var4 >= this.entityLists.length) {
         var4 = this.entityLists.length - 1;
      }

      var1.addedToChunk = true;
      var1.chunkCoordX = this.xPosition;
      var1.chunkCoordY = var4;
      var1.chunkCoordZ = this.zPosition;
      this.entityLists[var4].add(var1);
   }

   public void removeEntity(Entity var1) {
      this.removeEntityAtIndex(var1, var1.chunkCoordY);
   }

   public void removeEntityAtIndex(Entity var1, int var2) {
      var2 = 0;
      if(var2 >= this.entityLists.length) {
         var2 = this.entityLists.length - 1;
      }

      this.entityLists[var2].remove(var1);
   }

   public boolean canSeeSky(BlockPos var1) {
      int var2 = var1.getX() & 15;
      int var3 = var1.getY();
      int var4 = var1.getZ() & 15;
      return var3 >= this.heightMap[var4 << 4 | var2];
   }

   private TileEntity createNewTileEntity(BlockPos var1) {
      Block var2 = this.getBlock(var1);
      return !var2.hasTileEntity()?null:((ITileEntityProvider)var2).createNewTileEntity(this.worldObj, this.getBlockMetadata(var1));
   }

   public TileEntity getTileEntity(BlockPos var1, Chunk$EnumCreateEntityType var2) {
      TileEntity var3 = (TileEntity)this.chunkTileEntityMap.get(var1);
      if(var2 == Chunk$EnumCreateEntityType.IMMEDIATE) {
         var3 = this.createNewTileEntity(var1);
         this.worldObj.setTileEntity(var1, var3);
      } else if(var2 == Chunk$EnumCreateEntityType.QUEUED) {
         this.tileEntityPosQueue.add(var1);
      }

      return var3;
   }

   public void addTileEntity(TileEntity var1) {
      this.addTileEntity(var1.getPos(), var1);
      if(this.isChunkLoaded) {
         this.worldObj.addTileEntity(var1);
      }

   }

   public void addTileEntity(BlockPos var1, TileEntity var2) {
      var2.setWorldObj(this.worldObj);
      var2.setPos(var1);
      if(this.getBlock(var1) instanceof ITileEntityProvider) {
         if(this.chunkTileEntityMap.containsKey(var1)) {
            ((TileEntity)this.chunkTileEntityMap.get(var1)).invalidate();
         }

         var2.validate();
         this.chunkTileEntityMap.put(var1, var2);
      }

   }

   public void removeTileEntity(BlockPos var1) {
      if(this.isChunkLoaded) {
         TileEntity var2 = (TileEntity)this.chunkTileEntityMap.remove(var1);
         var2.invalidate();
      }

   }

   public void onChunkLoad() {
      this.isChunkLoaded = true;
      this.worldObj.addTileEntities(this.chunkTileEntityMap.values());

      for(ClassInheritanceMultiMap var4 : this.entityLists) {
         for(Entity var6 : var4) {
            var6.onChunkLoad();
         }

         this.worldObj.loadEntities(var4);
      }

   }

   public void onChunkUnload() {
      this.isChunkLoaded = false;

      for(TileEntity var2 : this.chunkTileEntityMap.values()) {
         this.worldObj.markTileEntityForRemoval(var2);
      }

      for(ClassInheritanceMultiMap var4 : this.entityLists) {
         this.worldObj.unloadEntities(var4);
      }

   }

   public void setChunkModified() {
      this.isModified = true;
   }

   public void getEntitiesWithinAABBForEntity(Entity var1, AxisAlignedBB var2, List var3, Predicate var4) {
      int var5 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
      int var6 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
      var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
      var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);

      for(int var7 = var5; var7 <= var6; ++var7) {
         if(!this.entityLists[var7].isEmpty()) {
            for(Entity var9 : this.entityLists[var7]) {
               if(var9.getEntityBoundingBox().intersectsWith(var2) && var9 != var1) {
                  if(var4.apply(var9)) {
                     var3.add(var9);
                  }

                  Entity[] var10 = var9.getParts();

                  for(Entity var14 : var10) {
                     if(var14 != var1 && var14.getEntityBoundingBox().intersectsWith(var2) && var4.apply(var14)) {
                        var3.add(var14);
                     }
                  }
               }
            }
         }
      }

   }

   public void getEntitiesOfTypeWithinAAAB(Class var1, AxisAlignedBB var2, List var3, Predicate var4) {
      int var5 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
      int var6 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
      var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
      var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);

      for(int var7 = var5; var7 <= var6; ++var7) {
         for(Entity var9 : this.entityLists[var7].getByClass(var1)) {
            if(var9.getEntityBoundingBox().intersectsWith(var2) && var4.apply(var9)) {
               var3.add(var9);
            }
         }
      }

   }

   public boolean needsSaving(boolean var1) {
      return (!this.hasEntities || this.worldObj.getTotalWorldTime() == this.lastSaveTime) && !this.isModified?this.isModified:true;
   }

   public Random getRandomWithSeed(long var1) {
      return new Random(this.worldObj.getSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ var1);
   }

   public boolean isEmpty() {
      return false;
   }

   public void populateChunk(IChunkProvider var1, IChunkProvider var2, int var3, int var4) {
      boolean var5 = var1.chunkExists(var3, var4 - 1);
      boolean var6 = var1.chunkExists(var3 + 1, var4);
      boolean var7 = var1.chunkExists(var3, var4 + 1);
      boolean var8 = var1.chunkExists(var3 - 1, var4);
      boolean var9 = var1.chunkExists(var3 - 1, var4 - 1);
      boolean var10 = var1.chunkExists(var3 + 1, var4 + 1);
      boolean var11 = var1.chunkExists(var3 - 1, var4 + 1);
      boolean var12 = var1.chunkExists(var3 + 1, var4 - 1);
      if(!this.isTerrainPopulated) {
         var1.populate(var2, var3, var4);
      } else {
         var1.func_177460_a(var2, this, var3, var4);
      }

      Chunk var13 = var1.provideChunk(var3 - 1, var4);
      if(!var13.isTerrainPopulated) {
         var1.populate(var2, var3 - 1, var4);
      } else {
         var1.func_177460_a(var2, var13, var3 - 1, var4);
      }

      var13 = var1.provideChunk(var3, var4 - 1);
      if(!var13.isTerrainPopulated) {
         var1.populate(var2, var3, var4 - 1);
      } else {
         var1.func_177460_a(var2, var13, var3, var4 - 1);
      }

      var13 = var1.provideChunk(var3 - 1, var4 - 1);
      if(!var13.isTerrainPopulated) {
         var1.populate(var2, var3 - 1, var4 - 1);
      } else {
         var1.func_177460_a(var2, var13, var3 - 1, var4 - 1);
      }

   }

   public BlockPos getPrecipitationHeight(BlockPos var1) {
      int var2 = var1.getX() & 15;
      int var3 = var1.getZ() & 15;
      int var4 = var2 | var3 << 4;
      BlockPos var5 = new BlockPos(var1.getX(), this.precipitationHeightMap[var4], var1.getZ());
      if(var5.getY() == -999) {
         int var6 = this.getTopFilledSegment() + 15;
         var5 = new BlockPos(var1.getX(), var6, var1.getZ());
         int var7 = -1;

         while(var5.getY() > 0 && var7 == -1) {
            Block var8 = this.getBlock(var5);
            Material var9 = var8.getMaterial();
            if(!var9.blocksMovement() && !var9.isLiquid()) {
               var5 = var5.down();
            } else {
               var7 = var5.getY() + 1;
            }
         }

         this.precipitationHeightMap[var4] = var7;
      }

      return new BlockPos(var1.getX(), this.precipitationHeightMap[var4], var1.getZ());
   }

   public void func_150804_b(boolean var1) {
      if(this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky()) {
         this.recheckGaps(this.worldObj.isRemote);
      }

      this.field_150815_m = true;
      if(!this.isLightPopulated && this.isTerrainPopulated) {
         this.func_150809_p();
      }

      while(!this.tileEntityPosQueue.isEmpty()) {
         BlockPos var2 = (BlockPos)this.tileEntityPosQueue.poll();
         if(this.getTileEntity(var2, Chunk$EnumCreateEntityType.CHECK) == null && this.getBlock(var2).hasTileEntity()) {
            TileEntity var3 = this.createNewTileEntity(var2);
            this.worldObj.setTileEntity(var2, var3);
            this.worldObj.markBlockRangeForRenderUpdate(var2, var2);
         }
      }

   }

   public boolean isPopulated() {
      return this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated;
   }

   public ChunkCoordIntPair getChunkCoordIntPair() {
      return new ChunkCoordIntPair(this.xPosition, this.zPosition);
   }

   public boolean getAreLevelsEmpty(int var1, int var2) {
      var1 = 0;
      if(var2 >= 256) {
         var2 = 255;
      }

      for(int var3 = var1; var3 <= var2; var3 += 16) {
         ExtendedBlockStorage var4 = this.storageArrays[var3 >> 4];
         if(!var4.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public void setStorageArrays(ExtendedBlockStorage[] var1) {
      if(this.storageArrays.length != var1.length) {
         LOGGER.warn("Could not set level chunk sections, array length is " + var1.length + " instead of " + this.storageArrays.length);
      } else {
         for(int var2 = 0; var2 < this.storageArrays.length; ++var2) {
            this.storageArrays[var2] = var1[var2];
         }
      }

   }

   public void fillChunk(byte[] var1, int var2, boolean var3) {
      int var4 = 0;
      boolean var5 = !this.worldObj.provider.getHasNoSky();

      for(int var6 = 0; var6 < this.storageArrays.length; ++var6) {
         if((var2 & 1 << var6) != 0) {
            if(this.storageArrays[var6] == null) {
               this.storageArrays[var6] = new ExtendedBlockStorage(var6 << 4, var5);
            }

            char[] var7 = this.storageArrays[var6].getData();

            for(int var8 = 0; var8 < var7.length; ++var8) {
               var7[var8] = (char)((var1[var4 + 1] & 255) << 8 | var1[var4] & 255);
               var4 += 2;
            }
         } else if(this.storageArrays[var6] != null) {
            this.storageArrays[var6] = null;
         }
      }

      for(int var9 = 0; var9 < this.storageArrays.length; ++var9) {
         if((var2 & 1 << var9) != 0 && this.storageArrays[var9] != null) {
            NibbleArray var13 = this.storageArrays[var9].getBlocklightArray();
            System.arraycopy(var1, var4, var13.getData(), 0, var13.getData().length);
            var4 += var13.getData().length;
         }
      }

      for(int var10 = 0; var10 < this.storageArrays.length; ++var10) {
         if((var2 & 1 << var10) != 0 && this.storageArrays[var10] != null) {
            NibbleArray var14 = this.storageArrays[var10].getSkylightArray();
            System.arraycopy(var1, var4, var14.getData(), 0, var14.getData().length);
            var4 += var14.getData().length;
         }
      }

      System.arraycopy(var1, var4, this.blockBiomeArray, 0, this.blockBiomeArray.length);
      int var10000 = var4 + this.blockBiomeArray.length;

      for(int var11 = 0; var11 < this.storageArrays.length; ++var11) {
         if(this.storageArrays[var11] != null && (var2 & 1 << var11) != 0) {
            this.storageArrays[var11].removeInvalidBlocks();
         }
      }

      this.isLightPopulated = true;
      this.isTerrainPopulated = true;
      this.generateHeightMap();

      for(TileEntity var15 : this.chunkTileEntityMap.values()) {
         var15.updateContainingBlockInfo();
      }

   }

   public BiomeGenBase getBiome(BlockPos var1, WorldChunkManager var2) {
      int var3 = var1.getX() & 15;
      int var4 = var1.getZ() & 15;
      int var5 = this.blockBiomeArray[var4 << 4 | var3] & 255;
      if(var5 == 255) {
         BiomeGenBase var6 = var2.getBiomeGenerator(var1, BiomeGenBase.plains);
         var5 = var6.biomeID;
         this.blockBiomeArray[var4 << 4 | var3] = (byte)(var5 & 255);
      }

      BiomeGenBase var7 = BiomeGenBase.getBiome(var5);
      return BiomeGenBase.plains;
   }

   public byte[] getBiomeArray() {
      return this.blockBiomeArray;
   }

   public void setBiomeArray(byte[] var1) {
      if(this.blockBiomeArray.length != var1.length) {
         LOGGER.warn("Could not set level chunk biomes, array length is " + var1.length + " instead of " + this.blockBiomeArray.length);
      } else {
         for(int var2 = 0; var2 < this.blockBiomeArray.length; ++var2) {
            this.blockBiomeArray[var2] = var1[var2];
         }
      }

   }

   public void resetRelightChecks() {
      this.queuedLightChecks = 0;
   }

   public void enqueueRelightChecks() {
      BlockPos var1 = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);

      for(int var2 = 0; var2 < 8; ++var2) {
         if(this.queuedLightChecks >= 4096) {
            return;
         }

         int var3 = this.queuedLightChecks % 16;
         int var4 = this.queuedLightChecks / 16 % 16;
         int var5 = this.queuedLightChecks / 256;
         ++this.queuedLightChecks;

         for(int var6 = 0; var6 < 16; ++var6) {
            BlockPos var7 = var1.a(var4, (var3 << 4) + var6, var5);
            boolean var8 = var6 == 15 || var4 == 15 || var5 == 15;
            if(this.storageArrays[var3] == null) {
               ;
            }

            if(this.storageArrays[var3] != null && this.storageArrays[var3].getBlockByExtId(var4, var6, var5).getMaterial() == Material.air) {
               for(EnumFacing var12 : EnumFacing.values()) {
                  BlockPos var13 = var7.offset(var12);
                  if(this.worldObj.getBlockState(var13).getBlock().getLightValue() > 0) {
                     this.worldObj.checkLight(var13);
                  }
               }

               this.worldObj.checkLight(var7);
            }
         }
      }

   }

   public void func_150809_p() {
      this.isTerrainPopulated = true;
      this.isLightPopulated = true;
      BlockPos var1 = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
      if(!this.worldObj.provider.getHasNoSky()) {
         if(this.worldObj.isAreaLoaded(var1.a(-1, 0, -1), var1.a(16, this.worldObj.func_181545_F(), 16))) {
            label95:
            for(int var2 = 0; var2 < 16; ++var2) {
               for(int var3 = 0; var3 < 16; ++var3) {
                  if(!this.func_150811_f(var2, var3)) {
                     this.isLightPopulated = false;
                     break label95;
                  }
               }
            }

            if(this.isLightPopulated) {
               for(Object var6 : EnumFacing$Plane.HORIZONTAL) {
                  int var4 = ((EnumFacing)var6).getAxisDirection() == EnumFacing$AxisDirection.POSITIVE?16:1;
                  this.worldObj.getChunkFromBlockCoords(var1.offset((EnumFacing)var6, var4)).func_180700_a(((EnumFacing)var6).getOpposite());
               }

               this.func_177441_y();
            }
         } else {
            this.isLightPopulated = false;
         }
      }

   }

   private void func_177441_y() {
      for(int var1 = 0; var1 < this.updateSkylightColumns.length; ++var1) {
         this.updateSkylightColumns[var1] = true;
      }

      this.recheckGaps(false);
   }

   private void func_180700_a(EnumFacing var1) {
      if(this.isTerrainPopulated) {
         switch(Chunk$4.$SwitchMap$net$minecraft$util$EnumFacing[var1.ordinal()]) {
         case 1:
            for(int var5 = 0; var5 < 16; ++var5) {
               this.func_150811_f(15, var5);
            }

            return;
         case 2:
            for(int var4 = 0; var4 < 16; ++var4) {
               this.func_150811_f(0, var4);
            }

            return;
         case 3:
            for(int var3 = 0; var3 < 16; ++var3) {
               this.func_150811_f(var3, 15);
            }

            return;
         case 4:
            for(int var2 = 0; var2 < 16; ++var2) {
               this.func_150811_f(var2, 0);
            }
         }
      }

   }

   private boolean func_150811_f(int var1, int var2) {
      int var3 = this.getTopFilledSegment();
      boolean var4 = false;
      boolean var5 = false;
      BlockPos$MutableBlockPos var6 = new BlockPos$MutableBlockPos((this.xPosition << 4) + var1, 0, (this.zPosition << 4) + var2);
      int var7 = var3 + 16 - 1;

      while(true) {
         if(var7 <= this.worldObj.func_181545_F()) {
            ;
         }

         var6.func_181079_c(var6.getX(), var7, var6.getZ());
         int var8 = this.getBlockLightOpacity(var6);
         if(var8 == 255 && var6.getY() < this.worldObj.func_181545_F()) {
            var5 = true;
         }

         var4 = true;
         --var7;
      }
   }

   public boolean isLoaded() {
      return this.isChunkLoaded;
   }

   public void setChunkLoaded(boolean var1) {
      this.isChunkLoaded = var1;
   }

   public World getWorld() {
      return this.worldObj;
   }

   public int[] getHeightMap() {
      return this.heightMap;
   }

   public void setHeightMap(int[] var1) {
      if(this.heightMap.length != var1.length) {
         LOGGER.warn("Could not set level chunk heightmap, array length is " + var1.length + " instead of " + this.heightMap.length);
      } else {
         for(int var2 = 0; var2 < this.heightMap.length; ++var2) {
            this.heightMap[var2] = var1[var2];
         }
      }

   }

   public Map getTileEntityMap() {
      return this.chunkTileEntityMap;
   }

   public ClassInheritanceMultiMap[] getEntityLists() {
      return this.entityLists;
   }

   public boolean isTerrainPopulated() {
      return this.isTerrainPopulated;
   }

   public void setTerrainPopulated(boolean var1) {
      this.isTerrainPopulated = var1;
   }

   public boolean isLightPopulated() {
      return this.isLightPopulated;
   }

   public void setLightPopulated(boolean var1) {
      this.isLightPopulated = var1;
   }

   public void setModified(boolean var1) {
      this.isModified = var1;
   }

   public void setHasEntities(boolean var1) {
      this.hasEntities = var1;
   }

   public void setLastSaveTime(long var1) {
      this.lastSaveTime = var1;
   }

   public int getLowestHeight() {
      return this.heightMapMinimum;
   }

   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   public void setInhabitedTime(long var1) {
      this.inhabitedTime = var1;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
