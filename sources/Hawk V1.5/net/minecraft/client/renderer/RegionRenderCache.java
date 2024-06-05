package net.minecraft.client.renderer;

import java.util.ArrayDeque;
import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import optifine.Config;
import optifine.DynamicLights;

public class RegionRenderCache extends ChunkCache {
   private static ArrayDeque<int[]> cacheLights;
   private final BlockPos field_175633_g;
   private static final IBlockState field_175632_f;
   private int[] field_175634_h;
   private static ArrayDeque<IBlockState[]> cacheStates;
   private static int maxCacheSize;
   private static final String __OBFID = "CL_00002565";
   private IBlockState[] field_175635_i;

   static {
      field_175632_f = Blocks.air.getDefaultState();
      cacheLights = new ArrayDeque();
      cacheStates = new ArrayDeque();
      maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
   }

   private static IBlockState[] allocateStates(int var0) {
      ArrayDeque var1 = cacheStates;
      synchronized(cacheStates) {
         IBlockState[] var3 = (IBlockState[])cacheStates.pollLast();
         if (var3 != null && var3.length >= var0) {
            Arrays.fill(var3, (Object)null);
         } else {
            var3 = new IBlockState[var0];
         }

         return var3;
      }
   }

   public TileEntity getTileEntity(BlockPos var1) {
      int var2 = (var1.getX() >> 4) - this.chunkX;
      int var3 = (var1.getZ() >> 4) - this.chunkZ;
      return this.chunkArray[var2][var3].func_177424_a(var1, Chunk.EnumCreateEntityType.QUEUED);
   }

   public void freeBuffers() {
      freeLights(this.field_175634_h);
      freeStates(this.field_175635_i);
   }

   private IBlockState func_175631_c(BlockPos var1) {
      if (var1.getY() >= 0 && var1.getY() < 256) {
         int var2 = (var1.getX() >> 4) - this.chunkX;
         int var3 = (var1.getZ() >> 4) - this.chunkZ;
         return this.chunkArray[var2][var3].getBlockState(var1);
      } else {
         return field_175632_f;
      }
   }

   public IBlockState getBlockState(BlockPos var1) {
      int var2 = this.func_175630_e(var1);
      IBlockState var3 = this.field_175635_i[var2];
      if (var3 == null) {
         var3 = this.func_175631_c(var1);
         this.field_175635_i[var2] = var3;
      }

      return var3;
   }

   private static int[] allocateLights(int var0) {
      ArrayDeque var1 = cacheLights;
      synchronized(cacheLights) {
         int[] var3 = (int[])cacheLights.pollLast();
         if (var3 == null || var3.length < var0) {
            var3 = new int[var0];
         }

         return var3;
      }
   }

   public static void freeStates(IBlockState[] var0) {
      ArrayDeque var1 = cacheStates;
      synchronized(cacheStates) {
         if (cacheStates.size() < maxCacheSize) {
            cacheStates.add(var0);
         }

      }
   }

   public static void freeLights(int[] var0) {
      ArrayDeque var1 = cacheLights;
      synchronized(cacheLights) {
         if (cacheLights.size() < maxCacheSize) {
            cacheLights.add(var0);
         }

      }
   }

   public RegionRenderCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      super(var1, var2, var3, var4);
      this.field_175633_g = var2.subtract(new Vec3i(var4, var4, var4));
      boolean var5 = true;
      this.field_175634_h = allocateLights(8000);
      Arrays.fill(this.field_175634_h, -1);
      this.field_175635_i = allocateStates(8000);
   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = this.func_175630_e(var1);
      int var4 = this.field_175634_h[var3];
      if (var4 == -1) {
         var4 = super.getCombinedLight(var1, var2);
         if (Config.isDynamicLights() && !this.getBlockState(var1).getBlock().isOpaqueCube()) {
            var4 = DynamicLights.getCombinedLight(var1, var4);
         }

         this.field_175634_h[var3] = var4;
      }

      return var4;
   }

   private int func_175630_e(BlockPos var1) {
      int var2 = var1.getX() - this.field_175633_g.getX();
      int var3 = var1.getY() - this.field_175633_g.getY();
      int var4 = var1.getZ() - this.field_175633_g.getZ();
      return var2 * 400 + var4 * 20 + var3;
   }
}
