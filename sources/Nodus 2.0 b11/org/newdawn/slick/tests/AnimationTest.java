/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Animation;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Input;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.SpriteSheet;
/*  12:    */ 
/*  13:    */ public class AnimationTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private Animation animation;
/*  17:    */   private Animation limited;
/*  18:    */   private Animation manual;
/*  19:    */   private Animation pingPong;
/*  20:    */   private GameContainer container;
/*  21: 30 */   private int start = 5000;
/*  22:    */   
/*  23:    */   public AnimationTest()
/*  24:    */   {
/*  25: 36 */     super("Animation Test");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void init(GameContainer container)
/*  29:    */     throws SlickException
/*  30:    */   {
/*  31: 43 */     this.container = container;
/*  32:    */     
/*  33: 45 */     SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
/*  34: 46 */     this.animation = new Animation();
/*  35: 47 */     for (int i = 0; i < 8; i++) {
/*  36: 48 */       this.animation.addFrame(sheet.getSprite(i, 0), 150);
/*  37:    */     }
/*  38: 50 */     this.limited = new Animation();
/*  39: 51 */     for (int i = 0; i < 8; i++) {
/*  40: 52 */       this.limited.addFrame(sheet.getSprite(i, 0), 150);
/*  41:    */     }
/*  42: 54 */     this.limited.stopAt(7);
/*  43: 55 */     this.manual = new Animation(false);
/*  44: 56 */     for (int i = 0; i < 8; i++) {
/*  45: 57 */       this.manual.addFrame(sheet.getSprite(i, 0), 150);
/*  46:    */     }
/*  47: 59 */     this.pingPong = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
/*  48: 60 */     this.pingPong.setPingPong(true);
/*  49: 61 */     container.getGraphics().setBackground(new Color(0.4F, 0.6F, 0.6F));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void render(GameContainer container, Graphics g)
/*  53:    */   {
/*  54: 68 */     g.drawString("Space to restart() animation", 100.0F, 50.0F);
/*  55: 69 */     g.drawString("Til Limited animation: " + this.start, 100.0F, 500.0F);
/*  56: 70 */     g.drawString("Hold 1 to move the manually animated", 100.0F, 70.0F);
/*  57: 71 */     g.drawString("PingPong Frame:" + this.pingPong.getFrame(), 600.0F, 70.0F);
/*  58:    */     
/*  59: 73 */     g.scale(-1.0F, 1.0F);
/*  60: 74 */     this.animation.draw(-100.0F, 100.0F);
/*  61: 75 */     this.animation.draw(-200.0F, 100.0F, 144.0F, 260.0F);
/*  62: 76 */     if (this.start < 0) {
/*  63: 77 */       this.limited.draw(-400.0F, 100.0F, 144.0F, 260.0F);
/*  64:    */     }
/*  65: 79 */     this.manual.draw(-600.0F, 100.0F, 144.0F, 260.0F);
/*  66: 80 */     this.pingPong.draw(-700.0F, 100.0F, 72.0F, 130.0F);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void update(GameContainer container, int delta)
/*  70:    */   {
/*  71: 87 */     if (container.getInput().isKeyDown(2)) {
/*  72: 88 */       this.manual.update(delta);
/*  73:    */     }
/*  74: 90 */     if (this.start >= 0) {
/*  75: 91 */       this.start -= delta;
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void main(String[] argv)
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:102 */       AppGameContainer container = new AppGameContainer(new AnimationTest());
/*  84:103 */       container.setDisplayMode(800, 600, false);
/*  85:104 */       container.start();
/*  86:    */     }
/*  87:    */     catch (SlickException e)
/*  88:    */     {
/*  89:106 */       e.printStackTrace();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void keyPressed(int key, char c)
/*  94:    */   {
/*  95:114 */     if (key == 1) {
/*  96:115 */       this.container.exit();
/*  97:    */     }
/*  98:117 */     if (key == 57) {
/*  99:118 */       this.limited.restart();
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.AnimationTest
 * JD-Core Version:    0.7.0.1
 */