package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

public class ModifyConstantInjector extends RedirectInjector
{
    private static final int OPCODE_OFFSET = 6;
    
    public ModifyConstantInjector(final InjectionInfo injectionInfo) {
        super(injectionInfo, "@ModifyConstant");
    }
    
    @Override
    protected void inject(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        if (!this.preInject(injectionNode)) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
        }
        final AbstractInsnNode currentTarget = injectionNode.getCurrentTarget();
        if (currentTarget instanceof JumpInsnNode) {
            this.checkTargetModifiers(target, false);
            this.injectExpandedConstantModifier(target, (JumpInsnNode)currentTarget);
            return;
        }
        if (Bytecode.isConstant(currentTarget)) {
            this.checkTargetModifiers(target, false);
            this.injectConstantModifier(target, currentTarget);
            return;
        }
        throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + target + " in " + this);
    }
    
    private void injectExpandedConstantModifier(final Target target, final JumpInsnNode jumpInsnNode) {
        final int opcode = jumpInsnNode.getOpcode();
        if (opcode < 155 || opcode > 158) {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + Bytecode.getOpcodeName(opcode) + " in " + target + " in " + this);
        }
        final InsnList list = new InsnList();
        list.add(new InsnNode(3));
        final AbstractInsnNode invokeConstantHandler = this.invokeConstantHandler(Type.getType("I"), target, list, list);
        list.add(new JumpInsnNode(opcode + 6, jumpInsnNode.label));
        target.replaceNode(jumpInsnNode, invokeConstantHandler, list);
        target.addToStack(1);
    }
    
    private void injectConstantModifier(final Target target, final AbstractInsnNode abstractInsnNode) {
        final Type constantType = Bytecode.getConstantType(abstractInsnNode);
        final InsnList list = new InsnList();
        final InsnList list2 = new InsnList();
        target.wrapNode(abstractInsnNode, this.invokeConstantHandler(constantType, target, list, list2), list, list2);
    }
    
    private AbstractInsnNode invokeConstantHandler(final Type type, final Target target, final InsnList list, final InsnList list2) {
        final boolean checkDescriptor = this.checkDescriptor(Bytecode.generateDescriptor(type, type), target, "getter");
        if (!this.isStatic) {
            list.insert(new VarInsnNode(25, 0));
            target.addToStack(1);
        }
        if (checkDescriptor) {
            this.pushArgs(target.arguments, list2, target.getArgIndices(), 0, target.arguments.length);
            target.addToStack(Bytecode.getArgsSize(target.arguments));
        }
        return this.invokeHandler(list2);
    }
}
