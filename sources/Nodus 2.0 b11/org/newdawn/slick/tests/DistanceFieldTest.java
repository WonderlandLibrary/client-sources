/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.lwjgl.opengl.GL11;
/*   4:    */ import org.newdawn.slick.AngelCodeFont;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ 
/*  12:    */ public class DistanceFieldTest
/*  13:    */   extends BasicGame
/*  14:    */ {
/*  15:    */   private AngelCodeFont font;
/*  16:    */   
/*  17:    */   public DistanceFieldTest()
/*  18:    */   {
/*  19: 26 */     super("DistanceMapTest Test");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void init(GameContainer container)
/*  23:    */     throws SlickException
/*  24:    */   {
/*  25: 33 */     this.font = new AngelCodeFont("testdata/distance.fnt", "testdata/distance-dis.png");
/*  26: 34 */     container.getGraphics().setBackground(Color.black);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void update(GameContainer container, int delta)
/*  30:    */     throws SlickException
/*  31:    */   {}
/*  32:    */   
/*  33:    */   public void render(GameContainer container, Graphics g)
/*  34:    */     throws SlickException
/*  35:    */   {
/*  36: 49 */     String text = "abc";
/*  37: 50 */     this.font.drawString(610.0F, 100.0F, text);
/*  38:    */     
/*  39: 52 */     GL11.glDisable(3042);
/*  40: 53 */     GL11.glEnable(3008);
/*  41: 54 */     GL11.glAlphaFunc(518, 0.5F);
/*  42: 55 */     this.font.drawString(610.0F, 150.0F, text);
/*  43: 56 */     GL11.glDisable(3008);
/*  44: 57 */     GL11.glEnable(3042);
/*  45:    */     
/*  46: 59 */     g.translate(-50.0F, -130.0F);
/*  47: 60 */     g.scale(10.0F, 10.0F);
/*  48: 61 */     this.font.drawString(0.0F, 0.0F, text);
/*  49:    */     
/*  50: 63 */     GL11.glDisable(3042);
/*  51: 64 */     GL11.glEnable(3008);
/*  52: 65 */     GL11.glAlphaFunc(518, 0.5F);
/*  53: 66 */     this.font.drawString(0.0F, 26.0F, text);
/*  54: 67 */     GL11.glDisable(3008);
/*  55: 68 */     GL11.glEnable(3042);
/*  56:    */     
/*  57: 70 */     g.resetTransform();
/*  58: 71 */     g.setColor(Color.lightGray);
/*  59: 72 */     g.drawString("Original Size on Sheet", 620.0F, 210.0F);
/*  60: 73 */     g.drawString("10x Scale Up", 40.0F, 575.0F);
/*  61:    */     
/*  62: 75 */     g.setColor(Color.darkGray);
/*  63: 76 */     g.drawRect(40.0F, 40.0F, 560.0F, 530.0F);
/*  64: 77 */     g.drawRect(610.0F, 105.0F, 150.0F, 100.0F);
/*  65:    */     
/*  66: 79 */     g.setColor(Color.white);
/*  67: 80 */     g.drawString("512x512 Font Sheet", 620.0F, 300.0F);
/*  68: 81 */     g.drawString("NEHE Charset", 620.0F, 320.0F);
/*  69: 82 */     g.drawString("4096x4096 (8x) Source Image", 620.0F, 340.0F);
/*  70: 83 */     g.drawString("ScanSize = 20", 620.0F, 360.0F);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void keyPressed(int key, char c) {}
/*  74:    */   
/*  75:    */   public static void main(String[] argv)
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79: 99 */       AppGameContainer container = new AppGameContainer(new DistanceFieldTest());
/*  80:100 */       container.setDisplayMode(800, 600, false);
/*  81:101 */       container.start();
/*  82:    */     }
/*  83:    */     catch (SlickException e)
/*  84:    */     {
/*  85:103 */       e.printStackTrace();
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.DistanceFieldTest
 * JD-Core Version:    0.7.0.1
 */