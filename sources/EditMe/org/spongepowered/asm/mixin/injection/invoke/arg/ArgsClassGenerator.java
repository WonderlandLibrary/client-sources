package org.spongepowered.asm.mixin.injection.invoke.arg;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.MethodVisitorEx;

public final class ArgsClassGenerator implements IClassGenerator {
   public static final String ARGS_NAME = Args.class.getName();
   public static final String ARGS_REF;
   public static final String GETTER_PREFIX = "$";
   private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
   private static final String OBJECT = "java/lang/Object";
   private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
   private static final String VALUES_FIELD = "values";
   private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
   private static final String SET = "set";
   private static final String SET_DESC = "(ILjava/lang/Object;)V";
   private static final String SETALL = "setAll";
   private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
   private static final String NPE = "java/lang/NullPointerException";
   private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
   private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
   private static final String AIOOBE_CTOR_DESC = "(I)V";
   private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
   private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
   private int nextIndex = 1;
   private final BiMap classNames = HashBiMap.create();
   private final Map classBytes = new HashMap();

   public String getClassName(String var1) {
      if (!var1.endsWith(")V")) {
         throw new IllegalArgumentException("Invalid @ModifyArgs method descriptor");
      } else {
         String var2 = (String)this.classNames.get(var1);
         if (var2 == null) {
            var2 = String.format("%s%d", "org.spongepowered.asm.synthetic.args.Args$", this.nextIndex++);
            this.classNames.put(var1, var2);
         }

         return var2;
      }
   }

   public String getClassRef(String var1) {
      return this.getClassName(var1).replace('.', '/');
   }

   public byte[] generate(String var1) {
      return this.getBytes(var1);
   }

   public byte[] getBytes(String var1) {
      byte[] var2 = (byte[])this.classBytes.get(var1);
      if (var2 == null) {
         String var3 = (String)this.classNames.inverse().get(var1);
         if (var3 == null) {
            return null;
         }

         var2 = this.generateClass(var1, var3);
         this.classBytes.put(var1, var2);
      }

      return var2;
   }

   private byte[] generateClass(String var1, String var2) {
      String var3 = var1.replace('.', '/');
      Type[] var4 = Type.getArgumentTypes(var2);
      ClassWriter var5 = new ClassWriter(2);
      Object var6 = var5;
      if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
         var6 = new CheckClassAdapter(var5);
      }

      ((ClassVisitor)var6).visit(50, 4129, var3, (String)null, ARGS_REF, (String[])null);
      ((ClassVisitor)var6).visitSource(var1.substring(var1.lastIndexOf(46) + 1) + ".java", (String)null);
      this.generateCtor(var3, var2, var4, (ClassVisitor)var6);
      this.generateToString(var3, var2, var4, (ClassVisitor)var6);
      this.generateFactory(var3, var2, var4, (ClassVisitor)var6);
      this.generateSetters(var3, var2, var4, (ClassVisitor)var6);
      this.generateGetters(var3, var2, var4, (ClassVisitor)var6);
      ((ClassVisitor)var6).visitEnd();
      return var5.toByteArray();
   }

   private void generateCtor(String var1, String var2, Type[] var3, ClassVisitor var4) {
      MethodVisitor var5 = var4.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", (String)null, (String[])null);
      var5.visitCode();
      var5.visitVarInsn(25, 0);
      var5.visitVarInsn(25, 1);
      var5.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
      var5.visitInsn(177);
      var5.visitMaxs(2, 2);
      var5.visitEnd();
   }

   private void generateToString(String var1, String var2, Type[] var3, ClassVisitor var4) {
      MethodVisitor var5 = var4.visitMethod(1, "toString", "()Ljava/lang/String;", (String)null, (String[])null);
      var5.visitCode();
      var5.visitLdcInsn("Args" + getSignature(var3));
      var5.visitInsn(176);
      var5.visitMaxs(1, 1);
      var5.visitEnd();
   }

   private void generateFactory(String var1, String var2, Type[] var3, ClassVisitor var4) {
      String var5 = Bytecode.changeDescriptorReturnType(var2, "L" + var1 + ";");
      MethodVisitorEx var6 = new MethodVisitorEx(var4.visitMethod(9, "of", var5, (String)null, (String[])null));
      var6.visitCode();
      var6.visitTypeInsn(187, var1);
      var6.visitInsn(89);
      var6.visitConstant((byte)var3.length);
      var6.visitTypeInsn(189, "java/lang/Object");
      byte var7 = 0;
      Type[] var8 = var3;
      int var9 = var3.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         Type var11 = var8[var10];
         var6.visitInsn(89);
         var6.visitConstant(var7);
         var6.visitVarInsn(var11.getOpcode(21), var7++);
         box(var6, var11);
         var6.visitInsn(83);
      }

      var6.visitMethodInsn(183, var1, "<init>", "([Ljava/lang/Object;)V", false);
      var6.visitInsn(176);
      var6.visitMaxs(6, Bytecode.getArgsSize(var3));
      var6.visitEnd();
   }

   private void generateGetters(String var1, String var2, Type[] var3, ClassVisitor var4) {
      byte var5 = 0;
      Type[] var6 = var3;
      int var7 = var3.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Type var9 = var6[var8];
         String var10 = "$" + var5;
         String var11 = "()" + var9.getDescriptor();
         MethodVisitorEx var12 = new MethodVisitorEx(var4.visitMethod(1, var10, var11, (String)null, (String[])null));
         var12.visitCode();
         var12.visitVarInsn(25, 0);
         var12.visitFieldInsn(180, var1, "values", "[Ljava/lang/Object;");
         var12.visitConstant(var5);
         var12.visitInsn(50);
         unbox(var12, var9);
         var12.visitInsn(var9.getOpcode(172));
         var12.visitMaxs(2, 1);
         var12.visitEnd();
         ++var5;
      }

   }

   private void generateSetters(String var1, String var2, Type[] var3, ClassVisitor var4) {
      this.generateIndexedSetter(var1, var2, var3, var4);
      this.generateMultiSetter(var1, var2, var3, var4);
   }

   private void generateIndexedSetter(String var1, String var2, Type[] var3, ClassVisitor var4) {
      MethodVisitorEx var5 = new MethodVisitorEx(var4.visitMethod(1, "set", "(ILjava/lang/Object;)V", (String)null, (String[])null));
      var5.visitCode();
      Label var6 = new Label();
      Label var7 = new Label();
      Label[] var8 = new Label[var3.length];

      int var9;
      for(var9 = 0; var9 < var8.length; ++var9) {
         var8[var9] = new Label();
      }

      var5.visitVarInsn(25, 0);
      var5.visitFieldInsn(180, var1, "values", "[Ljava/lang/Object;");

      for(byte var11 = 0; var11 < var3.length; ++var11) {
         var5.visitVarInsn(21, 1);
         var5.visitConstant(var11);
         var5.visitJumpInsn(159, var8[var11]);
      }

      throwAIOOBE(var5, 1);

      for(var9 = 0; var9 < var3.length; ++var9) {
         String var10 = Bytecode.getBoxingType(var3[var9]);
         var5.visitLabel(var8[var9]);
         var5.visitVarInsn(21, 1);
         var5.visitVarInsn(25, 2);
         var5.visitTypeInsn(192, var10 != null ? var10 : var3[var9].getInternalName());
         var5.visitJumpInsn(167, var10 != null ? var7 : var6);
      }

      var5.visitLabel(var7);
      var5.visitInsn(89);
      var5.visitJumpInsn(199, var6);
      throwNPE(var5, "Argument with primitive type cannot be set to NULL");
      var5.visitLabel(var6);
      var5.visitInsn(83);
      var5.visitInsn(177);
      var5.visitMaxs(6, 3);
      var5.visitEnd();
   }

   private void generateMultiSetter(String var1, String var2, Type[] var3, ClassVisitor var4) {
      MethodVisitorEx var5 = new MethodVisitorEx(var4.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", (String)null, (String[])null));
      var5.visitCode();
      Label var6 = new Label();
      Label var7 = new Label();
      byte var8 = 6;
      var5.visitVarInsn(25, 1);
      var5.visitInsn(190);
      var5.visitInsn(89);
      var5.visitConstant((byte)var3.length);
      var5.visitJumpInsn(159, var6);
      var5.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
      var5.visitInsn(89);
      var5.visitInsn(93);
      var5.visitInsn(88);
      var5.visitConstant((byte)var3.length);
      var5.visitLdcInsn(getSignature(var3));
      var5.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
      var5.visitInsn(191);
      var5.visitLabel(var6);
      var5.visitInsn(87);
      var5.visitVarInsn(25, 0);
      var5.visitFieldInsn(180, var1, "values", "[Ljava/lang/Object;");

      for(byte var9 = 0; var9 < var3.length; ++var9) {
         var5.visitInsn(89);
         var5.visitConstant(var9);
         var5.visitVarInsn(25, 1);
         var5.visitConstant(var9);
         var5.visitInsn(50);
         String var10 = Bytecode.getBoxingType(var3[var9]);
         var5.visitTypeInsn(192, var10 != null ? var10 : var3[var9].getInternalName());
         if (var10 != null) {
            var5.visitInsn(89);
            var5.visitJumpInsn(198, var7);
            var8 = 7;
         }

         var5.visitInsn(83);
      }

      var5.visitInsn(177);
      var5.visitLabel(var7);
      throwNPE(var5, "Argument with primitive type cannot be set to NULL");
      var5.visitInsn(177);
      var5.visitMaxs(var8, 2);
      var5.visitEnd();
   }

   private static void throwNPE(MethodVisitorEx var0, String var1) {
      var0.visitTypeInsn(187, "java/lang/NullPointerException");
      var0.visitInsn(89);
      var0.visitLdcInsn(var1);
      var0.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
      var0.visitInsn(191);
   }

   private static void throwAIOOBE(MethodVisitorEx var0, int var1) {
      var0.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
      var0.visitInsn(89);
      var0.visitVarInsn(21, var1);
      var0.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
      var0.visitInsn(191);
   }

   private static void box(MethodVisitor var0, Type var1) {
      String var2 = Bytecode.getBoxingType(var1);
      if (var2 != null) {
         String var3 = String.format("(%s)L%s;", var1.getDescriptor(), var2);
         var0.visitMethodInsn(184, var2, "valueOf", var3, false);
      }

   }

   private static void unbox(MethodVisitor var0, Type var1) {
      String var2 = Bytecode.getBoxingType(var1);
      if (var2 != null) {
         String var3 = Bytecode.getUnboxingMethod(var1);
         String var4 = "()" + var1.getDescriptor();
         var0.visitTypeInsn(192, var2);
         var0.visitMethodInsn(182, var2, var3, var4, false);
      } else {
         var0.visitTypeInsn(192, var1.getInternalName());
      }

   }

   private static String getSignature(Type[] var0) {
      return (new SignaturePrinter("", (Type)null, var0)).setFullyQualified(true).getFormattedArgs();
   }

   static {
      ARGS_REF = ARGS_NAME.replace('.', '/');
   }
}
