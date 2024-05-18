/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import org.newdawn.slick.opengl.SlickCallable;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalableGame
/*     */   implements Game
/*     */ {
/*  19 */   private static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   private float normalWidth;
/*     */ 
/*     */   
/*     */   private float normalHeight;
/*     */ 
/*     */   
/*     */   private Game held;
/*     */ 
/*     */   
/*     */   private boolean maintainAspect;
/*     */ 
/*     */   
/*     */   private int targetWidth;
/*     */ 
/*     */   
/*     */   private int targetHeight;
/*     */ 
/*     */   
/*     */   private GameContainer container;
/*     */ 
/*     */   
/*     */   public ScalableGame(Game held, int normalWidth, int normalHeight) {
/*  44 */     this(held, normalWidth, normalHeight, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalableGame(Game held, int normalWidth, int normalHeight, boolean maintainAspect) {
/*  56 */     this.held = held;
/*  57 */     this.normalWidth = normalWidth;
/*  58 */     this.normalHeight = normalHeight;
/*  59 */     this.maintainAspect = maintainAspect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  66 */     this.container = container;
/*     */     
/*  68 */     recalculateScale();
/*  69 */     this.held.init(container);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculateScale() throws SlickException {
/*  78 */     this.targetWidth = this.container.getWidth();
/*  79 */     this.targetHeight = this.container.getHeight();
/*     */     
/*  81 */     if (this.maintainAspect) {
/*  82 */       boolean normalIsWide = ((this.normalWidth / this.normalHeight) > 1.6D);
/*  83 */       boolean containerIsWide = ((this.targetWidth / this.targetHeight) > 1.6D);
/*  84 */       float wScale = this.targetWidth / this.normalWidth;
/*  85 */       float hScale = this.targetHeight / this.normalHeight;
/*     */       
/*  87 */       if (normalIsWide & containerIsWide) {
/*  88 */         float scale = (wScale < hScale) ? wScale : hScale;
/*  89 */         this.targetWidth = (int)(this.normalWidth * scale);
/*  90 */         this.targetHeight = (int)(this.normalHeight * scale);
/*  91 */       } else if ((normalIsWide & (!containerIsWide ? 1 : 0)) != 0) {
/*  92 */         this.targetWidth = (int)(this.normalWidth * wScale);
/*  93 */         this.targetHeight = (int)(this.normalHeight * wScale);
/*  94 */       } else if (((!normalIsWide ? 1 : 0) & containerIsWide) != 0) {
/*  95 */         this.targetWidth = (int)(this.normalWidth * hScale);
/*  96 */         this.targetHeight = (int)(this.normalHeight * hScale);
/*     */       } else {
/*  98 */         float scale = (wScale < hScale) ? wScale : hScale;
/*  99 */         this.targetWidth = (int)(this.normalWidth * scale);
/* 100 */         this.targetHeight = (int)(this.normalHeight * scale);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 105 */     if (this.held instanceof InputListener) {
/* 106 */       this.container.getInput().addListener((InputListener)this.held);
/*     */     }
/* 108 */     this.container.getInput().setScale(this.normalWidth / this.targetWidth, this.normalHeight / this.targetHeight);
/*     */ 
/*     */ 
/*     */     
/* 112 */     int yoffset = 0;
/* 113 */     int xoffset = 0;
/*     */     
/* 115 */     if (this.targetHeight < this.container.getHeight()) {
/* 116 */       yoffset = (this.container.getHeight() - this.targetHeight) / 2;
/*     */     }
/* 118 */     if (this.targetWidth < this.container.getWidth()) {
/* 119 */       xoffset = (this.container.getWidth() - this.targetWidth) / 2;
/*     */     }
/* 121 */     this.container.getInput().setOffset(-xoffset / this.targetWidth / this.normalWidth, -yoffset / this.targetHeight / this.normalHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/* 130 */     if (this.targetHeight != container.getHeight() || this.targetWidth != container.getWidth())
/*     */     {
/* 132 */       recalculateScale();
/*     */     }
/*     */     
/* 135 */     this.held.update(container, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void render(GameContainer container, Graphics g) throws SlickException {
/* 143 */     int yoffset = 0;
/* 144 */     int xoffset = 0;
/*     */     
/* 146 */     if (this.targetHeight < container.getHeight()) {
/* 147 */       yoffset = (container.getHeight() - this.targetHeight) / 2;
/*     */     }
/* 149 */     if (this.targetWidth < container.getWidth()) {
/* 150 */       xoffset = (container.getWidth() - this.targetWidth) / 2;
/*     */     }
/*     */     
/* 153 */     SlickCallable.enterSafeBlock();
/* 154 */     g.setClip(xoffset, yoffset, this.targetWidth, this.targetHeight);
/* 155 */     GL.glTranslatef(xoffset, yoffset, 0.0F);
/* 156 */     g.scale(this.targetWidth / this.normalWidth, this.targetHeight / this.normalHeight);
/* 157 */     GL.glPushMatrix();
/* 158 */     this.held.render(container, g);
/* 159 */     GL.glPopMatrix();
/* 160 */     g.clearClip();
/* 161 */     SlickCallable.leaveSafeBlock();
/*     */     
/* 163 */     renderOverlay(container, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderOverlay(GameContainer container, Graphics g) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closeRequested() {
/* 179 */     return this.held.closeRequested();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 186 */     return this.held.getTitle();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\ScalableGame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */