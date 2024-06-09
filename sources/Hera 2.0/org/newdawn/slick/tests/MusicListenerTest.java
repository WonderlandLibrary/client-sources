/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Music;
/*     */ import org.newdawn.slick.MusicListener;
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
/*     */ public class MusicListenerTest
/*     */   extends BasicGame
/*     */   implements MusicListener
/*     */ {
/*     */   private boolean musicEnded = false;
/*     */   private boolean musicSwapped = false;
/*     */   private Music music;
/*     */   private Music stream;
/*     */   
/*     */   public MusicListenerTest() {
/*  31 */     super("Music Listener Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  38 */     this.music = new Music("testdata/restart.ogg", false);
/*  39 */     this.stream = new Music("testdata/restart.ogg", false);
/*     */     
/*  41 */     this.music.addListener(this);
/*  42 */     this.stream.addListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void musicEnded(Music music) {
/*  55 */     this.musicEnded = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void musicSwapped(Music music, Music newMusic) {
/*  62 */     this.musicSwapped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  69 */     g.drawString("Press M to play music", 100.0F, 100.0F);
/*  70 */     g.drawString("Press S to stream music", 100.0F, 150.0F);
/*  71 */     if (this.musicEnded) {
/*  72 */       g.drawString("Music Ended", 100.0F, 200.0F);
/*     */     }
/*  74 */     if (this.musicSwapped) {
/*  75 */       g.drawString("Music Swapped", 100.0F, 250.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  83 */     if (key == 50) {
/*  84 */       this.musicEnded = false;
/*  85 */       this.musicSwapped = false;
/*  86 */       this.music.play();
/*     */     } 
/*  88 */     if (key == 31) {
/*  89 */       this.musicEnded = false;
/*  90 */       this.musicSwapped = false;
/*  91 */       this.stream.play();
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
/* 102 */       AppGameContainer container = new AppGameContainer((Game)new MusicListenerTest());
/* 103 */       container.setDisplayMode(800, 600, false);
/* 104 */       container.start();
/* 105 */     } catch (SlickException e) {
/* 106 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\MusicListenerTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */