package net.minecraft.network;

import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class NettyEncryptionTranslator
{
    private final Cipher cipher;
    private byte[] field_150505_b;
    private byte[] field_150506_c;
    
    protected NettyEncryptionTranslator(final Cipher cipherIn) {
        this.field_150505_b = new byte[0];
        this.field_150506_c = new byte[0];
        this.cipher = cipherIn;
    }
    
    private byte[] func_150502_a(final ByteBuf p_150502_1_) {
        final int var2 = p_150502_1_.readableBytes();
        if (this.field_150505_b.length < var2) {
            this.field_150505_b = new byte[var2];
        }
        p_150502_1_.readBytes(this.field_150505_b, 0, var2);
        return this.field_150505_b;
    }
    
    protected ByteBuf decipher(final ChannelHandlerContext ctx, final ByteBuf buffer) throws ShortBufferException {
        final int var3 = buffer.readableBytes();
        final byte[] var4 = this.func_150502_a(buffer);
        final ByteBuf var5 = ctx.alloc().heapBuffer(this.cipher.getOutputSize(var3));
        var5.writerIndex(this.cipher.update(var4, 0, var3, var5.array(), var5.arrayOffset()));
        return var5;
    }
    
    protected void cipher(final ByteBuf p_150504_1_, final ByteBuf p_150504_2_) throws ShortBufferException {
        final int var3 = p_150504_1_.readableBytes();
        final byte[] var4 = this.func_150502_a(p_150504_1_);
        final int var5 = this.cipher.getOutputSize(var3);
        if (this.field_150506_c.length < var5) {
            this.field_150506_c = new byte[var5];
        }
        p_150504_2_.writeBytes(this.field_150506_c, 0, this.cipher.update(var4, 0, var3, this.field_150506_c));
    }
}
