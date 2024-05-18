package org.spongepowered.tools.obfuscation;

import java.util.*;
import javax.tools.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerShadow(final IMixinAnnotationProcessor mixinAnnotationProcessor, final AnnotatedMixin annotatedMixin) {
        super(mixinAnnotationProcessor, annotatedMixin);
    }
    
    public void registerShadow(final AnnotatedElementShadow<?, ?> annotatedElementShadow) {
        this.validateTarget(annotatedElementShadow.getElement(), annotatedElementShadow.getAnnotation(), annotatedElementShadow.getName(), "@Shadow");
        if (!annotatedElementShadow.shouldRemap()) {
            return;
        }
        final Iterator<TypeHandle> iterator = this.mixin.getTargets().iterator();
        while (iterator.hasNext()) {
            this.registerShadowForTarget(annotatedElementShadow, iterator.next());
        }
    }
    
    private void registerShadowForTarget(final AnnotatedElementShadow<?, ?> annotatedElementShadow, final TypeHandle typeHandle) {
        final ObfuscationData<?> obfuscationData = annotatedElementShadow.getObfuscationData(this.obf.getDataProvider(), typeHandle);
        if (obfuscationData.isEmpty()) {
            final String s = this.mixin.isMultiTarget() ? (" in target " + typeHandle) : "";
            if (typeHandle.isSimulated()) {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + s + " for @Shadow " + annotatedElementShadow);
            }
            else {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + s + " for @Shadow " + annotatedElementShadow);
            }
            return;
        }
        for (final ObfuscationType obfuscationType : obfuscationData) {
            try {
                annotatedElementShadow.addMapping(obfuscationType, obfuscationData.get(obfuscationType));
            }
            catch (Mappings.MappingConflictException ex) {
                annotatedElementShadow.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + annotatedElementShadow + ": " + ex.getNew().getSimpleName() + " for target " + typeHandle + " conflicts with existing mapping " + ex.getOld().getSimpleName());
            }
        }
    }
    
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
    
    class AnnotatedElementShadowField extends AnnotatedElementShadow<VariableElement, MappingField>
    {
        final AnnotatedMixinElementHandlerShadow this$0;
        
        public AnnotatedElementShadowField(final AnnotatedMixinElementHandlerShadow this$0, final VariableElement variableElement, final AnnotationHandle annotationHandle, final boolean b) {
            this.this$0 = this$0;
            super(variableElement, annotationHandle, b, IMapping.Type.FIELD);
        }
        
        @Override
        public MappingField getMapping(final TypeHandle typeHandle, final String s, final String s2) {
            return new MappingField(typeHandle.getName(), s, s2);
        }
        
        @Override
        public void addMapping(final ObfuscationType obfuscationType, final IMapping<?> obfuscatedName) {
            this.this$0.addFieldMapping(obfuscationType, this.setObfuscatedName(obfuscatedName), this.getDesc(), obfuscatedName.getDesc());
        }
        
        @Override
        public IMapping getMapping(final TypeHandle typeHandle, final String s, final String s2) {
            return this.getMapping(typeHandle, s, s2);
        }
    }
    
    class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
    {
        final AnnotatedMixinElementHandlerShadow this$0;
        
        public AnnotatedElementShadowMethod(final AnnotatedMixinElementHandlerShadow this$0, final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean b) {
            this.this$0 = this$0;
            super(executableElement, annotationHandle, b, IMapping.Type.METHOD);
        }
        
        @Override
        public MappingMethod getMapping(final TypeHandle typeHandle, final String s, final String s2) {
            return typeHandle.getMappingMethod(s, s2);
        }
        
        @Override
        public void addMapping(final ObfuscationType obfuscationType, final IMapping<?> obfuscatedName) {
            this.this$0.addMethodMapping(obfuscationType, this.setObfuscatedName(obfuscatedName), this.getDesc(), obfuscatedName.getDesc());
        }
        
        @Override
        public IMapping getMapping(final TypeHandle typeHandle, final String s, final String s2) {
            return this.getMapping(typeHandle, s, s2);
        }
    }
}
