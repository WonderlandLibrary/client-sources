/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ public class ModelChest
/*  4:   */   extends ModelBase
/*  5:   */ {
/*  6: 6 */   public ModelRenderer chestLid = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
/*  7:   */   public ModelRenderer chestBelow;
/*  8:   */   public ModelRenderer chestKnob;
/*  9:   */   private static final String __OBFID = "CL_00000834";
/* 10:   */   
/* 11:   */   public ModelChest()
/* 12:   */   {
/* 13:17 */     this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
/* 14:18 */     this.chestLid.rotationPointX = 1.0F;
/* 15:19 */     this.chestLid.rotationPointY = 7.0F;
/* 16:20 */     this.chestLid.rotationPointZ = 15.0F;
/* 17:21 */     this.chestKnob = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
/* 18:22 */     this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
/* 19:23 */     this.chestKnob.rotationPointX = 8.0F;
/* 20:24 */     this.chestKnob.rotationPointY = 7.0F;
/* 21:25 */     this.chestKnob.rotationPointZ = 15.0F;
/* 22:26 */     this.chestBelow = new ModelRenderer(this, 0, 19).setTextureSize(64, 64);
/* 23:27 */     this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
/* 24:28 */     this.chestBelow.rotationPointX = 1.0F;
/* 25:29 */     this.chestBelow.rotationPointY = 6.0F;
/* 26:30 */     this.chestBelow.rotationPointZ = 1.0F;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void renderAll()
/* 30:   */   {
/* 31:38 */     this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
/* 32:39 */     this.chestLid.render(0.0625F);
/* 33:40 */     this.chestKnob.render(0.0625F);
/* 34:41 */     this.chestBelow.render(0.0625F);
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelChest
 * JD-Core Version:    0.7.0.1
 */