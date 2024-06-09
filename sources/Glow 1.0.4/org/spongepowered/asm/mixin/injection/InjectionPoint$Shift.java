package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class Shift extends InjectionPoint
{
    private final InjectionPoint input;
    private final int shift;
    
    public Shift(final InjectionPoint input, final int shift) {
        super();
        if (input == null) {
            throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
        }
        this.input = input;
        this.shift = shift;
    }
    
    @Override
    public String toString() {
        return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        final List<AbstractInsnNode> list2 = (collection instanceof List) ? collection : new ArrayList<AbstractInsnNode>(collection);
        this.input.find(s, list, collection);
        for (int i = 0; i < list2.size(); ++i) {
            list2.set(i, list.get(list.indexOf(list2.get(i)) + this.shift));
        }
        if (collection != list2) {
            collection.clear();
            collection.addAll((Collection<?>)list2);
        }
        return collection.size() > 0;
    }
}
