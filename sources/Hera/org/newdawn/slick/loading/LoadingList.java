/*     */ package org.newdawn.slick.loading;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.openal.SoundStore;
/*     */ import org.newdawn.slick.opengl.InternalTextureLoader;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoadingList
/*     */ {
/*  17 */   private static LoadingList single = new LoadingList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoadingList get() {
/*  25 */     return single;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDeferredLoading(boolean loading) {
/*  34 */     single = new LoadingList();
/*     */     
/*  36 */     InternalTextureLoader.get().setDeferredLoading(loading);
/*  37 */     SoundStore.get().setDeferredLoading(loading);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDeferredLoading() {
/*  46 */     return InternalTextureLoader.get().isDeferredLoading();
/*     */   }
/*     */ 
/*     */   
/*  50 */   private ArrayList deferred = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int total;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(DeferredResource resource) {
/*  66 */     this.total++;
/*  67 */     this.deferred.add(resource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(DeferredResource resource) {
/*  77 */     Log.info("Early loading of deferred resource due to req: " + resource.getDescription());
/*  78 */     this.total--;
/*  79 */     this.deferred.remove(resource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalResources() {
/*  88 */     return this.total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRemainingResources() {
/*  97 */     return this.deferred.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeferredResource getNext() {
/* 106 */     if (this.deferred.size() == 0) {
/* 107 */       return null;
/*     */     }
/*     */     
/* 110 */     return this.deferred.remove(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\loading\LoadingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */