package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPigZombie$1;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped {
   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

   public RenderPigZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new RenderPigZombie$1(this, this));
   }

   protected ResourceLocation getEntityTexture(EntityPigZombie var1) {
      return ZOMBIE_PIGMAN_TEXTURE;
   }
}
