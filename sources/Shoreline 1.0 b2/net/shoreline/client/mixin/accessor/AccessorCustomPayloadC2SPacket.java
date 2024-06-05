package net.shoreline.client.mixin.accessor;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 *
 * @author linus
 * @since 1.0
 */
@Mixin(CustomPayloadC2SPacket.class)
public interface AccessorCustomPayloadC2SPacket
{
    /**
     *
     * @param data
     */
    @Accessor("data")
    @Mutable
    void hookSetData(PacketByteBuf data);
}
