/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.newdawn.slick.opengl.GLUtils;
/*     */ import org.newdawn.slick.opengl.Texture;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.util.BufferedImageUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrueTypeFont
/*     */   implements Font
/*     */ {
/*  28 */   private static final SGL GL = Renderer.get();
/*     */ 
/*     */   
/*  31 */   private IntObject[] charArray = new IntObject[256];
/*     */ 
/*     */   
/*  34 */   private Map customChars = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   private boolean antiAlias;
/*     */ 
/*     */   
/*  40 */   private int fontSize = 0;
/*     */ 
/*     */   
/*  43 */   private int fontHeight = 0;
/*     */ 
/*     */   
/*     */   private Texture fontTexture;
/*     */ 
/*     */   
/*  49 */   private int textureWidth = 512;
/*     */ 
/*     */   
/*  52 */   private int textureHeight = 512;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Font font;
/*     */ 
/*     */ 
/*     */   
/*     */   private FontMetrics fontMetrics;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class IntObject
/*     */   {
/*     */     public int width;
/*     */ 
/*     */ 
/*     */     
/*     */     public int height;
/*     */ 
/*     */ 
/*     */     
/*     */     public int storedX;
/*     */ 
/*     */ 
/*     */     
/*     */     public int storedY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private IntObject() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrueTypeFont(Font font, boolean antiAlias, char[] additionalChars) {
/*  92 */     GLUtils.checkGLContext();
/*     */     
/*  94 */     this.font = font;
/*  95 */     this.fontSize = font.getSize();
/*  96 */     this.antiAlias = antiAlias;
/*     */     
/*  98 */     createSet(additionalChars);
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
/*     */   
/*     */   public TrueTypeFont(Font font, boolean antiAlias) {
/* 112 */     this(font, antiAlias, null);
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
/*     */   private BufferedImage getFontImage(char ch) {
/* 125 */     BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
/*     */     
/* 127 */     Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
/* 128 */     if (this.antiAlias == true) {
/* 129 */       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     }
/*     */     
/* 132 */     g.setFont(this.font);
/* 133 */     this.fontMetrics = g.getFontMetrics();
/* 134 */     int charwidth = this.fontMetrics.charWidth(ch);
/*     */     
/* 136 */     if (charwidth <= 0) {
/* 137 */       charwidth = 1;
/*     */     }
/* 139 */     int charheight = this.fontMetrics.getHeight();
/* 140 */     if (charheight <= 0) {
/* 141 */       charheight = this.fontSize;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
/*     */     
/* 148 */     Graphics2D gt = (Graphics2D)fontImage.getGraphics();
/* 149 */     if (this.antiAlias == true) {
/* 150 */       gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     }
/*     */     
/* 153 */     gt.setFont(this.font);
/*     */     
/* 155 */     gt.setColor(Color.WHITE);
/* 156 */     int charx = 0;
/* 157 */     int chary = 0;
/* 158 */     gt.drawString(String.valueOf(ch), charx, chary + this.fontMetrics.getAscent());
/*     */ 
/*     */     
/* 161 */     return fontImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createSet(char[] customCharsArray) {
/* 172 */     if (customCharsArray != null && customCharsArray.length > 0) {
/* 173 */       this.textureWidth *= 2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 182 */       BufferedImage imgTemp = new BufferedImage(this.textureWidth, this.textureHeight, 2);
/* 183 */       Graphics2D g = (Graphics2D)imgTemp.getGraphics();
/*     */       
/* 185 */       g.setColor(new Color(255, 255, 255, 1));
/* 186 */       g.fillRect(0, 0, this.textureWidth, this.textureHeight);
/*     */       
/* 188 */       int rowHeight = 0;
/* 189 */       int positionX = 0;
/* 190 */       int positionY = 0;
/*     */       
/* 192 */       int customCharsLength = (customCharsArray != null) ? customCharsArray.length : 0;
/*     */       
/* 194 */       for (int i = 0; i < 256 + customCharsLength; i++) {
/*     */ 
/*     */         
/* 197 */         char ch = (i < 256) ? (char)i : customCharsArray[i - 256];
/*     */         
/* 199 */         BufferedImage fontImage = getFontImage(ch);
/*     */         
/* 201 */         IntObject newIntObject = new IntObject();
/*     */         
/* 203 */         newIntObject.width = fontImage.getWidth();
/* 204 */         newIntObject.height = fontImage.getHeight();
/*     */         
/* 206 */         if (positionX + newIntObject.width >= this.textureWidth) {
/* 207 */           positionX = 0;
/* 208 */           positionY += rowHeight;
/* 209 */           rowHeight = 0;
/*     */         } 
/*     */         
/* 212 */         newIntObject.storedX = positionX;
/* 213 */         newIntObject.storedY = positionY;
/*     */         
/* 215 */         if (newIntObject.height > this.fontHeight) {
/* 216 */           this.fontHeight = newIntObject.height;
/*     */         }
/*     */         
/* 219 */         if (newIntObject.height > rowHeight) {
/* 220 */           rowHeight = newIntObject.height;
/*     */         }
/*     */ 
/*     */         
/* 224 */         g.drawImage(fontImage, positionX, positionY, (ImageObserver)null);
/*     */         
/* 226 */         positionX += newIntObject.width;
/*     */         
/* 228 */         if (i < 256) {
/* 229 */           this.charArray[i] = newIntObject;
/*     */         } else {
/* 231 */           this.customChars.put(new Character(ch), newIntObject);
/*     */         } 
/*     */         
/* 234 */         fontImage = null;
/*     */       } 
/*     */       
/* 237 */       this.fontTexture = BufferedImageUtil.getTexture(this.font.toString(), imgTemp);
/*     */     
/*     */     }
/* 240 */     catch (IOException e) {
/* 241 */       System.err.println("Failed to create font.");
/* 242 */       e.printStackTrace();
/*     */     } 
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
/*     */   private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2) {
/* 269 */     float DrawWidth = drawX2 - drawX;
/* 270 */     float DrawHeight = drawY2 - drawY;
/* 271 */     float TextureSrcX = srcX / this.textureWidth;
/* 272 */     float TextureSrcY = srcY / this.textureHeight;
/* 273 */     float SrcWidth = srcX2 - srcX;
/* 274 */     float SrcHeight = srcY2 - srcY;
/* 275 */     float RenderWidth = SrcWidth / this.textureWidth;
/* 276 */     float RenderHeight = SrcHeight / this.textureHeight;
/*     */     
/* 278 */     GL.glTexCoord2f(TextureSrcX, TextureSrcY);
/* 279 */     GL.glVertex2f(drawX, drawY);
/* 280 */     GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
/* 281 */     GL.glVertex2f(drawX, drawY + DrawHeight);
/* 282 */     GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
/* 283 */     GL.glVertex2f(drawX + DrawWidth, drawY + DrawHeight);
/* 284 */     GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
/* 285 */     GL.glVertex2f(drawX + DrawWidth, drawY);
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
/*     */   public int getWidth(String whatchars) {
/* 297 */     int totalwidth = 0;
/* 298 */     IntObject intObject = null;
/* 299 */     int currentChar = 0;
/* 300 */     for (int i = 0; i < whatchars.length(); i++) {
/* 301 */       currentChar = whatchars.charAt(i);
/* 302 */       if (currentChar < 256) {
/* 303 */         intObject = this.charArray[currentChar];
/*     */       } else {
/* 305 */         intObject = (IntObject)this.customChars.get(new Character((char)currentChar));
/*     */       } 
/*     */       
/* 308 */       if (intObject != null)
/* 309 */         totalwidth += intObject.width; 
/*     */     } 
/* 311 */     return totalwidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 320 */     return this.fontHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(String HeightString) {
/* 329 */     return this.fontHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 338 */     return this.fontHeight;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String whatchars, Color color) {
/* 355 */     drawString(x, y, whatchars, color, 0, whatchars.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String whatchars, Color color, int startIndex, int endIndex) {
/* 363 */     color.bind();
/* 364 */     this.fontTexture.bind();
/*     */     
/* 366 */     IntObject intObject = null;
/*     */ 
/*     */     
/* 369 */     GL.glBegin(7);
/*     */     
/* 371 */     int totalwidth = 0;
/* 372 */     for (int i = 0; i < whatchars.length(); i++) {
/* 373 */       int charCurrent = whatchars.charAt(i);
/* 374 */       if (charCurrent < 256) {
/* 375 */         intObject = this.charArray[charCurrent];
/*     */       } else {
/* 377 */         intObject = (IntObject)this.customChars.get(new Character((char)charCurrent));
/*     */       } 
/*     */       
/* 380 */       if (intObject != null) {
/* 381 */         if (i >= startIndex || i <= endIndex) {
/* 382 */           drawQuad(x + totalwidth, y, x + totalwidth + intObject.width, y + intObject.height, intObject.storedX, intObject.storedY, (intObject.storedX + intObject.width), (intObject.storedY + intObject.height));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 388 */         totalwidth += intObject.width;
/*     */       } 
/*     */     } 
/*     */     
/* 392 */     GL.glEnd();
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
/*     */   
/*     */   public void drawString(float x, float y, String whatchars) {
/* 406 */     drawString(x, y, whatchars, Color.white);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\TrueTypeFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */