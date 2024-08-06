package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Speed;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Sprint;
import com.shroomclient.shroomclientnextgen.modules.impl.player.Disabler;
import com.shroomclient.shroomclientnextgen.modules.impl.render.NoFOV;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "getFovMultiplier", cancellable = true)
    public void getFovMultiplier(CallbackInfoReturnable<Float> cir) {
        if (
            ModuleManager.isEnabled(Sprint.class) ||
            ModuleManager.isEnabled(Speed.class) ||
            (ModuleManager.isEnabled(Disabler.class) &&
                Disabler.vulcanScaffold) ||
            ModuleManager.isEnabled(NoFOV.class)
        ) cir.setReturnValue(
            MathHelper.lerp(
                MinecraftClient.getInstance()
                    .options.getFovEffectScale()
                    .getValue()
                    .floatValue(),
                1.0f,
                1.1f
            )
        );
    }
}
