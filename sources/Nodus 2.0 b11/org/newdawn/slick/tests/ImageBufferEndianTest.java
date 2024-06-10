/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.nio.ByteOrder;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Image;
/*  10:    */ import org.newdawn.slick.ImageBuffer;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ 
/*  13:    */ public class ImageBufferEndianTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private ImageBuffer redImageBuffer;
/*  17:    */   private ImageBuffer blueImageBuffer;
/*  18:    */   private Image fromRed;
/*  19:    */   private Image fromBlue;
/*  20:    */   private String endian;
/*  21:    */   
/*  22:    */   public ImageBufferEndianTest()
/*  23:    */   {
/*  24: 34 */     super("ImageBuffer Endian Test");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void main(String[] args)
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 44 */       AppGameContainer container = new AppGameContainer(new ImageBufferEndianTest());
/*  32: 45 */       container.setDisplayMode(800, 600, false);
/*  33: 46 */       container.start();
/*  34:    */     }
/*  35:    */     catch (SlickException e)
/*  36:    */     {
/*  37: 48 */       e.printStackTrace();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void render(GameContainer container, Graphics g)
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44: 57 */     g.setColor(Color.white);
/*  45: 58 */     g.drawString("Endianness is " + this.endian, 10.0F, 100.0F);
/*  46:    */     
/*  47: 60 */     g.drawString("Image below should be red", 10.0F, 200.0F);
/*  48: 61 */     g.drawImage(this.fromRed, 10.0F, 220.0F);
/*  49: 62 */     g.drawString("Image below should be blue", 410.0F, 200.0F);
/*  50: 63 */     g.drawImage(this.fromBlue, 410.0F, 220.0F);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void init(GameContainer container)
/*  54:    */     throws SlickException
/*  55:    */   {
/*  56: 72 */     if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/*  57: 73 */       this.endian = "Big endian";
/*  58: 74 */     } else if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*  59: 75 */       this.endian = "Little endian";
/*  60:    */     } else {
/*  61: 77 */       this.endian = "no idea";
/*  62:    */     }
/*  63: 79 */     this.redImageBuffer = new ImageBuffer(100, 100);
/*  64: 80 */     fillImageBufferWithColor(this.redImageBuffer, Color.red, 100, 100);
/*  65:    */     
/*  66: 82 */     this.blueImageBuffer = new ImageBuffer(100, 100);
/*  67: 83 */     fillImageBufferWithColor(this.blueImageBuffer, Color.blue, 100, 100);
/*  68:    */     
/*  69: 85 */     this.fromRed = this.redImageBuffer.getImage();
/*  70: 86 */     this.fromBlue = this.blueImageBuffer.getImage();
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void fillImageBufferWithColor(ImageBuffer buffer, Color c, int width, int height)
/*  74:    */   {
/*  75: 98 */     for (int x = 0; x < width; x++) {
/*  76: 99 */       for (int y = 0; y < height; y++) {
/*  77:100 */         buffer.setRGBA(x, y, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void update(GameContainer container, int delta)
/*  83:    */     throws SlickException
/*  84:    */   {}
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageBufferEndianTest
 * JD-Core Version:    0.7.0.1
 */