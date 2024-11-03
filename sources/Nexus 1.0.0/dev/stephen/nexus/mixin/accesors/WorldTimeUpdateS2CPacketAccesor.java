package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldTimeUpdateS2CPacket.class)
public interface WorldTimeUpdateS2CPacketAccesor {

    @Accessor
    long getTimeOfDay();

    @Mutable
    @Accessor
    void setTimeOfDay(long timeOfDay);
}
