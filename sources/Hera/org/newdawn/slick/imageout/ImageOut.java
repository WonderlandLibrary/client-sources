/*     */ package org.newdawn.slick.imageout;
/*     */ 
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageOut
/*     */ {
/*     */   private static final boolean DEFAULT_ALPHA_WRITE = false;
/*  22 */   public static String TGA = "tga";
/*     */   
/*  24 */   public static String PNG = "png";
/*     */   
/*  26 */   public static String JPG = "jpg";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getSupportedFormats() {
/*  35 */     return ImageWriterFactory.getSupportedFormats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String format, OutputStream out) throws SlickException {
/*  47 */     write(image, format, out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String format, OutputStream out, boolean writeAlpha) throws SlickException {
/*     */     try {
/*  61 */       ImageWriter writer = ImageWriterFactory.getWriterForFormat(format);
/*  62 */       writer.saveImage(image, format, out, writeAlpha);
/*  63 */     } catch (IOException e) {
/*  64 */       throw new SlickException("Unable to write out the image in format: " + format, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String dest) throws SlickException {
/*  77 */     write(image, dest, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String dest, boolean writeAlpha) throws SlickException {
/*     */     try {
/*  91 */       int ext = dest.lastIndexOf('.');
/*  92 */       if (ext < 0) {
/*  93 */         throw new SlickException("Unable to determine format from: " + dest);
/*     */       }
/*     */       
/*  96 */       String format = dest.substring(ext + 1);
/*  97 */       write(image, format, new FileOutputStream(dest), writeAlpha);
/*  98 */     } catch (IOException e) {
/*  99 */       throw new SlickException("Unable to write to the destination: " + dest, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String format, String dest) throws SlickException {
/* 112 */     write(image, format, dest, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(Image image, String format, String dest, boolean writeAlpha) throws SlickException {
/*     */     try {
/* 126 */       write(image, format, new FileOutputStream(dest), writeAlpha);
/* 127 */     } catch (IOException e) {
/* 128 */       throw new SlickException("Unable to write to the destination: " + dest, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\imageout\ImageOut.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */