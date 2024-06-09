/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import ibxm.Module;
/*     */ import ibxm.OpenALMODPlayer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.openal.AL10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MODSound
/*     */   extends AudioImpl
/*     */ {
/*  20 */   private static OpenALMODPlayer player = new OpenALMODPlayer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Module module;
/*     */ 
/*     */ 
/*     */   
/*     */   private SoundStore store;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MODSound(SoundStore store, InputStream in) throws IOException {
/*  35 */     this.store = store;
/*  36 */     this.module = OpenALMODPlayer.loadModule(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsMusic(float pitch, float gain, boolean loop) {
/*  43 */     cleanUpSource();
/*     */     
/*  45 */     player.play(this.module, this.store.getSource(0), loop, SoundStore.get().isMusicOn());
/*  46 */     player.setup(pitch, 1.0F);
/*  47 */     this.store.setCurrentMusicVolume(gain);
/*     */     
/*  49 */     this.store.setMOD(this);
/*     */     
/*  51 */     return this.store.getSource(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanUpSource() {
/*  58 */     AL10.alSourceStop(this.store.getSource(0));
/*  59 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/*  60 */     int queued = AL10.alGetSourcei(this.store.getSource(0), 4117);
/*     */     
/*  62 */     while (queued > 0) {
/*     */       
/*  64 */       AL10.alSourceUnqueueBuffers(this.store.getSource(0), buffer);
/*  65 */       queued--;
/*     */     } 
/*     */     
/*  68 */     AL10.alSourcei(this.store.getSource(0), 4105, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void poll() {
/*  75 */     player.update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop) {
/*  82 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  89 */     this.store.setMOD(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/*  96 */     throw new RuntimeException("Positioning on modules is not currently supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(float position) {
/* 103 */     throw new RuntimeException("Positioning on modules is not currently supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\MODSound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */