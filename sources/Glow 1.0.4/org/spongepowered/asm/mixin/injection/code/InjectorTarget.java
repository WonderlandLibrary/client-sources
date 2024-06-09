package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public class InjectorTarget
{
    private final ISliceContext context;
    private final Map<String, ReadOnlyInsnList> cache;
    private final Target target;
    private final String mergedBy;
    private final int mergedPriority;
    
    public InjectorTarget(final ISliceContext context, final Target target) {
        super();
        this.cache = new HashMap<String, ReadOnlyInsnList>();
        this.context = context;
        this.target = target;
        final AnnotationNode visible = Annotations.getVisible(target.method, MixinMerged.class);
        this.mergedBy = (String)Annotations.getValue(visible, "mixin");
        this.mergedPriority = Annotations.getValue(visible, "priority", 1000);
    }
    
    @Override
    public String toString() {
        return this.target.toString();
    }
    
    public Target getTarget() {
        return this.target;
    }
    
    public MethodNode getMethod() {
        return this.target.method;
    }
    
    public boolean isMerged() {
        return this.mergedBy != null;
    }
    
    public String getMergedBy() {
        return this.mergedBy;
    }
    
    public int getMergedPriority() {
        return this.mergedPriority;
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
