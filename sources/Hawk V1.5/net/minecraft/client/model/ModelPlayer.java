package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer field_178732_b;
   public ModelRenderer field_178733_c;
   public ModelRenderer field_178731_d;
   public ModelRenderer field_178734_a;
   private boolean field_178735_y;
   private ModelRenderer field_178736_x;
   private ModelRenderer field_178729_w;
   public ModelRenderer field_178730_v;
   private static final String __OBFID = "CL_00002626";

   public void func_178726_b() {
      this.bipedLeftArm.render(0.0625F);
      this.field_178734_a.render(0.0625F);
   }

   public void func_178727_b(float var1) {
      func_178685_a(this.bipedHead, this.field_178736_x);
      this.field_178736_x.rotationPointX = 0.0F;
      this.field_178736_x.rotationPointY = 0.0F;
      this.field_178736_x.render(var1);
   }

   public void postRenderHiddenArm(float var1) {
      if (this.field_178735_y) {
         ++this.bipedRightArm.rotationPointX;
         this.bipedRightArm.postRender(var1);
         --this.bipedRightArm.rotationPointX;
      } else {
         this.bipedRightArm.postRender(var1);
      }

   }

   public void func_178719_a(boolean var1) {
      super.func_178719_a(var1);
      this.field_178734_a.showModel = var1;
      this.field_178732_b.showModel = var1;
      this.field_178733_c.showModel = var1;
      this.field_178731_d.showModel = var1;
      this.field_178730_v.showModel = var1;
      this.field_178729_w.showModel = var1;
      this.field_178736_x.showModel = var1;
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.render(var1, var2, var3, var4, var5, var6, var7);
      GlStateManager.pushMatrix();
      if (this.isChild) {
         float var8 = 2.0F;
         GlStateManager.scale(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.translate(0.0F, 24.0F * var7, 0.0F);
         this.field_178733_c.render(var7);
         this.field_178731_d.render(var7);
         this.field_178734_a.render(var7);
         this.field_178732_b.render(var7);
         this.field_178730_v.render(var7);
      } else {
         if (var1.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         this.field_178733_c.render(var7);
         this.field_178731_d.render(var7);
         this.field_178734_a.render(var7);
         this.field_178732_b.render(var7);
         this.field_178730_v.render(var7);
      }

      GlStateManager.popMatrix();
   }

   public void func_178725_a() {
      this.bipedRightArm.render(0.0625F);
      this.field_178732_b.render(0.0625F);
   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.setRotationAngles(var1, var2, var3, var4, var5, var6, var7);
      func_178685_a(this.bipedLeftLeg, this.field_178733_c);
      func_178685_a(this.bipedRightLeg, this.field_178731_d);
      func_178685_a(this.bipedLeftArm, this.field_178734_a);
      func_178685_a(this.bipedRightArm, this.field_178732_b);
      func_178685_a(this.bipedBody, this.field_178730_v);
   }

   public ModelPlayer(float var1, boolean var2) {
      super(var1, 0.0F, 64, 64);
      this.field_178735_y = var2;
      this.field_178736_x = new ModelRenderer(this, 24, 0);
      this.field_178736_x.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, var1);
      this.field_178729_w = new ModelRenderer(this, 0, 0);
      this.field_178729_w.setTextureSize(64, 32);
      this.field_178729_w.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, var1);
      if (var2) {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
         this.field_178734_a.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
         this.field_178732_b.setRotationPoint(-5.0F, 2.5F, 10.0F);
      } else {
         this.bipedLeftArm = new ModelRenderer(this, 32, 48);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178734_a = new ModelRenderer(this, 48, 48);
         this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
         this.field_178734_a.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178732_b = new ModelRenderer(this, 40, 32);
         this.field_178732_b.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
         this.field_178732_b.setRotationPoint(-5.0F, 2.0F, 10.0F);
      }

      this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
      this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178733_c = new ModelRenderer(this, 0, 48);
      this.field_178733_c.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.field_178733_c.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178731_d = new ModelRenderer(this, 0, 32);
      this.field_178731_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.field_178731_d.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.field_178730_v = new ModelRenderer(this, 16, 32);
      this.field_178730_v.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1 + 0.25F);
      this.field_178730_v.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public void func_178728_c(float var1) {
      this.field_178729_w.render(var1);
   }
}
