package org.spongepowered.tools.obfuscation.mapping;

import java.io.File;
import java.io.IOException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

public interface IMappingProvider {
   void clear();

   boolean isEmpty();

   void read(File var1) throws IOException;

   MappingMethod getMethodMapping(MappingMethod var1);

   MappingField getFieldMapping(MappingField var1);

   String getClassMapping(String var1);

   String getPackageMapping(String var1);
}
