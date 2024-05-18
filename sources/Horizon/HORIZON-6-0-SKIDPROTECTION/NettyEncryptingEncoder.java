package HORIZON-6-0-SKIDPROTECTION;

import javax.crypto.ShortBufferException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncryptingEncoder extends MessageToByteEncoder
{
    private final NettyEncryptionTranslator HorizonCode_Horizon_È;
    private static final String Â = "CL_00001239";
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.HorizonCode_Horizon_È = new NettyEncryptionTranslator(cipher);
    }
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_encode_2_, p_encode_3_);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.HorizonCode_Horizon_È(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
    }
}
