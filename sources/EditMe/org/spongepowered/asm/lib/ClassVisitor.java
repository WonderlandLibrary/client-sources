package org.spongepowered.asm.lib;

public abstract class ClassVisitor {
   protected final int api;
   protected ClassVisitor cv;

   public ClassVisitor(int var1) {
      this(var1, (ClassVisitor)null);
   }

   public ClassVisitor(int var1, ClassVisitor var2) {
      if (var1 != 262144 && var1 != 327680) {
         throw new IllegalArgumentException();
      } else {
         this.api = var1;
         this.cv = var2;
      }
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      if (this.cv != null) {
         this.cv.visit(var1, var2, var3, var4, var5, var6);
      }

   }

   public void visitSource(String var1, String var2) {
      if (this.cv != null) {
         this.cv.visitSource(var1, var2);
      }

   }

   public void visitOuterClass(String var1, String var2, String var3) {
      if (this.cv != null) {
         this.cv.visitOuterClass(var1, var2, var3);
      }

   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      return this.cv != null ? this.cv.visitAnnotation(var1, var2) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.cv != null ? this.cv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      }
   }

   public void visitAttribute(Attribute var1) {
      if (this.cv != null) {
         this.cv.visitAttribute(var1);
      }

   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.cv != null) {
         this.cv.visitInnerClass(var1, var2, var3, var4);
      }

   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      return this.cv != null ? this.cv.visitField(var1, var2, var3, var4, var5) : null;
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return this.cv != null ? this.cv.visitMethod(var1, var2, var3, var4, var5) : null;
   }

   public void visitEnd() {
      if (this.cv != null) {
         this.cv.visitEnd();
      }

   }
}
