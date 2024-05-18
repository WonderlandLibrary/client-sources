package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public class InjectorTarget
{
    private final ISliceContext context;
    private final Map<String, ReadOnlyInsnList> cache;
    private final Target target;
    
    public InjectorTarget(final ISliceContext context, final Target target) {
        super();
        this.cache = new HashMap<String, ReadOnlyInsnList>();
        this.context = context;
        this.target = target;
    }
    
    public Target getTarget() {
        return this.target;
    }
    
    public MethodNode getMethod() {
        return this.target.method;
    }
    
    public InsnList getSlice(final String s) {
        ReadOnlyInsnList slice = this.cache.get(s);
        if (slice == null) {
            final MethodSlice slice2 = this.context.getSlice(s);
            if (slice2 != null) {
                slice = slice2.getSlice(this.target.method);
            }
            else {
                slice = new ReadOnlyInsnList(this.target.method.instructions);
            }
            this.cache.put(s, slice);
        }
        return slice;
    }
    
    public InsnList getSlice(final InjectionPoint injectionPoint) {
        return this.getSlice(injectionPoint.getSlice());
    }
    
    public void dispose() {
        final Iterator<ReadOnlyInsnList> iterator = this.cache.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().dispose();
        }
        this.cache.clear();
    }
}
