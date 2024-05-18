package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.gen.*;
import javax.lang.model.type.*;

static class AnnotatedElementAccessor extends AnnotatedElement<ExecutableElement>
{
    private final boolean shouldRemap;
    private final TypeMirror returnType;
    private String targetName;
    
    public AnnotatedElementAccessor(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final boolean shouldRemap) {
        super(executableElement, annotationHandle);
        this.shouldRemap = shouldRemap;
        this.returnType = this.getElement().getReturnType();
    }
    
    public boolean shouldRemap() {
        return this.shouldRemap;
    }
    
    public String getAnnotationValue() {
        return this.getAnnotation().getValue();
    }
    
    public TypeMirror getTargetType() {
        switch (this.getAccessorType()) {
            case FIELD_GETTER: {
                return this.returnType;
            }
            case FIELD_SETTER: {
                return ((VariableElement)this.getElement().getParameters().get(0)).asType();
            }
            default: {
                return null;
            }
        }
    }
    
    public String getTargetTypeName() {
        return TypeUtils.getTypeName(this.getTargetType());
    }
    
    public String getAccessorDesc() {
        return TypeUtils.getInternalName(this.getTargetType());
    }
    
    public MemberInfo getContext() {
        return new MemberInfo(this.getTargetName(), null, this.getAccessorDesc());
    }
    
    public AccessorInfo.AccessorType getAccessorType() {
        return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
    }
    
    public void setTargetName(final String targetName) {
        this.targetName = targetName;
    }
    
    public String getTargetName() {
        return this.targetName;
    }
    
    @Override
    public String toString() {
        return (this.targetName != null) ? this.targetName : "<invalid>";
    }
}
