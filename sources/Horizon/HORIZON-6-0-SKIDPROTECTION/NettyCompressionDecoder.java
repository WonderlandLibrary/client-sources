package HORIZON-6-0-SKIDPROTECTION;

import java.util.zip.DataFormatException;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.zip.Inflater;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyCompressionDecoder extends ByteToMessageDecoder
{
    private final Inflater HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00002314";
    
    public NettyCompressionDecoder(final int treshold) {
        this.Â = treshold;
        this.HorizonCode_Horizon_È = new Inflater();
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws DataFormatException {
        if (p_decode_2_.readableBytes() != 0) {
            final PacketBuffer var4 = new PacketBuffer(p_decode_2_);
            final int var5 = var4.Ø­áŒŠá();
            if (var5 == 0) {
                p_decode_3_.add(var4.readBytes(var4.readableBytes()));
            }
            else {
                if (var5 < this.Â) {
                    throw new DecoderException("Badly compressed packet - size of " + var5 + " is below server threshold of " + this.Â);
                }
                if (var5 > 2097152) {
                    throw new DecoderException("Badly compressed packet - size of " + var5 + " is larger than protocol maximum of " + 2097152);
                }
                final byte[] var6 = new byte[var4.readableBytes()];
                var4.readBytes(var6);
                this.HorizonCode_Horizon_È.setInput(var6);
                final byte[] var7 = new byte[var5];
                this.HorizonCode_Horizon_È.inflate(var7);
                p_decode_3_.add(Unpooled.wrappedBuffer(var7));
                this.HorizonCode_Horizon_È.reset();
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int treshold) {
        this.Â = treshold;
    }
}
