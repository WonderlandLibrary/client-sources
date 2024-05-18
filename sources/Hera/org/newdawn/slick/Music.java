/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.openal.Audio;
/*     */ import org.newdawn.slick.openal.AudioImpl;
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
/*     */ public class Music
/*     */ {
/*     */   private static Music currentMusic;
/*     */   private Audio sound;
/*     */   private boolean playing;
/*     */   
/*     */   public static void poll(int delta) {
/*  31 */     if (currentMusic != null) {
/*  32 */       SoundStore.get().poll(delta);
/*  33 */       if (!SoundStore.get().isMusicPlaying()) {
/*  34 */         if (!currentMusic.positioning) {
/*  35 */           Music oldMusic = currentMusic;
/*  36 */           currentMusic = null;
/*  37 */           oldMusic.fireMusicEnded();
/*     */         } 
/*     */       } else {
/*  40 */         currentMusic.update(delta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private ArrayList listeners = new ArrayList();
/*     */   
/*  52 */   private float volume = 1.0F;
/*     */   
/*     */   private float fadeStartGain;
/*     */   
/*     */   private float fadeEndGain;
/*     */   
/*     */   private int fadeTime;
/*     */   
/*     */   private int fadeDuration;
/*     */   
/*     */   private boolean stopAfterFade;
/*     */   
/*     */   private boolean positioning;
/*     */   
/*  66 */   private float requiredPosition = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Music(String ref) throws SlickException {
/*  75 */     this(ref, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Music(URL ref) throws SlickException {
/*  85 */     this(ref, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Music(InputStream in, String ref) throws SlickException {
/*  95 */     SoundStore.get().init();
/*     */     
/*     */     try {
/*  98 */       if (ref.toLowerCase().endsWith(".ogg")) {
/*  99 */         this.sound = SoundStore.get().getOgg(in);
/* 100 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/* 101 */         this.sound = SoundStore.get().getWAV(in);
/* 102 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/* 103 */         this.sound = SoundStore.get().getMOD(in);
/* 104 */       } else if (ref.toLowerCase().endsWith(".aif") || ref.toLowerCase().endsWith(".aiff")) {
/* 105 */         this.sound = SoundStore.get().getAIF(in);
/*     */       } else {
/* 107 */         throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
/*     */       } 
/* 109 */     } catch (Exception e) {
/* 110 */       Log.error(e);
/* 111 */       throw new SlickException("Failed to load music: " + ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Music(URL url, boolean streamingHint) throws SlickException {
/* 123 */     SoundStore.get().init();
/* 124 */     String ref = url.getFile();
/*     */     
/*     */     try {
/* 127 */       if (ref.toLowerCase().endsWith(".ogg")) {
/* 128 */         if (streamingHint) {
/* 129 */           this.sound = SoundStore.get().getOggStream(url);
/*     */         } else {
/* 131 */           this.sound = SoundStore.get().getOgg(url.openStream());
/*     */         } 
/* 133 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/* 134 */         this.sound = SoundStore.get().getWAV(url.openStream());
/* 135 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/* 136 */         this.sound = SoundStore.get().getMOD(url.openStream());
/* 137 */       } else if (ref.toLowerCase().endsWith(".aif") || ref.toLowerCase().endsWith(".aiff")) {
/* 138 */         this.sound = SoundStore.get().getAIF(url.openStream());
/*     */       } else {
/* 140 */         throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
/*     */       } 
/* 142 */     } catch (Exception e) {
/* 143 */       Log.error(e);
/* 144 */       throw new SlickException("Failed to load sound: " + url);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Music(String ref, boolean streamingHint) throws SlickException {
/* 156 */     SoundStore.get().init();
/*     */     
/*     */     try {
/* 159 */       if (ref.toLowerCase().endsWith(".ogg")) {
/* 160 */         if (streamingHint) {
/* 161 */           this.sound = SoundStore.get().getOggStream(ref);
/*     */         } else {
/* 163 */           this.sound = SoundStore.get().getOgg(ref);
/*     */         } 
/* 165 */       } else if (ref.toLowerCase().endsWith(".wav")) {
/* 166 */         this.sound = SoundStore.get().getWAV(ref);
/* 167 */       } else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
/* 168 */         this.sound = SoundStore.get().getMOD(ref);
/* 169 */       } else if (ref.toLowerCase().endsWith(".aif") || ref.toLowerCase().endsWith(".aiff")) {
/* 170 */         this.sound = SoundStore.get().getAIF(ref);
/*     */       } else {
/* 172 */         throw new SlickException("Only .xm, .mod, .ogg, and .aif/f are currently supported.");
/*     */       } 
/* 174 */     } catch (Exception e) {
/* 175 */       Log.error(e);
/* 176 */       throw new SlickException("Failed to load sound: " + ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(MusicListener listener) {
/* 186 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(MusicListener listener) {
/* 195 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireMusicEnded() {
/* 202 */     this.playing = false;
/* 203 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 204 */       ((MusicListener)this.listeners.get(i)).musicEnded(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireMusicSwapped(Music newMusic) {
/* 214 */     this.playing = false;
/* 215 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 216 */       ((MusicListener)this.listeners.get(i)).musicSwapped(this, newMusic);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loop() {
/* 223 */     loop(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/* 230 */     play(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play(float pitch, float volume) {
/* 240 */     startMusic(pitch, volume, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loop(float pitch, float volume) {
/* 250 */     startMusic(pitch, volume, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startMusic(float pitch, float volume, boolean loop) {
/* 260 */     if (currentMusic != null) {
/* 261 */       currentMusic.stop();
/* 262 */       currentMusic.fireMusicSwapped(this);
/*     */     } 
/*     */     
/* 265 */     currentMusic = this;
/* 266 */     if (volume < 0.0F)
/* 267 */       volume = 0.0F; 
/* 268 */     if (volume > 1.0F) {
/* 269 */       volume = 1.0F;
/*     */     }
/* 271 */     this.sound.playAsMusic(pitch, volume, loop);
/* 272 */     this.playing = true;
/* 273 */     setVolume(volume);
/* 274 */     if (this.requiredPosition != -1.0F) {
/* 275 */       setPosition(this.requiredPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 283 */     this.playing = false;
/* 284 */     AudioImpl.pauseMusic();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 291 */     this.sound.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resume() {
/* 298 */     this.playing = true;
/* 299 */     AudioImpl.restartMusic();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean playing() {
/* 308 */     return (currentMusic == this && this.playing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVolume(float volume) {
/* 318 */     if (volume > 1.0F) {
/* 319 */       volume = 1.0F;
/* 320 */     } else if (volume < 0.0F) {
/* 321 */       volume = 0.0F;
/*     */     } 
/*     */     
/* 324 */     this.volume = volume;
/*     */     
/* 326 */     if (currentMusic == this) {
/* 327 */       SoundStore.get().setCurrentMusicVolume(volume);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVolume() {
/* 336 */     return this.volume;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fade(int duration, float endVolume, boolean stopAfterFade) {
/* 347 */     this.stopAfterFade = stopAfterFade;
/* 348 */     this.fadeStartGain = this.volume;
/* 349 */     this.fadeEndGain = endVolume;
/* 350 */     this.fadeDuration = duration;
/* 351 */     this.fadeTime = duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void update(int delta) {
/* 361 */     if (!this.playing) {
/*     */       return;
/*     */     }
/*     */     
/* 365 */     if (this.fadeTime > 0) {
/* 366 */       this.fadeTime -= delta;
/* 367 */       if (this.fadeTime < 0) {
/* 368 */         this.fadeTime = 0;
/* 369 */         if (this.stopAfterFade) {
/* 370 */           stop();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 375 */       float offset = (this.fadeEndGain - this.fadeStartGain) * (1.0F - this.fadeTime / this.fadeDuration);
/* 376 */       setVolume(this.fadeStartGain + offset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(float position) {
/* 388 */     if (this.playing) {
/* 389 */       this.requiredPosition = -1.0F;
/*     */       
/* 391 */       this.positioning = true;
/* 392 */       this.playing = false;
/* 393 */       boolean result = this.sound.setPosition(position);
/* 394 */       this.playing = true;
/* 395 */       this.positioning = false;
/*     */       
/* 397 */       return result;
/*     */     } 
/* 399 */     this.requiredPosition = position;
/* 400 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/* 410 */     return this.sound.getPosition();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Music.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */