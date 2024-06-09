/*     */ package org.newdawn.slick.util;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
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
/*     */ public class LocatedImage
/*     */ {
/*     */   private Image image;
/*     */   private int x;
/*     */   private int y;
/*  20 */   private Color filter = Color.white;
/*     */ 
/*     */ 
/*     */   
/*     */   private float width;
/*     */ 
/*     */ 
/*     */   
/*     */   private float height;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocatedImage(Image image, int x, int y) {
/*  34 */     this.image = image;
/*  35 */     this.x = x;
/*  36 */     this.y = y;
/*  37 */     this.width = image.getWidth();
/*  38 */     this.height = image.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  47 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  56 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(float height) {
/*  65 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/*  74 */     this.width = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color c) {
/*  83 */     this.filter = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  92 */     return this.filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 101 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/* 110 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 119 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 128 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/* 135 */     this.image.draw(this.x, this.y, this.width, this.height, this.filter);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\LocatedImage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */