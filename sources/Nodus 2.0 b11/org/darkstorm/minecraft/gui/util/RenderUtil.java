/*   1:    */ package org.darkstorm.minecraft.gui.util;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Point;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.GuiScreen;
/*   7:    */ import net.minecraft.client.gui.ScaledResolution;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.client.settings.GameSettings;
/*  10:    */ import org.lwjgl.input.Mouse;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class RenderUtil
/*  14:    */ {
/*  15:    */   public static void scissorBox(int x, int y, int xend, int yend)
/*  16:    */   {
/*  17: 51 */     int width = xend - x;
/*  18: 52 */     int height = yend - y;
/*  19: 53 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
/*  20: 54 */     int factor = sr.getScaleFactor();
/*  21: 55 */     int bottomY = GuiScreen.height - yend;
/*  22: 56 */     GL11.glScissor(x * factor, bottomY * factor, width * factor, height * factor);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void setupLineSmooth()
/*  26:    */   {
/*  27: 60 */     GL11.glEnable(3042);
/*  28: 61 */     GL11.glDisable(2896);
/*  29: 62 */     GL11.glDisable(2929);
/*  30: 63 */     GL11.glEnable(2848);
/*  31: 64 */     GL11.glDisable(3553);
/*  32: 65 */     GL11.glHint(3154, 4354);
/*  33: 66 */     GL11.glBlendFunc(770, 771);
/*  34: 67 */     GL11.glEnable(32925);
/*  35: 68 */     GL11.glEnable(32926);
/*  36: 69 */     GL11.glShadeModel(7425);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, float thickness)
/*  40:    */   {
/*  41: 73 */     GL11.glPushMatrix();
/*  42: 74 */     setupLineSmooth();
/*  43: 75 */     GL11.glLineWidth(thickness);
/*  44: 76 */     GL11.glBegin(1);
/*  45: 77 */     GL11.glVertex3d(startX, startY, startZ);
/*  46: 78 */     GL11.glVertex3d(endX, endY, endZ);
/*  47: 79 */     GL11.glEnd();
/*  48: 80 */     GL11.glDisable(3042);
/*  49: 81 */     GL11.glEnable(3553);
/*  50: 82 */     GL11.glDisable(2848);
/*  51: 83 */     GL11.glEnable(2896);
/*  52: 84 */     GL11.glEnable(2929);
/*  53: 85 */     GL11.glDisable(32925);
/*  54: 86 */     GL11.glDisable(32926);
/*  55: 87 */     GL11.glPopMatrix();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
/*  59:    */   {
/*  60: 91 */     float var7 = 0.0039063F;
/*  61: 92 */     float var8 = 0.0039063F;
/*  62: 93 */     Tessellator var9 = Tessellator.instance;
/*  63: 94 */     var9.startDrawingQuads();
/*  64: 95 */     var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (par3 + 0) * var7, (par4 + par6) * var8);
/*  65: 96 */     var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (par3 + par5) * var7, (par4 + par6) * var8);
/*  66: 97 */     var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (par3 + par5) * var7, (par4 + 0) * var8);
/*  67: 98 */     var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (par3 + 0) * var7, (par4 + 0) * var8);
/*  68: 99 */     var9.draw();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void drawTexturedModalRect(int textureId, int posX, int posY, int width, int height)
/*  72:    */   {
/*  73:103 */     double halfHeight = height / 2;
/*  74:104 */     double halfWidth = width / 2;
/*  75:    */     
/*  76:106 */     GL11.glDisable(2884);
/*  77:107 */     GL11.glBindTexture(3553, textureId);
/*  78:108 */     GL11.glPushMatrix();
/*  79:109 */     GL11.glTranslated(posX + halfWidth, posY + halfHeight, 0.0D);
/*  80:110 */     GL11.glScalef(width, height, 0.0F);
/*  81:111 */     GL11.glEnable(3042);
/*  82:112 */     GL11.glBlendFunc(770, 771);
/*  83:113 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  84:114 */     GL11.glBegin(4);
/*  85:115 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/*  86:116 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  87:117 */     GL11.glVertex2d(1.0D, 1.0D);
/*  88:118 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  89:119 */     GL11.glVertex2d(-1.0D, 1.0D);
/*  90:120 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  91:121 */     GL11.glVertex2d(-1.0D, -1.0D);
/*  92:122 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  93:123 */     GL11.glVertex2d(-1.0D, -1.0D);
/*  94:124 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  95:125 */     GL11.glVertex2d(1.0D, -1.0D);
/*  96:126 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  97:127 */     GL11.glVertex2d(1.0D, 1.0D);
/*  98:128 */     GL11.glEnd();
/*  99:    */     
/* 100:130 */     GL11.glDisable(3042);
/* 101:131 */     GL11.glBindTexture(3553, 0);
/* 102:132 */     GL11.glPopMatrix();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static int interpolateColor(int rgba1, int rgba2, float percent)
/* 106:    */   {
/* 107:136 */     int r1 = rgba1 & 0xFF;int g1 = rgba1 >> 8 & 0xFF;int b1 = rgba1 >> 16 & 0xFF;int a1 = rgba1 >> 24 & 0xFF;
/* 108:137 */     int r2 = rgba2 & 0xFF;int g2 = rgba2 >> 8 & 0xFF;int b2 = rgba2 >> 16 & 0xFF;int a2 = rgba2 >> 24 & 0xFF;
/* 109:    */     
/* 110:139 */     int r = (int)(r1 < r2 ? r1 + (r2 - r1) * percent : r2 + (r1 - r2) * percent);
/* 111:140 */     int g = (int)(g1 < g2 ? g1 + (g2 - g1) * percent : g2 + (g1 - g2) * percent);
/* 112:141 */     int b = (int)(b1 < b2 ? b1 + (b2 - b1) * percent : b2 + (b1 - b2) * percent);
/* 113:142 */     int a = (int)(a1 < a2 ? a1 + (a2 - a1) * percent : a2 + (a1 - a2) * percent);
/* 114:    */     
/* 115:144 */     return r | g << 8 | b << 16 | a << 24;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void setColor(Color c)
/* 119:    */   {
/* 120:148 */     GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static Color toColor(int rgba)
/* 124:    */   {
/* 125:152 */     int r = rgba & 0xFF;int g = rgba >> 8 & 0xFF;int b = rgba >> 16 & 0xFF;int a = rgba >> 24 & 0xFF;
/* 126:153 */     return new Color(r, g, b, a);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static int toRGBA(Color c)
/* 130:    */   {
/* 131:157 */     return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void setColor(int rgba)
/* 135:    */   {
/* 136:161 */     int r = rgba & 0xFF;int g = rgba >> 8 & 0xFF;int b = rgba >> 16 & 0xFF;int a = rgba >> 24 & 0xFF;
/* 137:162 */     GL11.glColor4b((byte)r, (byte)g, (byte)b, (byte)a);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static Point calculateMouseLocation()
/* 141:    */   {
/* 142:166 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 143:167 */     int scale = minecraft.gameSettings.guiScale;
/* 144:168 */     if (scale == 0) {
/* 145:169 */       scale = 1000;
/* 146:    */     }
/* 147:170 */     int scaleFactor = 0;
/* 148:171 */     while ((scaleFactor < scale) && (minecraft.displayWidth / (scaleFactor + 1) >= 320) && (minecraft.displayHeight / (scaleFactor + 1) >= 240)) {
/* 149:172 */       scaleFactor++;
/* 150:    */     }
/* 151:173 */     return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.util.RenderUtil
 * JD-Core Version:    0.7.0.1
 */