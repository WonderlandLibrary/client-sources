package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingWriter {
   void write(String var1, ObfuscationType var2, IMappingConsumer.MappingSet var3, IMappingConsumer.MappingSet var4);
}
