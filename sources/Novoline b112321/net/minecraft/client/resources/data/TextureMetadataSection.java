package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.data.IMetadataSection;

public class TextureMetadataSection implements IMetadataSection {
   private final boolean textureBlur;
   private final boolean textureClamp;
   private final List listMipmaps;

   public TextureMetadataSection(boolean var1, boolean var2, List var3) {
      this.textureBlur = var1;
      this.textureClamp = var2;
      this.listMipmaps = var3;
   }

   public boolean getTextureBlur() {
      return this.textureBlur;
   }

   public boolean getTextureClamp() {
      return this.textureClamp;
   }

   public List getListMipmaps() {
      return Collections.unmodifiableList(this.listMipmaps);
   }
}
