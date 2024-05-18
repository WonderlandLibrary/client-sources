package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { Timer.class }, priority = 9999)
public class MixinTimer
{
    @Shadow
    public float field_194148_c;
    
    public MixinTimer() {
        super();
    }
    
    @Redirect(method = { "updateTimer" }, at = @At(value = "FIELD", target = "Lnet/minecraft/util/Timer;elapsedPartialTicks:F", ordinal = 0))
    private void updateTimer(final Timer timer, final float n) {
        final float n2 = n * vlwv.Axho;
        this.elapsedPartialTicks = ((n2 <= 0.0f) ? 0.1f : n2);
    }
}
