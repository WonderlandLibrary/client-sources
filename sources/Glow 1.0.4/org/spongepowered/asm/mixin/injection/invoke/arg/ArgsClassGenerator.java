package org.spongepowered.asm.mixin.injection.invoke.arg;

import org.spongepowered.asm.mixin.transformer.ext.*;
import com.google.common.collect.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.util.*;
import org.spongepowered.asm.util.asm.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;

public final class ArgsClassGenerator implements IClassGenerator
{
    public static final String ARGS_NAME;
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
    private int nextIndex;
    private final BiMap<String, String> classNames;
    private final Map<String, byte[]> classBytes;
    
    public ArgsClassGenerator() {
        super();
        this.nextIndex = 1;
        this.classNames = (BiMap<String, String>)HashBiMap.create();
        this.classBytes = new HashMap<String, byte[]>();
    }
    
    public String getClassName(final String s) {
        final String changeDescriptorReturnType = Bytecode.changeDescriptorReturnType(s, "V");
        String format = this.classNames.get(changeDescriptorReturnType);
        if (format == null) {
            format = String.format("%s%d", "org.spongepowered.asm.synthetic.args.Args$", this.nextIndex++);
            this.classNames.put(changeDescriptorReturnType, format);
        }
        return format;
    }
    
    public String getClassRef(final String s) {
        return this.getClassName(s).replace('.', '/');
    }
    
    @Override
    public byte[] generate(final String s) {
        return this.getBytes(s);
    }
    
    public byte[] getBytes(final String s) {
        byte[] generateClass = this.classBytes.get(s);
        if (generateClass == null) {
            final String s2 = this.classNames.inverse().get(s);
            if (s2 == null) {
                return null;
            }
            generateClass = this.generateClass(s, s2);
            this.classBytes.put(s, generateClass);
        }
        return generateClass;
    }
    
    private byte[] generateClass(final String s, final String s2) {
        final String replace = s.replace('.', '/');
        final Type[] argumentTypes = Type.getArgumentTypes(s2);
        ClassVisitor classVisitor;
        final ClassWriter classWriter = (ClassWriter)(classVisitor = new ClassWriter(2));
        if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
            classVisitor = new CheckClassAdapter(classWriter);
        }
        classVisitor.visit(50, 4129, replace, null, ArgsClassGenerator.ARGS_REF, null);
        classVisitor.visitSource(s.substring(s.lastIndexOf(46) + 1) + ".java", null);
        this.generateCtor(replace, s2, argumentTypes, classVisitor);
        this.generateToString(replace, s2, argumentTypes, classVisitor);
        this.generateFactory(replace, s2, argumentTypes, classVisitor);
        this.generateSetters(replace, s2, argumentTypes, classVisitor);
        this.generateGetters(replace, s2, argumentTypes, classVisitor);
        classVisitor.visitEnd();
        return classWriter.toByteArray();
    }
    
    private void generateCtor(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        final MethodVisitor visitMethod = classVisitor.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(25, 0);
        visitMethod.visitVarInsn(25, 1);
        visitMethod.visitMethodInsn(183, ArgsClassGenerator.ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
        visitMethod.visitInsn(177);
        visitMethod.visitMaxs(2, 2);
        visitMethod.visitEnd();
    }
    
    private void generateToString(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        final MethodVisitor visitMethod = classVisitor.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
        visitMethod.visitCode();
        visitMethod.visitLdcInsn("Args" + getSignature(array));
        visitMethod.visitInsn(176);
        visitMethod.visitMaxs(1, 1);
        visitMethod.visitEnd();
    }
    
    private void generateFactory(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        final MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(9, "of", Bytecode.changeDescriptorReturnType(s2, "L" + s + ";"), null, null));
        methodVisitorEx.visitCode();
        methodVisitorEx.visitTypeInsn(187, s);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitConstant((byte)array.length);
        methodVisitorEx.visitTypeInsn(189, "java/lang/Object");
        byte b = 0;
        for (final Type type : array) {
            methodVisitorEx.visitInsn(89);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitVarInsn(type.getOpcode(21), b);
            box(methodVisitorEx, type);
            methodVisitorEx.visitInsn(83);
            b += (byte)type.getSize();
        }
        methodVisitorEx.visitMethodInsn(183, s, "<init>", "([Ljava/lang/Object;)V", false);
        methodVisitorEx.visitInsn(176);
        methodVisitorEx.visitMaxs(6, Bytecode.getArgsSize(array));
        methodVisitorEx.visitEnd();
    }
    
    private void generateGetters(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        byte b = 0;
        for (final Type type : array) {
            final MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, "$" + b, "()" + type.getDescriptor(), null, null));
            methodVisitorEx.visitCode();
            methodVisitorEx.visitVarInsn(25, 0);
            methodVisitorEx.visitFieldInsn(180, s, "values", "[Ljava/lang/Object;");
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitInsn(50);
            unbox(methodVisitorEx, type);
            methodVisitorEx.visitInsn(type.getOpcode(172));
            methodVisitorEx.visitMaxs(2, 1);
            methodVisitorEx.visitEnd();
            ++b;
        }
    }
    
    private void generateSetters(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        this.generateIndexedSetter(s, s2, array, classVisitor);
        this.generateMultiSetter(s, s2, array, classVisitor);
    }
    
    private void generateIndexedSetter(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        final MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
        methodVisitorEx.visitCode();
        final Label label = new Label();
        final Label label2 = new Label();
        final Label[] array2 = new Label[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = new Label();
        }
        methodVisitorEx.visitVarInsn(25, 0);
        methodVisitorEx.visitFieldInsn(180, s, "values", "[Ljava/lang/Object;");
        for (byte b = 0; b < array.length; ++b) {
            methodVisitorEx.visitVarInsn(21, 1);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitJumpInsn(159, array2[b]);
        }
        throwAIOOBE(methodVisitorEx, 1);
        for (int j = 0; j < array.length; ++j) {
            final String boxingType = Bytecode.getBoxingType(array[j]);
            methodVisitorEx.visitLabel(array2[j]);
            methodVisitorEx.visitVarInsn(21, 1);
            methodVisitorEx.visitVarInsn(25, 2);
            methodVisitorEx.visitTypeInsn(192, (boxingType != null) ? boxingType : array[j].getInternalName());
            methodVisitorEx.visitJumpInsn(167, (boxingType != null) ? label2 : label);
        }
        methodVisitorEx.visitLabel(label2);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitJumpInsn(199, label);
        throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
        methodVisitorEx.visitLabel(label);
        methodVisitorEx.visitInsn(83);
        methodVisitorEx.visitInsn(177);
        methodVisitorEx.visitMaxs(6, 3);
        methodVisitorEx.visitEnd();
    }
    
    private void generateMultiSetter(final String s, final String s2, final Type[] array, final ClassVisitor classVisitor) {
        final MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
        methodVisitorEx.visitCode();
        final Label label = new Label();
        final Label label2 = new Label();
        int n = 6;
        methodVisitorEx.visitVarInsn(25, 1);
        methodVisitorEx.visitInsn(190);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitConstant((byte)array.length);
        methodVisitorEx.visitJumpInsn(159, label);
        methodVisitorEx.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitInsn(93);
        methodVisitorEx.visitInsn(88);
        methodVisitorEx.visitConstant((byte)array.length);
        methodVisitorEx.visitLdcInsn(getSignature(array));
        methodVisitorEx.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
        methodVisitorEx.visitInsn(191);
        methodVisitorEx.visitLabel(label);
        methodVisitorEx.visitInsn(87);
        methodVisitorEx.visitVarInsn(25, 0);
        methodVisitorEx.visitFieldInsn(180, s, "values", "[Ljava/lang/Object;");
        for (byte b = 0; b < array.length; ++b) {
            methodVisitorEx.visitInsn(89);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitVarInsn(25, 1);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitInsn(50);
            final String boxingType = Bytecode.getBoxingType(array[b]);
            methodVisitorEx.visitTypeInsn(192, (boxingType != null) ? boxingType : array[b].getInternalName());
            if (boxingType != null) {
                methodVisitorEx.visitInsn(89);
                methodVisitorEx.visitJumpInsn(198, label2);
                n = 7;
            }
            methodVisitorEx.visitInsn(83);
        }
        methodVisitorEx.visitInsn(177);
        methodVisitorEx.visitLabel(label2);
        throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
        methodVisitorEx.visitInsn(177);
        methodVisitorEx.visitMaxs(n, 2);
        methodVisitorEx.visitEnd();
    }
    
    private static void throwNPE(final MethodVisitorEx methodVisitorEx, final String s) {
        methodVisitorEx.visitTypeInsn(187, "java/lang/NullPointerException");
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitLdcInsn(s);
        methodVisitorEx.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitorEx.visitInsn(191);
    }
    
    private static void throwAIOOBE(final MethodVisitorEx methodVisitorEx, final int n) {
        methodVisitorEx.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitVarInsn(21, n);
        methodVisitorEx.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
        methodVisitorEx.visitInsn(191);
    }
    
    private static void box(final MethodVisitor methodVisitor, final Type type) {
        final String boxingType = Bytecode.getBoxingType(type);
        if (boxingType != null) {
            methodVisitor.visitMethodInsn(184, boxingType, "valueOf", String.format("(%s)L%s;", type.getDescriptor(), boxingType), false);
        }
    }
    
    private static void unbox(final MethodVisitor methodVisitor, final Type type) {
        final String boxingType = Bytecode.getBoxingType(type);
        if (boxingType != null) {
            final String unboxingMethod = Bytecode.getUnboxingMethod(type);
            final String string = "()" + type.getDescriptor();
            methodVisitor.visitTypeInsn(192, boxingType);
            methodVisitor.visitMethodInsn(182, boxingType, unboxingMethod, string, false);
        }
        else {
            methodVisitor.visitTypeInsn(192, type.getInternalName());
        }
    }
    
    private static String getSignature(final Type[] array) {
        return new SignaturePrinter("", null, array).setFullyQualified(true).getFormattedArgs();
    }
    
    static {
        ARGS_NAME = Args.class.getName();
        ARGS_REF = ArgsClassGenerator.ARGS_NAME.replace('.', '/');
    }
}
