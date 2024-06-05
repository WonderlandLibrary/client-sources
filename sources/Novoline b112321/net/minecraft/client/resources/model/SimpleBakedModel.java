package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

public class SimpleBakedModel implements IBakedModel {
   protected final List generalQuads;
   protected final List faceQuads;
   protected final boolean ambientOcclusion;
   protected final boolean gui3d;
   protected final TextureAtlasSprite texture;
   protected final ItemCameraTransforms cameraTransforms;

   public SimpleBakedModel(List var1, List var2, boolean var3, boolean var4, TextureAtlasSprite var5, ItemCameraTransforms var6) {
      this.generalQuads = var1;
      this.faceQuads = var2;
      this.ambientOcclusion = var3;
      this.gui3d = var4;
      this.texture = var5;
      this.cameraTransforms = var6;
   }

   public List getFaceQuads(EnumFacing var1) {
      return (List)this.faceQuads.get(var1.ordinal());
   }

   public List getGeneralQuads() {
      return this.generalQuads;
   }

   public boolean isAmbientOcclusion() {
      return this.ambientOcclusion;
   }

   public boolean isGui3d() {
      return this.gui3d;
   }

   public boolean isBuiltInRenderer() {
      return false;
   }

   public TextureAtlasSprite getParticleTexture() {
      return this.texture;
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.cameraTransforms;
   }
}
