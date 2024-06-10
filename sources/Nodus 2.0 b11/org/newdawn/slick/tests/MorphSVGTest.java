/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.SlickException;
/*  8:   */ import org.newdawn.slick.svg.Diagram;
/*  9:   */ import org.newdawn.slick.svg.InkscapeLoader;
/* 10:   */ import org.newdawn.slick.svg.SVGMorph;
/* 11:   */ import org.newdawn.slick.svg.SimpleDiagramRenderer;
/* 12:   */ 
/* 13:   */ public class MorphSVGTest
/* 14:   */   extends BasicGame
/* 15:   */ {
/* 16:   */   private SVGMorph morph;
/* 17:   */   private Diagram base;
/* 18:   */   private float time;
/* 19:26 */   private float x = -300.0F;
/* 20:   */   
/* 21:   */   public MorphSVGTest()
/* 22:   */   {
/* 23:32 */     super("MorphShapeTest");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void init(GameContainer container)
/* 27:   */     throws SlickException
/* 28:   */   {
/* 29:39 */     this.base = InkscapeLoader.load("testdata/svg/walk1.svg");
/* 30:40 */     this.morph = new SVGMorph(this.base);
/* 31:41 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk2.svg"));
/* 32:42 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk3.svg"));
/* 33:43 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk4.svg"));
/* 34:   */     
/* 35:45 */     container.setVSync(true);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void update(GameContainer container, int delta)
/* 39:   */     throws SlickException
/* 40:   */   {
/* 41:53 */     this.morph.updateMorphTime(delta * 0.003F);
/* 42:   */     
/* 43:55 */     this.x += delta * 0.2F;
/* 44:56 */     if (this.x > 550.0F) {
/* 45:57 */       this.x = -450.0F;
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void render(GameContainer container, Graphics g)
/* 50:   */     throws SlickException
/* 51:   */   {
/* 52:66 */     g.translate(this.x, 0.0F);
/* 53:67 */     SimpleDiagramRenderer.render(g, this.morph);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static void main(String[] argv)
/* 57:   */   {
/* 58:   */     try
/* 59:   */     {
/* 60:78 */       AppGameContainer container = new AppGameContainer(
/* 61:79 */         new MorphSVGTest());
/* 62:80 */       container.setDisplayMode(800, 600, false);
/* 63:81 */       container.start();
/* 64:   */     }
/* 65:   */     catch (SlickException e)
/* 66:   */     {
/* 67:83 */       e.printStackTrace();
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.MorphSVGTest
 * JD-Core Version:    0.7.0.1
 */