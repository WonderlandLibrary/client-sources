/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.Music;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.Sound;
/*     */ import org.newdawn.slick.openal.Audio;
/*     */ import org.newdawn.slick.openal.AudioLoader;
/*     */ import org.newdawn.slick.openal.SoundStore;
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
/*     */ 
/*     */ 
/*     */ public class SoundTest
/*     */   extends BasicGame
/*     */ {
/*     */   private GameContainer myContainer;
/*     */   private Sound sound;
/*     */   private Sound charlie;
/*     */   private Sound burp;
/*     */   private Music music;
/*     */   private Music musica;
/*     */   private Music musicb;
/*     */   private Audio engine;
/*  43 */   private int volume = 10;
/*     */ 
/*     */   
/*  46 */   private int[] engines = new int[3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundTest() {
/*  52 */     super("Sound And Music Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  59 */     SoundStore.get().setMaxSources(32);
/*     */     
/*  61 */     this.myContainer = container;
/*  62 */     this.sound = new Sound("testdata/restart.ogg");
/*  63 */     this.charlie = new Sound("testdata/cbrown01.wav");
/*     */     try {
/*  65 */       this.engine = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("testdata/engine.wav"));
/*  66 */     } catch (IOException e) {
/*  67 */       throw new SlickException("Failed to load engine", e);
/*     */     } 
/*  69 */     this.music = this.musica = new Music("testdata/SMB-X.XM");
/*     */     
/*  71 */     this.musicb = new Music("testdata/kirby.ogg", true);
/*  72 */     this.burp = new Sound("testdata/burp.aif");
/*     */     
/*  74 */     this.music.play();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  81 */     g.setColor(Color.white);
/*  82 */     g.drawString("The OGG loop is now streaming from the file, woot.", 100.0F, 60.0F);
/*  83 */     g.drawString("Press space for sound effect (OGG)", 100.0F, 100.0F);
/*  84 */     g.drawString("Press P to pause/resume music (XM)", 100.0F, 130.0F);
/*  85 */     g.drawString("Press E to pause/resume engine sound (WAV)", 100.0F, 190.0F);
/*  86 */     g.drawString("Press enter for charlie (WAV)", 100.0F, 160.0F);
/*  87 */     g.drawString("Press C to change music", 100.0F, 210.0F);
/*  88 */     g.drawString("Press B to burp (AIF)", 100.0F, 240.0F);
/*  89 */     g.drawString("Press + or - to change global volume of music", 100.0F, 270.0F);
/*  90 */     g.drawString("Press Y or X to change individual volume of music", 100.0F, 300.0F);
/*  91 */     g.drawString("Press N or M to change global volume of sound fx", 100.0F, 330.0F);
/*  92 */     g.setColor(Color.blue);
/*  93 */     g.drawString("Global Sound Volume Level: " + container.getSoundVolume(), 150.0F, 390.0F);
/*  94 */     g.drawString("Global Music Volume Level: " + container.getMusicVolume(), 150.0F, 420.0F);
/*  95 */     g.drawString("Current Music Volume Level: " + this.music.getVolume(), 150.0F, 450.0F);
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
/* 108 */     if (key == 1) {
/* 109 */       System.exit(0);
/*     */     }
/* 111 */     if (key == 57) {
/* 112 */       this.sound.play();
/*     */     }
/* 114 */     if (key == 48) {
/* 115 */       this.burp.play();
/*     */     }
/* 117 */     if (key == 30) {
/* 118 */       this.sound.playAt(-1.0F, 0.0F, 0.0F);
/*     */     }
/* 120 */     if (key == 38) {
/* 121 */       this.sound.playAt(1.0F, 0.0F, 0.0F);
/*     */     }
/* 123 */     if (key == 28) {
/* 124 */       this.charlie.play(1.0F, 1.0F);
/*     */     }
/* 126 */     if (key == 25) {
/* 127 */       if (this.music.playing()) {
/* 128 */         this.music.pause();
/*     */       } else {
/* 130 */         this.music.resume();
/*     */       } 
/*     */     }
/* 133 */     if (key == 46) {
/* 134 */       this.music.stop();
/* 135 */       if (this.music == this.musica) {
/* 136 */         this.music = this.musicb;
/*     */       } else {
/* 138 */         this.music = this.musica;
/*     */       } 
/*     */       
/* 141 */       this.music.loop();
/*     */     } 
/* 143 */     for (int i = 0; i < 3; i++) {
/* 144 */       if (key == 2 + i) {
/* 145 */         if (this.engines[i] != 0) {
/* 146 */           System.out.println("Stop " + i);
/* 147 */           SoundStore.get().stopSoundEffect(this.engines[i]);
/* 148 */           this.engines[i] = 0;
/*     */         } else {
/* 150 */           System.out.println("Start " + i);
/* 151 */           this.engines[i] = this.engine.playAsSoundEffect(1.0F, 1.0F, true);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if (c == '+') {
/* 157 */       this.volume++;
/* 158 */       setVolume();
/*     */     } 
/*     */     
/* 161 */     if (c == '-') {
/* 162 */       this.volume--;
/* 163 */       setVolume();
/*     */     } 
/*     */     
/* 166 */     if (key == 21) {
/* 167 */       int vol = (int)(this.music.getVolume() * 10.0F);
/* 168 */       vol--;
/* 169 */       if (vol < 0) vol = 0;
/*     */       
/* 171 */       this.music.setVolume(vol / 10.0F);
/*     */     } 
/* 173 */     if (key == 45) {
/* 174 */       int vol = (int)(this.music.getVolume() * 10.0F);
/* 175 */       vol++;
/* 176 */       if (vol > 10) vol = 10;
/*     */       
/* 178 */       this.music.setVolume(vol / 10.0F);
/*     */     } 
/* 180 */     if (key == 49) {
/* 181 */       int vol = (int)(this.myContainer.getSoundVolume() * 10.0F);
/* 182 */       vol--;
/* 183 */       if (vol < 0) vol = 0;
/*     */       
/* 185 */       this.myContainer.setSoundVolume(vol / 10.0F);
/*     */     } 
/* 187 */     if (key == 50) {
/* 188 */       int vol = (int)(this.myContainer.getSoundVolume() * 10.0F);
/* 189 */       vol++;
/* 190 */       if (vol > 10) vol = 10;
/*     */       
/* 192 */       this.myContainer.setSoundVolume(vol / 10.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setVolume() {
/* 202 */     if (this.volume > 10) {
/* 203 */       this.volume = 10;
/* 204 */     } else if (this.volume < 0) {
/* 205 */       this.volume = 0;
/*     */     } 
/*     */     
/* 208 */     this.myContainer.setMusicVolume(this.volume / 10.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 218 */       AppGameContainer container = new AppGameContainer((Game)new SoundTest());
/* 219 */       container.setDisplayMode(800, 600, false);
/* 220 */       container.start();
/* 221 */     } catch (SlickException e) {
/* 222 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\SoundTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */