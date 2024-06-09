package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public final class MethodSlices
{
    private final InjectionInfo info;
    private final Map<String, MethodSlice> slices;
    
    private MethodSlices(final InjectionInfo info) {
        super();
        this.slices = new HashMap<String, MethodSlice>(4);
        this.info = info;
    }
    
    private void add(final MethodSlice methodSlice) {
        final String sliceId = this.info.getSliceId(methodSlice.getId());
        if (this.slices.containsKey(sliceId)) {
            throw new InvalidSliceException((ISliceContext)this.info, methodSlice + " has a duplicate id, '" + sliceId + "' was already defined");
        }
        this.slices.put(sliceId, methodSlice);
    }
    
    public MethodSlice get(final String s) {
        return this.slices.get(s);
    }
    
    @Override
    public String toString() {
        return String.format("MethodSlices%s", this.slices.keySet());
    }
    
    public static MethodSlices parse(final InjectionInfo injectionInfo) {
        final MethodSlices methodSlices = new MethodSlices(injectionInfo);
        final AnnotationNode annotation = injectionInfo.getAnnotation();
        if (annotation != null) {
            final Iterator<AnnotationNode> iterator = Annotations.getValue(annotation, "slice", true).iterator();
            while (iterator.hasNext()) {
                methodSlices.add(MethodSlice.parse(injectionInfo, iterator.next()));
            }
        }
        return methodSlices;
    }
}
