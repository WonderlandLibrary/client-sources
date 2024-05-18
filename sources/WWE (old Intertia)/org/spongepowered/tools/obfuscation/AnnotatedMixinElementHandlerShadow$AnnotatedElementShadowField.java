package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;

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
