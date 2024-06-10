/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.tileentity.TileEntity;
/*   6:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ 
/*  12:    */ public class TileEntityBeaconRenderer
/*  13:    */   extends TileEntitySpecialRenderer
/*  14:    */ {
/*  15: 13 */   private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
/*  16:    */   private static final String __OBFID = "CL_00000962";
/*  17:    */   
/*  18:    */   public void renderTileEntityAt(TileEntityBeacon p_147522_1_, double p_147522_2_, double p_147522_4_, double p_147522_6_, float p_147522_8_)
/*  19:    */   {
/*  20: 18 */     float var9 = p_147522_1_.func_146002_i();
/*  21: 19 */     GL11.glAlphaFunc(516, 0.1F);
/*  22: 21 */     if (var9 > 0.0F)
/*  23:    */     {
/*  24: 23 */       Tessellator var10 = Tessellator.instance;
/*  25: 24 */       bindTexture(field_147523_b);
/*  26: 25 */       GL11.glTexParameterf(3553, 10242, 10497.0F);
/*  27: 26 */       GL11.glTexParameterf(3553, 10243, 10497.0F);
/*  28: 27 */       GL11.glDisable(2896);
/*  29: 28 */       GL11.glDisable(2884);
/*  30: 29 */       GL11.glDisable(3042);
/*  31: 30 */       GL11.glDepthMask(true);
/*  32: 31 */       OpenGlHelper.glBlendFunc(770, 1, 1, 0);
/*  33: 32 */       float var11 = (float)p_147522_1_.getWorldObj().getTotalWorldTime() + p_147522_8_;
/*  34: 33 */       float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);
/*  35: 34 */       byte var13 = 1;
/*  36: 35 */       double var14 = var11 * 0.025D * (1.0D - (var13 & 0x1) * 2.5D);
/*  37: 36 */       var10.startDrawingQuads();
/*  38: 37 */       var10.setColorRGBA(255, 255, 255, 32);
/*  39: 38 */       double var16 = var13 * 0.2D;
/*  40: 39 */       double var18 = 0.5D + Math.cos(var14 + 2.356194490192345D) * var16;
/*  41: 40 */       double var20 = 0.5D + Math.sin(var14 + 2.356194490192345D) * var16;
/*  42: 41 */       double var22 = 0.5D + Math.cos(var14 + 0.7853981633974483D) * var16;
/*  43: 42 */       double var24 = 0.5D + Math.sin(var14 + 0.7853981633974483D) * var16;
/*  44: 43 */       double var26 = 0.5D + Math.cos(var14 + 3.926990816987241D) * var16;
/*  45: 44 */       double var28 = 0.5D + Math.sin(var14 + 3.926990816987241D) * var16;
/*  46: 45 */       double var30 = 0.5D + Math.cos(var14 + 5.497787143782138D) * var16;
/*  47: 46 */       double var32 = 0.5D + Math.sin(var14 + 5.497787143782138D) * var16;
/*  48: 47 */       double var34 = 256.0F * var9;
/*  49: 48 */       double var36 = 0.0D;
/*  50: 49 */       double var38 = 1.0D;
/*  51: 50 */       double var40 = -1.0F + var12;
/*  52: 51 */       double var42 = 256.0F * var9 * (0.5D / var16) + var40;
/*  53: 52 */       var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_ + var34, p_147522_6_ + var20, var38, var42);
/*  54: 53 */       var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_, p_147522_6_ + var20, var38, var40);
/*  55: 54 */       var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_, p_147522_6_ + var24, var36, var40);
/*  56: 55 */       var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_ + var34, p_147522_6_ + var24, var36, var42);
/*  57: 56 */       var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_ + var34, p_147522_6_ + var32, var38, var42);
/*  58: 57 */       var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_, p_147522_6_ + var32, var38, var40);
/*  59: 58 */       var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_, p_147522_6_ + var28, var36, var40);
/*  60: 59 */       var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_ + var34, p_147522_6_ + var28, var36, var42);
/*  61: 60 */       var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_ + var34, p_147522_6_ + var24, var38, var42);
/*  62: 61 */       var10.addVertexWithUV(p_147522_2_ + var22, p_147522_4_, p_147522_6_ + var24, var38, var40);
/*  63: 62 */       var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_, p_147522_6_ + var32, var36, var40);
/*  64: 63 */       var10.addVertexWithUV(p_147522_2_ + var30, p_147522_4_ + var34, p_147522_6_ + var32, var36, var42);
/*  65: 64 */       var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_ + var34, p_147522_6_ + var28, var38, var42);
/*  66: 65 */       var10.addVertexWithUV(p_147522_2_ + var26, p_147522_4_, p_147522_6_ + var28, var38, var40);
/*  67: 66 */       var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_, p_147522_6_ + var20, var36, var40);
/*  68: 67 */       var10.addVertexWithUV(p_147522_2_ + var18, p_147522_4_ + var34, p_147522_6_ + var20, var36, var42);
/*  69: 68 */       var10.draw();
/*  70: 69 */       GL11.glEnable(3042);
/*  71: 70 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  72: 71 */       GL11.glDepthMask(false);
/*  73: 72 */       var10.startDrawingQuads();
/*  74: 73 */       var10.setColorRGBA(255, 255, 255, 32);
/*  75: 74 */       double var44 = 0.2D;
/*  76: 75 */       double var15 = 0.2D;
/*  77: 76 */       double var17 = 0.8D;
/*  78: 77 */       double var19 = 0.2D;
/*  79: 78 */       double var21 = 0.2D;
/*  80: 79 */       double var23 = 0.8D;
/*  81: 80 */       double var25 = 0.8D;
/*  82: 81 */       double var27 = 0.8D;
/*  83: 82 */       double var29 = 256.0F * var9;
/*  84: 83 */       double var31 = 0.0D;
/*  85: 84 */       double var33 = 1.0D;
/*  86: 85 */       double var35 = -1.0F + var12;
/*  87: 86 */       double var37 = 256.0F * var9 + var35;
/*  88: 87 */       var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_ + var29, p_147522_6_ + var15, var33, var37);
/*  89: 88 */       var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_, p_147522_6_ + var15, var33, var35);
/*  90: 89 */       var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_, p_147522_6_ + var19, var31, var35);
/*  91: 90 */       var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_ + var29, p_147522_6_ + var19, var31, var37);
/*  92: 91 */       var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_ + var29, p_147522_6_ + var27, var33, var37);
/*  93: 92 */       var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_, p_147522_6_ + var27, var33, var35);
/*  94: 93 */       var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_, p_147522_6_ + var23, var31, var35);
/*  95: 94 */       var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_ + var29, p_147522_6_ + var23, var31, var37);
/*  96: 95 */       var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_ + var29, p_147522_6_ + var19, var33, var37);
/*  97: 96 */       var10.addVertexWithUV(p_147522_2_ + var17, p_147522_4_, p_147522_6_ + var19, var33, var35);
/*  98: 97 */       var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_, p_147522_6_ + var27, var31, var35);
/*  99: 98 */       var10.addVertexWithUV(p_147522_2_ + var25, p_147522_4_ + var29, p_147522_6_ + var27, var31, var37);
/* 100: 99 */       var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_ + var29, p_147522_6_ + var23, var33, var37);
/* 101:100 */       var10.addVertexWithUV(p_147522_2_ + var21, p_147522_4_, p_147522_6_ + var23, var33, var35);
/* 102:101 */       var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_, p_147522_6_ + var15, var31, var35);
/* 103:102 */       var10.addVertexWithUV(p_147522_2_ + var44, p_147522_4_ + var29, p_147522_6_ + var15, var31, var37);
/* 104:103 */       var10.draw();
/* 105:104 */       GL11.glEnable(2896);
/* 106:105 */       GL11.glEnable(3553);
/* 107:106 */       GL11.glDepthMask(true);
/* 108:    */     }
/* 109:109 */     GL11.glAlphaFunc(516, 0.5F);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 113:    */   {
/* 114:114 */     renderTileEntityAt((TileEntityBeacon)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer
 * JD-Core Version:    0.7.0.1
 */