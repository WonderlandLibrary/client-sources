package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageSerializer2 extends MessageToByteEncoder
{
    private static final String HorizonCode_Horizon_È = "CL_00001256";
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_) {
        final int var4 = p_encode_2_.readableBytes();
        final int var5 = PacketBuffer.HorizonCode_Horizon_È(var4);
        if (var5 > 3) {
            throw new IllegalArgumentException("unable to fit " + var4 + " into " + 3);
        }
        final PacketBuffer var6 = new PacketBuffer(p_encode_3_);
        var6.ensureWritable(var5 + var4);
        var6.Â(var4);
        var6.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), var4);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) {
        this.HorizonCode_Horizon_È(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
    }
}
