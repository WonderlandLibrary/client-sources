/*   1:    */ package org.newdawn.slick.imageout;
/*   2:    */ 
/*   3:    */ import java.io.FileOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import org.newdawn.slick.Image;
/*   7:    */ import org.newdawn.slick.SlickException;
/*   8:    */ 
/*   9:    */ public class ImageOut
/*  10:    */ {
/*  11:    */   private static final boolean DEFAULT_ALPHA_WRITE = false;
/*  12: 22 */   public static String TGA = "tga";
/*  13: 24 */   public static String PNG = "png";
/*  14: 26 */   public static String JPG = "jpg";
/*  15:    */   
/*  16:    */   public static String[] getSupportedFormats()
/*  17:    */   {
/*  18: 35 */     return ImageWriterFactory.getSupportedFormats();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void write(Image image, String format, OutputStream out)
/*  22:    */     throws SlickException
/*  23:    */   {
/*  24: 47 */     write(image, format, out, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void write(Image image, String format, OutputStream out, boolean writeAlpha)
/*  28:    */     throws SlickException
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 61 */       ImageWriter writer = ImageWriterFactory.getWriterForFormat(format);
/*  33: 62 */       writer.saveImage(image, format, out, writeAlpha);
/*  34:    */     }
/*  35:    */     catch (IOException e)
/*  36:    */     {
/*  37: 64 */       throw new SlickException("Unable to write out the image in format: " + format, e);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(Image image, String dest)
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44: 77 */     write(image, dest, false);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void write(Image image, String dest, boolean writeAlpha)
/*  48:    */     throws SlickException
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52: 91 */       int ext = dest.lastIndexOf('.');
/*  53: 92 */       if (ext < 0) {
/*  54: 93 */         throw new SlickException("Unable to determine format from: " + dest);
/*  55:    */       }
/*  56: 96 */       String format = dest.substring(ext + 1);
/*  57: 97 */       write(image, format, new FileOutputStream(dest), writeAlpha);
/*  58:    */     }
/*  59:    */     catch (IOException e)
/*  60:    */     {
/*  61: 99 */       throw new SlickException("Unable to write to the destination: " + dest, e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void write(Image image, String format, String dest)
/*  66:    */     throws SlickException
/*  67:    */   {
/*  68:112 */     write(image, format, dest, false);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void write(Image image, String format, String dest, boolean writeAlpha)
/*  72:    */     throws SlickException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:126 */       write(image, format, new FileOutputStream(dest), writeAlpha);
/*  77:    */     }
/*  78:    */     catch (IOException e)
/*  79:    */     {
/*  80:128 */       throw new SlickException("Unable to write to the destination: " + dest, e);
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.imageout.ImageOut
 * JD-Core Version:    0.7.0.1
 */