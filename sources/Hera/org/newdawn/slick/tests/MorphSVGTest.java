/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.svg.Diagram;
/*    */ import org.newdawn.slick.svg.InkscapeLoader;
/*    */ import org.newdawn.slick.svg.SVGMorph;
/*    */ import org.newdawn.slick.svg.SimpleDiagramRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MorphSVGTest
/*    */   extends BasicGame
/*    */ {
/*    */   private SVGMorph morph;
/*    */   private Diagram base;
/*    */   private float time;
/* 26 */   private float x = -300.0F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MorphSVGTest() {
/* 32 */     super("MorphShapeTest");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 39 */     this.base = InkscapeLoader.load("testdata/svg/walk1.svg");
/* 40 */     this.morph = new SVGMorph(this.base);
/* 41 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk2.svg"));
/* 42 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk3.svg"));
/* 43 */     this.morph.addStep(InkscapeLoader.load("testdata/svg/walk4.svg"));
/*    */     
/* 45 */     container.setVSync(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {
/* 53 */     this.morph.updateMorphTime(delta * 0.003F);
/*    */     
/* 55 */     this.x += delta * 0.2F;
/* 56 */     if (this.x > 550.0F) {
/* 57 */       this.x = -450.0F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 66 */     g.translate(this.x, 0.0F);
/* 67 */     SimpleDiagramRenderer.render(g, (Diagram)this.morph);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 78 */       AppGameContainer container = new AppGameContainer((Game)new MorphSVGTest());
/*    */       
/* 80 */       container.setDisplayMode(800, 600, false);
/* 81 */       container.start();
/* 82 */     } catch (SlickException e) {
/* 83 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\MorphSVGTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */