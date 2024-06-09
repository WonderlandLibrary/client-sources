package com.cout970.fira.coremod.mixin;

import com.cout970.fira.Globals;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    public void sendPacket(CallbackInfo ci) {
        if (Globals.INSTANCE.getBlockOpenElytraPackets()) {
            ci.cancel();
        }
    }
}
