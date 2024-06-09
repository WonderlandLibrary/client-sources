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
/*     */ public class HorizontalSplitTransition
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
/*     */   public HorizontalSplitTransition() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HorizontalSplitTransition(Color background) {
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
/*  67 */     g.translate(-this.offset, 0.0F);
/*  68 */     g.setClip((int)-this.offset, 0, container.getWidth() / 2, container.getHeight());
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
/*     */     
/*  80 */     g.translate(this.offset * 2.0F, 0.0F);
/*  81 */     g.setClip((int)((container.getWidth() / 2) + this.offset), 0, container.getWidth() / 2, container.getHeight());
/*  82 */     if (this.background != null) {
/*  83 */       Color c = g.getColor();
/*  84 */       g.setColor(this.background);
/*  85 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  86 */       g.setColor(c);
/*     */     } 
/*  88 */     GL.glPushMatrix();
/*  89 */     this.prev.render(container, game, g);
/*  90 */     GL.glPopMatrix();
/*  91 */     g.clearClip();
/*  92 */     g.translate(-this.offset, 0.0F);
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
/* 107 */     this.offset += delta * 1.0F;
/* 108 */     if (this.offset > (container.getWidth() / 2))
/* 109 */       this.finish = true; 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\HorizontalSplitTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */