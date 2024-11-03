package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public interface EntityVelocityUpdateS2CPacketAccessor {
    @Accessor
    int getId();

    @Mutable
    @Accessor
    void setId(int id);

    @Accessor
    int getVelocityX();

    @Mutable
    @Accessor
    void setVelocityX(int velocityX);

    @Accessor
    int getVelocityY();

    @Mutable
    @Accessor
    void setVelocityY(int velocityY);

    @Accessor
    int getVelocityZ();

    @Mutable
    @Accessor
    void setVelocityZ(int velocityZ);
}
