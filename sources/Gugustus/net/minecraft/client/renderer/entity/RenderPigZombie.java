package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped<EntityPigZombie> {
   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

   public RenderPigZombie(RenderManager renderManagerIn) {
      super(renderManagerIn, new ModelZombie(), 0.5F, 1.0F);
      this.addLayer2(new LayerHeldItem(this));
      this.addLayer2(new LayerBipedArmor(this) {
         @Override
         protected void initArmor() {
            this.field_177189_c = new ModelZombie(0.5F, true);
            this.field_177186_d = new ModelZombie(1.0F, true);
         }
      });
   }

   protected ResourceLocation getEntityTexture(EntityPigZombie entity) {
      return ZOMBIE_PIGMAN_TEXTURE;
   }
}
