/*     */ package io.netty.util;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ @Deprecated
/*     */ public class UniqueName
/*     */   implements Comparable<UniqueName>
/*     */ {
/*  29 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */ 
/*     */ 
/*     */   
/*     */   private final int id;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public UniqueName(ConcurrentMap<String, Boolean> map, String name, Object... args) {
/*  42 */     if (map == null) {
/*  43 */       throw new NullPointerException("map");
/*     */     }
/*  45 */     if (name == null) {
/*  46 */       throw new NullPointerException("name");
/*     */     }
/*  48 */     if (args != null && args.length > 0) {
/*  49 */       validateArgs(args);
/*     */     }
/*     */     
/*  52 */     if (map.putIfAbsent(name, Boolean.TRUE) != null) {
/*  53 */       throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[] { name }));
/*     */     }
/*     */     
/*  56 */     this.id = nextId.incrementAndGet();
/*  57 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateArgs(Object... args) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String name() {
/*  77 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int id() {
/*  86 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  91 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object o) {
/*  96 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(UniqueName other) {
/* 101 */     if (this == other) {
/* 102 */       return 0;
/*     */     }
/*     */     
/* 105 */     int returnCode = this.name.compareTo(other.name);
/* 106 */     if (returnCode != 0) {
/* 107 */       return returnCode;
/*     */     }
/*     */     
/* 110 */     return Integer.valueOf(this.id).compareTo(Integer.valueOf(other.id));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     return name();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\UniqueName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */