package net.minecraft.client.model;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.UpdateModelEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer y;
   public ModelRenderer v;
   public ModelRenderer t;
   public ModelRenderer u;
   public ModelRenderer x;
   private ModelRenderer bipedCape;
   private ModelRenderer bipedDeadmau5Head;
   private boolean smallArms;

   public ModelPlayer(float var1, boolean var2) {
      super(var1, 0.0F, 64, 64);
      this.smallArms = var2;
      this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
      this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, var1);
      this.bipedCape = new ModelRenderer(this, 0, 0);
      this.bipedCape.setTextureSize(64, 32);
      this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, var1);
      this.bipedLeftArm = new ModelRenderer(this, 32, 48);
      this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1);
      this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1);
      this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
      this.y = new ModelRenderer(this, 48, 48);
      this.y.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
      this.y.setRotationPoint(5.0F, 2.5F, 0.0F);
      this.v = new ModelRenderer(this, 40, 32);
      this.v.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
      this.v.setRotationPoint(-5.0F, 2.5F, 10.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
      this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.t = new ModelRenderer(this, 0, 48);
      this.t.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.t.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.u = new ModelRenderer(this, 0, 32);
      this.u.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.u.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.x = new ModelRenderer(this, 16, 32);
      this.x.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1 + 0.25F);
      this.x.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.render(var1, var2, var3, var4, var5, var6, var7);
      GlStateManager.pushMatrix();
      if(this.isChild) {
         float var8 = 2.0F;
         GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.translate(0.0F, 24.0F * var7, 0.0F);
      } else if(var1.isSneaking()) {
         GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }

      this.t.render(var7);
      this.u.render(var7);
      this.y.render(var7);
      this.v.render(var7);
      this.x.render(var7);
      GlStateManager.popMatrix();
   }

   public void renderDeadmau5Head(float var1) {
      copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
      this.bipedDeadmau5Head.rotationPointX = 0.0F;
      this.bipedDeadmau5Head.rotationPointY = 0.0F;
      this.bipedDeadmau5Head.render(var1);
   }

   public void renderCape(float var1) {
      this.bipedCape.render(var1);
   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.setRotationAngles(var1, var2, var3, var4, var5, var6, var7);
      copyModelAngles(this.bipedLeftLeg, this.t);
      copyModelAngles(this.bipedRightLeg, this.u);
      copyModelAngles(this.bipedLeftArm, this.y);
      copyModelAngles(this.bipedRightArm, this.v);
      copyModelAngles(this.bipedBody, this.x);
      EventManager.call(new UpdateModelEvent(var7, this));
   }

   public void renderRightArm() {
      this.bipedRightArm.render(0.0625F);
      this.v.render(0.0625F);
   }

   public void renderLeftArm() {
      this.bipedLeftArm.render(0.0625F);
      this.y.render(0.0625F);
   }

   public void setInvisible(boolean var1) {
      super.setInvisible(var1);
      this.y.showModel = var1;
      this.v.showModel = var1;
      this.t.showModel = var1;
      this.u.showModel = var1;
      this.x.showModel = var1;
      this.bipedCape.showModel = var1;
      this.bipedDeadmau5Head.showModel = var1;
   }

   public void postRenderArm(float var1) {
      if(this.smallArms) {
         ++this.bipedRightArm.rotationPointX;
         this.bipedRightArm.postRender(var1);
         --this.bipedRightArm.rotationPointX;
      } else {
         this.bipedRightArm.postRender(var1);
      }

   }
}
