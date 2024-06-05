package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render {
   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();

   public RenderLeashKnot(RenderManager var1) {
      super(var1);
   }

   public void doRender(EntityLeashKnot var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
      float var10 = 0.0625F;
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      this.bindEntityTexture(var1);
      this.leashKnotModel.render(var1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
      GlStateManager.popMatrix();
      super.doRender(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(EntityLeashKnot var1) {
      return leashKnotTextures;
   }
}
