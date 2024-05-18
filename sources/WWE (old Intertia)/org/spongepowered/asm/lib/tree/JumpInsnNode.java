package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class JumpInsnNode extends AbstractInsnNode
{
    public LabelNode label;
    
    public JumpInsnNode(final int n, final LabelNode label) {
        super(n);
        this.label = label;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 7;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitJumpInsn(this.opcode, this.label.getLabel());
        this.acceptAnnotations(methodVisitor);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> map) {
        return new JumpInsnNode(this.opcode, AbstractInsnNode.clone(this.label, map)).cloneAnnotations(this);
    }
}
