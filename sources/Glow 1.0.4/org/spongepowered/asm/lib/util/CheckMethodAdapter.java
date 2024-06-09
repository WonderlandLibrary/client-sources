package org.spongepowered.asm.lib.util;

import java.lang.reflect.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.io.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class CheckMethodAdapter extends MethodVisitor
{
    public int version;
    private int access;
    private boolean startCode;
    private boolean endCode;
    private boolean endMethod;
    private int insnCount;
    private final Map<Label, Integer> labels;
    private Set<Label> usedLabels;
    private int expandedFrames;
    private int compressedFrames;
    private int lastFrame;
    private List<Label> handlers;
    private static final int[] TYPE;
    private static Field labelStatusField;
    
    public CheckMethodAdapter(final MethodVisitor methodVisitor) {
        this(methodVisitor, new HashMap<Label, Integer>());
    }
    
    public CheckMethodAdapter(final MethodVisitor methodVisitor, final Map<Label, Integer> map) {
        this(327680, methodVisitor, map);
        if (this.getClass() != CheckMethodAdapter.class) {
            throw new IllegalStateException();
        }
    }
    
    protected CheckMethodAdapter(final int n, final MethodVisitor methodVisitor, final Map<Label, Integer> labels) {
        super(n, methodVisitor);
        this.lastFrame = -1;
        this.labels = labels;
        this.usedLabels = new HashSet<Label>();
        this.handlers = new ArrayList<Label>();
    }
    
    public CheckMethodAdapter(final int access, final String s, final String s2, final MethodVisitor methodVisitor, final Map<Label, Integer> map) {
        this(new MethodNode(327680, access, s, s2, null, null, methodVisitor) {
            final MethodVisitor val$cmv;
            
            CheckMethodAdapter$1(final int n, final int n2, final String s, final String s2, final String s3, final String[] array, final MethodVisitor val$cmv) {
                this.val$cmv = val$cmv;
                super(n, n2, s, s2, s3, array);
            }
            
            @Override
            public void visitEnd() {
                final Analyzer<BasicValue> analyzer = new Analyzer<BasicValue>(new BasicVerifier());
                try {
                    analyzer.analyze("dummy", this);
                }
                catch (Exception ex) {
                    if (ex instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                        throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
                    }
                    ex.printStackTrace();
                    final StringWriter stringWriter = new StringWriter();
                    final PrintWriter printWriter = new PrintWriter(stringWriter, true);
                    CheckClassAdapter.printAnalyzerResult(this, analyzer, printWriter);
                    printWriter.close();
                    throw new RuntimeException(ex.getMessage() + ' ' + stringWriter.toString());
                }
                this.accept(this.val$cmv);
            }
        }, map);
        this.access = access;
    }
    
    @Override
    public void visitParameter(final String s, final int n) {
        if (s != null) {
            checkUnqualifiedName(this.version, s, "name");
        }
        CheckClassAdapter.checkAccess(n, 36880);
        super.visitParameter(s, n);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        this.checkEndMethod();
        checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(s, b));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        this.checkEndMethod();
        final int n2 = n >>> 24;
        if (n2 != 1 && n2 != 18 && n2 != 20 && n2 != 21 && n2 != 22 && n2 != 23) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(n, typePath, s, b));
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        this.checkEndMethod();
        return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        this.checkEndMethod();
        checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitParameterAnnotation(n, s, b));
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        this.checkEndMethod();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }
    
    @Override
    public void visitCode() {
        if ((this.access & 0x400) != 0x0) {
            throw new RuntimeException("Abstract methods cannot have code");
        }
        this.startCode = true;
        super.visitCode();
    }
    
    @Override
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        if (this.insnCount == this.lastFrame) {
            throw new IllegalStateException("At most one frame can be visited at a given code location.");
        }
        this.lastFrame = this.insnCount;
        int n4 = 0;
        int n5 = 0;
        switch (n) {
            case -1:
            case 0: {
                n4 = 0;
                n5 = 0;
                break;
            }
            case 3: {
                n4 = 0;
                n5 = 0;
                break;
            }
            case 4: {
                n4 = 0;
                n5 = 1;
                break;
            }
            case 1:
            case 2: {
                n4 = 3;
                n5 = 0;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid frame type " + n);
            }
        }
        if (n2 > n4) {
            throw new IllegalArgumentException("Invalid nLocal=" + n2 + " for frame type " + n);
        }
        if (n3 > n5) {
            throw new IllegalArgumentException("Invalid nStack=" + n3 + " for frame type " + n);
        }
        if (n != 2) {
            if (n2 > 0 && (array == null || array.length < n2)) {
                throw new IllegalArgumentException("Array local[] is shorter than nLocal");
            }
            for (int i = 0; i < n2; ++i) {
                this.checkFrameValue(array[i]);
            }
        }
        if (n3 > 0 && (array2 == null || array2.length < n3)) {
            throw new IllegalArgumentException("Array stack[] is shorter than nStack");
        }
        for (int j = 0; j < n3; ++j) {
            this.checkFrameValue(array2[j]);
        }
        if (n == -1) {
            ++this.expandedFrames;
        }
        else {
            ++this.compressedFrames;
        }
        if (this.expandedFrames > 0 && this.compressedFrames > 0) {
            throw new RuntimeException("Expanded and compressed frames must not be mixed.");
        }
        super.visitFrame(n, n2, array, n3, array2);
    }
    
    @Override
    public void visitInsn(final int n) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 0);
        super.visitInsn(n);
        ++this.insnCount;
    }
    
    @Override
    public void visitIntInsn(final int n, final int n2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 1);
        switch (n) {
            case 16: {
                checkSignedByte(n2, "Invalid operand");
                break;
            }
            case 17: {
                checkSignedShort(n2, "Invalid operand");
                break;
            }
            default: {
                if (n2 < 4 || n2 > 11) {
                    throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + n2);
                }
                break;
            }
        }
        super.visitIntInsn(n, n2);
        ++this.insnCount;
    }
    
    @Override
    public void visitVarInsn(final int n, final int n2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 2);
        checkUnsignedShort(n2, "Invalid variable index");
        super.visitVarInsn(n, n2);
        ++this.insnCount;
    }
    
    @Override
    public void visitTypeInsn(final int n, final String s) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 3);
        checkInternalName(s, "type");
        if (n == 187 && s.charAt(0) == '[') {
            throw new IllegalArgumentException("NEW cannot be used to create arrays: " + s);
        }
        super.visitTypeInsn(n, s);
        ++this.insnCount;
    }
    
    @Override
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 4);
        checkInternalName(s, "owner");
        checkUnqualifiedName(this.version, s2, "name");
        checkDesc(s3, false);
        super.visitFieldInsn(n, s, s2, s3);
        ++this.insnCount;
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
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 5);
        if (n != 183 || !"<init>".equals(s2)) {
            checkMethodIdentifier(this.version, s2, "name");
        }
        checkInternalName(s, "owner");
        checkMethodDesc(s3);
        if (n == 182 && b) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (n == 185 && !b) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (n == 183 && b && (this.version & 0xFFFF) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, s, s2, s3, b);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        this.checkStartCode();
        this.checkEndCode();
        checkMethodIdentifier(this.version, s, "name");
        checkMethodDesc(s2);
        if (handle.getTag() != 6 && handle.getTag() != 8) {
            throw new IllegalArgumentException("invalid handle tag " + handle.getTag());
        }
        for (int i = 0; i < array.length; ++i) {
            this.checkLDCConstant(array[i]);
        }
        super.visitInvokeDynamicInsn(s, s2, handle, array);
        ++this.insnCount;
    }
    
    @Override
    public void visitJumpInsn(final int n, final Label label) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(n, 6);
        this.checkLabel(label, false, "label");
        checkNonDebugLabel(label);
        super.visitJumpInsn(n, label);
        this.usedLabels.add(label);
        ++this.insnCount;
    }
    
    @Override
    public void visitLabel(final Label label) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(label, false, "label");
        if (this.labels.get(label) != null) {
            throw new IllegalArgumentException("Already visited label");
        }
        this.labels.put(label, this.insnCount);
        super.visitLabel(label);
    }
    
    @Override
    public void visitLdcInsn(final Object o) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLDCConstant(o);
        super.visitLdcInsn(o);
        ++this.insnCount;
    }
    
    @Override
    public void visitIincInsn(final int n, final int n2) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnsignedShort(n, "Invalid variable index");
        checkSignedShort(n2, "Invalid increment");
        super.visitIincInsn(n, n2);
        ++this.insnCount;
    }
    
    @Override
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        this.checkStartCode();
        this.checkEndCode();
        if (n2 < n) {
            throw new IllegalArgumentException("Max = " + n2 + " must be greater than or equal to min = " + n);
        }
        this.checkLabel(label, false, "default label");
        checkNonDebugLabel(label);
        if (array == null || array.length != n2 - n + 1) {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        for (int i = 0; i < array.length; ++i) {
            this.checkLabel(array[i], false, "label at index " + i);
            checkNonDebugLabel(array[i]);
        }
        super.visitTableSwitchInsn(n, n2, label, array);
        for (int j = 0; j < array.length; ++j) {
            this.usedLabels.add(array[j]);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        this.checkEndCode();
        this.checkStartCode();
        this.checkLabel(label, false, "default label");
        checkNonDebugLabel(label);
        if (array == null || array2 == null || array.length != array2.length) {
            throw new IllegalArgumentException("There must be the same number of keys and labels");
        }
        for (int i = 0; i < array2.length; ++i) {
            this.checkLabel(array2[i], false, "label at index " + i);
            checkNonDebugLabel(array2[i]);
        }
        super.visitLookupSwitchInsn(label, array, array2);
        this.usedLabels.add(label);
        for (int j = 0; j < array2.length; ++j) {
            this.usedLabels.add(array2[j]);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String s, final int n) {
        this.checkStartCode();
        this.checkEndCode();
        checkDesc(s, false);
        if (s.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + s);
        }
        if (n < 1) {
            throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + n);
        }
        if (n > s.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + n);
        }
        super.visitMultiANewArrayInsn(s, n);
        ++this.insnCount;
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        this.checkStartCode();
        this.checkEndCode();
        final int n2 = n >>> 24;
        if (n2 != 67 && n2 != 68 && n2 != 69 && n2 != 70 && n2 != 71 && n2 != 72 && n2 != 73 && n2 != 74 && n2 != 75) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitInsnAnnotation(n, typePath, s, b));
    }
    
    @Override
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(label, false, "start label");
        this.checkLabel(label2, false, "end label");
        this.checkLabel(label3, false, "handler label");
        checkNonDebugLabel(label);
        checkNonDebugLabel(label2);
        checkNonDebugLabel(label3);
        if (this.labels.get(label) != null || this.labels.get(label2) != null || this.labels.get(label3) != null) {
            throw new IllegalStateException("Try catch blocks must be visited before their labels");
        }
        if (s != null) {
            checkInternalName(s, "type");
        }
        super.visitTryCatchBlock(label, label2, label3, s);
        this.handlers.add(label);
        this.handlers.add(label2);
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        this.checkStartCode();
        this.checkEndCode();
        final int n2 = n >>> 24;
        if (n2 != 66) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        checkDesc(s, false);
        return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(n, typePath, s, b));
    }
    
    @Override
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnqualifiedName(this.version, s, "name");
        checkDesc(s2, false);
        this.checkLabel(label, true, "start label");
        this.checkLabel(label2, true, "end label");
        checkUnsignedShort(n, "Invalid variable index");
        if (this.labels.get(label2) < this.labels.get(label)) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        super.visitLocalVariable(s, s2, s3, label, label2, n);
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        this.checkStartCode();
        this.checkEndCode();
        final int n2 = n >>> 24;
        if (n2 != 64 && n2 != 65) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        checkDesc(s, false);
        if (array == null || array2 == null || array3 == null || array2.length != array.length || array3.length != array.length) {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
        }
        for (int i = 0; i < array.length; ++i) {
            this.checkLabel(array[i], true, "start label");
            this.checkLabel(array2[i], true, "end label");
            checkUnsignedShort(array3[i], "Invalid variable index");
            if (this.labels.get(array2[i]) < this.labels.get(array[i])) {
                throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
            }
        }
        return super.visitLocalVariableAnnotation(n, typePath, array, array2, array3, s, b);
    }
    
    @Override
    public void visitLineNumber(final int n, final Label label) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnsignedShort(n, "Invalid line number");
        this.checkLabel(label, true, "start label");
        super.visitLineNumber(n, label);
    }
    
    @Override
    public void visitMaxs(final int n, final int n2) {
        this.checkStartCode();
        this.checkEndCode();
        this.endCode = true;
        final Iterator<Label> iterator = this.usedLabels.iterator();
        while (iterator.hasNext()) {
            if (this.labels.get(iterator.next()) == null) {
                throw new IllegalStateException("Undefined label used");
            }
        }
        int i = 0;
        while (i < this.handlers.size()) {
            final Integer n3 = this.labels.get(this.handlers.get(i++));
            final Integer n4 = this.labels.get(this.handlers.get(i++));
            if (n3 == null || n4 == null) {
                throw new IllegalStateException("Undefined try catch block labels");
            }
            if (n4 <= n3) {
                throw new IllegalStateException("Emty try catch block handler range");
            }
        }
        checkUnsignedShort(n, "Invalid max stack");
        checkUnsignedShort(n2, "Invalid max locals");
        super.visitMaxs(n, n2);
    }
    
    @Override
    public void visitEnd() {
        this.checkEndMethod();
        this.endMethod = true;
        super.visitEnd();
    }
    
    void checkStartCode() {
        if (!this.startCode) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
        }
    }
    
    void checkEndCode() {
        if (this.endCode) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
        }
    }
    
    void checkEndMethod() {
        if (this.endMethod) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
        }
    }
    
    void checkFrameValue(final Object o) {
        if (o == Opcodes.TOP || o == Opcodes.INTEGER || o == Opcodes.FLOAT || o == Opcodes.LONG || o == Opcodes.DOUBLE || o == Opcodes.NULL || o == Opcodes.UNINITIALIZED_THIS) {
            return;
        }
        if (o instanceof String) {
            checkInternalName((String)o, "Invalid stack frame value");
            return;
        }
        if (!(o instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + o);
        }
        this.usedLabels.add((Label)o);
    }
    
    static void checkOpcode(final int n, final int n2) {
        if (n < 0 || n > 199 || CheckMethodAdapter.TYPE[n] != n2) {
            throw new IllegalArgumentException("Invalid opcode: " + n);
        }
    }
    
    static void checkSignedByte(final int n, final String s) {
        if (n < -128 || n > 127) {
            throw new IllegalArgumentException(s + " (must be a signed byte): " + n);
        }
    }
    
    static void checkSignedShort(final int n, final String s) {
        if (n < -32768 || n > 32767) {
            throw new IllegalArgumentException(s + " (must be a signed short): " + n);
        }
    }
    
    static void checkUnsignedShort(final int n, final String s) {
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException(s + " (must be an unsigned short): " + n);
        }
    }
    
    static void checkConstant(final Object o) {
        if (!(o instanceof Integer) && !(o instanceof Float) && !(o instanceof Long) && !(o instanceof Double) && !(o instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + o);
        }
    }
    
    void checkLDCConstant(final Object o) {
        if (o instanceof Type) {
            final int sort = ((Type)o).getSort();
            if (sort != 10 && sort != 9 && sort != 11) {
                throw new IllegalArgumentException("Illegal LDC constant value");
            }
            if (sort != 11 && (this.version & 0xFFFF) < 49) {
                throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
            }
            if (sort == 11 && (this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
            }
        }
        else if (o instanceof Handle) {
            if ((this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
            }
            final int tag = ((Handle)o).getTag();
            if (tag < 1 || tag > 9) {
                throw new IllegalArgumentException("invalid handle tag " + tag);
            }
        }
        else {
            checkConstant(o);
        }
    }
    
    static void checkUnqualifiedName(final int n, final String s, final String s2) {
        if ((n & 0xFFFF) < 49) {
            checkIdentifier(s, s2);
        }
        else {
            for (int i = 0; i < s.length(); ++i) {
                if (".;[/".indexOf(s.charAt(i)) != -1) {
                    throw new IllegalArgumentException("Invalid " + s2 + " (must be a valid unqualified name): " + s);
                }
            }
        }
    }
    
    static void checkIdentifier(final String s, final String s2) {
        checkIdentifier(s, 0, -1, s2);
    }
    
    static void checkIdentifier(final String s, final int n, final int n2, final String s2) {
        if (s != null) {
            if (n2 == -1) {
                if (s.length() <= n) {
                    throw new IllegalArgumentException("Invalid " + s2 + " (must not be null or empty)");
                }
            }
            else if (n2 <= n) {
                throw new IllegalArgumentException("Invalid " + s2 + " (must not be null or empty)");
            }
            if (!Character.isJavaIdentifierStart(s.charAt(n))) {
                throw new IllegalArgumentException("Invalid " + s2 + " (must be a valid Java identifier): " + s);
            }
            for (int n3 = (n2 == -1) ? s.length() : n2, i = n + 1; i < n3; ++i) {
                if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                    throw new IllegalArgumentException("Invalid " + s2 + " (must be a valid Java identifier): " + s);
                }
            }
            return;
        }
        throw new IllegalArgumentException("Invalid " + s2 + " (must not be null or empty)");
    }
    
    static void checkMethodIdentifier(final int n, final String s, final String s2) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("Invalid " + s2 + " (must not be null or empty)");
        }
        if ((n & 0xFFFF) >= 49) {
            for (int i = 0; i < s.length(); ++i) {
                if (".;[/<>".indexOf(s.charAt(i)) != -1) {
                    throw new IllegalArgumentException("Invalid " + s2 + " (must be a valid unqualified name): " + s);
                }
            }
            return;
        }
        if (!Character.isJavaIdentifierStart(s.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + s2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + s);
        }
        for (int j = 1; j < s.length(); ++j) {
            if (!Character.isJavaIdentifierPart(s.charAt(j))) {
                throw new IllegalArgumentException("Invalid " + s2 + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + s);
            }
        }
    }
    
    static void checkInternalName(final String s, final String s2) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("Invalid " + s2 + " (must not be null or empty)");
        }
        if (s.charAt(0) == '[') {
            checkDesc(s, false);
        }
        else {
            checkInternalName(s, 0, -1, s2);
        }
    }
    
    static void checkInternalName(final String s, final int n, final int n2, final String s2) {
        final int n3 = (n2 == -1) ? s.length() : n2;
        try {
            int n4 = n;
            int i;
            do {
                i = s.indexOf(47, n4 + 1);
                if (i == -1 || i > n3) {
                    i = n3;
                }
                checkIdentifier(s, n4, i, null);
                n4 = i + 1;
            } while (i != n3);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid " + s2 + " (must be a fully qualified class name in internal form): " + s);
        }
    }
    
    static void checkDesc(final String s, final boolean b) {
        if (checkDesc(s, 0, b) != s.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + s);
        }
    }
    
    static int checkDesc(final String s, final int n, final boolean b) {
        if (s == null || n >= s.length()) {
            throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
        }
        switch (s.charAt(n)) {
            case 'V': {
                if (b) {
                    return n + 1;
                }
                throw new IllegalArgumentException("Invalid descriptor: " + s);
            }
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
            case '[': {
                int n2;
                for (n2 = n + 1; n2 < s.length() && s.charAt(n2) == '['; ++n2) {}
                if (n2 < s.length()) {
                    return checkDesc(s, n2, false);
                }
                throw new IllegalArgumentException("Invalid descriptor: " + s);
            }
            case 'L': {
                final int index = s.indexOf(59, n);
                if (index == -1 || index - n < 2) {
                    throw new IllegalArgumentException("Invalid descriptor: " + s);
                }
                try {
                    checkInternalName(s, n + 1, index, null);
                }
                catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Invalid descriptor: " + s);
                }
                return index + 1;
            }
            default: {
                throw new IllegalArgumentException("Invalid descriptor: " + s);
            }
        }
    }
    
    static void checkMethodDesc(final String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
        }
        if (s.charAt(0) != '(' || s.length() < 3) {
            throw new IllegalArgumentException("Invalid descriptor: " + s);
        }
        int checkDesc = 1;
        Label_0143: {
            if (s.charAt(checkDesc) != ')') {
                while (s.charAt(checkDesc) != 'V') {
                    checkDesc = checkDesc(s, checkDesc, false);
                    if (checkDesc >= s.length() || s.charAt(checkDesc) == ')') {
                        break Label_0143;
                    }
                }
                throw new IllegalArgumentException("Invalid descriptor: " + s);
            }
        }
        if (checkDesc(s, checkDesc + 1, true) != s.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + s);
        }
    }
    
    void checkLabel(final Label label, final boolean b, final String s) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid " + s + " (must not be null)");
        }
        if (b && this.labels.get(label) == null) {
            throw new IllegalArgumentException("Invalid " + s + " (must be visited first)");
        }
    }
    
    private static void checkNonDebugLabel(final Label label) {
        final Field labelStatusField = getLabelStatusField();
        int n;
        try {
            n = (int)((labelStatusField == null) ? 0 : labelStatusField.get(label));
        }
        catch (IllegalAccessException ex) {
            throw new Error("Internal error");
        }
        if ((n & 0x1) != 0x0) {
            throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
        }
    }
    
    private static Field getLabelStatusField() {
        if (CheckMethodAdapter.labelStatusField == null) {
            CheckMethodAdapter.labelStatusField = getLabelField("a");
            if (CheckMethodAdapter.labelStatusField == null) {
                CheckMethodAdapter.labelStatusField = getLabelField("status");
            }
        }
        return CheckMethodAdapter.labelStatusField;
    }
    
    private static Field getLabelField(final String s) {
        try {
            final Field declaredField = Label.class.getDeclaredField(s);
            declaredField.setAccessible(true);
            return declaredField;
        }
        catch (NoSuchFieldException ex) {
            return null;
        }
    }
    
    static {
        final String s = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
        TYPE = new int[s.length()];
        for (int i = 0; i < CheckMethodAdapter.TYPE.length; ++i) {
            CheckMethodAdapter.TYPE[i] = s.charAt(i) - 'A' - 1;
        }
    }
}
