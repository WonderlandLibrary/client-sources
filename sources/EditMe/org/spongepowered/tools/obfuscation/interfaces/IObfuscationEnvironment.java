package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

public interface IObfuscationEnvironment {
   MappingMethod getObfMethod(MemberInfo var1);

   MappingMethod getObfMethod(MappingMethod var1);

   MappingMethod getObfMethod(MappingMethod var1, boolean var2);

   MappingField getObfField(MemberInfo var1);

   MappingField getObfField(MappingField var1);

   MappingField getObfField(MappingField var1, boolean var2);

   String getObfClass(String var1);

   MemberInfo remapDescriptor(MemberInfo var1);

   String remapDescriptor(String var1);

   void writeMappings(Collection var1);
}
