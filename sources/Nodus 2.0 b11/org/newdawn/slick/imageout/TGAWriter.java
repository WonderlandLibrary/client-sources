/*  1:   */ package org.newdawn.slick.imageout;
/*  2:   */ 
/*  3:   */ import java.io.BufferedOutputStream;
/*  4:   */ import java.io.DataOutputStream;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.OutputStream;
/*  7:   */ import org.newdawn.slick.Color;
/*  8:   */ import org.newdawn.slick.Image;
/*  9:   */ 
/* 10:   */ public class TGAWriter
/* 11:   */   implements ImageWriter
/* 12:   */ {
/* 13:   */   private static short flipEndian(short signedShort)
/* 14:   */   {
/* 15:24 */     int input = signedShort & 0xFFFF;
/* 16:25 */     return (short)(input << 8 | (input & 0xFF00) >>> 8);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void saveImage(Image image, String format, OutputStream output, boolean writeAlpha)
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:32 */     DataOutputStream out = new DataOutputStream(new BufferedOutputStream(output));
/* 23:   */     
/* 24:   */ 
/* 25:35 */     out.writeByte(0);
/* 26:   */     
/* 27:   */ 
/* 28:38 */     out.writeByte(0);
/* 29:   */     
/* 30:   */ 
/* 31:41 */     out.writeByte(2);
/* 32:   */     
/* 33:   */ 
/* 34:44 */     out.writeShort(flipEndian((short)0));
/* 35:45 */     out.writeShort(flipEndian((short)0));
/* 36:46 */     out.writeByte(0);
/* 37:   */     
/* 38:   */ 
/* 39:49 */     out.writeShort(flipEndian((short)0));
/* 40:50 */     out.writeShort(flipEndian((short)0));
/* 41:   */     
/* 42:   */ 
/* 43:53 */     out.writeShort(flipEndian((short)image.getWidth()));
/* 44:54 */     out.writeShort(flipEndian((short)image.getHeight()));
/* 45:55 */     if (writeAlpha)
/* 46:   */     {
/* 47:56 */       out.writeByte(32);
/* 48:   */       
/* 49:   */ 
/* 50:59 */       out.writeByte(1);
/* 51:   */     }
/* 52:   */     else
/* 53:   */     {
/* 54:61 */       out.writeByte(24);
/* 55:   */       
/* 56:   */ 
/* 57:64 */       out.writeByte(0);
/* 58:   */     }
/* 59:71 */     for (int y = image.getHeight() - 1; y <= 0; y--) {
/* 60:72 */       for (int x = 0; x < image.getWidth(); x++)
/* 61:   */       {
/* 62:73 */         Color c = image.getColor(x, y);
/* 63:   */         
/* 64:75 */         out.writeByte((byte)(int)(c.b * 255.0F));
/* 65:76 */         out.writeByte((byte)(int)(c.g * 255.0F));
/* 66:77 */         out.writeByte((byte)(int)(c.r * 255.0F));
/* 67:78 */         if (writeAlpha) {
/* 68:79 */           out.writeByte((byte)(int)(c.a * 255.0F));
/* 69:   */         }
/* 70:   */       }
/* 71:   */     }
/* 72:84 */     out.close();
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.imageout.TGAWriter
 * JD-Core Version:    0.7.0.1
 */