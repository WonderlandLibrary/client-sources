/*  1:   */ package org.newdawn.slick.opengl;
/*  2:   */ 
/*  3:   */ import java.security.AccessController;
/*  4:   */ import java.security.PrivilegedAction;
/*  5:   */ import org.newdawn.slick.util.Log;
/*  6:   */ 
/*  7:   */ public class ImageDataFactory
/*  8:   */ {
/*  9:17 */   private static boolean usePngLoader = true;
/* 10:19 */   private static boolean pngLoaderPropertyChecked = false;
/* 11:   */   private static final String PNG_LOADER = "org.newdawn.slick.pngloader";
/* 12:   */   
/* 13:   */   private static void checkProperty()
/* 14:   */   {
/* 15:29 */     if (!pngLoaderPropertyChecked)
/* 16:   */     {
/* 17:30 */       pngLoaderPropertyChecked = true;
/* 18:   */       try
/* 19:   */       {
/* 20:33 */         AccessController.doPrivileged(new PrivilegedAction()
/* 21:   */         {
/* 22:   */           public Object run()
/* 23:   */           {
/* 24:35 */             String val = System.getProperty("org.newdawn.slick.pngloader");
/* 25:36 */             if ("false".equalsIgnoreCase(val)) {
/* 26:37 */               ImageDataFactory.usePngLoader = false;
/* 27:   */             }
/* 28:40 */             Log.info("Use Java PNG Loader = " + ImageDataFactory.usePngLoader);
/* 29:41 */             return null;
/* 30:   */           }
/* 31:   */         });
/* 32:   */       }
/* 33:   */       catch (Throwable localThrowable) {}
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static LoadableImageData getImageDataFor(String ref)
/* 38:   */   {
/* 39:58 */     checkProperty();
/* 40:   */     
/* 41:60 */     ref = ref.toLowerCase();
/* 42:62 */     if (ref.endsWith(".tga")) {
/* 43:63 */       return new TGAImageData();
/* 44:   */     }
/* 45:65 */     if (ref.endsWith(".png"))
/* 46:   */     {
/* 47:66 */       CompositeImageData data = new CompositeImageData();
/* 48:67 */       if (usePngLoader) {
/* 49:68 */         data.add(new PNGImageData());
/* 50:   */       }
/* 51:70 */       data.add(new ImageIOImageData());
/* 52:   */       
/* 53:72 */       return data;
/* 54:   */     }
/* 55:75 */     return new ImageIOImageData();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.ImageDataFactory
 * JD-Core Version:    0.7.0.1
 */