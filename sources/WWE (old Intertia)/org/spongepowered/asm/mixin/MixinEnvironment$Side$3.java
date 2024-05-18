package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

enum MixinEnvironment$Side$3
{
    MixinEnvironment$Side$3(final String s, final int n) {
    }
    
    @Override
    protected boolean detect() {
        final String sideName = MixinService.getService().getSideName();
        return "SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName);
    }
}