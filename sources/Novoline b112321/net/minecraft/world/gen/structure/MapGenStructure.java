package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.storage.MapStorage;
import net.optifine.Reflector;

public abstract class MapGenStructure extends MapGenBase {
   private MapGenStructureData structureData;
   protected Map structureMap = Maps.newHashMap();
   private LongHashMap structureLongMap = new LongHashMap();

   public abstract String getStructureName();

   protected final void recursiveGenerate(World var1, int var2, int var3, int var4, int var5, ChunkPrimer var6) {
      this.func_143027_a(var1);
      if(!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(var2, var3))) {
         this.rand.nextInt();
         MapGenStructure var10000 = this;
         int var10001 = var2;
         int var10002 = var3;

         try {
            if(var10000.canSpawnStructureAtCoords(var10001, var10002)) {
               StructureStart var7 = this.getStructureStart(var2, var3);
               this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(var2, var3)), var7);
               this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(var2, var3), var7);
               this.func_143026_a(var2, var3, var7);
            }
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
            CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
            var9.addCrashSectionCallable("Is feature chunk", this::lambda$recursiveGenerate$0);
            var9.addCrashSection("Chunk location", String.format("%d,%d", new Object[]{Integer.valueOf(var2), Integer.valueOf(var3)}));
            var9.addCrashSectionCallable("Chunk pos hash", MapGenStructure::lambda$recursiveGenerate$1);
            var9.addCrashSectionCallable("Structure type", this::lambda$recursiveGenerate$2);
            throw new ReportedException(var8);
         }
      }

   }

   public boolean generateStructure(World var1, Random var2, ChunkCoordIntPair var3) {
      this.func_143027_a(var1);
      int var4 = (var3.chunkXPos << 4) + 8;
      int var5 = (var3.chunkZPos << 4) + 8;
      boolean var6 = false;

      for(Object var8 : this.structureMap.values()) {
         StructureStart var9 = (StructureStart)var8;
         if(var9.isSizeableStructure() && var9.func_175788_a(var3) && var9.getBoundingBox().intersectsWith(var4, var5, var4 + 15, var5 + 15)) {
            var9.generateStructure(var1, var2, new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
            var9.func_175787_b(var3);
            var6 = true;
            this.func_143026_a(var9.getChunkPosX(), var9.getChunkPosZ(), var9);
         }
      }

      return var6;
   }

   public boolean func_175795_b(BlockPos var1) {
      this.func_143027_a(this.worldObj);
      return this.func_175797_c(var1) != null;
   }

   protected StructureStart func_175797_c(BlockPos var1) {
      label24:
      for(Object var3 : this.structureMap.values()) {
         StructureStart var4 = (StructureStart)var3;
         if(var4.isSizeableStructure() && var4.getBoundingBox().isVecInside(var1)) {
            Iterator var5 = var4.getComponents().iterator();

            while(true) {
               if(!var5.hasNext()) {
                  continue label24;
               }

               StructureComponent var6 = (StructureComponent)var5.next();
               if(var6.getBoundingBox().isVecInside(var1)) {
                  break;
               }
            }

            return var4;
         }
      }

      return null;
   }

   public boolean func_175796_a(World var1, BlockPos var2) {
      this.func_143027_a(var1);

      for(Object var4 : this.structureMap.values()) {
         StructureStart var5 = (StructureStart)var4;
         if(var5.isSizeableStructure() && var5.getBoundingBox().isVecInside(var2)) {
            return true;
         }
      }

      return false;
   }

   public BlockPos getClosestStrongholdPos(World var1, BlockPos var2) {
      this.worldObj = var1;
      this.func_143027_a(var1);
      this.rand.setSeed(var1.getSeed());
      long var3 = this.rand.nextLong();
      long var5 = this.rand.nextLong();
      long var7 = (long)(var2.getX() >> 4) * var3;
      long var9 = (long)(var2.getZ() >> 4) * var5;
      this.rand.setSeed(var7 ^ var9 ^ var1.getSeed());
      this.recursiveGenerate(var1, var2.getX() >> 4, var2.getZ() >> 4, 0, 0, (ChunkPrimer)null);
      double var11 = Double.MAX_VALUE;
      BlockPos var13 = null;

      for(Object var15 : this.structureMap.values()) {
         StructureStart var16 = (StructureStart)var15;
         if(var16.isSizeableStructure()) {
            StructureComponent var17 = (StructureComponent)var16.getComponents().get(0);
            BlockPos var18 = var17.getBoundingBoxCenter();
            double var19 = var18.distanceSq(var2);
            if(var19 < var11) {
               var11 = var19;
               var13 = var18;
            }
         }
      }

      return var13;
   }

   protected List getCoordList() {
      return null;
   }

   private void func_143027_a(World var1) {
      if(this.structureData == null) {
         if(Reflector.aB.d()) {
            MapStorage var2 = (MapStorage)Reflector.f(var1, Reflector.aB, new Object[0]);
            this.structureData = (MapGenStructureData)var2.loadData(MapGenStructureData.class, this.getStructureName());
         } else {
            this.structureData = (MapGenStructureData)var1.loadItemData(MapGenStructureData.class, this.getStructureName());
         }

         if(this.structureData == null) {
            this.structureData = new MapGenStructureData(this.getStructureName());
            if(Reflector.aB.d()) {
               MapStorage var10 = (MapStorage)Reflector.f(var1, Reflector.aB, new Object[0]);
               var10.setData(this.getStructureName(), this.structureData);
            } else {
               var1.setItemData(this.getStructureName(), this.structureData);
            }
         } else {
            NBTTagCompound var11 = this.structureData.getTagCompound();

            for(String var4 : var11.getKeySet()) {
               NBTBase var5 = var11.getTag(var4);
               if(var5.getId() == 10) {
                  NBTTagCompound var6 = (NBTTagCompound)var5;
                  if(var6.hasKey("ChunkX") && var6.hasKey("ChunkZ")) {
                     int var7 = var6.getInteger("ChunkX");
                     int var8 = var6.getInteger("ChunkZ");
                     StructureStart var9 = MapGenStructureIO.getStructureStart(var6, var1);
                     this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(var7, var8)), var9);
                     this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(var7, var8), var9);
                  }
               }
            }
         }
      }

   }

   private void func_143026_a(int var1, int var2, StructureStart var3) {
      this.structureData.writeInstance(var3.writeStructureComponentsToNBT(var1, var2), var1, var2);
      this.structureData.markDirty();
   }

   protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);

   protected abstract StructureStart getStructureStart(int var1, int var2);

   private String lambda$recursiveGenerate$2() throws Exception {
      return this.getClass().getCanonicalName();
   }

   private static String lambda$recursiveGenerate$1(int var0, int var1) throws Exception {
      return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(var0, var1));
   }

   private String lambda$recursiveGenerate$0(int var1, int var2) throws Exception {
      return this.canSpawnStructureAtCoords(var1, var2)?"True":"False";
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
