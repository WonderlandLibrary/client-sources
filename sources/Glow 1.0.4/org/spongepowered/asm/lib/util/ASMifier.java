package org.spongepowered.asm.lib.util;

import java.io.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class ASMifier extends Printer
{
    protected final String name;
    protected final int id;
    protected Map<Label, String> labelNames;
    private static final int ACCESS_CLASS = 262144;
    private static final int ACCESS_FIELD = 524288;
    private static final int ACCESS_INNER = 1048576;
    
    public ASMifier() {
        this(327680, "cw", 0);
        if (this.getClass() != ASMifier.class) {
            throw new IllegalStateException();
        }
    }
    
    protected ASMifier(final int n, final String name, final int id) {
        super(n);
        this.name = name;
        this.id = id;
    }
    
    public static void main(final String[] array) throws Exception {
        int n = 0;
        int n2 = 2;
        int n3 = 1;
        if (array.length < 1 || array.length > 2) {
            n3 = 0;
        }
        if (n3 != 0 && "-debug".equals(array[0])) {
            n = 1;
            n2 = 0;
            if (array.length != 2) {
                n3 = 0;
            }
        }
        if (n3 == 0) {
            System.err.println("Prints the ASM code to generate the given class.");
            System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader classReader;
        if (array[n].endsWith(".class") || array[n].indexOf(92) > -1 || array[n].indexOf(47) > -1) {
            classReader = new ClassReader(new FileInputStream(array[n]));
        }
        else {
            classReader = new ClassReader(array[n]);
        }
        classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), n2);
    }
    
    @Override
    public void visit(final int n, final int n2, final String s, final String s2, final String s3, final String[] array) {
        final int lastIndex = s.lastIndexOf(47);
        String substring;
        if (lastIndex == -1) {
            substring = s;
        }
        else {
            this.text.add("package asm." + s.substring(0, lastIndex).replace('/', '.') + ";\n");
            substring = s.substring(lastIndex + 1);
        }
        this.text.add("import java.util.*;\n");
        this.text.add("import org.objectweb.asm.*;\n");
        this.text.add("public class " + substring + "Dump implements Opcodes {\n\n");
        this.text.add("public static byte[] dump () throws Exception {\n\n");
        this.text.add("ClassWriter cw = new ClassWriter(0);\n");
        this.text.add("FieldVisitor fv;\n");
        this.text.add("MethodVisitor mv;\n");
        this.text.add("AnnotationVisitor av0;\n\n");
        this.buf.setLength(0);
        this.buf.append("cw.visit(");
        switch (n) {
            case 196653: {
                this.buf.append("V1_1");
                break;
            }
            case 46: {
                this.buf.append("V1_2");
                break;
            }
            case 47: {
                this.buf.append("V1_3");
                break;
            }
            case 48: {
                this.buf.append("V1_4");
                break;
            }
            case 49: {
                this.buf.append("V1_5");
                break;
            }
            case 50: {
                this.buf.append("V1_6");
                break;
            }
            case 51: {
                this.buf.append("V1_7");
                break;
            }
            default: {
                this.buf.append(n);
                break;
            }
        }
        this.buf.append(", ");
        this.appendAccess(n2 | 0x40000);
        this.buf.append(", ");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        if (array != null && array.length > 0) {
            this.buf.append("new String[] {");
            for (int i = 0; i < array.length; ++i) {
                this.buf.append((i == 0) ? " " : ", ");
                this.appendConstant(array[i]);
            }
            this.buf.append(" }");
        }
        else {
            this.buf.append("null");
        }
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitSource(final String s, final String s2) {
        this.buf.setLength(0);
        this.buf.append("cw.visitSource(");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitOuterClass(final String s, final String s2, final String s3) {
        this.buf.setLength(0);
        this.buf.append("cw.visitOuterClass(");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitClassAnnotation(final String s, final boolean b) {
        return this.visitAnnotation(s, b);
    }
    
    @Override
    public ASMifier visitClassTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public void visitClassAttribute(final Attribute attribute) {
        this.visitAttribute(attribute);
    }
    
    @Override
    public void visitInnerClass(final String s, final String s2, final String s3, final int n) {
        this.buf.setLength(0);
        this.buf.append("cw.visitInnerClass(");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        this.appendAccess(n | 0x100000);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitField(final int n, final String s, final String s2, final String s3, final Object o) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("fv = cw.visitField(");
        this.appendAccess(n | 0x80000);
        this.buf.append(", ");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        this.appendConstant(o);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("fv", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public ASMifier visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("mv = cw.visitMethod(");
        this.appendAccess(n);
        this.buf.append(", ");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        if (array != null && array.length > 0) {
            this.buf.append("new String[] {");
            for (int i = 0; i < array.length; ++i) {
                this.buf.append((i == 0) ? " " : ", ");
                this.appendConstant(array[i]);
            }
            this.buf.append(" }");
        }
        else {
            this.buf.append("null");
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("mv", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public void visitClassEnd() {
        this.text.add("cw.visitEnd();\n\n");
        this.text.add("return cw.toByteArray();\n");
        this.text.add("}\n");
        this.text.add("}\n");
    }
    
    @Override
    public void visit(final String s, final Object o) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visit(");
        appendConstant(this.buf, s);
        this.buf.append(", ");
        appendConstant(this.buf, o);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitEnum(final String s, final String s2, final String s3) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnum(");
        appendConstant(this.buf, s);
        this.buf.append(", ");
        appendConstant(this.buf, s2);
        this.buf.append(", ");
        appendConstant(this.buf, s3);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitAnnotation(final String s, final String s2) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitAnnotation(");
        appendConstant(this.buf, s);
        this.buf.append(", ");
        appendConstant(this.buf, s2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", this.id + 1);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public ASMifier visitArray(final String s) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitArray(");
        appendConstant(this.buf, s);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", this.id + 1);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public void visitAnnotationEnd() {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitFieldAnnotation(final String s, final boolean b) {
        return this.visitAnnotation(s, b);
    }
    
    @Override
    public ASMifier visitFieldTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public void visitFieldAttribute(final Attribute attribute) {
        this.visitAttribute(attribute);
    }
    
    @Override
    public void visitFieldEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitParameter(final String s, final int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitParameter(");
        Printer.appendString(this.buf, s);
        this.buf.append(", ");
        this.appendAccess(n);
        this.text.add(this.buf.append(");\n").toString());
    }
    
    @Override
    public ASMifier visitAnnotationDefault() {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public ASMifier visitMethodAnnotation(final String s, final boolean b) {
        return this.visitAnnotation(s, b);
    }
    
    @Override
    public ASMifier visitMethodTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public ASMifier visitParameterAnnotation(final int n, final String s, final boolean b) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(n).append(", ");
        this.appendConstant(s);
        this.buf.append(", ").append(b).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public void visitMethodAttribute(final Attribute attribute) {
        this.visitAttribute(attribute);
    }
    
    @Override
    public void visitCode() {
        this.text.add(this.name + ".visitCode();\n");
    }
    
    @Override
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        this.buf.setLength(0);
        switch (n) {
            case -1:
            case 0: {
                this.declareFrameTypes(n2, array);
                this.declareFrameTypes(n3, array2);
                if (n == -1) {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
                }
                else {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
                }
                this.buf.append(n2).append(", new Object[] {");
                this.appendFrameTypes(n2, array);
                this.buf.append("}, ").append(n3).append(", new Object[] {");
                this.appendFrameTypes(n3, array2);
                this.buf.append('}');
                break;
            }
            case 1: {
                this.declareFrameTypes(n2, array);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(n2).append(", new Object[] {");
                this.appendFrameTypes(n2, array);
                this.buf.append("}, 0, null");
                break;
            }
            case 2: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(n2).append(", null, 0, null");
                break;
            }
            case 3: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
                break;
            }
            case 4: {
                this.declareFrameTypes(1, array2);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
                this.appendFrameTypes(1, array2);
                this.buf.append('}');
                break;
            }
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInsn(final int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInsn(").append(ASMifier.OPCODES[n]).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIntInsn(final int n, final int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIntInsn(").append(ASMifier.OPCODES[n]).append(", ").append((n == 188) ? ASMifier.TYPES[n2] : Integer.toString(n2)).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitVarInsn(final int n, final int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitVarInsn(").append(ASMifier.OPCODES[n]).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTypeInsn(final int n, final String s) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitTypeInsn(").append(ASMifier.OPCODES[n]).append(", ");
        this.appendConstant(s);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitFieldInsn(").append(ASMifier.OPCODES[n]).append(", ");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, s, s2, s3);
            return;
        }
        this.doVisitMethodInsn(n, s, s2, s3, n == 185);
    }
    
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, s, s2, s3, b);
            return;
        }
        this.doVisitMethodInsn(n, s, s2, s3, b);
    }
    
    private void doVisitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMethodInsn(").append(ASMifier.OPCODES[n]).append(", ");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        this.buf.append(b ? "true" : "false");
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(handle);
        this.buf.append(", new Object[]{");
        for (int i = 0; i < array.length; ++i) {
            this.appendConstant(array[i]);
            if (i != array.length - 1) {
                this.buf.append(", ");
            }
        }
        this.buf.append("});\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitJumpInsn(final int n, final Label label) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitJumpInsn(").append(ASMifier.OPCODES[n]).append(", ");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLabel(final Label label) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitLabel(");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLdcInsn(final Object o) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLdcInsn(");
        this.appendConstant(o);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIincInsn(final int n, final int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIincInsn(").append(n).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        this.buf.setLength(0);
        for (int i = 0; i < array.length; ++i) {
            this.declareLabel(array[i]);
        }
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitTableSwitchInsn(").append(n).append(", ").append(n2).append(", ");
        this.appendLabel(label);
        this.buf.append(", new Label[] {");
        for (int j = 0; j < array.length; ++j) {
            this.buf.append((j == 0) ? " " : ", ");
            this.appendLabel(array[j]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        this.buf.setLength(0);
        for (int i = 0; i < array2.length; ++i) {
            this.declareLabel(array2[i]);
        }
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitLookupSwitchInsn(");
        this.appendLabel(label);
        this.buf.append(", new int[] {");
        for (int j = 0; j < array.length; ++j) {
            this.buf.append((j == 0) ? " " : ", ").append(array[j]);
        }
        this.buf.append(" }, new Label[] {");
        for (int k = 0; k < array2.length; ++k) {
            this.buf.append((k == 0) ? " " : ", ");
            this.appendLabel(array2[k]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String s, final int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
        this.appendConstant(s);
        this.buf.append(", ").append(n).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation("visitInsnAnnotation", n, typePath, s, b);
    }
    
    @Override
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.declareLabel(label2);
        this.declareLabel(label3);
        this.buf.append(this.name).append(".visitTryCatchBlock(");
        this.appendLabel(label);
        this.buf.append(", ");
        this.appendLabel(label2);
        this.buf.append(", ");
        this.appendLabel(label3);
        this.buf.append(", ");
        this.appendConstant(s);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation("visitTryCatchAnnotation", n, typePath, s, b);
    }
    
    @Override
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLocalVariable(");
        this.appendConstant(s);
        this.buf.append(", ");
        this.appendConstant(s2);
        this.buf.append(", ");
        this.appendConstant(s3);
        this.buf.append(", ");
        this.appendLabel(label);
        this.buf.append(", ");
        this.appendLabel(label2);
        this.buf.append(", ").append(n).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Printer visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitLocalVariableAnnotation(");
        this.buf.append(n);
        if (typePath == null) {
            this.buf.append(", null, ");
        }
        else {
            this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        }
        this.buf.append("new Label[] {");
        for (int i = 0; i < array.length; ++i) {
            this.buf.append((i == 0) ? " " : ", ");
            this.appendLabel(array[i]);
        }
        this.buf.append(" }, new Label[] {");
        for (int j = 0; j < array2.length; ++j) {
            this.buf.append((j == 0) ? " " : ", ");
            this.appendLabel(array2[j]);
        }
        this.buf.append(" }, new int[] {");
        for (int k = 0; k < array3.length; ++k) {
            this.buf.append((k == 0) ? " " : ", ").append(array3[k]);
        }
        this.buf.append(" }, ");
        this.appendConstant(s);
        this.buf.append(", ").append(b).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    @Override
    public void visitLineNumber(final int n, final Label label) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLineNumber(").append(n).append(", ");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMaxs(final int n, final int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMaxs(").append(n).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMethodEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    public ASMifier visitAnnotation(final String s, final boolean b) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
        this.appendConstant(s);
        this.buf.append(", ").append(b).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    public ASMifier visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTypeAnnotation("visitTypeAnnotation", n, typePath, s, b);
    }
    
    public ASMifier visitTypeAnnotation(final String s, final int n, final TypePath typePath, final String s2, final boolean b) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(s).append("(");
        this.buf.append(n);
        if (typePath == null) {
            this.buf.append(", null, ");
        }
        else {
            this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        }
        this.appendConstant(s2);
        this.buf.append(", ").append(b).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier asMifier = this.createASMifier("av", 0);
        this.text.add(asMifier.getText());
        this.text.add("}\n");
        return asMifier;
    }
    
    public void visitAttribute(final Attribute attribute) {
        this.buf.setLength(0);
        this.buf.append("// ATTRIBUTE ").append(attribute.type).append('\n');
        if (attribute instanceof ASMifiable) {
            if (this.labelNames == null) {
                this.labelNames = new HashMap<Label, String>();
            }
            this.buf.append("{\n");
            ((ASMifiable)attribute).asmify(this.buf, "attr", this.labelNames);
            this.buf.append(this.name).append(".visitAttribute(attr);\n");
            this.buf.append("}\n");
        }
        this.text.add(this.buf.toString());
    }
    
    protected ASMifier createASMifier(final String s, final int n) {
        return new ASMifier(327680, s, n);
    }
    
    void appendAccess(final int n) {
        int n2 = 1;
        if ((n & 0x1) != 0x0) {
            this.buf.append("ACC_PUBLIC");
            n2 = 0;
        }
        if ((n & 0x2) != 0x0) {
            this.buf.append("ACC_PRIVATE");
            n2 = 0;
        }
        if ((n & 0x4) != 0x0) {
            this.buf.append("ACC_PROTECTED");
            n2 = 0;
        }
        if ((n & 0x10) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_FINAL");
            n2 = 0;
        }
        if ((n & 0x8) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STATIC");
            n2 = 0;
        }
        if ((n & 0x20) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            if ((n & 0x40000) == 0x0) {
                this.buf.append("ACC_SYNCHRONIZED");
            }
            else {
                this.buf.append("ACC_SUPER");
            }
            n2 = 0;
        }
        if ((n & 0x40) != 0x0 && (n & 0x80000) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VOLATILE");
            n2 = 0;
        }
        if ((n & 0x40) != 0x0 && (n & 0x40000) == 0x0 && (n & 0x80000) == 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_BRIDGE");
            n2 = 0;
        }
        if ((n & 0x80) != 0x0 && (n & 0x40000) == 0x0 && (n & 0x80000) == 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VARARGS");
            n2 = 0;
        }
        if ((n & 0x80) != 0x0 && (n & 0x80000) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_TRANSIENT");
            n2 = 0;
        }
        if ((n & 0x100) != 0x0 && (n & 0x40000) == 0x0 && (n & 0x80000) == 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_NATIVE");
            n2 = 0;
        }
        if ((n & 0x4000) != 0x0 && ((n & 0x40000) != 0x0 || (n & 0x80000) != 0x0 || (n & 0x100000) != 0x0)) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ENUM");
            n2 = 0;
        }
        if ((n & 0x2000) != 0x0 && ((n & 0x40000) != 0x0 || (n & 0x100000) != 0x0)) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ANNOTATION");
            n2 = 0;
        }
        if ((n & 0x400) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ABSTRACT");
            n2 = 0;
        }
        if ((n & 0x200) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_INTERFACE");
            n2 = 0;
        }
        if ((n & 0x800) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STRICT");
            n2 = 0;
        }
        if ((n & 0x1000) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_SYNTHETIC");
            n2 = 0;
        }
        if ((n & 0x20000) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_DEPRECATED");
            n2 = 0;
        }
        if ((n & 0x8000) != 0x0) {
            if (n2 == 0) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_MANDATED");
            n2 = 0;
        }
        if (n2 != 0) {
            this.buf.append('0');
        }
    }
    
    protected void appendConstant(final Object o) {
        appendConstant(this.buf, o);
    }
    
    static void appendConstant(final StringBuffer sb, final Object o) {
        if (o == null) {
            sb.append("null");
        }
        else if (o instanceof String) {
            Printer.appendString(sb, (String)o);
        }
        else if (o instanceof Type) {
            sb.append("Type.getType(\"");
            sb.append(((Type)o).getDescriptor());
            sb.append("\")");
        }
        else if (o instanceof Handle) {
            sb.append("new Handle(");
            final Handle handle = (Handle)o;
            sb.append("Opcodes.").append(ASMifier.HANDLE_TAG[handle.getTag()]).append(", \"");
            sb.append(handle.getOwner()).append("\", \"");
            sb.append(handle.getName()).append("\", \"");
            sb.append(handle.getDesc()).append("\")");
        }
        else if (o instanceof Byte) {
            sb.append("new Byte((byte)").append(o).append(')');
        }
        else if (o instanceof Boolean) {
            sb.append(o ? "Boolean.TRUE" : "Boolean.FALSE");
        }
        else if (o instanceof Short) {
            sb.append("new Short((short)").append(o).append(')');
        }
        else if (o instanceof Character) {
            sb.append("new Character((char)").append((int)(char)o).append(')');
        }
        else if (o instanceof Integer) {
            sb.append("new Integer(").append(o).append(')');
        }
        else if (o instanceof Float) {
            sb.append("new Float(\"").append(o).append("\")");
        }
        else if (o instanceof Long) {
            sb.append("new Long(").append(o).append("L)");
        }
        else if (o instanceof Double) {
            sb.append("new Double(\"").append(o).append("\")");
        }
        else if (o instanceof byte[]) {
            final byte[] array = (byte[])o;
            sb.append("new byte[] {");
            for (int i = 0; i < array.length; ++i) {
                sb.append((i == 0) ? "" : ",").append(array[i]);
            }
            sb.append('}');
        }
        else if (o instanceof boolean[]) {
            final boolean[] array2 = (boolean[])o;
            sb.append("new boolean[] {");
            for (int j = 0; j < array2.length; ++j) {
                sb.append((j == 0) ? "" : ",").append(array2[j]);
            }
            sb.append('}');
        }
        else if (o instanceof short[]) {
            final short[] array3 = (short[])o;
            sb.append("new short[] {");
            for (int k = 0; k < array3.length; ++k) {
                sb.append((k == 0) ? "" : ",").append("(short)").append(array3[k]);
            }
            sb.append('}');
        }
        else if (o instanceof char[]) {
            final char[] array4 = (char[])o;
            sb.append("new char[] {");
            for (int l = 0; l < array4.length; ++l) {
                sb.append((l == 0) ? "" : ",").append("(char)").append((int)array4[l]);
            }
            sb.append('}');
        }
        else if (o instanceof int[]) {
            final int[] array5 = (int[])o;
            sb.append("new int[] {");
            for (int n = 0; n < array5.length; ++n) {
                sb.append((n == 0) ? "" : ",").append(array5[n]);
            }
            sb.append('}');
        }
        else if (o instanceof long[]) {
            final long[] array6 = (long[])o;
            sb.append("new long[] {");
            for (int n2 = 0; n2 < array6.length; ++n2) {
                sb.append((n2 == 0) ? "" : ",").append(array6[n2]).append('L');
            }
            sb.append('}');
        }
        else if (o instanceof float[]) {
            final float[] array7 = (float[])o;
            sb.append("new float[] {");
            for (int n3 = 0; n3 < array7.length; ++n3) {
                sb.append((n3 == 0) ? "" : ",").append(array7[n3]).append('f');
            }
            sb.append('}');
        }
        else if (o instanceof double[]) {
            final double[] array8 = (double[])o;
            sb.append("new double[] {");
            for (int n4 = 0; n4 < array8.length; ++n4) {
                sb.append((n4 == 0) ? "" : ",").append(array8[n4]).append('d');
            }
            sb.append('}');
        }
    }
    
    private void declareFrameTypes(final int n, final Object[] array) {
        for (int i = 0; i < n; ++i) {
            if (array[i] instanceof Label) {
                this.declareLabel((Label)array[i]);
            }
        }
    }
    
    private void appendFrameTypes(final int n, final Object[] array) {
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                this.buf.append(", ");
            }
            if (array[i] instanceof String) {
                this.appendConstant(array[i]);
            }
            else if (array[i] instanceof Integer) {
                switch ((int)array[i]) {
                    case 0: {
                        this.buf.append("Opcodes.TOP");
                        break;
                    }
                    case 1: {
                        this.buf.append("Opcodes.INTEGER");
                        break;
                    }
                    case 2: {
                        this.buf.append("Opcodes.FLOAT");
                        break;
                    }
                    case 3: {
                        this.buf.append("Opcodes.DOUBLE");
                        break;
                    }
                    case 4: {
                        this.buf.append("Opcodes.LONG");
                        break;
                    }
                    case 5: {
                        this.buf.append("Opcodes.NULL");
                        break;
                    }
                    case 6: {
                        this.buf.append("Opcodes.UNINITIALIZED_THIS");
                        break;
                    }
                }
            }
            else {
                this.appendLabel((Label)array[i]);
            }
        }
    }
    
    protected void declareLabel(final Label label) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap<Label, String>();
        }
        if (this.labelNames.get(label) == null) {
            final String string = "l" + this.labelNames.size();
            this.labelNames.put(label, string);
            this.buf.append("Label ").append(string).append(" = new Label();\n");
        }
    }
    
    protected void appendLabel(final Label label) {
        this.buf.append(this.labelNames.get(label));
    }
    
    @Override
    public Printer visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitTryCatchAnnotation(n, typePath, s, b);
    }
    
    @Override
    public Printer visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitInsnAnnotation(n, typePath, s, b);
    }
    
    @Override
    public Printer visitParameterAnnotation(final int n, final String s, final boolean b) {
        return this.visitParameterAnnotation(n, s, b);
    }
    
    @Override
    public Printer visitMethodTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitMethodTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public Printer visitMethodAnnotation(final String s, final boolean b) {
        return this.visitMethodAnnotation(s, b);
    }
    
    @Override
    public Printer visitAnnotationDefault() {
        return this.visitAnnotationDefault();
    }
    
    @Override
    public Printer visitFieldTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitFieldTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public Printer visitFieldAnnotation(final String s, final boolean b) {
        return this.visitFieldAnnotation(s, b);
    }
    
    @Override
    public Printer visitArray(final String s) {
        return this.visitArray(s);
    }
    
    @Override
    public Printer visitAnnotation(final String s, final String s2) {
        return this.visitAnnotation(s, s2);
    }
    
    @Override
    public Printer visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
        return this.visitMethod(n, s, s2, s3, array);
    }
    
    @Override
    public Printer visitField(final int n, final String s, final String s2, final String s3, final Object o) {
        return this.visitField(n, s, s2, s3, o);
    }
    
    @Override
    public Printer visitClassTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        return this.visitClassTypeAnnotation(n, typePath, s, b);
    }
    
    @Override
    public Printer visitClassAnnotation(final String s, final boolean b) {
        return this.visitClassAnnotation(s, b);
    }
}
