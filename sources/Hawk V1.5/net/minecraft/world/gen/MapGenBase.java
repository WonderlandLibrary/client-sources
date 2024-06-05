package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class MapGenBase {
   private static final String __OBFID = "CL_00000394";
   protected int range = 8;
   protected Random rand = new Random();
   protected World worldObj;

   public void func_175792_a(IChunkProvider var1, World var2, int var3, int var4, ChunkPrimer var5) {
      int var6 = this.range;
      this.worldObj = var2;
      this.rand.setSeed(var2.getSeed());
      long var7 = this.rand.nextLong();
      long var9 = this.rand.nextLong();

      for(int var11 = var3 - var6; var11 <= var3 + var6; ++var11) {
         for(int var12 = var4 - var6; var12 <= var4 + var6; ++var12) {
            long var13 = (long)var11 * var7;
            long var15 = (long)var12 * var9;
            this.rand.setSeed(var13 ^ var15 ^ var2.getSeed());
            this.func_180701_a(var2, var11, var12, var3, var4, var5);
         }
      }

   }

   protected void func_180701_a(World var1, int var2, int var3, int var4, int var5, ChunkPrimer var6) {
   }
}
