/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ 
/*  10:    */ public class TransformTest
/*  11:    */   extends BasicGame
/*  12:    */ {
/*  13: 18 */   private float scale = 1.0F;
/*  14:    */   private boolean scaleUp;
/*  15:    */   private boolean scaleDown;
/*  16:    */   
/*  17:    */   public TransformTest()
/*  18:    */   {
/*  19: 28 */     super("Transform Test");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void init(GameContainer container)
/*  23:    */     throws SlickException
/*  24:    */   {
/*  25: 35 */     container.setTargetFrameRate(100);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void render(GameContainer contiainer, Graphics g)
/*  29:    */   {
/*  30: 42 */     g.translate(320.0F, 240.0F);
/*  31: 43 */     g.scale(this.scale, this.scale);
/*  32:    */     
/*  33: 45 */     g.setColor(Color.red);
/*  34: 46 */     for (int x = 0; x < 10; x++) {
/*  35: 47 */       for (int y = 0; y < 10; y++) {
/*  36: 48 */         g.fillRect(-500 + x * 100, -500 + y * 100, 80.0F, 80.0F);
/*  37:    */       }
/*  38:    */     }
/*  39: 52 */     g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
/*  40: 53 */     g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  41: 54 */     g.setColor(Color.white);
/*  42: 55 */     g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void update(GameContainer container, int delta)
/*  46:    */   {
/*  47: 62 */     if (this.scaleUp) {
/*  48: 63 */       this.scale += delta * 0.001F;
/*  49:    */     }
/*  50: 65 */     if (this.scaleDown) {
/*  51: 66 */       this.scale -= delta * 0.001F;
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void keyPressed(int key, char c)
/*  56:    */   {
/*  57: 74 */     if (key == 1) {
/*  58: 75 */       System.exit(0);
/*  59:    */     }
/*  60: 77 */     if (key == 16) {
/*  61: 78 */       this.scaleUp = true;
/*  62:    */     }
/*  63: 80 */     if (key == 30) {
/*  64: 81 */       this.scaleDown = true;
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void keyReleased(int key, char c)
/*  69:    */   {
/*  70: 89 */     if (key == 16) {
/*  71: 90 */       this.scaleUp = false;
/*  72:    */     }
/*  73: 92 */     if (key == 30) {
/*  74: 93 */       this.scaleDown = false;
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void main(String[] argv)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:104 */       AppGameContainer container = new AppGameContainer(new TransformTest());
/*  83:105 */       container.setDisplayMode(640, 480, false);
/*  84:106 */       container.start();
/*  85:    */     }
/*  86:    */     catch (SlickException e)
/*  87:    */     {
/*  88:108 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TransformTest
 * JD-Core Version:    0.7.0.1
 */