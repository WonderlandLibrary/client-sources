package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$2
{
    AccessorInfo$AccessorType$2(final String s, final int n, final Set set) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
        return new AccessorGeneratorFieldSetter(accessorInfo);
    }
}