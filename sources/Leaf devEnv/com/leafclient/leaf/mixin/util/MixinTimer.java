package com.leafclient.leaf.mixin.util;

import com.leafclient.leaf.extension.ExtensionTimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Timer.class)
public abstract class MixinTimer implements ExtensionTimer {

    @Shadow private float tickLength;

    @Override
    public void setTimerSpeed(float timerSpeed) {
        tickLength = 50F / timerSpeed;
    }

    @Override
    public float getTimerSpeed() {
        return 50F / tickLength;
    }

}
