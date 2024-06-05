package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private ItemCameraTransforms cameraTransforms;

   public BuiltInModel(ItemCameraTransforms var1) {
      this.cameraTransforms = var1;
   }

   public List getFaceQuads(EnumFacing var1) {
      return null;
   }

   public List getGeneralQuads() {
      return null;
   }

   public boolean isAmbientOcclusion() {
      return false;
   }

   public boolean isGui3d() {
      return true;
   }

   public boolean isBuiltInRenderer() {
      return true;
   }

   public TextureAtlasSprite getParticleTexture() {
      return null;
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.cameraTransforms;
   }
}
