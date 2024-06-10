/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ 
/*  10:    */ public class ClipTest
/*  11:    */   extends BasicGame
/*  12:    */ {
/*  13: 19 */   private float ang = 0.0F;
/*  14:    */   private boolean world;
/*  15:    */   private boolean clip;
/*  16:    */   
/*  17:    */   public ClipTest()
/*  18:    */   {
/*  19: 29 */     super("Clip Test");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void init(GameContainer container)
/*  23:    */     throws SlickException
/*  24:    */   {}
/*  25:    */   
/*  26:    */   public void update(GameContainer container, int delta)
/*  27:    */     throws SlickException
/*  28:    */   {
/*  29: 43 */     this.ang += delta * 0.01F;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void render(GameContainer container, Graphics g)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 51 */     g.setColor(Color.white);
/*  36: 52 */     g.drawString("1 - No Clipping", 100.0F, 10.0F);
/*  37: 53 */     g.drawString("2 - Screen Clipping", 100.0F, 30.0F);
/*  38: 54 */     g.drawString("3 - World Clipping", 100.0F, 50.0F);
/*  39: 56 */     if (this.world) {
/*  40: 57 */       g.drawString("WORLD CLIPPING ENABLED", 200.0F, 80.0F);
/*  41:    */     }
/*  42: 59 */     if (this.clip) {
/*  43: 60 */       g.drawString("SCREEN CLIPPING ENABLED", 200.0F, 80.0F);
/*  44:    */     }
/*  45: 63 */     g.rotate(400.0F, 400.0F, this.ang);
/*  46: 64 */     if (this.world) {
/*  47: 65 */       g.setWorldClip(350.0F, 302.0F, 100.0F, 196.0F);
/*  48:    */     }
/*  49: 67 */     if (this.clip) {
/*  50: 68 */       g.setClip(350, 302, 100, 196);
/*  51:    */     }
/*  52: 70 */     g.setColor(Color.red);
/*  53: 71 */     g.fillOval(300.0F, 300.0F, 200.0F, 200.0F);
/*  54: 72 */     g.setColor(Color.blue);
/*  55: 73 */     g.fillRect(390.0F, 200.0F, 20.0F, 400.0F);
/*  56:    */     
/*  57: 75 */     g.clearClip();
/*  58: 76 */     g.clearWorldClip();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void keyPressed(int key, char c)
/*  62:    */   {
/*  63: 83 */     if (key == 2)
/*  64:    */     {
/*  65: 84 */       this.world = false;
/*  66: 85 */       this.clip = false;
/*  67:    */     }
/*  68: 87 */     if (key == 3)
/*  69:    */     {
/*  70: 88 */       this.world = false;
/*  71: 89 */       this.clip = true;
/*  72:    */     }
/*  73: 91 */     if (key == 4)
/*  74:    */     {
/*  75: 92 */       this.world = true;
/*  76: 93 */       this.clip = false;
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void main(String[] argv)
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:104 */       AppGameContainer container = new AppGameContainer(new ClipTest());
/*  85:105 */       container.setDisplayMode(800, 600, false);
/*  86:106 */       container.start();
/*  87:    */     }
/*  88:    */     catch (SlickException e)
/*  89:    */     {
/*  90:108 */       e.printStackTrace();
/*  91:    */     }
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ClipTest
 * JD-Core Version:    0.7.0.1
 */