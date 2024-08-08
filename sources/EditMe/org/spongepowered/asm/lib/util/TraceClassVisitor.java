package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceClassVisitor extends ClassVisitor {
   private final PrintWriter pw;
   public final Printer p;

   public TraceClassVisitor(PrintWriter var1) {
      this((ClassVisitor)null, var1);
   }

   public TraceClassVisitor(ClassVisitor var1, PrintWriter var2) {
      this(var1, new Textifier(), var2);
   }

   public TraceClassVisitor(ClassVisitor var1, Printer var2, PrintWriter var3) {
      super(327680, var1);
      this.pw = var3;
      this.p = var2;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.p.visit(var1, var2, var3, var4, var5, var6);
      super.visit(var1, var2, var3, var4, var5, var6);
   }

   public void visitSource(String var1, String var2) {
      this.p.visitSource(var1, var2);
      super.visitSource(var1, var2);
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.p.visitOuterClass(var1, var2, var3);
      super.visitOuterClass(var1, var2, var3);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      Printer var3 = this.p.visitClassAnnotation(var1, var2);
      AnnotationVisitor var4 = this.cv == null ? null : this.cv.visitAnnotation(var1, var2);
      return new TraceAnnotationVisitor(var4, var3);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      Printer var5 = this.p.visitClassTypeAnnotation(var1, var2, var3, var4);
      AnnotationVisitor var6 = this.cv == null ? null : this.cv.visitTypeAnnotation(var1, var2, var3, var4);
      return new TraceAnnotationVisitor(var6, var5);
   }

   public void visitAttribute(Attribute var1) {
      this.p.visitClassAttribute(var1);
      super.visitAttribute(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.p.visitInnerClass(var1, var2, var3, var4);
      super.visitInnerClass(var1, var2, var3, var4);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      Printer var6 = this.p.visitField(var1, var2, var3, var4, var5);
      FieldVisitor var7 = this.cv == null ? null : this.cv.visitField(var1, var2, var3, var4, var5);
      return new TraceFieldVisitor(var7, var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      Printer var6 = this.p.visitMethod(var1, var2, var3, var4, var5);
      MethodVisitor var7 = this.cv == null ? null : this.cv.visitMethod(var1, var2, var3, var4, var5);
      return new TraceMethodVisitor(var7, var6);
   }

   public void visitEnd() {
      this.p.visitClassEnd();
      if (this.pw != null) {
         this.p.print(this.pw);
         this.pw.flush();
      }

      super.visitEnd();
   }
}
