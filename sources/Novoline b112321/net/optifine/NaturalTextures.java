package net.optifine;

import java.io.FileNotFoundException;
import net.acE;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockPos;
import net.optifine.MatchBlock;
import net.optifine.NaturalProperties;

public class NaturalTextures {
   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

   public static void update() {
      // $FF: Couldn't be decompiled
   }

   public static BakedQuad a(BlockPos var0, BakedQuad var1) {
      MatchBlock.b();
      TextureAtlasSprite var3 = var1.getSprite();
      if(var3 == null) {
         return var1;
      } else {
         NaturalProperties var4 = getNaturalProperties(var3);
         return var1;
      }
   }

   public static NaturalProperties getNaturalProperties(TextureAtlasSprite var0) {
      acE[] var1 = MatchBlock.b();
      if(!(var0 instanceof TextureAtlasSprite)) {
         return null;
      } else {
         int var2 = var0.getIndexInMap();
         if(var2 >= 0 && var2 < propertiesByIndex.length) {
            NaturalProperties var3 = propertiesByIndex[var2];
            return var3;
         } else {
            return null;
         }
      }
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
