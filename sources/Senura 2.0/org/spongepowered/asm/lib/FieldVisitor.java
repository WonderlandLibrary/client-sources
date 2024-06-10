/*     */ package org.spongepowered.asm.lib;
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
/*     */ public abstract class FieldVisitor
/*     */ {
/*     */   protected final int api;
/*     */   protected FieldVisitor fv;
/*     */   
/*     */   public FieldVisitor(int api) {
/*  61 */     this(api, null);
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
/*     */   public FieldVisitor(int api, FieldVisitor fv) {
/*  75 */     if (api != 262144 && api != 327680) {
/*  76 */       throw new IllegalArgumentException();
/*     */     }
/*  78 */     this.api = api;
/*  79 */     this.fv = fv;
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
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  93 */     if (this.fv != null) {
/*  94 */       return this.fv.visitAnnotation(desc, visible);
/*     */     }
/*  96 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 119 */     if (this.api < 327680) {
/* 120 */       throw new RuntimeException();
/*     */     }
/* 122 */     if (this.fv != null) {
/* 123 */       return this.fv.visitTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 135 */     if (this.fv != null) {
/* 136 */       this.fv.visitAttribute(attr);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 146 */     if (this.fv != null)
/* 147 */       this.fv.visitEnd(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\FieldVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */