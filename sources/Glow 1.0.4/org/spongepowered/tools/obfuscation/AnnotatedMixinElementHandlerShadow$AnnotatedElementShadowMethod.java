package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;

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
