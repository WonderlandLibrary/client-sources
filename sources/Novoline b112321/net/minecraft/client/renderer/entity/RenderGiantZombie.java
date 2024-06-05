package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderGiantZombie$1;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

public class RenderGiantZombie extends RenderLiving {
   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
   private float scale;

   public RenderGiantZombie(RenderManager var1, ModelBase var2, float var3, float var4) {
      super(var1, var2, var3 * var4);
      this.scale = var4;
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new RenderGiantZombie$1(this, this));
   }

   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(EntityGiantZombie var1, float var2) {
      GlStateManager.scale(this.scale, this.scale, this.scale);
   }

   protected ResourceLocation getEntityTexture(EntityGiantZombie var1) {
      return zombieTextures;
   }
}
