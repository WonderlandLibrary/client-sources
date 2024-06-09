package org.spongepowered.tools.obfuscation.mcp;

import org.spongepowered.tools.obfuscation.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mapping.mcp.*;

public class ObfuscationEnvironmentMCP extends ObfuscationEnvironment
{
    protected ObfuscationEnvironmentMCP(final ObfuscationType obfuscationType) {
        super(obfuscationType);
    }
    
    @Override
    protected IMappingProvider getMappingProvider(final Messager messager, final Filer filer) {
        return new MappingProviderSrg(messager, filer);
    }
    
    @Override
    protected IMappingWriter getMappingWriter(final Messager messager, final Filer filer) {
        return new MappingWriterSrg(messager, filer);
    }
}
