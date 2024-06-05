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
import net.minecraft.world.storage.MapStorage;
import optifine.Reflector;

public abstract class MapGenStructure extends MapGenBase {
   private MapGenStructureData field_143029_e;
   protected Map structureMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00000505";
   private LongHashMap structureLongMap = new LongHashMap();

   protected StructureStart func_175797_c(BlockPos var1) {
      Iterator var2 = this.structureMap.values().iterator();

      while(true) {
         StructureStart var3;
         do {
            do {
               if (!var2.hasNext()) {
                  return null;
               }

               var3 = (StructureStart)var2.next();
            } while(!var3.isSizeableStructure());
         } while(!var3.getBoundingBox().func_175898_b(var1));

         Iterator var4 = var3.getComponents().iterator();

         while(var4.hasNext()) {
            StructureComponent var5 = (StructureComponent)var4.next();
            if (var5.getBoundingBox().func_175898_b(var1)) {
               return var3;
            }
         }
      }
   }

   public boolean func_175795_b(BlockPos var1) {
      this.func_143027_a(this.worldObj);
      return this.func_175797_c(var1) != null;
   }

   protected List getCoordList() {
      return null;
   }

   private void func_143027_a(World var1) {
      if (this.field_143029_e == null) {
         MapStorage var2;
         if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
            var2 = (MapStorage)Reflector.call(var1, Reflector.ForgeWorld_getPerWorldStorage);
            this.field_143029_e = (MapGenStructureData)var2.loadData(MapGenStructureData.class, this.getStructureName());
         } else {
            this.field_143029_e = (MapGenStructureData)var1.loadItemData(MapGenStructureData.class, this.getStructureName());
         }

         if (this.field_143029_e == null) {
            this.field_143029_e = new MapGenStructureData(this.getStructureName());
            if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
               var2 = (MapStorage)Reflector.call(var1, Reflector.ForgeWorld_getPerWorldStorage);
               var2.setData(this.getStructureName(), this.field_143029_e);
            } else {
               var1.setItemData(this.getStructureName(), this.field_143029_e);
            }
         } else {
            NBTTagCompound var3 = this.field_143029_e.func_143041_a();
            Iterator var4 = var3.getKeySet().iterator();

            while(var4.hasNext()) {
               String var5 = (String)var4.next();
               NBTBase var6 = var3.getTag(var5);
               if (var6.getId() == 10) {
                  NBTTagCompound var7 = (NBTTagCompound)var6;
                  if (var7.hasKey("ChunkX") && var7.hasKey("ChunkZ")) {
                     int var8 = var7.getInteger("ChunkX");
                     int var9 = var7.getInteger("ChunkZ");
                     StructureStart var10 = MapGenStructureIO.func_143035_a(var7, var1);
                     if (var10 != null) {
                        this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var8, var9), var10);
                        this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(var8, var9), var10);
                     }
                  }
               }
            }
         }
      }

   }

   protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);

   protected abstract StructureStart getStructureStart(int var1, int var2);

   private void func_143026_a(int var1, int var2, StructureStart var3) {
      this.field_143029_e.func_143043_a(var3.func_143021_a(var1, var2), var1, var2);
      this.field_143029_e.markDirty();
   }

   public boolean func_175794_a(World var1, Random var2, ChunkCoordIntPair var3) {
      this.func_143027_a(var1);
      int var4 = (var3.chunkXPos << 4) + 8;
      int var5 = (var3.chunkZPos << 4) + 8;
      boolean var6 = false;
      Iterator var7 = this.structureMap.values().iterator();

      while(var7.hasNext()) {
         StructureStart var8 = (StructureStart)var7.next();
         if (var8.isSizeableStructure() && var8.func_175788_a(var3) && var8.getBoundingBox().intersectsWith(var4, var5, var4 + 15, var5 + 15)) {
            var8.generateStructure(var1, var2, new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
            var8.func_175787_b(var3);
            var6 = true;
            this.func_143026_a(var8.func_143019_e(), var8.func_143018_f(), var8);
         }
      }

      return var6;
   }

   public BlockPos func_180706_b(World var1, BlockPos var2) {
      this.worldObj = var1;
      this.func_143027_a(var1);
      this.rand.setSeed(var1.getSeed());
      long var3 = this.rand.nextLong();
      long var5 = this.rand.nextLong();
      long var7 = (long)(var2.getX() >> 4) * var3;
      long var9 = (long)(var2.getZ() >> 4) * var5;
      this.rand.setSeed(var7 ^ var9 ^ var1.getSeed());
      this.func_180701_a(var1, var2.getX() >> 4, var2.getZ() >> 4, 0, 0, (ChunkPrimer)null);
      double var11 = Double.MAX_VALUE;
      BlockPos var13 = null;
      Iterator var14 = this.structureMap.values().iterator();

      BlockPos var15;
      double var16;
      while(var14.hasNext()) {
         StructureStart var18 = (StructureStart)var14.next();
         if (var18.isSizeableStructure()) {
            StructureComponent var19 = (StructureComponent)var18.getComponents().get(0);
            var15 = var19.func_180776_a();
            var16 = var15.distanceSq(var2);
            if (var16 < var11) {
               var11 = var16;
               var13 = var15;
            }
         }
      }

      if (var13 != null) {
         return var13;
      } else {
         List var21 = this.getCoordList();
         if (var21 != null) {
            BlockPos var22 = null;
            Iterator var20 = var21.iterator();

            while(var20.hasNext()) {
               var15 = (BlockPos)var20.next();
               var16 = var15.distanceSq(var2);
               if (var16 < var11) {
                  var11 = var16;
                  var22 = var15;
               }
            }

            return var22;
         } else {
            return null;
         }
      }
   }

   public abstract String getStructureName();

   protected final void func_180701_a(World var1, int var2, int var3, int var4, int var5, ChunkPrimer var6) {
      this.func_143027_a(var1);
      if (!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(var2, var3))) {
         this.rand.nextInt();

         try {
            if (this.canSpawnStructureAtCoords(var2, var3)) {
               StructureStart var7 = this.getStructureStart(var2, var3);
               this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var2, var3), var7);
               this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(var2, var3), var7);
               this.func_143026_a(var2, var3, var7);
            }
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
            CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
            var9.addCrashSectionCallable("Is feature chunk", new Callable(this, var2, var3) {
               private final int val$p_180701_3_;
               private static final String __OBFID = "CL_00000506";
               final MapGenStructure this$0;
               private final int val$p_180701_2_;

               public String call() {
                  return this.this$0.canSpawnStructureAtCoords(this.val$p_180701_2_, this.val$p_180701_3_) ? "True" : "False";
               }

               public Object call() throws Exception {
                  return this.call();
               }

               {
                  this.this$0 = var1;
                  this.val$p_180701_2_ = var2;
                  this.val$p_180701_3_ = var3;
               }
            });
            var9.addCrashSection("Chunk location", String.format("%d,%d", var2, var3));
            var9.addCrashSectionCallable("Chunk pos hash", new Callable(this, var2, var3) {
               private final int val$p_180701_3_;
               final MapGenStructure this$0;
               private final int val$p_180701_2_;
               private static final String __OBFID = "CL_00000507";

               public Object call() throws Exception {
                  return this.call();
               }

               {
                  this.this$0 = var1;
                  this.val$p_180701_2_ = var2;
                  this.val$p_180701_3_ = var3;
               }

               public String call() {
                  return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.val$p_180701_2_, this.val$p_180701_3_));
               }
            });
            var9.addCrashSectionCallable("Structure type", new Callable(this) {
               final MapGenStructure this$0;

               {
                  this.this$0 = var1;
               }

               public String call() {
                  return this.this$0.getClass().getCanonicalName();
               }

               public Object call() throws Exception {
                  return this.call();
               }
            });
            throw new ReportedException(var8);
         }
      }

   }

   public boolean func_175796_a(World var1, BlockPos var2) {
      this.func_143027_a(var1);
      Iterator var3 = this.structureMap.values().iterator();

      StructureStart var4;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         var4 = (StructureStart)var3.next();
      } while(!var4.isSizeableStructure() || !var4.getBoundingBox().func_175898_b(var2));

      return true;
   }
}
