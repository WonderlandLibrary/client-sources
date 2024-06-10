/*      */ package org.spongepowered.asm.lib.util;
/*      */ 
/*      */ import java.io.FileInputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.spongepowered.asm.lib.Attribute;
/*      */ import org.spongepowered.asm.lib.ClassReader;
/*      */ import org.spongepowered.asm.lib.Handle;
/*      */ import org.spongepowered.asm.lib.Label;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.TypePath;
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
/*      */ public class ASMifier
/*      */   extends Printer
/*      */ {
/*      */   protected final String name;
/*      */   protected final int id;
/*      */   protected Map<Label, String> labelNames;
/*      */   private static final int ACCESS_CLASS = 262144;
/*      */   private static final int ACCESS_FIELD = 524288;
/*      */   private static final int ACCESS_INNER = 1048576;
/*      */   
/*      */   public ASMifier() {
/*   92 */     this(327680, "cw", 0);
/*   93 */     if (getClass() != ASMifier.class) {
/*   94 */       throw new IllegalStateException();
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
/*      */   protected ASMifier(int api, String name, int id) {
/*  111 */     super(api);
/*  112 */     this.name = name;
/*  113 */     this.id = id;
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
/*      */   public static void main(String[] args) throws Exception {
/*      */     ClassReader cr;
/*  129 */     int i = 0;
/*  130 */     int flags = 2;
/*      */     
/*  132 */     boolean ok = true;
/*  133 */     if (args.length < 1 || args.length > 2) {
/*  134 */       ok = false;
/*      */     }
/*  136 */     if (ok && "-debug".equals(args[0])) {
/*  137 */       i = 1;
/*  138 */       flags = 0;
/*  139 */       if (args.length != 2) {
/*  140 */         ok = false;
/*      */       }
/*      */     } 
/*  143 */     if (!ok) {
/*  144 */       System.err
/*  145 */         .println("Prints the ASM code to generate the given class.");
/*  146 */       System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  151 */     if (args[i].endsWith(".class") || args[i].indexOf('\\') > -1 || args[i]
/*  152 */       .indexOf('/') > -1) {
/*  153 */       cr = new ClassReader(new FileInputStream(args[i]));
/*      */     } else {
/*  155 */       cr = new ClassReader(args[i]);
/*      */     } 
/*  157 */     cr.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), flags);
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
/*      */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*      */     String simpleName;
/*  170 */     int n = name.lastIndexOf('/');
/*  171 */     if (n == -1) {
/*  172 */       simpleName = name;
/*      */     } else {
/*  174 */       this.text.add("package asm." + name.substring(0, n).replace('/', '.') + ";\n");
/*      */       
/*  176 */       simpleName = name.substring(n + 1);
/*      */     } 
/*  178 */     this.text.add("import java.util.*;\n");
/*  179 */     this.text.add("import org.objectweb.asm.*;\n");
/*  180 */     this.text.add("public class " + simpleName + "Dump implements Opcodes {\n\n");
/*  181 */     this.text.add("public static byte[] dump () throws Exception {\n\n");
/*  182 */     this.text.add("ClassWriter cw = new ClassWriter(0);\n");
/*  183 */     this.text.add("FieldVisitor fv;\n");
/*  184 */     this.text.add("MethodVisitor mv;\n");
/*  185 */     this.text.add("AnnotationVisitor av0;\n\n");
/*      */     
/*  187 */     this.buf.setLength(0);
/*  188 */     this.buf.append("cw.visit(");
/*  189 */     switch (version) {
/*      */       case 196653:
/*  191 */         this.buf.append("V1_1");
/*      */         break;
/*      */       case 46:
/*  194 */         this.buf.append("V1_2");
/*      */         break;
/*      */       case 47:
/*  197 */         this.buf.append("V1_3");
/*      */         break;
/*      */       case 48:
/*  200 */         this.buf.append("V1_4");
/*      */         break;
/*      */       case 49:
/*  203 */         this.buf.append("V1_5");
/*      */         break;
/*      */       case 50:
/*  206 */         this.buf.append("V1_6");
/*      */         break;
/*      */       case 51:
/*  209 */         this.buf.append("V1_7");
/*      */         break;
/*      */       default:
/*  212 */         this.buf.append(version);
/*      */         break;
/*      */     } 
/*  215 */     this.buf.append(", ");
/*  216 */     appendAccess(access | 0x40000);
/*  217 */     this.buf.append(", ");
/*  218 */     appendConstant(name);
/*  219 */     this.buf.append(", ");
/*  220 */     appendConstant(signature);
/*  221 */     this.buf.append(", ");
/*  222 */     appendConstant(superName);
/*  223 */     this.buf.append(", ");
/*  224 */     if (interfaces != null && interfaces.length > 0) {
/*  225 */       this.buf.append("new String[] {");
/*  226 */       for (int i = 0; i < interfaces.length; i++) {
/*  227 */         this.buf.append((i == 0) ? " " : ", ");
/*  228 */         appendConstant(interfaces[i]);
/*      */       } 
/*  230 */       this.buf.append(" }");
/*      */     } else {
/*  232 */       this.buf.append("null");
/*      */     } 
/*  234 */     this.buf.append(");\n\n");
/*  235 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String file, String debug) {
/*  240 */     this.buf.setLength(0);
/*  241 */     this.buf.append("cw.visitSource(");
/*  242 */     appendConstant(file);
/*  243 */     this.buf.append(", ");
/*  244 */     appendConstant(debug);
/*  245 */     this.buf.append(");\n\n");
/*  246 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String owner, String name, String desc) {
/*  252 */     this.buf.setLength(0);
/*  253 */     this.buf.append("cw.visitOuterClass(");
/*  254 */     appendConstant(owner);
/*  255 */     this.buf.append(", ");
/*  256 */     appendConstant(name);
/*  257 */     this.buf.append(", ");
/*  258 */     appendConstant(desc);
/*  259 */     this.buf.append(");\n\n");
/*  260 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitClassAnnotation(String desc, boolean visible) {
/*  266 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  272 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassAttribute(Attribute attr) {
/*  277 */     visitAttribute(attr);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  283 */     this.buf.setLength(0);
/*  284 */     this.buf.append("cw.visitInnerClass(");
/*  285 */     appendConstant(name);
/*  286 */     this.buf.append(", ");
/*  287 */     appendConstant(outerName);
/*  288 */     this.buf.append(", ");
/*  289 */     appendConstant(innerName);
/*  290 */     this.buf.append(", ");
/*  291 */     appendAccess(access | 0x100000);
/*  292 */     this.buf.append(");\n\n");
/*  293 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitField(int access, String name, String desc, String signature, Object value) {
/*  299 */     this.buf.setLength(0);
/*  300 */     this.buf.append("{\n");
/*  301 */     this.buf.append("fv = cw.visitField(");
/*  302 */     appendAccess(access | 0x80000);
/*  303 */     this.buf.append(", ");
/*  304 */     appendConstant(name);
/*  305 */     this.buf.append(", ");
/*  306 */     appendConstant(desc);
/*  307 */     this.buf.append(", ");
/*  308 */     appendConstant(signature);
/*  309 */     this.buf.append(", ");
/*  310 */     appendConstant(value);
/*  311 */     this.buf.append(");\n");
/*  312 */     this.text.add(this.buf.toString());
/*  313 */     ASMifier a = createASMifier("fv", 0);
/*  314 */     this.text.add(a.getText());
/*  315 */     this.text.add("}\n");
/*  316 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  322 */     this.buf.setLength(0);
/*  323 */     this.buf.append("{\n");
/*  324 */     this.buf.append("mv = cw.visitMethod(");
/*  325 */     appendAccess(access);
/*  326 */     this.buf.append(", ");
/*  327 */     appendConstant(name);
/*  328 */     this.buf.append(", ");
/*  329 */     appendConstant(desc);
/*  330 */     this.buf.append(", ");
/*  331 */     appendConstant(signature);
/*  332 */     this.buf.append(", ");
/*  333 */     if (exceptions != null && exceptions.length > 0) {
/*  334 */       this.buf.append("new String[] {");
/*  335 */       for (int i = 0; i < exceptions.length; i++) {
/*  336 */         this.buf.append((i == 0) ? " " : ", ");
/*  337 */         appendConstant(exceptions[i]);
/*      */       } 
/*  339 */       this.buf.append(" }");
/*      */     } else {
/*  341 */       this.buf.append("null");
/*      */     } 
/*  343 */     this.buf.append(");\n");
/*  344 */     this.text.add(this.buf.toString());
/*  345 */     ASMifier a = createASMifier("mv", 0);
/*  346 */     this.text.add(a.getText());
/*  347 */     this.text.add("}\n");
/*  348 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassEnd() {
/*  353 */     this.text.add("cw.visitEnd();\n\n");
/*  354 */     this.text.add("return cw.toByteArray();\n");
/*  355 */     this.text.add("}\n");
/*  356 */     this.text.add("}\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(String name, Object value) {
/*  365 */     this.buf.setLength(0);
/*  366 */     this.buf.append("av").append(this.id).append(".visit(");
/*  367 */     appendConstant(this.buf, name);
/*  368 */     this.buf.append(", ");
/*  369 */     appendConstant(this.buf, value);
/*  370 */     this.buf.append(");\n");
/*  371 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitEnum(String name, String desc, String value) {
/*  377 */     this.buf.setLength(0);
/*  378 */     this.buf.append("av").append(this.id).append(".visitEnum(");
/*  379 */     appendConstant(this.buf, name);
/*  380 */     this.buf.append(", ");
/*  381 */     appendConstant(this.buf, desc);
/*  382 */     this.buf.append(", ");
/*  383 */     appendConstant(this.buf, value);
/*  384 */     this.buf.append(");\n");
/*  385 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotation(String name, String desc) {
/*  390 */     this.buf.setLength(0);
/*  391 */     this.buf.append("{\n");
/*  392 */     this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
/*  393 */     this.buf.append(this.id).append(".visitAnnotation(");
/*  394 */     appendConstant(this.buf, name);
/*  395 */     this.buf.append(", ");
/*  396 */     appendConstant(this.buf, desc);
/*  397 */     this.buf.append(");\n");
/*  398 */     this.text.add(this.buf.toString());
/*  399 */     ASMifier a = createASMifier("av", this.id + 1);
/*  400 */     this.text.add(a.getText());
/*  401 */     this.text.add("}\n");
/*  402 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitArray(String name) {
/*  407 */     this.buf.setLength(0);
/*  408 */     this.buf.append("{\n");
/*  409 */     this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
/*  410 */     this.buf.append(this.id).append(".visitArray(");
/*  411 */     appendConstant(this.buf, name);
/*  412 */     this.buf.append(");\n");
/*  413 */     this.text.add(this.buf.toString());
/*  414 */     ASMifier a = createASMifier("av", this.id + 1);
/*  415 */     this.text.add(a.getText());
/*  416 */     this.text.add("}\n");
/*  417 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitAnnotationEnd() {
/*  422 */     this.buf.setLength(0);
/*  423 */     this.buf.append("av").append(this.id).append(".visitEnd();\n");
/*  424 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitFieldAnnotation(String desc, boolean visible) {
/*  434 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  440 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldAttribute(Attribute attr) {
/*  445 */     visitAttribute(attr);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldEnd() {
/*  450 */     this.buf.setLength(0);
/*  451 */     this.buf.append(this.name).append(".visitEnd();\n");
/*  452 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String parameterName, int access) {
/*  461 */     this.buf.setLength(0);
/*  462 */     this.buf.append(this.name).append(".visitParameter(");
/*  463 */     appendString(this.buf, parameterName);
/*  464 */     this.buf.append(", ");
/*  465 */     appendAccess(access);
/*  466 */     this.text.add(this.buf.append(");\n").toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotationDefault() {
/*  471 */     this.buf.setLength(0);
/*  472 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  473 */       .append(".visitAnnotationDefault();\n");
/*  474 */     this.text.add(this.buf.toString());
/*  475 */     ASMifier a = createASMifier("av", 0);
/*  476 */     this.text.add(a.getText());
/*  477 */     this.text.add("}\n");
/*  478 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethodAnnotation(String desc, boolean visible) {
/*  484 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  490 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  496 */     this.buf.setLength(0);
/*  497 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  498 */       .append(".visitParameterAnnotation(").append(parameter)
/*  499 */       .append(", ");
/*  500 */     appendConstant(desc);
/*  501 */     this.buf.append(", ").append(visible).append(");\n");
/*  502 */     this.text.add(this.buf.toString());
/*  503 */     ASMifier a = createASMifier("av", 0);
/*  504 */     this.text.add(a.getText());
/*  505 */     this.text.add("}\n");
/*  506 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodAttribute(Attribute attr) {
/*  511 */     visitAttribute(attr);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitCode() {
/*  516 */     this.text.add(this.name + ".visitCode();\n");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  522 */     this.buf.setLength(0);
/*  523 */     switch (type) {
/*      */       case -1:
/*      */       case 0:
/*  526 */         declareFrameTypes(nLocal, local);
/*  527 */         declareFrameTypes(nStack, stack);
/*  528 */         if (type == -1) {
/*  529 */           this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
/*      */         } else {
/*  531 */           this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
/*      */         } 
/*  533 */         this.buf.append(nLocal).append(", new Object[] {");
/*  534 */         appendFrameTypes(nLocal, local);
/*  535 */         this.buf.append("}, ").append(nStack).append(", new Object[] {");
/*  536 */         appendFrameTypes(nStack, stack);
/*  537 */         this.buf.append('}');
/*      */         break;
/*      */       case 1:
/*  540 */         declareFrameTypes(nLocal, local);
/*  541 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,")
/*  542 */           .append(nLocal).append(", new Object[] {");
/*  543 */         appendFrameTypes(nLocal, local);
/*  544 */         this.buf.append("}, 0, null");
/*      */         break;
/*      */       case 2:
/*  547 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,")
/*  548 */           .append(nLocal).append(", null, 0, null");
/*      */         break;
/*      */       case 3:
/*  551 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
/*      */         break;
/*      */       
/*      */       case 4:
/*  555 */         declareFrameTypes(1, stack);
/*  556 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
/*      */         
/*  558 */         appendFrameTypes(1, stack);
/*  559 */         this.buf.append('}');
/*      */         break;
/*      */     } 
/*  562 */     this.buf.append(");\n");
/*  563 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int opcode) {
/*  568 */     this.buf.setLength(0);
/*  569 */     this.buf.append(this.name).append(".visitInsn(").append(OPCODES[opcode])
/*  570 */       .append(");\n");
/*  571 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int opcode, int operand) {
/*  576 */     this.buf.setLength(0);
/*  577 */     this.buf.append(this.name)
/*  578 */       .append(".visitIntInsn(")
/*  579 */       .append(OPCODES[opcode])
/*  580 */       .append(", ")
/*  581 */       .append((opcode == 188) ? TYPES[operand] : 
/*  582 */         Integer.toString(operand)).append(");\n");
/*  583 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int opcode, int var) {
/*  588 */     this.buf.setLength(0);
/*  589 */     this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[opcode])
/*  590 */       .append(", ").append(var).append(");\n");
/*  591 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int opcode, String type) {
/*  596 */     this.buf.setLength(0);
/*  597 */     this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[opcode])
/*  598 */       .append(", ");
/*  599 */     appendConstant(type);
/*  600 */     this.buf.append(");\n");
/*  601 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  607 */     this.buf.setLength(0);
/*  608 */     this.buf.append(this.name).append(".visitFieldInsn(")
/*  609 */       .append(OPCODES[opcode]).append(", ");
/*  610 */     appendConstant(owner);
/*  611 */     this.buf.append(", ");
/*  612 */     appendConstant(name);
/*  613 */     this.buf.append(", ");
/*  614 */     appendConstant(desc);
/*  615 */     this.buf.append(");\n");
/*  616 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/*  623 */     if (this.api >= 327680) {
/*  624 */       super.visitMethodInsn(opcode, owner, name, desc);
/*      */       return;
/*      */     } 
/*  627 */     doVisitMethodInsn(opcode, owner, name, desc, (opcode == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  634 */     if (this.api < 327680) {
/*  635 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*      */       return;
/*      */     } 
/*  638 */     doVisitMethodInsn(opcode, owner, name, desc, itf);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  643 */     this.buf.setLength(0);
/*  644 */     this.buf.append(this.name).append(".visitMethodInsn(")
/*  645 */       .append(OPCODES[opcode]).append(", ");
/*  646 */     appendConstant(owner);
/*  647 */     this.buf.append(", ");
/*  648 */     appendConstant(name);
/*  649 */     this.buf.append(", ");
/*  650 */     appendConstant(desc);
/*  651 */     this.buf.append(", ");
/*  652 */     this.buf.append(itf ? "true" : "false");
/*  653 */     this.buf.append(");\n");
/*  654 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/*  660 */     this.buf.setLength(0);
/*  661 */     this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
/*  662 */     appendConstant(name);
/*  663 */     this.buf.append(", ");
/*  664 */     appendConstant(desc);
/*  665 */     this.buf.append(", ");
/*  666 */     appendConstant(bsm);
/*  667 */     this.buf.append(", new Object[]{");
/*  668 */     for (int i = 0; i < bsmArgs.length; i++) {
/*  669 */       appendConstant(bsmArgs[i]);
/*  670 */       if (i != bsmArgs.length - 1) {
/*  671 */         this.buf.append(", ");
/*      */       }
/*      */     } 
/*  674 */     this.buf.append("});\n");
/*  675 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int opcode, Label label) {
/*  680 */     this.buf.setLength(0);
/*  681 */     declareLabel(label);
/*  682 */     this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[opcode])
/*  683 */       .append(", ");
/*  684 */     appendLabel(label);
/*  685 */     this.buf.append(");\n");
/*  686 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label label) {
/*  691 */     this.buf.setLength(0);
/*  692 */     declareLabel(label);
/*  693 */     this.buf.append(this.name).append(".visitLabel(");
/*  694 */     appendLabel(label);
/*  695 */     this.buf.append(");\n");
/*  696 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object cst) {
/*  701 */     this.buf.setLength(0);
/*  702 */     this.buf.append(this.name).append(".visitLdcInsn(");
/*  703 */     appendConstant(cst);
/*  704 */     this.buf.append(");\n");
/*  705 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int var, int increment) {
/*  710 */     this.buf.setLength(0);
/*  711 */     this.buf.append(this.name).append(".visitIincInsn(").append(var).append(", ")
/*  712 */       .append(increment).append(");\n");
/*  713 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/*  719 */     this.buf.setLength(0); int i;
/*  720 */     for (i = 0; i < labels.length; i++) {
/*  721 */       declareLabel(labels[i]);
/*      */     }
/*  723 */     declareLabel(dflt);
/*      */     
/*  725 */     this.buf.append(this.name).append(".visitTableSwitchInsn(").append(min)
/*  726 */       .append(", ").append(max).append(", ");
/*  727 */     appendLabel(dflt);
/*  728 */     this.buf.append(", new Label[] {");
/*  729 */     for (i = 0; i < labels.length; i++) {
/*  730 */       this.buf.append((i == 0) ? " " : ", ");
/*  731 */       appendLabel(labels[i]);
/*      */     } 
/*  733 */     this.buf.append(" });\n");
/*  734 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/*  740 */     this.buf.setLength(0); int i;
/*  741 */     for (i = 0; i < labels.length; i++) {
/*  742 */       declareLabel(labels[i]);
/*      */     }
/*  744 */     declareLabel(dflt);
/*      */     
/*  746 */     this.buf.append(this.name).append(".visitLookupSwitchInsn(");
/*  747 */     appendLabel(dflt);
/*  748 */     this.buf.append(", new int[] {");
/*  749 */     for (i = 0; i < keys.length; i++) {
/*  750 */       this.buf.append((i == 0) ? " " : ", ").append(keys[i]);
/*      */     }
/*  752 */     this.buf.append(" }, new Label[] {");
/*  753 */     for (i = 0; i < labels.length; i++) {
/*  754 */       this.buf.append((i == 0) ? " " : ", ");
/*  755 */       appendLabel(labels[i]);
/*      */     } 
/*  757 */     this.buf.append(" });\n");
/*  758 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String desc, int dims) {
/*  763 */     this.buf.setLength(0);
/*  764 */     this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
/*  765 */     appendConstant(desc);
/*  766 */     this.buf.append(", ").append(dims).append(");\n");
/*  767 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  773 */     return visitTypeAnnotation("visitInsnAnnotation", typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/*  780 */     this.buf.setLength(0);
/*  781 */     declareLabel(start);
/*  782 */     declareLabel(end);
/*  783 */     declareLabel(handler);
/*  784 */     this.buf.append(this.name).append(".visitTryCatchBlock(");
/*  785 */     appendLabel(start);
/*  786 */     this.buf.append(", ");
/*  787 */     appendLabel(end);
/*  788 */     this.buf.append(", ");
/*  789 */     appendLabel(handler);
/*  790 */     this.buf.append(", ");
/*  791 */     appendConstant(type);
/*  792 */     this.buf.append(");\n");
/*  793 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  799 */     return visitTypeAnnotation("visitTryCatchAnnotation", typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/*  807 */     this.buf.setLength(0);
/*  808 */     this.buf.append(this.name).append(".visitLocalVariable(");
/*  809 */     appendConstant(name);
/*  810 */     this.buf.append(", ");
/*  811 */     appendConstant(desc);
/*  812 */     this.buf.append(", ");
/*  813 */     appendConstant(signature);
/*  814 */     this.buf.append(", ");
/*  815 */     appendLabel(start);
/*  816 */     this.buf.append(", ");
/*  817 */     appendLabel(end);
/*  818 */     this.buf.append(", ").append(index).append(");\n");
/*  819 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/*  826 */     this.buf.setLength(0);
/*  827 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  828 */       .append(".visitLocalVariableAnnotation(");
/*  829 */     this.buf.append(typeRef);
/*  830 */     if (typePath == null) {
/*  831 */       this.buf.append(", null, ");
/*      */     } else {
/*  833 */       this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
/*      */     } 
/*  835 */     this.buf.append("new Label[] {"); int i;
/*  836 */     for (i = 0; i < start.length; i++) {
/*  837 */       this.buf.append((i == 0) ? " " : ", ");
/*  838 */       appendLabel(start[i]);
/*      */     } 
/*  840 */     this.buf.append(" }, new Label[] {");
/*  841 */     for (i = 0; i < end.length; i++) {
/*  842 */       this.buf.append((i == 0) ? " " : ", ");
/*  843 */       appendLabel(end[i]);
/*      */     } 
/*  845 */     this.buf.append(" }, new int[] {");
/*  846 */     for (i = 0; i < index.length; i++) {
/*  847 */       this.buf.append((i == 0) ? " " : ", ").append(index[i]);
/*      */     }
/*  849 */     this.buf.append(" }, ");
/*  850 */     appendConstant(desc);
/*  851 */     this.buf.append(", ").append(visible).append(");\n");
/*  852 */     this.text.add(this.buf.toString());
/*  853 */     ASMifier a = createASMifier("av", 0);
/*  854 */     this.text.add(a.getText());
/*  855 */     this.text.add("}\n");
/*  856 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int line, Label start) {
/*  861 */     this.buf.setLength(0);
/*  862 */     this.buf.append(this.name).append(".visitLineNumber(").append(line).append(", ");
/*  863 */     appendLabel(start);
/*  864 */     this.buf.append(");\n");
/*  865 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int maxStack, int maxLocals) {
/*  870 */     this.buf.setLength(0);
/*  871 */     this.buf.append(this.name).append(".visitMaxs(").append(maxStack).append(", ")
/*  872 */       .append(maxLocals).append(");\n");
/*  873 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodEnd() {
/*  878 */     this.buf.setLength(0);
/*  879 */     this.buf.append(this.name).append(".visitEnd();\n");
/*  880 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotation(String desc, boolean visible) {
/*  888 */     this.buf.setLength(0);
/*  889 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  890 */       .append(".visitAnnotation(");
/*  891 */     appendConstant(desc);
/*  892 */     this.buf.append(", ").append(visible).append(");\n");
/*  893 */     this.text.add(this.buf.toString());
/*  894 */     ASMifier a = createASMifier("av", 0);
/*  895 */     this.text.add(a.getText());
/*  896 */     this.text.add("}\n");
/*  897 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  902 */     return visitTypeAnnotation("visitTypeAnnotation", typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitTypeAnnotation(String method, int typeRef, TypePath typePath, String desc, boolean visible) {
/*  908 */     this.buf.setLength(0);
/*  909 */     this.buf.append("{\n").append("av0 = ").append(this.name).append(".")
/*  910 */       .append(method).append("(");
/*  911 */     this.buf.append(typeRef);
/*  912 */     if (typePath == null) {
/*  913 */       this.buf.append(", null, ");
/*      */     } else {
/*  915 */       this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
/*      */     } 
/*  917 */     appendConstant(desc);
/*  918 */     this.buf.append(", ").append(visible).append(");\n");
/*  919 */     this.text.add(this.buf.toString());
/*  920 */     ASMifier a = createASMifier("av", 0);
/*  921 */     this.text.add(a.getText());
/*  922 */     this.text.add("}\n");
/*  923 */     return a;
/*      */   }
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  927 */     this.buf.setLength(0);
/*  928 */     this.buf.append("// ATTRIBUTE ").append(attr.type).append('\n');
/*  929 */     if (attr instanceof ASMifiable) {
/*  930 */       if (this.labelNames == null) {
/*  931 */         this.labelNames = new HashMap<Label, String>();
/*      */       }
/*  933 */       this.buf.append("{\n");
/*  934 */       ((ASMifiable)attr).asmify(this.buf, "attr", this.labelNames);
/*  935 */       this.buf.append(this.name).append(".visitAttribute(attr);\n");
/*  936 */       this.buf.append("}\n");
/*      */     } 
/*  938 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ASMifier createASMifier(String name, int id) {
/*  946 */     return new ASMifier(327680, name, id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appendAccess(int access) {
/*  957 */     boolean first = true;
/*  958 */     if ((access & 0x1) != 0) {
/*  959 */       this.buf.append("ACC_PUBLIC");
/*  960 */       first = false;
/*      */     } 
/*  962 */     if ((access & 0x2) != 0) {
/*  963 */       this.buf.append("ACC_PRIVATE");
/*  964 */       first = false;
/*      */     } 
/*  966 */     if ((access & 0x4) != 0) {
/*  967 */       this.buf.append("ACC_PROTECTED");
/*  968 */       first = false;
/*      */     } 
/*  970 */     if ((access & 0x10) != 0) {
/*  971 */       if (!first) {
/*  972 */         this.buf.append(" + ");
/*      */       }
/*  974 */       this.buf.append("ACC_FINAL");
/*  975 */       first = false;
/*      */     } 
/*  977 */     if ((access & 0x8) != 0) {
/*  978 */       if (!first) {
/*  979 */         this.buf.append(" + ");
/*      */       }
/*  981 */       this.buf.append("ACC_STATIC");
/*  982 */       first = false;
/*      */     } 
/*  984 */     if ((access & 0x20) != 0) {
/*  985 */       if (!first) {
/*  986 */         this.buf.append(" + ");
/*      */       }
/*  988 */       if ((access & 0x40000) == 0) {
/*  989 */         this.buf.append("ACC_SYNCHRONIZED");
/*      */       } else {
/*  991 */         this.buf.append("ACC_SUPER");
/*      */       } 
/*  993 */       first = false;
/*      */     } 
/*  995 */     if ((access & 0x40) != 0 && (access & 0x80000) != 0) {
/*      */       
/*  997 */       if (!first) {
/*  998 */         this.buf.append(" + ");
/*      */       }
/* 1000 */       this.buf.append("ACC_VOLATILE");
/* 1001 */       first = false;
/*      */     } 
/* 1003 */     if ((access & 0x40) != 0 && (access & 0x40000) == 0 && (access & 0x80000) == 0) {
/*      */       
/* 1005 */       if (!first) {
/* 1006 */         this.buf.append(" + ");
/*      */       }
/* 1008 */       this.buf.append("ACC_BRIDGE");
/* 1009 */       first = false;
/*      */     } 
/* 1011 */     if ((access & 0x80) != 0 && (access & 0x40000) == 0 && (access & 0x80000) == 0) {
/*      */       
/* 1013 */       if (!first) {
/* 1014 */         this.buf.append(" + ");
/*      */       }
/* 1016 */       this.buf.append("ACC_VARARGS");
/* 1017 */       first = false;
/*      */     } 
/* 1019 */     if ((access & 0x80) != 0 && (access & 0x80000) != 0) {
/*      */       
/* 1021 */       if (!first) {
/* 1022 */         this.buf.append(" + ");
/*      */       }
/* 1024 */       this.buf.append("ACC_TRANSIENT");
/* 1025 */       first = false;
/*      */     } 
/* 1027 */     if ((access & 0x100) != 0 && (access & 0x40000) == 0 && (access & 0x80000) == 0) {
/*      */       
/* 1029 */       if (!first) {
/* 1030 */         this.buf.append(" + ");
/*      */       }
/* 1032 */       this.buf.append("ACC_NATIVE");
/* 1033 */       first = false;
/*      */     } 
/* 1035 */     if ((access & 0x4000) != 0 && ((access & 0x40000) != 0 || (access & 0x80000) != 0 || (access & 0x100000) != 0)) {
/*      */ 
/*      */       
/* 1038 */       if (!first) {
/* 1039 */         this.buf.append(" + ");
/*      */       }
/* 1041 */       this.buf.append("ACC_ENUM");
/* 1042 */       first = false;
/*      */     } 
/* 1044 */     if ((access & 0x2000) != 0 && ((access & 0x40000) != 0 || (access & 0x100000) != 0)) {
/*      */       
/* 1046 */       if (!first) {
/* 1047 */         this.buf.append(" + ");
/*      */       }
/* 1049 */       this.buf.append("ACC_ANNOTATION");
/* 1050 */       first = false;
/*      */     } 
/* 1052 */     if ((access & 0x400) != 0) {
/* 1053 */       if (!first) {
/* 1054 */         this.buf.append(" + ");
/*      */       }
/* 1056 */       this.buf.append("ACC_ABSTRACT");
/* 1057 */       first = false;
/*      */     } 
/* 1059 */     if ((access & 0x200) != 0) {
/* 1060 */       if (!first) {
/* 1061 */         this.buf.append(" + ");
/*      */       }
/* 1063 */       this.buf.append("ACC_INTERFACE");
/* 1064 */       first = false;
/*      */     } 
/* 1066 */     if ((access & 0x800) != 0) {
/* 1067 */       if (!first) {
/* 1068 */         this.buf.append(" + ");
/*      */       }
/* 1070 */       this.buf.append("ACC_STRICT");
/* 1071 */       first = false;
/*      */     } 
/* 1073 */     if ((access & 0x1000) != 0) {
/* 1074 */       if (!first) {
/* 1075 */         this.buf.append(" + ");
/*      */       }
/* 1077 */       this.buf.append("ACC_SYNTHETIC");
/* 1078 */       first = false;
/*      */     } 
/* 1080 */     if ((access & 0x20000) != 0) {
/* 1081 */       if (!first) {
/* 1082 */         this.buf.append(" + ");
/*      */       }
/* 1084 */       this.buf.append("ACC_DEPRECATED");
/* 1085 */       first = false;
/*      */     } 
/* 1087 */     if ((access & 0x8000) != 0) {
/* 1088 */       if (!first) {
/* 1089 */         this.buf.append(" + ");
/*      */       }
/* 1091 */       this.buf.append("ACC_MANDATED");
/* 1092 */       first = false;
/*      */     } 
/* 1094 */     if (first) {
/* 1095 */       this.buf.append('0');
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
/*      */   protected void appendConstant(Object cst) {
/* 1108 */     appendConstant(this.buf, cst);
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
/*      */   static void appendConstant(StringBuffer buf, Object cst) {
/* 1122 */     if (cst == null) {
/* 1123 */       buf.append("null");
/* 1124 */     } else if (cst instanceof String) {
/* 1125 */       appendString(buf, (String)cst);
/* 1126 */     } else if (cst instanceof Type) {
/* 1127 */       buf.append("Type.getType(\"");
/* 1128 */       buf.append(((Type)cst).getDescriptor());
/* 1129 */       buf.append("\")");
/* 1130 */     } else if (cst instanceof Handle) {
/* 1131 */       buf.append("new Handle(");
/* 1132 */       Handle h = (Handle)cst;
/* 1133 */       buf.append("Opcodes.").append(HANDLE_TAG[h.getTag()])
/* 1134 */         .append(", \"");
/* 1135 */       buf.append(h.getOwner()).append("\", \"");
/* 1136 */       buf.append(h.getName()).append("\", \"");
/* 1137 */       buf.append(h.getDesc()).append("\")");
/* 1138 */     } else if (cst instanceof Byte) {
/* 1139 */       buf.append("new Byte((byte)").append(cst).append(')');
/* 1140 */     } else if (cst instanceof Boolean) {
/* 1141 */       buf.append(((Boolean)cst).booleanValue() ? "Boolean.TRUE" : "Boolean.FALSE");
/*      */     }
/* 1143 */     else if (cst instanceof Short) {
/* 1144 */       buf.append("new Short((short)").append(cst).append(')');
/* 1145 */     } else if (cst instanceof Character) {
/* 1146 */       int c = ((Character)cst).charValue();
/* 1147 */       buf.append("new Character((char)").append(c).append(')');
/* 1148 */     } else if (cst instanceof Integer) {
/* 1149 */       buf.append("new Integer(").append(cst).append(')');
/* 1150 */     } else if (cst instanceof Float) {
/* 1151 */       buf.append("new Float(\"").append(cst).append("\")");
/* 1152 */     } else if (cst instanceof Long) {
/* 1153 */       buf.append("new Long(").append(cst).append("L)");
/* 1154 */     } else if (cst instanceof Double) {
/* 1155 */       buf.append("new Double(\"").append(cst).append("\")");
/* 1156 */     } else if (cst instanceof byte[]) {
/* 1157 */       byte[] v = (byte[])cst;
/* 1158 */       buf.append("new byte[] {");
/* 1159 */       for (int i = 0; i < v.length; i++) {
/* 1160 */         buf.append((i == 0) ? "" : ",").append(v[i]);
/*      */       }
/* 1162 */       buf.append('}');
/* 1163 */     } else if (cst instanceof boolean[]) {
/* 1164 */       boolean[] v = (boolean[])cst;
/* 1165 */       buf.append("new boolean[] {");
/* 1166 */       for (int i = 0; i < v.length; i++) {
/* 1167 */         buf.append((i == 0) ? "" : ",").append(v[i]);
/*      */       }
/* 1169 */       buf.append('}');
/* 1170 */     } else if (cst instanceof short[]) {
/* 1171 */       short[] v = (short[])cst;
/* 1172 */       buf.append("new short[] {");
/* 1173 */       for (int i = 0; i < v.length; i++) {
/* 1174 */         buf.append((i == 0) ? "" : ",").append("(short)").append(v[i]);
/*      */       }
/* 1176 */       buf.append('}');
/* 1177 */     } else if (cst instanceof char[]) {
/* 1178 */       char[] v = (char[])cst;
/* 1179 */       buf.append("new char[] {");
/* 1180 */       for (int i = 0; i < v.length; i++) {
/* 1181 */         buf.append((i == 0) ? "" : ",").append("(char)")
/* 1182 */           .append(v[i]);
/*      */       }
/* 1184 */       buf.append('}');
/* 1185 */     } else if (cst instanceof int[]) {
/* 1186 */       int[] v = (int[])cst;
/* 1187 */       buf.append("new int[] {");
/* 1188 */       for (int i = 0; i < v.length; i++) {
/* 1189 */         buf.append((i == 0) ? "" : ",").append(v[i]);
/*      */       }
/* 1191 */       buf.append('}');
/* 1192 */     } else if (cst instanceof long[]) {
/* 1193 */       long[] v = (long[])cst;
/* 1194 */       buf.append("new long[] {");
/* 1195 */       for (int i = 0; i < v.length; i++) {
/* 1196 */         buf.append((i == 0) ? "" : ",").append(v[i]).append('L');
/*      */       }
/* 1198 */       buf.append('}');
/* 1199 */     } else if (cst instanceof float[]) {
/* 1200 */       float[] v = (float[])cst;
/* 1201 */       buf.append("new float[] {");
/* 1202 */       for (int i = 0; i < v.length; i++) {
/* 1203 */         buf.append((i == 0) ? "" : ",").append(v[i]).append('f');
/*      */       }
/* 1205 */       buf.append('}');
/* 1206 */     } else if (cst instanceof double[]) {
/* 1207 */       double[] v = (double[])cst;
/* 1208 */       buf.append("new double[] {");
/* 1209 */       for (int i = 0; i < v.length; i++) {
/* 1210 */         buf.append((i == 0) ? "" : ",").append(v[i]).append('d');
/*      */       }
/* 1212 */       buf.append('}');
/*      */     } 
/*      */   }
/*      */   
/*      */   private void declareFrameTypes(int n, Object[] o) {
/* 1217 */     for (int i = 0; i < n; i++) {
/* 1218 */       if (o[i] instanceof Label) {
/* 1219 */         declareLabel((Label)o[i]);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void appendFrameTypes(int n, Object[] o) {
/* 1225 */     for (int i = 0; i < n; i++) {
/* 1226 */       if (i > 0) {
/* 1227 */         this.buf.append(", ");
/*      */       }
/* 1229 */       if (o[i] instanceof String) {
/* 1230 */         appendConstant(o[i]);
/* 1231 */       } else if (o[i] instanceof Integer) {
/* 1232 */         switch (((Integer)o[i]).intValue()) {
/*      */           case 0:
/* 1234 */             this.buf.append("Opcodes.TOP");
/*      */             break;
/*      */           case 1:
/* 1237 */             this.buf.append("Opcodes.INTEGER");
/*      */             break;
/*      */           case 2:
/* 1240 */             this.buf.append("Opcodes.FLOAT");
/*      */             break;
/*      */           case 3:
/* 1243 */             this.buf.append("Opcodes.DOUBLE");
/*      */             break;
/*      */           case 4:
/* 1246 */             this.buf.append("Opcodes.LONG");
/*      */             break;
/*      */           case 5:
/* 1249 */             this.buf.append("Opcodes.NULL");
/*      */             break;
/*      */           case 6:
/* 1252 */             this.buf.append("Opcodes.UNINITIALIZED_THIS");
/*      */             break;
/*      */         } 
/*      */       } else {
/* 1256 */         appendLabel((Label)o[i]);
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
/*      */   protected void declareLabel(Label l) {
/* 1270 */     if (this.labelNames == null) {
/* 1271 */       this.labelNames = new HashMap<Label, String>();
/*      */     }
/* 1273 */     String name = this.labelNames.get(l);
/* 1274 */     if (name == null) {
/* 1275 */       name = "l" + this.labelNames.size();
/* 1276 */       this.labelNames.put(l, name);
/* 1277 */       this.buf.append("Label ").append(name).append(" = new Label();\n");
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
/*      */   protected void appendLabel(Label l) {
/* 1290 */     this.buf.append(this.labelNames.get(l));
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\ASMifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */