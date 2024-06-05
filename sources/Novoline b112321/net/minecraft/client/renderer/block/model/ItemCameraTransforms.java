package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$1;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;

public class ItemCameraTransforms {
   public static final ItemCameraTransforms DEFAULT = new ItemCameraTransforms();
   public static float field_181690_b = 0.0F;
   public static float field_181691_c = 0.0F;
   public static float field_181692_d = 0.0F;
   public static float field_181693_e = 0.0F;
   public static float field_181694_f = 0.0F;
   public static float field_181695_g = 0.0F;
   public static float field_181696_h = 0.0F;
   public static float field_181697_i = 0.0F;
   public static float field_181698_j = 0.0F;
   public final ItemTransformVec3f thirdPerson;
   public final ItemTransformVec3f firstPerson;
   public final ItemTransformVec3f head;
   public final ItemTransformVec3f gui;
   public final ItemTransformVec3f ground;
   public final ItemTransformVec3f fixed;

   private ItemCameraTransforms() {
      this(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
   }

   public ItemCameraTransforms(ItemCameraTransforms var1) {
      this.thirdPerson = var1.thirdPerson;
      this.firstPerson = var1.firstPerson;
      this.head = var1.head;
      this.gui = var1.gui;
      this.ground = var1.ground;
      this.fixed = var1.fixed;
   }

   public ItemCameraTransforms(ItemTransformVec3f var1, ItemTransformVec3f var2, ItemTransformVec3f var3, ItemTransformVec3f var4, ItemTransformVec3f var5, ItemTransformVec3f var6) {
      this.thirdPerson = var1;
      this.firstPerson = var2;
      this.head = var3;
      this.gui = var4;
      this.ground = var5;
      this.fixed = var6;
   }

   public void applyTransform(ItemCameraTransforms$TransformType var1) {
      ItemTransformVec3f var2 = this.getTransform(var1);
      if(var2 != ItemTransformVec3f.DEFAULT) {
         GlStateManager.translate(var2.translation.x + field_181690_b, var2.translation.y + field_181691_c, var2.translation.z + field_181692_d);
         GlStateManager.rotate(var2.rotation.y + field_181694_f, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(var2.rotation.x + field_181693_e, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var2.rotation.z + field_181695_g, 0.0F, 0.0F, 1.0F);
         GlStateManager.scale(var2.scale.x + field_181696_h, var2.scale.y + field_181697_i, var2.scale.z + field_181698_j);
      }

   }

   public ItemTransformVec3f getTransform(ItemCameraTransforms$TransformType var1) {
      switch(ItemCameraTransforms$1.$SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[var1.ordinal()]) {
      case 1:
         return this.thirdPerson;
      case 2:
         return this.firstPerson;
      case 3:
         return this.head;
      case 4:
         return this.gui;
      case 5:
         return this.ground;
      case 6:
         return this.fixed;
      default:
         return ItemTransformVec3f.DEFAULT;
      }
   }

   public boolean func_181687_c(ItemCameraTransforms$TransformType var1) {
      return !this.getTransform(var1).equals(ItemTransformVec3f.DEFAULT);
   }
}
