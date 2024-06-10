/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   7:    */ import net.minecraft.util.ResourceLocation;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public class RenderLightningBolt
/*  11:    */   extends Render
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00001011";
/*  14:    */   
/*  15:    */   public void doRender(EntityLightningBolt par1EntityLightningBolt, double par2, double par4, double par6, float par8, float par9)
/*  16:    */   {
/*  17: 22 */     Tessellator var10 = Tessellator.instance;
/*  18: 23 */     GL11.glDisable(3553);
/*  19: 24 */     GL11.glDisable(2896);
/*  20: 25 */     GL11.glEnable(3042);
/*  21: 26 */     GL11.glBlendFunc(770, 1);
/*  22: 27 */     double[] var11 = new double[8];
/*  23: 28 */     double[] var12 = new double[8];
/*  24: 29 */     double var13 = 0.0D;
/*  25: 30 */     double var15 = 0.0D;
/*  26: 31 */     Random var17 = new Random(par1EntityLightningBolt.boltVertex);
/*  27: 33 */     for (int var18 = 7; var18 >= 0; var18--)
/*  28:    */     {
/*  29: 35 */       var11[var18] = var13;
/*  30: 36 */       var12[var18] = var15;
/*  31: 37 */       var13 += var17.nextInt(11) - 5;
/*  32: 38 */       var15 += var17.nextInt(11) - 5;
/*  33:    */     }
/*  34: 41 */     for (int var45 = 0; var45 < 4; var45++)
/*  35:    */     {
/*  36: 43 */       Random var46 = new Random(par1EntityLightningBolt.boltVertex);
/*  37: 45 */       for (int var19 = 0; var19 < 3; var19++)
/*  38:    */       {
/*  39: 47 */         int var20 = 7;
/*  40: 48 */         int var21 = 0;
/*  41: 50 */         if (var19 > 0) {
/*  42: 52 */           var20 = 7 - var19;
/*  43:    */         }
/*  44: 55 */         if (var19 > 0) {
/*  45: 57 */           var21 = var20 - 2;
/*  46:    */         }
/*  47: 60 */         double var22 = var11[var20] - var13;
/*  48: 61 */         double var24 = var12[var20] - var15;
/*  49: 63 */         for (int var26 = var20; var26 >= var21; var26--)
/*  50:    */         {
/*  51: 65 */           double var27 = var22;
/*  52: 66 */           double var29 = var24;
/*  53: 68 */           if (var19 == 0)
/*  54:    */           {
/*  55: 70 */             var22 += var46.nextInt(11) - 5;
/*  56: 71 */             var24 += var46.nextInt(11) - 5;
/*  57:    */           }
/*  58:    */           else
/*  59:    */           {
/*  60: 75 */             var22 += var46.nextInt(31) - 15;
/*  61: 76 */             var24 += var46.nextInt(31) - 15;
/*  62:    */           }
/*  63: 79 */           var10.startDrawing(5);
/*  64: 80 */           float var31 = 0.5F;
/*  65: 81 */           var10.setColorRGBA_F(0.9F * var31, 0.9F * var31, 1.0F * var31, 0.3F);
/*  66: 82 */           double var32 = 0.1D + var45 * 0.2D;
/*  67: 84 */           if (var19 == 0) {
/*  68: 86 */             var32 *= (var26 * 0.1D + 1.0D);
/*  69:    */           }
/*  70: 89 */           double var34 = 0.1D + var45 * 0.2D;
/*  71: 91 */           if (var19 == 0) {
/*  72: 93 */             var34 *= ((var26 - 1) * 0.1D + 1.0D);
/*  73:    */           }
/*  74: 96 */           for (int var36 = 0; var36 < 5; var36++)
/*  75:    */           {
/*  76: 98 */             double var37 = par2 + 0.5D - var32;
/*  77: 99 */             double var39 = par6 + 0.5D - var32;
/*  78:101 */             if ((var36 == 1) || (var36 == 2)) {
/*  79:103 */               var37 += var32 * 2.0D;
/*  80:    */             }
/*  81:106 */             if ((var36 == 2) || (var36 == 3)) {
/*  82:108 */               var39 += var32 * 2.0D;
/*  83:    */             }
/*  84:111 */             double var41 = par2 + 0.5D - var34;
/*  85:112 */             double var43 = par6 + 0.5D - var34;
/*  86:114 */             if ((var36 == 1) || (var36 == 2)) {
/*  87:116 */               var41 += var34 * 2.0D;
/*  88:    */             }
/*  89:119 */             if ((var36 == 2) || (var36 == 3)) {
/*  90:121 */               var43 += var34 * 2.0D;
/*  91:    */             }
/*  92:124 */             var10.addVertex(var41 + var22, par4 + var26 * 16, var43 + var24);
/*  93:125 */             var10.addVertex(var37 + var27, par4 + (var26 + 1) * 16, var39 + var29);
/*  94:    */           }
/*  95:128 */           var10.draw();
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:133 */     GL11.glDisable(3042);
/* 100:134 */     GL11.glEnable(2896);
/* 101:135 */     GL11.glEnable(3553);
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected ResourceLocation getEntityTexture(EntityLightningBolt par1EntityLightningBolt)
/* 105:    */   {
/* 106:143 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 110:    */   {
/* 111:151 */     return getEntityTexture((EntityLightningBolt)par1Entity);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 115:    */   {
/* 116:162 */     doRender((EntityLightningBolt)par1Entity, par2, par4, par6, par8, par9);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderLightningBolt
 * JD-Core Version:    0.7.0.1
 */