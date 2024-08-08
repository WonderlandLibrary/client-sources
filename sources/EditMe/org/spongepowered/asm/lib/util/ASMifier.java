package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class ASMifier extends Printer {
   protected final String name;
   protected final int id;
   protected Map labelNames;
   private static final int ACCESS_CLASS = 262144;
   private static final int ACCESS_FIELD = 524288;
   private static final int ACCESS_INNER = 1048576;

   public ASMifier() {
      this(327680, "cw", 0);
      if (this.getClass() != ASMifier.class) {
         throw new IllegalStateException();
      }
   }

   protected ASMifier(int var1, String var2, int var3) {
      super(var1);
      this.name = var2;
      this.id = var3;
   }

   public static void main(String[] var0) throws Exception {
      byte var1 = 0;
      byte var2 = 2;
      boolean var3 = true;
      if (var0.length < 1 || var0.length > 2) {
         var3 = false;
      }

      if (var3 && "-debug".equals(var0[0])) {
         var1 = 1;
         var2 = 0;
         if (var0.length != 2) {
            var3 = false;
         }
      }

      if (!var3) {
         System.err.println("Prints the ASM code to generate the given class.");
         System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
      } else {
         ClassReader var4;
         if (!var0[var1].endsWith(".class") && var0[var1].indexOf(92) <= -1 && var0[var1].indexOf(47) <= -1) {
            var4 = new ClassReader(var0[var1]);
         } else {
            var4 = new ClassReader(new FileInputStream(var0[var1]));
         }

         var4.accept(new TraceClassVisitor((ClassVisitor)null, new ASMifier(), new PrintWriter(System.out)), var2);
      }
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      int var7 = var3.lastIndexOf(47);
      String var8;
      if (var7 == -1) {
         var8 = var3;
      } else {
         this.text.add("package asm." + var3.substring(0, var7).replace('/', '.') + ";\n");
         var8 = var3.substring(var7 + 1);
      }

      this.text.add("import java.util.*;\n");
      this.text.add("import org.objectweb.asm.*;\n");
      this.text.add("public class " + var8 + "Dump implements Opcodes {\n\n");
      this.text.add("public static byte[] dump () throws Exception {\n\n");
      this.text.add("ClassWriter cw = new ClassWriter(0);\n");
      this.text.add("FieldVisitor fv;\n");
      this.text.add("MethodVisitor mv;\n");
      this.text.add("AnnotationVisitor av0;\n\n");
      this.buf.setLength(0);
      this.buf.append("cw.visit(");
      switch(var1) {
      case 46:
         this.buf.append("V1_2");
         break;
      case 47:
         this.buf.append("V1_3");
         break;
      case 48:
         this.buf.append("V1_4");
         break;
      case 49:
         this.buf.append("V1_5");
         break;
      case 50:
         this.buf.append("V1_6");
         break;
      case 51:
         this.buf.append("V1_7");
         break;
      case 196653:
         this.buf.append("V1_1");
         break;
      default:
         this.buf.append(var1);
      }

      this.buf.append(", ");
      this.appendAccess(var2 | 262144);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(", ");
      this.appendConstant(var5);
      this.buf.append(", ");
      if (var6 != null && var6.length > 0) {
         this.buf.append("new String[] {");

         for(int var9 = 0; var9 < var6.length; ++var9) {
            this.buf.append(var9 == 0 ? " " : ", ");
            this.appendConstant(var6[var9]);
         }

         this.buf.append(" }");
      } else {
         this.buf.append("null");
      }

      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public void visitSource(String var1, String var2) {
      this.buf.setLength(0);
      this.buf.append("cw.visitSource(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.buf.append("cw.visitOuterClass(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitClassAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitClassAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.buf.setLength(0);
      this.buf.append("cw.visitInnerClass(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendAccess(var4 | 1048576);
      this.buf.append(");\n\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitField(int var1, String var2, String var3, String var4, Object var5) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("fv = cw.visitField(");
      this.appendAccess(var1 | 524288);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(", ");
      this.appendConstant(var5);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var6 = this.createASMifier("fv", 0);
      this.text.add(var6.getText());
      this.text.add("}\n");
      return var6;
   }

   public ASMifier visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("mv = cw.visitMethod(");
      this.appendAccess(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(", ");
      if (var5 != null && var5.length > 0) {
         this.buf.append("new String[] {");

         for(int var6 = 0; var6 < var5.length; ++var6) {
            this.buf.append(var6 == 0 ? " " : ", ");
            this.appendConstant(var5[var6]);
         }

         this.buf.append(" }");
      } else {
         this.buf.append("null");
      }

      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var7 = this.createASMifier("mv", 0);
      this.text.add(var7.getText());
      this.text.add("}\n");
      return var7;
   }

   public void visitClassEnd() {
      this.text.add("cw.visitEnd();\n\n");
      this.text.add("return cw.toByteArray();\n");
      this.text.add("}\n");
      this.text.add("}\n");
   }

   public void visit(String var1, Object var2) {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visit(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visitEnum(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(", ");
      appendConstant(this.buf, var3);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitAnnotation(String var1, String var2) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
      this.buf.append(this.id).append(".visitAnnotation(");
      appendConstant(this.buf, var1);
      this.buf.append(", ");
      appendConstant(this.buf, var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var3 = this.createASMifier("av", this.id + 1);
      this.text.add(var3.getText());
      this.text.add("}\n");
      return var3;
   }

   public ASMifier visitArray(String var1) {
      this.buf.setLength(0);
      this.buf.append("{\n");
      this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
      this.buf.append(this.id).append(".visitArray(");
      appendConstant(this.buf, var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var2 = this.createASMifier("av", this.id + 1);
      this.text.add(var2.getText());
      this.text.add("}\n");
      return var2;
   }

   public void visitAnnotationEnd() {
      this.buf.setLength(0);
      this.buf.append("av").append(this.id).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitFieldAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitFieldAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitFieldEnd() {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public void visitParameter(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitParameter(");
      appendString(this.buf, var1);
      this.buf.append(", ");
      this.appendAccess(var2);
      this.text.add(this.buf.append(");\n").toString());
   }

   public ASMifier visitAnnotationDefault() {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
      this.text.add(this.buf.toString());
      ASMifier var1 = this.createASMifier("av", 0);
      this.text.add(var1.getText());
      this.text.add("}\n");
      return var1;
   }

   public ASMifier visitMethodAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public ASMifier visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public ASMifier visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(var1).append(", ");
      this.appendConstant(var2);
      this.buf.append(", ").append(var3).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var4 = this.createASMifier("av", 0);
      this.text.add(var4.getText());
      this.text.add("}\n");
      return var4;
   }

   public void visitMethodAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitCode() {
      this.text.add(this.name + ".visitCode();\n");
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      this.buf.setLength(0);
      switch(var1) {
      case -1:
      case 0:
         this.declareFrameTypes(var2, var3);
         this.declareFrameTypes(var4, var5);
         if (var1 == -1) {
            this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
         } else {
            this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
         }

         this.buf.append(var2).append(", new Object[] {");
         this.appendFrameTypes(var2, var3);
         this.buf.append("}, ").append(var4).append(", new Object[] {");
         this.appendFrameTypes(var4, var5);
         this.buf.append('}');
         break;
      case 1:
         this.declareFrameTypes(var2, var3);
         this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(var2).append(", new Object[] {");
         this.appendFrameTypes(var2, var3);
         this.buf.append("}, 0, null");
         break;
      case 2:
         this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(var2).append(", null, 0, null");
         break;
      case 3:
         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
         break;
      case 4:
         this.declareFrameTypes(1, var5);
         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
         this.appendFrameTypes(1, var5);
         this.buf.append('}');
      }

      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitInsn(int var1) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitInsn(").append(OPCODES[var1]).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitIntInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitIntInsn(").append(OPCODES[var1]).append(", ").append(var1 == 188 ? TYPES[var2] : Integer.toString(var2)).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitVarInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[var1]).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitTypeInsn(int var1, String var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[var1]).append(", ");
      this.appendConstant(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitFieldInsn(").append(OPCODES[var1]).append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var1 == 185);
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitMethodInsn(").append(OPCODES[var1]).append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(", ");
      this.buf.append(var5 ? "true" : "false");
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", new Object[]{");

      for(int var5 = 0; var5 < var4.length; ++var5) {
         this.appendConstant(var4[var5]);
         if (var5 != var4.length - 1) {
            this.buf.append(", ");
         }
      }

      this.buf.append("});\n");
      this.text.add(this.buf.toString());
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.buf.setLength(0);
      this.declareLabel(var2);
      this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[var1]).append(", ");
      this.appendLabel(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitLabel(Label var1) {
      this.buf.setLength(0);
      this.declareLabel(var1);
      this.buf.append(this.name).append(".visitLabel(");
      this.appendLabel(var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitLdcInsn(Object var1) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLdcInsn(");
      this.appendConstant(var1);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitIincInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitIincInsn(").append(var1).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.buf.setLength(0);

      int var5;
      for(var5 = 0; var5 < var4.length; ++var5) {
         this.declareLabel(var4[var5]);
      }

      this.declareLabel(var3);
      this.buf.append(this.name).append(".visitTableSwitchInsn(").append(var1).append(", ").append(var2).append(", ");
      this.appendLabel(var3);
      this.buf.append(", new Label[] {");

      for(var5 = 0; var5 < var4.length; ++var5) {
         this.buf.append(var5 == 0 ? " " : ", ");
         this.appendLabel(var4[var5]);
      }

      this.buf.append(" });\n");
      this.text.add(this.buf.toString());
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.buf.setLength(0);

      int var4;
      for(var4 = 0; var4 < var3.length; ++var4) {
         this.declareLabel(var3[var4]);
      }

      this.declareLabel(var1);
      this.buf.append(this.name).append(".visitLookupSwitchInsn(");
      this.appendLabel(var1);
      this.buf.append(", new int[] {");

      for(var4 = 0; var4 < var2.length; ++var4) {
         this.buf.append(var4 == 0 ? " " : ", ").append(var2[var4]);
      }

      this.buf.append(" }, new Label[] {");

      for(var4 = 0; var4 < var3.length; ++var4) {
         this.buf.append(var4 == 0 ? " " : ", ");
         this.appendLabel(var3[var4]);
      }

      this.buf.append(" });\n");
      this.text.add(this.buf.toString());
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
      this.appendConstant(var1);
      this.buf.append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitInsnAnnotation", var1, var2, var3, var4);
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.buf.setLength(0);
      this.declareLabel(var1);
      this.declareLabel(var2);
      this.declareLabel(var3);
      this.buf.append(this.name).append(".visitTryCatchBlock(");
      this.appendLabel(var1);
      this.buf.append(", ");
      this.appendLabel(var2);
      this.buf.append(", ");
      this.appendLabel(var3);
      this.buf.append(", ");
      this.appendConstant(var4);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitTryCatchAnnotation", var1, var2, var3, var4);
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLocalVariable(");
      this.appendConstant(var1);
      this.buf.append(", ");
      this.appendConstant(var2);
      this.buf.append(", ");
      this.appendConstant(var3);
      this.buf.append(", ");
      this.appendLabel(var4);
      this.buf.append(", ");
      this.appendLabel(var5);
      this.buf.append(", ").append(var6).append(");\n");
      this.text.add(this.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitLocalVariableAnnotation(");
      this.buf.append(var1);
      if (var2 == null) {
         this.buf.append(", null, ");
      } else {
         this.buf.append(", TypePath.fromString(\"").append(var2).append("\"), ");
      }

      this.buf.append("new Label[] {");

      int var8;
      for(var8 = 0; var8 < var3.length; ++var8) {
         this.buf.append(var8 == 0 ? " " : ", ");
         this.appendLabel(var3[var8]);
      }

      this.buf.append(" }, new Label[] {");

      for(var8 = 0; var8 < var4.length; ++var8) {
         this.buf.append(var8 == 0 ? " " : ", ");
         this.appendLabel(var4[var8]);
      }

      this.buf.append(" }, new int[] {");

      for(var8 = 0; var8 < var5.length; ++var8) {
         this.buf.append(var8 == 0 ? " " : ", ").append(var5[var8]);
      }

      this.buf.append(" }, ");
      this.appendConstant(var6);
      this.buf.append(", ").append(var7).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var9 = this.createASMifier("av", 0);
      this.text.add(var9.getText());
      this.text.add("}\n");
      return var9;
   }

   public void visitLineNumber(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitLineNumber(").append(var1).append(", ");
      this.appendLabel(var2);
      this.buf.append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitMaxs(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitMaxs(").append(var1).append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
   }

   public void visitMethodEnd() {
      this.buf.setLength(0);
      this.buf.append(this.name).append(".visitEnd();\n");
      this.text.add(this.buf.toString());
   }

   public ASMifier visitAnnotation(String var1, boolean var2) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
      this.appendConstant(var1);
      this.buf.append(", ").append(var2).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var3 = this.createASMifier("av", 0);
      this.text.add(var3.getText());
      this.text.add("}\n");
      return var3;
   }

   public ASMifier visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation("visitTypeAnnotation", var1, var2, var3, var4);
   }

   public ASMifier visitTypeAnnotation(String var1, int var2, TypePath var3, String var4, boolean var5) {
      this.buf.setLength(0);
      this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(var1).append("(");
      this.buf.append(var2);
      if (var3 == null) {
         this.buf.append(", null, ");
      } else {
         this.buf.append(", TypePath.fromString(\"").append(var3).append("\"), ");
      }

      this.appendConstant(var4);
      this.buf.append(", ").append(var5).append(");\n");
      this.text.add(this.buf.toString());
      ASMifier var6 = this.createASMifier("av", 0);
      this.text.add(var6.getText());
      this.text.add("}\n");
      return var6;
   }

   public void visitAttribute(Attribute var1) {
      this.buf.setLength(0);
      this.buf.append("// ATTRIBUTE ").append(var1.type).append('\n');
      if (var1 instanceof ASMifiable) {
         if (this.labelNames == null) {
            this.labelNames = new HashMap();
         }

         this.buf.append("{\n");
         ((ASMifiable)var1).asmify(this.buf, "attr", this.labelNames);
         this.buf.append(this.name).append(".visitAttribute(attr);\n");
         this.buf.append("}\n");
      }

      this.text.add(this.buf.toString());
   }

   protected ASMifier createASMifier(String var1, int var2) {
      return new ASMifier(327680, var1, var2);
   }

   void appendAccess(int var1) {
      boolean var2 = true;
      if ((var1 & 1) != 0) {
         this.buf.append("ACC_PUBLIC");
         var2 = false;
      }

      if ((var1 & 2) != 0) {
         this.buf.append("ACC_PRIVATE");
         var2 = false;
      }

      if ((var1 & 4) != 0) {
         this.buf.append("ACC_PROTECTED");
         var2 = false;
      }

      if ((var1 & 16) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_FINAL");
         var2 = false;
      }

      if ((var1 & 8) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_STATIC");
         var2 = false;
      }

      if ((var1 & 32) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         if ((var1 & 262144) == 0) {
            this.buf.append("ACC_SYNCHRONIZED");
         } else {
            this.buf.append("ACC_SUPER");
         }

         var2 = false;
      }

      if ((var1 & 64) != 0 && (var1 & 524288) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_VOLATILE");
         var2 = false;
      }

      if ((var1 & 64) != 0 && (var1 & 262144) == 0 && (var1 & 524288) == 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_BRIDGE");
         var2 = false;
      }

      if ((var1 & 128) != 0 && (var1 & 262144) == 0 && (var1 & 524288) == 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_VARARGS");
         var2 = false;
      }

      if ((var1 & 128) != 0 && (var1 & 524288) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_TRANSIENT");
         var2 = false;
      }

      if ((var1 & 256) != 0 && (var1 & 262144) == 0 && (var1 & 524288) == 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_NATIVE");
         var2 = false;
      }

      if ((var1 & 16384) != 0 && ((var1 & 262144) != 0 || (var1 & 524288) != 0 || (var1 & 1048576) != 0)) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_ENUM");
         var2 = false;
      }

      if ((var1 & 8192) != 0 && ((var1 & 262144) != 0 || (var1 & 1048576) != 0)) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_ANNOTATION");
         var2 = false;
      }

      if ((var1 & 1024) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_ABSTRACT");
         var2 = false;
      }

      if ((var1 & 512) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_INTERFACE");
         var2 = false;
      }

      if ((var1 & 2048) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_STRICT");
         var2 = false;
      }

      if ((var1 & 4096) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_SYNTHETIC");
         var2 = false;
      }

      if ((var1 & 131072) != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_DEPRECATED");
         var2 = false;
      }

      if ((var1 & '耀') != 0) {
         if (!var2) {
            this.buf.append(" + ");
         }

         this.buf.append("ACC_MANDATED");
         var2 = false;
      }

      if (var2) {
         this.buf.append('0');
      }

   }

   protected void appendConstant(Object var1) {
      appendConstant(this.buf, var1);
   }

   static void appendConstant(StringBuffer var0, Object var1) {
      if (var1 == null) {
         var0.append("null");
      } else if (var1 instanceof String) {
         appendString(var0, (String)var1);
      } else if (var1 instanceof Type) {
         var0.append("Type.getType(\"");
         var0.append(((Type)var1).getDescriptor());
         var0.append("\")");
      } else if (var1 instanceof Handle) {
         var0.append("new Handle(");
         Handle var2 = (Handle)var1;
         var0.append("Opcodes.").append(HANDLE_TAG[var2.getTag()]).append(", \"");
         var0.append(var2.getOwner()).append("\", \"");
         var0.append(var2.getName()).append("\", \"");
         var0.append(var2.getDesc()).append("\")");
      } else if (var1 instanceof Byte) {
         var0.append("new Byte((byte)").append(var1).append(')');
      } else if (var1 instanceof Boolean) {
         var0.append((Boolean)var1 ? "Boolean.TRUE" : "Boolean.FALSE");
      } else if (var1 instanceof Short) {
         var0.append("new Short((short)").append(var1).append(')');
      } else if (var1 instanceof Character) {
         char var4 = (Character)var1;
         var0.append("new Character((char)").append(var4).append(')');
      } else if (var1 instanceof Integer) {
         var0.append("new Integer(").append(var1).append(')');
      } else if (var1 instanceof Float) {
         var0.append("new Float(\"").append(var1).append("\")");
      } else if (var1 instanceof Long) {
         var0.append("new Long(").append(var1).append("L)");
      } else if (var1 instanceof Double) {
         var0.append("new Double(\"").append(var1).append("\")");
      } else {
         int var3;
         if (var1 instanceof byte[]) {
            byte[] var5 = (byte[])((byte[])var1);
            var0.append("new byte[] {");

            for(var3 = 0; var3 < var5.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var5[var3]);
            }

            var0.append('}');
         } else if (var1 instanceof boolean[]) {
            boolean[] var6 = (boolean[])((boolean[])var1);
            var0.append("new boolean[] {");

            for(var3 = 0; var3 < var6.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var6[var3]);
            }

            var0.append('}');
         } else if (var1 instanceof short[]) {
            short[] var7 = (short[])((short[])var1);
            var0.append("new short[] {");

            for(var3 = 0; var3 < var7.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append("(short)").append(var7[var3]);
            }

            var0.append('}');
         } else if (var1 instanceof char[]) {
            char[] var8 = (char[])((char[])var1);
            var0.append("new char[] {");

            for(var3 = 0; var3 < var8.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append("(char)").append(var8[var3]);
            }

            var0.append('}');
         } else if (var1 instanceof int[]) {
            int[] var9 = (int[])((int[])var1);
            var0.append("new int[] {");

            for(var3 = 0; var3 < var9.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var9[var3]);
            }

            var0.append('}');
         } else if (var1 instanceof long[]) {
            long[] var10 = (long[])((long[])var1);
            var0.append("new long[] {");

            for(var3 = 0; var3 < var10.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var10[var3]).append('L');
            }

            var0.append('}');
         } else if (var1 instanceof float[]) {
            float[] var11 = (float[])((float[])var1);
            var0.append("new float[] {");

            for(var3 = 0; var3 < var11.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var11[var3]).append('f');
            }

            var0.append('}');
         } else if (var1 instanceof double[]) {
            double[] var12 = (double[])((double[])var1);
            var0.append("new double[] {");

            for(var3 = 0; var3 < var12.length; ++var3) {
               var0.append(var3 == 0 ? "" : ",").append(var12[var3]).append('d');
            }

            var0.append('}');
         }
      }

   }

   private void declareFrameTypes(int var1, Object[] var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         if (var2[var3] instanceof Label) {
            this.declareLabel((Label)var2[var3]);
         }
      }

   }

   private void appendFrameTypes(int var1, Object[] var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         if (var3 > 0) {
            this.buf.append(", ");
         }

         if (var2[var3] instanceof String) {
            this.appendConstant(var2[var3]);
         } else if (var2[var3] instanceof Integer) {
            switch((Integer)var2[var3]) {
            case 0:
               this.buf.append("Opcodes.TOP");
               break;
            case 1:
               this.buf.append("Opcodes.INTEGER");
               break;
            case 2:
               this.buf.append("Opcodes.FLOAT");
               break;
            case 3:
               this.buf.append("Opcodes.DOUBLE");
               break;
            case 4:
               this.buf.append("Opcodes.LONG");
               break;
            case 5:
               this.buf.append("Opcodes.NULL");
               break;
            case 6:
               this.buf.append("Opcodes.UNINITIALIZED_THIS");
            }
         } else {
            this.appendLabel((Label)var2[var3]);
         }
      }

   }

   protected void declareLabel(Label var1) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap();
      }

      String var2 = (String)this.labelNames.get(var1);
      if (var2 == null) {
         var2 = "l" + this.labelNames.size();
         this.labelNames.put(var1, var2);
         this.buf.append("Label ").append(var2).append(" = new Label();\n");
      }

   }

   protected void appendLabel(Label var1) {
      this.buf.append((String)this.labelNames.get(var1));
   }

   public Printer visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTryCatchAnnotation(var1, var2, var3, var4);
   }

   public Printer visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitInsnAnnotation(var1, var2, var3, var4);
   }

   public Printer visitParameterAnnotation(int var1, String var2, boolean var3) {
      return this.visitParameterAnnotation(var1, var2, var3);
   }

   public Printer visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitMethodTypeAnnotation(var1, var2, var3, var4);
   }

   public Printer visitMethodAnnotation(String var1, boolean var2) {
      return this.visitMethodAnnotation(var1, var2);
   }

   public Printer visitAnnotationDefault() {
      return this.visitAnnotationDefault();
   }

   public Printer visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitFieldTypeAnnotation(var1, var2, var3, var4);
   }

   public Printer visitFieldAnnotation(String var1, boolean var2) {
      return this.visitFieldAnnotation(var1, var2);
   }

   public Printer visitArray(String var1) {
      return this.visitArray(var1);
   }

   public Printer visitAnnotation(String var1, String var2) {
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return this.visitMethod(var1, var2, var3, var4, var5);
   }

   public Printer visitField(int var1, String var2, String var3, String var4, Object var5) {
      return this.visitField(var1, var2, var3, var4, var5);
   }

   public Printer visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitClassTypeAnnotation(var1, var2, var3, var4);
   }

   public Printer visitClassAnnotation(String var1, boolean var2) {
      return this.visitClassAnnotation(var1, var2);
   }
}
