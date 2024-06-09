package com.cout970.fira.coremod.mixin;

import com.cout970.fira.modules.YawLock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "turn", at = @At("RETURN"))
    public void turn(CallbackInfo ci) {
        YawLock.INSTANCE.onTurn(this);
    }
}
