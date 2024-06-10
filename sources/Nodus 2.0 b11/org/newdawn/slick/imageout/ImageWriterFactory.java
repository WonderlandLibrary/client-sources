/*  1:   */ package org.newdawn.slick.imageout;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Set;
/*  5:   */ import javax.imageio.ImageIO;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ 
/*  8:   */ public class ImageWriterFactory
/*  9:   */ {
/* 10:16 */   private static HashMap writers = new HashMap();
/* 11:   */   
/* 12:   */   static
/* 13:   */   {
/* 14:20 */     String[] formats = ImageIO.getWriterFormatNames();
/* 15:21 */     ImageIOWriter writer = new ImageIOWriter();
/* 16:22 */     for (int i = 0; i < formats.length; i++) {
/* 17:23 */       registerWriter(formats[i], writer);
/* 18:   */     }
/* 19:26 */     TGAWriter tga = new TGAWriter();
/* 20:27 */     registerWriter("tga", tga);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static void registerWriter(String format, ImageWriter writer)
/* 24:   */   {
/* 25:38 */     writers.put(format, writer);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static String[] getSupportedFormats()
/* 29:   */   {
/* 30:47 */     return (String[])writers.keySet().toArray(new String[0]);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static ImageWriter getWriterForFormat(String format)
/* 34:   */     throws SlickException
/* 35:   */   {
/* 36:59 */     ImageWriter writer = (ImageWriter)writers.get(format);
/* 37:60 */     if (writer != null) {
/* 38:61 */       return writer;
/* 39:   */     }
/* 40:64 */     writer = (ImageWriter)writers.get(format.toLowerCase());
/* 41:65 */     if (writer != null) {
/* 42:66 */       return writer;
/* 43:   */     }
/* 44:69 */     writer = (ImageWriter)writers.get(format.toUpperCase());
/* 45:70 */     if (writer != null) {
/* 46:71 */       return writer;
/* 47:   */     }
/* 48:74 */     throw new SlickException("No image writer available for: " + format);
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.imageout.ImageWriterFactory
 * JD-Core Version:    0.7.0.1
 */