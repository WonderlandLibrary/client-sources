/*  1:   */ package org.newdawn.slick.imageout;
/*  2:   */ 
/*  3:   */ import java.awt.Point;
/*  4:   */ import java.awt.color.ColorSpace;
/*  5:   */ import java.awt.image.BufferedImage;
/*  6:   */ import java.awt.image.ColorModel;
/*  7:   */ import java.awt.image.ComponentColorModel;
/*  8:   */ import java.awt.image.DataBufferByte;
/*  9:   */ import java.awt.image.PixelInterleavedSampleModel;
/* 10:   */ import java.awt.image.Raster;
/* 11:   */ import java.awt.image.WritableRaster;
/* 12:   */ import java.io.IOException;
/* 13:   */ import java.io.OutputStream;
/* 14:   */ import java.nio.ByteBuffer;
/* 15:   */ import javax.imageio.ImageIO;
/* 16:   */ import org.newdawn.slick.Color;
/* 17:   */ import org.newdawn.slick.Image;
/* 18:   */ 
/* 19:   */ public class ImageIOWriter
/* 20:   */   implements ImageWriter
/* 21:   */ {
/* 22:   */   public void saveImage(Image image, String format, OutputStream output, boolean hasAlpha)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:35 */     int len = 4 * image.getWidth() * image.getHeight();
/* 26:36 */     if (!hasAlpha) {
/* 27:37 */       len = 3 * image.getWidth() * image.getHeight();
/* 28:   */     }
/* 29:40 */     ByteBuffer out = ByteBuffer.allocate(len);
/* 30:43 */     for (int y = 0; y < image.getHeight(); y++) {
/* 31:44 */       for (int x = 0; x < image.getWidth(); x++)
/* 32:   */       {
/* 33:45 */         Color c = image.getColor(x, y);
/* 34:   */         
/* 35:47 */         out.put((byte)(int)(c.r * 255.0F));
/* 36:48 */         out.put((byte)(int)(c.g * 255.0F));
/* 37:49 */         out.put((byte)(int)(c.b * 255.0F));
/* 38:50 */         if (hasAlpha) {
/* 39:51 */           out.put((byte)(int)(c.a * 255.0F));
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:57 */     DataBufferByte dataBuffer = new DataBufferByte(out.array(), len);
/* 44:   */     ColorModel cm;
/* 45:   */     PixelInterleavedSampleModel sampleModel;
/* 46:   */     ColorModel cm;
/* 47:63 */     if (hasAlpha)
/* 48:   */     {
/* 49:64 */       int[] offsets = { 0, 1, 2, 3 };
/* 50:65 */       PixelInterleavedSampleModel sampleModel = new PixelInterleavedSampleModel(
/* 51:66 */         0, image.getWidth(), image.getHeight(), 4, 
/* 52:67 */         4 * image.getWidth(), offsets);
/* 53:   */       
/* 54:69 */       cm = new ComponentColorModel(
/* 55:70 */         ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, 
/* 56:71 */         true, false, 3, 
/* 57:72 */         0);
/* 58:   */     }
/* 59:   */     else
/* 60:   */     {
/* 61:74 */       int[] offsets = { 0, 1, 2 };
/* 62:75 */       sampleModel = new PixelInterleavedSampleModel(
/* 63:76 */         0, image.getWidth(), image.getHeight(), 3, 
/* 64:77 */         3 * image.getWidth(), offsets);
/* 65:   */       
/* 66:79 */       cm = new ComponentColorModel(ColorSpace.getInstance(1000), 
/* 67:80 */         new int[] { 8, 8, 8 }, 
/* 68:81 */         false, 
/* 69:82 */         false, 
/* 70:83 */         1, 
/* 71:84 */         0);
/* 72:   */     }
/* 73:86 */     WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));
/* 74:   */     
/* 75:   */ 
/* 76:   */ 
/* 77:90 */     BufferedImage img = new BufferedImage(cm, raster, false, null);
/* 78:   */     
/* 79:92 */     ImageIO.write(img, format, output);
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.imageout.ImageIOWriter
 * JD-Core Version:    0.7.0.1
 */