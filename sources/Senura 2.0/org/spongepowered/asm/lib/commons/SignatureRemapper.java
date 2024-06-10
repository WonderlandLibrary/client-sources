/*     */ package org.spongepowered.asm.lib.commons;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureRemapper
/*     */   extends SignatureVisitor
/*     */ {
/*     */   private final SignatureVisitor v;
/*     */   private final Remapper remapper;
/*  49 */   private Stack<String> classNames = new Stack<String>();
/*     */   
/*     */   public SignatureRemapper(SignatureVisitor v, Remapper remapper) {
/*  52 */     this(327680, v, remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SignatureRemapper(int api, SignatureVisitor v, Remapper remapper) {
/*  57 */     super(api);
/*  58 */     this.v = v;
/*  59 */     this.remapper = remapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String name) {
/*  64 */     this.classNames.push(name);
/*  65 */     this.v.visitClassType(this.remapper.mapType(name));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String name) {
/*  70 */     String outerClassName = this.classNames.pop();
/*  71 */     String className = outerClassName + '$' + name;
/*  72 */     this.classNames.push(className);
/*  73 */     String remappedOuter = this.remapper.mapType(outerClassName) + '$';
/*  74 */     String remappedName = this.remapper.mapType(className);
/*     */     
/*  76 */     int index = remappedName.startsWith(remappedOuter) ? remappedOuter.length() : (remappedName.lastIndexOf('$') + 1);
/*  77 */     this.v.visitInnerClassType(remappedName.substring(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String name) {
/*  82 */     this.v.visitFormalTypeParameter(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String name) {
/*  87 */     this.v.visitTypeVariable(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/*  92 */     this.v.visitArrayType();
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBaseType(char descriptor) {
/*  98 */     this.v.visitBaseType(descriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 103 */     this.v.visitClassBound();
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 109 */     this.v.visitExceptionType();
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 115 */     this.v.visitInterface();
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 121 */     this.v.visitInterfaceBound();
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 127 */     this.v.visitParameterType();
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 133 */     this.v.visitReturnType();
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 139 */     this.v.visitSuperclass();
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 145 */     this.v.visitTypeArgument();
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char wildcard) {
/* 150 */     this.v.visitTypeArgument(wildcard);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 156 */     this.v.visitEnd();
/* 157 */     this.classNames.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\commons\SignatureRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */