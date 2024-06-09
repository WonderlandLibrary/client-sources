/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import org.lwjgl.openal.AL10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AudioImpl
/*     */   implements Audio
/*     */ {
/*     */   private SoundStore store;
/*     */   private int buffer;
/*  18 */   private int index = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AudioImpl(SoundStore store, int buffer) {
/*  30 */     this.store = store;
/*  31 */     this.buffer = buffer;
/*     */     
/*  33 */     int bytes = AL10.alGetBufferi(buffer, 8196);
/*  34 */     int bits = AL10.alGetBufferi(buffer, 8194);
/*  35 */     int channels = AL10.alGetBufferi(buffer, 8195);
/*  36 */     int freq = AL10.alGetBufferi(buffer, 8193);
/*     */     
/*  38 */     int samples = bytes / bits / 8;
/*  39 */     this.length = samples / freq / channels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBufferID() {
/*  49 */     return this.buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AudioImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  63 */     if (this.index != -1) {
/*  64 */       this.store.stopSource(this.index);
/*  65 */       this.index = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlaying() {
/*  73 */     if (this.index != -1) {
/*  74 */       return this.store.isPlaying(this.index);
/*     */     }
/*     */     
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop) {
/*  84 */     this.index = this.store.playAsSound(this.buffer, pitch, gain, loop);
/*  85 */     return this.store.getSource(this.index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
/*  93 */     this.index = this.store.playAsSoundAt(this.buffer, pitch, gain, loop, x, y, z);
/*  94 */     return this.store.getSource(this.index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsMusic(float pitch, float gain, boolean loop) {
/* 101 */     this.store.playAsMusic(this.buffer, pitch, gain, loop);
/* 102 */     this.index = 0;
/* 103 */     return this.store.getSource(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void pauseMusic() {
/* 110 */     SoundStore.get().pauseLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void restartMusic() {
/* 117 */     SoundStore.get().restartLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(float position) {
/* 124 */     position %= this.length;
/*     */     
/* 126 */     AL10.alSourcef(this.store.getSource(this.index), 4132, position);
/* 127 */     if (AL10.alGetError() != 0) {
/* 128 */       return false;
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/* 137 */     return AL10.alGetSourcef(this.store.getSource(this.index), 4132);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\AudioImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */