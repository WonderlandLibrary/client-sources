package net.silentclient.client.mixin.mixins;

import net.minecraft.client.particle.EntityFX;
import net.silentclient.client.mixin.ducks.EntityFXExt;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityFX.class)
public class EntityFXMixin implements EntityFXExt {
    @Unique
    private float silent$cullState;

    @Override
    public void setCullState(float cullState) {
        this.silent$cullState = cullState;
    }

    @Override
    public float getCullState() {
        return this.silent$cullState;
    }

    @Redirect(method = "renderParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;getBrightnessForRender(F)I"))
    private int silent$staticParticleColor(EntityFX entityFX, float partialTicks) {
        return FPSBoostMod.basicEnabled() ? 15728880 : entityFX.getBrightnessForRender(partialTicks);
    }
}
