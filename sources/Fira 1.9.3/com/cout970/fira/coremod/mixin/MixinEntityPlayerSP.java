package com.cout970.fira.coremod.mixin;

import com.cout970.fira.Globals;
import com.cout970.fira.modules.ElytraTweaks;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(
        method = "onLivingUpdate",
        at = @At(
            value = "NEW",
            target = "net/minecraft/network/play/client/CPacketEntityAction",
            ordinal = 0,
            shift = At.Shift.BEFORE
        )
    )
    public void initCPacketEntityAction(CallbackInfo ci) {
        if (ElytraTweaks.INSTANCE.onOpenElytra()) {
            Globals.INSTANCE.setBlockOpenElytraPackets(true);
        } else {
            Globals.INSTANCE.setBlockOpenElytraPackets(false);
        }
    }

    @Inject(
        method = "onLivingUpdate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/play/client/CPacketEntityAction;<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/play/client/CPacketEntityAction$Action;)V",
            ordinal = 0,
            shift = At.Shift.BY,
            by = 2
        )
    )
    public void afterSendPacketCPacketEntityAction(CallbackInfo ci) {
        Globals.INSTANCE.setBlockOpenElytraPackets(false);
    }
}
