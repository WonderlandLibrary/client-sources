/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class ModelBlaze
/*  7:   */   extends ModelBase
/*  8:   */ {
/*  9: 9 */   private ModelRenderer[] blazeSticks = new ModelRenderer[12];
/* 10:   */   private ModelRenderer blazeHead;
/* 11:   */   private static final String __OBFID = "CL_00000831";
/* 12:   */   
/* 13:   */   public ModelBlaze()
/* 14:   */   {
/* 15:15 */     for (int var1 = 0; var1 < this.blazeSticks.length; var1++)
/* 16:   */     {
/* 17:17 */       this.blazeSticks[var1] = new ModelRenderer(this, 0, 16);
/* 18:18 */       this.blazeSticks[var1].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
/* 19:   */     }
/* 20:21 */     this.blazeHead = new ModelRenderer(this, 0, 0);
/* 21:22 */     this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int func_78104_a()
/* 25:   */   {
/* 26:27 */     return 8;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/* 30:   */   {
/* 31:35 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 32:36 */     this.blazeHead.render(par7);
/* 33:38 */     for (int var8 = 0; var8 < this.blazeSticks.length; var8++) {
/* 34:40 */       this.blazeSticks[var8].render(par7);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/* 39:   */   {
/* 40:51 */     float var8 = par3 * 3.141593F * -0.1F;
/* 41:54 */     for (int var9 = 0; var9 < 4; var9++)
/* 42:   */     {
/* 43:56 */       this.blazeSticks[var9].rotationPointY = (-2.0F + MathHelper.cos((var9 * 2 + par3) * 0.25F));
/* 44:57 */       this.blazeSticks[var9].rotationPointX = (MathHelper.cos(var8) * 9.0F);
/* 45:58 */       this.blazeSticks[var9].rotationPointZ = (MathHelper.sin(var8) * 9.0F);
/* 46:59 */       var8 += 1.0F;
/* 47:   */     }
/* 48:62 */     var8 = 0.7853982F + par3 * 3.141593F * 0.03F;
/* 49:64 */     for (var9 = 4; var9 < 8; var9++)
/* 50:   */     {
/* 51:66 */       this.blazeSticks[var9].rotationPointY = (2.0F + MathHelper.cos((var9 * 2 + par3) * 0.25F));
/* 52:67 */       this.blazeSticks[var9].rotationPointX = (MathHelper.cos(var8) * 7.0F);
/* 53:68 */       this.blazeSticks[var9].rotationPointZ = (MathHelper.sin(var8) * 7.0F);
/* 54:69 */       var8 += 1.0F;
/* 55:   */     }
/* 56:72 */     var8 = 0.4712389F + par3 * 3.141593F * -0.05F;
/* 57:74 */     for (var9 = 8; var9 < 12; var9++)
/* 58:   */     {
/* 59:76 */       this.blazeSticks[var9].rotationPointY = (11.0F + MathHelper.cos((var9 * 1.5F + par3) * 0.5F));
/* 60:77 */       this.blazeSticks[var9].rotationPointX = (MathHelper.cos(var8) * 5.0F);
/* 61:78 */       this.blazeSticks[var9].rotationPointZ = (MathHelper.sin(var8) * 5.0F);
/* 62:79 */       var8 += 1.0F;
/* 63:   */     }
/* 64:82 */     this.blazeHead.rotateAngleY = (par4 / 57.295776F);
/* 65:83 */     this.blazeHead.rotateAngleX = (par5 / 57.295776F);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBlaze
 * JD-Core Version:    0.7.0.1
 */