package com.client.glowclient.sponge.mixin;

import net.minecraft.network.handshake.client.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ C00Handshake.class })
public abstract class MixinC00Handshake
{
    @Shadow
    int field_149600_a;
    @Shadow
    String field_149598_b;
    @Shadow
    int field_149599_c;
    @Shadow
    EnumConnectionState field_149597_d;
    
    public MixinC00Handshake() {
        super();
    }
    
    @Inject(method = { "writePacketData" }, at = { @At("HEAD") }, cancellable = true)
    public void writePacketData(final PacketBuffer packetBuffer, final CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        packetBuffer.writeVarInt(this.protocolVersion);
        packetBuffer.writeString(this.ip);
        packetBuffer.writeShort(this.port);
        packetBuffer.writeVarInt(this.requestedState.getId());
    }
}
