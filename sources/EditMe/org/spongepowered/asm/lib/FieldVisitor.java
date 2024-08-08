package org.spongepowered.asm.lib;

public abstract class FieldVisitor {
   protected final int api;
   protected FieldVisitor fv;

   public FieldVisitor(int var1) {
      this(var1, (FieldVisitor)null);
   }

   public FieldVisitor(int var1, FieldVisitor var2) {
      if (var1 != 262144 && var1 != 327680) {
         throw new IllegalArgumentException();
      } else {
         this.api = var1;
         this.fv = var2;
      }
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      return this.fv != null ? this.fv.visitAnnotation(var1, var2) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.fv != null ? this.fv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      }
   }

   public void visitAttribute(Attribute var1) {
      if (this.fv != null) {
         this.fv.visitAttribute(var1);
      }

   }

   public void visitEnd() {
      if (this.fv != null) {
         this.fv.visitEnd();
      }

   }
}
