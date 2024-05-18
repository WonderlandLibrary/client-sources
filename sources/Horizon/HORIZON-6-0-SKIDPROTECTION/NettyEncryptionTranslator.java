package HORIZON-6-0-SKIDPROTECTION;

import javax.crypto.ShortBufferException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import javax.crypto.Cipher;

public class NettyEncryptionTranslator
{
    private final Cipher HorizonCode_Horizon_È;
    private byte[] Â;
    private byte[] Ý;
    private static final String Ø­áŒŠá = "CL_00001237";
    
    protected NettyEncryptionTranslator(final Cipher cipherIn) {
        this.Â = new byte[0];
        this.Ý = new byte[0];
        this.HorizonCode_Horizon_È = cipherIn;
    }
    
    private byte[] HorizonCode_Horizon_È(final ByteBuf p_150502_1_) {
        final int var2 = p_150502_1_.readableBytes();
        if (this.Â.length < var2) {
            this.Â = new byte[var2];
        }
        p_150502_1_.readBytes(this.Â, 0, var2);
        return this.Â;
    }
    
    protected ByteBuf HorizonCode_Horizon_È(final ChannelHandlerContext ctx, final ByteBuf buffer) throws ShortBufferException {
        final int var3 = buffer.readableBytes();
        final byte[] var4 = this.HorizonCode_Horizon_È(buffer);
        final ByteBuf var5 = ctx.alloc().heapBuffer(this.HorizonCode_Horizon_È.getOutputSize(var3));
        var5.writerIndex(this.HorizonCode_Horizon_È.update(var4, 0, var3, var5.array(), var5.arrayOffset()));
        return var5;
    }
    
    protected void HorizonCode_Horizon_È(final ByteBuf p_150504_1_, final ByteBuf p_150504_2_) throws ShortBufferException {
        final int var3 = p_150504_1_.readableBytes();
        final byte[] var4 = this.HorizonCode_Horizon_È(p_150504_1_);
        final int var5 = this.HorizonCode_Horizon_È.getOutputSize(var3);
        if (this.Ý.length < var5) {
            this.Ý = new byte[var5];
        }
        p_150504_2_.writeBytes(this.Ý, 0, this.HorizonCode_Horizon_È.update(var4, 0, var3, this.Ý));
    }
}
