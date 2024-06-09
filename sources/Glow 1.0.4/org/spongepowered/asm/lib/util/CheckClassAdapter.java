package org.spongepowered.asm.lib.util;

import java.io.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class CheckClassAdapter extends ClassVisitor
{
    private int version;
    private boolean start;
    private boolean source;
    private boolean outer;
    private boolean end;
    private Map<Label, Integer> labels;
    private boolean checkDataFlow;
    
    public static void main(final String[] array) throws Exception {
        if (array.length != 1) {
            System.err.println("Verifies the given class.");
            System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
            return;
        }
        ClassReader classReader;
        if (array[0].endsWith(".class")) {
            classReader = new ClassReader(new FileInputStream(array[0]));
        }
        else {
            classReader = new ClassReader(array[0]);
        }
        verify(classReader, false, new PrintWriter(System.err));
    }
    
    public static void verify(final ClassReader classReader, final ClassLoader classLoader, final boolean b, final PrintWriter printWriter) {
        final ClassNode classNode = new ClassNode();
        classReader.accept(new CheckClassAdapter(classNode, false), 2);
        final Type type = (classNode.superName == null) ? null : Type.getObjectType(classNode.superName);
        final List<MethodNode> methods = classNode.methods;
        final ArrayList<Type> list = new ArrayList<Type>();
        final Iterator<String> iterator = classNode.interfaces.iterator();
        while (iterator.hasNext()) {
            list.add(Type.getObjectType(iterator.next()));
        }
        for (int i = 0; i < methods.size(); ++i) {
            final MethodNode methodNode = methods.get(i);
            final SimpleVerifier simpleVerifier = new SimpleVerifier(Type.getObjectType(classNode.name), type, list, (classNode.access & 0x200) != 0x0);
            final Analyzer analyzer = new Analyzer<BasicValue>((Interpreter<Value>)simpleVerifier);
            if (classLoader != null) {
                simpleVerifier.setClassLoader(classLoader);
            }
            try {
                analyzer.analyze(classNode.name, methodNode);
                if (!b) {
                    continue;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace(printWriter);
            }
            printAnalyzerResult(methodNode, (Analyzer<BasicValue>)analyzer, printWriter);
        }
        printWriter.flush();
    }
    
    public static void verify(final ClassReader classReader, final boolean b, final PrintWriter printWriter) {
        verify(classReader, null, b, printWriter);
    }
    
    static void printAnalyzerResult(final MethodNode methodNode, final Analyzer<BasicValue> analyzer, final PrintWriter printWriter) {
        final Frame<BasicValue>[] frames = analyzer.getFrames();
        final Textifier textifier = new Textifier();
        final TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
        printWriter.println(methodNode.name + methodNode.desc);
        for (int i = 0; i < methodNode.instructions.size(); ++i) {
            methodNode.instructions.get(i).accept(traceMethodVisitor);
            final StringBuilder sb = new StringBuilder();
            final Frame<BasicValue> frame = frames[i];
            if (frame == null) {
                sb.append('?');
            }
            else {
                for (int j = 0; j < frame.getLocals(); ++j) {
                    sb.append(getShortName(frame.getLocal(j).toString())).append(' ');
                }
                sb.append(" : ");
                for (int k = 0; k < frame.getStackSize(); ++k) {
                    sb.append(getShortName(frame.getStack(k).toString())).append(' ');
                }
            }
            while (sb.length() < methodNode.maxStack + methodNode.maxLocals + 1) {
                sb.append(' ');
            }
            printWriter.print(Integer.toString(i + 100000).substring(1));
            printWriter.print(" " + (Object)sb + " : " + textifier.text.get(textifier.text.size() - 1));
        }
        for (int l = 0; l < methodNode.tryCatchBlocks.size(); ++l) {
            methodNode.tryCatchBlocks.get(l).accept(traceMethodVisitor);
            printWriter.print(" " + textifier.text.get(textifier.text.size() - 1));
        }
        printWriter.println();
    }
    
    private static String getShortName(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        int length = s.length();
        if (s.charAt(length - 1) == ';') {
            --length;
        }
        return (lastIndex == -1) ? s : s.substring(lastIndex + 1, length);
    }
    
    public CheckClassAdapter(final ClassVisitor classVisitor) {
        this(classVisitor, true);
    }
    
    public CheckClassAdapter(final ClassVisitor classVisitor, final boolean b) {
        this(327680, classVisitor, b);
        if (this.getClass() != CheckClassAdapter.class) {
            throw new IllegalStateException();
        }
    }
    
    protected CheckClassAdapter(final int n, final ClassVisitor classVisitor, final boolean checkDataFlow) {
        super(n, classVisitor);
        this.labels = new HashMap<Label, Integer>();
        this.checkDataFlow = checkDataFlow;
    }
    
    @Override
    public void visit(final int version, final int n, final String s, final String s2, final String s3, final String[] array) {
        if (this.start) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.start = true;
        this.checkState();
        checkAccess(n, 423473);
        if (s == null || !s.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(s, "class name");
        }
        if ("java/lang/Object".equals(s)) {
            if (s3 != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        }
        else {
            CheckMethodAdapter.checkInternalName(s3, "super class name");
        }
        if (s2 != null) {
            checkClassSignature(s2);
        }
        if ((n & 0x200) != 0x0 && !"java/lang/Object".equals(s3)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                CheckMethodAdapter.checkInternalName(array[i], "interface name at index " + i);
            }
        }
        super.visit(this.version = version, n, s, s2, s3, array);
    }
    
    @Override
    public void visitSource(final String s, final String s2) {
        this.checkState();
        if (this.source) {
            throw new IllegalStateException("visitSource can be called only once.");
        }
        this.source = true;
        super.visitSource(s, s2);
    }
    
    @Override
    public void visitOuterClass(final String s, final String s2, final String s3) {
        this.checkState();
        if (this.outer) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
        }
        this.outer = true;
        if (s == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        if (s3 != null) {
            CheckMethodAdapter.checkMethodDesc(s3);
        }
        super.visitOuterClass(s, s2, s3);
    }
    
    @Override
    public void visitInnerClass(final String s, final String s2, final String s3, final int n) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(s, "class name");
        if (s2 != null) {
            CheckMethodAdapter.checkInternalName(s2, "outer class name");
        }
        if (s3 != null) {
            int n2;
            for (n2 = 0; n2 < s3.length() && Character.isDigit(s3.charAt(n2)); ++n2) {}
            if (n2 == 0 || n2 < s3.length()) {
                CheckMethodAdapter.checkIdentifier(s3, n2, -1, "inner class name");
            }
        }
        checkAccess(n, 30239);
        super.visitInnerClass(s, s2, s3, n);
    }
    
    @Override
    public FieldVisitor visitField(final int n, final String s, final String s2, final String s3, final Object o) {
        this.checkState();
        checkAccess(n, 413919);
        CheckMethodAdapter.checkUnqualifiedName(this.version, s, "field name");
        CheckMethodAdapter.checkDesc(s2, false);
        if (s3 != null) {
            checkFieldSignature(s3);
        }
        if (o != null) {
            CheckMethodAdapter.checkConstant(o);
        }
        return new CheckFieldAdapter(super.visitField(n, s, s2, s3, o));
    }
    
    @Override
    public MethodVisitor visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
        this.checkState();
        checkAccess(n, 400895);
        if (!"<init>".equals(s) && !"<clinit>".equals(s)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, s, "method name");
        }
        CheckMethodAdapter.checkMethodDesc(s2);
        if (s3 != null) {
            checkMethodSignature(s3);
        }
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                CheckMethodAdapter.checkInternalName(array[i], "exception name at index " + i);
            }
        }
        CheckMethodAdapter checkMethodAdapter;
        if (this.checkDataFlow) {
            checkMethodAdapter = new CheckMethodAdapter(n, s, s2, super.visitMethod(n, s, s2, s3, array), this.labels);
        }
        else {
            checkMethodAdapter = new CheckMethodAdapter(super.visitMethod(n, s, s2, s3, array), this.labels);
        }
        checkMethodAdapter.version = this.version;
        return checkMethodAdapter;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        this.checkState();
        CheckMethodAdapter.checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(s, b));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        this.checkState();
        final int n2 = n >>> 24;
        if (n2 != 0 && n2 != 17 && n2 != 16) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(n, typePath, s, b));
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        this.checkState();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }
    
    @Override
    public void visitEnd() {
        this.checkState();
        this.end = true;
        super.visitEnd();
    }
    
    private void checkState() {
        if (!this.start) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
        }
        if (this.end) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
        }
    }
    
    static void checkAccess(final int n, final int n2) {
        if ((n & ~n2) != 0x0) {
            throw new IllegalArgumentException("Invalid access flags: " + n);
        }
        if (((((n & 0x1) != 0x0) + ((n & 0x2) != 0x0) + ((n & 0x4) != 0x0)) ? 1 : 0) > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + n);
        }
        if (((((n & 0x10) != 0x0) + ((n & 0x400) != 0x0)) ? 1 : 0) > 1) {
            throw new IllegalArgumentException("final and abstract are mutually exclusive: " + n);
        }
    }
    
    public static void checkClassSignature(final String s) {
        int checkFormalTypeParameters = 0;
        if (getChar(s, 0) == '<') {
            checkFormalTypeParameters = checkFormalTypeParameters(s, checkFormalTypeParameters);
        }
        int n;
        for (n = checkClassTypeSignature(s, checkFormalTypeParameters); getChar(s, n) == 'L'; n = checkClassTypeSignature(s, n)) {}
        if (n != s.length()) {
            throw new IllegalArgumentException(s + ": error at index " + n);
        }
    }
    
    public static void checkMethodSignature(final String s) {
        int checkFormalTypeParameters = 0;
        if (getChar(s, 0) == '<') {
            checkFormalTypeParameters = checkFormalTypeParameters(s, checkFormalTypeParameters);
        }
        int n;
        for (n = checkChar('(', s, checkFormalTypeParameters); "ZCBSIFJDL[T".indexOf(getChar(s, n)) != -1; n = checkTypeSignature(s, n)) {}
        int n2 = checkChar(')', s, n);
        if (getChar(s, n2) == 'V') {
            ++n2;
        }
        else {
            n2 = checkTypeSignature(s, n2);
        }
        while (getChar(s, n2) == '^') {
            ++n2;
            if (getChar(s, n2) == 'L') {
                n2 = checkClassTypeSignature(s, n2);
            }
            else {
                n2 = checkTypeVariableSignature(s, n2);
            }
        }
        if (n2 != s.length()) {
            throw new IllegalArgumentException(s + ": error at index " + n2);
        }
    }
    
    public static void checkFieldSignature(final String s) {
        final int checkFieldTypeSignature = checkFieldTypeSignature(s, 0);
        if (checkFieldTypeSignature != s.length()) {
            throw new IllegalArgumentException(s + ": error at index " + checkFieldTypeSignature);
        }
    }
    
    static void checkTypeRefAndPath(final int n, final TypePath typePath) {
        int n2 = 0;
        switch (n >>> 24) {
            case 0:
            case 1:
            case 22: {
                n2 = -65536;
                break;
            }
            case 19:
            case 20:
            case 21:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 70: {
                n2 = -16777216;
                break;
            }
            case 16:
            case 17:
            case 18:
            case 23:
            case 66: {
                n2 = -256;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                n2 = -16776961;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n >>> 24));
            }
        }
        if ((n & ~n2) != 0x0) {
            throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(n));
        }
        if (typePath != null) {
            for (int i = 0; i < typePath.getLength(); ++i) {
                final int step = typePath.getStep(i);
                if (step != 0 && step != 1 && step != 3 && step != 2) {
                    throw new IllegalArgumentException("Invalid type path step " + i + " in " + typePath);
                }
                if (step != 3 && typePath.getStepArgument(i) != 0) {
                    throw new IllegalArgumentException("Invalid type path step argument for step " + i + " in " + typePath);
                }
            }
        }
    }
    
    private static int checkFormalTypeParameters(final String s, int n) {
        for (n = checkChar('<', s, n), n = checkFormalTypeParameter(s, n); getChar(s, n) != '>'; n = checkFormalTypeParameter(s, n)) {}
        return n + 1;
    }
    
    private static int checkFormalTypeParameter(final String s, int n) {
        n = checkIdentifier(s, n);
        n = checkChar(':', s, n);
        if ("L[T".indexOf(getChar(s, n)) != -1) {
            n = checkFieldTypeSignature(s, n);
        }
        while (getChar(s, n) == ':') {
            n = checkFieldTypeSignature(s, n + 1);
        }
        return n;
    }
    
    private static int checkFieldTypeSignature(final String s, final int n) {
        switch (getChar(s, n)) {
            case 'L': {
                return checkClassTypeSignature(s, n);
            }
            case '[': {
                return checkTypeSignature(s, n + 1);
            }
            default: {
                return checkTypeVariableSignature(s, n);
            }
        }
    }
    
    private static int checkClassTypeSignature(final String s, int n) {
        for (n = checkChar('L', s, n), n = checkIdentifier(s, n); getChar(s, n) == '/'; n = checkIdentifier(s, n + 1)) {}
        if (getChar(s, n) == '<') {
            n = checkTypeArguments(s, n);
        }
        while (getChar(s, n) == '.') {
            n = checkIdentifier(s, n + 1);
            if (getChar(s, n) == '<') {
                n = checkTypeArguments(s, n);
            }
        }
        return checkChar(';', s, n);
    }
    
    private static int checkTypeArguments(final String s, int n) {
        for (n = checkChar('<', s, n), n = checkTypeArgument(s, n); getChar(s, n) != '>'; n = checkTypeArgument(s, n)) {}
        return n + 1;
    }
    
    private static int checkTypeArgument(final String s, int n) {
        final char char1 = getChar(s, n);
        if (char1 == '*') {
            return n + 1;
        }
        if (char1 == '+' || char1 == '-') {
            ++n;
        }
        return checkFieldTypeSignature(s, n);
    }
    
    private static int checkTypeVariableSignature(final String s, int n) {
        n = checkChar('T', s, n);
        n = checkIdentifier(s, n);
        return checkChar(';', s, n);
    }
    
    private static int checkTypeSignature(final String s, final int n) {
        switch (getChar(s, n)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z': {
                return n + 1;
            }
            default: {
                return checkFieldTypeSignature(s, n);
            }
        }
    }
    
    private static int checkIdentifier(final String s, int n) {
        if (!Character.isJavaIdentifierStart(getChar(s, n))) {
            throw new IllegalArgumentException(s + ": identifier expected at index " + n);
        }
        ++n;
        while (Character.isJavaIdentifierPart(getChar(s, n))) {
            ++n;
        }
        return n;
    }
    
    private static int checkChar(final char c, final String s, final int n) {
        if (getChar(s, n) == c) {
            return n + 1;
        }
        throw new IllegalArgumentException(s + ": '" + c + "' expected at index " + n);
    }
    
    private static char getChar(final String s, final int n) {
        return (n < s.length()) ? s.charAt(n) : '\0';
    }
}
