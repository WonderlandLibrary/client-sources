package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderDebug implements IChunkProvider {
   private final World field_177463_c;
   private static final int field_177462_b;
   private static final List field_177464_a = Lists.newArrayList();
   private static final String __OBFID = "CL_00002002";

   public boolean canSave() {
      return true;
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean chunkExists(int var1, int var2) {
      return true;
   }

   public ChunkProviderDebug(World var1) {
      this.field_177463_c = var1;
   }

   public List func_177458_a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.field_177463_c.getBiomeGenForCoords(var2);
      return var3.getSpawnableList(var1);
   }

   public void func_180514_a(Chunk var1, int var2, int var3) {
   }

   public void populate(IChunkProvider var1, int var2, int var3) {
   }

   public Chunk func_177459_a(BlockPos var1) {
      return this.provideChunk(var1.getX() >> 4, var1.getZ() >> 4);
   }

   public Chunk provideChunk(int var1, int var2) {
      ChunkPrimer var3 = new ChunkPrimer();

      int var4;
      for(int var5 = 0; var5 < 16; ++var5) {
         for(int var6 = 0; var6 < 16; ++var6) {
            int var7 = var1 * 16 + var5;
            var4 = var2 * 16 + var6;
            var3.setBlockState(var5, 60, var6, Blocks.barrier.getDefaultState());
            IBlockState var8 = func_177461_b(var7, var4);
            if (var8 != null) {
               var3.setBlockState(var5, 70, var6, var8);
            }
         }
      }

      Chunk var9 = new Chunk(this.field_177463_c, var3, var1, var2);
      var9.generateSkylightMap();
      BiomeGenBase[] var10 = this.field_177463_c.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, var1 * 16, var2 * 16, 16, 16);
      byte[] var11 = var9.getBiomeArray();

      for(var4 = 0; var4 < var11.length; ++var4) {
         var11[var4] = (byte)var10[var4].biomeID;
      }

      var9.generateSkylightMap();
      return var9;
   }

   static {
      Iterator var0 = Block.blockRegistry.iterator();

      while(var0.hasNext()) {
         Block var1 = (Block)var0.next();
         field_177464_a.addAll(var1.getBlockState().getValidStates());
      }

      field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float((float)field_177464_a.size()));
   }

   public BlockPos func_180513_a(World var1, String var2, BlockPos var3) {
      return null;
   }

   public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   public String makeString() {
      return "DebugLevelSource";
   }

   public boolean saveChunks(boolean var1, IProgressUpdate var2) {
      return true;
   }

   public static IBlockState func_177461_b(int var0, int var1) {
      IBlockState var2 = null;
      if (var0 > 0 && var1 > 0 && var0 % 2 != 0 && var1 % 2 != 0) {
         var0 /= 2;
         var1 /= 2;
         if (var0 <= field_177462_b && var1 <= field_177462_b) {
            int var3 = MathHelper.abs_int(var0 * field_177462_b + var1);
            if (var3 < field_177464_a.size()) {
               var2 = (IBlockState)field_177464_a.get(var3);
            }
         }
      }

      return var2;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void saveExtraData() {
   }
}
