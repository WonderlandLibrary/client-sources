package net;

import java.util.List;
import net.minecraft.client.resources.data.TextureMetadataSection;

public class ax8 {
   public static boolean c(TextureMetadataSection var0) {
      return var0.getTextureBlur();
   }

   public static boolean b(TextureMetadataSection var0) {
      return var0.getTextureClamp();
   }

   public static List a(TextureMetadataSection var0) {
      return var0.getListMipmaps();
   }
}
