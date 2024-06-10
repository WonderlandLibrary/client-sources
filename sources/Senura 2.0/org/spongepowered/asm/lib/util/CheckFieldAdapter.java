/*     */ package org.spongepowered.asm.lib.util;
/*     */ 
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Attribute;
/*     */ import org.spongepowered.asm.lib.FieldVisitor;
/*     */ import org.spongepowered.asm.lib.TypePath;
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
/*     */ public class CheckFieldAdapter
/*     */   extends FieldVisitor
/*     */ {
/*     */   private boolean end;
/*     */   
/*     */   public CheckFieldAdapter(FieldVisitor fv) {
/*  57 */     this(327680, fv);
/*  58 */     if (getClass() != CheckFieldAdapter.class) {
/*  59 */       throw new IllegalStateException();
/*     */     }
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
/*     */   protected CheckFieldAdapter(int api, FieldVisitor fv) {
/*  73 */     super(api, fv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  79 */     checkEnd();
/*  80 */     CheckMethodAdapter.checkDesc(desc, false);
/*  81 */     return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  87 */     checkEnd();
/*  88 */     int sort = typeRef >>> 24;
/*  89 */     if (sort != 19) {
/*  90 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  91 */           Integer.toHexString(sort));
/*     */     }
/*  93 */     CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
/*  94 */     CheckMethodAdapter.checkDesc(desc, false);
/*  95 */     return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 101 */     checkEnd();
/* 102 */     if (attr == null) {
/* 103 */       throw new IllegalArgumentException("Invalid attribute (must not be null)");
/*     */     }
/*     */     
/* 106 */     super.visitAttribute(attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 111 */     checkEnd();
/* 112 */     this.end = true;
/* 113 */     super.visitEnd();
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 117 */     if (this.end)
/* 118 */       throw new IllegalStateException("Cannot call a visit method after visitEnd has been called"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\CheckFieldAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */