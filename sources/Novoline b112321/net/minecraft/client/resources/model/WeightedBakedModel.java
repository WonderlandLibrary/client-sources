package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel$MyWeighedRandomItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
   private final int totalWeight;
   private final List models;
   private final IBakedModel baseModel;

   public WeightedBakedModel(List var1) {
      this.models = var1;
      this.totalWeight = WeightedRandom.getTotalWeight(var1);
      this.baseModel = ((WeightedBakedModel$MyWeighedRandomItem)var1.get(0)).model;
   }

   public List getFaceQuads(EnumFacing var1) {
      return this.baseModel.getFaceQuads(var1);
   }

   public List getGeneralQuads() {
      return this.baseModel.getGeneralQuads();
   }

   public boolean isAmbientOcclusion() {
      return this.baseModel.isAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.baseModel.isGui3d();
   }

   public boolean isBuiltInRenderer() {
      return this.baseModel.isBuiltInRenderer();
   }

   public TextureAtlasSprite getParticleTexture() {
      return this.baseModel.getParticleTexture();
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.baseModel.getItemCameraTransforms();
   }

   public IBakedModel getAlternativeModel(long var1) {
      return ((WeightedBakedModel$MyWeighedRandomItem)WeightedRandom.getRandomItem(this.models, Math.abs((int)var1 >> 16) % this.totalWeight)).model;
   }
}
