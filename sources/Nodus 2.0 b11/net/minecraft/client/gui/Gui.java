/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.util.IIcon;
/*   6:    */ import net.minecraft.util.ResourceLocation;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class Gui
/*  10:    */ {
/*  11: 11 */   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
/*  12: 12 */   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  13: 13 */   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
/*  14:    */   protected static float zLevel;
/*  15:    */   private static final String __OBFID = "CL_00000662";
/*  16:    */   
/*  17:    */   protected void drawHorizontalLine(int par1, int par2, int par3, int par4)
/*  18:    */   {
/*  19: 19 */     if (par2 < par1)
/*  20:    */     {
/*  21: 21 */       int var5 = par1;
/*  22: 22 */       par1 = par2;
/*  23: 23 */       par2 = var5;
/*  24:    */     }
/*  25: 26 */     drawRect(par1, par3, par2 + 1, par3 + 1, par4);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected void drawVerticalLine(int par1, int par2, int par3, int par4)
/*  29:    */   {
/*  30: 31 */     if (par3 < par2)
/*  31:    */     {
/*  32: 33 */       int var5 = par2;
/*  33: 34 */       par2 = par3;
/*  34: 35 */       par3 = var5;
/*  35:    */     }
/*  36: 38 */     drawRect(par1, par2 + 1, par1 + 1, par3, par4);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void drawRect(float par0, int par1, int par2, int par3, int par4)
/*  40:    */   {
/*  41: 48 */     if (par0 < par2)
/*  42:    */     {
/*  43: 50 */       int var5 = (int)par0;
/*  44: 51 */       par0 = par2;
/*  45: 52 */       par2 = var5;
/*  46:    */     }
/*  47: 55 */     if (par1 < par3)
/*  48:    */     {
/*  49: 57 */       int var5 = par1;
/*  50: 58 */       par1 = par3;
/*  51: 59 */       par3 = var5;
/*  52:    */     }
/*  53: 62 */     float var10 = (par4 >> 24 & 0xFF) / 255.0F;
/*  54: 63 */     float var6 = (par4 >> 16 & 0xFF) / 255.0F;
/*  55: 64 */     float var7 = (par4 >> 8 & 0xFF) / 255.0F;
/*  56: 65 */     float var8 = (par4 & 0xFF) / 255.0F;
/*  57: 66 */     Tessellator var9 = Tessellator.instance;
/*  58: 67 */     GL11.glEnable(3042);
/*  59: 68 */     GL11.glDisable(3553);
/*  60: 69 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  61: 70 */     GL11.glColor4f(var6, var7, var8, var10);
/*  62: 71 */     var9.startDrawingQuads();
/*  63: 72 */     var9.addVertex(par0, par3, 0.0D);
/*  64: 73 */     var9.addVertex(par2, par3, 0.0D);
/*  65: 74 */     var9.addVertex(par2, par1, 0.0D);
/*  66: 75 */     var9.addVertex(par0, par1, 0.0D);
/*  67: 76 */     var9.draw();
/*  68: 77 */     GL11.glEnable(3553);
/*  69: 78 */     GL11.glDisable(3042);
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
/*  73:    */   {
/*  74: 86 */     float var7 = (par5 >> 24 & 0xFF) / 255.0F;
/*  75: 87 */     float var8 = (par5 >> 16 & 0xFF) / 255.0F;
/*  76: 88 */     float var9 = (par5 >> 8 & 0xFF) / 255.0F;
/*  77: 89 */     float var10 = (par5 & 0xFF) / 255.0F;
/*  78: 90 */     float var11 = (par6 >> 24 & 0xFF) / 255.0F;
/*  79: 91 */     float var12 = (par6 >> 16 & 0xFF) / 255.0F;
/*  80: 92 */     float var13 = (par6 >> 8 & 0xFF) / 255.0F;
/*  81: 93 */     float var14 = (par6 & 0xFF) / 255.0F;
/*  82: 94 */     GL11.glDisable(3553);
/*  83: 95 */     GL11.glEnable(3042);
/*  84: 96 */     GL11.glDisable(3008);
/*  85: 97 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  86: 98 */     GL11.glShadeModel(7425);
/*  87: 99 */     Tessellator var15 = Tessellator.instance;
/*  88:100 */     var15.startDrawingQuads();
/*  89:101 */     var15.setColorRGBA_F(var8, var9, var10, var7);
/*  90:102 */     var15.addVertex(par3, par2, zLevel);
/*  91:103 */     var15.addVertex(par1, par2, zLevel);
/*  92:104 */     var15.setColorRGBA_F(var12, var13, var14, var11);
/*  93:105 */     var15.addVertex(par1, par4, zLevel);
/*  94:106 */     var15.addVertex(par3, par4, zLevel);
/*  95:107 */     var15.draw();
/*  96:108 */     GL11.glShadeModel(7424);
/*  97:109 */     GL11.glDisable(3042);
/*  98:110 */     GL11.glEnable(3008);
/*  99:111 */     GL11.glEnable(3553);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
/* 103:    */   {
/* 104:119 */     par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
/* 108:    */   {
/* 109:127 */     par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
/* 113:    */   {
/* 114:135 */     float var7 = 0.0039063F;
/* 115:136 */     float var8 = 0.0039063F;
/* 116:137 */     Tessellator var9 = Tessellator.instance;
/* 117:138 */     var9.startDrawingQuads();
/* 118:139 */     var9.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
/* 119:140 */     var9.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
/* 120:141 */     var9.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
/* 121:142 */     var9.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
/* 122:143 */     var9.draw();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void drawTexturedModelRectFromIcon(int par1, int par2, IIcon par3Icon, int par4, int par5)
/* 126:    */   {
/* 127:148 */     Tessellator var6 = Tessellator.instance;
/* 128:149 */     var6.startDrawingQuads();
/* 129:150 */     var6.addVertexWithUV(par1 + 0, par2 + par5, zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
/* 130:151 */     var6.addVertexWithUV(par1 + par4, par2 + par5, zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
/* 131:152 */     var6.addVertexWithUV(par1 + par4, par2 + 0, zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
/* 132:153 */     var6.addVertexWithUV(par1 + 0, par2 + 0, zLevel, par3Icon.getMinU(), par3Icon.getMinV());
/* 133:154 */     var6.draw();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void func_146110_a(int p_146110_0_, int p_146110_1_, float p_146110_2_, float p_146110_3_, int p_146110_4_, int p_146110_5_, float p_146110_6_, float p_146110_7_)
/* 137:    */   {
/* 138:159 */     float var8 = 1.0F / p_146110_6_;
/* 139:160 */     float var9 = 1.0F / p_146110_7_;
/* 140:161 */     Tessellator var10 = Tessellator.instance;
/* 141:162 */     var10.startDrawingQuads();
/* 142:163 */     var10.addVertexWithUV(p_146110_0_, p_146110_1_ + p_146110_5_, 0.0D, p_146110_2_ * var8, (p_146110_3_ + p_146110_5_) * var9);
/* 143:164 */     var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_ + p_146110_5_, 0.0D, (p_146110_2_ + p_146110_4_) * var8, (p_146110_3_ + p_146110_5_) * var9);
/* 144:165 */     var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_, 0.0D, (p_146110_2_ + p_146110_4_) * var8, p_146110_3_ * var9);
/* 145:166 */     var10.addVertexWithUV(p_146110_0_, p_146110_1_, 0.0D, p_146110_2_ * var8, p_146110_3_ * var9);
/* 146:167 */     var10.draw();
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.Gui
 * JD-Core Version:    0.7.0.1
 */