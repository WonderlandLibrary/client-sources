package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

class MixinClassNode extends ClassNode
{
    public final List<MixinMethodNode> mixinMethods;
    final MixinInfo this$0;
    
    public MixinClassNode(final MixinInfo mixinInfo, final MixinInfo mixinInfo2) {
        this(mixinInfo, 327680);
    }
    
    public MixinClassNode(final MixinInfo this$0, final int n) {
        this.this$0 = this$0;
        super(n);
        this.mixinMethods = (List<MixinMethodNode>)this.methods;
    }
    
    public MixinInfo getMixin() {
        return this.this$0;
    }
    
    @Override
    public MethodVisitor visitMethod(final int n, final String s, final String s2, final String s3, final String[] array) {
        final MixinMethodNode mixinMethodNode = this.this$0.new MixinMethodNode(n, s, s2, s3, array);
        this.methods.add(mixinMethodNode);
        return mixinMethodNode;
    }
}
