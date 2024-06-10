/*    */ package org.spongepowered.asm.lib.util;
/*    */ 
/*    */ import org.spongepowered.asm.lib.AnnotationVisitor;
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
/*    */ public final class TraceAnnotationVisitor
/*    */   extends AnnotationVisitor
/*    */ {
/*    */   private final Printer p;
/*    */   
/*    */   public TraceAnnotationVisitor(Printer p) {
/* 46 */     this(null, p);
/*    */   }
/*    */   
/*    */   public TraceAnnotationVisitor(AnnotationVisitor av, Printer p) {
/* 50 */     super(327680, av);
/* 51 */     this.p = p;
/*    */   }
/*    */ 
/*    */   
/*    */   public void visit(String name, Object value) {
/* 56 */     this.p.visit(name, value);
/* 57 */     super.visit(name, value);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitEnum(String name, String desc, String value) {
/* 63 */     this.p.visitEnum(name, desc, value);
/* 64 */     super.visitEnum(name, desc, value);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 70 */     Printer p = this.p.visitAnnotation(name, desc);
/*    */     
/* 72 */     AnnotationVisitor av = (this.av == null) ? null : this.av.visitAnnotation(name, desc);
/* 73 */     return new TraceAnnotationVisitor(av, p);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitArray(String name) {
/* 78 */     Printer p = this.p.visitArray(name);
/*    */     
/* 80 */     AnnotationVisitor av = (this.av == null) ? null : this.av.visitArray(name);
/* 81 */     return new TraceAnnotationVisitor(av, p);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitEnd() {
/* 86 */     this.p.visitAnnotationEnd();
/* 87 */     super.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\TraceAnnotationVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */