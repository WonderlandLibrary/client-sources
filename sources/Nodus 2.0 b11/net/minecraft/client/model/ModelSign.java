/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ public class ModelSign
/*  4:   */   extends ModelBase
/*  5:   */ {
/*  6: 6 */   public ModelRenderer signBoard = new ModelRenderer(this, 0, 0);
/*  7:   */   public ModelRenderer signStick;
/*  8:   */   private static final String __OBFID = "CL_00000854";
/*  9:   */   
/* 10:   */   public ModelSign()
/* 11:   */   {
/* 12:14 */     this.signBoard.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
/* 13:15 */     this.signStick = new ModelRenderer(this, 0, 14);
/* 14:16 */     this.signStick.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void renderSign()
/* 18:   */   {
/* 19:24 */     this.signBoard.render(0.0625F);
/* 20:25 */     this.signStick.render(0.0625F);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelSign
 * JD-Core Version:    0.7.0.1
 */