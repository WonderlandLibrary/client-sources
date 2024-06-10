/*  1:   */ package org.newdawn.slick.util;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.InputStream;
/*  7:   */ import java.net.URI;
/*  8:   */ import java.net.URL;
/*  9:   */ 
/* 10:   */ public class FileSystemLocation
/* 11:   */   implements ResourceLocation
/* 12:   */ {
/* 13:   */   private File root;
/* 14:   */   
/* 15:   */   public FileSystemLocation(File root)
/* 16:   */   {
/* 17:24 */     this.root = root;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public URL getResource(String ref)
/* 21:   */   {
/* 22:   */     try
/* 23:   */     {
/* 24:32 */       File file = new File(this.root, ref);
/* 25:33 */       if (!file.exists()) {
/* 26:34 */         file = new File(ref);
/* 27:   */       }
/* 28:36 */       if (!file.exists()) {
/* 29:37 */         return null;
/* 30:   */       }
/* 31:40 */       return file.toURI().toURL();
/* 32:   */     }
/* 33:   */     catch (IOException e) {}
/* 34:42 */     return null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public InputStream getResourceAsStream(String ref)
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:51 */       File file = new File(this.root, ref);
/* 42:52 */       if (!file.exists()) {
/* 43:53 */         file = new File(ref);
/* 44:   */       }
/* 45:55 */       return new FileInputStream(file);
/* 46:   */     }
/* 47:   */     catch (IOException e) {}
/* 48:57 */     return null;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.FileSystemLocation
 * JD-Core Version:    0.7.0.1
 */