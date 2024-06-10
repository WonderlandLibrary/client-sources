/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelZombie
/*  7:   */   extends ModelBiped
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000869";
/* 10:   */   
/* 11:   */   public ModelZombie()
/* 12:   */   {
/* 13:12 */     this(0.0F, false);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected ModelZombie(float par1, float par2, int par3, int par4)
/* 17:   */   {
/* 18:17 */     super(par1, par2, par3, par4);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ModelZombie(float par1, boolean par2)
/* 22:   */   {
/* 23:22 */     super(par1, 0.0F, 64, par2 ? 32 : 64);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 27:   */   {
/* 28:32 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 29:33 */     float var8 = MathHelper.sin(this.onGround * 3.141593F);
/* 30:34 */     float var9 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * 3.141593F);
/* 31:35 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 32:36 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 33:37 */     this.bipedRightArm.rotateAngleY = (-(0.1F - var8 * 0.6F));
/* 34:38 */     this.bipedLeftArm.rotateAngleY = (0.1F - var8 * 0.6F);
/* 35:39 */     this.bipedRightArm.rotateAngleX = -1.570796F;
/* 36:40 */     this.bipedLeftArm.rotateAngleX = -1.570796F;
/* 37:41 */     this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 38:42 */     this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
/* 39:43 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 40:44 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 41:45 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 42:46 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelZombie
 * JD-Core Version:    0.7.0.1
 */