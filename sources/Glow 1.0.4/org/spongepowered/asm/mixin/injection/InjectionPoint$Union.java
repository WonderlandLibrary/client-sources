package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class Union extends CompositeInjectionPoint
{
    public Union(final InjectionPoint... array) {
        super(array);
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        final LinkedHashSet<AbstractInsnNode> set = new LinkedHashSet<AbstractInsnNode>();
        for (int i = 0; i < this.components.length; ++i) {
            this.components[i].find(s, list, set);
        }
        collection.addAll(set);
        return set.size() > 0;
    }
}
