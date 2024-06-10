/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ public class ModelLargeChest
/*  4:   */   extends ModelChest
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000841";
/*  7:   */   
/*  8:   */   public ModelLargeChest()
/*  9:   */   {
/* 10: 9 */     this.chestLid = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
/* 11:10 */     this.chestLid.addBox(0.0F, -5.0F, -14.0F, 30, 5, 14, 0.0F);
/* 12:11 */     this.chestLid.rotationPointX = 1.0F;
/* 13:12 */     this.chestLid.rotationPointY = 7.0F;
/* 14:13 */     this.chestLid.rotationPointZ = 15.0F;
/* 15:14 */     this.chestKnob = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
/* 16:15 */     this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
/* 17:16 */     this.chestKnob.rotationPointX = 16.0F;
/* 18:17 */     this.chestKnob.rotationPointY = 7.0F;
/* 19:18 */     this.chestKnob.rotationPointZ = 15.0F;
/* 20:19 */     this.chestBelow = new ModelRenderer(this, 0, 19).setTextureSize(128, 64);
/* 21:20 */     this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 30, 10, 14, 0.0F);
/* 22:21 */     this.chestBelow.rotationPointX = 1.0F;
/* 23:22 */     this.chestBelow.rotationPointY = 6.0F;
/* 24:23 */     this.chestBelow.rotationPointZ = 1.0F;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelLargeChest
 * JD-Core Version:    0.7.0.1
 */