package net;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import net.apX;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public class ne {
   public static InputStream a(IResourcePack var0, ResourceLocation var1) {
      return var0.getInputStream(var1);
   }

   public static boolean b(IResourcePack var0, ResourceLocation var1) {
      return var0.resourceExists(var1);
   }

   public static String b(IResourcePack var0) {
      return var0.getPackName();
   }

   public static IMetadataSection a(IResourcePack var0, apX var1, String var2) {
      return var0.a(var1, var2);
   }

   public static BufferedImage a(IResourcePack var0) {
      return var0.getPackImage();
   }
}
