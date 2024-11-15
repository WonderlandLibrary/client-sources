package exhibition.tails;

import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class TextureHelper {
   private static ResourceLocation generateTexture(UUID uuid, int typeid, int subid, int textureID, int[] tints) {
      return null;
   }

   public static ResourceLocation generateTexture(UUID uuid, PartInfo partInfo) {
      return generateTexture(uuid, partInfo.typeid, partInfo.subid, partInfo.textureID, partInfo.tints);
   }
}
