package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerPositionLookS2CPacket.class)
public interface PlayerPositionLookS2CPacketAccessor {
    @Accessor
    float getPitch();

    @Mutable
    @Accessor
    void setPitch(float pitch);

    @Accessor
    float getYaw();

    @Mutable
    @Accessor
    void setYaw(float yaw);
}
