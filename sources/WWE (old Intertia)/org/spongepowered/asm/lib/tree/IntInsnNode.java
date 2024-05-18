package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class IntInsnNode extends AbstractInsnNode
{
    public int operand;
    
    public IntInsnNode(final int n, final int operand) {
        super(n);
        this.operand = operand;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 1;
    }
    
    @Override
    public void accept(final MethodVisitor methodVisitor) {
        methodVisitor.visitIntInsn(this.opcode, this.operand);
        this.acceptAnnotations(methodVisitor);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> map) {
        return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
    }
}
