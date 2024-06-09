package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.invoke.util.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.apache.logging.log4j.*;

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
        if (constantType.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            this.checkNarrowing(target, abstractInsnNode, constantType);
        }
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
    
    private void checkNarrowing(final Target target, final AbstractInsnNode abstractInsnNode, final Type type) {
        final AbstractInsnNode popInsn = new InsnFinder().findPopInsn(target, abstractInsnNode);
        if (popInsn == null) {
            return;
        }
        if (popInsn instanceof FieldInsnNode) {
            final FieldInsnNode fieldInsnNode = (FieldInsnNode)popInsn;
            final Type type2 = Type.getType(fieldInsnNode.desc);
            this.checkNarrowing(target, abstractInsnNode, type, type2, target.indexOf(popInsn), String.format("%s %s %s.%s", Bytecode.getOpcodeName(popInsn), SignaturePrinter.getTypeName(type2, false), fieldInsnNode.owner.replace('/', '.'), fieldInsnNode.name));
        }
        else if (popInsn.getOpcode() == 172) {
            this.checkNarrowing(target, abstractInsnNode, type, target.returnType, target.indexOf(popInsn), "RETURN " + SignaturePrinter.getTypeName(target.returnType, false));
        }
        else if (popInsn.getOpcode() == 54) {
            final int var = ((VarInsnNode)popInsn).var;
            final LocalVariableNode localVariable = Locals.getLocalVariableAt(target.classNode, target.method, popInsn, var);
            if (localVariable != null && localVariable.desc != null) {
                final String s = (localVariable.name != null) ? localVariable.name : "unnamed";
                final Type type3 = Type.getType(localVariable.desc);
                this.checkNarrowing(target, abstractInsnNode, type, type3, target.indexOf(popInsn), String.format("ISTORE[var=%d] %s %s", var, SignaturePrinter.getTypeName(type3, false), s));
            }
        }
    }
    
    private void checkNarrowing(final Target target, final AbstractInsnNode abstractInsnNode, final Type type, final Type type2, final int n, final String s) {
        final int sort = type.getSort();
        final int sort2 = type2.getSort();
        if (sort2 < sort) {
            Injector.logger.log((sort2 == 1) ? Level.ERROR : Level.WARN, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[] { SignaturePrinter.getTypeName(type, false), SignaturePrinter.getTypeName(type2, false), this.info, target, n, s, (sort2 == 1) ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "" });
        }
    }
}
