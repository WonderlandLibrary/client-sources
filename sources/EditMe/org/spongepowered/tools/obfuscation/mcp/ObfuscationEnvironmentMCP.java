package org.spongepowered.tools.obfuscation.mcp;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mapping.mcp.MappingProviderSrg;
import org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg;

public class ObfuscationEnvironmentMCP extends ObfuscationEnvironment {
   protected ObfuscationEnvironmentMCP(ObfuscationType var1) {
      super(var1);
   }

   protected IMappingProvider getMappingProvider(Messager var1, Filer var2) {
      return new MappingProviderSrg(var1, var2);
   }

   protected IMappingWriter getMappingWriter(Messager var1, Filer var2) {
      return new MappingWriterSrg(var1, var2);
   }
}
