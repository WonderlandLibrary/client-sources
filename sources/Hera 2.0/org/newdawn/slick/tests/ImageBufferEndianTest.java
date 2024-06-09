/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.ImageBuffer;
/*     */ import org.newdawn.slick.SlickException;
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
/*     */ public class ImageBufferEndianTest
/*     */   extends BasicGame
/*     */ {
/*     */   private ImageBuffer redImageBuffer;
/*     */   private ImageBuffer blueImageBuffer;
/*     */   private Image fromRed;
/*     */   private Image fromBlue;
/*     */   private String endian;
/*     */   
/*     */   public ImageBufferEndianTest() {
/*  34 */     super("ImageBuffer Endian Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/*  44 */       AppGameContainer container = new AppGameContainer((Game)new ImageBufferEndianTest());
/*  45 */       container.setDisplayMode(800, 600, false);
/*  46 */       container.start();
/*  47 */     } catch (SlickException e) {
/*  48 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  57 */     g.setColor(Color.white);
/*  58 */     g.drawString("Endianness is " + this.endian, 10.0F, 100.0F);
/*     */     
/*  60 */     g.drawString("Image below should be red", 10.0F, 200.0F);
/*  61 */     g.drawImage(this.fromRed, 10.0F, 220.0F);
/*  62 */     g.drawString("Image below should be blue", 410.0F, 200.0F);
/*  63 */     g.drawImage(this.fromBlue, 410.0F, 220.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  72 */     if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/*  73 */       this.endian = "Big endian";
/*  74 */     } else if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*  75 */       this.endian = "Little endian";
/*     */     } else {
/*  77 */       this.endian = "no idea";
/*     */     } 
/*  79 */     this.redImageBuffer = new ImageBuffer(100, 100);
/*  80 */     fillImageBufferWithColor(this.redImageBuffer, Color.red, 100, 100);
/*     */     
/*  82 */     this.blueImageBuffer = new ImageBuffer(100, 100);
/*  83 */     fillImageBufferWithColor(this.blueImageBuffer, Color.blue, 100, 100);
/*     */     
/*  85 */     this.fromRed = this.redImageBuffer.getImage();
/*  86 */     this.fromBlue = this.blueImageBuffer.getImage();
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
/*     */   private void fillImageBufferWithColor(ImageBuffer buffer, Color c, int width, int height) {
/*  98 */     for (int x = 0; x < width; x++) {
/*  99 */       for (int y = 0; y < height; y++)
/* 100 */         buffer.setRGBA(x, y, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\ImageBufferEndianTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */