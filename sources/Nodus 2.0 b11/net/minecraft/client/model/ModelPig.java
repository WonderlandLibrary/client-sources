/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ public class ModelPig
/*  4:   */   extends ModelQuadruped
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000849";
/*  7:   */   
/*  8:   */   public ModelPig()
/*  9:   */   {
/* 10: 9 */     this(0.0F);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ModelPig(float par1)
/* 14:   */   {
/* 15:14 */     super(6, par1);
/* 16:15 */     this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, par1);
/* 17:16 */     this.field_78145_g = 4.0F;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelPig
 * JD-Core Version:    0.7.0.1
 */