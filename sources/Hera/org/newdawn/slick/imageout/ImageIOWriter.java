/*    */ package org.newdawn.slick.imageout;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.color.ColorSpace;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.awt.image.ColorModel;
/*    */ import java.awt.image.ComponentColorModel;
/*    */ import java.awt.image.DataBufferByte;
/*    */ import java.awt.image.PixelInterleavedSampleModel;
/*    */ import java.awt.image.Raster;
/*    */ import java.awt.image.WritableRaster;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import javax.imageio.ImageIO;
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
/*    */ public class ImageIOWriter
/*    */   implements ImageWriter
/*    */ {
/*    */   public void saveImage(Image image, String format, OutputStream output, boolean hasAlpha) throws IOException {
/*    */     PixelInterleavedSampleModel sampleModel;
/*    */     ColorModel cm;
/* 35 */     int len = 4 * image.getWidth() * image.getHeight();
/* 36 */     if (!hasAlpha) {
/* 37 */       len = 3 * image.getWidth() * image.getHeight();
/*    */     }
/*    */     
/* 40 */     ByteBuffer out = ByteBuffer.allocate(len);
/*    */ 
/*    */     
/* 43 */     for (int y = 0; y < image.getHeight(); y++) {
/* 44 */       for (int x = 0; x < image.getWidth(); x++) {
/* 45 */         Color c = image.getColor(x, y);
/*    */         
/* 47 */         out.put((byte)(int)(c.r * 255.0F));
/* 48 */         out.put((byte)(int)(c.g * 255.0F));
/* 49 */         out.put((byte)(int)(c.b * 255.0F));
/* 50 */         if (hasAlpha) {
/* 51 */           out.put((byte)(int)(c.a * 255.0F));
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 57 */     DataBufferByte dataBuffer = new DataBufferByte(out.array(), len);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     if (hasAlpha) {
/* 64 */       int[] offsets = { 0, 1, 2, 3 };
/* 65 */       sampleModel = new PixelInterleavedSampleModel(0, image.getWidth(), image.getHeight(), 4, 4 * image.getWidth(), offsets);
/*    */ 
/*    */ 
/*    */       
/* 69 */       cm = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 74 */       int[] offsets = { 0, 1, 2 };
/* 75 */       sampleModel = new PixelInterleavedSampleModel(0, image.getWidth(), image.getHeight(), 3, 3 * image.getWidth(), offsets);
/*    */ 
/*    */ 
/*    */       
/* 79 */       cm = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 0 }, false, false, 1, 0);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));
/*    */ 
/*    */ 
/*    */     
/* 90 */     BufferedImage img = new BufferedImage(cm, raster, false, null);
/*    */     
/* 92 */     ImageIO.write(img, format, output);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\imageout\ImageIOWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */