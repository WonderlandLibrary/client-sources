/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.openal.AL10;
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
/*     */ public class StreamSound
/*     */   extends AudioImpl
/*     */ {
/*     */   private OpenALStreamPlayer player;
/*     */   
/*     */   public StreamSound(OpenALStreamPlayer player) {
/*  28 */     this.player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlaying() {
/*  35 */     return SoundStore.get().isPlaying(this.player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsMusic(float pitch, float gain, boolean loop) {
/*     */     try {
/*  43 */       cleanUpSource();
/*     */       
/*  45 */       this.player.setup(pitch);
/*  46 */       this.player.play(loop);
/*  47 */       SoundStore.get().setStream(this.player);
/*  48 */     } catch (IOException e) {
/*  49 */       Log.error("Failed to read OGG source: " + this.player.getSource());
/*     */     } 
/*     */     
/*  52 */     return SoundStore.get().getSource(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanUpSource() {
/*  59 */     SoundStore store = SoundStore.get();
/*     */     
/*  61 */     AL10.alSourceStop(store.getSource(0));
/*  62 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/*  63 */     int queued = AL10.alGetSourcei(store.getSource(0), 4117);
/*     */     
/*  65 */     while (queued > 0) {
/*     */       
/*  67 */       AL10.alSourceUnqueueBuffers(store.getSource(0), buffer);
/*  68 */       queued--;
/*     */     } 
/*     */     
/*  71 */     AL10.alSourcei(store.getSource(0), 4105, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
/*  78 */     return playAsMusic(pitch, gain, loop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop) {
/*  85 */     return playAsMusic(pitch, gain, loop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  92 */     SoundStore.get().setStream(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(float position) {
/*  99 */     return this.player.setPosition(position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/* 106 */     return this.player.getPosition();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\StreamSound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */