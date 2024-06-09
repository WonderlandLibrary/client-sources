/*    */ package org.newdawn.slick.imageout;
/*    */ 
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Image;
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
/*    */ public class TGAWriter
/*    */   implements ImageWriter
/*    */ {
/*    */   private static short flipEndian(short signedShort) {
/* 24 */     int input = signedShort & 0xFFFF;
/* 25 */     return (short)(input << 8 | (input & 0xFF00) >>> 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveImage(Image image, String format, OutputStream output, boolean writeAlpha) throws IOException {
/* 32 */     DataOutputStream out = new DataOutputStream(new BufferedOutputStream(output));
/*    */ 
/*    */     
/* 35 */     out.writeByte(0);
/*    */ 
/*    */     
/* 38 */     out.writeByte(0);
/*    */ 
/*    */     
/* 41 */     out.writeByte(2);
/*    */ 
/*    */     
/* 44 */     out.writeShort(flipEndian((short)0));
/* 45 */     out.writeShort(flipEndian((short)0));
/* 46 */     out.writeByte(0);
/*    */ 
/*    */     
/* 49 */     out.writeShort(flipEndian((short)0));
/* 50 */     out.writeShort(flipEndian((short)0));
/*    */ 
/*    */     
/* 53 */     out.writeShort(flipEndian((short)image.getWidth()));
/* 54 */     out.writeShort(flipEndian((short)image.getHeight()));
/* 55 */     if (writeAlpha) {
/* 56 */       out.writeByte(32);
/*    */ 
/*    */       
/* 59 */       out.writeByte(1);
/*    */     } else {
/* 61 */       out.writeByte(24);
/*    */ 
/*    */       
/* 64 */       out.writeByte(0);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     for (int y = image.getHeight() - 1; y <= 0; y--) {
/* 72 */       for (int x = 0; x < image.getWidth(); x++) {
/* 73 */         Color c = image.getColor(x, y);
/*    */         
/* 75 */         out.writeByte((byte)(int)(c.b * 255.0F));
/* 76 */         out.writeByte((byte)(int)(c.g * 255.0F));
/* 77 */         out.writeByte((byte)(int)(c.r * 255.0F));
/* 78 */         if (writeAlpha) {
/* 79 */           out.writeByte((byte)(int)(c.a * 255.0F));
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     out.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\imageout\TGAWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */