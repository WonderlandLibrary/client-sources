/*     */ package org.newdawn.slick.state.transition;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.state.GameState;
/*     */ import org.newdawn.slick.state.StateBasedGame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VerticalSplitTransition
/*     */   implements Transition
/*     */ {
/*  22 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */ 
/*     */   
/*     */   private GameState prev;
/*     */ 
/*     */ 
/*     */   
/*     */   private float offset;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finish;
/*     */ 
/*     */   
/*     */   private Color background;
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalSplitTransition() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalSplitTransition(Color background) {
/*  46 */     this.background = background;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameState firstState, GameState secondState) {
/*  53 */     this.prev = secondState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  60 */     return this.finish;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/*  67 */     g.translate(0.0F, -this.offset);
/*  68 */     g.setClip(0, (int)-this.offset, container.getWidth(), container.getHeight() / 2);
/*  69 */     if (this.background != null) {
/*  70 */       Color c = g.getColor();
/*  71 */       g.setColor(this.background);
/*  72 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  73 */       g.setColor(c);
/*     */     } 
/*  75 */     GL.glPushMatrix();
/*  76 */     this.prev.render(container, game, g);
/*  77 */     GL.glPopMatrix();
/*  78 */     g.clearClip();
/*  79 */     g.resetTransform();
/*     */     
/*  81 */     g.translate(0.0F, this.offset);
/*  82 */     g.setClip(0, (int)((container.getHeight() / 2) + this.offset), container.getWidth(), container.getHeight() / 2);
/*  83 */     if (this.background != null) {
/*  84 */       Color c = g.getColor();
/*  85 */       g.setColor(this.background);
/*  86 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  87 */       g.setColor(c);
/*     */     } 
/*  89 */     GL.glPushMatrix();
/*  90 */     this.prev.render(container, game, g);
/*  91 */     GL.glPopMatrix();
/*  92 */     g.clearClip();
/*  93 */     g.translate(0.0F, -this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
/* 108 */     this.offset += delta * 1.0F;
/* 109 */     if (this.offset > (container.getHeight() / 2))
/* 110 */       this.finish = true; 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\VerticalSplitTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */