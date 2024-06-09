/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClasspathLocation
/*    */   implements ResourceLocation
/*    */ {
/*    */   public URL getResource(String ref) {
/* 16 */     String cpRef = ref.replace('\\', '/');
/* 17 */     return ResourceLoader.class.getClassLoader().getResource(cpRef);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String ref) {
/* 24 */     String cpRef = ref.replace('\\', '/');
/* 25 */     return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\ClasspathLocation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */