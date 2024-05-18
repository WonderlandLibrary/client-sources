package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import com.google.common.collect.*;

static class RedirectedInvoke
{
    final Target target;
    final MethodInsnNode node;
    final Type returnType;
    final Type[] args;
    final Type[] locals;
    boolean captureTargetArgs;
    
    RedirectedInvoke(final Target target, final MethodInsnNode node) {
        super();
        this.captureTargetArgs = false;
        this.target = target;
        this.node = node;
        this.returnType = Type.getReturnType(node.desc);
        this.args = Type.getArgumentTypes(node.desc);
        this.locals = (Type[])((node.getOpcode() == 184) ? this.args : ObjectArrays.concat((Object)Type.getType("L" + node.owner + ";"), (Object[])this.args));
    }
}
