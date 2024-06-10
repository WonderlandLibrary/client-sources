/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Image;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ 
/* 10:   */ public class ImageCornerTest
/* 11:   */   extends BasicGame
/* 12:   */ {
/* 13:   */   private Image image;
/* 14:   */   private Image[] images;
/* 15:   */   private int width;
/* 16:   */   private int height;
/* 17:   */   
/* 18:   */   public ImageCornerTest()
/* 19:   */   {
/* 20:29 */     super("Image Corner Test");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init(GameContainer container)
/* 24:   */     throws SlickException
/* 25:   */   {
/* 26:36 */     this.image = new Image("testdata/logo.png");
/* 27:   */     
/* 28:38 */     this.width = (this.image.getWidth() / 3);
/* 29:39 */     this.height = (this.image.getHeight() / 3);
/* 30:   */     
/* 31:41 */     this.images = new Image[] {
/* 32:42 */       this.image.getSubImage(0, 0, this.width, this.height), this.image.getSubImage(this.width, 0, this.width, this.height), this.image.getSubImage(this.width * 2, 0, this.width, this.height), 
/* 33:43 */       this.image.getSubImage(0, this.height, this.width, this.height), this.image.getSubImage(this.width, this.height, this.width, this.height), this.image.getSubImage(this.width * 2, this.height, this.width, this.height), 
/* 34:44 */       this.image.getSubImage(0, this.height * 2, this.width, this.height), this.image.getSubImage(this.width, this.height * 2, this.width, this.height), this.image.getSubImage(this.width * 2, this.height * 2, this.width, this.height) };
/* 35:   */     
/* 36:   */ 
/* 37:47 */     this.images[0].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 38:48 */     this.images[1].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 39:49 */     this.images[1].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 40:50 */     this.images[2].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 41:51 */     this.images[3].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 42:52 */     this.images[3].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 43:   */     
/* 44:54 */     this.images[4].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 45:55 */     this.images[4].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 46:56 */     this.images[4].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 47:57 */     this.images[4].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
/* 48:58 */     this.images[5].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 49:59 */     this.images[5].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
/* 50:   */     
/* 51:61 */     this.images[6].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 52:62 */     this.images[7].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
/* 53:63 */     this.images[7].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 54:64 */     this.images[8].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void render(GameContainer container, Graphics g)
/* 58:   */   {
/* 59:71 */     for (int x = 0; x < 3; x++) {
/* 60:72 */       for (int y = 0; y < 3; y++) {
/* 61:73 */         this.images[(x + y * 3)].draw(100 + x * this.width, 100 + y * this.height);
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static void main(String[] argv)
/* 67:   */   {
/* 68:84 */     boolean sharedContextTest = false;
/* 69:   */     try
/* 70:   */     {
/* 71:87 */       AppGameContainer container = new AppGameContainer(new ImageCornerTest());
/* 72:88 */       container.setDisplayMode(800, 600, false);
/* 73:89 */       container.start();
/* 74:   */     }
/* 75:   */     catch (SlickException e)
/* 76:   */     {
/* 77:91 */       e.printStackTrace();
/* 78:   */     }
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void update(GameContainer container, int delta)
/* 82:   */     throws SlickException
/* 83:   */   {}
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageCornerTest
 * JD-Core Version:    0.7.0.1
 */