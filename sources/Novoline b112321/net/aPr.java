package net;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.MatchBlock;
import net.optifine.Matches;

public class aPr {
   public static boolean a(BiomeGenBase var0, BiomeGenBase[] var1) {
      return Matches.biome(var0, var1);
   }

   public static boolean a(int var0, MatchBlock[] var1) {
      return Matches.blockId(var0, var1);
   }

   public static boolean a(int var0, int var1, MatchBlock[] var2) {
      return Matches.block(var0, var1, var2);
   }

   public static boolean a(int var0, int[] var1) {
      return Matches.a(var0, var1);
   }

   public static boolean a(TextureAtlasSprite var0, TextureAtlasSprite[] var1) {
      return Matches.a(var0, var1);
   }

   public static boolean a(BlockStateBase var0, MatchBlock[] var1) {
      return Matches.block(var0, var1);
   }
}
