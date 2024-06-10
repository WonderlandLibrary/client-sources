/*      */ package org.spongepowered.asm.lib.util;
/*      */ 
/*      */ import java.io.FileInputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*      */ import org.spongepowered.asm.lib.Attribute;
/*      */ import org.spongepowered.asm.lib.ClassReader;
/*      */ import org.spongepowered.asm.lib.ClassVisitor;
/*      */ import org.spongepowered.asm.lib.FieldVisitor;
/*      */ import org.spongepowered.asm.lib.Label;
/*      */ import org.spongepowered.asm.lib.MethodVisitor;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.TypePath;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
/*      */ import org.spongepowered.asm.lib.tree.analysis.Analyzer;
/*      */ import org.spongepowered.asm.lib.tree.analysis.BasicValue;
/*      */ import org.spongepowered.asm.lib.tree.analysis.Frame;
/*      */ import org.spongepowered.asm.lib.tree.analysis.Interpreter;
/*      */ import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CheckClassAdapter
/*      */   extends ClassVisitor
/*      */ {
/*      */   private int version;
/*      */   private boolean start;
/*      */   private boolean source;
/*      */   private boolean outer;
/*      */   private boolean end;
/*      */   private Map<Label, Integer> labels;
/*      */   private boolean checkDataFlow;
/*      */   
/*      */   public static void main(String[] args) throws Exception {
/*      */     ClassReader cr;
/*  177 */     if (args.length != 1) {
/*  178 */       System.err.println("Verifies the given class.");
/*  179 */       System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  184 */     if (args[0].endsWith(".class")) {
/*  185 */       cr = new ClassReader(new FileInputStream(args[0]));
/*      */     } else {
/*  187 */       cr = new ClassReader(args[0]);
/*      */     } 
/*      */     
/*  190 */     verify(cr, false, new PrintWriter(System.err));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void verify(ClassReader cr, ClassLoader loader, boolean dump, PrintWriter pw) {
/*  211 */     ClassNode cn = new ClassNode();
/*  212 */     cr.accept(new CheckClassAdapter((ClassVisitor)cn, false), 2);
/*      */ 
/*      */     
/*  215 */     Type syperType = (cn.superName == null) ? null : Type.getObjectType(cn.superName);
/*  216 */     List<MethodNode> methods = cn.methods;
/*      */     
/*  218 */     List<Type> interfaces = new ArrayList<Type>();
/*  219 */     for (Iterator<String> iterator = cn.interfaces.iterator(); iterator.hasNext();) {
/*  220 */       interfaces.add(Type.getObjectType(iterator.next()));
/*      */     }
/*      */     
/*  223 */     for (int i = 0; i < methods.size(); i++) {
/*  224 */       MethodNode method = methods.get(i);
/*      */       
/*  226 */       SimpleVerifier verifier = new SimpleVerifier(Type.getObjectType(cn.name), syperType, interfaces, ((cn.access & 0x200) != 0));
/*      */       
/*  228 */       Analyzer<BasicValue> a = new Analyzer((Interpreter)verifier);
/*  229 */       if (loader != null) {
/*  230 */         verifier.setClassLoader(loader);
/*      */       }
/*      */       try {
/*  233 */         a.analyze(cn.name, method);
/*  234 */         if (!dump) {
/*      */           continue;
/*      */         }
/*  237 */       } catch (Exception e) {
/*  238 */         e.printStackTrace(pw);
/*      */       } 
/*  240 */       printAnalyzerResult(method, a, pw); continue;
/*      */     } 
/*  242 */     pw.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void verify(ClassReader cr, boolean dump, PrintWriter pw) {
/*  259 */     verify(cr, null, dump, pw);
/*      */   }
/*      */ 
/*      */   
/*      */   static void printAnalyzerResult(MethodNode method, Analyzer<BasicValue> a, PrintWriter pw) {
/*  264 */     Frame[] arrayOfFrame = a.getFrames();
/*  265 */     Textifier t = new Textifier();
/*  266 */     TraceMethodVisitor mv = new TraceMethodVisitor(t);
/*      */     
/*  268 */     pw.println(method.name + method.desc); int j;
/*  269 */     for (j = 0; j < method.instructions.size(); j++) {
/*  270 */       method.instructions.get(j).accept(mv);
/*      */       
/*  272 */       StringBuilder sb = new StringBuilder();
/*  273 */       Frame<BasicValue> f = arrayOfFrame[j];
/*  274 */       if (f == null) {
/*  275 */         sb.append('?');
/*      */       } else {
/*  277 */         int k; for (k = 0; k < f.getLocals(); k++) {
/*  278 */           sb.append(getShortName(((BasicValue)f.getLocal(k)).toString()))
/*  279 */             .append(' ');
/*      */         }
/*  281 */         sb.append(" : ");
/*  282 */         for (k = 0; k < f.getStackSize(); k++) {
/*  283 */           sb.append(getShortName(((BasicValue)f.getStack(k)).toString()))
/*  284 */             .append(' ');
/*      */         }
/*      */       } 
/*  287 */       while (sb.length() < method.maxStack + method.maxLocals + 1) {
/*  288 */         sb.append(' ');
/*      */       }
/*  290 */       pw.print(Integer.toString(j + 100000).substring(1));
/*  291 */       pw.print(" " + sb + " : " + t.text.get(t.text.size() - 1));
/*      */     } 
/*  293 */     for (j = 0; j < method.tryCatchBlocks.size(); j++) {
/*  294 */       ((TryCatchBlockNode)method.tryCatchBlocks.get(j)).accept(mv);
/*  295 */       pw.print(" " + t.text.get(t.text.size() - 1));
/*      */     } 
/*  297 */     pw.println();
/*      */   }
/*      */   
/*      */   private static String getShortName(String name) {
/*  301 */     int n = name.lastIndexOf('/');
/*  302 */     int k = name.length();
/*  303 */     if (name.charAt(k - 1) == ';') {
/*  304 */       k--;
/*      */     }
/*  306 */     return (n == -1) ? name : name.substring(n + 1, k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CheckClassAdapter(ClassVisitor cv) {
/*  318 */     this(cv, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CheckClassAdapter(ClassVisitor cv, boolean checkDataFlow) {
/*  337 */     this(327680, cv, checkDataFlow);
/*  338 */     if (getClass() != CheckClassAdapter.class) {
/*  339 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CheckClassAdapter(int api, ClassVisitor cv, boolean checkDataFlow) {
/*  359 */     super(api, cv);
/*  360 */     this.labels = new HashMap<Label, Integer>();
/*  361 */     this.checkDataFlow = checkDataFlow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  372 */     if (this.start) {
/*  373 */       throw new IllegalStateException("visit must be called only once");
/*      */     }
/*  375 */     this.start = true;
/*  376 */     checkState();
/*  377 */     checkAccess(access, 423473);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  382 */     if (name == null || !name.endsWith("package-info")) {
/*  383 */       CheckMethodAdapter.checkInternalName(name, "class name");
/*      */     }
/*  385 */     if ("java/lang/Object".equals(name)) {
/*  386 */       if (superName != null) {
/*  387 */         throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
/*      */       }
/*      */     } else {
/*      */       
/*  391 */       CheckMethodAdapter.checkInternalName(superName, "super class name");
/*      */     } 
/*  393 */     if (signature != null) {
/*  394 */       checkClassSignature(signature);
/*      */     }
/*  396 */     if ((access & 0x200) != 0 && 
/*  397 */       !"java/lang/Object".equals(superName)) {
/*  398 */       throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
/*      */     }
/*      */ 
/*      */     
/*  402 */     if (interfaces != null) {
/*  403 */       for (int i = 0; i < interfaces.length; i++) {
/*  404 */         CheckMethodAdapter.checkInternalName(interfaces[i], "interface name at index " + i);
/*      */       }
/*      */     }
/*      */     
/*  408 */     this.version = version;
/*  409 */     super.visit(version, access, name, signature, superName, interfaces);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String file, String debug) {
/*  414 */     checkState();
/*  415 */     if (this.source) {
/*  416 */       throw new IllegalStateException("visitSource can be called only once.");
/*      */     }
/*      */     
/*  419 */     this.source = true;
/*  420 */     super.visitSource(file, debug);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String owner, String name, String desc) {
/*  426 */     checkState();
/*  427 */     if (this.outer) {
/*  428 */       throw new IllegalStateException("visitOuterClass can be called only once.");
/*      */     }
/*      */     
/*  431 */     this.outer = true;
/*  432 */     if (owner == null) {
/*  433 */       throw new IllegalArgumentException("Illegal outer class owner");
/*      */     }
/*  435 */     if (desc != null) {
/*  436 */       CheckMethodAdapter.checkMethodDesc(desc);
/*      */     }
/*  438 */     super.visitOuterClass(owner, name, desc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  444 */     checkState();
/*  445 */     CheckMethodAdapter.checkInternalName(name, "class name");
/*  446 */     if (outerName != null) {
/*  447 */       CheckMethodAdapter.checkInternalName(outerName, "outer class name");
/*      */     }
/*  449 */     if (innerName != null) {
/*  450 */       int start = 0;
/*  451 */       while (start < innerName.length() && 
/*  452 */         Character.isDigit(innerName.charAt(start))) {
/*  453 */         start++;
/*      */       }
/*  455 */       if (start == 0 || start < innerName.length()) {
/*  456 */         CheckMethodAdapter.checkIdentifier(innerName, start, -1, "inner class name");
/*      */       }
/*      */     } 
/*      */     
/*  460 */     checkAccess(access, 30239);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  465 */     super.visitInnerClass(name, outerName, innerName, access);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/*  471 */     checkState();
/*  472 */     checkAccess(access, 413919);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  477 */     CheckMethodAdapter.checkUnqualifiedName(this.version, name, "field name");
/*  478 */     CheckMethodAdapter.checkDesc(desc, false);
/*  479 */     if (signature != null) {
/*  480 */       checkFieldSignature(signature);
/*      */     }
/*  482 */     if (value != null) {
/*  483 */       CheckMethodAdapter.checkConstant(value);
/*      */     }
/*      */     
/*  486 */     FieldVisitor av = super.visitField(access, name, desc, signature, value);
/*  487 */     return new CheckFieldAdapter(av);
/*      */   }
/*      */ 
/*      */   
/*      */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*      */     CheckMethodAdapter cma;
/*  493 */     checkState();
/*  494 */     checkAccess(access, 400895);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  500 */     if (!"<init>".equals(name) && !"<clinit>".equals(name)) {
/*  501 */       CheckMethodAdapter.checkMethodIdentifier(this.version, name, "method name");
/*      */     }
/*      */     
/*  504 */     CheckMethodAdapter.checkMethodDesc(desc);
/*  505 */     if (signature != null) {
/*  506 */       checkMethodSignature(signature);
/*      */     }
/*  508 */     if (exceptions != null) {
/*  509 */       for (int i = 0; i < exceptions.length; i++) {
/*  510 */         CheckMethodAdapter.checkInternalName(exceptions[i], "exception name at index " + i);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  515 */     if (this.checkDataFlow) {
/*  516 */       cma = new CheckMethodAdapter(access, name, desc, super.visitMethod(access, name, desc, signature, exceptions), this.labels);
/*      */     } else {
/*      */       
/*  519 */       cma = new CheckMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions), this.labels);
/*      */     } 
/*      */     
/*  522 */     cma.version = this.version;
/*  523 */     return cma;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  529 */     checkState();
/*  530 */     CheckMethodAdapter.checkDesc(desc, false);
/*  531 */     return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  537 */     checkState();
/*  538 */     int sort = typeRef >>> 24;
/*  539 */     if (sort != 0 && sort != 17 && sort != 16)
/*      */     {
/*      */       
/*  542 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  543 */           Integer.toHexString(sort));
/*      */     }
/*  545 */     checkTypeRefAndPath(typeRef, typePath);
/*  546 */     CheckMethodAdapter.checkDesc(desc, false);
/*  547 */     return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  553 */     checkState();
/*  554 */     if (attr == null) {
/*  555 */       throw new IllegalArgumentException("Invalid attribute (must not be null)");
/*      */     }
/*      */     
/*  558 */     super.visitAttribute(attr);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitEnd() {
/*  563 */     checkState();
/*  564 */     this.end = true;
/*  565 */     super.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkState() {
/*  577 */     if (!this.start) {
/*  578 */       throw new IllegalStateException("Cannot visit member before visit has been called.");
/*      */     }
/*      */     
/*  581 */     if (this.end) {
/*  582 */       throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkAccess(int access, int possibleAccess) {
/*  598 */     if ((access & (possibleAccess ^ 0xFFFFFFFF)) != 0) {
/*  599 */       throw new IllegalArgumentException("Invalid access flags: " + access);
/*      */     }
/*      */     
/*  602 */     int pub = ((access & 0x1) == 0) ? 0 : 1;
/*  603 */     int pri = ((access & 0x2) == 0) ? 0 : 1;
/*  604 */     int pro = ((access & 0x4) == 0) ? 0 : 1;
/*  605 */     if (pub + pri + pro > 1) {
/*  606 */       throw new IllegalArgumentException("public private and protected are mutually exclusive: " + access);
/*      */     }
/*      */ 
/*      */     
/*  610 */     int fin = ((access & 0x10) == 0) ? 0 : 1;
/*  611 */     int abs = ((access & 0x400) == 0) ? 0 : 1;
/*  612 */     if (fin + abs > 1) {
/*  613 */       throw new IllegalArgumentException("final and abstract are mutually exclusive: " + access);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkClassSignature(String signature) {
/*  628 */     int pos = 0;
/*  629 */     if (getChar(signature, 0) == '<') {
/*  630 */       pos = checkFormalTypeParameters(signature, pos);
/*      */     }
/*  632 */     pos = checkClassTypeSignature(signature, pos);
/*  633 */     while (getChar(signature, pos) == 'L') {
/*  634 */       pos = checkClassTypeSignature(signature, pos);
/*      */     }
/*  636 */     if (pos != signature.length()) {
/*  637 */       throw new IllegalArgumentException(signature + ": error at index " + pos);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkMethodSignature(String signature) {
/*  653 */     int pos = 0;
/*  654 */     if (getChar(signature, 0) == '<') {
/*  655 */       pos = checkFormalTypeParameters(signature, pos);
/*      */     }
/*  657 */     pos = checkChar('(', signature, pos);
/*  658 */     while ("ZCBSIFJDL[T".indexOf(getChar(signature, pos)) != -1) {
/*  659 */       pos = checkTypeSignature(signature, pos);
/*      */     }
/*  661 */     pos = checkChar(')', signature, pos);
/*  662 */     if (getChar(signature, pos) == 'V') {
/*  663 */       pos++;
/*      */     } else {
/*  665 */       pos = checkTypeSignature(signature, pos);
/*      */     } 
/*  667 */     while (getChar(signature, pos) == '^') {
/*  668 */       pos++;
/*  669 */       if (getChar(signature, pos) == 'L') {
/*  670 */         pos = checkClassTypeSignature(signature, pos); continue;
/*      */       } 
/*  672 */       pos = checkTypeVariableSignature(signature, pos);
/*      */     } 
/*      */     
/*  675 */     if (pos != signature.length()) {
/*  676 */       throw new IllegalArgumentException(signature + ": error at index " + pos);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkFieldSignature(String signature) {
/*  688 */     int pos = checkFieldTypeSignature(signature, 0);
/*  689 */     if (pos != signature.length()) {
/*  690 */       throw new IllegalArgumentException(signature + ": error at index " + pos);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkTypeRefAndPath(int typeRef, TypePath typePath) {
/*  706 */     int mask = 0;
/*  707 */     switch (typeRef >>> 24) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 22:
/*  711 */         mask = -65536;
/*      */         break;
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 64:
/*      */       case 65:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*  722 */         mask = -16777216;
/*      */         break;
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 23:
/*      */       case 66:
/*  729 */         mask = -256;
/*      */         break;
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*  736 */         mask = -16776961;
/*      */         break;
/*      */       default:
/*  739 */         throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  740 */             Integer.toHexString(typeRef >>> 24));
/*      */     } 
/*  742 */     if ((typeRef & (mask ^ 0xFFFFFFFF)) != 0) {
/*  743 */       throw new IllegalArgumentException("Invalid type reference 0x" + 
/*  744 */           Integer.toHexString(typeRef));
/*      */     }
/*  746 */     if (typePath != null) {
/*  747 */       for (int i = 0; i < typePath.getLength(); i++) {
/*  748 */         int step = typePath.getStep(i);
/*  749 */         if (step != 0 && step != 1 && step != 3 && step != 2)
/*      */         {
/*      */ 
/*      */           
/*  753 */           throw new IllegalArgumentException("Invalid type path step " + i + " in " + typePath);
/*      */         }
/*      */         
/*  756 */         if (step != 3 && typePath
/*  757 */           .getStepArgument(i) != 0) {
/*  758 */           throw new IllegalArgumentException("Invalid type path step argument for step " + i + " in " + typePath);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkFormalTypeParameters(String signature, int pos) {
/*  779 */     pos = checkChar('<', signature, pos);
/*  780 */     pos = checkFormalTypeParameter(signature, pos);
/*  781 */     while (getChar(signature, pos) != '>') {
/*  782 */       pos = checkFormalTypeParameter(signature, pos);
/*      */     }
/*  784 */     return pos + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkFormalTypeParameter(String signature, int pos) {
/*  800 */     pos = checkIdentifier(signature, pos);
/*  801 */     pos = checkChar(':', signature, pos);
/*  802 */     if ("L[T".indexOf(getChar(signature, pos)) != -1) {
/*  803 */       pos = checkFieldTypeSignature(signature, pos);
/*      */     }
/*  805 */     while (getChar(signature, pos) == ':') {
/*  806 */       pos = checkFieldTypeSignature(signature, pos + 1);
/*      */     }
/*  808 */     return pos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkFieldTypeSignature(String signature, int pos) {
/*  827 */     switch (getChar(signature, pos)) {
/*      */       case 'L':
/*  829 */         return checkClassTypeSignature(signature, pos);
/*      */       case '[':
/*  831 */         return checkTypeSignature(signature, pos + 1);
/*      */     } 
/*  833 */     return checkTypeVariableSignature(signature, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkClassTypeSignature(String signature, int pos) {
/*  851 */     pos = checkChar('L', signature, pos);
/*  852 */     pos = checkIdentifier(signature, pos);
/*  853 */     while (getChar(signature, pos) == '/') {
/*  854 */       pos = checkIdentifier(signature, pos + 1);
/*      */     }
/*  856 */     if (getChar(signature, pos) == '<') {
/*  857 */       pos = checkTypeArguments(signature, pos);
/*      */     }
/*  859 */     while (getChar(signature, pos) == '.') {
/*  860 */       pos = checkIdentifier(signature, pos + 1);
/*  861 */       if (getChar(signature, pos) == '<') {
/*  862 */         pos = checkTypeArguments(signature, pos);
/*      */       }
/*      */     } 
/*  865 */     return checkChar(';', signature, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkTypeArguments(String signature, int pos) {
/*  881 */     pos = checkChar('<', signature, pos);
/*  882 */     pos = checkTypeArgument(signature, pos);
/*  883 */     while (getChar(signature, pos) != '>') {
/*  884 */       pos = checkTypeArgument(signature, pos);
/*      */     }
/*  886 */     return pos + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkTypeArgument(String signature, int pos) {
/*  902 */     char c = getChar(signature, pos);
/*  903 */     if (c == '*')
/*  904 */       return pos + 1; 
/*  905 */     if (c == '+' || c == '-') {
/*  906 */       pos++;
/*      */     }
/*  908 */     return checkFieldTypeSignature(signature, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkTypeVariableSignature(String signature, int pos) {
/*  925 */     pos = checkChar('T', signature, pos);
/*  926 */     pos = checkIdentifier(signature, pos);
/*  927 */     return checkChar(';', signature, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkTypeSignature(String signature, int pos) {
/*  943 */     switch (getChar(signature, pos)) {
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'D':
/*      */       case 'F':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'S':
/*      */       case 'Z':
/*  952 */         return pos + 1;
/*      */     } 
/*  954 */     return checkFieldTypeSignature(signature, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkIdentifier(String signature, int pos) {
/*  968 */     if (!Character.isJavaIdentifierStart(getChar(signature, pos))) {
/*  969 */       throw new IllegalArgumentException(signature + ": identifier expected at index " + pos);
/*      */     }
/*      */     
/*  972 */     pos++;
/*  973 */     while (Character.isJavaIdentifierPart(getChar(signature, pos))) {
/*  974 */       pos++;
/*      */     }
/*  976 */     return pos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkChar(char c, String signature, int pos) {
/*  989 */     if (getChar(signature, pos) == c) {
/*  990 */       return pos + 1;
/*      */     }
/*  992 */     throw new IllegalArgumentException(signature + ": '" + c + "' expected at index " + pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static char getChar(String signature, int pos) {
/* 1007 */     return (pos < signature.length()) ? signature.charAt(pos) : Character.MIN_VALUE;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\CheckClassAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */