/*  1:   */ package org.newdawn.slick.opengl;
/*  2:   */ 
/*  3:   */ import java.nio.ByteBuffer;
/*  4:   */ import org.lwjgl.BufferUtils;
/*  5:   */ 
/*  6:   */ public class EmptyImageData
/*  7:   */   implements ImageData
/*  8:   */ {
/*  9:   */   private int width;
/* 10:   */   private int height;
/* 11:   */   
/* 12:   */   public EmptyImageData(int width, int height)
/* 13:   */   {
/* 14:25 */     this.width = width;
/* 15:26 */     this.height = height;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getDepth()
/* 19:   */   {
/* 20:33 */     return 32;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getHeight()
/* 24:   */   {
/* 25:40 */     return this.height;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public ByteBuffer getImageBufferData()
/* 29:   */   {
/* 30:47 */     return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getTexHeight()
/* 34:   */   {
/* 35:54 */     return InternalTextureLoader.get2Fold(this.height);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getTexWidth()
/* 39:   */   {
/* 40:61 */     return InternalTextureLoader.get2Fold(this.width);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int getWidth()
/* 44:   */   {
/* 45:68 */     return this.width;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.EmptyImageData
 * JD-Core Version:    0.7.0.1
 */