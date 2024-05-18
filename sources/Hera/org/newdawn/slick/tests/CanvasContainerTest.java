/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.CanvasGameContainer;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CanvasContainerTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Image tga;
/*     */   private Image scaleMe;
/*     */   private Image scaled;
/*     */   private Image gif;
/*     */   private Image image;
/*     */   private Image subImage;
/*     */   private float rot;
/*     */   
/*     */   public CanvasContainerTest() {
/*  41 */     super("Canvas Container Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  48 */     this.image = this.tga = new Image("testdata/logo.tga");
/*  49 */     this.scaleMe = new Image("testdata/logo.tga", true, 2);
/*  50 */     this.gif = new Image("testdata/logo.gif");
/*  51 */     this.scaled = this.gif.getScaledCopy(120, 120);
/*  52 */     this.subImage = this.image.getSubImage(200, 0, 70, 260);
/*  53 */     this.rot = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  60 */     this.image.draw(0.0F, 0.0F);
/*  61 */     this.image.draw(500.0F, 0.0F, 200.0F, 100.0F);
/*  62 */     this.scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
/*  63 */     this.scaled.draw(400.0F, 500.0F);
/*  64 */     Image flipped = this.scaled.getFlippedCopy(true, false);
/*  65 */     flipped.draw(520.0F, 500.0F);
/*  66 */     Image flipped2 = flipped.getFlippedCopy(false, true);
/*  67 */     flipped2.draw(520.0F, 380.0F);
/*  68 */     Image flipped3 = flipped2.getFlippedCopy(true, false);
/*  69 */     flipped3.draw(400.0F, 380.0F);
/*     */     
/*  71 */     for (int i = 0; i < 3; i++) {
/*  72 */       this.subImage.draw((200 + i * 30), 300.0F);
/*     */     }
/*     */     
/*  75 */     g.translate(500.0F, 200.0F);
/*  76 */     g.rotate(50.0F, 50.0F, this.rot);
/*  77 */     g.scale(0.3F, 0.3F);
/*  78 */     this.image.draw();
/*  79 */     g.resetTransform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  86 */     this.rot += delta * 0.1F;
/*  87 */     if (this.rot > 360.0F) {
/*  88 */       this.rot -= 360.0F;
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
/*  99 */       CanvasGameContainer container = new CanvasGameContainer((Game)new CanvasContainerTest());
/*     */       
/* 101 */       Frame frame = new Frame("Test");
/* 102 */       frame.setLayout(new GridLayout(1, 2));
/* 103 */       frame.setSize(500, 500);
/* 104 */       frame.add((Component)container);
/*     */       
/* 106 */       frame.addWindowListener(new WindowAdapter() {
/*     */             public void windowClosing(WindowEvent e) {
/* 108 */               System.exit(0);
/*     */             }
/*     */           });
/* 111 */       frame.setVisible(true);
/* 112 */       container.start();
/* 113 */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 122 */     if (key == 57)
/* 123 */       if (this.image == this.gif) {
/* 124 */         this.image = this.tga;
/*     */       } else {
/* 126 */         this.image = this.gif;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\CanvasContainerTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */