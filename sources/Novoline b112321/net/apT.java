package net;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class apT {
   public static boolean a(IChunkProvider var0, int var1, int var2) {
      return var0.chunkExists(var1, var2);
   }

   public static void a(IChunkProvider var0, IChunkProvider var1, int var2, int var3) {
      var0.populate(var1, var2, var3);
   }

   public static boolean a(IChunkProvider var0, IChunkProvider var1, Chunk var2, int var3, int var4) {
      return var0.func_177460_a(var1, var2, var3, var4);
   }

   public static Chunk b(IChunkProvider var0, int var1, int var2) {
      return var0.provideChunk(var1, var2);
   }

   public static boolean d(IChunkProvider var0) {
      return var0.unloadQueuedChunks();
   }

   public static List a(IChunkProvider var0, EnumCreatureType var1, BlockPos var2) {
      return var0.getPossibleCreatures(var1, var2);
   }

   public static boolean c(IChunkProvider var0) {
      return var0.canSave();
   }

   public static boolean a(IChunkProvider var0, boolean var1, IProgressUpdate var2) {
      return var0.saveChunks(var1, var2);
   }

   public static void b(IChunkProvider var0) {
      var0.saveExtraData();
   }

   public static int a(IChunkProvider var0) {
      return var0.getLoadedChunkCount();
   }

   public static String e(IChunkProvider var0) {
      return var0.makeString();
   }

   public static Chunk a(IChunkProvider var0, BlockPos var1) {
      return var0.provideChunk(var1);
   }

   public static BlockPos a(IChunkProvider var0, World var1, String var2, BlockPos var3) {
      return var0.getStrongholdGen(var1, var2, var3);
   }
}
