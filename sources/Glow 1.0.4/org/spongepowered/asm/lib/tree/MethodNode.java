package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class MethodNode extends MethodVisitor
{
    public int access;
    public String name;
    public String desc;
    public String signature;
    public List<String> exceptions;
    public List<ParameterNode> parameters;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public Object annotationDefault;
    public List<AnnotationNode>[] visibleParameterAnnotations;
    public List<AnnotationNode>[] invisibleParameterAnnotations;
    public InsnList instructions;
    public List<TryCatchBlockNode> tryCatchBlocks;
    public int maxStack;
    public int maxLocals;
    public List<LocalVariableNode> localVariables;
    public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
    public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
    private boolean visited;
    
    public MethodNode() {
        this(327680);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public MethodNode(final int n) {
        super(n);
        this.instructions = new InsnList();
    }
    
    public MethodNode(final int n, final String s, final String s2, final String s3, final String[] array) {
        this(327680, n, s, s2, s3, array);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public MethodNode(final int n, final int access, final String name, final String desc, final String signature, final String[] array) {
        super(n);
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = new ArrayList<String>((array == null) ? 0 : array.length);
        if ((access & 0x400) == 0x0) {
            this.localVariables = new ArrayList<LocalVariableNode>(5);
        }
        this.tryCatchBlocks = new ArrayList<TryCatchBlockNode>();
        if (array != null) {
            this.exceptions.addAll(Arrays.asList(array));
        }
        this.instructions = new InsnList();
    }
    
    @Override
    public void visitParameter(final String s, final int n) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<ParameterNode>(5);
        }
        this.parameters.add(new ParameterNode(s, n));
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return new AnnotationNode(new ArrayList<Object>(this, 0) {
            final MethodNode this$0;
            
            MethodNode$1(final MethodNode this$0, final int n) {
                this.this$0 = this$0;
                super(n);
            }
            
            @Override
            public boolean add(final Object annotationDefault) {
                this.this$0.annotationDefault = annotationDefault;
                return super.add(annotationDefault);
            }
        });
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String s, final boolean b) {
        final AnnotationNode annotationNode = new AnnotationNode(s);
        if (b) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.visibleAnnotations.add(annotationNode);
        }
        else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleAnnotations.add(annotationNode);
        }
        return annotationNode;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, s);
        if (b) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.visibleTypeAnnotations.add(typeAnnotationNode);
        }
        else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int n, final String s, final boolean b) {
        final AnnotationNode annotationNode = new AnnotationNode(s);
        if (b) {
            if (this.visibleParameterAnnotations == null) {
                this.visibleParameterAnnotations = (List<AnnotationNode>[])new List[Type.getArgumentTypes(this.desc).length];
            }
            if (this.visibleParameterAnnotations[n] == null) {
                this.visibleParameterAnnotations[n] = new ArrayList<AnnotationNode>(1);
            }
            this.visibleParameterAnnotations[n].add(annotationNode);
        }
        else {
            if (this.invisibleParameterAnnotations == null) {
                this.invisibleParameterAnnotations = (List<AnnotationNode>[])new List[Type.getArgumentTypes(this.desc).length];
            }
            if (this.invisibleParameterAnnotations[n] == null) {
                this.invisibleParameterAnnotations[n] = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleParameterAnnotations[n].add(annotationNode);
        }
        return annotationNode;
    }
    
    @Override
    public void visitAttribute(final Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList<Attribute>(1);
        }
        this.attrs.add(attribute);
    }
    
    @Override
    public void visitCode() {
    }
    
    @Override
    public void visitFrame(final int n, final int n2, final Object[] array, final int n3, final Object[] array2) {
        this.instructions.add(new FrameNode(n, n2, (Object[])((array == null) ? null : this.getLabelNodes(array)), n3, (Object[])((array2 == null) ? null : this.getLabelNodes(array2))));
    }
    
    @Override
    public void visitInsn(final int n) {
        this.instructions.add(new InsnNode(n));
    }
    
    @Override
    public void visitIntInsn(final int n, final int n2) {
        this.instructions.add(new IntInsnNode(n, n2));
    }
    
    @Override
    public void visitVarInsn(final int n, final int n2) {
        this.instructions.add(new VarInsnNode(n, n2));
    }
    
    @Override
    public void visitTypeInsn(final int n, final String s) {
        this.instructions.add(new TypeInsnNode(n, s));
    }
    
    @Override
    public void visitFieldInsn(final int n, final String s, final String s2, final String s3) {
        this.instructions.add(new FieldInsnNode(n, s, s2, s3));
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, s, s2, s3);
            return;
        }
        this.instructions.add(new MethodInsnNode(n, s, s2, s3));
    }
    
    @Override
    public void visitMethodInsn(final int n, final String s, final String s2, final String s3, final boolean b) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, s, s2, s3, b);
            return;
        }
        this.instructions.add(new MethodInsnNode(n, s, s2, s3, b));
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String s, final String s2, final Handle handle, final Object... array) {
        this.instructions.add(new InvokeDynamicInsnNode(s, s2, handle, array));
    }
    
    @Override
    public void visitJumpInsn(final int n, final Label label) {
        this.instructions.add(new JumpInsnNode(n, this.getLabelNode(label)));
    }
    
    @Override
    public void visitLabel(final Label label) {
        this.instructions.add(this.getLabelNode(label));
    }
    
    @Override
    public void visitLdcInsn(final Object o) {
        this.instructions.add(new LdcInsnNode(o));
    }
    
    @Override
    public void visitIincInsn(final int n, final int n2) {
        this.instructions.add(new IincInsnNode(n, n2));
    }
    
    @Override
    public void visitTableSwitchInsn(final int n, final int n2, final Label label, final Label... array) {
        this.instructions.add(new TableSwitchInsnNode(n, n2, this.getLabelNode(label), this.getLabelNodes(array)));
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label label, final int[] array, final Label[] array2) {
        this.instructions.add(new LookupSwitchInsnNode(this.getLabelNode(label), array, this.getLabelNodes(array2)));
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String s, final int n) {
        this.instructions.add(new MultiANewArrayInsnNode(s, n));
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        AbstractInsnNode abstractInsnNode;
        for (abstractInsnNode = this.instructions.getLast(); abstractInsnNode.getOpcode() == -1; abstractInsnNode = abstractInsnNode.getPrevious()) {}
        final TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, s);
        if (b) {
            if (abstractInsnNode.visibleTypeAnnotations == null) {
                abstractInsnNode.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            abstractInsnNode.visibleTypeAnnotations.add(typeAnnotationNode);
        }
        else {
            if (abstractInsnNode.invisibleTypeAnnotations == null) {
                abstractInsnNode.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            abstractInsnNode.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }
    
    @Override
    public void visitTryCatchBlock(final Label label, final Label label2, final Label label3, final String s) {
        this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(label), this.getLabelNode(label2), this.getLabelNode(label3), s));
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int n, final TypePath typePath, final String s, final boolean b) {
        final TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get((n & 0xFFFF00) >> 8);
        final TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, s);
        if (b) {
            if (tryCatchBlockNode.visibleTypeAnnotations == null) {
                tryCatchBlockNode.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            tryCatchBlockNode.visibleTypeAnnotations.add(typeAnnotationNode);
        }
        else {
            if (tryCatchBlockNode.invisibleTypeAnnotations == null) {
                tryCatchBlockNode.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            tryCatchBlockNode.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }
    
    @Override
    public void visitLocalVariable(final String s, final String s2, final String s3, final Label label, final Label label2, final int n) {
        this.localVariables.add(new LocalVariableNode(s, s2, s3, this.getLabelNode(label), this.getLabelNode(label2), n));
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int n, final TypePath typePath, final Label[] array, final Label[] array2, final int[] array3, final String s, final boolean b) {
        final LocalVariableAnnotationNode localVariableAnnotationNode = new LocalVariableAnnotationNode(n, typePath, this.getLabelNodes(array), this.getLabelNodes(array2), array3, s);
        if (b) {
            if (this.visibleLocalVariableAnnotations == null) {
                this.visibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
            }
            this.visibleLocalVariableAnnotations.add(localVariableAnnotationNode);
        }
        else {
            if (this.invisibleLocalVariableAnnotations == null) {
                this.invisibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
            }
            this.invisibleLocalVariableAnnotations.add(localVariableAnnotationNode);
        }
        return localVariableAnnotationNode;
    }
    
    @Override
    public void visitLineNumber(final int n, final Label label) {
        this.instructions.add(new LineNumberNode(n, this.getLabelNode(label)));
    }
    
    @Override
    public void visitMaxs(final int maxStack, final int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
    }
    
    @Override
    public void visitEnd() {
    }
    
    protected LabelNode getLabelNode(final Label label) {
        if (!(label.info instanceof LabelNode)) {
            label.info = new LabelNode();
        }
        return (LabelNode)label.info;
    }
    
    private LabelNode[] getLabelNodes(final Label[] array) {
        final LabelNode[] array2 = new LabelNode[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = this.getLabelNode(array[i]);
        }
        return array2;
    }
    
    private Object[] getLabelNodes(final Object[] array) {
        final Object[] array2 = new Object[array.length];
        for (int i = 0; i < array.length; ++i) {
            Object labelNode = array[i];
            if (labelNode instanceof Label) {
                labelNode = this.getLabelNode((Label)labelNode);
            }
            array2[i] = labelNode;
        }
        return array2;
    }
    
    public void check(final int n) {
        if (n == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            for (int n2 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(), i = 0; i < n2; ++i) {
                final TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get(i);
                if (tryCatchBlockNode.visibleTypeAnnotations != null && tryCatchBlockNode.visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (tryCatchBlockNode.invisibleTypeAnnotations != null && tryCatchBlockNode.invisibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
            }
            for (int j = 0; j < this.instructions.size(); ++j) {
                final AbstractInsnNode value = this.instructions.get(j);
                if (value.visibleTypeAnnotations != null && value.visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (value.invisibleTypeAnnotations != null && value.invisibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (value instanceof MethodInsnNode && ((MethodInsnNode)value).itf != (value.opcode == 185)) {
                    throw new RuntimeException();
                }
            }
            if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }
    
    public void accept(final ClassVisitor classVisitor) {
        final String[] array = new String[this.exceptions.size()];
        this.exceptions.toArray(array);
        final MethodVisitor visitMethod = classVisitor.visitMethod(this.access, this.name, this.desc, this.signature, array);
        if (visitMethod != null) {
            this.accept(visitMethod);
        }
    }
    
    public void accept(final MethodVisitor methodVisitor) {
        for (int n = (this.parameters == null) ? 0 : this.parameters.size(), i = 0; i < n; ++i) {
            final ParameterNode parameterNode = this.parameters.get(i);
            methodVisitor.visitParameter(parameterNode.name, parameterNode.access);
        }
        if (this.annotationDefault != null) {
            final AnnotationVisitor visitAnnotationDefault = methodVisitor.visitAnnotationDefault();
            AnnotationNode.accept(visitAnnotationDefault, null, this.annotationDefault);
            if (visitAnnotationDefault != null) {
                visitAnnotationDefault.visitEnd();
            }
        }
        for (int n2 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(), j = 0; j < n2; ++j) {
            final AnnotationNode annotationNode = this.visibleAnnotations.get(j);
            annotationNode.accept(methodVisitor.visitAnnotation(annotationNode.desc, true));
        }
        for (int n3 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size(), k = 0; k < n3; ++k) {
            final AnnotationNode annotationNode2 = this.invisibleAnnotations.get(k);
            annotationNode2.accept(methodVisitor.visitAnnotation(annotationNode2.desc, false));
        }
        for (int n4 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), l = 0; l < n4; ++l) {
            final TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(l);
            typeAnnotationNode.accept(methodVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
        }
        for (int n5 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), n6 = 0; n6 < n5; ++n6) {
            final TypeAnnotationNode typeAnnotationNode2 = this.invisibleTypeAnnotations.get(n6);
            typeAnnotationNode2.accept(methodVisitor.visitTypeAnnotation(typeAnnotationNode2.typeRef, typeAnnotationNode2.typePath, typeAnnotationNode2.desc, false));
        }
        for (int n7 = (this.visibleParameterAnnotations == null) ? 0 : this.visibleParameterAnnotations.length, n8 = 0; n8 < n7; ++n8) {
            final List<AnnotationNode> list = this.visibleParameterAnnotations[n8];
            if (list != null) {
                for (int n9 = 0; n9 < list.size(); ++n9) {
                    final AnnotationNode annotationNode3 = list.get(n9);
                    annotationNode3.accept(methodVisitor.visitParameterAnnotation(n8, annotationNode3.desc, true));
                }
            }
        }
        for (int n10 = (this.invisibleParameterAnnotations == null) ? 0 : this.invisibleParameterAnnotations.length, n11 = 0; n11 < n10; ++n11) {
            final List<AnnotationNode> list2 = this.invisibleParameterAnnotations[n11];
            if (list2 != null) {
                for (int n12 = 0; n12 < list2.size(); ++n12) {
                    final AnnotationNode annotationNode4 = list2.get(n12);
                    annotationNode4.accept(methodVisitor.visitParameterAnnotation(n11, annotationNode4.desc, false));
                }
            }
        }
        if (this.visited) {
            this.instructions.resetLabels();
        }
        for (int n13 = (this.attrs == null) ? 0 : this.attrs.size(), n14 = 0; n14 < n13; ++n14) {
            methodVisitor.visitAttribute(this.attrs.get(n14));
        }
        if (this.instructions.size() > 0) {
            methodVisitor.visitCode();
            for (int n15 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(), n16 = 0; n16 < n15; ++n16) {
                this.tryCatchBlocks.get(n16).updateIndex(n16);
                this.tryCatchBlocks.get(n16).accept(methodVisitor);
            }
            this.instructions.accept(methodVisitor);
            for (int n17 = (this.localVariables == null) ? 0 : this.localVariables.size(), n18 = 0; n18 < n17; ++n18) {
                this.localVariables.get(n18).accept(methodVisitor);
            }
            for (int n19 = (this.visibleLocalVariableAnnotations == null) ? 0 : this.visibleLocalVariableAnnotations.size(), n20 = 0; n20 < n19; ++n20) {
                this.visibleLocalVariableAnnotations.get(n20).accept(methodVisitor, true);
            }
            for (int n21 = (this.invisibleLocalVariableAnnotations == null) ? 0 : this.invisibleLocalVariableAnnotations.size(), n22 = 0; n22 < n21; ++n22) {
                this.invisibleLocalVariableAnnotations.get(n22).accept(methodVisitor, false);
            }
            methodVisitor.visitMaxs(this.maxStack, this.maxLocals);
            this.visited = true;
        }
        methodVisitor.visitEnd();
    }
}
