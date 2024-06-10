/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ public class ModelCow
/*  4:   */   extends ModelQuadruped
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000836";
/*  7:   */   
/*  8:   */   public ModelCow()
/*  9:   */   {
/* 10: 9 */     super(12, 0.0F);
/* 11:10 */     this.head = new ModelRenderer(this, 0, 0);
/* 12:11 */     this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
/* 13:12 */     this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
/* 14:13 */     this.head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
/* 15:14 */     this.head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
/* 16:15 */     this.body = new ModelRenderer(this, 18, 4);
/* 17:16 */     this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
/* 18:17 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 19:18 */     this.body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);
/* 20:19 */     this.leg1.rotationPointX -= 1.0F;
/* 21:20 */     this.leg2.rotationPointX += 1.0F;
/* 22:21 */     this.leg1.rotationPointZ += 0.0F;
/* 23:22 */     this.leg2.rotationPointZ += 0.0F;
/* 24:23 */     this.leg3.rotationPointX -= 1.0F;
/* 25:24 */     this.leg4.rotationPointX += 1.0F;
/* 26:25 */     this.leg3.rotationPointZ -= 1.0F;
/* 27:26 */     this.leg4.rotationPointZ -= 1.0F;
/* 28:27 */     this.field_78151_h += 2.0F;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelCow
 * JD-Core Version:    0.7.0.1
 */