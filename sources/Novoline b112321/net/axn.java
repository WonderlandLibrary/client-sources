package net;

import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStronghold;

public class axn {
   public static void a(MapGenStronghold var0, IChunkProvider var1, World var2, int var3, int var4, ChunkPrimer var5) {
      var0.generate(var1, var2, var3, var4, var5);
   }

   public static boolean a(MapGenStronghold var0, World var1, Random var2, ChunkCoordIntPair var3) {
      return var0.generateStructure(var1, var2, var3);
   }

   public static BlockPos a(MapGenStronghold var0, World var1, BlockPos var2) {
      return var0.getClosestStrongholdPos(var1, var2);
   }
}
