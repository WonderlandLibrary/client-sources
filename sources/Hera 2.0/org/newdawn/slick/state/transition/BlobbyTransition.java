/*     */ package org.newdawn.slick.state.transition;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ import org.newdawn.slick.state.GameState;
/*     */ import org.newdawn.slick.state.StateBasedGame;
/*     */ import org.newdawn.slick.util.MaskUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlobbyTransition
/*     */   implements Transition
/*     */ {
/*  25 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   private GameState prev;
/*     */   
/*     */   private boolean finish;
/*     */   
/*     */   private Color background;
/*     */   
/*  34 */   private ArrayList blobs = new ArrayList();
/*     */   
/*  36 */   private int timer = 1000;
/*     */   
/*  38 */   private int blobCount = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlobbyTransition() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlobbyTransition(Color background) {
/*  53 */     this.background = background;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameState firstState, GameState secondState) {
/*  60 */     this.prev = secondState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  67 */     return this.finish;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/*  74 */     MaskUtil.resetMask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
/*  82 */     this.prev.render(container, game, g);
/*     */     
/*  84 */     MaskUtil.defineMask();
/*  85 */     for (int i = 0; i < this.blobs.size(); i++) {
/*  86 */       ((Blob)this.blobs.get(i)).render(g);
/*     */     }
/*  88 */     MaskUtil.finishDefineMask();
/*     */     
/*  90 */     MaskUtil.drawOnMask();
/*  91 */     if (this.background != null) {
/*  92 */       Color c = g.getColor();
/*  93 */       g.setColor(this.background);
/*  94 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  95 */       g.setColor(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
/* 104 */     if (this.blobs.size() == 0) {
/* 105 */       for (int j = 0; j < this.blobCount; j++) {
/* 106 */         this.blobs.add(new Blob(container));
/*     */       }
/*     */     }
/*     */     
/* 110 */     for (int i = 0; i < this.blobs.size(); i++) {
/* 111 */       ((Blob)this.blobs.get(i)).update(delta);
/*     */     }
/*     */     
/* 114 */     this.timer -= delta;
/* 115 */     if (this.timer < 0) {
/* 116 */       this.finish = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Blob
/*     */   {
/*     */     private float x;
/*     */ 
/*     */ 
/*     */     
/*     */     private float y;
/*     */ 
/*     */ 
/*     */     
/*     */     private float growSpeed;
/*     */ 
/*     */     
/*     */     private float rad;
/*     */ 
/*     */ 
/*     */     
/*     */     public Blob(GameContainer container) {
/* 141 */       this.x = (float)(Math.random() * container.getWidth());
/* 142 */       this.y = (float)(Math.random() * container.getWidth());
/* 143 */       this.growSpeed = (float)(1.0D + Math.random() * 1.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(int delta) {
/* 152 */       this.rad += this.growSpeed * delta * 0.4F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void render(Graphics g) {
/* 161 */       g.fillOval(this.x - this.rad, this.y - this.rad, this.rad * 2.0F, this.rad * 2.0F);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\BlobbyTransition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */