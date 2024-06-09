/*    */ package org.newdawn.slick.opengl;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyImageData
/*    */   implements ImageData
/*    */ {
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   public EmptyImageData(int width, int height) {
/* 25 */     this.width = width;
/* 26 */     this.height = height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDepth() {
/* 33 */     return 32;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 40 */     return this.height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer getImageBufferData() {
/* 47 */     return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTexHeight() {
/* 54 */     return InternalTextureLoader.get2Fold(this.height);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTexWidth() {
/* 61 */     return InternalTextureLoader.get2Fold(this.width);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 68 */     return this.width;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\EmptyImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */