package org.spongepowered.asm.util;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.asm.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.util.*;

public final class Locals
{
    private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables;
    
    private Locals() {
        super();
    }
    
    public static void loadLocals(final Type[] array, final InsnList list, int n, int n2) {
        while (n < array.length && n2 > 0) {
            if (array[n] != null) {
                list.add(new VarInsnNode(array[n].getOpcode(21), n));
                --n2;
            }
            ++n;
        }
    }
    
    public static LocalVariableNode[] getLocalsAt(final ClassNode classNode, final MethodNode methodNode, AbstractInsnNode nextNode) {
        for (int n = 0; n < 3 && (nextNode instanceof LabelNode || nextNode instanceof LineNumberNode); nextNode = nextNode(methodNode.instructions, nextNode), ++n) {}
        final ClassInfo forName = ClassInfo.forName(classNode.name);
        if (forName == null) {
            throw new LVTGeneratorException("Could not load class metadata for " + classNode.name + " generating LVT for " + methodNode.name);
        }
        final ClassInfo.Method method = forName.findMethod(methodNode);
        if (method == null) {
            throw new LVTGeneratorException("Could not locate method metadata for " + methodNode.name + " generating LVT in " + classNode.name);
        }
        final List<ClassInfo.FrameData> frames = method.getFrames();
        final LocalVariableNode[] array = new LocalVariableNode[methodNode.maxLocals];
        int n2 = 0;
        int n3 = 0;
        if ((methodNode.access & 0x8) == 0x0) {
            array[n2++] = new LocalVariableNode("this", classNode.name, null, null, null, 0);
        }
        for (final Type type : Type.getArgumentTypes(methodNode.desc)) {
            array[n2] = new LocalVariableNode("arg" + n3++, type.toString(), null, null, null, n2);
            n2 += type.getSize();
        }
        final int n4 = n2;
        int n5 = -1;
        int n6 = 0;
        for (final AbstractInsnNode abstractInsnNode : methodNode.instructions) {
            if (abstractInsnNode instanceof FrameNode) {
                ++n5;
                final FrameNode frameNode = (FrameNode)abstractInsnNode;
                final ClassInfo.FrameData frameData = (n5 < frames.size()) ? frames.get(n5) : null;
                n6 = ((frameData != null && frameData.type == 0) ? Math.min(n6, frameData.locals) : frameNode.local.size());
                for (int n7 = 0, j = 0; j < array.length; ++j, ++n7) {
                    final Object o = (n7 < frameNode.local.size()) ? frameNode.local.get(n7) : null;
                    if (o instanceof String) {
                        array[j] = getLocalVariableAt(classNode, methodNode, nextNode, j);
                    }
                    else if (o instanceof Integer) {
                        final boolean b = o == Opcodes.UNINITIALIZED_THIS || o == Opcodes.NULL;
                        final boolean b2 = o == Opcodes.INTEGER || o == Opcodes.FLOAT;
                        final boolean b3 = o == Opcodes.DOUBLE || o == Opcodes.LONG;
                        if (o != Opcodes.TOP) {
                            if (b) {
                                array[j] = null;
                            }
                            else {
                                if (!b2 && !b3) {
                                    throw new LVTGeneratorException("Unrecognised locals opcode " + o + " in locals array at position " + n7 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                                }
                                array[j] = getLocalVariableAt(classNode, methodNode, nextNode, j);
                                if (b3) {
                                    ++j;
                                    array[j] = null;
                                }
                            }
                        }
                    }
                    else {
                        if (o != null) {
                            throw new LVTGeneratorException("Invalid value " + o + " in locals array at position " + n7 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                        }
                        if (j >= n4 && j >= n6 && n6 > 0) {
                            array[j] = null;
                        }
                    }
                }
            }
            else if (abstractInsnNode instanceof VarInsnNode) {
                final VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;
                array[varInsnNode.var] = getLocalVariableAt(classNode, methodNode, nextNode, varInsnNode.var);
            }
            if (abstractInsnNode == nextNode) {
                break;
            }
        }
        for (int k = 0; k < array.length; ++k) {
            if (array[k] != null && array[k].desc == null) {
                array[k] = null;
            }
        }
        return array;
    }
    
    public static LocalVariableNode getLocalVariableAt(final ClassNode classNode, final MethodNode methodNode, final AbstractInsnNode abstractInsnNode, final int n) {
        return getLocalVariableAt(classNode, methodNode, methodNode.instructions.indexOf(abstractInsnNode), n);
    }
    
    private static LocalVariableNode getLocalVariableAt(final ClassNode classNode, final MethodNode methodNode, final int n, final int n2) {
        LocalVariableNode localVariableNode = null;
        LocalVariableNode localVariableNode2 = null;
        for (final LocalVariableNode localVariableNode3 : getLocalVariableTable(classNode, methodNode)) {
            if (localVariableNode3.index != n2) {
                continue;
            }
            if (isOpcodeInRange(methodNode.instructions, localVariableNode3, n)) {
                localVariableNode = localVariableNode3;
            }
            else {
                if (localVariableNode != null) {
                    continue;
                }
                localVariableNode2 = localVariableNode3;
            }
        }
        if (localVariableNode == null && !methodNode.localVariables.isEmpty()) {
            for (final LocalVariableNode localVariableNode4 : getGeneratedLocalVariableTable(classNode, methodNode)) {
                if (localVariableNode4.index == n2 && isOpcodeInRange(methodNode.instructions, localVariableNode4, n)) {
                    localVariableNode = localVariableNode4;
                }
            }
        }
        return (localVariableNode != null) ? localVariableNode : localVariableNode2;
    }
    
    private static boolean isOpcodeInRange(final InsnList list, final LocalVariableNode localVariableNode, final int n) {
        return list.indexOf(localVariableNode.start) < n && list.indexOf(localVariableNode.end) > n;
    }
    
    public static List<LocalVariableNode> getLocalVariableTable(final ClassNode classNode, final MethodNode methodNode) {
        if (methodNode.localVariables.isEmpty()) {
            return getGeneratedLocalVariableTable(classNode, methodNode);
        }
        return methodNode.localVariables;
    }
    
    public static List<LocalVariableNode> getGeneratedLocalVariableTable(final ClassNode classNode, final MethodNode methodNode) {
        final String format = String.format("%s.%s%s", classNode.name, methodNode.name, methodNode.desc);
        final List<LocalVariableNode> list = Locals.calculatedLocalVariables.get(format);
        if (list != null) {
            return list;
        }
        final List<LocalVariableNode> generateLocalVariableTable = generateLocalVariableTable(classNode, methodNode);
        Locals.calculatedLocalVariables.put(format, generateLocalVariableTable);
        return generateLocalVariableTable;
    }
    
    public static List<LocalVariableNode> generateLocalVariableTable(final ClassNode classNode, final MethodNode methodNode) {
        List<Type> list = null;
        if (classNode.interfaces != null) {
            list = new ArrayList<Type>();
            final Iterator<String> iterator = classNode.interfaces.iterator();
            while (iterator.hasNext()) {
                list.add(Type.getObjectType(iterator.next()));
            }
        }
        Type objectType = null;
        if (classNode.superName != null) {
            objectType = Type.getObjectType(classNode.superName);
        }
        final Analyzer analyzer = new Analyzer((Interpreter<Value>)new MixinVerifier(Type.getObjectType(classNode.name), objectType, list, false));
        try {
            analyzer.analyze(classNode.name, methodNode);
        }
        catch (AnalyzerException ex) {
            ex.printStackTrace();
        }
        final Frame[] frames = analyzer.getFrames();
        final int size = methodNode.instructions.size();
        final ArrayList<LocalVariableNode> list2 = new ArrayList<LocalVariableNode>();
        final LocalVariableNode[] array = new LocalVariableNode[methodNode.maxLocals];
        final BasicValue[] array2 = new BasicValue[methodNode.maxLocals];
        final LabelNode[] array3 = new LabelNode[size];
        final String[] array4 = new String[methodNode.maxLocals];
        for (int i = 0; i < size; ++i) {
            final Frame frame = frames[i];
            if (frame != null) {
                LabelNode labelNode = null;
                for (int j = 0; j < frame.getLocals(); ++j) {
                    final BasicValue basicValue = frame.getLocal(j);
                    if (basicValue != null || array2[j] != null) {
                        if (basicValue == null || !basicValue.equals(array2[j])) {
                            if (labelNode == null) {
                                final AbstractInsnNode value = methodNode.instructions.get(i);
                                if (value instanceof LabelNode) {
                                    labelNode = (LabelNode)value;
                                }
                                else {
                                    labelNode = (array3[i] = new LabelNode());
                                }
                            }
                            if (basicValue == null && array2[j] != null) {
                                list2.add(array[j]);
                                array[j].end = labelNode;
                                array[j] = null;
                            }
                            else if (basicValue != null) {
                                if (array2[j] != null) {
                                    list2.add(array[j]);
                                    array[j].end = labelNode;
                                    array[j] = null;
                                }
                                final String s = (basicValue.getType() != null) ? basicValue.getType().getDescriptor() : array4[j];
                                array[j] = new LocalVariableNode("var" + j, s, null, labelNode, null, j);
                                if (s != null) {
                                    array4[j] = s;
                                }
                            }
                            array2[j] = basicValue;
                        }
                    }
                }
            }
        }
        LabelNode end = null;
        for (int k = 0; k < array.length; ++k) {
            if (array[k] != null) {
                if (end == null) {
                    end = new LabelNode();
                    methodNode.instructions.add(end);
                }
                array[k].end = end;
                list2.add(array[k]);
            }
        }
        for (int l = size - 1; l >= 0; --l) {
            if (array3[l] != null) {
                methodNode.instructions.insert(methodNode.instructions.get(l), array3[l]);
            }
        }
        return list2;
    }
    
    private static AbstractInsnNode nextNode(final InsnList list, final AbstractInsnNode abstractInsnNode) {
        final int n = list.indexOf(abstractInsnNode) + 1;
        if (n > 0 && n < list.size()) {
            return list.get(n);
        }
        return abstractInsnNode;
    }
    
    static {
        calculatedLocalVariables = new HashMap<String, List<LocalVariableNode>>();
    }
}
