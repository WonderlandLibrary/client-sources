package org.spongepowered.asm.lib.util;

import java.util.*;
import org.spongepowered.asm.lib.*;
import java.io.*;

public abstract class Printer
{
    public static final String[] OPCODES;
    public static final String[] TYPES;
    public static final String[] HANDLE_TAG;
    protected final int api;
    protected final StringBuffer buf;
    public final List<Object> text;
    
    protected Printer(final int api) {
        super();
        this.api = api;
        this.buf = new StringBuffer();
        this.text = new ArrayList<Object>();
    }
    
    public abstract void visit(final int p0, final int p1, final String p2, final String p3, final String p4, final String[] p5);
    
    public abstract void visitSource(final String p0, final String p1);
    
    public abstract void visitOuterClass(final String p0, final String p1, final String p2);
    
    public abstract Printer visitClassAnnotation(final String p0, final boolean p1);
    
    public Printer visitClassTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitClassAttribute(final Attribute p0);
    
    public abstract void visitInnerClass(final String p0, final String p1, final String p2, final int p3);
    
    public abstract Printer visitField(final int p0, final String p1, final String p2, final String p3, final Object p4);
    
    public abstract Printer visitMethod(final int p0, final String p1, final String p2, final String p3, final String[] p4);
    
    public abstract void visitClassEnd();
    
    public abstract void visit(final String p0, final Object p1);
    
    public abstract void visitEnum(final String p0, final String p1, final String p2);
    
    public abstract Printer visitAnnotation(final String p0, final String p1);
    
    public abstract Printer visitArray(final String p0);
    
    public abstract void visitAnnotationEnd();
    
    public abstract Printer visitFieldAnnotation(final String p0, final boolean p1);
    
    public Printer visitFieldTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitFieldAttribute(final Attribute p0);
    
    public abstract void visitFieldEnd();
    
    public void visitParameter(final String s, final int n) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract Printer visitAnnotationDefault();
    
    public abstract Printer visitMethodAnnotation(final String p0, final boolean p1);
    
    public Printer visitMethodTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract Printer visitParameterAnnotation(final int p0, final String p1, final boolean p2);
    
    public abstract void visitMethodAttribute(final Attribute p0);
    
    public abstract void visitCode();
    
    public abstract void visitFrame(final int p0, final int p1, final Object[] p2, final int p3, final Object[] p4);
    
    public abstract void visitInsn(final int p0);
    
    public abstract void visitIntInsn(final int p0, final int p1);
    
    public abstract void visitVarInsn(final int p0, final int p1);
    
    public abstract void visitTypeInsn(final int p0, final String p1);
    
    public abstract void visitFieldInsn(final int p0, final String p1, final String p2, final String p3);
    
    @Deprecated
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.api >= 327680) {
            this.visitMethodInsn(n, s, s2, s3, n == 185);
            return;
        }
        throw new RuntimeException("Must be overriden");
    }
    
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.api >= 327680) {
            throw new RuntimeException("Must be overriden");
        }
        if (b != (n == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
        }
        this.visitMethodInsn(n, s, s2, s3);
    }
    
    public abstract void visitInvokeDynamicInsn(final String p0, final String p1, final Handle p2, final Object... p3);
    
    public abstract void visitJumpInsn(final int p0, final Label p1);
    
    public abstract void visitLabel(final Label p0);
    
    public abstract void visitLdcInsn(final Object p0);
    
    public abstract void visitIincInsn(final int p0, final int p1);
    
    public abstract void visitTableSwitchInsn(final int p0, final int p1, final Label p2, final Label... p3);
    
    public abstract void visitLookupSwitchInsn(final Label p0, final int[] p1, final Label[] p2);
    
    public abstract void visitMultiANewArrayInsn(final String p0, final int p1);
    
    public Printer visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitTryCatchBlock(final Label p0, final Label p1, final Label p2, final String p3);
    
    public Printer visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitLocalVariable(final String p0, final String p1, final String p2, final Label p3, final Label p4, final int p5);
    
    public Printer visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitLineNumber(final int p0, final Label p1);
    
    public abstract void visitMaxs(final int p0, final int p1);
    
    public abstract void visitMethodEnd();
    
    public List<Object> getText() {
        return this.text;
    }
    
    public void print(final PrintWriter printWriter) {
        printList(printWriter, this.text);
    }
    
    public static void appendString(final StringBuffer sb, final String s) {
        sb.append('\"');
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\n') {
                sb.append("\\n");
            }
            else if (char1 == '\r') {
                sb.append("\\r");
            }
            else if (char1 == '\\') {
                sb.append("\\\\");
            }
            else if (char1 == '\"') {
                sb.append("\\\"");
            }
            else if (char1 < ' ' || char1 > '\u007f') {
                sb.append("\\u");
                if (char1 < '\u0010') {
                    sb.append("000");
                }
                else if (char1 < '\u0100') {
                    sb.append("00");
                }
                else if (char1 < '\u1000') {
                    sb.append('0');
                }
                sb.append(Integer.toString(char1, 16));
            }
            else {
                sb.append(char1);
            }
        }
        sb.append('\"');
    }
    
    static void printList(final PrintWriter printWriter, final List<?> list) {
        for (int i = 0; i < list.size(); ++i) {
            final Object value = list.get(i);
            if (value instanceof List) {
                printList(printWriter, (List<?>)value);
            }
            else {
                printWriter.print(value.toString());
            }
        }
    }
    
    static {
        final String s = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
        OPCODES = new String[200];
        int n = 0;
        int index;
        for (int n2 = 0; (index = s.indexOf(44, n2)) > 0; n2 = index + 1) {
            Printer.OPCODES[n++] = ((n2 + 1 == index) ? null : s.substring(n2, index));
        }
        final String s2 = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
        TYPES = new String[12];
        int n3 = 0;
        int n4 = 4;
        int index2;
        while ((index2 = s2.indexOf(44, n3)) > 0) {
            Printer.TYPES[n4++] = s2.substring(n3, index2);
            n3 = index2 + 1;
        }
        final String s3 = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
        HANDLE_TAG = new String[10];
        int n5 = 0;
        int n6 = 1;
        int index3;
        while ((index3 = s3.indexOf(44, n5)) > 0) {
            Printer.HANDLE_TAG[n6++] = s3.substring(n5, index3);
            n5 = index3 + 1;
        }
    }
}
