/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
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
/*    */ public class FileSystemLocation
/*    */   implements ResourceLocation
/*    */ {
/*    */   private File root;
/*    */   
/*    */   public FileSystemLocation(File root) {
/* 24 */     this.root = root;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public URL getResource(String ref) {
/*    */     try {
/* 32 */       File file = new File(this.root, ref);
/* 33 */       if (!file.exists()) {
/* 34 */         file = new File(ref);
/*    */       }
/* 36 */       if (!file.exists()) {
/* 37 */         return null;
/*    */       }
/*    */       
/* 40 */       return file.toURI().toURL();
/* 41 */     } catch (IOException e) {
/* 42 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String ref) {
/*    */     try {
/* 51 */       File file = new File(this.root, ref);
/* 52 */       if (!file.exists()) {
/* 53 */         file = new File(ref);
/*    */       }
/* 55 */       return new FileInputStream(file);
/* 56 */     } catch (IOException e) {
/* 57 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\FileSystemLocation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */