package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;

// $FF: synthetic class
class ItemCameraTransforms$1 {
   static final int[] $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType = new int[ItemCameraTransforms$TransformType.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.THIRD_PERSON.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.FIRST_PERSON.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.HEAD.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.GUI.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.GROUND.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[ItemCameraTransforms$TransformType.FIXED.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
