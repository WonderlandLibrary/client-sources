/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import org.newdawn.slick.openal.Audio;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.util.Log;
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
/*     */ public class Sound
/*     */ {
/*     */   private Audio sound;
/*     */   
/*     */   public Sound(InputStream in, String ref) throws SlickException {
/*  28 */     SoundStore.get().init();
/*     */     
/*     */     try {
/*  31 */       if (ref.toLowerCase().endsWith(".ogg")) {
/*  32 */         this.sound = SoundStore.get().getOgg(in);
/*  33 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/*  34 */         this.sound = SoundStore.get().getWAV(in);
/*  35 */       } else if (ref.toLowerCase().endsWith(".aif")) {
/*  36 */         this.sound = SoundStore.get().getAIF(in);
/*  37 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/*  38 */         this.sound = SoundStore.get().getMOD(in);
/*     */       } else {
/*  40 */         throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
/*     */       } 
/*  42 */     } catch (Exception e) {
/*  43 */       Log.error(e);
/*  44 */       throw new SlickException("Failed to load sound: " + ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sound(URL url) throws SlickException {
/*  55 */     SoundStore.get().init();
/*  56 */     String ref = url.getFile();
/*     */     
/*     */     try {
/*  59 */       if (ref.toLowerCase().endsWith(".ogg")) {
/*  60 */         this.sound = SoundStore.get().getOgg(url.openStream());
/*  61 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/*  62 */         this.sound = SoundStore.get().getWAV(url.openStream());
/*  63 */       } else if (ref.toLowerCase().endsWith(".aif")) {
/*  64 */         this.sound = SoundStore.get().getAIF(url.openStream());
/*  65 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/*  66 */         this.sound = SoundStore.get().getMOD(url.openStream());
/*     */       } else {
/*  68 */         throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
/*     */       } 
/*  70 */     } catch (Exception e) {
/*  71 */       Log.error(e);
/*  72 */       throw new SlickException("Failed to load sound: " + ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sound(String ref) throws SlickException {
/*  83 */     SoundStore.get().init();
/*     */     
/*     */     try {
/*  86 */       if (ref.toLowerCase().endsWith(".ogg")) {
/*  87 */         this.sound = SoundStore.get().getOgg(ref);
/*  88 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/*  89 */         this.sound = SoundStore.get().getWAV(ref);
/*  90 */       } else if (ref.toLowerCase().endsWith(".aif")) {
/*  91 */         this.sound = SoundStore.get().getAIF(ref);
/*  92 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/*  93 */         this.sound = SoundStore.get().getMOD(ref);
/*     */       } else {
/*  95 */         throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
/*     */       } 
/*  97 */     } catch (Exception e) {
/*  98 */       Log.error(e);
/*  99 */       throw new SlickException("Failed to load sound: " + ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/* 107 */     play(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play(float pitch, float volume) {
/* 117 */     this.sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playAt(float x, float y, float z) {
/* 128 */     playAt(1.0F, 1.0F, x, y, z);
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
/*     */   
/*     */   public void playAt(float pitch, float volume, float x, float y, float z) {
/* 141 */     this.sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loop() {
/* 147 */     loop(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loop(float pitch, float volume) {
/* 157 */     this.sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean playing() {
/* 166 */     return this.sound.isPlaying();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 173 */     this.sound.stop();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Sound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */