package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped {
   private static final String __OBFID = "CL_00002434";
   private static final ResourceLocation field_177120_j = new ResourceLocation("textures/entity/zombie_pigman.png");

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_177119_a((EntityPigZombie)var1);
   }

   protected ResourceLocation getEntityTexture(EntityLiving var1) {
      return this.func_177119_a((EntityPigZombie)var1);
   }

   public RenderPigZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerBipedArmor(this, this) {
         private static final String __OBFID = "CL_00002433";
         final RenderPigZombie this$0;

         protected void func_177177_a() {
            this.field_177189_c = new ModelZombie(0.5F, true);
            this.field_177186_d = new ModelZombie(1.0F, true);
         }

         {
            this.this$0 = var1;
         }
      });
   }

   protected ResourceLocation func_177119_a(EntityPigZombie var1) {
      return field_177120_j;
   }
}
