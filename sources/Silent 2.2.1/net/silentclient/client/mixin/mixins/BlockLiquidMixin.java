package net.silentclient.client.mixin.mixins;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockLiquid.class)
public class BlockLiquidMixin {
    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V", ordinal = 1))
    public void cancelParticle1(World instance, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] p_175688_14_) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Lava Particles").getValBoolean()) {
            instance.spawnParticle(EnumParticleTypes.LAVA, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
        }
    }

    @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V", ordinal = 3))
    public void cancelParticle2(World instance, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] p_175688_14_) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Lava Particles").getValBoolean()) {
            instance.spawnParticle(EnumParticleTypes.LAVA, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
        }
    }
}
