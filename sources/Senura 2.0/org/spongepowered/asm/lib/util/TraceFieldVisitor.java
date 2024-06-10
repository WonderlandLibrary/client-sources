/*    */ package org.spongepowered.asm.lib.util;
/*    */ 
/*    */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*    */ import org.spongepowered.asm.lib.Attribute;
/*    */ import org.spongepowered.asm.lib.FieldVisitor;
/*    */ import org.spongepowered.asm.lib.TypePath;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TraceFieldVisitor
/*    */   extends FieldVisitor
/*    */ {
/*    */   public final Printer p;
/*    */   
/*    */   public TraceFieldVisitor(Printer p) {
/* 49 */     this(null, p);
/*    */   }
/*    */   
/*    */   public TraceFieldVisitor(FieldVisitor fv, Printer p) {
/* 53 */     super(327680, fv);
/* 54 */     this.p = p;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 60 */     Printer p = this.p.visitFieldAnnotation(desc, visible);
/* 61 */     AnnotationVisitor av = (this.fv == null) ? null : this.fv.visitAnnotation(desc, visible);
/*    */     
/* 63 */     return new TraceAnnotationVisitor(av, p);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 69 */     Printer p = this.p.visitFieldTypeAnnotation(typeRef, typePath, desc, visible);
/*    */     
/* 71 */     AnnotationVisitor av = (this.fv == null) ? null : this.fv.visitTypeAnnotation(typeRef, typePath, desc, visible);
/*    */     
/* 73 */     return new TraceAnnotationVisitor(av, p);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitAttribute(Attribute attr) {
/* 78 */     this.p.visitFieldAttribute(attr);
/* 79 */     super.visitAttribute(attr);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitEnd() {
/* 84 */     this.p.visitFieldEnd();
/* 85 */     super.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\TraceFieldVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */