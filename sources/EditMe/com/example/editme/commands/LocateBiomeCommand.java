package com.example.editme.commands;

import com.example.editme.util.command.syntax.ChunkBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class LocateBiomeCommand extends Command {
   private static BlockPos spiralOutwardsLookingForBiome(BlockPos var0, Biome var1, long var2, int var4) {
      BiomeProvider var5 = new BiomeProvider(var2, WorldType.field_77137_b, "");
      double var6 = 16.0D / Math.sqrt(3.141592653589793D);
      double var8 = 2.0D * Math.sqrt(3.141592653589793D);
      double var14 = 0.0D;
      long var16 = System.currentTimeMillis();
      PooledMutableBlockPos var18 = PooledMutableBlockPos.func_185346_s();
      int var19 = 0;
      int var20 = 0;

      for(int var21 = 0; var14 < 2.147483647E9D; ++var21) {
         if (System.currentTimeMillis() - var16 > (long)var4) {
            return null;
         }

         double var22 = Math.sqrt((double)var21);
         var14 = var6 * var22;
         double var10 = (double)var0.func_177958_n() + var14 * Math.sin(var8 * var22);
         double var12 = (double)var0.func_177952_p() + var14 * Math.cos(var8 * var22);
         var18.func_189532_c(var10, 0.0D, var12);
         if (var19 == 3) {
            var19 = 0;
         }

         String var10000;
         if (var19 == 0) {
            var10000 = ".";
         } else {
            var10000 = var19 == 1 ? ".." : "...";
         }

         if (var20 == 9216) {
            ++var19;
            var20 = 0;
         }

         ++var20;
         if (var5.func_180631_a(var18).equals(var1)) {
            return new BlockPos((int)var10, 0, (int)var12);
         }
      }

      return null;
   }

   public LocateBiomeCommand() {
      super("locatebiome", (new ChunkBuilder()).append("biome", true).append("seed", true).append("timeout", true).build());
   }

   private static Biome getBiomeFromString(String var0) {
      ResourceLocation var1 = new ResourceLocation(var0);
      Biome var2 = (Biome)ForgeRegistries.BIOMES.getValue(var1);
      return var2;
   }

   public void call(String[] var1) {
      try {
         String var2 = var1[0].toLowerCase().replaceAll(" ", "_");
         Biome var3 = getBiomeFromString(var1[0]);
         (new Thread(LocateBiomeCommand::lambda$call$0)).start();
      } catch (Exception var4) {
      }

   }

   private static double getDistance(int var0, int var1, int var2, int var3) {
      return (double)MathHelper.func_76133_a(Math.pow((double)(var2 - var0), 2.0D) + Math.pow((double)(var3 - var1), 2.0D));
   }

   private static void lambda$call$0(Biome var0, String[] var1) {
      BlockPos var2 = spiralOutwardsLookingForBiome(Minecraft.func_71410_x().field_71439_g.func_180425_c(), var0, Long.parseLong(var1[1]), Integer.parseInt(var1[2]));
      if (var2 == null) {
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append(var1[0]).append(" biome not found!")));
      } else {
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Found ").append(var1[0]).append(" biome at ").append(var2.func_177958_n()).append(", ").append(var2.func_177952_p())));
      }

   }
}
