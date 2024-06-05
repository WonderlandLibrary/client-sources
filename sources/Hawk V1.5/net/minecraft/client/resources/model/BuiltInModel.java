package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private static final String __OBFID = "CL_00002392";
   private ItemCameraTransforms field_177557_a;

   public TextureAtlasSprite getTexture() {
      return null;
   }

   public boolean isGui3d() {
      return false;
   }

   public List func_177550_a() {
      return null;
   }

   public boolean isAmbientOcclusionEnabled() {
      return true;
   }

   public BuiltInModel(ItemCameraTransforms var1) {
      this.field_177557_a = var1;
   }

   public boolean isBuiltInRenderer() {
      return true;
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.field_177557_a;
   }

   public List func_177551_a(EnumFacing var1) {
      return null;
   }
}
