package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mapping.*;

public interface IObfuscationEnvironment
{
    MappingMethod getObfMethod(final MemberInfo p0);
    
    MappingMethod getObfMethod(final MappingMethod p0);
    
    MappingMethod getObfMethod(final MappingMethod p0, final boolean p1);
    
    MappingField getObfField(final MemberInfo p0);
    
    MappingField getObfField(final MappingField p0);
    
    MappingField getObfField(final MappingField p0, final boolean p1);
    
    String getObfClass(final String p0);
    
    MemberInfo remapDescriptor(final MemberInfo p0);
    
    String remapDescriptor(final String p0);
    
    void writeMappings(final Collection<IMappingConsumer> p0);
}
