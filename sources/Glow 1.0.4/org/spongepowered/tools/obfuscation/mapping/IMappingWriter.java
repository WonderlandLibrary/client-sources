package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public interface IMappingWriter
{
    void write(final String p0, final ObfuscationType p1, final IMappingConsumer.MappingSet<MappingField> p2, final IMappingConsumer.MappingSet<MappingMethod> p3);
}
