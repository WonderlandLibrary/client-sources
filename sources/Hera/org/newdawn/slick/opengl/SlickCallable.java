/*     */ package org.newdawn.slick.opengl;
/*     */ 
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
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
/*     */ 
/*     */ 
/*     */ public abstract class SlickCallable
/*     */ {
/*     */   private static Texture lastUsed;
/*     */   private static boolean inSafe = false;
/*     */   
/*     */   public static void enterSafeBlock() {
/*  56 */     if (inSafe) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     Renderer.get().flush();
/*  61 */     lastUsed = TextureImpl.getLastBind();
/*  62 */     TextureImpl.bindNone();
/*  63 */     GL11.glPushAttrib(1048575);
/*  64 */     GL11.glPushClientAttrib(-1);
/*  65 */     GL11.glMatrixMode(5888);
/*  66 */     GL11.glPushMatrix();
/*  67 */     GL11.glMatrixMode(5889);
/*  68 */     GL11.glPushMatrix();
/*  69 */     GL11.glMatrixMode(5888);
/*     */     
/*  71 */     inSafe = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void leaveSafeBlock() {
/*  80 */     if (!inSafe) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     GL11.glMatrixMode(5889);
/*  85 */     GL11.glPopMatrix();
/*  86 */     GL11.glMatrixMode(5888);
/*  87 */     GL11.glPopMatrix();
/*  88 */     GL11.glPopClientAttrib();
/*  89 */     GL11.glPopAttrib();
/*     */     
/*  91 */     if (lastUsed != null) {
/*  92 */       lastUsed.bind();
/*     */     } else {
/*  94 */       TextureImpl.bindNone();
/*     */     } 
/*     */     
/*  97 */     inSafe = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void call() throws SlickException {
/* 108 */     enterSafeBlock();
/*     */     
/* 110 */     performGLOperations();
/*     */     
/* 112 */     leaveSafeBlock();
/*     */   }
/*     */   
/*     */   protected abstract void performGLOperations() throws SlickException;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\SlickCallable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */