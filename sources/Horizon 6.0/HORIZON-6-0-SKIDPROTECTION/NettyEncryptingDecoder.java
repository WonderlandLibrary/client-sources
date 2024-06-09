package HORIZON-6-0-SKIDPROTECTION;

import javax.crypto.ShortBufferException;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import io.netty.handler.codec.MessageToMessageDecoder;

public class NettyEncryptingDecoder extends MessageToMessageDecoder
{
    private final NettyEncryptionTranslator HorizonCode_Horizon_È;
    private static final String Â = "CL_00001238";
    
    public NettyEncryptingDecoder(final Cipher cipher) {
        this.HorizonCode_Horizon_È = new NettyEncryptionTranslator(cipher);
    }
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        p_decode_3_.add(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_decode_1_, p_decode_2_));
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final Object p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        this.HorizonCode_Horizon_È(p_decode_1_, (ByteBuf)p_decode_2_, p_decode_3_);
    }
}
