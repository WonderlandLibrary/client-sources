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
/*      */ import org.spongepowered.asm.lib.TypeReference;
/*      */ import org.spongepowered.asm.lib.signature.SignatureReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Textifier
/*      */   extends Printer
/*      */ {
/*      */   public static final int INTERNAL_NAME = 0;
/*      */   public static final int FIELD_DESCRIPTOR = 1;
/*      */   public static final int FIELD_SIGNATURE = 2;
/*      */   public static final int METHOD_DESCRIPTOR = 3;
/*      */   public static final int METHOD_SIGNATURE = 4;
/*      */   public static final int CLASS_SIGNATURE = 5;
/*      */   public static final int TYPE_DECLARATION = 6;
/*      */   public static final int CLASS_DECLARATION = 7;
/*      */   public static final int PARAMETERS_DECLARATION = 8;
/*      */   public static final int HANDLE_DESCRIPTOR = 9;
/*  118 */   protected String tab = "  ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   protected String tab2 = "    ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   protected String tab3 = "      ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   protected String ltab = "   ";
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<Label, String> labelNames;
/*      */ 
/*      */ 
/*      */   
/*      */   private int access;
/*      */ 
/*      */ 
/*      */   
/*  145 */   private int valueNumber = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier() {
/*  156 */     this(327680);
/*  157 */     if (getClass() != Textifier.class) {
/*  158 */       throw new IllegalStateException();
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
/*      */   protected Textifier(int api) {
/*  170 */     super(api);
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
/*      */   public static void main(String[] args) throws Exception {
/*      */     ClassReader cr;
/*  185 */     int i = 0;
/*  186 */     int flags = 2;
/*      */     
/*  188 */     boolean ok = true;
/*  189 */     if (args.length < 1 || args.length > 2) {
/*  190 */       ok = false;
/*      */     }
/*  192 */     if (ok && "-debug".equals(args[0])) {
/*  193 */       i = 1;
/*  194 */       flags = 0;
/*  195 */       if (args.length != 2) {
/*  196 */         ok = false;
/*      */       }
/*      */     } 
/*  199 */     if (!ok) {
/*  200 */       System.err
/*  201 */         .println("Prints a disassembled view of the given class.");
/*  202 */       System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  207 */     if (args[i].endsWith(".class") || args[i].indexOf('\\') > -1 || args[i]
/*  208 */       .indexOf('/') > -1) {
/*  209 */       cr = new ClassReader(new FileInputStream(args[i]));
/*      */     } else {
/*  211 */       cr = new ClassReader(args[i]);
/*      */     } 
/*  213 */     cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), flags);
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
/*  224 */     this.access = access;
/*  225 */     int major = version & 0xFFFF;
/*  226 */     int minor = version >>> 16;
/*  227 */     this.buf.setLength(0);
/*  228 */     this.buf.append("// class version ").append(major).append('.').append(minor)
/*  229 */       .append(" (").append(version).append(")\n");
/*  230 */     if ((access & 0x20000) != 0) {
/*  231 */       this.buf.append("// DEPRECATED\n");
/*      */     }
/*  233 */     this.buf.append("// access flags 0x")
/*  234 */       .append(Integer.toHexString(access).toUpperCase()).append('\n');
/*      */     
/*  236 */     appendDescriptor(5, signature);
/*  237 */     if (signature != null) {
/*  238 */       TraceSignatureVisitor sv = new TraceSignatureVisitor(access);
/*  239 */       SignatureReader r = new SignatureReader(signature);
/*  240 */       r.accept(sv);
/*  241 */       this.buf.append("// declaration: ").append(name)
/*  242 */         .append(sv.getDeclaration()).append('\n');
/*      */     } 
/*      */     
/*  245 */     appendAccess(access & 0xFFFFFFDF);
/*  246 */     if ((access & 0x2000) != 0) {
/*  247 */       this.buf.append("@interface ");
/*  248 */     } else if ((access & 0x200) != 0) {
/*  249 */       this.buf.append("interface ");
/*  250 */     } else if ((access & 0x4000) == 0) {
/*  251 */       this.buf.append("class ");
/*      */     } 
/*  253 */     appendDescriptor(0, name);
/*      */     
/*  255 */     if (superName != null && !"java/lang/Object".equals(superName)) {
/*  256 */       this.buf.append(" extends ");
/*  257 */       appendDescriptor(0, superName);
/*  258 */       this.buf.append(' ');
/*      */     } 
/*  260 */     if (interfaces != null && interfaces.length > 0) {
/*  261 */       this.buf.append(" implements ");
/*  262 */       for (int i = 0; i < interfaces.length; i++) {
/*  263 */         appendDescriptor(0, interfaces[i]);
/*  264 */         this.buf.append(' ');
/*      */       } 
/*      */     } 
/*  267 */     this.buf.append(" {\n\n");
/*      */     
/*  269 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String file, String debug) {
/*  274 */     this.buf.setLength(0);
/*  275 */     if (file != null) {
/*  276 */       this.buf.append(this.tab).append("// compiled from: ").append(file)
/*  277 */         .append('\n');
/*      */     }
/*  279 */     if (debug != null) {
/*  280 */       this.buf.append(this.tab).append("// debug info: ").append(debug)
/*  281 */         .append('\n');
/*      */     }
/*  283 */     if (this.buf.length() > 0) {
/*  284 */       this.text.add(this.buf.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String owner, String name, String desc) {
/*  291 */     this.buf.setLength(0);
/*  292 */     this.buf.append(this.tab).append("OUTERCLASS ");
/*  293 */     appendDescriptor(0, owner);
/*  294 */     this.buf.append(' ');
/*  295 */     if (name != null) {
/*  296 */       this.buf.append(name).append(' ');
/*      */     }
/*  298 */     appendDescriptor(3, desc);
/*  299 */     this.buf.append('\n');
/*  300 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitClassAnnotation(String desc, boolean visible) {
/*  306 */     this.text.add("\n");
/*  307 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  313 */     this.text.add("\n");
/*  314 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassAttribute(Attribute attr) {
/*  319 */     this.text.add("\n");
/*  320 */     visitAttribute(attr);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  326 */     this.buf.setLength(0);
/*  327 */     this.buf.append(this.tab).append("// access flags 0x");
/*  328 */     this.buf.append(
/*  329 */         Integer.toHexString(access & 0xFFFFFFDF).toUpperCase())
/*  330 */       .append('\n');
/*  331 */     this.buf.append(this.tab);
/*  332 */     appendAccess(access);
/*  333 */     this.buf.append("INNERCLASS ");
/*  334 */     appendDescriptor(0, name);
/*  335 */     this.buf.append(' ');
/*  336 */     appendDescriptor(0, outerName);
/*  337 */     this.buf.append(' ');
/*  338 */     appendDescriptor(0, innerName);
/*  339 */     this.buf.append('\n');
/*  340 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitField(int access, String name, String desc, String signature, Object value) {
/*  346 */     this.buf.setLength(0);
/*  347 */     this.buf.append('\n');
/*  348 */     if ((access & 0x20000) != 0) {
/*  349 */       this.buf.append(this.tab).append("// DEPRECATED\n");
/*      */     }
/*  351 */     this.buf.append(this.tab).append("// access flags 0x")
/*  352 */       .append(Integer.toHexString(access).toUpperCase()).append('\n');
/*  353 */     if (signature != null) {
/*  354 */       this.buf.append(this.tab);
/*  355 */       appendDescriptor(2, signature);
/*      */       
/*  357 */       TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
/*  358 */       SignatureReader r = new SignatureReader(signature);
/*  359 */       r.acceptType(sv);
/*  360 */       this.buf.append(this.tab).append("// declaration: ")
/*  361 */         .append(sv.getDeclaration()).append('\n');
/*      */     } 
/*      */     
/*  364 */     this.buf.append(this.tab);
/*  365 */     appendAccess(access);
/*      */     
/*  367 */     appendDescriptor(1, desc);
/*  368 */     this.buf.append(' ').append(name);
/*  369 */     if (value != null) {
/*  370 */       this.buf.append(" = ");
/*  371 */       if (value instanceof String) {
/*  372 */         this.buf.append('"').append(value).append('"');
/*      */       } else {
/*  374 */         this.buf.append(value);
/*      */       } 
/*      */     } 
/*      */     
/*  378 */     this.buf.append('\n');
/*  379 */     this.text.add(this.buf.toString());
/*      */     
/*  381 */     Textifier t = createTextifier();
/*  382 */     this.text.add(t.getText());
/*  383 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  389 */     this.buf.setLength(0);
/*  390 */     this.buf.append('\n');
/*  391 */     if ((access & 0x20000) != 0) {
/*  392 */       this.buf.append(this.tab).append("// DEPRECATED\n");
/*      */     }
/*  394 */     this.buf.append(this.tab).append("// access flags 0x")
/*  395 */       .append(Integer.toHexString(access).toUpperCase()).append('\n');
/*      */     
/*  397 */     if (signature != null) {
/*  398 */       this.buf.append(this.tab);
/*  399 */       appendDescriptor(4, signature);
/*      */       
/*  401 */       TraceSignatureVisitor v = new TraceSignatureVisitor(0);
/*  402 */       SignatureReader r = new SignatureReader(signature);
/*  403 */       r.accept(v);
/*  404 */       String genericDecl = v.getDeclaration();
/*  405 */       String genericReturn = v.getReturnType();
/*  406 */       String genericExceptions = v.getExceptions();
/*      */       
/*  408 */       this.buf.append(this.tab).append("// declaration: ").append(genericReturn)
/*  409 */         .append(' ').append(name).append(genericDecl);
/*  410 */       if (genericExceptions != null) {
/*  411 */         this.buf.append(" throws ").append(genericExceptions);
/*      */       }
/*  413 */       this.buf.append('\n');
/*      */     } 
/*      */     
/*  416 */     this.buf.append(this.tab);
/*  417 */     appendAccess(access & 0xFFFFFFBF);
/*  418 */     if ((access & 0x100) != 0) {
/*  419 */       this.buf.append("native ");
/*      */     }
/*  421 */     if ((access & 0x80) != 0) {
/*  422 */       this.buf.append("varargs ");
/*      */     }
/*  424 */     if ((access & 0x40) != 0) {
/*  425 */       this.buf.append("bridge ");
/*      */     }
/*  427 */     if ((this.access & 0x200) != 0 && (access & 0x400) == 0 && (access & 0x8) == 0)
/*      */     {
/*      */       
/*  430 */       this.buf.append("default ");
/*      */     }
/*      */     
/*  433 */     this.buf.append(name);
/*  434 */     appendDescriptor(3, desc);
/*  435 */     if (exceptions != null && exceptions.length > 0) {
/*  436 */       this.buf.append(" throws ");
/*  437 */       for (int i = 0; i < exceptions.length; i++) {
/*  438 */         appendDescriptor(0, exceptions[i]);
/*  439 */         this.buf.append(' ');
/*      */       } 
/*      */     } 
/*      */     
/*  443 */     this.buf.append('\n');
/*  444 */     this.text.add(this.buf.toString());
/*      */     
/*  446 */     Textifier t = createTextifier();
/*  447 */     this.text.add(t.getText());
/*  448 */     return t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassEnd() {
/*  453 */     this.text.add("}\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(String name, Object value) {
/*  462 */     this.buf.setLength(0);
/*  463 */     appendComa(this.valueNumber++);
/*      */     
/*  465 */     if (name != null) {
/*  466 */       this.buf.append(name).append('=');
/*      */     }
/*      */     
/*  469 */     if (value instanceof String) {
/*  470 */       visitString((String)value);
/*  471 */     } else if (value instanceof Type) {
/*  472 */       visitType((Type)value);
/*  473 */     } else if (value instanceof Byte) {
/*  474 */       visitByte(((Byte)value).byteValue());
/*  475 */     } else if (value instanceof Boolean) {
/*  476 */       visitBoolean(((Boolean)value).booleanValue());
/*  477 */     } else if (value instanceof Short) {
/*  478 */       visitShort(((Short)value).shortValue());
/*  479 */     } else if (value instanceof Character) {
/*  480 */       visitChar(((Character)value).charValue());
/*  481 */     } else if (value instanceof Integer) {
/*  482 */       visitInt(((Integer)value).intValue());
/*  483 */     } else if (value instanceof Float) {
/*  484 */       visitFloat(((Float)value).floatValue());
/*  485 */     } else if (value instanceof Long) {
/*  486 */       visitLong(((Long)value).longValue());
/*  487 */     } else if (value instanceof Double) {
/*  488 */       visitDouble(((Double)value).doubleValue());
/*  489 */     } else if (value.getClass().isArray()) {
/*  490 */       this.buf.append('{');
/*  491 */       if (value instanceof byte[]) {
/*  492 */         byte[] v = (byte[])value;
/*  493 */         for (int i = 0; i < v.length; i++) {
/*  494 */           appendComa(i);
/*  495 */           visitByte(v[i]);
/*      */         } 
/*  497 */       } else if (value instanceof boolean[]) {
/*  498 */         boolean[] v = (boolean[])value;
/*  499 */         for (int i = 0; i < v.length; i++) {
/*  500 */           appendComa(i);
/*  501 */           visitBoolean(v[i]);
/*      */         } 
/*  503 */       } else if (value instanceof short[]) {
/*  504 */         short[] v = (short[])value;
/*  505 */         for (int i = 0; i < v.length; i++) {
/*  506 */           appendComa(i);
/*  507 */           visitShort(v[i]);
/*      */         } 
/*  509 */       } else if (value instanceof char[]) {
/*  510 */         char[] v = (char[])value;
/*  511 */         for (int i = 0; i < v.length; i++) {
/*  512 */           appendComa(i);
/*  513 */           visitChar(v[i]);
/*      */         } 
/*  515 */       } else if (value instanceof int[]) {
/*  516 */         int[] v = (int[])value;
/*  517 */         for (int i = 0; i < v.length; i++) {
/*  518 */           appendComa(i);
/*  519 */           visitInt(v[i]);
/*      */         } 
/*  521 */       } else if (value instanceof long[]) {
/*  522 */         long[] v = (long[])value;
/*  523 */         for (int i = 0; i < v.length; i++) {
/*  524 */           appendComa(i);
/*  525 */           visitLong(v[i]);
/*      */         } 
/*  527 */       } else if (value instanceof float[]) {
/*  528 */         float[] v = (float[])value;
/*  529 */         for (int i = 0; i < v.length; i++) {
/*  530 */           appendComa(i);
/*  531 */           visitFloat(v[i]);
/*      */         } 
/*  533 */       } else if (value instanceof double[]) {
/*  534 */         double[] v = (double[])value;
/*  535 */         for (int i = 0; i < v.length; i++) {
/*  536 */           appendComa(i);
/*  537 */           visitDouble(v[i]);
/*      */         } 
/*      */       } 
/*  540 */       this.buf.append('}');
/*      */     } 
/*      */     
/*  543 */     this.text.add(this.buf.toString());
/*      */   }
/*      */   
/*      */   private void visitInt(int value) {
/*  547 */     this.buf.append(value);
/*      */   }
/*      */   
/*      */   private void visitLong(long value) {
/*  551 */     this.buf.append(value).append('L');
/*      */   }
/*      */   
/*      */   private void visitFloat(float value) {
/*  555 */     this.buf.append(value).append('F');
/*      */   }
/*      */   
/*      */   private void visitDouble(double value) {
/*  559 */     this.buf.append(value).append('D');
/*      */   }
/*      */   
/*      */   private void visitChar(char value) {
/*  563 */     this.buf.append("(char)").append(value);
/*      */   }
/*      */   
/*      */   private void visitShort(short value) {
/*  567 */     this.buf.append("(short)").append(value);
/*      */   }
/*      */   
/*      */   private void visitByte(byte value) {
/*  571 */     this.buf.append("(byte)").append(value);
/*      */   }
/*      */   
/*      */   private void visitBoolean(boolean value) {
/*  575 */     this.buf.append(value);
/*      */   }
/*      */   
/*      */   private void visitString(String value) {
/*  579 */     appendString(this.buf, value);
/*      */   }
/*      */   
/*      */   private void visitType(Type value) {
/*  583 */     this.buf.append(value.getClassName()).append(".class");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitEnum(String name, String desc, String value) {
/*  589 */     this.buf.setLength(0);
/*  590 */     appendComa(this.valueNumber++);
/*  591 */     if (name != null) {
/*  592 */       this.buf.append(name).append('=');
/*      */     }
/*  594 */     appendDescriptor(1, desc);
/*  595 */     this.buf.append('.').append(value);
/*  596 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotation(String name, String desc) {
/*  601 */     this.buf.setLength(0);
/*  602 */     appendComa(this.valueNumber++);
/*  603 */     if (name != null) {
/*  604 */       this.buf.append(name).append('=');
/*      */     }
/*  606 */     this.buf.append('@');
/*  607 */     appendDescriptor(1, desc);
/*  608 */     this.buf.append('(');
/*  609 */     this.text.add(this.buf.toString());
/*  610 */     Textifier t = createTextifier();
/*  611 */     this.text.add(t.getText());
/*  612 */     this.text.add(")");
/*  613 */     return t;
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitArray(String name) {
/*  618 */     this.buf.setLength(0);
/*  619 */     appendComa(this.valueNumber++);
/*  620 */     if (name != null) {
/*  621 */       this.buf.append(name).append('=');
/*      */     }
/*  623 */     this.buf.append('{');
/*  624 */     this.text.add(this.buf.toString());
/*  625 */     Textifier t = createTextifier();
/*  626 */     this.text.add(t.getText());
/*  627 */     this.text.add("}");
/*  628 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAnnotationEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitFieldAnnotation(String desc, boolean visible) {
/*  642 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  648 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldAttribute(Attribute attr) {
/*  653 */     visitAttribute(attr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String name, int access) {
/*  666 */     this.buf.setLength(0);
/*  667 */     this.buf.append(this.tab2).append("// parameter ");
/*  668 */     appendAccess(access);
/*  669 */     this.buf.append(' ').append((name == null) ? "<no name>" : name)
/*  670 */       .append('\n');
/*  671 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotationDefault() {
/*  676 */     this.text.add(this.tab2 + "default=");
/*  677 */     Textifier t = createTextifier();
/*  678 */     this.text.add(t.getText());
/*  679 */     this.text.add("\n");
/*  680 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitMethodAnnotation(String desc, boolean visible) {
/*  686 */     return visitAnnotation(desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  692 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  698 */     this.buf.setLength(0);
/*  699 */     this.buf.append(this.tab2).append('@');
/*  700 */     appendDescriptor(1, desc);
/*  701 */     this.buf.append('(');
/*  702 */     this.text.add(this.buf.toString());
/*  703 */     Textifier t = createTextifier();
/*  704 */     this.text.add(t.getText());
/*  705 */     this.text.add(visible ? ") // parameter " : ") // invisible, parameter ");
/*  706 */     this.text.add(Integer.valueOf(parameter));
/*  707 */     this.text.add("\n");
/*  708 */     return t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodAttribute(Attribute attr) {
/*  713 */     this.buf.setLength(0);
/*  714 */     this.buf.append(this.tab).append("ATTRIBUTE ");
/*  715 */     appendDescriptor(-1, attr.type);
/*      */     
/*  717 */     if (attr instanceof Textifiable) {
/*  718 */       ((Textifiable)attr).textify(this.buf, this.labelNames);
/*      */     } else {
/*  720 */       this.buf.append(" : unknown\n");
/*      */     } 
/*      */     
/*  723 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitCode() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  733 */     this.buf.setLength(0);
/*  734 */     this.buf.append(this.ltab);
/*  735 */     this.buf.append("FRAME ");
/*  736 */     switch (type) {
/*      */       case -1:
/*      */       case 0:
/*  739 */         this.buf.append("FULL [");
/*  740 */         appendFrameTypes(nLocal, local);
/*  741 */         this.buf.append("] [");
/*  742 */         appendFrameTypes(nStack, stack);
/*  743 */         this.buf.append(']');
/*      */         break;
/*      */       case 1:
/*  746 */         this.buf.append("APPEND [");
/*  747 */         appendFrameTypes(nLocal, local);
/*  748 */         this.buf.append(']');
/*      */         break;
/*      */       case 2:
/*  751 */         this.buf.append("CHOP ").append(nLocal);
/*      */         break;
/*      */       case 3:
/*  754 */         this.buf.append("SAME");
/*      */         break;
/*      */       case 4:
/*  757 */         this.buf.append("SAME1 ");
/*  758 */         appendFrameTypes(1, stack);
/*      */         break;
/*      */     } 
/*  761 */     this.buf.append('\n');
/*  762 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int opcode) {
/*  767 */     this.buf.setLength(0);
/*  768 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append('\n');
/*  769 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int opcode, int operand) {
/*  774 */     this.buf.setLength(0);
/*  775 */     this.buf.append(this.tab2)
/*  776 */       .append(OPCODES[opcode])
/*  777 */       .append(' ')
/*  778 */       .append((opcode == 188) ? TYPES[operand] : 
/*  779 */         Integer.toString(operand)).append('\n');
/*  780 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int opcode, int var) {
/*  785 */     this.buf.setLength(0);
/*  786 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ').append(var)
/*  787 */       .append('\n');
/*  788 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int opcode, String type) {
/*  793 */     this.buf.setLength(0);
/*  794 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
/*  795 */     appendDescriptor(0, type);
/*  796 */     this.buf.append('\n');
/*  797 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  803 */     this.buf.setLength(0);
/*  804 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
/*  805 */     appendDescriptor(0, owner);
/*  806 */     this.buf.append('.').append(name).append(" : ");
/*  807 */     appendDescriptor(1, desc);
/*  808 */     this.buf.append('\n');
/*  809 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/*  816 */     if (this.api >= 327680) {
/*  817 */       super.visitMethodInsn(opcode, owner, name, desc);
/*      */       return;
/*      */     } 
/*  820 */     doVisitMethodInsn(opcode, owner, name, desc, (opcode == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  827 */     if (this.api < 327680) {
/*  828 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*      */       return;
/*      */     } 
/*  831 */     doVisitMethodInsn(opcode, owner, name, desc, itf);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  836 */     this.buf.setLength(0);
/*  837 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
/*  838 */     appendDescriptor(0, owner);
/*  839 */     this.buf.append('.').append(name).append(' ');
/*  840 */     appendDescriptor(3, desc);
/*  841 */     this.buf.append('\n');
/*  842 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/*  848 */     this.buf.setLength(0);
/*  849 */     this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
/*  850 */     this.buf.append(name);
/*  851 */     appendDescriptor(3, desc);
/*  852 */     this.buf.append(" [");
/*  853 */     this.buf.append('\n');
/*  854 */     this.buf.append(this.tab3);
/*  855 */     appendHandle(bsm);
/*  856 */     this.buf.append('\n');
/*  857 */     this.buf.append(this.tab3).append("// arguments:");
/*  858 */     if (bsmArgs.length == 0) {
/*  859 */       this.buf.append(" none");
/*      */     } else {
/*  861 */       this.buf.append('\n');
/*  862 */       for (int i = 0; i < bsmArgs.length; i++) {
/*  863 */         this.buf.append(this.tab3);
/*  864 */         Object cst = bsmArgs[i];
/*  865 */         if (cst instanceof String) {
/*  866 */           Printer.appendString(this.buf, (String)cst);
/*  867 */         } else if (cst instanceof Type) {
/*  868 */           Type type = (Type)cst;
/*  869 */           if (type.getSort() == 11) {
/*  870 */             appendDescriptor(3, type.getDescriptor());
/*      */           } else {
/*  872 */             this.buf.append(type.getDescriptor()).append(".class");
/*      */           } 
/*  874 */         } else if (cst instanceof Handle) {
/*  875 */           appendHandle((Handle)cst);
/*      */         } else {
/*  877 */           this.buf.append(cst);
/*      */         } 
/*  879 */         this.buf.append(", \n");
/*      */       } 
/*  881 */       this.buf.setLength(this.buf.length() - 3);
/*      */     } 
/*  883 */     this.buf.append('\n');
/*  884 */     this.buf.append(this.tab2).append("]\n");
/*  885 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int opcode, Label label) {
/*  890 */     this.buf.setLength(0);
/*  891 */     this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
/*  892 */     appendLabel(label);
/*  893 */     this.buf.append('\n');
/*  894 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label label) {
/*  899 */     this.buf.setLength(0);
/*  900 */     this.buf.append(this.ltab);
/*  901 */     appendLabel(label);
/*  902 */     this.buf.append('\n');
/*  903 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object cst) {
/*  908 */     this.buf.setLength(0);
/*  909 */     this.buf.append(this.tab2).append("LDC ");
/*  910 */     if (cst instanceof String) {
/*  911 */       Printer.appendString(this.buf, (String)cst);
/*  912 */     } else if (cst instanceof Type) {
/*  913 */       this.buf.append(((Type)cst).getDescriptor()).append(".class");
/*      */     } else {
/*  915 */       this.buf.append(cst);
/*      */     } 
/*  917 */     this.buf.append('\n');
/*  918 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int var, int increment) {
/*  923 */     this.buf.setLength(0);
/*  924 */     this.buf.append(this.tab2).append("IINC ").append(var).append(' ')
/*  925 */       .append(increment).append('\n');
/*  926 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/*  932 */     this.buf.setLength(0);
/*  933 */     this.buf.append(this.tab2).append("TABLESWITCH\n");
/*  934 */     for (int i = 0; i < labels.length; i++) {
/*  935 */       this.buf.append(this.tab3).append(min + i).append(": ");
/*  936 */       appendLabel(labels[i]);
/*  937 */       this.buf.append('\n');
/*      */     } 
/*  939 */     this.buf.append(this.tab3).append("default: ");
/*  940 */     appendLabel(dflt);
/*  941 */     this.buf.append('\n');
/*  942 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/*  948 */     this.buf.setLength(0);
/*  949 */     this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
/*  950 */     for (int i = 0; i < labels.length; i++) {
/*  951 */       this.buf.append(this.tab3).append(keys[i]).append(": ");
/*  952 */       appendLabel(labels[i]);
/*  953 */       this.buf.append('\n');
/*      */     } 
/*  955 */     this.buf.append(this.tab3).append("default: ");
/*  956 */     appendLabel(dflt);
/*  957 */     this.buf.append('\n');
/*  958 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String desc, int dims) {
/*  963 */     this.buf.setLength(0);
/*  964 */     this.buf.append(this.tab2).append("MULTIANEWARRAY ");
/*  965 */     appendDescriptor(1, desc);
/*  966 */     this.buf.append(' ').append(dims).append('\n');
/*  967 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  973 */     return visitTypeAnnotation(typeRef, typePath, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/*  979 */     this.buf.setLength(0);
/*  980 */     this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
/*  981 */     appendLabel(start);
/*  982 */     this.buf.append(' ');
/*  983 */     appendLabel(end);
/*  984 */     this.buf.append(' ');
/*  985 */     appendLabel(handler);
/*  986 */     this.buf.append(' ');
/*  987 */     appendDescriptor(0, type);
/*  988 */     this.buf.append('\n');
/*  989 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  995 */     this.buf.setLength(0);
/*  996 */     this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
/*  997 */     appendDescriptor(1, desc);
/*  998 */     this.buf.append('(');
/*  999 */     this.text.add(this.buf.toString());
/* 1000 */     Textifier t = createTextifier();
/* 1001 */     this.text.add(t.getText());
/* 1002 */     this.buf.setLength(0);
/* 1003 */     this.buf.append(") : ");
/* 1004 */     appendTypeReference(typeRef);
/* 1005 */     this.buf.append(", ").append(typePath);
/* 1006 */     this.buf.append(visible ? "\n" : " // invisible\n");
/* 1007 */     this.text.add(this.buf.toString());
/* 1008 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 1015 */     this.buf.setLength(0);
/* 1016 */     this.buf.append(this.tab2).append("LOCALVARIABLE ").append(name).append(' ');
/* 1017 */     appendDescriptor(1, desc);
/* 1018 */     this.buf.append(' ');
/* 1019 */     appendLabel(start);
/* 1020 */     this.buf.append(' ');
/* 1021 */     appendLabel(end);
/* 1022 */     this.buf.append(' ').append(index).append('\n');
/*      */     
/* 1024 */     if (signature != null) {
/* 1025 */       this.buf.append(this.tab2);
/* 1026 */       appendDescriptor(2, signature);
/*      */       
/* 1028 */       TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
/* 1029 */       SignatureReader r = new SignatureReader(signature);
/* 1030 */       r.acceptType(sv);
/* 1031 */       this.buf.append(this.tab2).append("// declaration: ")
/* 1032 */         .append(sv.getDeclaration()).append('\n');
/*      */     } 
/* 1034 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 1041 */     this.buf.setLength(0);
/* 1042 */     this.buf.append(this.tab2).append("LOCALVARIABLE @");
/* 1043 */     appendDescriptor(1, desc);
/* 1044 */     this.buf.append('(');
/* 1045 */     this.text.add(this.buf.toString());
/* 1046 */     Textifier t = createTextifier();
/* 1047 */     this.text.add(t.getText());
/* 1048 */     this.buf.setLength(0);
/* 1049 */     this.buf.append(") : ");
/* 1050 */     appendTypeReference(typeRef);
/* 1051 */     this.buf.append(", ").append(typePath);
/* 1052 */     for (int i = 0; i < start.length; i++) {
/* 1053 */       this.buf.append(" [ ");
/* 1054 */       appendLabel(start[i]);
/* 1055 */       this.buf.append(" - ");
/* 1056 */       appendLabel(end[i]);
/* 1057 */       this.buf.append(" - ").append(index[i]).append(" ]");
/*      */     } 
/* 1059 */     this.buf.append(visible ? "\n" : " // invisible\n");
/* 1060 */     this.text.add(this.buf.toString());
/* 1061 */     return t;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int line, Label start) {
/* 1066 */     this.buf.setLength(0);
/* 1067 */     this.buf.append(this.tab2).append("LINENUMBER ").append(line).append(' ');
/* 1068 */     appendLabel(start);
/* 1069 */     this.buf.append('\n');
/* 1070 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int maxStack, int maxLocals) {
/* 1075 */     this.buf.setLength(0);
/* 1076 */     this.buf.append(this.tab2).append("MAXSTACK = ").append(maxStack).append('\n');
/* 1077 */     this.text.add(this.buf.toString());
/*      */     
/* 1079 */     this.buf.setLength(0);
/* 1080 */     this.buf.append(this.tab2).append("MAXLOCALS = ").append(maxLocals).append('\n');
/* 1081 */     this.text.add(this.buf.toString());
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
/*      */   public void visitMethodEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotation(String desc, boolean visible) {
/* 1102 */     this.buf.setLength(0);
/* 1103 */     this.buf.append(this.tab).append('@');
/* 1104 */     appendDescriptor(1, desc);
/* 1105 */     this.buf.append('(');
/* 1106 */     this.text.add(this.buf.toString());
/* 1107 */     Textifier t = createTextifier();
/* 1108 */     this.text.add(t.getText());
/* 1109 */     this.text.add(visible ? ")\n" : ") // invisible\n");
/* 1110 */     return t;
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
/*      */   public Textifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 1130 */     this.buf.setLength(0);
/* 1131 */     this.buf.append(this.tab).append('@');
/* 1132 */     appendDescriptor(1, desc);
/* 1133 */     this.buf.append('(');
/* 1134 */     this.text.add(this.buf.toString());
/* 1135 */     Textifier t = createTextifier();
/* 1136 */     this.text.add(t.getText());
/* 1137 */     this.buf.setLength(0);
/* 1138 */     this.buf.append(") : ");
/* 1139 */     appendTypeReference(typeRef);
/* 1140 */     this.buf.append(", ").append(typePath);
/* 1141 */     this.buf.append(visible ? "\n" : " // invisible\n");
/* 1142 */     this.text.add(this.buf.toString());
/* 1143 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/* 1153 */     this.buf.setLength(0);
/* 1154 */     this.buf.append(this.tab).append("ATTRIBUTE ");
/* 1155 */     appendDescriptor(-1, attr.type);
/*      */     
/* 1157 */     if (attr instanceof Textifiable) {
/* 1158 */       ((Textifiable)attr).textify(this.buf, null);
/*      */     } else {
/* 1160 */       this.buf.append(" : unknown\n");
/*      */     } 
/*      */     
/* 1163 */     this.text.add(this.buf.toString());
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
/*      */   protected Textifier createTextifier() {
/* 1176 */     return new Textifier();
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
/*      */   protected void appendDescriptor(int type, String desc) {
/* 1191 */     if (type == 5 || type == 2 || type == 4) {
/*      */       
/* 1193 */       if (desc != null) {
/* 1194 */         this.buf.append("// signature ").append(desc).append('\n');
/*      */       }
/*      */     } else {
/* 1197 */       this.buf.append(desc);
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
/*      */   protected void appendLabel(Label l) {
/* 1209 */     if (this.labelNames == null) {
/* 1210 */       this.labelNames = new HashMap<Label, String>();
/*      */     }
/* 1212 */     String name = this.labelNames.get(l);
/* 1213 */     if (name == null) {
/* 1214 */       name = "L" + this.labelNames.size();
/* 1215 */       this.labelNames.put(l, name);
/*      */     } 
/* 1217 */     this.buf.append(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendHandle(Handle h) {
/* 1227 */     int tag = h.getTag();
/* 1228 */     this.buf.append("// handle kind 0x").append(Integer.toHexString(tag))
/* 1229 */       .append(" : ");
/* 1230 */     boolean isMethodHandle = false;
/* 1231 */     switch (tag) {
/*      */       case 1:
/* 1233 */         this.buf.append("GETFIELD");
/*      */         break;
/*      */       case 2:
/* 1236 */         this.buf.append("GETSTATIC");
/*      */         break;
/*      */       case 3:
/* 1239 */         this.buf.append("PUTFIELD");
/*      */         break;
/*      */       case 4:
/* 1242 */         this.buf.append("PUTSTATIC");
/*      */         break;
/*      */       case 9:
/* 1245 */         this.buf.append("INVOKEINTERFACE");
/* 1246 */         isMethodHandle = true;
/*      */         break;
/*      */       case 7:
/* 1249 */         this.buf.append("INVOKESPECIAL");
/* 1250 */         isMethodHandle = true;
/*      */         break;
/*      */       case 6:
/* 1253 */         this.buf.append("INVOKESTATIC");
/* 1254 */         isMethodHandle = true;
/*      */         break;
/*      */       case 5:
/* 1257 */         this.buf.append("INVOKEVIRTUAL");
/* 1258 */         isMethodHandle = true;
/*      */         break;
/*      */       case 8:
/* 1261 */         this.buf.append("NEWINVOKESPECIAL");
/* 1262 */         isMethodHandle = true;
/*      */         break;
/*      */     } 
/* 1265 */     this.buf.append('\n');
/* 1266 */     this.buf.append(this.tab3);
/* 1267 */     appendDescriptor(0, h.getOwner());
/* 1268 */     this.buf.append('.');
/* 1269 */     this.buf.append(h.getName());
/* 1270 */     if (!isMethodHandle) {
/* 1271 */       this.buf.append('(');
/*      */     }
/* 1273 */     appendDescriptor(9, h.getDesc());
/* 1274 */     if (!isMethodHandle) {
/* 1275 */       this.buf.append(')');
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
/*      */   private void appendAccess(int access) {
/* 1287 */     if ((access & 0x1) != 0) {
/* 1288 */       this.buf.append("public ");
/*      */     }
/* 1290 */     if ((access & 0x2) != 0) {
/* 1291 */       this.buf.append("private ");
/*      */     }
/* 1293 */     if ((access & 0x4) != 0) {
/* 1294 */       this.buf.append("protected ");
/*      */     }
/* 1296 */     if ((access & 0x10) != 0) {
/* 1297 */       this.buf.append("final ");
/*      */     }
/* 1299 */     if ((access & 0x8) != 0) {
/* 1300 */       this.buf.append("static ");
/*      */     }
/* 1302 */     if ((access & 0x20) != 0) {
/* 1303 */       this.buf.append("synchronized ");
/*      */     }
/* 1305 */     if ((access & 0x40) != 0) {
/* 1306 */       this.buf.append("volatile ");
/*      */     }
/* 1308 */     if ((access & 0x80) != 0) {
/* 1309 */       this.buf.append("transient ");
/*      */     }
/* 1311 */     if ((access & 0x400) != 0) {
/* 1312 */       this.buf.append("abstract ");
/*      */     }
/* 1314 */     if ((access & 0x800) != 0) {
/* 1315 */       this.buf.append("strictfp ");
/*      */     }
/* 1317 */     if ((access & 0x1000) != 0) {
/* 1318 */       this.buf.append("synthetic ");
/*      */     }
/* 1320 */     if ((access & 0x8000) != 0) {
/* 1321 */       this.buf.append("mandated ");
/*      */     }
/* 1323 */     if ((access & 0x4000) != 0) {
/* 1324 */       this.buf.append("enum ");
/*      */     }
/*      */   }
/*      */   
/*      */   private void appendComa(int i) {
/* 1329 */     if (i != 0) {
/* 1330 */       this.buf.append(", ");
/*      */     }
/*      */   }
/*      */   
/*      */   private void appendTypeReference(int typeRef) {
/* 1335 */     TypeReference ref = new TypeReference(typeRef);
/* 1336 */     switch (ref.getSort()) {
/*      */       case 0:
/* 1338 */         this.buf.append("CLASS_TYPE_PARAMETER ").append(ref
/* 1339 */             .getTypeParameterIndex());
/*      */         break;
/*      */       case 1:
/* 1342 */         this.buf.append("METHOD_TYPE_PARAMETER ").append(ref
/* 1343 */             .getTypeParameterIndex());
/*      */         break;
/*      */       case 16:
/* 1346 */         this.buf.append("CLASS_EXTENDS ").append(ref.getSuperTypeIndex());
/*      */         break;
/*      */       case 17:
/* 1349 */         this.buf.append("CLASS_TYPE_PARAMETER_BOUND ")
/* 1350 */           .append(ref.getTypeParameterIndex()).append(", ")
/* 1351 */           .append(ref.getTypeParameterBoundIndex());
/*      */         break;
/*      */       case 18:
/* 1354 */         this.buf.append("METHOD_TYPE_PARAMETER_BOUND ")
/* 1355 */           .append(ref.getTypeParameterIndex()).append(", ")
/* 1356 */           .append(ref.getTypeParameterBoundIndex());
/*      */         break;
/*      */       case 19:
/* 1359 */         this.buf.append("FIELD");
/*      */         break;
/*      */       case 20:
/* 1362 */         this.buf.append("METHOD_RETURN");
/*      */         break;
/*      */       case 21:
/* 1365 */         this.buf.append("METHOD_RECEIVER");
/*      */         break;
/*      */       case 22:
/* 1368 */         this.buf.append("METHOD_FORMAL_PARAMETER ").append(ref
/* 1369 */             .getFormalParameterIndex());
/*      */         break;
/*      */       case 23:
/* 1372 */         this.buf.append("THROWS ").append(ref.getExceptionIndex());
/*      */         break;
/*      */       case 64:
/* 1375 */         this.buf.append("LOCAL_VARIABLE");
/*      */         break;
/*      */       case 65:
/* 1378 */         this.buf.append("RESOURCE_VARIABLE");
/*      */         break;
/*      */       case 66:
/* 1381 */         this.buf.append("EXCEPTION_PARAMETER ").append(ref
/* 1382 */             .getTryCatchBlockIndex());
/*      */         break;
/*      */       case 67:
/* 1385 */         this.buf.append("INSTANCEOF");
/*      */         break;
/*      */       case 68:
/* 1388 */         this.buf.append("NEW");
/*      */         break;
/*      */       case 69:
/* 1391 */         this.buf.append("CONSTRUCTOR_REFERENCE");
/*      */         break;
/*      */       case 70:
/* 1394 */         this.buf.append("METHOD_REFERENCE");
/*      */         break;
/*      */       case 71:
/* 1397 */         this.buf.append("CAST ").append(ref.getTypeArgumentIndex());
/*      */         break;
/*      */       case 72:
/* 1400 */         this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(ref
/* 1401 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 73:
/* 1404 */         this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(ref
/* 1405 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 74:
/* 1408 */         this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(ref
/* 1409 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 75:
/* 1412 */         this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(ref
/* 1413 */             .getTypeArgumentIndex());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void appendFrameTypes(int n, Object[] o) {
/* 1419 */     for (int i = 0; i < n; i++) {
/* 1420 */       if (i > 0) {
/* 1421 */         this.buf.append(' ');
/*      */       }
/* 1423 */       if (o[i] instanceof String) {
/* 1424 */         String desc = (String)o[i];
/* 1425 */         if (desc.startsWith("[")) {
/* 1426 */           appendDescriptor(1, desc);
/*      */         } else {
/* 1428 */           appendDescriptor(0, desc);
/*      */         } 
/* 1430 */       } else if (o[i] instanceof Integer) {
/* 1431 */         switch (((Integer)o[i]).intValue()) {
/*      */           case 0:
/* 1433 */             appendDescriptor(1, "T");
/*      */             break;
/*      */           case 1:
/* 1436 */             appendDescriptor(1, "I");
/*      */             break;
/*      */           case 2:
/* 1439 */             appendDescriptor(1, "F");
/*      */             break;
/*      */           case 3:
/* 1442 */             appendDescriptor(1, "D");
/*      */             break;
/*      */           case 4:
/* 1445 */             appendDescriptor(1, "J");
/*      */             break;
/*      */           case 5:
/* 1448 */             appendDescriptor(1, "N");
/*      */             break;
/*      */           case 6:
/* 1451 */             appendDescriptor(1, "U");
/*      */             break;
/*      */         } 
/*      */       } else {
/* 1455 */         appendLabel((Label)o[i]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\Textifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */