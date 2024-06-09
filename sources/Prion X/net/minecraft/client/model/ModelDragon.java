package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;























public class ModelDragon
  extends ModelBase
{
  private ModelRenderer head;
  private ModelRenderer spine;
  private ModelRenderer jaw;
  private ModelRenderer body;
  private ModelRenderer rearLeg;
  private ModelRenderer frontLeg;
  private ModelRenderer rearLegTip;
  private ModelRenderer frontLegTip;
  private ModelRenderer rearFoot;
  private ModelRenderer frontFoot;
  private ModelRenderer wing;
  private ModelRenderer wingTip;
  private float partialTicks;
  private static final String __OBFID = "CL_00000870";
  
  public ModelDragon(float p_i46360_1_)
  {
    textureWidth = 256;
    textureHeight = 256;
    setTextureOffset("body.body", 0, 0);
    setTextureOffset("wing.skin", -56, 88);
    setTextureOffset("wingtip.skin", -56, 144);
    setTextureOffset("rearleg.main", 0, 0);
    setTextureOffset("rearfoot.main", 112, 0);
    setTextureOffset("rearlegtip.main", 196, 0);
    setTextureOffset("head.upperhead", 112, 30);
    setTextureOffset("wing.bone", 112, 88);
    setTextureOffset("head.upperlip", 176, 44);
    setTextureOffset("jaw.jaw", 176, 65);
    setTextureOffset("frontleg.main", 112, 104);
    setTextureOffset("wingtip.bone", 112, 136);
    setTextureOffset("frontfoot.main", 144, 104);
    setTextureOffset("neck.box", 192, 104);
    setTextureOffset("frontlegtip.main", 226, 138);
    setTextureOffset("body.scale", 220, 53);
    setTextureOffset("head.scale", 0, 0);
    setTextureOffset("neck.scale", 48, 0);
    setTextureOffset("head.nostril", 112, 0);
    float var2 = -16.0F;
    head = new ModelRenderer(this, "head");
    head.addBox("upperlip", -6.0F, -1.0F, -8.0F + var2, 12, 5, 16);
    head.addBox("upperhead", -8.0F, -8.0F, 6.0F + var2, 16, 16, 16);
    head.mirror = true;
    head.addBox("scale", -5.0F, -12.0F, 12.0F + var2, 2, 4, 6);
    head.addBox("nostril", -5.0F, -3.0F, -6.0F + var2, 2, 2, 4);
    head.mirror = false;
    head.addBox("scale", 3.0F, -12.0F, 12.0F + var2, 2, 4, 6);
    head.addBox("nostril", 3.0F, -3.0F, -6.0F + var2, 2, 2, 4);
    jaw = new ModelRenderer(this, "jaw");
    jaw.setRotationPoint(0.0F, 4.0F, 8.0F + var2);
    jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
    head.addChild(jaw);
    spine = new ModelRenderer(this, "neck");
    spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
    spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
    body = new ModelRenderer(this, "body");
    body.setRotationPoint(0.0F, 4.0F, 8.0F);
    body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
    body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
    body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
    body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
    wing = new ModelRenderer(this, "wing");
    wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
    wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
    wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    wingTip = new ModelRenderer(this, "wingtip");
    wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
    wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
    wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    wing.addChild(wingTip);
    frontLeg = new ModelRenderer(this, "frontleg");
    frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
    frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
    frontLegTip = new ModelRenderer(this, "frontlegtip");
    frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
    frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
    frontLeg.addChild(frontLegTip);
    frontFoot = new ModelRenderer(this, "frontfoot");
    frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
    frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
    frontLegTip.addChild(frontFoot);
    rearLeg = new ModelRenderer(this, "rearleg");
    rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
    rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
    rearLegTip = new ModelRenderer(this, "rearlegtip");
    rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
    rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
    rearLeg.addChild(rearLegTip);
    rearFoot = new ModelRenderer(this, "rearfoot");
    rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
    rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
    rearLegTip.addChild(rearFoot);
  }
  




  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
  {
    partialTicks = p_78086_4_;
  }
  



  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    GlStateManager.pushMatrix();
    EntityDragon var8 = (EntityDragon)p_78088_1_;
    float var9 = prevAnimTime + (animTime - prevAnimTime) * partialTicks;
    jaw.rotateAngleX = ((float)(Math.sin(var9 * 3.1415927F * 2.0F) + 1.0D) * 0.2F);
    float var10 = (float)(Math.sin(var9 * 3.1415927F * 2.0F - 1.0F) + 1.0D);
    var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
    GlStateManager.translate(0.0F, var10 - 2.0F, -3.0F);
    GlStateManager.rotate(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
    float var11 = -30.0F;
    float var13 = 0.0F;
    float var14 = 1.5F;
    double[] var15 = var8.getMovementOffsets(6, partialTicks);
    float var16 = updateRotations(var8.getMovementOffsets(5, partialTicks)[0] - var8.getMovementOffsets(10, partialTicks)[0]);
    float var17 = updateRotations(var8.getMovementOffsets(5, partialTicks)[0] + var16 / 2.0F);
    var11 += 2.0F;
    float var18 = var9 * 3.1415927F * 2.0F;
    var11 = 20.0F;
    float var12 = -12.0F;
    

    for (int var19 = 0; var19 < 5; var19++)
    {
      double[] var20 = var8.getMovementOffsets(5 - var19, partialTicks);
      float var21 = (float)Math.cos(var19 * 0.45F + var18) * 0.15F;
      spine.rotateAngleY = (updateRotations(var20[0] - var15[0]) * 3.1415927F / 180.0F * var14);
      spine.rotateAngleX = (var21 + (float)(var20[1] - var15[1]) * 3.1415927F / 180.0F * var14 * 5.0F);
      spine.rotateAngleZ = (-updateRotations(var20[0] - var17) * 3.1415927F / 180.0F * var14);
      spine.rotationPointY = var11;
      spine.rotationPointZ = var12;
      spine.rotationPointX = var13;
      var11 = (float)(var11 + Math.sin(spine.rotateAngleX) * 10.0D);
      var12 = (float)(var12 - Math.cos(spine.rotateAngleY) * Math.cos(spine.rotateAngleX) * 10.0D);
      var13 = (float)(var13 - Math.sin(spine.rotateAngleY) * Math.cos(spine.rotateAngleX) * 10.0D);
      spine.render(p_78088_7_);
    }
    
    head.rotationPointY = var11;
    head.rotationPointZ = var12;
    head.rotationPointX = var13;
    double[] var22 = var8.getMovementOffsets(0, partialTicks);
    head.rotateAngleY = (updateRotations(var22[0] - var15[0]) * 3.1415927F / 180.0F * 1.0F);
    head.rotateAngleZ = (-updateRotations(var22[0] - var17) * 3.1415927F / 180.0F * 1.0F);
    head.render(p_78088_7_);
    GlStateManager.pushMatrix();
    GlStateManager.translate(0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(-var16 * var14 * 1.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.translate(0.0F, -1.0F, 0.0F);
    body.rotateAngleZ = 0.0F;
    body.render(p_78088_7_);
    
    for (int var23 = 0; var23 < 2; var23++)
    {
      GlStateManager.enableCull();
      float var21 = var9 * 3.1415927F * 2.0F;
      wing.rotateAngleX = (0.125F - (float)Math.cos(var21) * 0.2F);
      wing.rotateAngleY = 0.25F;
      wing.rotateAngleZ = ((float)(Math.sin(var21) + 0.125D) * 0.8F);
      wingTip.rotateAngleZ = (-(float)(Math.sin(var21 + 2.0F) + 0.5D) * 0.75F);
      rearLeg.rotateAngleX = (1.0F + var10 * 0.1F);
      rearLegTip.rotateAngleX = (0.5F + var10 * 0.1F);
      rearFoot.rotateAngleX = (0.75F + var10 * 0.1F);
      frontLeg.rotateAngleX = (1.3F + var10 * 0.1F);
      frontLegTip.rotateAngleX = (-0.5F - var10 * 0.1F);
      frontFoot.rotateAngleX = (0.75F + var10 * 0.1F);
      wing.render(p_78088_7_);
      frontLeg.render(p_78088_7_);
      rearLeg.render(p_78088_7_);
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      
      if (var23 == 0)
      {
        GlStateManager.cullFace(1028);
      }
    }
    
    GlStateManager.popMatrix();
    GlStateManager.cullFace(1029);
    GlStateManager.disableCull();
    float var24 = -(float)Math.sin(var9 * 3.1415927F * 2.0F) * 0.0F;
    var18 = var9 * 3.1415927F * 2.0F;
    var11 = 10.0F;
    var12 = 60.0F;
    var13 = 0.0F;
    var15 = var8.getMovementOffsets(11, partialTicks);
    
    for (int var25 = 0; var25 < 12; var25++)
    {
      var22 = var8.getMovementOffsets(12 + var25, partialTicks);
      var24 = (float)(var24 + Math.sin(var25 * 0.45F + var18) * 0.05000000074505806D);
      spine.rotateAngleY = ((updateRotations(var22[0] - var15[0]) * var14 + 180.0F) * 3.1415927F / 180.0F);
      spine.rotateAngleX = (var24 + (float)(var22[1] - var15[1]) * 3.1415927F / 180.0F * var14 * 5.0F);
      spine.rotateAngleZ = (updateRotations(var22[0] - var17) * 3.1415927F / 180.0F * var14);
      spine.rotationPointY = var11;
      spine.rotationPointZ = var12;
      spine.rotationPointX = var13;
      var11 = (float)(var11 + Math.sin(spine.rotateAngleX) * 10.0D);
      var12 = (float)(var12 - Math.cos(spine.rotateAngleY) * Math.cos(spine.rotateAngleX) * 10.0D);
      var13 = (float)(var13 - Math.sin(spine.rotateAngleY) * Math.cos(spine.rotateAngleX) * 10.0D);
      spine.render(p_78088_7_);
    }
    
    GlStateManager.popMatrix();
  }
  





  private float updateRotations(double p_78214_1_)
  {
    while (p_78214_1_ >= 180.0D)
    {
      p_78214_1_ -= 360.0D;
    }
    
    while (p_78214_1_ < -180.0D)
    {
      p_78214_1_ += 360.0D;
    }
    
    return (float)p_78214_1_;
  }
}
