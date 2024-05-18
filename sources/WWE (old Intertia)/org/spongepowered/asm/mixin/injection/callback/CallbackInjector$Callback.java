package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.code.*;

private class Callback extends InsnList
{
    private final MethodNode handler;
    private final AbstractInsnNode head;
    final Target target;
    final InjectionNodes.InjectionNode node;
    final LocalVariableNode[] locals;
    final Type[] localTypes;
    final int frameSize;
    final int extraArgs;
    final boolean canCaptureLocals;
    final boolean isAtReturn;
    final String desc;
    final String descl;
    final String[] argNames;
    int ctor;
    int invoke;
    private int marshalVar;
    private boolean captureArgs;
    final CallbackInjector this$0;
    
    Callback(final CallbackInjector this$0, final MethodNode handler, final Target target, final InjectionNodes.InjectionNode node, final LocalVariableNode[] locals, final boolean b) {
        this.this$0 = this$0;
        super();
        this.marshalVar = -1;
        this.captureArgs = true;
        this.handler = handler;
        this.target = target;
        this.head = target.insns.getFirst();
        this.node = node;
        this.locals = locals;
        this.localTypes = (Type[])((locals != null) ? new Type[locals.length] : null);
        this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !this$0.isStatic());
        List<String> list = null;
        if (locals != null) {
            final int n = this$0.isStatic() ? 0 : 1;
            list = new ArrayList<String>();
            for (int i = 0; i <= locals.length; ++i) {
                if (i == this.frameSize) {
                    list.add((target.returnType == Type.VOID_TYPE) ? "ci" : "cir");
                }
                if (i < locals.length && locals[i] != null) {
                    this.localTypes[i] = Type.getType(locals[i].desc);
                    if (i >= n) {
                        list.add(CallbackInjector.meltSnowman(i, locals[i].name));
                    }
                }
            }
        }
        this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
        this.argNames = (String[])((list != null) ? ((String[])list.toArray(new String[list.size()])) : null);
        this.canCaptureLocals = (b && locals != null && locals.length > this.frameSize);
        this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
        this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
        this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
        this.invoke = target.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
    }
    
    private boolean isValueReturnOpcode(final int n) {
        return n >= 172 && n < 177;
    }
    
    String getDescriptor() {
        return this.canCaptureLocals ? this.descl : this.desc;
    }
    
    String getDescriptorWithAllLocals() {
        return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
    }
    
    String getCallbackInfoConstructorDescriptor() {
        return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
    }
    
    void add(final AbstractInsnNode abstractInsnNode, final boolean b, final boolean b2) {
        this.add(abstractInsnNode, b, b2, false);
    }
    
    void add(final AbstractInsnNode abstractInsnNode, final boolean b, final boolean b2, final boolean b3) {
        if (b3) {
            this.target.insns.insertBefore(this.head, abstractInsnNode);
        }
        else {
            this.add(abstractInsnNode);
        }
        this.ctor += (b ? 1 : 0);
        this.invoke += (b2 ? 1 : 0);
    }
    
    void inject() {
        this.target.insertBefore(this.node, this);
        this.target.addToStack(Math.max(this.invoke, this.ctor));
    }
    
    boolean checkDescriptor(final String s) {
        if (this.getDescriptor().equals(s)) {
            return true;
        }
        if (this.target.getSimpleCallbackDescriptor().equals(s) && !this.canCaptureLocals) {
            this.captureArgs = false;
            return true;
        }
        final Type[] argumentTypes = Type.getArgumentTypes(s);
        final Type[] argumentTypes2 = Type.getArgumentTypes(this.descl);
        if (argumentTypes.length != argumentTypes2.length) {
            return false;
        }
        for (int i = 0; i < argumentTypes2.length; ++i) {
            final Type type = argumentTypes[i];
            if (!type.equals(argumentTypes2[i])) {
                if (type.getSort() == 9) {
                    return false;
                }
                if (Annotations.getInvisibleParameter(this.handler, Coerce.class, i) == null) {
                    return false;
                }
                if (!Injector.canCoerce(argumentTypes[i], argumentTypes2[i])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean captureArgs() {
        return this.captureArgs;
    }
    
    int marshalVar() {
        if (this.marshalVar < 0) {
            this.marshalVar = this.target.allocateLocal();
        }
        return this.marshalVar;
    }
}
