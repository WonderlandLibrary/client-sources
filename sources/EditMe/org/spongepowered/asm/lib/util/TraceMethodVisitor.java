package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public final class TraceMethodVisitor extends MethodVisitor {
   public final Printer p;

   public TraceMethodVisitor(Printer var1) {
      this((MethodVisitor)null, var1);
   }

   public TraceMethodVisitor(MethodVisitor var1, Printer var2) {
      super(327680, var1);
      this.p = var2;
   }

   public void visitParameter(String var1, int var2) {
      this.p.visitParameter(var1, var2);
      super.visitParameter(var1, var2);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      Printer var3 = this.p.visitMethodAnnotation(var1, var2);
      AnnotationVisitor var4 = this.mv == null ? null : this.mv.visitAnnotation(var1, var2);
      return new TraceAnnotationVisitor(var4, var3);
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      Printer var5 = this.p.visitMethodTypeAnnotation(var1, var2, var3, var4);
      AnnotationVisitor var6 = this.mv == null ? null : this.mv.visitTypeAnnotation(var1, var2, var3, var4);
      return new TraceAnnotationVisitor(var6, var5);
   }

   public void visitAttribute(Attribute var1) {
      this.p.visitMethodAttribute(var1);
      super.visitAttribute(var1);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      Printer var1 = this.p.visitAnnotationDefault();
      AnnotationVisitor var2 = this.mv == null ? null : this.mv.visitAnnotationDefault();
      return new TraceAnnotationVisitor(var2, var1);
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      Printer var4 = this.p.visitParameterAnnotation(var1, var2, var3);
      AnnotationVisitor var5 = this.mv == null ? null : this.mv.visitParameterAnnotation(var1, var2, var3);
      return new TraceAnnotationVisitor(var5, var4);
   }

   public void visitCode() {
      this.p.visitCode();
      super.visitCode();
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      this.p.visitFrame(var1, var2, var3, var4, var5);
      super.visitFrame(var1, var2, var3, var4, var5);
   }

   public void visitInsn(int var1) {
      this.p.visitInsn(var1);
      super.visitInsn(var1);
   }

   public void visitIntInsn(int var1, int var2) {
      this.p.visitIntInsn(var1, var2);
      super.visitIntInsn(var1, var2);
   }

   public void visitVarInsn(int var1, int var2) {
      this.p.visitVarInsn(var1, var2);
      super.visitVarInsn(var1, var2);
   }

   public void visitTypeInsn(int var1, String var2) {
      this.p.visitTypeInsn(var1, var2);
      super.visitTypeInsn(var1, var2);
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.p.visitFieldInsn(var1, var2, var3, var4);
      super.visitFieldInsn(var1, var2, var3, var4);
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.p.visitMethodInsn(var1, var2, var3, var4);
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, var2, var3, var4);
         }

      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.p.visitMethodInsn(var1, var2, var3, var4, var5);
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, var2, var3, var4, var5);
         }

      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.p.visitInvokeDynamicInsn(var1, var2, var3, var4);
      super.visitInvokeDynamicInsn(var1, var2, var3, var4);
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.p.visitJumpInsn(var1, var2);
      super.visitJumpInsn(var1, var2);
   }

   public void visitLabel(Label var1) {
      this.p.visitLabel(var1);
      super.visitLabel(var1);
   }

   public void visitLdcInsn(Object var1) {
      this.p.visitLdcInsn(var1);
      super.visitLdcInsn(var1);
   }

   public void visitIincInsn(int var1, int var2) {
      this.p.visitIincInsn(var1, var2);
      super.visitIincInsn(var1, var2);
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.p.visitTableSwitchInsn(var1, var2, var3, var4);
      super.visitTableSwitchInsn(var1, var2, var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.p.visitLookupSwitchInsn(var1, var2, var3);
      super.visitLookupSwitchInsn(var1, var2, var3);
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.p.visitMultiANewArrayInsn(var1, var2);
      super.visitMultiANewArrayInsn(var1, var2);
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      Printer var5 = this.p.visitInsnAnnotation(var1, var2, var3, var4);
      AnnotationVisitor var6 = this.mv == null ? null : this.mv.visitInsnAnnotation(var1, var2, var3, var4);
      return new TraceAnnotationVisitor(var6, var5);
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.p.visitTryCatchBlock(var1, var2, var3, var4);
      super.visitTryCatchBlock(var1, var2, var3, var4);
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      Printer var5 = this.p.visitTryCatchAnnotation(var1, var2, var3, var4);
      AnnotationVisitor var6 = this.mv == null ? null : this.mv.visitTryCatchAnnotation(var1, var2, var3, var4);
      return new TraceAnnotationVisitor(var6, var5);
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.p.visitLocalVariable(var1, var2, var3, var4, var5, var6);
      super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      Printer var8 = this.p.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, var6, var7);
      AnnotationVisitor var9 = this.mv == null ? null : this.mv.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, var6, var7);
      return new TraceAnnotationVisitor(var9, var8);
   }

   public void visitLineNumber(int var1, Label var2) {
      this.p.visitLineNumber(var1, var2);
      super.visitLineNumber(var1, var2);
   }

   public void visitMaxs(int var1, int var2) {
      this.p.visitMaxs(var1, var2);
      super.visitMaxs(var1, var2);
   }

   public void visitEnd() {
      this.p.visitMethodEnd();
      super.visitEnd();
   }
}
