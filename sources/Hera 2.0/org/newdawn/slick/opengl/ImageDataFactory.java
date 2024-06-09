/*    */ package org.newdawn.slick.opengl;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import org.newdawn.slick.util.Log;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageDataFactory
/*    */ {
/*    */   private static boolean usePngLoader = true;
/*    */   private static boolean pngLoaderPropertyChecked = false;
/*    */   private static final String PNG_LOADER = "org.newdawn.slick.pngloader";
/*    */   
/*    */   private static void checkProperty() {
/* 29 */     if (!pngLoaderPropertyChecked) {
/* 30 */       pngLoaderPropertyChecked = true;
/*    */       
/*    */       try {
/* 33 */         AccessController.doPrivileged(new PrivilegedAction() {
/*    */               public Object run() {
/* 35 */                 String val = System.getProperty("org.newdawn.slick.pngloader");
/* 36 */                 if ("false".equalsIgnoreCase(val)) {
/* 37 */                   ImageDataFactory.usePngLoader = false;
/*    */                 }
/*    */                 
/* 40 */                 Log.info("Use Java PNG Loader = " + ImageDataFactory.usePngLoader);
/* 41 */                 return null;
/*    */               }
/*    */             });
/* 44 */       } catch (Throwable e) {}
/*    */     } 
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
/*    */   
/*    */   public static LoadableImageData getImageDataFor(String ref) {
/* 58 */     checkProperty();
/*    */     
/* 60 */     ref = ref.toLowerCase();
/*    */     
/* 62 */     if (ref.endsWith(".tga")) {
/* 63 */       return new TGAImageData();
/*    */     }
/* 65 */     if (ref.endsWith(".png")) {
/* 66 */       CompositeImageData data = new CompositeImageData();
/* 67 */       if (usePngLoader) {
/* 68 */         data.add(new PNGImageData());
/*    */       }
/* 70 */       data.add(new ImageIOImageData());
/*    */       
/* 72 */       return data;
/*    */     } 
/*    */     
/* 75 */     return new ImageIOImageData();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\ImageDataFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */