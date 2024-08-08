package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface IObfuscationDataProvider {
   ObfuscationData getObfEntryRecursive(MemberInfo var1);

   ObfuscationData getObfEntry(MemberInfo var1);

   ObfuscationData getObfEntry(IMapping var1);

   ObfuscationData getObfMethodRecursive(MemberInfo var1);

   ObfuscationData getObfMethod(MemberInfo var1);

   ObfuscationData getRemappedMethod(MemberInfo var1);

   ObfuscationData getObfMethod(MappingMethod var1);

   ObfuscationData getRemappedMethod(MappingMethod var1);

   ObfuscationData getObfFieldRecursive(MemberInfo var1);

   ObfuscationData getObfField(MemberInfo var1);

   ObfuscationData getObfField(MappingField var1);

   ObfuscationData getObfClass(TypeHandle var1);

   ObfuscationData getObfClass(String var1);
}
