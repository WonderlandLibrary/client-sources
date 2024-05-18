package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$3
{
    AccessorInfo$AccessorType$3(final String s, final int n, final Set set) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
        return new AccessorGeneratorMethodProxy(accessorInfo);
    }
}