/*     */ package me.eagler.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderHelper
/*     */ {
/*     */   public void renderLine(double x, double y, double z, double x2, double y2, double z2, float lineWidth, Color c) {
/*  21 */     GlStateManager.pushMatrix();
/*     */     
/*  23 */     GlStateManager.depthMask(false);
/*  24 */     GlStateManager.disableTexture2D();
/*  25 */     GlStateManager.disableLighting();
/*  26 */     GlStateManager.disableCull();
/*  27 */     GlStateManager.disableBlend();
/*     */     
/*  29 */     GlStateManager.disableDepth();
/*     */     
/*  31 */     Tessellator tessellator = Tessellator.getInstance();
/*  32 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */     
/*  34 */     worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/*  36 */     GL11.glLineWidth(lineWidth);
/*     */     
/*  38 */     worldRenderer.pos(x, y, z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  39 */     worldRenderer.pos(x2, y2, z2).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/*  41 */     tessellator.draw();
/*     */     
/*  43 */     GlStateManager.enableDepth();
/*     */     
/*  45 */     GlStateManager.depthMask(true);
/*  46 */     GlStateManager.enableTexture2D();
/*  47 */     GlStateManager.enableLighting();
/*  48 */     GlStateManager.enableCull();
/*  49 */     GlStateManager.enableBlend();
/*     */     
/*  51 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBox(double x, double y, double z, float width, float height, Color c) {
/*  57 */     float halfwidth = width / 2.0F;
/*  58 */     float halfheight = height / 2.0F;
/*     */     
/*  60 */     GlStateManager.pushMatrix();
/*     */     
/*  62 */     GlStateManager.depthMask(false);
/*  63 */     GlStateManager.disableTexture2D();
/*  64 */     GlStateManager.disableLighting();
/*  65 */     GlStateManager.disableCull();
/*  66 */     GlStateManager.disableBlend();
/*     */     
/*  68 */     GlStateManager.disableDepth();
/*     */     
/*  70 */     Tessellator tessellator = Tessellator.getInstance();
/*  71 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */     
/*  73 */     GlStateManager.enableBlend();
/*  74 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */     
/*  76 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/*  78 */     y++;
/*     */     
/*  80 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/*  81 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  82 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/*  83 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  84 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/*  85 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  86 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/*  87 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/*  89 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/*  90 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  91 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/*  92 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  93 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/*  94 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*  95 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/*  96 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/*  98 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/*  99 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 100 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/* 101 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 102 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/* 103 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 104 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 105 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 107 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 108 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 109 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/* 110 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 111 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/* 112 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 113 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 114 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 116 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/* 117 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 118 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/* 119 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 120 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/* 121 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 122 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/* 123 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 125 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 126 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 127 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 128 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 129 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 130 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 131 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 132 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 134 */     tessellator.draw();
/*     */     
/* 136 */     GlStateManager.enableDepth();
/*     */     
/* 138 */     GlStateManager.depthMask(true);
/* 139 */     GlStateManager.enableTexture2D();
/* 140 */     GlStateManager.enableLighting();
/* 141 */     GlStateManager.enableCull();
/* 142 */     GlStateManager.enableBlend();
/*     */     
/* 144 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOutlines(double x, double y, double z, float width, float height, Color c) {
/* 157 */     float halfwidth = width / 2.0F;
/* 158 */     float halfheight = height / 2.0F;
/*     */     
/* 160 */     GlStateManager.pushMatrix();
/*     */     
/* 162 */     GlStateManager.depthMask(false);
/* 163 */     GlStateManager.disableTexture2D();
/* 164 */     GlStateManager.disableLighting();
/* 165 */     GlStateManager.disableCull();
/* 166 */     GlStateManager.disableBlend();
/*     */     
/* 168 */     GlStateManager.disableDepth();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     Tessellator tessellator = Tessellator.getInstance();
/* 176 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */ 
/*     */ 
/*     */     
/* 180 */     worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     y++;
/*     */ 
/*     */ 
/*     */     
/* 206 */     GL11.glLineWidth(1.2F);
/*     */     
/* 208 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 209 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 210 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/* 211 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 213 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 214 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 215 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/* 216 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 218 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 219 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 220 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/* 221 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 223 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 224 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 225 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/* 226 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */ 
/*     */ 
/*     */     
/* 230 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 231 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 232 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 233 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 235 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 236 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 237 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 238 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 240 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 241 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 242 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 243 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 245 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 246 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 247 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 248 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */ 
/*     */ 
/*     */     
/* 252 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/* 253 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 254 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/* 255 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 257 */     worldRenderer.pos(x - halfwidth, y + halfheight, z - halfwidth)
/* 258 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 259 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/* 260 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 262 */     worldRenderer.pos(x + halfwidth, y + halfheight, z - halfwidth)
/* 263 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 264 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/* 265 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 267 */     worldRenderer.pos(x - halfwidth, y + halfheight, z + halfwidth)
/* 268 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 269 */     worldRenderer.pos(x + halfwidth, y + halfheight, z + halfwidth)
/* 270 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 272 */     tessellator.draw();
/*     */     
/* 274 */     GlStateManager.enableDepth();
/*     */     
/* 276 */     GlStateManager.depthMask(true);
/* 277 */     GlStateManager.enableTexture2D();
/* 278 */     GlStateManager.enableLighting();
/* 279 */     GlStateManager.enableCull();
/* 280 */     GlStateManager.enableBlend();
/*     */     
/* 282 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBoxWithOutline(double x, double y, double z, float width, float height, Color c) {
/* 290 */     renderBox(x, y, z, width, height, c);
/* 291 */     renderOutlines(x, y, z, width, height, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderHealthBox(EntityPlayer entity, double x, double y, double z, float width, float height, Color c) {
/* 297 */     float halfwidth = width / 2.0F;
/* 298 */     float halfheight = height / 2.0F;
/*     */     
/* 300 */     GlStateManager.pushMatrix();
/*     */     
/* 302 */     GlStateManager.depthMask(false);
/* 303 */     GlStateManager.disableTexture2D();
/* 304 */     GlStateManager.disableLighting();
/* 305 */     GlStateManager.disableCull();
/* 306 */     GlStateManager.disableBlend();
/*     */     
/* 308 */     GlStateManager.disableDepth();
/*     */     
/* 310 */     Tessellator tessellator = Tessellator.getInstance();
/* 311 */     WorldRenderer worldRenderer = tessellator.getWorldRenderer();
/*     */     
/* 313 */     GlStateManager.enableBlend();
/* 314 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */     
/* 316 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*     */     
/* 318 */     y++;
/*     */     
/* 320 */     float maxHeight = (float)(y - halfheight) + entity.getHealth() / 10.0F;
/*     */     
/* 322 */     x = x + halfwidth + 0.699999988079071D;
/*     */     
/* 324 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 325 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 326 */     worldRenderer.pos(x - halfwidth, maxHeight, z + halfwidth)
/* 327 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 328 */     worldRenderer.pos(x + halfwidth, maxHeight, z + halfwidth)
/* 329 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 330 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 331 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 333 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 334 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 335 */     worldRenderer.pos(x - halfwidth, maxHeight, z - halfwidth)
/* 336 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 337 */     worldRenderer.pos(x + halfwidth, maxHeight, z - halfwidth)
/* 338 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 339 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 340 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 342 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 343 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 344 */     worldRenderer.pos(x - halfwidth, maxHeight, z - halfwidth)
/* 345 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 346 */     worldRenderer.pos(x - halfwidth, maxHeight, z + halfwidth)
/* 347 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 348 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 349 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 351 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 352 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 353 */     worldRenderer.pos(x + halfwidth, maxHeight, z - halfwidth)
/* 354 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 355 */     worldRenderer.pos(x + halfwidth, maxHeight, z + halfwidth)
/* 356 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 357 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 358 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 360 */     worldRenderer.pos(x + halfwidth, maxHeight, z - halfwidth)
/* 361 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 362 */     worldRenderer.pos(x + halfwidth, maxHeight, z + halfwidth)
/* 363 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 364 */     worldRenderer.pos(x - halfwidth, maxHeight, z + halfwidth)
/* 365 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 366 */     worldRenderer.pos(x - halfwidth, maxHeight, z - halfwidth)
/* 367 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 369 */     worldRenderer.pos(x + halfwidth, y - halfheight, z - halfwidth)
/* 370 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 371 */     worldRenderer.pos(x + halfwidth, y - halfheight, z + halfwidth)
/* 372 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 373 */     worldRenderer.pos(x - halfwidth, y - halfheight, z + halfwidth)
/* 374 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/* 375 */     worldRenderer.pos(x - halfwidth, y - halfheight, z - halfwidth)
/* 376 */       .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
/*     */     
/* 378 */     tessellator.draw();
/*     */     
/* 380 */     GlStateManager.enableDepth();
/*     */     
/* 382 */     GlStateManager.depthMask(true);
/* 383 */     GlStateManager.enableTexture2D();
/* 384 */     GlStateManager.enableLighting();
/* 385 */     GlStateManager.enableCull();
/* 386 */     GlStateManager.enableBlend();
/*     */     
/* 388 */     GlStateManager.popMatrix();
/*     */     
/* 390 */     renderOutlines(x, y - 1.0D, z, width, height, c);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagle\\utils\RenderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */