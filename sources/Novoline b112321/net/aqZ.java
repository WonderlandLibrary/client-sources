package net;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel$Builder;
import net.minecraft.util.EnumFacing;

public class aqZ {
   public static SimpleBakedModel$Builder a(SimpleBakedModel$Builder var0, TextureAtlasSprite var1) {
      return var0.setTexture(var1);
   }

   public static SimpleBakedModel$Builder a(SimpleBakedModel$Builder var0, BakedQuad var1) {
      return var0.addGeneralQuad(var1);
   }

   public static SimpleBakedModel$Builder a(SimpleBakedModel$Builder var0, EnumFacing var1, BakedQuad var2) {
      return var0.addFaceQuad(var1, var2);
   }

   public static IBakedModel a(SimpleBakedModel$Builder var0) {
      return var0.makeBakedModel();
   }
}
