package net.silentclient.client.mixin.mixins;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.silentclient.client.mixin.ducks.EntityFXExt;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.culling.ParticleCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class EffectRendererMixin {
    @Redirect(
            method = "renderParticles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"
            )
    )
    private void silent$cullParticles(
            EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks,
            float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ
    ) {
        if (ParticleCulling.shouldRender(instance)) {
            instance.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        }
    }

    @ModifyVariable(
            method = "updateEffectAlphaLayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;tickParticle(Lnet/minecraft/client/particle/EntityFX;)V", shift = At.Shift.AFTER)
    )
    private EntityFX silent$checkIfCulled(EntityFX entityFX) {
        if (ParticleCulling.camera != null) {
            ((EntityFXExt) entityFX).setCullState(ParticleCulling.camera.isBoundingBoxInFrustum(
                    //#if MC==10809
                    entityFX.getEntityBoundingBox()
                    //#else
                    //$$ entityFX.getBoundingBox()
                    //#endif
            ) ? 1.0F : -1.0F);
        }

        return entityFX;
    }

    @ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
    public int changeMaxParticleLimit(int original) {
        return FPSBoostMod.advancedEnabled() ? 100 : original;
    }

    @Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
    public void removeBlockEffects1(BlockPos pos, IBlockState state, CallbackInfo ci) {
        if(FPSBoostMod.advancedEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "addBlockHitEffects", at = @At("HEAD"), cancellable = true)
    public void removeBlockEffects2(BlockPos pos, EnumFacing side, CallbackInfo ci) {
        if(FPSBoostMod.advancedEnabled()) {
            ci.cancel();
        }
    }
}
