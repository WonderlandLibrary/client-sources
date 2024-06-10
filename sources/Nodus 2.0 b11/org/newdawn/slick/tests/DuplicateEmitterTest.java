/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.SlickException;
/*   9:    */ import org.newdawn.slick.particles.ConfigurableEmitter;
/*  10:    */ import org.newdawn.slick.particles.ParticleIO;
/*  11:    */ import org.newdawn.slick.particles.ParticleSystem;
/*  12:    */ 
/*  13:    */ public class DuplicateEmitterTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private GameContainer container;
/*  17:    */   private ParticleSystem explosionSystem;
/*  18:    */   private ConfigurableEmitter explosionEmitter;
/*  19:    */   
/*  20:    */   public DuplicateEmitterTest()
/*  21:    */   {
/*  22: 33 */     super("DuplicateEmitterTest");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void init(GameContainer container)
/*  26:    */     throws SlickException
/*  27:    */   {
/*  28: 42 */     this.container = container;
/*  29:    */     try
/*  30:    */     {
/*  31: 46 */       this.explosionSystem = ParticleIO.loadConfiguredSystem("testdata/endlessexplosion.xml");
/*  32:    */       
/*  33: 48 */       this.explosionEmitter = ((ConfigurableEmitter)this.explosionSystem.getEmitter(0));
/*  34:    */       
/*  35: 50 */       this.explosionEmitter.setPosition(400.0F, 100.0F);
/*  36: 52 */       for (int i = 0; i < 5; i++)
/*  37:    */       {
/*  38: 54 */         ConfigurableEmitter newOne = this.explosionEmitter.duplicate();
/*  39: 56 */         if (newOne == null) {
/*  40: 57 */           throw new SlickException("Failed to duplicate explosionEmitter");
/*  41:    */         }
/*  42: 59 */         newOne.name = (newOne.name + "_" + i);
/*  43:    */         
/*  44: 61 */         newOne.setPosition((i + 1) * 133, 400.0F);
/*  45:    */         
/*  46: 63 */         this.explosionSystem.addEmitter(newOne);
/*  47:    */       }
/*  48:    */     }
/*  49:    */     catch (IOException e)
/*  50:    */     {
/*  51: 66 */       throw new SlickException("Failed to load particle systems", e);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void update(GameContainer container, int delta)
/*  56:    */     throws SlickException
/*  57:    */   {
/*  58: 74 */     this.explosionSystem.update(delta);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void render(GameContainer container, Graphics g)
/*  62:    */     throws SlickException
/*  63:    */   {
/*  64: 81 */     this.explosionSystem.render();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void keyPressed(int key, char c)
/*  68:    */   {
/*  69: 88 */     if (key == 1) {
/*  70: 89 */       this.container.exit();
/*  71:    */     }
/*  72: 91 */     if (key == 37) {
/*  73: 92 */       this.explosionEmitter.wrapUp();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void main(String[] argv)
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:103 */       AppGameContainer container = new AppGameContainer(new DuplicateEmitterTest());
/*  82:104 */       container.setDisplayMode(800, 600, false);
/*  83:105 */       container.start();
/*  84:    */     }
/*  85:    */     catch (SlickException e)
/*  86:    */     {
/*  87:107 */       e.printStackTrace();
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.DuplicateEmitterTest
 * JD-Core Version:    0.7.0.1
 */