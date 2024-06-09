package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.mirror.*;

abstract static class AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedElement<E>
{
    private final boolean shouldRemap;
    private final ShadowElementName name;
    private final IMapping.Type type;
    
    protected AnnotatedElementShadow(final E e, final AnnotationHandle annotationHandle, final boolean shouldRemap, final IMapping.Type type) {
        super(e, annotationHandle);
        this.shouldRemap = shouldRemap;
        this.name = new ShadowElementName(e, annotationHandle);
        this.type = type;
    }
    
    public boolean shouldRemap() {
        return this.shouldRemap;
    }
    
    public ShadowElementName getName() {
        return this.name;
    }
    
    public IMapping.Type getElementType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return this.getElementType().name().toLowerCase();
    }
    
    public ShadowElementName setObfuscatedName(final IMapping<?> mapping) {
        return this.setObfuscatedName(mapping.getSimpleName());
    }
    
    public ShadowElementName setObfuscatedName(final String obfuscatedName) {
        return this.getName().setObfuscatedName(obfuscatedName);
    }
    
    public ObfuscationData<M> getObfuscationData(final IObfuscationDataProvider obfuscationDataProvider, final TypeHandle typeHandle) {
        return obfuscationDataProvider.getObfEntry((IMapping<M>)this.getMapping(typeHandle, this.getName().toString(), this.getDesc()));
    }
    
    public abstract M getMapping(final TypeHandle p0, final String p1, final String p2);
    
    public abstract void addMapping(final ObfuscationType p0, final IMapping<?> p1);
}
