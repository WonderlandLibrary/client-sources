/*     */ package org.spongepowered.asm.lib.commons;
/*     */ 
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
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
/*     */ public class MethodRemapper
/*     */   extends MethodVisitor
/*     */ {
/*     */   protected final Remapper remapper;
/*     */   
/*     */   public MethodRemapper(MethodVisitor mv, Remapper remapper) {
/*  50 */     this(327680, mv, remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MethodRemapper(int api, MethodVisitor mv, Remapper remapper) {
/*  55 */     super(api, mv);
/*  56 */     this.remapper = remapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/*  61 */     AnnotationVisitor av = super.visitAnnotationDefault();
/*  62 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  67 */     AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
/*     */     
/*  69 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  75 */     AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper
/*  76 */         .mapDesc(desc), visible);
/*  77 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  83 */     AnnotationVisitor av = super.visitParameterAnnotation(parameter, this.remapper
/*  84 */         .mapDesc(desc), visible);
/*  85 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  91 */     super.visitFrame(type, nLocal, remapEntries(nLocal, local), nStack, 
/*  92 */         remapEntries(nStack, stack));
/*     */   }
/*     */   
/*     */   private Object[] remapEntries(int n, Object[] entries) {
/*  96 */     for (int i = 0; i < n; i++) {
/*  97 */       if (entries[i] instanceof String) {
/*  98 */         Object[] newEntries = new Object[n];
/*  99 */         if (i > 0) {
/* 100 */           System.arraycopy(entries, 0, newEntries, 0, i);
/*     */         }
/*     */         while (true) {
/* 103 */           Object t = entries[i];
/* 104 */           newEntries[i++] = (t instanceof String) ? this.remapper
/* 105 */             .mapType((String)t) : t;
/* 106 */           if (i >= n)
/* 107 */             return newEntries; 
/*     */         } 
/*     */       } 
/* 110 */     }  return entries;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 116 */     super.visitFieldInsn(opcode, this.remapper.mapType(owner), this.remapper
/* 117 */         .mapFieldName(owner, name, desc), this.remapper
/* 118 */         .mapDesc(desc));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 125 */     if (this.api >= 327680) {
/* 126 */       super.visitMethodInsn(opcode, owner, name, desc);
/*     */       return;
/*     */     } 
/* 129 */     doVisitMethodInsn(opcode, owner, name, desc, (opcode == 185));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/* 136 */     if (this.api < 327680) {
/* 137 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */       return;
/*     */     } 
/* 140 */     doVisitMethodInsn(opcode, owner, name, desc, itf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/* 151 */     if (this.mv != null) {
/* 152 */       this.mv.visitMethodInsn(opcode, this.remapper.mapType(owner), this.remapper
/* 153 */           .mapMethodName(owner, name, desc), this.remapper
/* 154 */           .mapMethodDesc(desc), itf);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/* 161 */     for (int i = 0; i < bsmArgs.length; i++) {
/* 162 */       bsmArgs[i] = this.remapper.mapValue(bsmArgs[i]);
/*     */     }
/* 164 */     super.visitInvokeDynamicInsn(this.remapper
/* 165 */         .mapInvokeDynamicMethodName(name, desc), this.remapper
/* 166 */         .mapMethodDesc(desc), (Handle)this.remapper.mapValue(bsm), bsmArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 172 */     super.visitTypeInsn(opcode, this.remapper.mapType(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 177 */     super.visitLdcInsn(this.remapper.mapValue(cst));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 182 */     super.visitMultiANewArrayInsn(this.remapper.mapDesc(desc), dims);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 188 */     AnnotationVisitor av = super.visitInsnAnnotation(typeRef, typePath, this.remapper
/* 189 */         .mapDesc(desc), visible);
/* 190 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 196 */     super.visitTryCatchBlock(start, end, handler, (type == null) ? null : this.remapper
/* 197 */         .mapType(type));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 203 */     AnnotationVisitor av = super.visitTryCatchAnnotation(typeRef, typePath, this.remapper
/* 204 */         .mapDesc(desc), visible);
/* 205 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 211 */     super.visitLocalVariable(name, this.remapper.mapDesc(desc), this.remapper
/* 212 */         .mapSignature(signature, true), start, end, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 219 */     AnnotationVisitor av = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, this.remapper
/* 220 */         .mapDesc(desc), visible);
/* 221 */     return (av == null) ? av : new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\commons\MethodRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */