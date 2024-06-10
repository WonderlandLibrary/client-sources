/*  1:   */ package org.newdawn.slick.opengl;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ public class TextureLoader
/*  7:   */ {
/*  8:   */   public static Texture getTexture(String format, InputStream in)
/*  9:   */     throws IOException
/* 10:   */   {
/* 11:24 */     return getTexture(format, in, false, 9729);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static Texture getTexture(String format, InputStream in, boolean flipped)
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:37 */     return getTexture(format, in, flipped, 9729);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static Texture getTexture(String format, InputStream in, int filter)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:50 */     return getTexture(format, in, false, filter);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static Texture getTexture(String format, InputStream in, boolean flipped, int filter)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:64 */     return InternalTextureLoader.get().getTexture(in, in.toString() + "." + format, flipped, filter);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.TextureLoader
 * JD-Core Version:    0.7.0.1
 */