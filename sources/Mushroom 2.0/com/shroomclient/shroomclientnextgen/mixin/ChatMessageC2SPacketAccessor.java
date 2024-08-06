package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatMessageC2SPacket.class)
public interface ChatMessageC2SPacketAccessor {
    @Accessor
    @Mutable
    void setChatMessage(String message);
}
