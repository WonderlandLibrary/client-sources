/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AngelCodeFont;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Image;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.util.Log;
/*  12:    */ 
/*  13:    */ public class FontTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private AngelCodeFont font;
/*  17:    */   private AngelCodeFont font2;
/*  18:    */   private Image image;
/*  19:    */   private static AppGameContainer container;
/*  20:    */   
/*  21:    */   public FontTest()
/*  22:    */   {
/*  23: 31 */     super("Font Test");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void init(GameContainer container)
/*  27:    */     throws SlickException
/*  28:    */   {
/*  29: 38 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/*  30: 39 */     this.font2 = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
/*  31: 40 */     this.image = new Image("testdata/demo2_00.tga", false);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void render(GameContainer container, Graphics g)
/*  35:    */   {
/*  36: 47 */     this.font.drawString(80.0F, 5.0F, "A Font Example", Color.red);
/*  37: 48 */     this.font.drawString(100.0F, 32.0F, "We - AV - Here is a more complete line that hopefully");
/*  38: 49 */     this.font.drawString(100.0F, 36 + this.font.getHeight("We Here is a more complete line that hopefully"), 
/*  39: 50 */       "will show some kerning.");
/*  40:    */     
/*  41: 52 */     this.font2.drawString(80.0F, 85.0F, "A Font Example", Color.red);
/*  42: 53 */     this.font2.drawString(100.0F, 132.0F, "We - AV - Here is a more complete line that hopefully");
/*  43: 54 */     this.font2.drawString(100.0F, 136 + this.font2.getHeight("We - Here is a more complete line that hopefully"), 
/*  44: 55 */       "will show some kerning.");
/*  45: 56 */     this.image.draw(100.0F, 400.0F);
/*  46:    */     
/*  47: 58 */     String testStr = "Testing Font";
/*  48: 59 */     this.font2.drawString(100.0F, 300.0F, testStr);
/*  49: 60 */     g.setColor(Color.white);
/*  50: 61 */     g.drawRect(100.0F, 300 + this.font2.getYOffset(testStr), this.font2.getWidth(testStr), this.font2.getHeight(testStr) - this.font2.getYOffset(testStr));
/*  51: 62 */     this.font.drawString(500.0F, 300.0F, testStr);
/*  52: 63 */     g.setColor(Color.white);
/*  53: 64 */     g.drawRect(500.0F, 300 + this.font.getYOffset(testStr), this.font.getWidth(testStr), this.font.getHeight(testStr) - this.font.getYOffset(testStr));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void update(GameContainer container, int delta)
/*  57:    */     throws SlickException
/*  58:    */   {}
/*  59:    */   
/*  60:    */   public void keyPressed(int key, char c)
/*  61:    */   {
/*  62: 77 */     if (key == 1) {
/*  63: 78 */       System.exit(0);
/*  64:    */     }
/*  65: 80 */     if (key == 57) {
/*  66:    */       try
/*  67:    */       {
/*  68: 82 */         container.setDisplayMode(640, 480, false);
/*  69:    */       }
/*  70:    */       catch (SlickException e)
/*  71:    */       {
/*  72: 84 */         Log.error(e);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void main(String[] argv)
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81: 99 */       container = new AppGameContainer(new FontTest());
/*  82:100 */       container.setDisplayMode(800, 600, false);
/*  83:101 */       container.start();
/*  84:    */     }
/*  85:    */     catch (SlickException e)
/*  86:    */     {
/*  87:103 */       e.printStackTrace();
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.FontTest
 * JD-Core Version:    0.7.0.1
 */