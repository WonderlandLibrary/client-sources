/*     */ package org.newdawn.slick.openal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.newdawn.slick.loading.DeferredResource;
/*     */ import org.newdawn.slick.loading.LoadingList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeferredSound
/*     */   extends AudioImpl
/*     */   implements DeferredResource
/*     */ {
/*     */   public static final int OGG = 1;
/*     */   public static final int WAV = 2;
/*     */   public static final int MOD = 3;
/*     */   public static final int AIF = 4;
/*     */   private int type;
/*     */   private String ref;
/*     */   private Audio target;
/*     */   private InputStream in;
/*     */   
/*     */   public DeferredSound(String ref, InputStream in, int type) {
/*  43 */     this.ref = ref;
/*  44 */     this.type = type;
/*     */ 
/*     */     
/*  47 */     if (ref.equals(in.toString())) {
/*  48 */       this.in = in;
/*     */     }
/*     */     
/*  51 */     LoadingList.get().add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTarget() {
/*  58 */     if (this.target == null) {
/*  59 */       throw new RuntimeException("Attempt to use deferred sound before loading");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/*  67 */     boolean before = SoundStore.get().isDeferredLoading();
/*  68 */     SoundStore.get().setDeferredLoading(false);
/*  69 */     if (this.in != null) {
/*  70 */       switch (this.type) {
/*     */         case 1:
/*  72 */           this.target = SoundStore.get().getOgg(this.in);
/*     */           break;
/*     */         case 2:
/*  75 */           this.target = SoundStore.get().getWAV(this.in);
/*     */           break;
/*     */         case 3:
/*  78 */           this.target = SoundStore.get().getMOD(this.in);
/*     */           break;
/*     */         case 4:
/*  81 */           this.target = SoundStore.get().getAIF(this.in);
/*     */           break;
/*     */         default:
/*  84 */           Log.error("Unrecognised sound type: " + this.type);
/*     */           break;
/*     */       } 
/*     */     } else {
/*  88 */       switch (this.type) {
/*     */         case 1:
/*  90 */           this.target = SoundStore.get().getOgg(this.ref);
/*     */           break;
/*     */         case 2:
/*  93 */           this.target = SoundStore.get().getWAV(this.ref);
/*     */           break;
/*     */         case 3:
/*  96 */           this.target = SoundStore.get().getMOD(this.ref);
/*     */           break;
/*     */         case 4:
/*  99 */           this.target = SoundStore.get().getAIF(this.ref);
/*     */           break;
/*     */         default:
/* 102 */           Log.error("Unrecognised sound type: " + this.type);
/*     */           break;
/*     */       } 
/*     */     } 
/* 106 */     SoundStore.get().setDeferredLoading(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlaying() {
/* 113 */     checkTarget();
/*     */     
/* 115 */     return this.target.isPlaying();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsMusic(float pitch, float gain, boolean loop) {
/* 122 */     checkTarget();
/* 123 */     return this.target.playAsMusic(pitch, gain, loop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop) {
/* 130 */     checkTarget();
/* 131 */     return this.target.playAsSoundEffect(pitch, gain, loop);
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
/*     */   
/*     */   public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
/* 145 */     checkTarget();
/* 146 */     return this.target.playAsSoundEffect(pitch, gain, loop, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 153 */     checkTarget();
/* 154 */     this.target.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 161 */     return this.ref;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\openal\DeferredSound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */