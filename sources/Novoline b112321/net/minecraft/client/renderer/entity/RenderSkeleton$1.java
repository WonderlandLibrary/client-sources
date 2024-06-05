package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class RenderSkeleton$1 extends LayerBipedArmor {
   final RenderSkeleton this$0;

   RenderSkeleton$1(RenderSkeleton var1, RendererLivingEntity var2) {
      super(var2);
      this.this$0 = var1;
   }

   protected void initArmor() {
      this.field_177189_c = new ModelSkeleton(0.5F, true);
      this.field_177186_d = new ModelSkeleton(1.0F, true);
   }
}
