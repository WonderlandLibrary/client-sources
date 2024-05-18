package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

class Reloaded extends State
{
    private final State previous;
    final MixinInfo this$0;
    
    Reloaded(final MixinInfo this$0, final State previous, final byte[] array) {
        this.this$0 = this$0.super(array, previous.getClassInfo());
        this.previous = previous;
    }
    
    @Override
    protected void validateChanges(final SubType subType, final List<ClassInfo> list) {
        if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
            throw new MixinReloadException(this.this$0, "Cannot change inner classes");
        }
        if (!this.interfaces.equals(this.previous.interfaces)) {
            throw new MixinReloadException(this.this$0, "Cannot change interfaces");
        }
        if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
            throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
        }
        if (!new HashSet(this.this$0.readTargetClasses(this.classNode, true)).equals(new HashSet(list))) {
            throw new MixinReloadException(this.this$0, "Cannot change target classes");
        }
        if (this.this$0.readPriority(this.classNode) != this.this$0.getPriority()) {
            throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
        }
    }
}
