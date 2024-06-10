/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ 
/*  5:   */ public class ModelSkeletonHead
/*  6:   */   extends ModelBase
/*  7:   */ {
/*  8:   */   public ModelRenderer skeletonHead;
/*  9:   */   private static final String __OBFID = "CL_00000856";
/* 10:   */   
/* 11:   */   public ModelSkeletonHead()
/* 12:   */   {
/* 13:12 */     this(0, 35, 64, 64);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ModelSkeletonHead(int par1, int par2, int par3, int par4)
/* 17:   */   {
/* 18:17 */     this.textureWidth = par3;
/* 19:18 */     this.textureHeight = par4;
/* 20:19 */     this.skeletonHead = new ModelRenderer(this, par1, par2);
/* 21:20 */     this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
/* 22:21 */     this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 26:   */   {
/* 27:29 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 28:30 */     this.skeletonHead.render(par7);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 32:   */   {
/* 33:40 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
/* 34:41 */     this.skeletonHead.rotateAngleY = (par4 / 57.295776F);
/* 35:42 */     this.skeletonHead.rotateAngleX = (par5 / 57.295776F);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSkeletonHead
 * JD-Core Version:    0.7.0.1
 */