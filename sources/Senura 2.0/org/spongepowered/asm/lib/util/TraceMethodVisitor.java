/*     */ package org.spongepowered.asm.lib.util;
/*     */ 
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Attribute;
/*     */ import org.spongepowered.asm.lib.Handle;
/*     */ import org.spongepowered.asm.lib.Label;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.TypePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceMethodVisitor
/*     */   extends MethodVisitor
/*     */ {
/*     */   public final Printer p;
/*     */   
/*     */   public TraceMethodVisitor(Printer p) {
/*  51 */     this((MethodVisitor)null, p);
/*     */   }
/*     */   
/*     */   public TraceMethodVisitor(MethodVisitor mv, Printer p) {
/*  55 */     super(327680, mv);
/*  56 */     this.p = p;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitParameter(String name, int access) {
/*  61 */     this.p.visitParameter(name, access);
/*  62 */     super.visitParameter(name, access);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  68 */     Printer p = this.p.visitMethodAnnotation(desc, visible);
/*  69 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitAnnotation(desc, visible);
/*     */     
/*  71 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  77 */     Printer p = this.p.visitMethodTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     
/*  79 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     
/*  81 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/*  86 */     this.p.visitMethodAttribute(attr);
/*  87 */     super.visitAttribute(attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/*  92 */     Printer p = this.p.visitAnnotationDefault();
/*  93 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitAnnotationDefault();
/*  94 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/* 100 */     Printer p = this.p.visitParameterAnnotation(parameter, desc, visible);
/* 101 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitParameterAnnotation(parameter, desc, visible);
/*     */     
/* 103 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCode() {
/* 108 */     this.p.visitCode();
/* 109 */     super.visitCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/* 115 */     this.p.visitFrame(type, nLocal, local, nStack, stack);
/* 116 */     super.visitFrame(type, nLocal, local, nStack, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int opcode) {
/* 121 */     this.p.visitInsn(opcode);
/* 122 */     super.visitInsn(opcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 127 */     this.p.visitIntInsn(opcode, operand);
/* 128 */     super.visitIntInsn(opcode, operand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 133 */     this.p.visitVarInsn(opcode, var);
/* 134 */     super.visitVarInsn(opcode, var);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 139 */     this.p.visitTypeInsn(opcode, type);
/* 140 */     super.visitTypeInsn(opcode, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 146 */     this.p.visitFieldInsn(opcode, owner, name, desc);
/* 147 */     super.visitFieldInsn(opcode, owner, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 154 */     if (this.api >= 327680) {
/* 155 */       super.visitMethodInsn(opcode, owner, name, desc);
/*     */       return;
/*     */     } 
/* 158 */     this.p.visitMethodInsn(opcode, owner, name, desc);
/* 159 */     if (this.mv != null) {
/* 160 */       this.mv.visitMethodInsn(opcode, owner, name, desc);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/* 167 */     if (this.api < 327680) {
/* 168 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */       return;
/*     */     } 
/* 171 */     this.p.visitMethodInsn(opcode, owner, name, desc, itf);
/* 172 */     if (this.mv != null) {
/* 173 */       this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/* 180 */     this.p.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
/* 181 */     super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 186 */     this.p.visitJumpInsn(opcode, label);
/* 187 */     super.visitJumpInsn(opcode, label);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label label) {
/* 192 */     this.p.visitLabel(label);
/* 193 */     super.visitLabel(label);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 198 */     this.p.visitLdcInsn(cst);
/* 199 */     super.visitLdcInsn(cst);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {
/* 204 */     this.p.visitIincInsn(var, increment);
/* 205 */     super.visitIincInsn(var, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/* 211 */     this.p.visitTableSwitchInsn(min, max, dflt, labels);
/* 212 */     super.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 218 */     this.p.visitLookupSwitchInsn(dflt, keys, labels);
/* 219 */     super.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 224 */     this.p.visitMultiANewArrayInsn(desc, dims);
/* 225 */     super.visitMultiANewArrayInsn(desc, dims);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 232 */     Printer p = this.p.visitInsnAnnotation(typeRef, typePath, desc, visible);
/* 233 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitInsnAnnotation(typeRef, typePath, desc, visible);
/*     */     
/* 235 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 241 */     this.p.visitTryCatchBlock(start, end, handler, type);
/* 242 */     super.visitTryCatchBlock(start, end, handler, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 248 */     Printer p = this.p.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
/*     */     
/* 250 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
/*     */     
/* 252 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 259 */     this.p.visitLocalVariable(name, desc, signature, start, end, index);
/* 260 */     super.visitLocalVariable(name, desc, signature, start, end, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 267 */     Printer p = this.p.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
/*     */ 
/*     */     
/* 270 */     AnnotationVisitor av = (this.mv == null) ? null : this.mv.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
/*     */     
/* 272 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLineNumber(int line, Label start) {
/* 277 */     this.p.visitLineNumber(line, start);
/* 278 */     super.visitLineNumber(line, start);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int maxStack, int maxLocals) {
/* 283 */     this.p.visitMaxs(maxStack, maxLocals);
/* 284 */     super.visitMaxs(maxStack, maxLocals);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 289 */     this.p.visitMethodEnd();
/* 290 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\TraceMethodVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */