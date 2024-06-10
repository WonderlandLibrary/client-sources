/*     */ package org.spongepowered.asm.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObfuscationUtil
/*     */ {
/*     */   public static String mapDescriptor(String desc, IClassRemapper remapper) {
/*  65 */     return remapDescriptor(desc, remapper, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unmapDescriptor(String desc, IClassRemapper remapper) {
/*  76 */     return remapDescriptor(desc, remapper, true);
/*     */   }
/*     */   
/*     */   private static String remapDescriptor(String desc, IClassRemapper remapper, boolean unmap) {
/*  80 */     StringBuilder sb = new StringBuilder();
/*  81 */     StringBuilder token = null;
/*     */     
/*  83 */     for (int pos = 0; pos < desc.length(); pos++) {
/*  84 */       char c = desc.charAt(pos);
/*  85 */       if (token != null) {
/*  86 */         if (c == ';') {
/*  87 */           sb.append('L').append(remap(token.toString(), remapper, unmap)).append(';');
/*  88 */           token = null;
/*     */         } else {
/*  90 */           token.append(c);
/*     */         }
/*     */       
/*     */       }
/*  94 */       else if (c == 'L') {
/*  95 */         token = new StringBuilder();
/*     */       } else {
/*  97 */         sb.append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     if (token != null) {
/* 102 */       throw new IllegalArgumentException("Invalid descriptor '" + desc + "', missing ';'");
/*     */     }
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static Object remap(String typeName, IClassRemapper remapper, boolean unmap) {
/* 109 */     String result = unmap ? remapper.unmap(typeName) : remapper.map(typeName);
/* 110 */     return (result != null) ? result : typeName;
/*     */   }
/*     */   
/*     */   public static interface IClassRemapper {
/*     */     String map(String param1String);
/*     */     
/*     */     String unmap(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\ObfuscationUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */