package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.zip.Deflater;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyCompressionEncoder extends MessageToByteEncoder
{
    private final byte[] HorizonCode_Horizon_È;
    private final Deflater Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00002313";
    
    public NettyCompressionEncoder(final int treshold) {
        this.HorizonCode_Horizon_È = new byte[8192];
        this.Ý = treshold;
        this.Â = new Deflater();
    }
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext ctx, final ByteBuf input, final ByteBuf output) {
        final int var4 = input.readableBytes();
        final PacketBuffer var5 = new PacketBuffer(output);
        if (var4 < this.Ý) {
            var5.Â(0);
            var5.writeBytes(input);
        }
        else {
            final byte[] var6 = new byte[var4];
            input.readBytes(var6);
            var5.Â(var6.length);
            this.Â.setInput(var6, 0, var4);
            this.Â.finish();
            while (!this.Â.finished()) {
                final int var7 = this.Â.deflate(this.HorizonCode_Horizon_È);
                var5.writeBytes(this.HorizonCode_Horizon_È, 0, var7);
            }
            this.Â.reset();
        }
    }
    
    public void HorizonCode_Horizon_È(final int treshold) {
        this.Ý = treshold;
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) {
        this.HorizonCode_Horizon_È(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
    }
}
