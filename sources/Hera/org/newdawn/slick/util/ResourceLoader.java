/*     */ package org.newdawn.slick.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceLoader
/*     */ {
/*  17 */   private static ArrayList locations = new ArrayList();
/*     */   
/*     */   static {
/*  20 */     locations.add(new ClasspathLocation());
/*  21 */     locations.add(new FileSystemLocation(new File(".")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addResourceLocation(ResourceLocation location) {
/*  30 */     locations.add(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeResourceLocation(ResourceLocation location) {
/*  39 */     locations.remove(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAllResourceLocations() {
/*  47 */     locations.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(String ref) {
/*  57 */     InputStream in = null;
/*     */     
/*  59 */     for (int i = 0; i < locations.size(); i++) {
/*  60 */       ResourceLocation location = locations.get(i);
/*  61 */       in = location.getResourceAsStream(ref);
/*  62 */       if (in != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     if (in == null)
/*     */     {
/*  69 */       throw new RuntimeException("Resource not found: " + ref);
/*     */     }
/*     */     
/*  72 */     return new BufferedInputStream(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean resourceExists(String ref) {
/*  82 */     URL url = null;
/*     */     
/*  84 */     for (int i = 0; i < locations.size(); i++) {
/*  85 */       ResourceLocation location = locations.get(i);
/*  86 */       url = location.getResource(ref);
/*  87 */       if (url != null) {
/*  88 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL getResource(String ref) {
/* 103 */     URL url = null;
/*     */     
/* 105 */     for (int i = 0; i < locations.size(); i++) {
/* 106 */       ResourceLocation location = locations.get(i);
/* 107 */       url = location.getResource(ref);
/* 108 */       if (url != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     if (url == null)
/*     */     {
/* 115 */       throw new RuntimeException("Resource not found: " + ref);
/*     */     }
/*     */     
/* 118 */     return url;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\ResourceLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */