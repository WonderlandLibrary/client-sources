package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import java.lang.reflect.*;

static final class Intersection extends CompositeInjectionPoint
{
    public Intersection(final InjectionPoint... array) {
        super(array);
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        boolean b = false;
        final ArrayList[] array = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
        for (int i = 0; i < this.components.length; ++i) {
            array[i] = new ArrayList();
            this.components[i].find(s, list, array[i]);
        }
        final ArrayList list2 = array[0];
        for (int j = 0; j < list2.size(); ++j) {
            final AbstractInsnNode abstractInsnNode = list2.get(j);
            final boolean b2 = true;
            for (int n = 1; n < array.length && array[n].contains(abstractInsnNode); ++n) {}
            if (b2) {
                collection.add(abstractInsnNode);
                b = true;
            }
        }
        return b;
    }
}
