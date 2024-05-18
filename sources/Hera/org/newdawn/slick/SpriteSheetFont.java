/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpriteSheetFont
/*     */   implements Font
/*     */ {
/*     */   private SpriteSheet font;
/*     */   private char startingCharacter;
/*     */   private int charWidth;
/*     */   private int charHeight;
/*     */   private int horizontalCount;
/*     */   private int numChars;
/*     */   
/*     */   public SpriteSheetFont(SpriteSheet font, char startingCharacter) {
/*  44 */     this.font = font;
/*  45 */     this.startingCharacter = startingCharacter;
/*  46 */     this.horizontalCount = font.getHorizontalCount();
/*  47 */     int verticalCount = font.getVerticalCount();
/*  48 */     this.charWidth = font.getWidth() / this.horizontalCount;
/*  49 */     this.charHeight = font.getHeight() / verticalCount;
/*  50 */     this.numChars = this.horizontalCount * verticalCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text) {
/*  57 */     drawString(x, y, text, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col) {
/*  64 */     drawString(x, y, text, col, 0, text.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex) {
/*     */     try {
/*  72 */       byte[] data = text.getBytes("US-ASCII");
/*  73 */       for (int i = 0; i < data.length; i++) {
/*  74 */         int index = data[i] - this.startingCharacter;
/*  75 */         if (index < this.numChars) {
/*  76 */           int xPos = index % this.horizontalCount;
/*  77 */           int yPos = index / this.horizontalCount;
/*     */           
/*  79 */           if (i >= startIndex || i <= endIndex) {
/*  80 */             this.font.getSprite(xPos, yPos).draw(x + (i * this.charWidth), y, col);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*  85 */     } catch (UnsupportedEncodingException e) {
/*     */       
/*  87 */       Log.error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(String text) {
/*  95 */     return this.charHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/* 102 */     return this.charWidth * text.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineHeight() {
/* 109 */     return this.charHeight;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\SpriteSheetFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */