package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public interface EntityVelocityUpdateS2CPacketAccessor {
    @Accessor
    @Mutable
    void setVelocityX(int x);

    @Accessor
    @Mutable
    void setVelocityY(int y);

    @Accessor
    @Mutable
    void setVelocityZ(int z);
}
