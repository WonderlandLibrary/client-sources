/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.Image;
/*  8:   */ import org.newdawn.slick.SlickException;
/*  9:   */ import org.newdawn.slick.particles.ParticleSystem;
/* 10:   */ import org.newdawn.slick.particles.effects.FireEmitter;
/* 11:   */ 
/* 12:   */ public class ParticleTest
/* 13:   */   extends BasicGame
/* 14:   */ {
/* 15:   */   private ParticleSystem system;
/* 16:22 */   private int mode = 2;
/* 17:   */   
/* 18:   */   public ParticleTest()
/* 19:   */   {
/* 20:28 */     super("Particle Test");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init(GameContainer container)
/* 24:   */     throws SlickException
/* 25:   */   {
/* 26:35 */     Image image = new Image("testdata/particle.tga", true);
/* 27:36 */     this.system = new ParticleSystem(image);
/* 28:   */     
/* 29:38 */     this.system.addEmitter(new FireEmitter(400, 300, 45.0F));
/* 30:39 */     this.system.addEmitter(new FireEmitter(200, 300, 60.0F));
/* 31:40 */     this.system.addEmitter(new FireEmitter(600, 300, 30.0F));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void render(GameContainer container, Graphics g)
/* 35:   */   {
/* 36:49 */     for (int i = 0; i < 100; i++)
/* 37:   */     {
/* 38:50 */       g.translate(1.0F, 1.0F);
/* 39:51 */       this.system.render();
/* 40:   */     }
/* 41:53 */     g.resetTransform();
/* 42:54 */     g.drawString("Press space to toggle blending mode", 200.0F, 500.0F);
/* 43:55 */     g.drawString("Particle Count: " + this.system.getParticleCount() * 100, 200.0F, 520.0F);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void update(GameContainer container, int delta)
/* 47:   */   {
/* 48:62 */     this.system.update(delta);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void keyPressed(int key, char c)
/* 52:   */   {
/* 53:69 */     if (key == 1) {
/* 54:70 */       System.exit(0);
/* 55:   */     }
/* 56:72 */     if (key == 57)
/* 57:   */     {
/* 58:73 */       this.mode = (1 == this.mode ? 2 : 1);
/* 59:74 */       this.system.setBlendingMode(this.mode);
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void main(String[] argv)
/* 64:   */   {
/* 65:   */     try
/* 66:   */     {
/* 67:85 */       AppGameContainer container = new AppGameContainer(new ParticleTest());
/* 68:86 */       container.setDisplayMode(800, 600, false);
/* 69:87 */       container.start();
/* 70:   */     }
/* 71:   */     catch (SlickException e)
/* 72:   */     {
/* 73:89 */       e.printStackTrace();
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.ParticleTest
 * JD-Core Version:    0.7.0.1
 */