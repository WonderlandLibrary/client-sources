/*     */ package org.spongepowered.asm.lib.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Attribute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceClassVisitor
/*     */   extends ClassVisitor
/*     */ {
/*     */   private final PrintWriter pw;
/*     */   public final Printer p;
/*     */   
/*     */   public TraceClassVisitor(PrintWriter pw) {
/* 103 */     this(null, pw);
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
/*     */   public TraceClassVisitor(ClassVisitor cv, PrintWriter pw) {
/* 116 */     this(cv, new Textifier(), pw);
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
/*     */   public TraceClassVisitor(ClassVisitor cv, Printer p, PrintWriter pw) {
/* 134 */     super(327680, cv);
/* 135 */     this.pw = pw;
/* 136 */     this.p = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/* 143 */     this.p.visit(version, access, name, signature, superName, interfaces);
/* 144 */     super.visit(version, access, name, signature, superName, interfaces);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitSource(String file, String debug) {
/* 149 */     this.p.visitSource(file, debug);
/* 150 */     super.visitSource(file, debug);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String owner, String name, String desc) {
/* 156 */     this.p.visitOuterClass(owner, name, desc);
/* 157 */     super.visitOuterClass(owner, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 163 */     Printer p = this.p.visitClassAnnotation(desc, visible);
/* 164 */     AnnotationVisitor av = (this.cv == null) ? null : this.cv.visitAnnotation(desc, visible);
/*     */     
/* 166 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 172 */     Printer p = this.p.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     
/* 174 */     AnnotationVisitor av = (this.cv == null) ? null : this.cv.visitTypeAnnotation(typeRef, typePath, desc, visible);
/*     */     
/* 176 */     return new TraceAnnotationVisitor(av, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 181 */     this.p.visitClassAttribute(attr);
/* 182 */     super.visitAttribute(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 188 */     this.p.visitInnerClass(name, outerName, innerName, access);
/* 189 */     super.visitInnerClass(name, outerName, innerName, access);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 195 */     Printer p = this.p.visitField(access, name, desc, signature, value);
/* 196 */     FieldVisitor fv = (this.cv == null) ? null : this.cv.visitField(access, name, desc, signature, value);
/*     */     
/* 198 */     return new TraceFieldVisitor(fv, p);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 204 */     Printer p = this.p.visitMethod(access, name, desc, signature, exceptions);
/*     */     
/* 206 */     MethodVisitor mv = (this.cv == null) ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
/*     */     
/* 208 */     return new TraceMethodVisitor(mv, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 213 */     this.p.visitClassEnd();
/* 214 */     if (this.pw != null) {
/* 215 */       this.p.print(this.pw);
/* 216 */       this.pw.flush();
/*     */     } 
/* 218 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\TraceClassVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */