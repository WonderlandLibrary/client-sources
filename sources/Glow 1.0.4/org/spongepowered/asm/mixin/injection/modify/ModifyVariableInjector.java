package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.refmap.*;

public class ModifyVariableInjector extends Injector
{
    private final LocalVariableDiscriminator discriminator;
    
    public ModifyVariableInjector(final InjectionInfo injectionInfo, final LocalVariableDiscriminator discriminator) {
        super(injectionInfo);
        this.discriminator = discriminator;
    }
    
    @Override
    protected boolean findTargetNodes(final MethodNode methodNode, final InjectionPoint injectionPoint, final InsnList list, final Collection<AbstractInsnNode> collection) {
        if (injectionPoint instanceof ContextualInjectionPoint) {
            return ((ContextualInjectionPoint)injectionPoint).find(this.info.getContext().getTargetMethod(methodNode), collection);
        }
        return injectionPoint.find(methodNode.desc, list, collection);
    }
    
    @Override
    protected void sanityCheck(final Target target, final List<InjectionPoint> list) {
        super.sanityCheck(target, list);
        if (target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
        }
        final int ordinal = this.discriminator.getOrdinal();
        if (ordinal < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + ordinal + " specified in " + this);
        }
        if (this.discriminator.getIndex() == 0 && !this.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
        }
    }
    
    @Override
    protected void inject(final Target target, final InjectionNodes.InjectionNode injectionNode) {
        if (injectionNode.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
        }
        final Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, injectionNode.getCurrentTarget());
        if (this.discriminator.printLVT()) {
            this.printLocals(context);
        }
        final String descriptor = Bytecode.getDescriptor(new Type[] { this.returnType }, this.returnType);
        if (!descriptor.equals(this.methodNode.desc)) {
            throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + descriptor + " but found " + this.methodNode.desc);
        }
        try {
            final int local = this.discriminator.findLocal(context);
            if (local > -1) {
                this.inject(context, local);
            }
        }
        catch (InvalidImplicitDiscriminatorException ex) {
            if (this.discriminator.printLVT()) {
                this.info.addCallbackInvocation(this.methodNode);
                return;
            }
            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, ex);
        }
        target.insns.insertBefore(context.node, context.insns);
        target.addToStack(this.isStatic ? 1 : 2);
    }
    
    private void printLocals(final Context context) {
        new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[] { "var" }).setModifiers(this.methodNode);
        new PrettyPrinter().kvWidth(20).kv("Target Class", (Object)this.classNode.name.replace('/', '.')).kv("Target Method", (Object)context.target.method.name).kv("Callback Name", (Object)this.methodNode.name).kv("Capture Type", (Object)SignaturePrinter.getTypeName(this.returnType, false)).kv("Instruction", "%s %s", context.node.getClass().getSimpleName(), Bytecode.getOpcodeName(context.node.getOpcode())).hr().kv("Match mode", (Object)(this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)")).kv("Match ordinal", (this.discriminator.getOrdinal() < 0) ? "any" : Integer.valueOf(this.discriminator.getOrdinal())).kv("Match index", (this.discriminator.getIndex() < context.baseArgIndex) ? "any" : Integer.valueOf(this.discriminator.getIndex())).kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").kv("Args only", this.discriminator.isArgsOnly()).hr().add(context).print(System.err);
    }
    
    private void inject(final Context context, final int n) {
        if (!this.isStatic) {
            context.insns.add(new VarInsnNode(25, 0));
        }
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(21), n));
        this.invokeHandler(context.insns);
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(54), n));
    }
    
    static class Context extends LocalVariableDiscriminator.Context
    {
        final InsnList insns;
        
        public Context(final Type type, final boolean b, final Target target, final AbstractInsnNode abstractInsnNode) {
            super(type, b, target, abstractInsnNode);
            this.insns = new InsnList();
        }
    }
    
    abstract static class ContextualInjectionPoint extends InjectionPoint
    {
        protected final IMixinContext context;
        
        ContextualInjectionPoint(final IMixinContext context) {
            super();
            this.context = context;
        }
        
        @Override
        public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
            throw new InvalidInjectionException(this.context, this.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
        }
        
        abstract boolean find(final Target p0, final Collection<AbstractInsnNode> p1);
    }
}
