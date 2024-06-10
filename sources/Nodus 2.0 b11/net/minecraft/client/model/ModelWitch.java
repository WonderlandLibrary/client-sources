/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelWitch
/*  7:   */   extends ModelVillager
/*  8:   */ {
/*  9:   */   public boolean field_82900_g;
/* 10: 9 */   private ModelRenderer field_82901_h = new ModelRenderer(this).setTextureSize(64, 128);
/* 11:   */   private ModelRenderer witchHat;
/* 12:   */   private static final String __OBFID = "CL_00000866";
/* 13:   */   
/* 14:   */   public ModelWitch(float par1)
/* 15:   */   {
/* 16:15 */     super(par1, 0.0F, 64, 128);
/* 17:16 */     this.field_82901_h.setRotationPoint(0.0F, -2.0F, 0.0F);
/* 18:17 */     this.field_82901_h.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
/* 19:18 */     this.villagerNose.addChild(this.field_82901_h);
/* 20:19 */     this.witchHat = new ModelRenderer(this).setTextureSize(64, 128);
/* 21:20 */     this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
/* 22:21 */     this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
/* 23:22 */     this.villagerHead.addChild(this.witchHat);
/* 24:23 */     ModelRenderer var2 = new ModelRenderer(this).setTextureSize(64, 128);
/* 25:24 */     var2.setRotationPoint(1.75F, -4.0F, 2.0F);
/* 26:25 */     var2.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
/* 27:26 */     var2.rotateAngleX = -0.05235988F;
/* 28:27 */     var2.rotateAngleZ = 0.0261799F;
/* 29:28 */     this.witchHat.addChild(var2);
/* 30:29 */     ModelRenderer var3 = new ModelRenderer(this).setTextureSize(64, 128);
/* 31:30 */     var3.setRotationPoint(1.75F, -4.0F, 2.0F);
/* 32:31 */     var3.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
/* 33:32 */     var3.rotateAngleX = -0.1047198F;
/* 34:33 */     var3.rotateAngleZ = 0.05235988F;
/* 35:34 */     var2.addChild(var3);
/* 36:35 */     ModelRenderer var4 = new ModelRenderer(this).setTextureSize(64, 128);
/* 37:36 */     var4.setRotationPoint(1.75F, -2.0F, 2.0F);
/* 38:37 */     var4.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
/* 39:38 */     var4.rotateAngleX = -0.2094395F;
/* 40:39 */     var4.rotateAngleZ = 0.1047198F;
/* 41:40 */     var3.addChild(var4);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 45:   */   {
/* 46:50 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 47:51 */     this.villagerNose.offsetX = (this.villagerNose.offsetY = this.villagerNose.offsetZ = 0.0F);
/* 48:52 */     float var8 = 0.01F * (par7Entity.getEntityId() % 10);
/* 49:53 */     this.villagerNose.rotateAngleX = (MathHelper.sin(par7Entity.ticksExisted * var8) * 4.5F * 3.141593F / 180.0F);
/* 50:54 */     this.villagerNose.rotateAngleY = 0.0F;
/* 51:55 */     this.villagerNose.rotateAngleZ = (MathHelper.cos(par7Entity.ticksExisted * var8) * 2.5F * 3.141593F / 180.0F);
/* 52:57 */     if (this.field_82900_g)
/* 53:   */     {
/* 54:59 */       this.villagerNose.rotateAngleX = -0.9F;
/* 55:60 */       this.villagerNose.offsetZ = -0.09375F;
/* 56:61 */       this.villagerNose.offsetY = 0.1875F;
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelWitch
 * JD-Core Version:    0.7.0.1
 */