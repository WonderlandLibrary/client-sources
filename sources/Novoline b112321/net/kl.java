package net;

import java.util.Random;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenMineshaft;

public class kl {
   public static void a(MapGenMineshaft var0, IChunkProvider var1, World var2, int var3, int var4, ChunkPrimer var5) {
      var0.generate(var1, var2, var3, var4, var5);
   }

   public static boolean a(MapGenMineshaft var0, World var1, Random var2, ChunkCoordIntPair var3) {
      return var0.generateStructure(var1, var2, var3);
   }
}
