package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;

public final class TraceAnnotationVisitor extends AnnotationVisitor {
   private final Printer p;

   public TraceAnnotationVisitor(Printer var1) {
      this((AnnotationVisitor)null, var1);
   }

   public TraceAnnotationVisitor(AnnotationVisitor var1, Printer var2) {
      super(327680, var1);
      this.p = var2;
   }

   public void visit(String var1, Object var2) {
      this.p.visit(var1, var2);
      super.visit(var1, var2);
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.p.visitEnum(var1, var2, var3);
      super.visitEnum(var1, var2, var3);
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      Printer var3 = this.p.visitAnnotation(var1, var2);
      AnnotationVisitor var4 = this.av == null ? null : this.av.visitAnnotation(var1, var2);
      return new TraceAnnotationVisitor(var4, var3);
   }

   public AnnotationVisitor visitArray(String var1) {
      Printer var2 = this.p.visitArray(var1);
      AnnotationVisitor var3 = this.av == null ? null : this.av.visitArray(var1);
      return new TraceAnnotationVisitor(var3, var2);
   }

   public void visitEnd() {
      this.p.visitAnnotationEnd();
      super.visitEnd();
   }
}
