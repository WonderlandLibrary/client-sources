/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassNode
/*     */   extends ClassVisitor
/*     */ {
/*     */   public int version;
/*     */   public int access;
/*     */   public String name;
/*     */   public String signature;
/*     */   public String superName;
/*     */   public List<String> interfaces;
/*     */   public String sourceFile;
/*     */   public String sourceDebug;
/*     */   public String outerClass;
/*     */   public String outerMethod;
/*     */   public String outerMethodDesc;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   public List<InnerClassNode> innerClasses;
/*     */   public List<FieldNode> fields;
/*     */   public List<MethodNode> methods;
/*     */   
/*     */   public ClassNode() {
/* 195 */     this(327680);
/* 196 */     if (getClass() != ClassNode.class) {
/* 197 */       throw new IllegalStateException();
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
/*     */   public ClassNode(int api) {
/* 209 */     super(api);
/* 210 */     this.interfaces = new ArrayList<String>();
/* 211 */     this.innerClasses = new ArrayList<InnerClassNode>();
/* 212 */     this.fields = new ArrayList<FieldNode>();
/* 213 */     this.methods = new ArrayList<MethodNode>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/* 224 */     this.version = version;
/* 225 */     this.access = access;
/* 226 */     this.name = name;
/* 227 */     this.signature = signature;
/* 228 */     this.superName = superName;
/* 229 */     if (interfaces != null) {
/* 230 */       this.interfaces.addAll(Arrays.asList(interfaces));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitSource(String file, String debug) {
/* 236 */     this.sourceFile = file;
/* 237 */     this.sourceDebug = debug;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String owner, String name, String desc) {
/* 243 */     this.outerClass = owner;
/* 244 */     this.outerMethod = name;
/* 245 */     this.outerMethodDesc = desc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 251 */     AnnotationNode an = new AnnotationNode(desc);
/* 252 */     if (visible) {
/* 253 */       if (this.visibleAnnotations == null) {
/* 254 */         this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 256 */       this.visibleAnnotations.add(an);
/*     */     } else {
/* 258 */       if (this.invisibleAnnotations == null) {
/* 259 */         this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 261 */       this.invisibleAnnotations.add(an);
/*     */     } 
/* 263 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 269 */     TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
/* 270 */     if (visible) {
/* 271 */       if (this.visibleTypeAnnotations == null) {
/* 272 */         this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 274 */       this.visibleTypeAnnotations.add(an);
/*     */     } else {
/* 276 */       if (this.invisibleTypeAnnotations == null) {
/* 277 */         this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 279 */       this.invisibleTypeAnnotations.add(an);
/*     */     } 
/* 281 */     return an;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 286 */     if (this.attrs == null) {
/* 287 */       this.attrs = new ArrayList<Attribute>(1);
/*     */     }
/* 289 */     this.attrs.add(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 295 */     InnerClassNode icn = new InnerClassNode(name, outerName, innerName, access);
/*     */     
/* 297 */     this.innerClasses.add(icn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/* 303 */     FieldNode fn = new FieldNode(access, name, desc, signature, value);
/* 304 */     this.fields.add(fn);
/* 305 */     return fn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/* 311 */     MethodNode mn = new MethodNode(access, name, desc, signature, exceptions);
/*     */     
/* 313 */     this.methods.add(mn);
/* 314 */     return mn;
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
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(int api) {
/* 336 */     if (api == 262144) {
/* 337 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 338 */         .size() > 0) {
/* 339 */         throw new RuntimeException();
/*     */       }
/* 341 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 342 */         .size() > 0) {
/* 343 */         throw new RuntimeException();
/*     */       }
/* 345 */       for (FieldNode f : this.fields) {
/* 346 */         f.check(api);
/*     */       }
/* 348 */       for (MethodNode m : this.methods) {
/* 349 */         m.check(api);
/*     */       }
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
/*     */   public void accept(ClassVisitor cv) {
/* 362 */     String[] interfaces = new String[this.interfaces.size()];
/* 363 */     this.interfaces.toArray(interfaces);
/* 364 */     cv.visit(this.version, this.access, this.name, this.signature, this.superName, interfaces);
/*     */     
/* 366 */     if (this.sourceFile != null || this.sourceDebug != null) {
/* 367 */       cv.visitSource(this.sourceFile, this.sourceDebug);
/*     */     }
/*     */     
/* 370 */     if (this.outerClass != null) {
/* 371 */       cv.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
/*     */     }
/*     */ 
/*     */     
/* 375 */     int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(); int i;
/* 376 */     for (i = 0; i < n; i++) {
/* 377 */       AnnotationNode an = this.visibleAnnotations.get(i);
/* 378 */       an.accept(cv.visitAnnotation(an.desc, true));
/*     */     } 
/* 380 */     n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 381 */     for (i = 0; i < n; i++) {
/* 382 */       AnnotationNode an = this.invisibleAnnotations.get(i);
/* 383 */       an.accept(cv.visitAnnotation(an.desc, false));
/*     */     } 
/* 385 */     n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 386 */     for (i = 0; i < n; i++) {
/* 387 */       TypeAnnotationNode an = this.visibleTypeAnnotations.get(i);
/* 388 */       an.accept(cv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 392 */     n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 393 */     for (i = 0; i < n; i++) {
/* 394 */       TypeAnnotationNode an = this.invisibleTypeAnnotations.get(i);
/* 395 */       an.accept(cv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, false));
/*     */     } 
/*     */     
/* 398 */     n = (this.attrs == null) ? 0 : this.attrs.size();
/* 399 */     for (i = 0; i < n; i++) {
/* 400 */       cv.visitAttribute(this.attrs.get(i));
/*     */     }
/*     */     
/* 403 */     for (i = 0; i < this.innerClasses.size(); i++) {
/* 404 */       ((InnerClassNode)this.innerClasses.get(i)).accept(cv);
/*     */     }
/*     */     
/* 407 */     for (i = 0; i < this.fields.size(); i++) {
/* 408 */       ((FieldNode)this.fields.get(i)).accept(cv);
/*     */     }
/*     */     
/* 411 */     for (i = 0; i < this.methods.size(); i++) {
/* 412 */       ((MethodNode)this.methods.get(i)).accept(cv);
/*     */     }
/*     */     
/* 415 */     cv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\ClassNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */