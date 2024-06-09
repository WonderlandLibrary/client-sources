/*    */ package org.newdawn.slick.imageout;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.imageio.ImageIO;
/*    */ import org.newdawn.slick.SlickException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageWriterFactory
/*    */ {
/* 16 */   private static HashMap writers = new HashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   static {
/* 20 */     String[] formats = ImageIO.getWriterFormatNames();
/* 21 */     ImageIOWriter writer = new ImageIOWriter();
/* 22 */     for (int i = 0; i < formats.length; i++) {
/* 23 */       registerWriter(formats[i], writer);
/*    */     }
/*    */     
/* 26 */     TGAWriter tga = new TGAWriter();
/* 27 */     registerWriter("tga", tga);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerWriter(String format, ImageWriter writer) {
/* 38 */     writers.put(format, writer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] getSupportedFormats() {
/* 47 */     return (String[])writers.keySet().toArray((Object[])new String[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ImageWriter getWriterForFormat(String format) throws SlickException {
/* 59 */     ImageWriter writer = (ImageWriter)writers.get(format);
/* 60 */     if (writer != null) {
/* 61 */       return writer;
/*    */     }
/*    */     
/* 64 */     writer = (ImageWriter)writers.get(format.toLowerCase());
/* 65 */     if (writer != null) {
/* 66 */       return writer;
/*    */     }
/*    */     
/* 69 */     writer = (ImageWriter)writers.get(format.toUpperCase());
/* 70 */     if (writer != null) {
/* 71 */       return writer;
/*    */     }
/*    */     
/* 74 */     throw new SlickException("No image writer available for: " + format);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\imageout\ImageWriterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */