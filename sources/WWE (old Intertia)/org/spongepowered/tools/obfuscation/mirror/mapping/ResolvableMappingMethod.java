package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;

public final class ResolvableMappingMethod extends MappingMethod
{
    private final TypeHandle ownerHandle;
    
    public ResolvableMappingMethod(final TypeHandle ownerHandle, final String s, final String s2) {
        super(ownerHandle.getName(), s, s2);
        this.ownerHandle = ownerHandle;
    }
    
    @Override
    public MappingMethod getSuper() {
        if (this.ownerHandle == null) {
            return super.getSuper();
        }
        final String simpleName = this.getSimpleName();
        final String desc = this.getDesc();
        final String javaSignature = TypeUtils.getJavaSignature(desc);
        final TypeHandle superclass = this.ownerHandle.getSuperclass();
        if (superclass != null && superclass.findMethod(simpleName, javaSignature) != null) {
            return superclass.getMappingMethod(simpleName, desc);
        }
        for (final TypeHandle typeHandle : this.ownerHandle.getInterfaces()) {
            if (typeHandle.findMethod(simpleName, javaSignature) != null) {
                return typeHandle.getMappingMethod(simpleName, desc);
            }
        }
        if (superclass != null) {
            return superclass.getMappingMethod(simpleName, desc).getSuper();
        }
        return super.getSuper();
    }
    
    public MappingMethod move(final TypeHandle typeHandle) {
        return new ResolvableMappingMethod(typeHandle, this.getSimpleName(), this.getDesc());
    }
    
    @Override
    public MappingMethod remap(final String s) {
        return new ResolvableMappingMethod(this.ownerHandle, s, this.getDesc());
    }
    
    @Override
    public MappingMethod transform(final String s) {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), s);
    }
    
    @Override
    public MappingMethod copy() {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), this.getDesc());
    }
    
    @Override
    public Object getSuper() {
        return this.getSuper();
    }
    
    @Override
    public Object copy() {
        return this.copy();
    }
    
    @Override
    public Object transform(final String s) {
        return this.transform(s);
    }
    
    @Override
    public Object remap(final String s) {
        return this.remap(s);
    }
}
