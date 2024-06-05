package net;

import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class kd {
   public static Set a(IResourceManager var0) {
      return var0.getResourceDomains();
   }

   public static List a(IResourceManager var0, ResourceLocation var1) {
      return var0.getAllResources(var1);
   }

   public static IResource b(IResourceManager var0, ResourceLocation var1) {
      return var0.getResource(var1);
   }
}
