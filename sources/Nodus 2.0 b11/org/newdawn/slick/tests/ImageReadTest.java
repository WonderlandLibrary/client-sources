/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.Color;
/*  6:   */ import org.newdawn.slick.GameContainer;
/*  7:   */ import org.newdawn.slick.Graphics;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ import org.newdawn.slick.Input;
/* 10:   */ import org.newdawn.slick.SlickException;
/* 11:   */ 
/* 12:   */ public class ImageReadTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private Image image;
/* 16:20 */   private Color[] read = new Color[6];
/* 17:   */   private Graphics g;
/* 18:   */   
/* 19:   */   public ImageReadTest()
/* 20:   */   {
/* 21:28 */     super("Image Read Test");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void init(GameContainer container)
/* 25:   */     throws SlickException
/* 26:   */   {
/* 27:35 */     this.image = new Image("testdata/testcard.png");
/* 28:36 */     this.read[0] = this.image.getColor(0, 0);
/* 29:37 */     this.read[1] = this.image.getColor(30, 40);
/* 30:38 */     this.read[2] = this.image.getColor(55, 70);
/* 31:39 */     this.read[3] = this.image.getColor(80, 90);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void render(GameContainer container, Graphics g)
/* 35:   */   {
/* 36:46 */     this.g = g;
/* 37:   */     
/* 38:48 */     this.image.draw(100.0F, 100.0F);
/* 39:49 */     g.setColor(Color.white);
/* 40:50 */     g.drawString("Move mouse over test image", 200.0F, 20.0F);
/* 41:51 */     g.setColor(this.read[0]);
/* 42:52 */     g.drawString(this.read[0].toString(), 100.0F, 300.0F);
/* 43:53 */     g.setColor(this.read[1]);
/* 44:54 */     g.drawString(this.read[1].toString(), 150.0F, 320.0F);
/* 45:55 */     g.setColor(this.read[2]);
/* 46:56 */     g.drawString(this.read[2].toString(), 200.0F, 340.0F);
/* 47:57 */     g.setColor(this.read[3]);
/* 48:58 */     g.drawString(this.read[3].toString(), 250.0F, 360.0F);
/* 49:59 */     if (this.read[4] != null)
/* 50:   */     {
/* 51:60 */       g.setColor(this.read[4]);
/* 52:61 */       g.drawString("On image: " + this.read[4].toString(), 100.0F, 250.0F);
/* 53:   */     }
/* 54:63 */     if (this.read[5] != null)
/* 55:   */     {
/* 56:64 */       g.setColor(Color.white);
/* 57:65 */       g.drawString("On screen: " + this.read[5].toString(), 100.0F, 270.0F);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void update(GameContainer container, int delta)
/* 62:   */   {
/* 63:73 */     int mx = container.getInput().getMouseX();
/* 64:74 */     int my = container.getInput().getMouseY();
/* 65:76 */     if ((mx >= 100) && (my >= 100) && (mx < 200) && (my < 200)) {
/* 66:77 */       this.read[4] = this.image.getColor(mx - 100, my - 100);
/* 67:   */     } else {
/* 68:79 */       this.read[4] = Color.black;
/* 69:   */     }
/* 70:82 */     this.read[5] = this.g.getPixel(mx, my);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public static void main(String[] argv)
/* 74:   */   {
/* 75:   */     try
/* 76:   */     {
/* 77:92 */       AppGameContainer container = new AppGameContainer(new ImageReadTest());
/* 78:93 */       container.setDisplayMode(800, 600, false);
/* 79:94 */       container.start();
/* 80:   */     }
/* 81:   */     catch (SlickException e)
/* 82:   */     {
/* 83:96 */       e.printStackTrace();
/* 84:   */     }
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ImageReadTest
 * JD-Core Version:    0.7.0.1
 */