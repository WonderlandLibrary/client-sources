/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Music;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.Sound;
/*     */ import org.newdawn.slick.util.ResourceLoader;
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
/*     */ public class SoundURLTest
/*     */   extends BasicGame
/*     */ {
/*     */   private Sound sound;
/*     */   private Sound charlie;
/*     */   private Sound burp;
/*     */   private Music music;
/*     */   private Music musica;
/*     */   private Music musicb;
/*     */   private Sound engine;
/*  36 */   private int volume = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundURLTest() {
/*  42 */     super("Sound URL Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  49 */     this.sound = new Sound(ResourceLoader.getResource("testdata/restart.ogg"));
/*  50 */     this.charlie = new Sound(ResourceLoader.getResource("testdata/cbrown01.wav"));
/*  51 */     this.engine = new Sound(ResourceLoader.getResource("testdata/engine.wav"));
/*     */     
/*  53 */     this.music = this.musica = new Music(ResourceLoader.getResource("testdata/restart.ogg"), false);
/*  54 */     this.musicb = new Music(ResourceLoader.getResource("testdata/kirby.ogg"), false);
/*  55 */     this.burp = new Sound(ResourceLoader.getResource("testdata/burp.aif"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  62 */     g.setColor(Color.white);
/*  63 */     g.drawString("The OGG loop is now streaming from the file, woot.", 100.0F, 60.0F);
/*  64 */     g.drawString("Press space for sound effect (OGG)", 100.0F, 100.0F);
/*  65 */     g.drawString("Press P to pause/resume music (XM)", 100.0F, 130.0F);
/*  66 */     g.drawString("Press E to pause/resume engine sound (WAV)", 100.0F, 190.0F);
/*  67 */     g.drawString("Press enter for charlie (WAV)", 100.0F, 160.0F);
/*  68 */     g.drawString("Press C to change music", 100.0F, 210.0F);
/*  69 */     g.drawString("Press B to burp (AIF)", 100.0F, 240.0F);
/*  70 */     g.drawString("Press + or - to change volume of music", 100.0F, 270.0F);
/*  71 */     g.setColor(Color.blue);
/*  72 */     g.drawString("Music Volume Level: " + (this.volume / 10.0F), 150.0F, 300.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/*  85 */     if (key == 1) {
/*  86 */       System.exit(0);
/*     */     }
/*  88 */     if (key == 57) {
/*  89 */       this.sound.play();
/*     */     }
/*  91 */     if (key == 48) {
/*  92 */       this.burp.play();
/*     */     }
/*  94 */     if (key == 30) {
/*  95 */       this.sound.playAt(-1.0F, 0.0F, 0.0F);
/*     */     }
/*  97 */     if (key == 38) {
/*  98 */       this.sound.playAt(1.0F, 0.0F, 0.0F);
/*     */     }
/* 100 */     if (key == 28) {
/* 101 */       this.charlie.play(1.0F, 1.0F);
/*     */     }
/* 103 */     if (key == 25) {
/* 104 */       if (this.music.playing()) {
/* 105 */         this.music.pause();
/*     */       } else {
/* 107 */         this.music.resume();
/*     */       } 
/*     */     }
/* 110 */     if (key == 46) {
/* 111 */       this.music.stop();
/* 112 */       if (this.music == this.musica) {
/* 113 */         this.music = this.musicb;
/*     */       } else {
/* 115 */         this.music = this.musica;
/*     */       } 
/*     */       
/* 118 */       this.music.loop();
/*     */     } 
/* 120 */     if (key == 18) {
/* 121 */       if (this.engine.playing()) {
/* 122 */         this.engine.stop();
/*     */       } else {
/* 124 */         this.engine.loop();
/*     */       } 
/*     */     }
/*     */     
/* 128 */     if (c == '+') {
/* 129 */       this.volume++;
/* 130 */       setVolume();
/*     */     } 
/*     */     
/* 133 */     if (c == '-') {
/* 134 */       this.volume--;
/* 135 */       setVolume();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setVolume() {
/* 145 */     if (this.volume > 10) {
/* 146 */       this.volume = 10;
/* 147 */     } else if (this.volume < 0) {
/* 148 */       this.volume = 0;
/*     */     } 
/*     */     
/* 151 */     this.music.setVolume(this.volume / 10.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 161 */       AppGameContainer container = new AppGameContainer((Game)new SoundURLTest());
/* 162 */       container.setDisplayMode(800, 600, false);
/* 163 */       container.start();
/* 164 */     } catch (SlickException e) {
/* 165 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\SoundURLTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */