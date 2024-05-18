package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.injection.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.extensibility.*;

class MixinMethodNode extends MethodNode
{
    private final String originalName;
    final MixinInfo this$0;
    
    public MixinMethodNode(final MixinInfo this$0, final int n, final String originalName, final String s, final String s2, final String[] array) {
        this.this$0 = this$0;
        super(327680, n, originalName, s, s2, array);
        this.originalName = originalName;
    }
    
    @Override
    public String toString() {
        return String.format("%s%s", this.originalName, this.desc);
    }
    
    public String getOriginalName() {
        return this.originalName;
    }
    
    public boolean isInjector() {
        return this.getInjectorAnnotation() != null || this.isSurrogate();
    }
    
    public boolean isSurrogate() {
        return this.getVisibleAnnotation(Surrogate.class) != null;
    }
    
    public boolean isSynthetic() {
        return Bytecode.hasFlag(this, 4096);
    }
    
    public AnnotationNode getVisibleAnnotation(final Class<? extends Annotation> clazz) {
        return Annotations.getVisible(this, clazz);
    }
    
    public AnnotationNode getInjectorAnnotation() {
        return InjectionInfo.getInjectorAnnotation(this.this$0, this);
    }
    
    public IMixinInfo getOwner() {
        return this.this$0;
    }
}
