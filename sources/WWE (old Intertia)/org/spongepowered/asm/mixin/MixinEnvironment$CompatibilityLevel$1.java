package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

enum MixinEnvironment$CompatibilityLevel$1
{
    MixinEnvironment$CompatibilityLevel$1(final String s, final int n, final int n2, final int n3, final boolean b) {
    }
    
    @Override
    boolean isSupported() {
        return JavaVersion.current() >= 1.7;
    }
}