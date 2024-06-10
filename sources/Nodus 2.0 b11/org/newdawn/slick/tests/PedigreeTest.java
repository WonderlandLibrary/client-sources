/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.particles.ConfigurableEmitter;
/*  11:    */ import org.newdawn.slick.particles.ParticleIO;
/*  12:    */ import org.newdawn.slick.particles.ParticleSystem;
/*  13:    */ 
/*  14:    */ public class PedigreeTest
/*  15:    */   extends BasicGame
/*  16:    */ {
/*  17:    */   private Image image;
/*  18:    */   private GameContainer container;
/*  19:    */   private ParticleSystem trail;
/*  20:    */   private ParticleSystem fire;
/*  21:    */   private float rx;
/*  22: 33 */   private float ry = 900.0F;
/*  23:    */   
/*  24:    */   public PedigreeTest()
/*  25:    */   {
/*  26: 39 */     super("Pedigree Test");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void init(GameContainer container)
/*  30:    */     throws SlickException
/*  31:    */   {
/*  32: 46 */     this.container = container;
/*  33:    */     try
/*  34:    */     {
/*  35: 49 */       this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
/*  36: 50 */       this.trail = ParticleIO.loadConfiguredSystem("testdata/smoketrail.xml");
/*  37:    */     }
/*  38:    */     catch (IOException e)
/*  39:    */     {
/*  40: 53 */       throw new SlickException("Failed to load particle systems", e);
/*  41:    */     }
/*  42: 55 */     this.image = new Image("testdata/rocket.png");
/*  43:    */     
/*  44: 57 */     spawnRocket();
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void spawnRocket()
/*  48:    */   {
/*  49: 64 */     this.ry = 700.0F;
/*  50: 65 */     this.rx = ((float)(Math.random() * 600.0D + 100.0D));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void render(GameContainer container, Graphics g)
/*  54:    */   {
/*  55: 72 */     ((ConfigurableEmitter)this.trail.getEmitter(0)).setPosition(this.rx + 14.0F, this.ry + 35.0F);
/*  56: 73 */     this.trail.render();
/*  57: 74 */     this.image.draw((int)this.rx, (int)this.ry);
/*  58:    */     
/*  59: 76 */     g.translate(400.0F, 300.0F);
/*  60: 77 */     this.fire.render();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void update(GameContainer container, int delta)
/*  64:    */   {
/*  65: 84 */     this.fire.update(delta);
/*  66: 85 */     this.trail.update(delta);
/*  67:    */     
/*  68: 87 */     this.ry -= delta * 0.25F;
/*  69: 88 */     if (this.ry < -100.0F) {
/*  70: 89 */       spawnRocket();
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void mousePressed(int button, int x, int y)
/*  75:    */   {
/*  76: 94 */     super.mousePressed(button, x, y);
/*  77: 96 */     for (int i = 0; i < this.fire.getEmitterCount(); i++) {
/*  78: 97 */       ((ConfigurableEmitter)this.fire.getEmitter(i)).setPosition(x - 400, y - 300, true);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static void main(String[] argv)
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:108 */       AppGameContainer container = new AppGameContainer(new PedigreeTest());
/*  87:109 */       container.setDisplayMode(800, 600, false);
/*  88:110 */       container.start();
/*  89:    */     }
/*  90:    */     catch (SlickException e)
/*  91:    */     {
/*  92:112 */       e.printStackTrace();
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void keyPressed(int key, char c)
/*  97:    */   {
/*  98:120 */     if (key == 1) {
/*  99:121 */       this.container.exit();
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.PedigreeTest
 * JD-Core Version:    0.7.0.1
 */