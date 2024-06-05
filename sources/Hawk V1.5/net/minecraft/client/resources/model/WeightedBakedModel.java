package net.minecraft.client.resources.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
   private static final String __OBFID = "CL_00002384";
   private final IBakedModel baseModel;
   private final List models;
   private final int totalWeight;

   public boolean isAmbientOcclusionEnabled() {
      return this.baseModel.isAmbientOcclusionEnabled();
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.baseModel.getItemCameraTransforms();
   }

   public boolean isBuiltInRenderer() {
      return this.baseModel.isBuiltInRenderer();
   }

   public List func_177550_a() {
      return this.baseModel.func_177550_a();
   }

   public List func_177551_a(EnumFacing var1) {
      return this.baseModel.func_177551_a(var1);
   }

   public WeightedBakedModel(List var1) {
      this.models = var1;
      this.totalWeight = WeightedRandom.getTotalWeight(var1);
      this.baseModel = ((WeightedBakedModel.MyWeighedRandomItem)var1.get(0)).model;
   }

   public TextureAtlasSprite getTexture() {
      return this.baseModel.getTexture();
   }

   public boolean isGui3d() {
      return this.baseModel.isGui3d();
   }

   public IBakedModel func_177564_a(long var1) {
      return ((WeightedBakedModel.MyWeighedRandomItem)WeightedRandom.func_180166_a(this.models, Math.abs((int)var1 >> 16) % this.totalWeight)).model;
   }

   static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable {
      protected final IBakedModel model;
      private static final String __OBFID = "CL_00002382";

      protected int func_177635_a() {
         int var1 = this.model.func_177550_a().size();
         EnumFacing[] var2 = EnumFacing.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumFacing var5 = var2[var4];
            var1 += this.model.func_177551_a(var5).size();
         }

         return var1;
      }

      public String toString() {
         return String.valueOf((new StringBuilder("MyWeighedRandomItem{weight=")).append(this.itemWeight).append(", model=").append(this.model).append('}'));
      }

      public int compareTo(Object var1) {
         return this.func_177634_a((WeightedBakedModel.MyWeighedRandomItem)var1);
      }

      public MyWeighedRandomItem(IBakedModel var1, int var2) {
         super(var2);
         this.model = var1;
      }

      public int func_177634_a(WeightedBakedModel.MyWeighedRandomItem var1) {
         return ComparisonChain.start().compare(var1.itemWeight, this.itemWeight).compare(this.func_177635_a(), var1.func_177635_a()).result();
      }
   }

   public static class Builder {
      private List field_177678_a = Lists.newArrayList();
      private static final String __OBFID = "CL_00002383";

      public WeightedBakedModel build() {
         Collections.sort(this.field_177678_a);
         return new WeightedBakedModel(this.field_177678_a);
      }

      public IBakedModel first() {
         return ((WeightedBakedModel.MyWeighedRandomItem)this.field_177678_a.get(0)).model;
      }

      public WeightedBakedModel.Builder add(IBakedModel var1, int var2) {
         this.field_177678_a.add(new WeightedBakedModel.MyWeighedRandomItem(var1, var2));
         return this;
      }
   }
}
