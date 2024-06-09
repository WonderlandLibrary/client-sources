/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformTest2
/*     */   extends BasicGame
/*     */ {
/*  18 */   private float scale = 1.0F;
/*     */ 
/*     */   
/*     */   private boolean scaleUp;
/*     */   
/*     */   private boolean scaleDown;
/*     */   
/*  25 */   private float camX = 320.0F;
/*     */   
/*  27 */   private float camY = 240.0F;
/*     */ 
/*     */   
/*     */   private boolean moveLeft;
/*     */ 
/*     */   
/*     */   private boolean moveUp;
/*     */ 
/*     */   
/*     */   private boolean moveRight;
/*     */   
/*     */   private boolean moveDown;
/*     */ 
/*     */   
/*     */   public TransformTest2() {
/*  42 */     super("Transform Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  49 */     container.setTargetFrameRate(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer contiainer, Graphics g) {
/*  56 */     g.translate(320.0F, 240.0F);
/*     */     
/*  58 */     g.translate(-this.camX * this.scale, -this.camY * this.scale);
/*     */ 
/*     */     
/*  61 */     g.scale(this.scale, this.scale);
/*     */     
/*  63 */     g.setColor(Color.red);
/*  64 */     for (int x = 0; x < 10; x++) {
/*  65 */       for (int y = 0; y < 10; y++) {
/*  66 */         g.fillRect((-500 + x * 100), (-500 + y * 100), 80.0F, 80.0F);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
/*  71 */     g.fillRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*  72 */     g.setColor(Color.white);
/*  73 */     g.drawRect(-320.0F, -240.0F, 640.0F, 480.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  80 */     if (this.scaleUp) {
/*  81 */       this.scale += delta * 0.001F;
/*     */     }
/*  83 */     if (this.scaleDown) {
/*  84 */       this.scale -= delta * 0.001F;
/*     */     }
/*     */     
/*  87 */     float moveSpeed = delta * 0.4F * 1.0F / this.scale;
/*     */     
/*  89 */     if (this.moveLeft) {
/*  90 */       this.camX -= moveSpeed;
/*     */     }
/*  92 */     if (this.moveUp) {
/*  93 */       this.camY -= moveSpeed;
/*     */     }
/*  95 */     if (this.moveRight) {
/*  96 */       this.camX += moveSpeed;
/*     */     }
/*  98 */     if (this.moveDown) {
/*  99 */       this.camY += moveSpeed;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 107 */     if (key == 1) {
/* 108 */       System.exit(0);
/*     */     }
/* 110 */     if (key == 16) {
/* 111 */       this.scaleUp = true;
/*     */     }
/* 113 */     if (key == 30) {
/* 114 */       this.scaleDown = true;
/*     */     }
/*     */     
/* 117 */     if (key == 203) {
/* 118 */       this.moveLeft = true;
/*     */     }
/* 120 */     if (key == 200) {
/* 121 */       this.moveUp = true;
/*     */     }
/* 123 */     if (key == 205) {
/* 124 */       this.moveRight = true;
/*     */     }
/* 126 */     if (key == 208) {
/* 127 */       this.moveDown = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(int key, char c) {
/* 135 */     if (key == 16) {
/* 136 */       this.scaleUp = false;
/*     */     }
/* 138 */     if (key == 30) {
/* 139 */       this.scaleDown = false;
/*     */     }
/*     */     
/* 142 */     if (key == 203) {
/* 143 */       this.moveLeft = false;
/*     */     }
/* 145 */     if (key == 200) {
/* 146 */       this.moveUp = false;
/*     */     }
/* 148 */     if (key == 205) {
/* 149 */       this.moveRight = false;
/*     */     }
/* 151 */     if (key == 208) {
/* 152 */       this.moveDown = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 163 */       AppGameContainer container = new AppGameContainer((Game)new TransformTest2());
/* 164 */       container.setDisplayMode(640, 480, false);
/* 165 */       container.start();
/* 166 */     } catch (SlickException e) {
/* 167 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TransformTest2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */