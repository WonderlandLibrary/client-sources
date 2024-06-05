package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class ArmorStandRenderer$1 extends LayerBipedArmor {
   final ArmorStandRenderer this$0;

   ArmorStandRenderer$1(ArmorStandRenderer var1, RendererLivingEntity var2) {
      super(var2);
      this.this$0 = var1;
   }

   protected void initArmor() {
      this.field_177189_c = new ModelArmorStandArmor(0.5F);
      this.field_177186_d = new ModelArmorStandArmor(1.0F);
   }
}
