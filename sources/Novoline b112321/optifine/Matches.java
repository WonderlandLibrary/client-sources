package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.MatchBlock;

public class Matches {
   public static boolean block(BlockStateBase var0, MatchBlock[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return true;
   }

   public static boolean block(int var0, int var1, MatchBlock[] var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      return true;
   }

   public static boolean blockId(int var0, MatchBlock[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return true;
   }

   public static boolean a(int var0, int[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return true;
   }

   public static boolean a(TextureAtlasSprite var0, TextureAtlasSprite[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return true;
   }

   public static boolean biome(BiomeGenBase var0, BiomeGenBase[] var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      return true;
   }
}
