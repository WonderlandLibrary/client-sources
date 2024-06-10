/*     */ package org.spongepowered.asm.lib.commons;
/*     */ 
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.FieldVisitor;
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
/*     */ 
/*     */ public class ClassRemapper
/*     */   extends ClassVisitor
/*     */ {
/*     */   protected final Remapper remapper;
/*     */   protected String className;
/*     */   
/*     */   public ClassRemapper(ClassVisitor cv, Remapper remapper) {
/*  52 */     this(327680, cv, remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClassRemapper(int api, ClassVisitor cv, Remapper remapper) {
/*  57 */     super(api, cv);
/*  58 */     this.remapper = remapper;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  64 */     this.className = name;
/*  65 */     super.visit(version, access, this.remapper.mapType(name), this.remapper
/*  66 */         .mapSignature(signature, false), this.remapper.mapType(superName), (interfaces == null) ? null : this.remapper
/*  67 */         .mapTypes(interfaces));
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  72 */     AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
/*     */     
/*  74 */     return (av == null) ? null : createAnnotationRemapper(av);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  80 */     AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper
/*  81 */         .mapDesc(desc), visible);
/*  82 */     return (av == null) ? null : createAnnotationRemapper(av);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/*  88 */     FieldVisitor fv = super.visitField(access, this.remapper
/*  89 */         .mapFieldName(this.className, name, desc), this.remapper
/*  90 */         .mapDesc(desc), this.remapper.mapSignature(signature, true), this.remapper
/*  91 */         .mapValue(value));
/*  92 */     return (fv == null) ? null : createFieldRemapper(fv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  98 */     String newDesc = this.remapper.mapMethodDesc(desc);
/*  99 */     MethodVisitor mv = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, desc), newDesc, this.remapper
/* 100 */         .mapSignature(signature, false), (exceptions == null) ? null : this.remapper
/*     */         
/* 102 */         .mapTypes(exceptions));
/* 103 */     return (mv == null) ? null : createMethodRemapper(mv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 110 */     super.visitInnerClass(this.remapper.mapType(name), (outerName == null) ? null : this.remapper
/* 111 */         .mapType(outerName), innerName, access);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String owner, String name, String desc) {
/* 116 */     super.visitOuterClass(this.remapper.mapType(owner), (name == null) ? null : this.remapper
/* 117 */         .mapMethodName(owner, name, desc), (desc == null) ? null : this.remapper
/* 118 */         .mapMethodDesc(desc));
/*     */   }
/*     */   
/*     */   protected FieldVisitor createFieldRemapper(FieldVisitor fv) {
/* 122 */     return new FieldRemapper(fv, this.remapper);
/*     */   }
/*     */   
/*     */   protected MethodVisitor createMethodRemapper(MethodVisitor mv) {
/* 126 */     return new MethodRemapper(mv, this.remapper);
/*     */   }
/*     */   
/*     */   protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor av) {
/* 130 */     return new AnnotationRemapper(av, this.remapper);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\commons\ClassRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */