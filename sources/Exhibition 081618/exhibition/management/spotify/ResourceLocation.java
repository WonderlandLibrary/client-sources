package exhibition.management.spotify;

public class ResourceLocation extends net.minecraft.util.ResourceLocation implements IResourceLocation {
   public ResourceLocation(String resourcePath) {
      super(resourcePath);
   }

   public ResourceLocation(String resourceDomain, String resourcePath) {
      super(resourceDomain, resourcePath);
   }
}
