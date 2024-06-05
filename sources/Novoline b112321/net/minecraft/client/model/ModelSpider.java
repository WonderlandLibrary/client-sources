package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpider extends ModelBase {
   public ModelRenderer spiderHead;
   public ModelRenderer spiderNeck;
   public ModelRenderer spiderBody;
   public ModelRenderer spiderLeg1;
   public ModelRenderer spiderLeg2;
   public ModelRenderer spiderLeg3;
   public ModelRenderer spiderLeg4;
   public ModelRenderer spiderLeg5;
   public ModelRenderer spiderLeg6;
   public ModelRenderer spiderLeg7;
   public ModelRenderer spiderLeg8;

   public ModelSpider() {
      float var1 = 0.0F;
      boolean var2 = true;
      this.spiderHead = new ModelRenderer(this, 32, 4);
      this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
      this.spiderHead.setRotationPoint(0.0F, 15.0F, -3.0F);
      this.spiderNeck = new ModelRenderer(this, 0, 0);
      this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
      this.spiderNeck.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.spiderBody = new ModelRenderer(this, 0, 12);
      this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, 0.0F);
      this.spiderBody.setRotationPoint(0.0F, 15.0F, 9.0F);
      this.spiderLeg1 = new ModelRenderer(this, 18, 0);
      this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg1.setRotationPoint(-4.0F, 15.0F, 2.0F);
      this.spiderLeg2 = new ModelRenderer(this, 18, 0);
      this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg2.setRotationPoint(4.0F, 15.0F, 2.0F);
      this.spiderLeg3 = new ModelRenderer(this, 18, 0);
      this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg3.setRotationPoint(-4.0F, 15.0F, 1.0F);
      this.spiderLeg4 = new ModelRenderer(this, 18, 0);
      this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg4.setRotationPoint(4.0F, 15.0F, 1.0F);
      this.spiderLeg5 = new ModelRenderer(this, 18, 0);
      this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg5.setRotationPoint(-4.0F, 15.0F, 0.0F);
      this.spiderLeg6 = new ModelRenderer(this, 18, 0);
      this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg6.setRotationPoint(4.0F, 15.0F, 0.0F);
      this.spiderLeg7 = new ModelRenderer(this, 18, 0);
      this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg7.setRotationPoint(-4.0F, 15.0F, -1.0F);
      this.spiderLeg8 = new ModelRenderer(this, 18, 0);
      this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, 0.0F);
      this.spiderLeg8.setRotationPoint(4.0F, 15.0F, -1.0F);
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.setRotationAngles(var2, var3, var4, var5, var6, var7, var1);
      this.spiderHead.render(var7);
      this.spiderNeck.render(var7);
      this.spiderBody.render(var7);
      this.spiderLeg1.render(var7);
      this.spiderLeg2.render(var7);
      this.spiderLeg3.render(var7);
      this.spiderLeg4.render(var7);
      this.spiderLeg5.render(var7);
      this.spiderLeg6.render(var7);
      this.spiderLeg7.render(var7);
      this.spiderLeg8.render(var7);
   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.spiderHead.rotateAngleY = var4 / 57.295776F;
      this.spiderHead.rotateAngleX = var5 / 57.295776F;
      float var8 = 0.7853982F;
      this.spiderLeg1.rotateAngleZ = -0.7853982F;
      this.spiderLeg2.rotateAngleZ = 0.7853982F;
      this.spiderLeg3.rotateAngleZ = -0.58119464F;
      this.spiderLeg4.rotateAngleZ = 0.58119464F;
      this.spiderLeg5.rotateAngleZ = -0.58119464F;
      this.spiderLeg6.rotateAngleZ = 0.58119464F;
      this.spiderLeg7.rotateAngleZ = -0.7853982F;
      this.spiderLeg8.rotateAngleZ = 0.7853982F;
      float var9 = -0.0F;
      float var10 = 0.3926991F;
      this.spiderLeg1.rotateAngleY = 0.7853982F;
      this.spiderLeg2.rotateAngleY = -0.7853982F;
      this.spiderLeg3.rotateAngleY = 0.3926991F;
      this.spiderLeg4.rotateAngleY = -0.3926991F;
      this.spiderLeg5.rotateAngleY = -0.3926991F;
      this.spiderLeg6.rotateAngleY = 0.3926991F;
      this.spiderLeg7.rotateAngleY = -0.7853982F;
      this.spiderLeg8.rotateAngleY = 0.7853982F;
      float var11 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * var2;
      float var12 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * var2;
      float var13 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * var2;
      float var14 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * var2;
      float var15 = Math.abs(MathHelper.sin(var1 * 0.6662F + 0.0F) * 0.4F) * var2;
      float var16 = Math.abs(MathHelper.sin(var1 * 0.6662F + 3.1415927F) * 0.4F) * var2;
      float var17 = Math.abs(MathHelper.sin(var1 * 0.6662F + 1.5707964F) * 0.4F) * var2;
      float var18 = Math.abs(MathHelper.sin(var1 * 0.6662F + 4.712389F) * 0.4F) * var2;
      this.spiderLeg1.rotateAngleY += var11;
      this.spiderLeg2.rotateAngleY += -var11;
      this.spiderLeg3.rotateAngleY += var12;
      this.spiderLeg4.rotateAngleY += -var12;
      this.spiderLeg5.rotateAngleY += var13;
      this.spiderLeg6.rotateAngleY += -var13;
      this.spiderLeg7.rotateAngleY += var14;
      this.spiderLeg8.rotateAngleY += -var14;
      this.spiderLeg1.rotateAngleZ += var15;
      this.spiderLeg2.rotateAngleZ += -var15;
      this.spiderLeg3.rotateAngleZ += var16;
      this.spiderLeg4.rotateAngleZ += -var16;
      this.spiderLeg5.rotateAngleZ += var17;
      this.spiderLeg6.rotateAngleZ += -var17;
      this.spiderLeg7.rotateAngleZ += var18;
      this.spiderLeg8.rotateAngleZ += -var18;
   }
}
