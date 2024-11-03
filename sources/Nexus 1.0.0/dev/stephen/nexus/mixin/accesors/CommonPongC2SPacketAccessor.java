package dev.stephen.nexus.mixin.accesors;

import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CommonPongC2SPacket.class)
public interface CommonPongC2SPacketAccessor {
    @Accessor
    int getParameter();

    @Mutable
    @Accessor
    void setParameter(int parameter);
}
