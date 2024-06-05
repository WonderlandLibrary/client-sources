package net;

import java.io.InputStream;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public class zS {
   public static InputStream c(IResource var0) {
      return var0.getInputStream();
   }

   public static boolean b(IResource var0) {
      return var0.hasMetadata();
   }

   public static IMetadataSection a(IResource var0, String var1) {
      return var0.getMetadata(var1);
   }

   public static ResourceLocation d(IResource var0) {
      return var0.getResourceLocation();
   }

   public static String a(IResource var0) {
      return var0.getResourcePackName();
   }
}
