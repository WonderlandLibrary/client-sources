package net;

import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.optifine.CustomItemProperties;

public class a_G {
   public static boolean a(CustomItemProperties var0, String var1) {
      return var0.isValid(var1);
   }

   public static void a(CustomItemProperties var0, TextureMap var1, ItemModelGenerator var2) {
      var0.updateModel(var1, var2);
   }

   public static IBakedModel a(CustomItemProperties var0, ModelResourceLocation var1) {
      return var0.getModel(var1);
   }

   public static float a(CustomItemProperties var0, TextureManager var1) {
      return var0.getTextureWidth(var1);
   }
}
