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
import net.minecraft.world.chunk.Chunk$EnumCreateEntityType;
import net.optifine.Config;
import net.optifine.DynamicLights;

public class RegionRenderCache extends ChunkCache {
   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
   private final BlockPos position;
   private int[] combinedLights;
   private IBlockState[] blockStates;
   private static final String h = "CL_00002565";
   private static ArrayDeque cacheLights = new ArrayDeque();
   private static ArrayDeque cacheStates = new ArrayDeque();
   private static int maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);

   public RegionRenderCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      super(var1, var2, var3, var4);
      this.position = var2.subtract(new Vec3i(var4, var4, var4));
      boolean var5 = true;
      this.combinedLights = allocateLights(8000);
      Arrays.fill((int[])this.combinedLights, -1);
      this.blockStates = allocateStates(8000);
   }

   public TileEntity getTileEntity(BlockPos var1) {
      int var2 = (var1.getX() >> 4) - this.chunkX;
      int var3 = (var1.getZ() >> 4) - this.chunkZ;
      return this.chunkArray[var2][var3].getTileEntity(var1, Chunk$EnumCreateEntityType.QUEUED);
   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = this.getPositionIndex(var1);
      int var4 = this.combinedLights[var3];
      if(var4 == -1) {
         var4 = super.getCombinedLight(var1, var2);
         if(Config.al() && !this.getBlockState(var1).getBlock().isOpaqueCube()) {
            var4 = DynamicLights.getCombinedLight(var1, var4);
         }

         this.combinedLights[var3] = var4;
      }

      return var4;
   }

   public IBlockState getBlockState(BlockPos var1) {
      int var2 = this.getPositionIndex(var1);
      IBlockState var3 = this.blockStates[var2];
      var3 = this.getBlockStateRaw(var1);
      this.blockStates[var2] = var3;
      return var3;
   }

   private IBlockState getBlockStateRaw(BlockPos var1) {
      if(var1.getY() >= 0 && var1.getY() < 256) {
         int var2 = (var1.getX() >> 4) - this.chunkX;
         int var3 = (var1.getZ() >> 4) - this.chunkZ;
         return this.chunkArray[var2][var3].getBlockState(var1);
      } else {
         return DEFAULT_STATE;
      }
   }

   private int getPositionIndex(BlockPos var1) {
      int var2 = var1.getX() - this.position.getX();
      int var3 = var1.getY() - this.position.getY();
      int var4 = var1.getZ() - this.position.getZ();
      return var2 * 400 + var4 * 20 + var3;
   }

   public void freeBuffers() {
      freeLights(this.combinedLights);
      freeStates(this.blockStates);
   }

   private static int[] allocateLights(int param0) {
      // $FF: Couldn't be decompiled
   }

   public static void freeLights(int[] param0) {
      // $FF: Couldn't be decompiled
   }

   private static IBlockState[] allocateStates(int param0) {
      // $FF: Couldn't be decompiled
   }

   public static void freeStates(IBlockState[] param0) {
      // $FF: Couldn't be decompiled
   }
}
