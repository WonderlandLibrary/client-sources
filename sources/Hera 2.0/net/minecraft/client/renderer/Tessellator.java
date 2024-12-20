/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ public class Tessellator
/*    */ {
/*    */   private WorldRenderer worldRenderer;
/*  6 */   private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
/*    */ 
/*    */   
/*  9 */   private static final Tessellator instance = new Tessellator(2097152);
/*    */ 
/*    */   
/*    */   public static Tessellator getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public Tessellator(int bufferSize) {
/* 18 */     this.worldRenderer = new WorldRenderer(bufferSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     this.worldRenderer.finishDrawing();
/* 27 */     this.vboUploader.func_181679_a(this.worldRenderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldRenderer getWorldRenderer() {
/* 32 */     return this.worldRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\Tessellator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */