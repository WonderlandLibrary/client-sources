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
/*     */ 
/*     */ public class SelectTransition
/*     */   implements Transition
/*     */ {
/*  23 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   private GameState prev;
/*     */ 
/*     */   
/*     */   private boolean finish;
/*     */   
/*     */   private Color background;
/*     */   
/*  33 */   private float scale1 = 1.0F;
/*     */   
/*  35 */   private float xp1 = 0.0F;
/*     */   
/*  37 */   private float yp1 = 0.0F;
/*     */   
/*  39 */   private float scale2 = 0.4F;
/*     */   
/*  41 */   private float xp2 = 0.0F;
/*     */   
/*  43 */   private float yp2 = 0.0F;
/*     */ 
/*     */   
/*     */   private boolean init = false;
/*     */   
/*     */   private boolean moveBackDone = false;
/*     */   
/*  50 */   private int pause = 300;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectTransition() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectTransition(Color background) {
/*  65 */     this.background = background;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameState firstState, GameState secondState) {
/*  72 */     this.prev = secondState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  79 */     return this.finish;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/*  86 */     g.resetTransform();
/*     */     
/*  88 */     if (!this.moveBackDone) {
/*  89 */       g.translate(this.xp1, this.yp1);
/*  90 */       g.scale(this.scale1, this.scale1);
/*  91 */       g.setClip((int)this.xp1, (int)this.yp1, (int)(this.scale1 * container.getWidth()), (int)(this.scale1 * container.getHeight()));
/*  92 */       this.prev.render(container, game, g);
/*  93 */       g.resetTransform();
/*  94 */       g.clearClip();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/* 103 */     if (this.moveBackDone) {
/* 104 */       g.translate(this.xp1, this.yp1);
/* 105 */       g.scale(this.scale1, this.scale1);
/* 106 */       g.setClip((int)this.xp1, (int)this.yp1, (int)(this.scale1 * container.getWidth()), (int)(this.scale1 * container.getHeight()));
/* 107 */       this.prev.render(container, game, g);
/* 108 */       g.resetTransform();
/* 109 */       g.clearClip();
/*     */     } 
/*     */     
/* 112 */     g.translate(this.xp2, this.yp2);
/* 113 */     g.scale(this.scale2, this.scale2);
/* 114 */     g.setClip((int)this.xp2, (int)this.yp2, (int)(this.scale2 * container.getWidth()), (int)(this.scale2 * container.getHeight()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
/* 122 */     if (!this.init) {
/* 123 */       this.init = true;
/* 124 */       this.xp2 = (container.getWidth() / 2 + 50);
/* 125 */       this.yp2 = (container.getHeight() / 4);
/*     */     } 
/*     */     
/* 128 */     if (!this.moveBackDone) {
/* 129 */       if (this.scale1 > 0.4F) {
/* 130 */         this.scale1 -= delta * 0.002F;
/* 131 */         if (this.scale1 <= 0.4F) {
/* 132 */           this.scale1 = 0.4F;
/*     */         }
/* 134 */         this.xp1 += delta * 0.3F;
/* 135 */         if (this.xp1 > 50.0F) {
/* 136 */           this.xp1 = 50.0F;
/*     */         }
/* 138 */         this.yp1 += delta * 0.5F;
/* 139 */         if (this.yp1 > (container.getHeight() / 4)) {
/* 140 */           this.yp1 = (container.getHeight() / 4);
/*     */         }
/*     */       } else {
/* 143 */         this.moveBackDone = true;
/*     */       } 
/*     */     } else {
/* 146 */       this.pause -= delta;
/* 147 */       if (this.pause > 0) {
/*     */         return;
/*     */       }
/* 150 */       if (this.scale2 < 1.0F) {
/* 151 */         this.scale2 += delta * 0.002F;
/* 152 */         if (this.scale2 >= 1.0F) {
/* 153 */           this.scale2 = 1.0F;
/*     */         }
/* 155 */         this.xp2 -= delta * 1.5F;
/* 156 */         if (this.xp2 < 0.0F) {
/* 157 */           this.xp2 = 0.0F;
/*     */         }
/* 159 */         this.yp2 -= delta * 0.5F;
/* 160 */         if (this.yp2 < 0.0F) {
/* 161 */           this.yp2 = 0.0F;
/*     */         }
/*     */       } else {
/* 164 */         this.finish = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\SelectTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */