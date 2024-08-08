package org.spongepowered.asm.lib;

public abstract class MethodVisitor {
   protected final int api;
   protected MethodVisitor mv;

   public MethodVisitor(int var1) {
      this(var1, (MethodVisitor)null);
   }

   public MethodVisitor(int var1, MethodVisitor var2) {
      if (var1 != 262144 && var1 != 327680) {
         throw new IllegalArgumentException();
      } else {
         this.api = var1;
         this.mv = var2;
      }
   }

   public void visitParameter(String var1, int var2) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         if (this.mv != null) {
            this.mv.visitParameter(var1, var2);
         }

      }
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return this.mv != null ? this.mv.visitAnnotationDefault() : null;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      return this.mv != null ? this.mv.visitAnnotation(var1, var2) : null;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.mv != null ? this.mv.visitTypeAnnotation(var1, var2, var3, var4) : null;
      }
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      return this.mv != null ? this.mv.visitParameterAnnotation(var1, var2, var3) : null;
   }

   public void visitAttribute(Attribute var1) {
      if (this.mv != null) {
         this.mv.visitAttribute(var1);
      }

   }

   public void visitCode() {
      if (this.mv != null) {
         this.mv.visitCode();
      }

   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (this.mv != null) {
         this.mv.visitFrame(var1, var2, var3, var4, var5);
      }

   }

   public void visitInsn(int var1) {
      if (this.mv != null) {
         this.mv.visitInsn(var1);
      }

   }

   public void visitIntInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitIntInsn(var1, var2);
      }

   }

   public void visitVarInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitVarInsn(var1, var2);
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      if (this.mv != null) {
         this.mv.visitTypeInsn(var1, var2);
      }

   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      if (this.mv != null) {
         this.mv.visitFieldInsn(var1, var2, var3, var4);
      }

   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         boolean var5 = var1 == 185;
         this.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, var2, var3, var4);
         }

      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         if (var5 != (var1 == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
         } else {
            this.visitMethodInsn(var1, var2, var3, var4);
         }
      } else {
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, var2, var3, var4, var5);
         }

      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      if (this.mv != null) {
         this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
      }

   }

   public void visitJumpInsn(int var1, Label var2) {
      if (this.mv != null) {
         this.mv.visitJumpInsn(var1, var2);
      }

   }

   public void visitLabel(Label var1) {
      if (this.mv != null) {
         this.mv.visitLabel(var1);
      }

   }

   public void visitLdcInsn(Object var1) {
      if (this.mv != null) {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void visitIincInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitIincInsn(var1, var2);
      }

   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      if (this.mv != null) {
         this.mv.visitTableSwitchInsn(var1, var2, var3, var4);
      }

   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      if (this.mv != null) {
         this.mv.visitLookupSwitchInsn(var1, var2, var3);
      }

   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      if (this.mv != null) {
         this.mv.visitMultiANewArrayInsn(var1, var2);
      }

   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.mv != null ? this.mv.visitInsnAnnotation(var1, var2, var3, var4) : null;
      }
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      if (this.mv != null) {
         this.mv.visitTryCatchBlock(var1, var2, var3, var4);
      }

   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.mv != null ? this.mv.visitTryCatchAnnotation(var1, var2, var3, var4) : null;
      }
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      if (this.mv != null) {
         this.mv.visitLocalVariable(var1, var2, var3, var4, var5, var6);
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      if (this.api < 327680) {
         throw new RuntimeException();
      } else {
         return this.mv != null ? this.mv.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, var6, var7) : null;
      }
   }

   public void visitLineNumber(int var1, Label var2) {
      if (this.mv != null) {
         this.mv.visitLineNumber(var1, var2);
      }

   }

   public void visitMaxs(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitMaxs(var1, var2);
      }

   }

   public void visitEnd() {
      if (this.mv != null) {
         this.mv.visitEnd();
      }

   }
}
