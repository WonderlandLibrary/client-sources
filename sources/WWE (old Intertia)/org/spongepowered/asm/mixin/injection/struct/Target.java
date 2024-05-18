package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

public class Target implements Comparable<Target>, Iterable<AbstractInsnNode>
{
    public final ClassNode classNode;
    public final MethodNode method;
    public final InsnList insns;
    public final boolean isStatic;
    public final boolean isCtor;
    public final Type[] arguments;
    public final Type returnType;
    private final int maxStack;
    private final int maxLocals;
    private final InjectionNodes injectionNodes;
    private String callbackInfoClass;
    private String callbackDescriptor;
    private int[] argIndices;
    private List<Integer> argMapVars;
    private LabelNode start;
    private LabelNode end;
    
    public Target(final ClassNode classNode, final MethodNode method) {
        super();
        this.injectionNodes = new InjectionNodes();
        this.classNode = classNode;
        this.method = method;
        this.insns = method.instructions;
        this.isStatic = Bytecode.methodIsStatic(method);
        this.isCtor = method.name.equals("<init>");
        this.arguments = Type.getArgumentTypes(method.desc);
        this.returnType = Type.getReturnType(method.desc);
        this.maxStack = method.maxStack;
        this.maxLocals = method.maxLocals;
    }
    
    public InjectionNodes.InjectionNode addInjectionNode(final AbstractInsnNode abstractInsnNode) {
        return this.injectionNodes.add(abstractInsnNode);
    }
    
    public InjectionNodes.InjectionNode getInjectionNode(final AbstractInsnNode abstractInsnNode) {
        return this.injectionNodes.get(abstractInsnNode);
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public int getCurrentMaxLocals() {
        return this.method.maxLocals;
    }
    
    public int getCurrentMaxStack() {
        return this.method.maxStack;
    }
    
    public int allocateLocal() {
        return this.allocateLocals(1);
    }
    
    public int allocateLocals(final int n) {
        final int maxLocals = this.method.maxLocals;
        final MethodNode method = this.method;
        method.maxLocals += n;
        return maxLocals;
    }
    
    public void addToLocals(final int n) {
        this.setMaxLocals(this.maxLocals + n);
    }
    
    public void setMaxLocals(final int maxLocals) {
        if (maxLocals > this.method.maxLocals) {
            this.method.maxLocals = maxLocals;
        }
    }
    
    public void addToStack(final int n) {
        this.setMaxStack(this.maxStack + n);
    }
    
    public void setMaxStack(final int maxStack) {
        if (maxStack > this.method.maxStack) {
            this.method.maxStack = maxStack;
        }
    }
    
    public int[] generateArgMap(final Type[] array, final int n) {
        if (this.argMapVars == null) {
            this.argMapVars = new ArrayList<Integer>();
        }
        final int[] array2 = new int[array.length];
        int i = n;
        int n2 = 0;
        while (i < array.length) {
            final int size = array[i].getSize();
            array2[i] = this.allocateArgMapLocal(n2, size);
            n2 += size;
            ++i;
        }
        return array2;
    }
    
    private int allocateArgMapLocal(final int n, final int n2) {
        if (n >= this.argMapVars.size()) {
            final int allocateLocals = this.allocateLocals(n2);
            for (int i = 0; i < n2; ++i) {
                this.argMapVars.add(allocateLocals + i);
            }
            return allocateLocals;
        }
        final int intValue = this.argMapVars.get(n);
        if (n2 <= 1 || n + n2 <= this.argMapVars.size()) {
            return intValue;
        }
        final int allocateLocals2 = this.allocateLocals(1);
        if (allocateLocals2 == intValue + 1) {
            this.argMapVars.add(allocateLocals2);
            return intValue;
        }
        this.argMapVars.set(n, allocateLocals2);
        this.argMapVars.add(this.allocateLocals(1));
        return allocateLocals2;
    }
    
    public int[] getArgIndices() {
        if (this.argIndices == null) {
            this.argIndices = this.calcArgIndices(this.isStatic ? 0 : 1);
        }
        return this.argIndices;
    }
    
    private int[] calcArgIndices(int n) {
        final int[] array = new int[this.arguments.length];
        for (int i = 0; i < this.arguments.length; ++i) {
            array[i] = n;
            n += this.arguments[i].getSize();
        }
        return array;
    }
    
    public String getCallbackInfoClass() {
        if (this.callbackInfoClass == null) {
            this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
        }
        return this.callbackInfoClass;
    }
    
    public String getSimpleCallbackDescriptor() {
        return String.format("(L%s;)V", this.getCallbackInfoClass());
    }
    
    public String getCallbackDescriptor(final Type[] array, final Type[] array2) {
        return this.getCallbackDescriptor(false, array, array2, 0, 32767);
    }
    
    public String getCallbackDescriptor(final boolean b, final Type[] array, final Type[] array2, final int n, int n2) {
        if (this.callbackDescriptor == null) {
            this.callbackDescriptor = String.format("(%sL%s;)V", this.method.desc.substring(1, this.method.desc.indexOf(41)), this.getCallbackInfoClass());
        }
        if (!b || array == null) {
            return this.callbackDescriptor;
        }
        final StringBuilder sb = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(41)));
        for (int n3 = n; n3 < array.length && n2 > 0; ++n3) {
            if (array[n3] != null) {
                sb.append(array[n3].getDescriptor());
                --n2;
            }
        }
        return sb.append(")V").toString();
    }
    
    @Override
    public String toString() {
        return String.format("%s::%s%s", this.classNode.name, this.method.name, this.method.desc);
    }
    
    @Override
    public int compareTo(final Target target) {
        if (target == null) {
            return 0;
        }
        return this.toString().compareTo(target.toString());
    }
    
    public int indexOf(final InjectionNodes.InjectionNode injectionNode) {
        return this.insns.indexOf(injectionNode.getCurrentTarget());
    }
    
    public int indexOf(final AbstractInsnNode abstractInsnNode) {
        return this.insns.indexOf(abstractInsnNode);
    }
    
    public AbstractInsnNode get(final int n) {
        return this.insns.get(n);
    }
    
    @Override
    public Iterator<AbstractInsnNode> iterator() {
        return this.insns.iterator();
    }
    
    public MethodInsnNode findInitNodeFor(final TypeInsnNode typeInsnNode) {
        final ListIterator<AbstractInsnNode> iterator = this.insns.iterator(this.indexOf(typeInsnNode));
        while (iterator.hasNext()) {
            final AbstractInsnNode abstractInsnNode = iterator.next();
            if (abstractInsnNode instanceof MethodInsnNode && abstractInsnNode.getOpcode() == 183) {
                final MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                if ("<init>".equals(methodInsnNode.name) && methodInsnNode.owner.equals(typeInsnNode.desc)) {
                    return methodInsnNode;
                }
                continue;
            }
        }
        return null;
    }
    
    public MethodInsnNode findSuperInitNode() {
        if (!this.isCtor) {
            return null;
        }
        return Bytecode.findSuperInit(this.method, ClassInfo.forName(this.classNode.name).getSuperName());
    }
    
    public void insertBefore(final InjectionNodes.InjectionNode injectionNode, final InsnList list) {
        this.insns.insertBefore(injectionNode.getCurrentTarget(), list);
    }
    
    public void insertBefore(final AbstractInsnNode abstractInsnNode, final InsnList list) {
        this.insns.insertBefore(abstractInsnNode, list);
    }
    
    public void replaceNode(final AbstractInsnNode abstractInsnNode, final AbstractInsnNode abstractInsnNode2) {
        this.insns.insertBefore(abstractInsnNode, abstractInsnNode2);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }
    
    public void replaceNode(final AbstractInsnNode abstractInsnNode, final AbstractInsnNode abstractInsnNode2, final InsnList list) {
        this.insns.insertBefore(abstractInsnNode, list);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }
    
    public void wrapNode(final AbstractInsnNode abstractInsnNode, final AbstractInsnNode abstractInsnNode2, final InsnList list, final InsnList list2) {
        this.insns.insertBefore(abstractInsnNode, list);
        this.insns.insert(abstractInsnNode, list2);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }
    
    public void replaceNode(final AbstractInsnNode abstractInsnNode, final InsnList list) {
        this.insns.insertBefore(abstractInsnNode, list);
        this.removeNode(abstractInsnNode);
    }
    
    public void removeNode(final AbstractInsnNode abstractInsnNode) {
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.remove(abstractInsnNode);
    }
    
    public void addLocalVariable(final int n, final String s, final String s2) {
        if (this.start == null) {
            this.start = new LabelNode(new Label());
            this.end = new LabelNode(new Label());
            this.insns.insert(this.start);
            this.insns.add(this.end);
        }
        this.addLocalVariable(n, s, s2, this.start, this.end);
    }
    
    private void addLocalVariable(final int n, final String s, final String s2, final LabelNode labelNode, final LabelNode labelNode2) {
        if (this.method.localVariables == null) {
            this.method.localVariables = new ArrayList<LocalVariableNode>();
        }
        this.method.localVariables.add(new LocalVariableNode(s, s2, null, labelNode, labelNode2, n));
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Target)o);
    }
}
