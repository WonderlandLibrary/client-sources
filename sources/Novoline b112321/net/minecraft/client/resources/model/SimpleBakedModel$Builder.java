package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;

public class SimpleBakedModel$Builder {
   private final List builderGeneralQuads;
   private final List builderFaceQuads;
   private final boolean builderAmbientOcclusion;
   private TextureAtlasSprite builderTexture;
   private boolean builderGui3d;
   private ItemCameraTransforms builderCameraTransforms;

   public SimpleBakedModel$Builder(ModelBlock var1) {
      this(var1.isAmbientOcclusion(), var1.isGui3d(), var1.func_181682_g());
   }

   public SimpleBakedModel$Builder(IBakedModel var1, TextureAtlasSprite var2) {
      this(var1.isAmbientOcclusion(), var1.isGui3d(), var1.getItemCameraTransforms());
      this.builderTexture = var1.getParticleTexture();

      for(EnumFacing var6 : EnumFacing.values()) {
         this.addFaceBreakingFours(var1, var2, var6);
      }

      this.addGeneralBreakingFours(var1, var2);
   }

   private void addFaceBreakingFours(IBakedModel var1, TextureAtlasSprite var2, EnumFacing var3) {
      for(BakedQuad var5 : var1.getFaceQuads(var3)) {
         this.addFaceQuad(var3, new BreakingFour(var5, var2));
      }

   }

   private void addGeneralBreakingFours(IBakedModel var1, TextureAtlasSprite var2) {
      for(BakedQuad var4 : var1.getGeneralQuads()) {
         this.addGeneralQuad(new BreakingFour(var4, var2));
      }

   }

   private SimpleBakedModel$Builder(boolean var1, boolean var2, ItemCameraTransforms var3) {
      this.builderGeneralQuads = Lists.newArrayList();
      this.builderFaceQuads = Lists.newArrayListWithCapacity(6);

      for(EnumFacing var10000 : EnumFacing.values()) {
         this.builderFaceQuads.add(Lists.newArrayList());
      }

      this.builderAmbientOcclusion = var1;
      this.builderGui3d = var2;
      this.builderCameraTransforms = var3;
   }

   public SimpleBakedModel$Builder addFaceQuad(EnumFacing var1, BakedQuad var2) {
      ((List)this.builderFaceQuads.get(var1.ordinal())).add(var2);
      return this;
   }

   public SimpleBakedModel$Builder addGeneralQuad(BakedQuad var1) {
      this.builderGeneralQuads.add(var1);
      return this;
   }

   public SimpleBakedModel$Builder setTexture(TextureAtlasSprite var1) {
      this.builderTexture = var1;
      return this;
   }

   public IBakedModel makeBakedModel() {
      if(this.builderTexture == null) {
         throw new RuntimeException("Missing particle!");
      } else {
         return new SimpleBakedModel(this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
      }
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
