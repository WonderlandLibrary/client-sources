package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;

public class InvokerInfo extends AccessorInfo
{
    public InvokerInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode) {
        super(mixinTargetContext, methodNode, Invoker.class);
    }
    
    @Override
    protected AccessorType initType() {
        return AccessorType.METHOD_PROXY;
    }
    
    @Override
    protected Type initTargetFieldType() {
        return null;
    }
    
    @Override
    protected MemberInfo initTarget() {
        return new MemberInfo(this.getTargetName(), null, this.method.desc);
    }
    
    @Override
    public void locate() {
        this.targetMethod = this.findTargetMethod();
    }
    
    private MethodNode findTargetMethod() {
        return this.findTarget(this.classNode.methods);
    }
}
