/*     */ package org.spongepowered.asm.mixin.injection.invoke.arg;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.ClassWriter;
/*     */ import org.spongepowered.asm.lib.Label;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.util.CheckClassAdapter;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
/*     */ import org.spongepowered.asm.util.asm.MethodVisitorEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArgsClassGenerator
/*     */   implements IClassGenerator
/*     */ {
/*  57 */   public static final String ARGS_NAME = Args.class.getName();
/*  58 */   public static final String ARGS_REF = ARGS_NAME.replace('.', '/');
/*     */   
/*     */   public static final String GETTER_PREFIX = "$";
/*     */   
/*     */   private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
/*     */   
/*     */   private static final String OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
/*     */   
/*     */   private static final String VALUES_FIELD = "values";
/*     */   
/*     */   private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String SET = "set";
/*     */   
/*     */   private static final String SET_DESC = "(ILjava/lang/Object;)V";
/*     */   
/*     */   private static final String SETALL = "setAll";
/*     */   
/*     */   private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String NPE = "java/lang/NullPointerException";
/*     */   
/*     */   private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
/*     */   
/*     */   private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
/*     */   
/*     */   private static final String AIOOBE_CTOR_DESC = "(I)V";
/*     */   
/*     */   private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
/*     */   private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
/*  90 */   private int nextIndex = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private final BiMap<String, String> classNames = (BiMap<String, String>)HashBiMap.create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private final Map<String, byte[]> classBytes = (Map)new HashMap<String, byte>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName(String desc) {
/* 115 */     String voidDesc = Bytecode.changeDescriptorReturnType(desc, "V");
/* 116 */     String name = (String)this.classNames.get(voidDesc);
/* 117 */     if (name == null) {
/* 118 */       name = String.format("%s%d", new Object[] { "org.spongepowered.asm.synthetic.args.Args$", Integer.valueOf(this.nextIndex++) });
/* 119 */       this.classNames.put(voidDesc, name);
/*     */     } 
/* 121 */     return name;
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
/*     */   public String getClassRef(String desc) {
/* 134 */     return getClassName(desc).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generate(String name) {
/* 143 */     return getBytes(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes(String name) {
/* 154 */     byte[] bytes = this.classBytes.get(name);
/* 155 */     if (bytes == null) {
/* 156 */       String desc = (String)this.classNames.inverse().get(name);
/* 157 */       if (desc == null) {
/* 158 */         return null;
/*     */       }
/* 160 */       bytes = generateClass(name, desc);
/* 161 */       this.classBytes.put(name, bytes);
/*     */     } 
/* 163 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] generateClass(String name, String desc) {
/*     */     CheckClassAdapter checkClassAdapter;
/* 174 */     String ref = name.replace('.', '/');
/* 175 */     Type[] args = Type.getArgumentTypes(desc);
/*     */     
/* 177 */     ClassWriter writer = new ClassWriter(2);
/* 178 */     ClassWriter classWriter1 = writer;
/* 179 */     if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/* 180 */       checkClassAdapter = new CheckClassAdapter((ClassVisitor)writer);
/*     */     }
/*     */     
/* 183 */     checkClassAdapter.visit(50, 4129, ref, null, ARGS_REF, null);
/* 184 */     checkClassAdapter.visitSource(name.substring(name.lastIndexOf('.') + 1) + ".java", null);
/*     */     
/* 186 */     generateCtor(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 187 */     generateToString(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 188 */     generateFactory(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 189 */     generateSetters(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 190 */     generateGetters(ref, desc, args, (ClassVisitor)checkClassAdapter);
/*     */     
/* 192 */     checkClassAdapter.visitEnd();
/*     */     
/* 194 */     return writer.toByteArray();
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
/*     */   private void generateCtor(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 207 */     MethodVisitor ctor = writer.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
/* 208 */     ctor.visitCode();
/* 209 */     ctor.visitVarInsn(25, 0);
/* 210 */     ctor.visitVarInsn(25, 1);
/* 211 */     ctor.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
/* 212 */     ctor.visitInsn(177);
/* 213 */     ctor.visitMaxs(2, 2);
/* 214 */     ctor.visitEnd();
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
/*     */   private void generateToString(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 226 */     MethodVisitor toString = writer.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
/* 227 */     toString.visitCode();
/* 228 */     toString.visitLdcInsn("Args" + getSignature(args));
/* 229 */     toString.visitInsn(176);
/* 230 */     toString.visitMaxs(1, 1);
/* 231 */     toString.visitEnd();
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
/*     */   private void generateFactory(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 246 */     String factoryDesc = Bytecode.changeDescriptorReturnType(desc, "L" + ref + ";");
/* 247 */     MethodVisitorEx of = new MethodVisitorEx(writer.visitMethod(9, "of", factoryDesc, null, null));
/* 248 */     of.visitCode();
/*     */ 
/*     */     
/* 251 */     of.visitTypeInsn(187, ref);
/* 252 */     of.visitInsn(89);
/*     */ 
/*     */     
/* 255 */     of.visitConstant((byte)args.length);
/* 256 */     of.visitTypeInsn(189, "java/lang/Object");
/*     */ 
/*     */     
/* 259 */     byte argIndex = 0;
/* 260 */     for (Type arg : args) {
/* 261 */       of.visitInsn(89);
/* 262 */       of.visitConstant(argIndex);
/* 263 */       of.visitVarInsn(arg.getOpcode(21), argIndex);
/* 264 */       box((MethodVisitor)of, arg);
/* 265 */       of.visitInsn(83);
/* 266 */       argIndex = (byte)(argIndex + arg.getSize());
/*     */     } 
/*     */ 
/*     */     
/* 270 */     of.visitMethodInsn(183, ref, "<init>", "([Ljava/lang/Object;)V", false);
/*     */ 
/*     */     
/* 273 */     of.visitInsn(176);
/*     */     
/* 275 */     of.visitMaxs(6, Bytecode.getArgsSize(args));
/* 276 */     of.visitEnd();
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
/*     */   private void generateGetters(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 291 */     byte argIndex = 0;
/* 292 */     for (Type arg : args) {
/* 293 */       String name = "$" + argIndex;
/* 294 */       String sig = "()" + arg.getDescriptor();
/* 295 */       MethodVisitorEx get = new MethodVisitorEx(writer.visitMethod(1, name, sig, null, null));
/* 296 */       get.visitCode();
/*     */ 
/*     */       
/* 299 */       get.visitVarInsn(25, 0);
/* 300 */       get.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/* 301 */       get.visitConstant(argIndex);
/* 302 */       get.visitInsn(50);
/*     */ 
/*     */       
/* 305 */       unbox((MethodVisitor)get, arg);
/*     */ 
/*     */       
/* 308 */       get.visitInsn(arg.getOpcode(172));
/*     */       
/* 310 */       get.visitMaxs(2, 1);
/* 311 */       get.visitEnd();
/* 312 */       argIndex = (byte)(argIndex + 1);
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
/*     */   private void generateSetters(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 326 */     generateIndexedSetter(ref, desc, args, writer);
/* 327 */     generateMultiSetter(ref, desc, args, writer);
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
/*     */   private void generateIndexedSetter(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 342 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
/*     */     
/* 344 */     set.visitCode();
/*     */     
/* 346 */     Label store = new Label(), checkNull = new Label();
/* 347 */     Label[] labels = new Label[args.length];
/* 348 */     for (int label = 0; label < labels.length; label++) {
/* 349 */       labels[label] = new Label();
/*     */     }
/*     */ 
/*     */     
/* 353 */     set.visitVarInsn(25, 0);
/* 354 */     set.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/*     */     
/*     */     byte b;
/* 357 */     for (b = 0; b < args.length; b = (byte)(b + 1)) {
/* 358 */       set.visitVarInsn(21, 1);
/* 359 */       set.visitConstant(b);
/* 360 */       set.visitJumpInsn(159, labels[b]);
/*     */     } 
/*     */ 
/*     */     
/* 364 */     throwAIOOBE(set, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     for (int index = 0; index < args.length; index++) {
/* 370 */       String boxingType = Bytecode.getBoxingType(args[index]);
/* 371 */       set.visitLabel(labels[index]);
/* 372 */       set.visitVarInsn(21, 1);
/* 373 */       set.visitVarInsn(25, 2);
/* 374 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : args[index].getInternalName());
/* 375 */       set.visitJumpInsn(167, (boxingType != null) ? checkNull : store);
/*     */     } 
/*     */ 
/*     */     
/* 379 */     set.visitLabel(checkNull);
/* 380 */     set.visitInsn(89);
/* 381 */     set.visitJumpInsn(199, store);
/*     */ 
/*     */     
/* 384 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/*     */ 
/*     */     
/* 387 */     set.visitLabel(store);
/* 388 */     set.visitInsn(83);
/* 389 */     set.visitInsn(177);
/* 390 */     set.visitMaxs(6, 3);
/* 391 */     set.visitEnd();
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
/*     */   private void generateMultiSetter(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 405 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
/*     */     
/* 407 */     set.visitCode();
/*     */     
/* 409 */     Label lengthOk = new Label(), nullPrimitive = new Label();
/* 410 */     int maxStack = 6;
/*     */ 
/*     */     
/* 413 */     set.visitVarInsn(25, 1);
/* 414 */     set.visitInsn(190);
/* 415 */     set.visitInsn(89);
/* 416 */     set.visitConstant((byte)args.length);
/*     */ 
/*     */     
/* 419 */     set.visitJumpInsn(159, lengthOk);
/*     */     
/* 421 */     set.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
/* 422 */     set.visitInsn(89);
/* 423 */     set.visitInsn(93);
/* 424 */     set.visitInsn(88);
/* 425 */     set.visitConstant((byte)args.length);
/* 426 */     set.visitLdcInsn(getSignature(args));
/*     */     
/* 428 */     set.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
/* 429 */     set.visitInsn(191);
/*     */     
/* 431 */     set.visitLabel(lengthOk);
/* 432 */     set.visitInsn(87);
/*     */ 
/*     */     
/* 435 */     set.visitVarInsn(25, 0);
/* 436 */     set.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/*     */     byte index;
/* 438 */     for (index = 0; index < args.length; index = (byte)(index + 1)) {
/*     */       
/* 440 */       set.visitInsn(89);
/* 441 */       set.visitConstant(index);
/*     */ 
/*     */       
/* 444 */       set.visitVarInsn(25, 1);
/* 445 */       set.visitConstant(index);
/* 446 */       set.visitInsn(50);
/*     */ 
/*     */       
/* 449 */       String boxingType = Bytecode.getBoxingType(args[index]);
/* 450 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : args[index].getInternalName());
/*     */ 
/*     */       
/* 453 */       if (boxingType != null) {
/* 454 */         set.visitInsn(89);
/* 455 */         set.visitJumpInsn(198, nullPrimitive);
/* 456 */         maxStack = 7;
/*     */       } 
/*     */ 
/*     */       
/* 460 */       set.visitInsn(83);
/*     */     } 
/*     */     
/* 463 */     set.visitInsn(177);
/*     */     
/* 465 */     set.visitLabel(nullPrimitive);
/* 466 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/* 467 */     set.visitInsn(177);
/*     */     
/* 469 */     set.visitMaxs(maxStack, 2);
/* 470 */     set.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwNPE(MethodVisitorEx method, String message) {
/* 477 */     method.visitTypeInsn(187, "java/lang/NullPointerException");
/* 478 */     method.visitInsn(89);
/* 479 */     method.visitLdcInsn(message);
/* 480 */     method.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
/* 481 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwAIOOBE(MethodVisitorEx method, int arg) {
/* 489 */     method.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
/* 490 */     method.visitInsn(89);
/* 491 */     method.visitVarInsn(21, arg);
/* 492 */     method.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
/* 493 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void box(MethodVisitor method, Type var) {
/* 504 */     String boxingType = Bytecode.getBoxingType(var);
/* 505 */     if (boxingType != null) {
/* 506 */       String desc = String.format("(%s)L%s;", new Object[] { var.getDescriptor(), boxingType });
/* 507 */       method.visitMethodInsn(184, boxingType, "valueOf", desc, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void unbox(MethodVisitor method, Type var) {
/* 518 */     String boxingType = Bytecode.getBoxingType(var);
/* 519 */     if (boxingType != null) {
/* 520 */       String unboxingMethod = Bytecode.getUnboxingMethod(var);
/* 521 */       String desc = "()" + var.getDescriptor();
/* 522 */       method.visitTypeInsn(192, boxingType);
/* 523 */       method.visitMethodInsn(182, boxingType, unboxingMethod, desc, false);
/*     */     } else {
/* 525 */       method.visitTypeInsn(192, var.getInternalName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getSignature(Type[] args) {
/* 530 */     return (new SignaturePrinter("", null, args)).setFullyQualified(true).getFormattedArgs();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\invoke\arg\ArgsClassGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */