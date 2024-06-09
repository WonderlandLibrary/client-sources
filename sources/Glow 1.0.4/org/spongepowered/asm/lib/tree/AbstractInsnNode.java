package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public abstract class AbstractInsnNode
{
    public static final int INSN = 0;
    public static final int INT_INSN = 1;
    public static final int VAR_INSN = 2;
    public static final int TYPE_INSN = 3;
    public static final int FIELD_INSN = 4;
    public static final int METHOD_INSN = 5;
    public static final int INVOKE_DYNAMIC_INSN = 6;
    public static final int JUMP_INSN = 7;
    public static final int LABEL = 8;
    public static final int LDC_INSN = 9;
    public static final int IINC_INSN = 10;
    public static final int TABLESWITCH_INSN = 11;
    public static final int LOOKUPSWITCH_INSN = 12;
    public static final int MULTIANEWARRAY_INSN = 13;
    public static final int FRAME = 14;
    public static final int LINE = 15;
    protected int opcode;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    AbstractInsnNode prev;
    AbstractInsnNode next;
    int index;
    
    protected AbstractInsnNode(final int opcode) {
        super();
        this.opcode = opcode;
        this.index = -1;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public abstract int getType();
    
    public AbstractInsnNode getPrevious() {
        return this.prev;
    }
    
    public AbstractInsnNode getNext() {
        return this.next;
    }
    
    public abstract void accept(final MethodVisitor p0);
    
    protected final void acceptAnnotations(final MethodVisitor methodVisitor) {
        for (int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), i = 0; i < n; ++i) {
            final TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(i);
            typeAnnotationNode.accept(methodVisitor.visitInsnAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
        }
        for (int n2 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), j = 0; j < n2; ++j) {
            final TypeAnnotationNode typeAnnotationNode2 = this.invisibleTypeAnnotations.get(j);
            typeAnnotationNode2.accept(methodVisitor.visitInsnAnnotation(typeAnnotationNode2.typeRef, typeAnnotationNode2.typePath, typeAnnotationNode2.desc, false));
        }
    }
    
    public abstract AbstractInsnNode clone(final Map<LabelNode, LabelNode> p0);
    
    static LabelNode clone(final LabelNode labelNode, final Map<LabelNode, LabelNode> map) {
        return map.get(labelNode);
    }
    
    static LabelNode[] clone(final List<LabelNode> list, final Map<LabelNode, LabelNode> map) {
        final LabelNode[] array = new LabelNode[list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = map.get(list.get(i));
        }
        return array;
    }
    
    protected final AbstractInsnNode cloneAnnotations(final AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode.visibleTypeAnnotations != null) {
            this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            for (int i = 0; i < abstractInsnNode.visibleTypeAnnotations.size(); ++i) {
                final TypeAnnotationNode typeAnnotationNode = abstractInsnNode.visibleTypeAnnotations.get(i);
                final TypeAnnotationNode typeAnnotationNode2 = new TypeAnnotationNode(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc);
                typeAnnotationNode.accept(typeAnnotationNode2);
                this.visibleTypeAnnotations.add(typeAnnotationNode2);
            }
        }
        if (abstractInsnNode.invisibleTypeAnnotations != null) {
            this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            for (int j = 0; j < abstractInsnNode.invisibleTypeAnnotations.size(); ++j) {
                final TypeAnnotationNode typeAnnotationNode3 = abstractInsnNode.invisibleTypeAnnotations.get(j);
                final TypeAnnotationNode typeAnnotationNode4 = new TypeAnnotationNode(typeAnnotationNode3.typeRef, typeAnnotationNode3.typePath, typeAnnotationNode3.desc);
                typeAnnotationNode3.accept(typeAnnotationNode4);
                this.invisibleTypeAnnotations.add(typeAnnotationNode4);
            }
        }
        return this;
    }
}
