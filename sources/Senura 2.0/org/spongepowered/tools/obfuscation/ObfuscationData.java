/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class ObfuscationData<T>
/*     */   implements Iterable<ObfuscationType>
/*     */ {
/*  53 */   private final Map<ObfuscationType, T> data = new HashMap<ObfuscationType, T>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final T defaultValue;
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData() {
/*  62 */     this(null);
/*     */   }
/*     */   
/*     */   public ObfuscationData(T defaultValue) {
/*  66 */     this.defaultValue = defaultValue;
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
/*     */   
/*     */   @Deprecated
/*     */   public void add(ObfuscationType type, T value) {
/*  81 */     put(type, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(ObfuscationType type, T value) {
/*  91 */     this.data.put(type, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  98 */     return this.data.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(ObfuscationType type) {
/* 109 */     T value = this.data.get(type);
/* 110 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ObfuscationType> iterator() {
/* 118 */     return this.data.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return String.format("ObfuscationData[%s,DEFAULT=%s]", new Object[] { listValues(), this.defaultValue });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String values() {
/* 135 */     return "[" + listValues() + "]";
/*     */   }
/*     */   
/*     */   private String listValues() {
/* 139 */     StringBuilder sb = new StringBuilder();
/* 140 */     boolean delim = false;
/* 141 */     for (ObfuscationType type : this.data.keySet()) {
/* 142 */       if (delim) {
/* 143 */         sb.append(',');
/*     */       }
/* 145 */       sb.append(type.getKey()).append('=').append(this.data.get(type));
/* 146 */       delim = true;
/*     */     } 
/* 148 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ObfuscationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */