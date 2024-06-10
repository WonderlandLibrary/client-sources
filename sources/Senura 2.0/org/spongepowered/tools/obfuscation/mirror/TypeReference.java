/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.lang.model.element.TypeElement;
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
/*     */ public class TypeReference
/*     */   implements Serializable, Comparable<TypeReference>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final String name;
/*     */   private transient TypeHandle handle;
/*     */   
/*     */   public TypeReference(TypeHandle handle) {
/*  55 */     this.name = handle.getName();
/*  56 */     this.handle = handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeReference(String name) {
/*  65 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  72 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*  79 */     return this.name.replace('/', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getHandle(ProcessingEnvironment processingEnv) {
/*  90 */     if (this.handle == null) {
/*  91 */       TypeElement element = processingEnv.getElementUtils().getTypeElement(getClassName());
/*     */       try {
/*  93 */         this.handle = new TypeHandle(element);
/*  94 */       } catch (Exception ex) {
/*     */         
/*  96 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return this.handle;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return String.format("TypeReference[%s]", new Object[] { this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(TypeReference other) {
/* 110 */     return (other == null) ? -1 : this.name.compareTo(other.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 115 */     return (other instanceof TypeReference && compareTo((TypeReference)other) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return this.name.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\TypeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */