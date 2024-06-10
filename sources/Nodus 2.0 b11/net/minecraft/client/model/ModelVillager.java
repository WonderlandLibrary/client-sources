/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelVillager
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9:   */   public ModelRenderer villagerHead;
/* 10:   */   public ModelRenderer villagerBody;
/* 11:   */   public ModelRenderer villagerArms;
/* 12:   */   public ModelRenderer rightVillagerLeg;
/* 13:   */   public ModelRenderer leftVillagerLeg;
/* 14:   */   public ModelRenderer villagerNose;
/* 15:   */   private static final String __OBFID = "CL_00000864";
/* 16:   */   
/* 17:   */   public ModelVillager(float par1)
/* 18:   */   {
/* 19:27 */     this(par1, 0.0F, 64, 64);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ModelVillager(float par1, float par2, int par3, int par4)
/* 23:   */   {
/* 24:32 */     this.villagerHead = new ModelRenderer(this).setTextureSize(par3, par4);
/* 25:33 */     this.villagerHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/* 26:34 */     this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, par1);
/* 27:35 */     this.villagerNose = new ModelRenderer(this).setTextureSize(par3, par4);
/* 28:36 */     this.villagerNose.setRotationPoint(0.0F, par2 - 2.0F, 0.0F);
/* 29:37 */     this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, par1);
/* 30:38 */     this.villagerHead.addChild(this.villagerNose);
/* 31:39 */     this.villagerBody = new ModelRenderer(this).setTextureSize(par3, par4);
/* 32:40 */     this.villagerBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/* 33:41 */     this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, par1);
/* 34:42 */     this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, par1 + 0.5F);
/* 35:43 */     this.villagerArms = new ModelRenderer(this).setTextureSize(par3, par4);
/* 36:44 */     this.villagerArms.setRotationPoint(0.0F, 0.0F + par2 + 2.0F, 0.0F);
/* 37:45 */     this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, par1);
/* 38:46 */     this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, par1);
/* 39:47 */     this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, par1);
/* 40:48 */     this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4);
/* 41:49 */     this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + par2, 0.0F);
/* 42:50 */     this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
/* 43:51 */     this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4);
/* 44:52 */     this.leftVillagerLeg.mirror = true;
/* 45:53 */     this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + par2, 0.0F);
/* 46:54 */     this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, par1);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 50:   */   {
/* 51:62 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 52:63 */     this.villagerHead.render(par7);
/* 53:64 */     this.villagerBody.render(par7);
/* 54:65 */     this.rightVillagerLeg.render(par7);
/* 55:66 */     this.leftVillagerLeg.render(par7);
/* 56:67 */     this.villagerArms.render(par7);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 60:   */   {
/* 61:77 */     this.villagerHead.rotateAngleY = (par4 / 57.295776F);
/* 62:78 */     this.villagerHead.rotateAngleX = (par5 / 57.295776F);
/* 63:79 */     this.villagerArms.rotationPointY = 3.0F;
/* 64:80 */     this.villagerArms.rotationPointZ = -1.0F;
/* 65:81 */     this.villagerArms.rotateAngleX = -0.75F;
/* 66:82 */     this.rightVillagerLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2 * 0.5F);
/* 67:83 */     this.leftVillagerLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2 * 0.5F);
/* 68:84 */     this.rightVillagerLeg.rotateAngleY = 0.0F;
/* 69:85 */     this.leftVillagerLeg.rotateAngleY = 0.0F;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelVillager
 * JD-Core Version:    0.7.0.1
 */