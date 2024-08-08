package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceFieldVisitor extends FieldVisitor {
   public final Printer p;

   public TraceFieldVisitor(Printer var1) {
      this((FieldVisitor)null, var1);
   }

   public TraceFieldVisitor(FieldVisitor var1, Printer var2) {
      super(327680, var1);
      this.p = var2;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      Printer var3 = this.p.visitFieldAnnotation(var1, var2);
      AnnotationVisitor var4 = this.fv == null ? null : this.fv.visitAnnotation(var1, var2);
      return new TraceAnnotationVisitor(var4, var3);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      Printer var5 = this.p.visitFieldTypeAnnotation(var1, var2, var3, var4);
      AnnotationVisitor var6 = this.fv == null ? null : this.fv.visitTypeAnnotation(var1, var2, var3, var4);
      return new TraceAnnotationVisitor(var6, var5);
   }

   public void visitAttribute(Attribute var1) {
      this.p.visitFieldAttribute(var1);
      super.visitAttribute(var1);
   }

   public void visitEnd() {
      this.p.visitFieldEnd();
      super.visitEnd();
   }
}
