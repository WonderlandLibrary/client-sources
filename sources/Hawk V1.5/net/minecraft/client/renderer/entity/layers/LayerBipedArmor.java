package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase {
   private static final String __OBFID = "CL_00002417";

   protected void func_177177_a() {
      this.field_177189_c = new ModelBiped(0.5F);
      this.field_177186_d = new ModelBiped(1.0F);
   }

   public LayerBipedArmor(RendererLivingEntity var1) {
      super(var1);
   }

   protected void func_177179_a(ModelBase var1, int var2) {
      this.func_177195_a((ModelBiped)var1, var2);
   }

   protected void func_177195_a(ModelBiped var1, int var2) {
      this.func_177194_a(var1);
      switch(var2) {
      case 1:
         var1.bipedRightLeg.showModel = true;
         var1.bipedLeftLeg.showModel = true;
         break;
      case 2:
         var1.bipedBody.showModel = true;
         var1.bipedRightLeg.showModel = true;
         var1.bipedLeftLeg.showModel = true;
         break;
      case 3:
         var1.bipedBody.showModel = true;
         var1.bipedRightArm.showModel = true;
         var1.bipedLeftArm.showModel = true;
         break;
      case 4:
         var1.bipedHead.showModel = true;
         var1.bipedHeadwear.showModel = true;
      }

   }

   protected void func_177194_a(ModelBiped var1) {
      var1.func_178719_a(false);
   }
}
