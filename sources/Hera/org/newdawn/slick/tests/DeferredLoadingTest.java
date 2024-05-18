/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.newdawn.slick.AngelCodeFont;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Font;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.Music;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.Sound;
/*     */ import org.newdawn.slick.loading.DeferredResource;
/*     */ import org.newdawn.slick.loading.LoadingList;
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
/*     */ 
/*     */ public class DeferredLoadingTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Music music;
/*     */   private Sound sound;
/*     */   private Image image;
/*     */   private Font font;
/*     */   private DeferredResource nextResource;
/*     */   private boolean started;
/*     */   
/*     */   public DeferredLoadingTest() {
/*  43 */     super("Deferred Loading Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  50 */     LoadingList.setDeferredLoading(true);
/*     */     
/*  52 */     new Sound("testdata/cbrown01.wav");
/*  53 */     new Sound("testdata/engine.wav");
/*  54 */     this.sound = new Sound("testdata/restart.ogg");
/*  55 */     new Music("testdata/testloop.ogg");
/*  56 */     this.music = new Music("testdata/SMB-X.XM");
/*     */     
/*  58 */     new Image("testdata/cursor.png");
/*  59 */     new Image("testdata/cursor.tga");
/*  60 */     new Image("testdata/cursor.png");
/*  61 */     new Image("testdata/cursor.png");
/*  62 */     new Image("testdata/dungeontiles.gif");
/*  63 */     new Image("testdata/logo.gif");
/*  64 */     this.image = new Image("testdata/logo.tga");
/*  65 */     new Image("testdata/logo.png");
/*  66 */     new Image("testdata/rocket.png");
/*  67 */     new Image("testdata/testpack.png");
/*     */     
/*  69 */     this.font = (Font)new AngelCodeFont("testdata/demo.fnt", "testdata/demo_00.tga");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  76 */     if (this.nextResource != null) {
/*  77 */       g.drawString("Loading: " + this.nextResource.getDescription(), 100.0F, 100.0F);
/*     */     }
/*     */     
/*  80 */     int total = LoadingList.get().getTotalResources();
/*  81 */     int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
/*     */     
/*  83 */     float bar = loaded / total;
/*  84 */     g.fillRect(100.0F, 150.0F, (loaded * 40), 20.0F);
/*  85 */     g.drawRect(100.0F, 150.0F, (total * 40), 20.0F);
/*     */     
/*  87 */     if (this.started) {
/*  88 */       this.image.draw(100.0F, 200.0F);
/*  89 */       this.font.drawString(100.0F, 500.0F, "LOADING COMPLETE");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/*  97 */     if (this.nextResource != null) {
/*     */       try {
/*  99 */         this.nextResource.load();
/*     */         
/* 101 */         try { Thread.sleep(50L); } catch (Exception e) {}
/* 102 */       } catch (IOException e) {
/* 103 */         throw new SlickException("Failed to load: " + this.nextResource.getDescription(), e);
/*     */       } 
/*     */       
/* 106 */       this.nextResource = null;
/*     */     } 
/*     */     
/* 109 */     if (LoadingList.get().getRemainingResources() > 0) {
/* 110 */       this.nextResource = LoadingList.get().getNext();
/*     */     }
/* 112 */     else if (!this.started) {
/* 113 */       this.started = true;
/* 114 */       this.music.loop();
/* 115 */       this.sound.play();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 127 */       AppGameContainer container = new AppGameContainer((Game)new DeferredLoadingTest());
/* 128 */       container.setDisplayMode(800, 600, false);
/* 129 */       container.start();
/* 130 */     } catch (SlickException e) {
/* 131 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void keyPressed(int key, char c) {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\DeferredLoadingTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */