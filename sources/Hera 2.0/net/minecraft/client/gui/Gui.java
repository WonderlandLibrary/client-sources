/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class Gui
/*     */ {
/*  14 */   public static final ResourceLocation optionsBackground = new ResourceLocation(
/*  15 */       "textures/gui/options_background.png");
/*  16 */   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  17 */   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
/*     */ 
/*     */   
/*     */   protected float zLevel;
/*     */ 
/*     */   
/*     */   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
/*  24 */     if (endX < startX) {
/*  25 */       int i = startX;
/*  26 */       startX = endX;
/*  27 */       endX = i;
/*     */     } 
/*     */     
/*  30 */     drawRect(startX, y, endX + 1, y + 1, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalLine(int x, int startY, int endY, int color) {
/*  37 */     if (endY < startY) {
/*  38 */       int i = startY;
/*  39 */       startY = endY;
/*  40 */       endY = i;
/*     */     } 
/*     */     
/*  43 */     drawRect(x, startY + 1, x + 1, endY, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  51 */     if (left < right) {
/*  52 */       int i = left;
/*  53 */       left = right;
/*  54 */       right = i;
/*     */     } 
/*     */     
/*  57 */     if (top < bottom) {
/*  58 */       int j = top;
/*  59 */       top = bottom;
/*  60 */       bottom = j;
/*     */     } 
/*     */     
/*  63 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  64 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  65 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  66 */     float f2 = (color & 0xFF) / 255.0F;
/*  67 */     Tessellator tessellator = Tessellator.getInstance();
/*  68 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  69 */     GlStateManager.enableBlend();
/*  70 */     GlStateManager.disableTexture2D();
/*  71 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  72 */     GlStateManager.color(f, f1, f2, f3);
/*  73 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  74 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/*  75 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/*  76 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/*  77 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/*  78 */     tessellator.draw();
/*  79 */     GlStateManager.enableTexture2D();
/*  80 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/*  84 */     if (left < right) {
/*  85 */       double i = left;
/*  86 */       left = right;
/*  87 */       right = i;
/*     */     } 
/*  89 */     if (top < bottom) {
/*  90 */       double j = top;
/*  91 */       top = bottom;
/*  92 */       bottom = j;
/*     */     } 
/*  94 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  95 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  96 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  97 */     float f2 = (color & 0xFF) / 255.0F;
/*  98 */     Tessellator tessellator = Tessellator.getInstance();
/*  99 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 100 */     GlStateManager.enableBlend();
/* 101 */     GlStateManager.disableTexture2D();
/* 102 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 103 */     GlStateManager.color(f, f1, f2, f3);
/* 104 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 105 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/* 106 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/* 107 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/* 108 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/* 109 */     tessellator.draw();
/* 110 */     GlStateManager.enableTexture2D();
/* 111 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(double x, double y, double width, double height, Color color) {
/* 116 */     drawRect(x, y, x + width, y + height, color.getRGB());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRectWithShadow(double x, double y, double width, double height, String xPos, String yPos, Color color) {
/* 122 */     double x1 = x + 2.0D;
/* 123 */     double y1 = y + 2.0D;
/*     */     
/* 125 */     if (xPos.equalsIgnoreCase("left"))
/*     */     {
/* 127 */       x1 = x - 2.0D;
/*     */     }
/*     */ 
/*     */     
/* 131 */     if (yPos.equalsIgnoreCase("top"))
/*     */     {
/* 133 */       y1 = y - 2.0D;
/*     */     }
/*     */ 
/*     */     
/* 137 */     drawRect(x1, y1, x1 + width, y1 + height, Color.black.getRGB());
/*     */     
/* 139 */     drawRect(x, y, x + width, y + height, color.getRGB());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRectWithEdge(double x, double y, double width, double height, Color color) {
/* 145 */     drawRect(x, y, x + width, y + height, color.getRGB());
/*     */     
/* 147 */     int c = Color.black.getRGB();
/*     */     
/* 149 */     drawRect(x - 1.0D, y, x, y + height, c);
/*     */     
/* 151 */     drawRect(x + width, y, x + width + 1.0D, y + height, c);
/*     */     
/* 153 */     drawRect(x - 1.0D, y - 1.0D, x + width + 1.0D, y, c);
/*     */     
/* 155 */     drawRect(x - 1.0D, y + height, x + width + 1.0D, y + height + 1.0D, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDiamond(double x, double y, double r, Color color) {
/* 161 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/* 163 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */     
/* 165 */     GlStateManager.disableTexture2D();
/* 166 */     GlStateManager.enableBlend();
/*     */     
/* 168 */     worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/* 170 */     worldRenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/* 171 */     worldRenderer.pos(x, y + r - 1.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*     */     double i;
/* 173 */     for (i = 0.0D; i < r; i += 0.2D) {
/*     */       
/* 175 */       worldRenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/* 176 */       worldRenderer.pos(x + i, y + r - i - 1.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*     */     } 
/*     */ 
/*     */     
/* 180 */     for (i = 0.0D; i < r; i += 0.2D) {
/*     */       
/* 182 */       worldRenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/* 183 */       worldRenderer.pos(x - i, y + r - i - 1.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*     */     } 
/*     */ 
/*     */     
/* 187 */     for (i = 0.0D; i < r; i += 0.2D) {
/*     */       
/* 189 */       worldRenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/* 190 */       worldRenderer.pos(x - i, y - r + i + 1.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*     */     } 
/*     */ 
/*     */     
/* 194 */     for (i = 0.0D; i < r; i += 0.2D) {
/*     */       
/* 196 */       worldRenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/* 197 */       worldRenderer.pos(x + i, y - r + i + 1.0D, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
/*     */     } 
/*     */ 
/*     */     
/* 201 */     tessellator.draw();
/*     */     
/* 203 */     GlStateManager.enableTexture2D();
/* 204 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRedHurt(Color c) {
/* 210 */     ScaledResolution sr = new ScaledResolution(Client.instance.getMc());
/* 211 */     drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), c.getRGB(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/* 221 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/* 222 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/* 223 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/* 224 */     float f3 = (startColor & 0xFF) / 255.0F;
/* 225 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/* 226 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/* 227 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/* 228 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 229 */     GlStateManager.disableTexture2D();
/* 230 */     GlStateManager.enableBlend();
/* 231 */     GlStateManager.disableAlpha();
/* 232 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 233 */     GlStateManager.shadeModel(7425);
/* 234 */     Tessellator tessellator = Tessellator.getInstance();
/* 235 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 236 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 237 */     worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 238 */     worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 239 */     worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 240 */     worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 241 */     tessellator.draw();
/* 242 */     GlStateManager.shadeModel(7424);
/* 243 */     GlStateManager.disableBlend();
/* 244 */     GlStateManager.enableAlpha();
/* 245 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 253 */     fontRendererIn.drawStringWithShadow(text, (x - fontRendererIn.getStringWidth(text) / 2), y, 
/* 254 */         color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 262 */     fontRendererIn.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 270 */     float f = 0.00390625F;
/* 271 */     float f1 = 0.00390625F;
/* 272 */     Tessellator tessellator = Tessellator.getInstance();
/* 273 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 274 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 275 */     worldrenderer.pos((x + 0), (y + height), this.zLevel)
/* 276 */       .tex(((textureX + 0) * f), ((textureY + height) * f1)).endVertex();
/* 277 */     worldrenderer.pos((x + width), (y + height), this.zLevel)
/* 278 */       .tex(((textureX + width) * f), ((textureY + height) * f1))
/* 279 */       .endVertex();
/* 280 */     worldrenderer.pos((x + width), (y + 0), this.zLevel)
/* 281 */       .tex(((textureX + width) * f), ((textureY + 0) * f1)).endVertex();
/* 282 */     worldrenderer.pos((x + 0), (y + 0), this.zLevel)
/* 283 */       .tex(((textureX + 0) * f), ((textureY + 0) * f1)).endVertex();
/* 284 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
/* 292 */     float f = 0.00390625F;
/* 293 */     float f1 = 0.00390625F;
/* 294 */     Tessellator tessellator = Tessellator.getInstance();
/* 295 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 296 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 297 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + maxV), this.zLevel)
/* 298 */       .tex(((minU + 0) * f), ((minV + maxV) * f1)).endVertex();
/* 299 */     worldrenderer.pos((xCoord + maxU), (yCoord + maxV), this.zLevel)
/* 300 */       .tex(((minU + maxU) * f), ((minV + maxV) * f1)).endVertex();
/* 301 */     worldrenderer.pos((xCoord + maxU), (yCoord + 0.0F), this.zLevel)
/* 302 */       .tex(((minU + maxU) * f), ((minV + 0) * f1)).endVertex();
/* 303 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + 0.0F), this.zLevel)
/* 304 */       .tex(((minU + 0) * f), ((minV + 0) * f1)).endVertex();
/* 305 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
/* 314 */     Tessellator tessellator = Tessellator.getInstance();
/* 315 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 316 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 317 */     worldrenderer.pos((xCoord + 0), (yCoord + heightIn), this.zLevel)
/* 318 */       .tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
/* 319 */     worldrenderer.pos((xCoord + widthIn), (yCoord + heightIn), this.zLevel)
/* 320 */       .tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
/* 321 */     worldrenderer.pos((xCoord + widthIn), (yCoord + 0), this.zLevel)
/* 322 */       .tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
/* 323 */     worldrenderer.pos((xCoord + 0), (yCoord + 0), this.zLevel)
/* 324 */       .tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
/* 325 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
/* 334 */     float f = 1.0F / textureWidth;
/* 335 */     float f1 = 1.0F / textureHeight;
/* 336 */     Tessellator tessellator = Tessellator.getInstance();
/* 337 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 338 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 339 */     worldrenderer.pos(x, (y + height), 0.0D)
/* 340 */       .tex((u * f), ((v + height) * f1)).endVertex();
/* 341 */     worldrenderer.pos((x + width), (y + height), 0.0D)
/* 342 */       .tex(((u + width) * f), ((v + height) * f1)).endVertex();
/* 343 */     worldrenderer.pos((x + width), y, 0.0D)
/* 344 */       .tex(((u + width) * f), (v * f1)).endVertex();
/* 345 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 346 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 355 */     float f = 1.0F / tileWidth;
/* 356 */     float f1 = 1.0F / tileHeight;
/* 357 */     Tessellator tessellator = Tessellator.getInstance();
/* 358 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 359 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 360 */     worldrenderer.pos(x, (y + height), 0.0D)
/* 361 */       .tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 362 */     worldrenderer.pos((x + width), (y + height), 0.0D)
/* 363 */       .tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 364 */     worldrenderer.pos((x + width), y, 0.0D)
/* 365 */       .tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 366 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 367 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */