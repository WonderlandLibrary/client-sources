package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;

public class Textifier extends Printer {
   public static final int INTERNAL_NAME = 0;
   public static final int FIELD_DESCRIPTOR = 1;
   public static final int FIELD_SIGNATURE = 2;
   public static final int METHOD_DESCRIPTOR = 3;
   public static final int METHOD_SIGNATURE = 4;
   public static final int CLASS_SIGNATURE = 5;
   public static final int TYPE_DECLARATION = 6;
   public static final int CLASS_DECLARATION = 7;
   public static final int PARAMETERS_DECLARATION = 8;
   public static final int HANDLE_DESCRIPTOR = 9;
   protected String tab;
   protected String tab2;
   protected String tab3;
   protected String ltab;
   protected Map labelNames;
   private int access;
   private int valueNumber;

   public Textifier() {
      this(327680);
      if (this.getClass() != Textifier.class) {
         throw new IllegalStateException();
      }
   }

   protected Textifier(int var1) {
      super(var1);
      this.tab = "  ";
      this.tab2 = "    ";
      this.tab3 = "      ";
      this.ltab = "   ";
      this.valueNumber = 0;
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
         System.err.println("Prints a disassembled view of the given class.");
         System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
      } else {
         ClassReader var4;
         if (!var0[var1].endsWith(".class") && var0[var1].indexOf(92) <= -1 && var0[var1].indexOf(47) <= -1) {
            var4 = new ClassReader(var0[var1]);
         } else {
            var4 = new ClassReader(new FileInputStream(var0[var1]));
         }

         var4.accept(new TraceClassVisitor(new PrintWriter(System.out)), var2);
      }
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.access = var2;
      int var7 = var1 & '\uffff';
      int var8 = var1 >>> 16;
      this.buf.setLength(0);
      this.buf.append("// class version ").append(var7).append('.').append(var8).append(" (").append(var1).append(")\n");
      if ((var2 & 131072) != 0) {
         this.buf.append("// DEPRECATED\n");
      }

      this.buf.append("// access flags 0x").append(Integer.toHexString(var2).toUpperCase()).append('\n');
      this.appendDescriptor(5, var4);
      if (var4 != null) {
         TraceSignatureVisitor var9 = new TraceSignatureVisitor(var2);
         SignatureReader var10 = new SignatureReader(var4);
         var10.accept(var9);
         this.buf.append("// declaration: ").append(var3).append(var9.getDeclaration()).append('\n');
      }

      this.appendAccess(var2 & -33);
      if ((var2 & 8192) != 0) {
         this.buf.append("@interface ");
      } else if ((var2 & 512) != 0) {
         this.buf.append("interface ");
      } else if ((var2 & 16384) == 0) {
         this.buf.append("class ");
      }

      this.appendDescriptor(0, var3);
      if (var5 != null && !"java/lang/Object".equals(var5)) {
         this.buf.append(" extends ");
         this.appendDescriptor(0, var5);
         this.buf.append(' ');
      }

      if (var6 != null && var6.length > 0) {
         this.buf.append(" implements ");

         for(int var11 = 0; var11 < var6.length; ++var11) {
            this.appendDescriptor(0, var6[var11]);
            this.buf.append(' ');
         }
      }

      this.buf.append(" {\n\n");
      this.text.add(this.buf.toString());
   }

   public void visitSource(String var1, String var2) {
      this.buf.setLength(0);
      if (var1 != null) {
         this.buf.append(this.tab).append("// compiled from: ").append(var1).append('\n');
      }

      if (var2 != null) {
         this.buf.append(this.tab).append("// debug info: ").append(var2).append('\n');
      }

      if (this.buf.length() > 0) {
         this.text.add(this.buf.toString());
      }

   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append("OUTERCLASS ");
      this.appendDescriptor(0, var1);
      this.buf.append(' ');
      if (var2 != null) {
         this.buf.append(var2).append(' ');
      }

      this.appendDescriptor(3, var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitClassAnnotation(String var1, boolean var2) {
      this.text.add("\n");
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.text.add("\n");
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitClassAttribute(Attribute var1) {
      this.text.add("\n");
      this.visitAttribute(var1);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append("// access flags 0x");
      this.buf.append(Integer.toHexString(var4 & -33).toUpperCase()).append('\n');
      this.buf.append(this.tab);
      this.appendAccess(var4);
      this.buf.append("INNERCLASS ");
      this.appendDescriptor(0, var1);
      this.buf.append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append(' ');
      this.appendDescriptor(0, var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitField(int var1, String var2, String var3, String var4, Object var5) {
      this.buf.setLength(0);
      this.buf.append('\n');
      if ((var1 & 131072) != 0) {
         this.buf.append(this.tab).append("// DEPRECATED\n");
      }

      this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(var1).toUpperCase()).append('\n');
      if (var4 != null) {
         this.buf.append(this.tab);
         this.appendDescriptor(2, var4);
         TraceSignatureVisitor var6 = new TraceSignatureVisitor(0);
         SignatureReader var7 = new SignatureReader(var4);
         var7.acceptType(var6);
         this.buf.append(this.tab).append("// declaration: ").append(var6.getDeclaration()).append('\n');
      }

      this.buf.append(this.tab);
      this.appendAccess(var1);
      this.appendDescriptor(1, var3);
      this.buf.append(' ').append(var2);
      if (var5 != null) {
         this.buf.append(" = ");
         if (var5 instanceof String) {
            this.buf.append('"').append(var5).append('"');
         } else {
            this.buf.append(var5);
         }
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
      Textifier var8 = this.createTextifier();
      this.text.add(var8.getText());
      return var8;
   }

   public Textifier visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      this.buf.setLength(0);
      this.buf.append('\n');
      if ((var1 & 131072) != 0) {
         this.buf.append(this.tab).append("// DEPRECATED\n");
      }

      this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(var1).toUpperCase()).append('\n');
      if (var4 != null) {
         this.buf.append(this.tab);
         this.appendDescriptor(4, var4);
         TraceSignatureVisitor var6 = new TraceSignatureVisitor(0);
         SignatureReader var7 = new SignatureReader(var4);
         var7.accept(var6);
         String var8 = var6.getDeclaration();
         String var9 = var6.getReturnType();
         String var10 = var6.getExceptions();
         this.buf.append(this.tab).append("// declaration: ").append(var9).append(' ').append(var2).append(var8);
         if (var10 != null) {
            this.buf.append(" throws ").append(var10);
         }

         this.buf.append('\n');
      }

      this.buf.append(this.tab);
      this.appendAccess(var1 & -65);
      if ((var1 & 256) != 0) {
         this.buf.append("native ");
      }

      if ((var1 & 128) != 0) {
         this.buf.append("varargs ");
      }

      if ((var1 & 64) != 0) {
         this.buf.append("bridge ");
      }

      if ((this.access & 512) != 0 && (var1 & 1024) == 0 && (var1 & 8) == 0) {
         this.buf.append("default ");
      }

      this.buf.append(var2);
      this.appendDescriptor(3, var3);
      if (var5 != null && var5.length > 0) {
         this.buf.append(" throws ");

         for(int var11 = 0; var11 < var5.length; ++var11) {
            this.appendDescriptor(0, var5[var11]);
            this.buf.append(' ');
         }
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
      Textifier var12 = this.createTextifier();
      this.text.add(var12.getText());
      return var12;
   }

   public void visitClassEnd() {
      this.text.add("}\n");
   }

   public void visit(String var1, Object var2) {
      this.buf.setLength(0);
      this.appendComa(this.valueNumber++);
      if (var1 != null) {
         this.buf.append(var1).append('=');
      }

      if (var2 instanceof String) {
         this.visitString((String)var2);
      } else if (var2 instanceof Type) {
         this.visitType((Type)var2);
      } else if (var2 instanceof Byte) {
         this.visitByte((Byte)var2);
      } else if (var2 instanceof Boolean) {
         this.visitBoolean((Boolean)var2);
      } else if (var2 instanceof Short) {
         this.visitShort((Short)var2);
      } else if (var2 instanceof Character) {
         this.visitChar((Character)var2);
      } else if (var2 instanceof Integer) {
         this.visitInt((Integer)var2);
      } else if (var2 instanceof Float) {
         this.visitFloat((Float)var2);
      } else if (var2 instanceof Long) {
         this.visitLong((Long)var2);
      } else if (var2 instanceof Double) {
         this.visitDouble((Double)var2);
      } else if (var2.getClass().isArray()) {
         this.buf.append('{');
         int var4;
         if (var2 instanceof byte[]) {
            byte[] var11 = (byte[])((byte[])var2);

            for(var4 = 0; var4 < var11.length; ++var4) {
               this.appendComa(var4);
               this.visitByte(var11[var4]);
            }
         } else if (var2 instanceof boolean[]) {
            boolean[] var10 = (boolean[])((boolean[])var2);

            for(var4 = 0; var4 < var10.length; ++var4) {
               this.appendComa(var4);
               this.visitBoolean(var10[var4]);
            }
         } else if (var2 instanceof short[]) {
            short[] var9 = (short[])((short[])var2);

            for(var4 = 0; var4 < var9.length; ++var4) {
               this.appendComa(var4);
               this.visitShort(var9[var4]);
            }
         } else if (var2 instanceof char[]) {
            char[] var8 = (char[])((char[])var2);

            for(var4 = 0; var4 < var8.length; ++var4) {
               this.appendComa(var4);
               this.visitChar(var8[var4]);
            }
         } else if (var2 instanceof int[]) {
            int[] var7 = (int[])((int[])var2);

            for(var4 = 0; var4 < var7.length; ++var4) {
               this.appendComa(var4);
               this.visitInt(var7[var4]);
            }
         } else if (var2 instanceof long[]) {
            long[] var6 = (long[])((long[])var2);

            for(var4 = 0; var4 < var6.length; ++var4) {
               this.appendComa(var4);
               this.visitLong(var6[var4]);
            }
         } else if (var2 instanceof float[]) {
            float[] var5 = (float[])((float[])var2);

            for(var4 = 0; var4 < var5.length; ++var4) {
               this.appendComa(var4);
               this.visitFloat(var5[var4]);
            }
         } else if (var2 instanceof double[]) {
            double[] var3 = (double[])((double[])var2);

            for(var4 = 0; var4 < var3.length; ++var4) {
               this.appendComa(var4);
               this.visitDouble(var3[var4]);
            }
         }

         this.buf.append('}');
      }

      this.text.add(this.buf.toString());
   }

   private void visitInt(int var1) {
      this.buf.append(var1);
   }

   private void visitLong(long var1) {
      this.buf.append(var1).append('L');
   }

   private void visitFloat(float var1) {
      this.buf.append(var1).append('F');
   }

   private void visitDouble(double var1) {
      this.buf.append(var1).append('D');
   }

   private void visitChar(char var1) {
      this.buf.append("(char)").append(var1);
   }

   private void visitShort(short var1) {
      this.buf.append("(short)").append(var1);
   }

   private void visitByte(byte var1) {
      this.buf.append("(byte)").append(var1);
   }

   private void visitBoolean(boolean var1) {
      this.buf.append(var1);
   }

   private void visitString(String var1) {
      appendString(this.buf, var1);
   }

   private void visitType(Type var1) {
      this.buf.append(var1.getClassName()).append(".class");
   }

   public void visitEnum(String var1, String var2, String var3) {
      this.buf.setLength(0);
      this.appendComa(this.valueNumber++);
      if (var1 != null) {
         this.buf.append(var1).append('=');
      }

      this.appendDescriptor(1, var2);
      this.buf.append('.').append(var3);
      this.text.add(this.buf.toString());
   }

   public Textifier visitAnnotation(String var1, String var2) {
      this.buf.setLength(0);
      this.appendComa(this.valueNumber++);
      if (var1 != null) {
         this.buf.append(var1).append('=');
      }

      this.buf.append('@');
      this.appendDescriptor(1, var2);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var3 = this.createTextifier();
      this.text.add(var3.getText());
      this.text.add(")");
      return var3;
   }

   public Textifier visitArray(String var1) {
      this.buf.setLength(0);
      this.appendComa(this.valueNumber++);
      if (var1 != null) {
         this.buf.append(var1).append('=');
      }

      this.buf.append('{');
      this.text.add(this.buf.toString());
      Textifier var2 = this.createTextifier();
      this.text.add(var2.getText());
      this.text.add("}");
      return var2;
   }

   public void visitAnnotationEnd() {
   }

   public Textifier visitFieldAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitFieldAttribute(Attribute var1) {
      this.visitAttribute(var1);
   }

   public void visitFieldEnd() {
   }

   public void visitParameter(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("// parameter ");
      this.appendAccess(var2);
      this.buf.append(' ').append(var1 == null ? "<no name>" : var1).append('\n');
      this.text.add(this.buf.toString());
   }

   public Textifier visitAnnotationDefault() {
      this.text.add(this.tab2 + "default=");
      Textifier var1 = this.createTextifier();
      this.text.add(var1.getText());
      this.text.add("\n");
      return var1;
   }

   public Textifier visitMethodAnnotation(String var1, boolean var2) {
      return this.visitAnnotation(var1, var2);
   }

   public Printer visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public Textifier visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append('@');
      this.appendDescriptor(1, var2);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var4 = this.createTextifier();
      this.text.add(var4.getText());
      this.text.add(var3 ? ") // parameter " : ") // invisible, parameter ");
      this.text.add(var1);
      this.text.add("\n");
      return var4;
   }

   public void visitMethodAttribute(Attribute var1) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append("ATTRIBUTE ");
      this.appendDescriptor(-1, var1.type);
      if (var1 instanceof Textifiable) {
         ((Textifiable)var1).textify(this.buf, this.labelNames);
      } else {
         this.buf.append(" : unknown\n");
      }

      this.text.add(this.buf.toString());
   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      this.buf.setLength(0);
      this.buf.append(this.ltab);
      this.buf.append("FRAME ");
      switch(var1) {
      case -1:
      case 0:
         this.buf.append("FULL [");
         this.appendFrameTypes(var2, var3);
         this.buf.append("] [");
         this.appendFrameTypes(var4, var5);
         this.buf.append(']');
         break;
      case 1:
         this.buf.append("APPEND [");
         this.appendFrameTypes(var2, var3);
         this.buf.append(']');
         break;
      case 2:
         this.buf.append("CHOP ").append(var2);
         break;
      case 3:
         this.buf.append("SAME");
         break;
      case 4:
         this.buf.append("SAME1 ");
         this.appendFrameTypes(1, var5);
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitInsn(int var1) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitIntInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ').append(var1 == 188 ? TYPES[var2] : Integer.toString(var2)).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitVarInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitTypeInsn(int var1, String var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('.').append(var3).append(" : ");
      this.appendDescriptor(1, var4);
      this.buf.append('\n');
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
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendDescriptor(0, var2);
      this.buf.append('.').append(var3).append(' ');
      this.appendDescriptor(3, var4);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
      this.buf.append(var1);
      this.appendDescriptor(3, var2);
      this.buf.append(" [");
      this.buf.append('\n');
      this.buf.append(this.tab3);
      this.appendHandle(var3);
      this.buf.append('\n');
      this.buf.append(this.tab3).append("// arguments:");
      if (var4.length == 0) {
         this.buf.append(" none");
      } else {
         this.buf.append('\n');

         for(int var5 = 0; var5 < var4.length; ++var5) {
            this.buf.append(this.tab3);
            Object var6 = var4[var5];
            if (var6 instanceof String) {
               Printer.appendString(this.buf, (String)var6);
            } else if (var6 instanceof Type) {
               Type var7 = (Type)var6;
               if (var7.getSort() == 11) {
                  this.appendDescriptor(3, var7.getDescriptor());
               } else {
                  this.buf.append(var7.getDescriptor()).append(".class");
               }
            } else if (var6 instanceof Handle) {
               this.appendHandle((Handle)var6);
            } else {
               this.buf.append(var6);
            }

            this.buf.append(", \n");
         }

         this.buf.setLength(this.buf.length() - 3);
      }

      this.buf.append('\n');
      this.buf.append(this.tab2).append("]\n");
      this.text.add(this.buf.toString());
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append(OPCODES[var1]).append(' ');
      this.appendLabel(var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLabel(Label var1) {
      this.buf.setLength(0);
      this.buf.append(this.ltab);
      this.appendLabel(var1);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLdcInsn(Object var1) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LDC ");
      if (var1 instanceof String) {
         Printer.appendString(this.buf, (String)var1);
      } else if (var1 instanceof Type) {
         this.buf.append(((Type)var1).getDescriptor()).append(".class");
      } else {
         this.buf.append(var1);
      }

      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitIincInsn(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("IINC ").append(var1).append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TABLESWITCH\n");

      for(int var5 = 0; var5 < var4.length; ++var5) {
         this.buf.append(this.tab3).append(var1 + var5).append(": ");
         this.appendLabel(var4[var5]);
         this.buf.append('\n');
      }

      this.buf.append(this.tab3).append("default: ");
      this.appendLabel(var3);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOOKUPSWITCH\n");

      for(int var4 = 0; var4 < var3.length; ++var4) {
         this.buf.append(this.tab3).append(var2[var4]).append(": ");
         this.appendLabel(var3[var4]);
         this.buf.append('\n');
      }

      this.buf.append(this.tab3).append("default: ");
      this.appendLabel(var1);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MULTIANEWARRAY ");
      this.appendDescriptor(1, var1);
      this.buf.append(' ').append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public Printer visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      return this.visitTypeAnnotation(var1, var2, var3, var4);
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
      this.appendLabel(var1);
      this.buf.append(' ');
      this.appendLabel(var2);
      this.buf.append(' ');
      this.appendLabel(var3);
      this.buf.append(' ');
      this.appendDescriptor(0, var4);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public Printer visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
      this.appendDescriptor(1, var3);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var5 = this.createTextifier();
      this.text.add(var5.getText());
      this.buf.setLength(0);
      this.buf.append(") : ");
      this.appendTypeReference(var1);
      this.buf.append(", ").append(var2);
      this.buf.append(var4 ? "\n" : " // invisible\n");
      this.text.add(this.buf.toString());
      return var5;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOCALVARIABLE ").append(var1).append(' ');
      this.appendDescriptor(1, var2);
      this.buf.append(' ');
      this.appendLabel(var4);
      this.buf.append(' ');
      this.appendLabel(var5);
      this.buf.append(' ').append(var6).append('\n');
      if (var3 != null) {
         this.buf.append(this.tab2);
         this.appendDescriptor(2, var3);
         TraceSignatureVisitor var7 = new TraceSignatureVisitor(0);
         SignatureReader var8 = new SignatureReader(var3);
         var8.acceptType(var7);
         this.buf.append(this.tab2).append("// declaration: ").append(var7.getDeclaration()).append('\n');
      }

      this.text.add(this.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LOCALVARIABLE @");
      this.appendDescriptor(1, var6);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var8 = this.createTextifier();
      this.text.add(var8.getText());
      this.buf.setLength(0);
      this.buf.append(") : ");
      this.appendTypeReference(var1);
      this.buf.append(", ").append(var2);

      for(int var9 = 0; var9 < var3.length; ++var9) {
         this.buf.append(" [ ");
         this.appendLabel(var3[var9]);
         this.buf.append(" - ");
         this.appendLabel(var4[var9]);
         this.buf.append(" - ").append(var5[var9]).append(" ]");
      }

      this.buf.append(var7 ? "\n" : " // invisible\n");
      this.text.add(this.buf.toString());
      return var8;
   }

   public void visitLineNumber(int var1, Label var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("LINENUMBER ").append(var1).append(' ');
      this.appendLabel(var2);
      this.buf.append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMaxs(int var1, int var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MAXSTACK = ").append(var1).append('\n');
      this.text.add(this.buf.toString());
      this.buf.setLength(0);
      this.buf.append(this.tab2).append("MAXLOCALS = ").append(var2).append('\n');
      this.text.add(this.buf.toString());
   }

   public void visitMethodEnd() {
   }

   public Textifier visitAnnotation(String var1, boolean var2) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append('@');
      this.appendDescriptor(1, var1);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var3 = this.createTextifier();
      this.text.add(var3.getText());
      this.text.add(var2 ? ")\n" : ") // invisible\n");
      return var3;
   }

   public Textifier visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append('@');
      this.appendDescriptor(1, var3);
      this.buf.append('(');
      this.text.add(this.buf.toString());
      Textifier var5 = this.createTextifier();
      this.text.add(var5.getText());
      this.buf.setLength(0);
      this.buf.append(") : ");
      this.appendTypeReference(var1);
      this.buf.append(", ").append(var2);
      this.buf.append(var4 ? "\n" : " // invisible\n");
      this.text.add(this.buf.toString());
      return var5;
   }

   public void visitAttribute(Attribute var1) {
      this.buf.setLength(0);
      this.buf.append(this.tab).append("ATTRIBUTE ");
      this.appendDescriptor(-1, var1.type);
      if (var1 instanceof Textifiable) {
         ((Textifiable)var1).textify(this.buf, (Map)null);
      } else {
         this.buf.append(" : unknown\n");
      }

      this.text.add(this.buf.toString());
   }

   protected Textifier createTextifier() {
      return new Textifier();
   }

   protected void appendDescriptor(int var1, String var2) {
      if (var1 != 5 && var1 != 2 && var1 != 4) {
         this.buf.append(var2);
      } else if (var2 != null) {
         this.buf.append("// signature ").append(var2).append('\n');
      }

   }

   protected void appendLabel(Label var1) {
      if (this.labelNames == null) {
         this.labelNames = new HashMap();
      }

      String var2 = (String)this.labelNames.get(var1);
      if (var2 == null) {
         var2 = "L" + this.labelNames.size();
         this.labelNames.put(var1, var2);
      }

      this.buf.append(var2);
   }

   protected void appendHandle(Handle var1) {
      int var2 = var1.getTag();
      this.buf.append("// handle kind 0x").append(Integer.toHexString(var2)).append(" : ");
      boolean var3 = false;
      switch(var2) {
      case 1:
         this.buf.append("GETFIELD");
         break;
      case 2:
         this.buf.append("GETSTATIC");
         break;
      case 3:
         this.buf.append("PUTFIELD");
         break;
      case 4:
         this.buf.append("PUTSTATIC");
         break;
      case 5:
         this.buf.append("INVOKEVIRTUAL");
         var3 = true;
         break;
      case 6:
         this.buf.append("INVOKESTATIC");
         var3 = true;
         break;
      case 7:
         this.buf.append("INVOKESPECIAL");
         var3 = true;
         break;
      case 8:
         this.buf.append("NEWINVOKESPECIAL");
         var3 = true;
         break;
      case 9:
         this.buf.append("INVOKEINTERFACE");
         var3 = true;
      }

      this.buf.append('\n');
      this.buf.append(this.tab3);
      this.appendDescriptor(0, var1.getOwner());
      this.buf.append('.');
      this.buf.append(var1.getName());
      if (!var3) {
         this.buf.append('(');
      }

      this.appendDescriptor(9, var1.getDesc());
      if (!var3) {
         this.buf.append(')');
      }

   }

   private void appendAccess(int var1) {
      if ((var1 & 1) != 0) {
         this.buf.append("public ");
      }

      if ((var1 & 2) != 0) {
         this.buf.append("private ");
      }

      if ((var1 & 4) != 0) {
         this.buf.append("protected ");
      }

      if ((var1 & 16) != 0) {
         this.buf.append("final ");
      }

      if ((var1 & 8) != 0) {
         this.buf.append("static ");
      }

      if ((var1 & 32) != 0) {
         this.buf.append("synchronized ");
      }

      if ((var1 & 64) != 0) {
         this.buf.append("volatile ");
      }

      if ((var1 & 128) != 0) {
         this.buf.append("transient ");
      }

      if ((var1 & 1024) != 0) {
         this.buf.append("abstract ");
      }

      if ((var1 & 2048) != 0) {
         this.buf.append("strictfp ");
      }

      if ((var1 & 4096) != 0) {
         this.buf.append("synthetic ");
      }

      if ((var1 & '耀') != 0) {
         this.buf.append("mandated ");
      }

      if ((var1 & 16384) != 0) {
         this.buf.append("enum ");
      }

   }

   private void appendComa(int var1) {
      if (var1 != 0) {
         this.buf.append(", ");
      }

   }

   private void appendTypeReference(int var1) {
      TypeReference var2 = new TypeReference(var1);
      switch(var2.getSort()) {
      case 0:
         this.buf.append("CLASS_TYPE_PARAMETER ").append(var2.getTypeParameterIndex());
         break;
      case 1:
         this.buf.append("METHOD_TYPE_PARAMETER ").append(var2.getTypeParameterIndex());
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      default:
         break;
      case 16:
         this.buf.append("CLASS_EXTENDS ").append(var2.getSuperTypeIndex());
         break;
      case 17:
         this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(var2.getTypeParameterIndex()).append(", ").append(var2.getTypeParameterBoundIndex());
         break;
      case 18:
         this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(var2.getTypeParameterIndex()).append(", ").append(var2.getTypeParameterBoundIndex());
         break;
      case 19:
         this.buf.append("FIELD");
         break;
      case 20:
         this.buf.append("METHOD_RETURN");
         break;
      case 21:
         this.buf.append("METHOD_RECEIVER");
         break;
      case 22:
         this.buf.append("METHOD_FORMAL_PARAMETER ").append(var2.getFormalParameterIndex());
         break;
      case 23:
         this.buf.append("THROWS ").append(var2.getExceptionIndex());
         break;
      case 64:
         this.buf.append("LOCAL_VARIABLE");
         break;
      case 65:
         this.buf.append("RESOURCE_VARIABLE");
         break;
      case 66:
         this.buf.append("EXCEPTION_PARAMETER ").append(var2.getTryCatchBlockIndex());
         break;
      case 67:
         this.buf.append("INSTANCEOF");
         break;
      case 68:
         this.buf.append("NEW");
         break;
      case 69:
         this.buf.append("CONSTRUCTOR_REFERENCE");
         break;
      case 70:
         this.buf.append("METHOD_REFERENCE");
         break;
      case 71:
         this.buf.append("CAST ").append(var2.getTypeArgumentIndex());
         break;
      case 72:
         this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
         break;
      case 73:
         this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
         break;
      case 74:
         this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
         break;
      case 75:
         this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(var2.getTypeArgumentIndex());
      }

   }

   private void appendFrameTypes(int var1, Object[] var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         if (var3 > 0) {
            this.buf.append(' ');
         }

         if (var2[var3] instanceof String) {
            String var4 = (String)var2[var3];
            if (var4.startsWith("[")) {
               this.appendDescriptor(1, var4);
            } else {
               this.appendDescriptor(0, var4);
            }
         } else if (var2[var3] instanceof Integer) {
            switch((Integer)var2[var3]) {
            case 0:
               this.appendDescriptor(1, "T");
               break;
            case 1:
               this.appendDescriptor(1, "I");
               break;
            case 2:
               this.appendDescriptor(1, "F");
               break;
            case 3:
               this.appendDescriptor(1, "D");
               break;
            case 4:
               this.appendDescriptor(1, "J");
               break;
            case 5:
               this.appendDescriptor(1, "N");
               break;
            case 6:
               this.appendDescriptor(1, "U");
            }
         } else {
            this.appendLabel((Label)var2[var3]);
         }
      }

   }

   public Printer visitParameterAnnotation(int var1, String var2, boolean var3) {
      return this.visitParameterAnnotation(var1, var2, var3);
   }

   public Printer visitMethodAnnotation(String var1, boolean var2) {
      return this.visitMethodAnnotation(var1, var2);
   }

   public Printer visitAnnotationDefault() {
      return this.visitAnnotationDefault();
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

   public Printer visitClassAnnotation(String var1, boolean var2) {
      return this.visitClassAnnotation(var1, var2);
   }
}
