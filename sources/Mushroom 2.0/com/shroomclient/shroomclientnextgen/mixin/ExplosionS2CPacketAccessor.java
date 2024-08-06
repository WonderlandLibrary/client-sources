package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExplosionS2CPacket.class)
public interface ExplosionS2CPacketAccessor {
    @Accessor
    @Mutable
    void setPlayerVelocityX(float x);

    @Accessor
    @Mutable
    void setPlayerVelocityY(float y);

    @Accessor
    @Mutable
    void setPlayerVelocityZ(float z);
}
