package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;

public class ModelDragon extends ModelBase {
   private final ModelRenderer head;
   private final ModelRenderer spine;
   private final ModelRenderer jaw;
   private final ModelRenderer body;
   private final ModelRenderer rearLeg;
   private final ModelRenderer frontLeg;
   private final ModelRenderer rearLegTip;
   private final ModelRenderer frontLegTip;
   private final ModelRenderer rearFoot;
   private final ModelRenderer frontFoot;
   private final ModelRenderer wing;
   private final ModelRenderer wingTip;
   private float partialTicks;

   public ModelDragon(float var1) {
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.setTextureOffset("body.body", 0, 0);
      this.setTextureOffset("wing.skin", -56, 88);
      this.setTextureOffset("wingtip.skin", -56, 144);
      this.setTextureOffset("rearleg.main", 0, 0);
      this.setTextureOffset("rearfoot.main", 112, 0);
      this.setTextureOffset("rearlegtip.main", 196, 0);
      this.setTextureOffset("head.upperhead", 112, 30);
      this.setTextureOffset("wing.bone", 112, 88);
      this.setTextureOffset("head.upperlip", 176, 44);
      this.setTextureOffset("jaw.jaw", 176, 65);
      this.setTextureOffset("frontleg.main", 112, 104);
      this.setTextureOffset("wingtip.bone", 112, 136);
      this.setTextureOffset("frontfoot.main", 144, 104);
      this.setTextureOffset("neck.box", 192, 104);
      this.setTextureOffset("frontlegtip.main", 226, 138);
      this.setTextureOffset("body.scale", 220, 53);
      this.setTextureOffset("head.scale", 0, 0);
      this.setTextureOffset("neck.scale", 48, 0);
      this.setTextureOffset("head.nostril", 112, 0);
      float var2 = -16.0F;
      this.head = new ModelRenderer(this, "head");
      this.head.addBox("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16);
      this.head.addBox("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16);
      this.head.mirror = true;
      this.head.addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6);
      this.head.addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4);
      this.head.mirror = false;
      this.head.addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6);
      this.head.addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4);
      this.jaw = new ModelRenderer(this, "jaw");
      this.jaw.setRotationPoint(0.0F, 4.0F, -8.0F);
      this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.head.addChild(this.jaw);
      this.spine = new ModelRenderer(this, "neck");
      this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.body = new ModelRenderer(this, "body");
      this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
      this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.wing = new ModelRenderer(this, "wing");
      this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
      this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wingTip = new ModelRenderer(this, "wingtip");
      this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
      this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wing.addChild(this.wingTip);
      this.frontLeg = new ModelRenderer(this, "frontleg");
      this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
      this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.frontLegTip = new ModelRenderer(this, "frontlegtip");
      this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.frontLeg.addChild(this.frontLegTip);
      this.frontFoot = new ModelRenderer(this, "frontfoot");
      this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.frontLegTip.addChild(this.frontFoot);
      this.rearLeg = new ModelRenderer(this, "rearleg");
      this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
      this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.rearLegTip = new ModelRenderer(this, "rearlegtip");
      this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
      this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.rearLeg.addChild(this.rearLegTip);
      this.rearFoot = new ModelRenderer(this, "rearfoot");
      this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
      this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.rearLegTip.addChild(this.rearFoot);
   }

   public void setLivingAnimations(EntityLivingBase var1, float var2, float var3, float var4) {
      this.partialTicks = var4;
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.pushMatrix();
      EntityDragon var8 = (EntityDragon)var1;
      float var9 = var8.prevAnimTime + (var8.animTime - var8.prevAnimTime) * this.partialTicks;
      this.jaw.rotateAngleX = (float)((double)MathHelper.sin(var9 * 3.1415927F * 2.0F) + 1.0D) * 0.2F;
      float var10 = (float)((double)MathHelper.sin((double)(var9 * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
      var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
      GlStateManager.translate(0.0F, var10 - 2.0F, -3.0F);
      GlStateManager.rotate(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
      float var11 = 0.0F;
      float var12 = 1.5F;
      double[] var13 = var8.getMovementOffsets(6, this.partialTicks);
      float var14 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] - var8.getMovementOffsets(10, this.partialTicks)[0]);
      float var15 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] + (double)(var14 / 2.0F));
      float var16 = var9 * 3.1415927F * 2.0F;
      float var17 = 20.0F;
      float var18 = -12.0F;

      for(int var19 = 0; var19 < 5; ++var19) {
         double[] var20 = var8.getMovementOffsets(5 - var19, this.partialTicks);
         float var21 = MathHelper.cos((float)var19 * 0.45F + var16) * 0.15F;
         float var22 = 0.017453292F;
         this.spine.rotateAngleY = this.updateRotations(var20[0] - var13[0]) * 0.017453292F * 1.5F;
         this.spine.rotateAngleX = var21 + (float)(var20[1] - var13[1]) * 0.017453292F * 1.5F * 5.0F;
         this.spine.rotateAngleZ = -this.updateRotations(var20[0] - (double)var15) * 0.017453292F * 1.5F;
         this.spine.rotationPointY = var17;
         this.spine.rotationPointZ = var18;
         this.spine.rotationPointX = var11;
         var17 = (float)((double)var17 + (double)MathHelper.sin(this.spine.rotateAngleX) * 10.0D);
         float var23 = MathHelper.cos(this.spine.rotateAngleX);
         var18 = (float)((double)var18 - (double)(MathHelper.cos(this.spine.rotateAngleY) * var23) * 10.0D);
         var11 = (float)((double)var11 - (double)(MathHelper.sin(this.spine.rotateAngleY) * var23) * 10.0D);
         this.spine.render(var7);
      }

      this.head.rotationPointY = var17;
      this.head.rotationPointZ = var18;
      this.head.rotationPointX = var11;
      double[] var30 = var8.getMovementOffsets(0, this.partialTicks);
      float var32 = 0.017453292F;
      this.head.rotateAngleY = this.updateRotations(var30[0] - var13[0]) * 0.017453292F * 1.0F;
      this.head.rotateAngleZ = -this.updateRotations(var30[0] - (double)var15) * 0.017453292F * 1.0F;
      this.head.render(var7);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-var14 * 1.5F * 1.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      this.body.rotateAngleZ = 0.0F;
      this.body.render(var7);

      for(int var33 = 0; var33 < 2; ++var33) {
         GlStateManager.enableCull();
         float var35 = var9 * 3.1415927F * 2.0F;
         this.wing.rotateAngleX = 0.125F - MathHelper.cos(var35) * 0.2F;
         this.wing.rotateAngleY = 0.25F;
         this.wing.rotateAngleZ = (float)((double)MathHelper.sin(var35) + 0.125D) * 0.8F;
         this.wingTip.rotateAngleZ = -((float)((double)MathHelper.sin(var35 + 2.0F) + 0.5D)) * 0.75F;
         this.rearLeg.rotateAngleX = 1.0F + var10 * 0.1F;
         this.rearLegTip.rotateAngleX = 0.5F + var10 * 0.1F;
         this.rearFoot.rotateAngleX = 0.75F + var10 * 0.1F;
         this.frontLeg.rotateAngleX = 1.3F + var10 * 0.1F;
         this.frontLegTip.rotateAngleX = -0.5F - var10 * 0.1F;
         this.frontFoot.rotateAngleX = 0.75F + var10 * 0.1F;
         this.wing.render(var7);
         this.frontLeg.render(var7);
         this.rearLeg.render(var7);
         GlStateManager.scale(-1.0F, 1.0F, 1.0F);
         GlStateManager.cullFace(1028);
      }

      GlStateManager.popMatrix();
      GlStateManager.cullFace(1029);
      GlStateManager.disableCull();
      float var34 = -MathHelper.sin(var9 * 3.1415927F * 2.0F) * 0.0F;
      var16 = var9 * 3.1415927F * 2.0F;
      var17 = 10.0F;
      var18 = 60.0F;
      var11 = 0.0F;
      var13 = var8.getMovementOffsets(11, this.partialTicks);

      for(int var36 = 0; var36 < 12; ++var36) {
         var30 = var8.getMovementOffsets(12 + var36, this.partialTicks);
         var34 = (float)((double)var34 + (double)MathHelper.sin((float)var36 * 0.45F + var16) * 0.05000000074505806D);
         this.spine.rotateAngleY = (this.updateRotations(var30[0] - var13[0]) * 1.5F + 180.0F) * 3.1415927F / 180.0F;
         this.spine.rotateAngleX = var34 + (float)(var30[1] - var13[1]) * 3.1415927F / 180.0F * 1.5F * 5.0F;
         this.spine.rotateAngleZ = this.updateRotations(var30[0] - (double)var15) * 3.1415927F / 180.0F * 1.5F;
         this.spine.rotationPointY = var17;
         this.spine.rotationPointZ = var18;
         this.spine.rotationPointX = var11;
         var17 = (float)((double)var17 + (double)MathHelper.sin(this.spine.rotateAngleX) * 10.0D);
         var18 = (float)((double)var18 - (double)(MathHelper.cos(this.spine.rotateAngleY) * MathHelper.cos(this.spine.rotateAngleX)) * 10.0D);
         var11 = (float)((double)var11 - (double)(MathHelper.sin(this.spine.rotateAngleY) * MathHelper.cos(this.spine.rotateAngleX)) * 10.0D);
         this.spine.render(var7);
      }

      GlStateManager.popMatrix();
   }

   private float updateRotations(double var1) {
      while(var1 >= 180.0D) {
         var1 -= 360.0D;
      }

      while(var1 < -180.0D) {
         var1 += 360.0D;
      }

      return (float)var1;
   }
}
