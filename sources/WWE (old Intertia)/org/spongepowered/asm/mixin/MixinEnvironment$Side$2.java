package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

enum MixinEnvironment$Side$2
{
    MixinEnvironment$Side$2(final String s, final int n) {
    }
    
    @Override
    protected boolean detect() {
        return "CLIENT".equals(MixinService.getService().getSideName());
    }
}