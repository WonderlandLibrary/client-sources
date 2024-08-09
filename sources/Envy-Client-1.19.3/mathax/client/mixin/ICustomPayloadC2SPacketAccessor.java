package mathax.client.mixin;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CustomPayloadC2SPacket.class)
public interface ICustomPayloadC2SPacketAccessor {
    @Accessor("channel")
    Identifier getChannel();

    @Mutable
    @Accessor("channel")
    void setChannel(Identifier newValue);

    @Accessor("data")
    PacketByteBuf getData();

    @Mutable
    @Accessor("data")
    void setData(PacketByteBuf newValue);
}
