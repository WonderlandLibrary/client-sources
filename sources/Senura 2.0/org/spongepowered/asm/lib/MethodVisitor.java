/*     */ package org.spongepowered.asm.lib;
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
/*     */ public abstract class MethodVisitor
/*     */ {
/*     */   protected final int api;
/*     */   protected MethodVisitor mv;
/*     */   
/*     */   public MethodVisitor(int api) {
/*  78 */     this(api, null);
/*     */   }
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
/*     */   public MethodVisitor(int api, MethodVisitor mv) {
/*  92 */     if (api != 262144 && api != 327680) {
/*  93 */       throw new IllegalArgumentException();
/*     */     }
/*  95 */     this.api = api;
/*  96 */     this.mv = mv;
/*     */   }
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
/*     */   public void visitParameter(String name, int access) {
/* 114 */     if (this.api < 327680) {
/* 115 */       throw new RuntimeException();
/*     */     }
/* 117 */     if (this.mv != null) {
/* 118 */       this.mv.visitParameter(name, access);
/*     */     }
/*     */   }
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
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/* 133 */     if (this.mv != null) {
/* 134 */       return this.mv.visitAnnotationDefault();
/*     */     }
/* 136 */     return null;
/*     */   }
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
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 150 */     if (this.mv != null) {
/* 151 */       return this.mv.visitAnnotation(desc, visible);
/*     */     }
/* 153 */     return null;
/*     */   }
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
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 183 */     if (this.api < 327680) {
/* 184 */       throw new RuntimeException();
/*     */     }
/* 186 */     if (this.mv != null) {
/* 187 */       return this.mv.visitTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     }
/* 189 */     return null;
/*     */   }
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
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/* 206 */     if (this.mv != null) {
/* 207 */       return this.mv.visitParameterAnnotation(parameter, desc, visible);
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 219 */     if (this.mv != null) {
/* 220 */       this.mv.visitAttribute(attr);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCode() {
/* 228 */     if (this.mv != null) {
/* 229 */       this.mv.visitCode();
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/* 310 */     if (this.mv != null) {
/* 311 */       this.mv.visitFrame(type, nLocal, local, nStack, stack);
/*     */     }
/*     */   }
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
/*     */   public void visitInsn(int opcode) {
/* 340 */     if (this.mv != null) {
/* 341 */       this.mv.visitInsn(opcode);
/*     */     }
/*     */   }
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
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 364 */     if (this.mv != null) {
/* 365 */       this.mv.visitIntInsn(opcode, operand);
/*     */     }
/*     */   }
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
/*     */   public void visitVarInsn(int opcode, int var) {
/* 382 */     if (this.mv != null) {
/* 383 */       this.mv.visitVarInsn(opcode, var);
/*     */     }
/*     */   }
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
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 400 */     if (this.mv != null) {
/* 401 */       this.mv.visitTypeInsn(opcode, type);
/*     */     }
/*     */   }
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
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 422 */     if (this.mv != null) {
/* 423 */       this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */     }
/*     */   }
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
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 446 */     if (this.api >= 327680) {
/* 447 */       boolean itf = (opcode == 185);
/* 448 */       visitMethodInsn(opcode, owner, name, desc, itf);
/*     */       return;
/*     */     } 
/* 451 */     if (this.mv != null) {
/* 452 */       this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */     }
/*     */   }
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
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/* 476 */     if (this.api < 327680) {
/* 477 */       if (itf != ((opcode == 185))) {
/* 478 */         throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
/*     */       }
/*     */       
/* 481 */       visitMethodInsn(opcode, owner, name, desc);
/*     */       return;
/*     */     } 
/* 484 */     if (this.mv != null) {
/* 485 */       this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */     }
/*     */   }
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
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/* 507 */     if (this.mv != null) {
/* 508 */       this.mv.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
/*     */     }
/*     */   }
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
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 527 */     if (this.mv != null) {
/* 528 */       this.mv.visitJumpInsn(opcode, label);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLabel(Label label) {
/* 540 */     if (this.mv != null) {
/* 541 */       this.mv.visitLabel(label);
/*     */     }
/*     */   }
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
/*     */   public void visitLdcInsn(Object cst) {
/* 594 */     if (this.mv != null) {
/* 595 */       this.mv.visitLdcInsn(cst);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {
/* 608 */     if (this.mv != null) {
/* 609 */       this.mv.visitIincInsn(var, increment);
/*     */     }
/*     */   }
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
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/* 628 */     if (this.mv != null) {
/* 629 */       this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */     }
/*     */   }
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
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 645 */     if (this.mv != null) {
/* 646 */       this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 659 */     if (this.mv != null) {
/* 660 */       this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */     }
/*     */   }
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
/*     */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 697 */     if (this.api < 327680) {
/* 698 */       throw new RuntimeException();
/*     */     }
/* 700 */     if (this.mv != null) {
/* 701 */       return this.mv.visitInsnAnnotation(typeRef, typePath, desc, visible);
/*     */     }
/* 703 */     return null;
/*     */   }
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
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 729 */     if (this.mv != null) {
/* 730 */       this.mv.visitTryCatchBlock(start, end, handler, type);
/*     */     }
/*     */   }
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
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 757 */     if (this.api < 327680) {
/* 758 */       throw new RuntimeException();
/*     */     }
/* 760 */     if (this.mv != null) {
/* 761 */       return this.mv.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
/*     */     }
/* 763 */     return null;
/*     */   }
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
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 791 */     if (this.mv != null) {
/* 792 */       this.mv.visitLocalVariable(name, desc, signature, start, end, index);
/*     */     }
/*     */   }
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
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 828 */     if (this.api < 327680) {
/* 829 */       throw new RuntimeException();
/*     */     }
/* 831 */     if (this.mv != null) {
/* 832 */       return this.mv.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
/*     */     }
/*     */     
/* 835 */     return null;
/*     */   }
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
/*     */   public void visitLineNumber(int line, Label start) {
/* 851 */     if (this.mv != null) {
/* 852 */       this.mv.visitLineNumber(line, start);
/*     */     }
/*     */   }
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
/*     */   public void visitMaxs(int maxStack, int maxLocals) {
/* 866 */     if (this.mv != null) {
/* 867 */       this.mv.visitMaxs(maxStack, maxLocals);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 877 */     if (this.mv != null)
/* 878 */       this.mv.visitEnd(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\MethodVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */