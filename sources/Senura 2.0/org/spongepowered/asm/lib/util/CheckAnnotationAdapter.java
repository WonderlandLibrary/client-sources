/*     */ package org.spongepowered.asm.lib.util;
/*     */ 
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
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
/*     */ public class CheckAnnotationAdapter
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   private final boolean named;
/*     */   private boolean end;
/*     */   
/*     */   public CheckAnnotationAdapter(AnnotationVisitor av) {
/*  48 */     this(av, true);
/*     */   }
/*     */   
/*     */   CheckAnnotationAdapter(AnnotationVisitor av, boolean named) {
/*  52 */     super(327680, av);
/*  53 */     this.named = named;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(String name, Object value) {
/*  58 */     checkEnd();
/*  59 */     checkName(name);
/*  60 */     if (!(value instanceof Byte) && !(value instanceof Boolean) && !(value instanceof Character) && !(value instanceof Short) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Float) && !(value instanceof Double) && !(value instanceof String) && !(value instanceof Type) && !(value instanceof byte[]) && !(value instanceof boolean[]) && !(value instanceof char[]) && !(value instanceof short[]) && !(value instanceof int[]) && !(value instanceof long[]) && !(value instanceof float[]) && !(value instanceof double[]))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       throw new IllegalArgumentException("Invalid annotation value");
/*     */     }
/*  71 */     if (value instanceof Type) {
/*  72 */       int sort = ((Type)value).getSort();
/*  73 */       if (sort == 11) {
/*  74 */         throw new IllegalArgumentException("Invalid annotation value");
/*     */       }
/*     */     } 
/*  77 */     if (this.av != null) {
/*  78 */       this.av.visit(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String name, String desc, String value) {
/*  85 */     checkEnd();
/*  86 */     checkName(name);
/*  87 */     CheckMethodAdapter.checkDesc(desc, false);
/*  88 */     if (value == null) {
/*  89 */       throw new IllegalArgumentException("Invalid enum value");
/*     */     }
/*  91 */     if (this.av != null) {
/*  92 */       this.av.visitEnum(name, desc, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/*  99 */     checkEnd();
/* 100 */     checkName(name);
/* 101 */     CheckMethodAdapter.checkDesc(desc, false);
/* 102 */     return new CheckAnnotationAdapter((this.av == null) ? null : this.av
/* 103 */         .visitAnnotation(name, desc));
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String name) {
/* 108 */     checkEnd();
/* 109 */     checkName(name);
/* 110 */     return new CheckAnnotationAdapter((this.av == null) ? null : this.av
/* 111 */         .visitArray(name), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 116 */     checkEnd();
/* 117 */     this.end = true;
/* 118 */     if (this.av != null) {
/* 119 */       this.av.visitEnd();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 124 */     if (this.end) {
/* 125 */       throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkName(String name) {
/* 131 */     if (this.named && name == null)
/* 132 */       throw new IllegalArgumentException("Annotation value name must not be null"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\CheckAnnotationAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */