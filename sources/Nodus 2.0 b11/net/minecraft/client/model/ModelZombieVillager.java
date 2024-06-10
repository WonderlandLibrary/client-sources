/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelZombieVillager
/*  7:   */   extends ModelBiped
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000865";
/* 10:   */   
/* 11:   */   public ModelZombieVillager()
/* 12:   */   {
/* 13:12 */     this(0.0F, 0.0F, false);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ModelZombieVillager(float par1, float par2, boolean par3)
/* 17:   */   {
/* 18:17 */     super(par1, 0.0F, 64, par3 ? 32 : 64);
/* 19:19 */     if (par3)
/* 20:   */     {
/* 21:21 */       this.bipedHead = new ModelRenderer(this, 0, 0);
/* 22:22 */       this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 6, 8, par1);
/* 23:23 */       this.bipedHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/* 24:   */     }
/* 25:   */     else
/* 26:   */     {
/* 27:27 */       this.bipedHead = new ModelRenderer(this);
/* 28:28 */       this.bipedHead.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
/* 29:29 */       this.bipedHead.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, par1);
/* 30:30 */       this.bipedHead.setTextureOffset(24, 32).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, par1);
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int func_82897_a()
/* 35:   */   {
/* 36:36 */     return 10;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 40:   */   {
/* 41:46 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 42:47 */     float var8 = MathHelper.sin(this.onGround * 3.141593F);
/* 43:48 */     float var9 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * 3.141593F);
/* 44:49 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 45:50 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 46:51 */     this.bipedRightArm.rotateAngleY = (-(0.1F - var8 * 0.6F));
/* 47:52 */     this.bipedLeftArm.rotateAngleY = (0.1F - var8 * 0.6F);
/* 48:53 */     this.bipedRightArm.rotateAngleX = -1.570796F;
/* 49:54 */     this.bipedLeftArm.rotateAngleX = -1.570796F;
/* 50:55 */     this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 51:56 */     this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 52:57 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 53:58 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 54:59 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 55:60 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelZombieVillager
 * JD-Core Version:    0.7.0.1
 */