package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import net.minecraft.network.*;
import java.util.zip.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { NettyCompressionDecoder.class }, priority = 9999)
public class MixinNettyCompressionDecoder
{
    @Shadow
    @Final
    private Inflater field_179305_a;
    @Shadow
    private int field_179304_b;
    
    public MixinNettyCompressionDecoder() {
        super();
    }
    
    @Overwrite
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws DataFormatException, Exception {
        if (byteBuf.readableBytes() != 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            packetBuffer.readVarInt();
            list.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
        }
    }
}
