package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

public abstract class Printer {
   public static final String[] OPCODES;
   public static final String[] TYPES;
   public static final String[] HANDLE_TAG;
   protected final int api;
   protected final StringBuffer buf;
   public final List text;

   protected Printer(int var1) {
      this.api = var1;
      this.buf = new StringBuffer();
      this.text = new ArrayList();
   }

   public abstract void visit(int var1, int var2, String var3, String var4, String var5, String[] var6);

   public abstract void visitSource(String var1, String var2);

   public abstract void visitOuterClass(String var1, String var2, String var3);

   public abstract Printer visitClassAnnotation(String var1, boolean var2);

   public Printer visitClassTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitClassAttribute(Attribute var1);

   public abstract void visitInnerClass(String var1, String var2, String var3, int var4);

   public abstract Printer visitField(int var1, String var2, String var3, String var4, Object var5);

   public abstract Printer visitMethod(int var1, String var2, String var3, String var4, String[] var5);

   public abstract void visitClassEnd();

   public abstract void visit(String var1, Object var2);

   public abstract void visitEnum(String var1, String var2, String var3);

   public abstract Printer visitAnnotation(String var1, String var2);

   public abstract Printer visitArray(String var1);

   public abstract void visitAnnotationEnd();

   public abstract Printer visitFieldAnnotation(String var1, boolean var2);

   public Printer visitFieldTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitFieldAttribute(Attribute var1);

   public abstract void visitFieldEnd();

   public void visitParameter(String var1, int var2) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitAnnotationDefault();

   public abstract Printer visitMethodAnnotation(String var1, boolean var2);

   public Printer visitMethodTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitParameterAnnotation(int var1, String var2, boolean var3);

   public abstract void visitMethodAttribute(Attribute var1);

   public abstract void visitCode();

   public abstract void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5);

   public abstract void visitInsn(int var1);

   public abstract void visitIntInsn(int var1, int var2);

   public abstract void visitVarInsn(int var1, int var2);

   public abstract void visitTypeInsn(int var1, String var2);

   public abstract void visitFieldInsn(int var1, String var2, String var3, String var4);

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         boolean var5 = var1 == 185;
         this.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         throw new RuntimeException("Must be overriden");
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         if (var5 != (var1 == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
         } else {
            this.visitMethodInsn(var1, var2, var3, var4);
         }
      } else {
         throw new RuntimeException("Must be overriden");
      }
   }

   public abstract void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4);

   public abstract void visitJumpInsn(int var1, Label var2);

   public abstract void visitLabel(Label var1);

   public abstract void visitLdcInsn(Object var1);

   public abstract void visitIincInsn(int var1, int var2);

   public abstract void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4);

   public abstract void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3);

   public abstract void visitMultiANewArrayInsn(String var1, int var2);

   public Printer visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4);

   public Printer visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6);

   public Printer visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLineNumber(int var1, Label var2);

   public abstract void visitMaxs(int var1, int var2);

   public abstract void visitMethodEnd();

   public List getText() {
      return this.text;
   }

   public void print(PrintWriter var1) {
      printList(var1, this.text);
   }

   public static void appendString(StringBuffer var0, String var1) {
      var0.append('"');

      for(int var2 = 0; var2 < var1.length(); ++var2) {
         char var3 = var1.charAt(var2);
         if (var3 == '\n') {
            var0.append("\\n");
         } else if (var3 == '\r') {
            var0.append("\\r");
         } else if (var3 == '\\') {
            var0.append("\\\\");
         } else if (var3 == '"') {
            var0.append("\\\"");
         } else if (var3 >= ' ' && var3 <= 127) {
            var0.append(var3);
         } else {
            var0.append("\\u");
            if (var3 < 16) {
               var0.append("000");
            } else if (var3 < 256) {
               var0.append("00");
            } else if (var3 < 4096) {
               var0.append('0');
            }

            var0.append(Integer.toString(var3, 16));
         }
      }

      var0.append('"');
   }

   static void printList(PrintWriter var0, List var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Object var3 = var1.get(var2);
         if (var3 instanceof List) {
            printList(var0, (List)var3);
         } else {
            var0.print(var3.toString());
         }
      }

   }

   static {
      String var0 = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
      OPCODES = new String[200];
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; (var3 = var0.indexOf(44, var2)) > 0; var2 = var3 + 1) {
         OPCODES[var1++] = var2 + 1 == var3 ? null : var0.substring(var2, var3);
      }

      var0 = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
      TYPES = new String[12];
      var2 = 0;

      for(var1 = 4; (var3 = var0.indexOf(44, var2)) > 0; var2 = var3 + 1) {
         TYPES[var1++] = var0.substring(var2, var3);
      }

      var0 = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
      HANDLE_TAG = new String[10];
      var2 = 0;

      for(var1 = 1; (var3 = var0.indexOf(44, var2)) > 0; var2 = var3 + 1) {
         HANDLE_TAG[var1++] = var0.substring(var2, var3);
      }

   }
}
