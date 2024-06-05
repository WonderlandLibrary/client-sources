package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderGiantZombie;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class RenderGiantZombie$1 extends LayerBipedArmor {
   final RenderGiantZombie this$0;

   RenderGiantZombie$1(RenderGiantZombie var1, RendererLivingEntity var2) {
      super(var2);
      this.this$0 = var1;
   }

   protected void initArmor() {
      this.field_177189_c = new ModelZombie(0.5F, true);
      this.field_177186_d = new ModelZombie(1.0F, true);
   }
}
