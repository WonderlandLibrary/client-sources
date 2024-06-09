package org.spongepowered.asm.util;

import java.util.regex.*;
import java.io.*;
import org.spongepowered.asm.lib.util.*;
import java.lang.reflect.*;
import org.spongepowered.asm.lib.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.primitives.*;
import java.util.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public final class Bytecode
{
    public static final int[] CONSTANTS_INT;
    public static final int[] CONSTANTS_FLOAT;
    public static final int[] CONSTANTS_DOUBLE;
    public static final int[] CONSTANTS_LONG;
    public static final int[] CONSTANTS_ALL;
    private static final Object[] CONSTANTS_VALUES;
    private static final String[] CONSTANTS_TYPES;
    private static final String[] BOXING_TYPES;
    private static final String[] UNBOXING_METHODS;
    private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS;
    private static Pattern mergeableAnnotationPattern;
    private static final Logger logger;
    
    private Bytecode() {
        super();
    }
    
    public static MethodNode findMethod(final ClassNode classNode, final String s, final String s2) {
        for (final MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(s) && methodNode.desc.equals(s2)) {
                return methodNode;
            }
        }
        return null;
    }
    
    public static AbstractInsnNode findInsn(final MethodNode methodNode, final int n) {
        for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
            if (abstractInsnNode.getOpcode() == n) {
                return abstractInsnNode;
            }
        }
        return null;
    }
    
    public static MethodInsnNode findSuperInit(final MethodNode methodNode, final String s) {
        if (!"<init>".equals(methodNode.name)) {
            return null;
        }
        int n = 0;
        for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
            if (abstractInsnNode instanceof TypeInsnNode && abstractInsnNode.getOpcode() == 187) {
                ++n;
            }
            else {
                if (!(abstractInsnNode instanceof MethodInsnNode) || abstractInsnNode.getOpcode() != 183) {
                    continue;
                }
                final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                if (!"<init>".equals(methodInsnNode.name)) {
                    continue;
                }
                if (n > 0) {
                    --n;
                }
                else {
                    if (methodInsnNode.owner.equals(s)) {
                        return methodInsnNode;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    public static void textify(final ClassNode classNode, final OutputStream outputStream) {
        classNode.accept(new TraceClassVisitor(new PrintWriter(outputStream)));
    }
    
    public static void textify(final MethodNode methodNode, final OutputStream outputStream) {
        final TraceClassVisitor traceClassVisitor = new TraceClassVisitor(new PrintWriter(outputStream));
        methodNode.accept(traceClassVisitor.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, methodNode.exceptions.toArray(new String[0])));
        traceClassVisitor.visitEnd();
    }
    
    public static void dumpClass(final ClassNode classNode) {
        final ClassWriter classWriter = new ClassWriter(3);
        classNode.accept(classWriter);
        dumpClass(classWriter.toByteArray());
    }
    
    public static void dumpClass(final byte[] array) {
        CheckClassAdapter.verify(new ClassReader(array), true, new PrintWriter(System.out));
    }
    
    public static void printMethodWithOpcodeIndices(final MethodNode methodNode) {
        System.err.printf("%s%s\n", methodNode.name, methodNode.desc);
        int n = 0;
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        while (iterator.hasNext()) {
            System.err.printf("[%4d] %s\n", n++, describeNode((AbstractInsnNode)iterator.next()));
        }
    }
    
    public static void printMethod(final MethodNode methodNode) {
        System.err.printf("%s%s\n", methodNode.name, methodNode.desc);
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        while (iterator.hasNext()) {
            System.err.print("  ");
            printNode(iterator.next());
        }
    }
    
    public static void printNode(final AbstractInsnNode abstractInsnNode) {
        System.err.printf("%s\n", describeNode(abstractInsnNode));
    }
    
    public static String describeNode(final AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return String.format("   %-14s ", "null");
        }
        if (abstractInsnNode instanceof LabelNode) {
            return String.format("[%s]", ((LabelNode)abstractInsnNode).getLabel());
        }
        final String format = String.format("   %-14s ", abstractInsnNode.getClass().getSimpleName().replace("Node", ""));
        String s;
        if (abstractInsnNode instanceof JumpInsnNode) {
            s = format + String.format("[%s] [%s]", getOpcodeName(abstractInsnNode), ((JumpInsnNode)abstractInsnNode).label.getLabel());
        }
        else if (abstractInsnNode instanceof VarInsnNode) {
            s = format + String.format("[%s] %d", getOpcodeName(abstractInsnNode), ((VarInsnNode)abstractInsnNode).var);
        }
        else if (abstractInsnNode instanceof MethodInsnNode) {
            final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
            s = format + String.format("[%s] %s %s %s", getOpcodeName(abstractInsnNode), methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
        }
        else if (abstractInsnNode instanceof FieldInsnNode) {
            final FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
            s = format + String.format("[%s] %s %s %s", getOpcodeName(abstractInsnNode), fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
        }
        else if (abstractInsnNode instanceof LineNumberNode) {
            final LineNumberNode lineNumberNode = (LineNumberNode)abstractInsnNode;
            s = format + String.format("LINE=[%d] LABEL=[%s]", lineNumberNode.line, lineNumberNode.start.getLabel());
        }
        else if (abstractInsnNode instanceof LdcInsnNode) {
            s = format + ((LdcInsnNode)abstractInsnNode).cst;
        }
        else if (abstractInsnNode instanceof IntInsnNode) {
            s = format + ((IntInsnNode)abstractInsnNode).operand;
        }
        else if (abstractInsnNode instanceof FrameNode) {
            s = format + String.format("[%s] ", getOpcodeName(((FrameNode)abstractInsnNode).type, "H_INVOKEINTERFACE", -1));
        }
        else {
            s = format + String.format("[%s] ", getOpcodeName(abstractInsnNode));
        }
        return s;
    }
    
    public static String getOpcodeName(final AbstractInsnNode abstractInsnNode) {
        return (abstractInsnNode != null) ? getOpcodeName(abstractInsnNode.getOpcode()) : "";
    }
    
    public static String getOpcodeName(final int n) {
        return getOpcodeName(n, "UNINITIALIZED_THIS", 1);
    }
    
    private static String getOpcodeName(final int n, final String s, final int n2) {
        if (n >= n2) {
            int n3 = 0;
            try {
                for (final Field field : Opcodes.class.getDeclaredFields()) {
                    if (n3 != 0 || field.getName().equals(s)) {
                        n3 = 1;
                        if (field.getType() == Integer.TYPE && field.getInt(null) == n) {
                            return field.getName();
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
        return (n >= 0) ? String.valueOf(n) : "UNKNOWN";
    }
    
    public static boolean methodHasLineNumbers(final MethodNode methodNode) {
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof LineNumberNode) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean methodIsStatic(final MethodNode methodNode) {
        return (methodNode.access & 0x8) == 0x8;
    }
    
    public static boolean fieldIsStatic(final FieldNode fieldNode) {
        return (fieldNode.access & 0x8) == 0x8;
    }
    
    public static int getFirstNonArgLocalIndex(final MethodNode methodNode) {
        return getFirstNonArgLocalIndex(Type.getArgumentTypes(methodNode.desc), (methodNode.access & 0x8) == 0x0);
    }
    
    public static int getFirstNonArgLocalIndex(final Type[] array, final boolean b) {
        return getArgsSize(array) + (b ? 1 : 0);
    }
    
    public static int getArgsSize(final Type[] array) {
        int n = 0;
        for (int length = array.length, i = 0; i < length; ++i) {
            n += array[i].getSize();
        }
        return n;
    }
    
    public static void loadArgs(final Type[] array, final InsnList list, final int n) {
        loadArgs(array, list, n, -1);
    }
    
    public static void loadArgs(final Type[] array, final InsnList list, final int n, final int n2) {
        loadArgs(array, list, n, n2, null);
    }
    
    public static void loadArgs(final Type[] array, final InsnList list, final int n, final int n2, final Type[] array2) {
        int n3 = n;
        int n4 = 0;
        for (final Type type : array) {
            list.add(new VarInsnNode(type.getOpcode(21), n3));
            if (array2 != null && n4 < array2.length && array2[n4] != null) {
                list.add(new TypeInsnNode(192, array2[n4].getInternalName()));
            }
            n3 += type.getSize();
            if (n2 >= n && n3 >= n2) {
                return;
            }
            ++n4;
        }
    }
    
    public static Map<LabelNode, LabelNode> cloneLabels(final InsnList list) {
        final HashMap<LabelNode, LabelNode> hashMap = new HashMap<LabelNode, LabelNode>();
        for (final AbstractInsnNode abstractInsnNode : list) {
            if (abstractInsnNode instanceof LabelNode) {
                hashMap.put((LabelNode)abstractInsnNode, new LabelNode(((LabelNode)abstractInsnNode).getLabel()));
            }
        }
        return hashMap;
    }
    
    public static String generateDescriptor(final Object o, final Object... array) {
        final StringBuilder append = new StringBuilder().append('(');
        for (int length = array.length, i = 0; i < length; ++i) {
            append.append(toDescriptor(array[i]));
        }
        return append.append(')').append((o != null) ? toDescriptor(o) : "V").toString();
    }
    
    private static String toDescriptor(final Object o) {
        if (o instanceof String) {
            return (String)o;
        }
        if (o instanceof Type) {
            return o.toString();
        }
        if (o instanceof Class) {
            return Type.getDescriptor((Class<?>)o);
        }
        return (o == null) ? "" : o.toString();
    }
    
    public static String getDescriptor(final Type[] array) {
        return "(" + Joiner.on("").join((Object[])array) + ")";
    }
    
    public static String getDescriptor(final Type[] array, final Type type) {
        return getDescriptor(array) + type.toString();
    }
    
    public static String changeDescriptorReturnType(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        if (s2 == null) {
            return s;
        }
        return s.substring(0, s.lastIndexOf(41) + 1) + s2;
    }
    
    public static String getSimpleName(final Class<? extends Annotation> clazz) {
        return clazz.getSimpleName();
    }
    
    public static String getSimpleName(final AnnotationNode annotationNode) {
        return getSimpleName(annotationNode.desc);
    }
    
    public static String getSimpleName(final String s) {
        return s.substring(Math.max(s.lastIndexOf(47), 0) + 1).replace(";", "");
    }
    
    public static boolean isConstant(final AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode != null && Ints.contains(Bytecode.CONSTANTS_ALL, abstractInsnNode.getOpcode());
    }
    
    public static Object getConstant(final AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (abstractInsnNode instanceof LdcInsnNode) {
            return ((LdcInsnNode)abstractInsnNode).cst;
        }
        if (!(abstractInsnNode instanceof IntInsnNode)) {
            final int index = Ints.indexOf(Bytecode.CONSTANTS_ALL, abstractInsnNode.getOpcode());
            return (index < 0) ? null : Bytecode.CONSTANTS_VALUES[index];
        }
        final int operand = ((IntInsnNode)abstractInsnNode).operand;
        if (abstractInsnNode.getOpcode() == 16 || abstractInsnNode.getOpcode() == 17) {
            return operand;
        }
        throw new IllegalArgumentException("IntInsnNode with invalid opcode " + abstractInsnNode.getOpcode() + " in getConstant");
    }
    
    public static Type getConstantType(final AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (!(abstractInsnNode instanceof LdcInsnNode)) {
            final int index = Ints.indexOf(Bytecode.CONSTANTS_ALL, abstractInsnNode.getOpcode());
            return (index < 0) ? null : Type.getType(Bytecode.CONSTANTS_TYPES[index]);
        }
        final Object cst = ((LdcInsnNode)abstractInsnNode).cst;
        if (cst instanceof Integer) {
            return Type.getType("I");
        }
        if (cst instanceof Float) {
            return Type.getType("F");
        }
        if (cst instanceof Long) {
            return Type.getType("J");
        }
        if (cst instanceof Double) {
            return Type.getType("D");
        }
        if (cst instanceof String) {
            return Type.getType("Ljava/lang/String;");
        }
        if (cst instanceof Type) {
            return Type.getType("Ljava/lang/Class;");
        }
        throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + cst.getClass() + " in getConstant");
    }
    
    public static boolean hasFlag(final ClassNode classNode, final int n) {
        return (classNode.access & n) == n;
    }
    
    public static boolean hasFlag(final MethodNode methodNode, final int n) {
        return (methodNode.access & n) == n;
    }
    
    public static boolean hasFlag(final FieldNode fieldNode, final int n) {
        return (fieldNode.access & n) == n;
    }
    
    public static boolean compareFlags(final MethodNode methodNode, final MethodNode methodNode2, final int n) {
        return hasFlag(methodNode, n) == hasFlag(methodNode2, n);
    }
    
    public static boolean compareFlags(final FieldNode fieldNode, final FieldNode fieldNode2, final int n) {
        return hasFlag(fieldNode, n) == hasFlag(fieldNode2, n);
    }
    
    public static Visibility getVisibility(final MethodNode methodNode) {
        return getVisibility(methodNode.access & 0x7);
    }
    
    public static Visibility getVisibility(final FieldNode fieldNode) {
        return getVisibility(fieldNode.access & 0x7);
    }
    
    private static Visibility getVisibility(final int n) {
        if ((n & 0x4) != 0x0) {
            return Visibility.PROTECTED;
        }
        if ((n & 0x2) != 0x0) {
            return Visibility.PRIVATE;
        }
        if ((n & 0x1) != 0x0) {
            return Visibility.PUBLIC;
        }
        return Visibility.PACKAGE;
    }
    
    public static void setVisibility(final MethodNode methodNode, final Visibility visibility) {
        methodNode.access = setVisibility(methodNode.access, visibility.access);
    }
    
    public static void setVisibility(final FieldNode fieldNode, final Visibility visibility) {
        fieldNode.access = setVisibility(fieldNode.access, visibility.access);
    }
    
    public static void setVisibility(final MethodNode methodNode, final int n) {
        methodNode.access = setVisibility(methodNode.access, n);
    }
    
    public static void setVisibility(final FieldNode fieldNode, final int n) {
        fieldNode.access = setVisibility(fieldNode.access, n);
    }
    
    private static int setVisibility(final int n, final int n2) {
        return (n & 0xFFFFFFF8) | (n2 & 0x7);
    }
    
    public static int getMaxLineNumber(final ClassNode classNode, final int n, final int n2) {
        int max = 0;
        final Iterator<MethodNode> iterator = classNode.methods.iterator();
        while (iterator.hasNext()) {
            for (final AbstractInsnNode abstractInsnNode : iterator.next().instructions) {
                if (abstractInsnNode instanceof LineNumberNode) {
                    max = Math.max(max, ((LineNumberNode)abstractInsnNode).line);
                }
            }
        }
        return Math.max(n, max + n2);
    }
    
    public static String getBoxingType(final Type type) {
        return (type == null) ? null : Bytecode.BOXING_TYPES[type.getSort()];
    }
    
    public static String getUnboxingMethod(final Type type) {
        return (type == null) ? null : Bytecode.UNBOXING_METHODS[type.getSort()];
    }
    
    public static void mergeAnnotations(final ClassNode classNode, final ClassNode classNode2) {
        classNode2.visibleAnnotations = mergeAnnotations(classNode.visibleAnnotations, classNode2.visibleAnnotations, "class", classNode.name);
        classNode2.invisibleAnnotations = mergeAnnotations(classNode.invisibleAnnotations, classNode2.invisibleAnnotations, "class", classNode.name);
    }
    
    public static void mergeAnnotations(final MethodNode methodNode, final MethodNode methodNode2) {
        methodNode2.visibleAnnotations = mergeAnnotations(methodNode.visibleAnnotations, methodNode2.visibleAnnotations, "method", methodNode.name);
        methodNode2.invisibleAnnotations = mergeAnnotations(methodNode.invisibleAnnotations, methodNode2.invisibleAnnotations, "method", methodNode.name);
    }
    
    public static void mergeAnnotations(final FieldNode fieldNode, final FieldNode fieldNode2) {
        fieldNode2.visibleAnnotations = mergeAnnotations(fieldNode.visibleAnnotations, fieldNode2.visibleAnnotations, "field", fieldNode.name);
        fieldNode2.invisibleAnnotations = mergeAnnotations(fieldNode.invisibleAnnotations, fieldNode2.invisibleAnnotations, "field", fieldNode.name);
    }
    
    private static List<AnnotationNode> mergeAnnotations(final List<AnnotationNode> list, List<AnnotationNode> list2, final String s, final String s2) {
        try {
            if (list == null) {
                return list2;
            }
            if (list2 == null) {
                list2 = new ArrayList<AnnotationNode>();
            }
            for (final AnnotationNode annotationNode : list) {
                if (!isMergeableAnnotation(annotationNode)) {
                    continue;
                }
                final Iterator<Object> iterator2 = list2.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().desc.equals(annotationNode.desc)) {
                        iterator2.remove();
                        break;
                    }
                }
                list2.add(annotationNode);
            }
        }
        catch (Exception ex) {
            Bytecode.logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[] { s, s2 });
        }
        return list2;
    }
    
    private static boolean isMergeableAnnotation(final AnnotationNode annotationNode) {
        return !annotationNode.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF) || Bytecode.mergeableAnnotationPattern.matcher(annotationNode.desc).matches();
    }
    
    private static Pattern getMergeableAnnotationPattern() {
        final StringBuilder sb = new StringBuilder("^L(");
        for (int i = 0; i < Bytecode.MERGEABLE_MIXIN_ANNOTATIONS.length; ++i) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(Bytecode.MERGEABLE_MIXIN_ANNOTATIONS[i].getName().replace('.', '/'));
        }
        return Pattern.compile(sb.append(");$").toString());
    }
    
    public static void compareBridgeMethods(final MethodNode methodNode, final MethodNode methodNode2) {
        final ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
        final ListIterator<AbstractInsnNode> iterator2 = methodNode2.instructions.iterator();
        int n = 0;
        while (iterator.hasNext() && iterator2.hasNext()) {
            final AbstractInsnNode abstractInsnNode = iterator.next();
            final AbstractInsnNode abstractInsnNode2 = iterator2.next();
            if (!(abstractInsnNode instanceof LabelNode)) {
                if (abstractInsnNode instanceof MethodInsnNode) {
                    final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                    final MethodInsnNode methodInsnNode2 = (MethodInsnNode)abstractInsnNode2;
                    if (!methodInsnNode.name.equals(methodInsnNode2.name)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, methodNode.name, methodNode.desc, n, abstractInsnNode, abstractInsnNode2);
                    }
                    if (!methodInsnNode.desc.equals(methodInsnNode2.desc)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, methodNode.name, methodNode.desc, n, abstractInsnNode, abstractInsnNode2);
                    }
                }
                else {
                    if (abstractInsnNode.getOpcode() != abstractInsnNode2.getOpcode()) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, methodNode.name, methodNode.desc, n, abstractInsnNode, abstractInsnNode2);
                    }
                    if (abstractInsnNode instanceof VarInsnNode) {
                        if (((VarInsnNode)abstractInsnNode).var != ((VarInsnNode)abstractInsnNode2).var) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, methodNode.name, methodNode.desc, n, abstractInsnNode, abstractInsnNode2);
                        }
                    }
                    else if (abstractInsnNode instanceof TypeInsnNode) {
                        final TypeInsnNode typeInsnNode = (TypeInsnNode)abstractInsnNode;
                        final TypeInsnNode typeInsnNode2 = (TypeInsnNode)abstractInsnNode2;
                        if (typeInsnNode.getOpcode() == 192 && !typeInsnNode.desc.equals(typeInsnNode2.desc)) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, methodNode.name, methodNode.desc, n, abstractInsnNode, abstractInsnNode2);
                        }
                    }
                }
            }
            ++n;
        }
        if (iterator.hasNext() || iterator2.hasNext()) {
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, methodNode.name, methodNode.desc, n, null, null);
        }
    }
    
    static {
        CONSTANTS_INT = new int[] { 2, 3, 4, 5, 6, 7, 8 };
        CONSTANTS_FLOAT = new int[] { 11, 12, 13 };
        CONSTANTS_DOUBLE = new int[] { 14, 15 };
        CONSTANTS_LONG = new int[] { 9, 10 };
        CONSTANTS_ALL = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
        CONSTANTS_VALUES = new Object[] { null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, 0.0f, 1.0f, 2.0f, 0.0, 1.0 };
        CONSTANTS_TYPES = new String[] { null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I" };
        BOXING_TYPES = new String[] { null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null };
        UNBOXING_METHODS = new String[] { null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null };
        MERGEABLE_MIXIN_ANNOTATIONS = new Class[] { Overwrite.class, Intrinsic.class, Final.class, Debug.class };
        Bytecode.mergeableAnnotationPattern = getMergeableAnnotationPattern();
        logger = LogManager.getLogger("mixin");
    }
    
    public enum Visibility
    {
        PRIVATE(2), 
        PROTECTED(4), 
        PACKAGE(0), 
        PUBLIC(1);
        
        static final int MASK = 7;
        final int access;
        private static final Visibility[] $VALUES;
        
        public static Visibility[] values() {
            return Visibility.$VALUES.clone();
        }
        
        public static Visibility valueOf(final String s) {
            return Enum.valueOf(Visibility.class, s);
        }
        
        private Visibility(final int access) {
            this.access = access;
        }
        
        static {
            $VALUES = new Visibility[] { Visibility.PRIVATE, Visibility.PROTECTED, Visibility.PACKAGE, Visibility.PUBLIC };
        }
    }
}
