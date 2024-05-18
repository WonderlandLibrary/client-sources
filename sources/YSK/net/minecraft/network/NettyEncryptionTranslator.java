package net.minecraft.network;

import io.netty.channel.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptionTranslator
{
    private final Cipher cipher;
    private byte[] field_150505_b;
    private byte[] field_150506_c;
    
    protected ByteBuf decipher(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws ShortBufferException {
        final int readableBytes = byteBuf.readableBytes();
        final byte[] func_150502_a = this.func_150502_a(byteBuf);
        final ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(this.cipher.getOutputSize(readableBytes));
        heapBuffer.writerIndex(this.cipher.update(func_150502_a, "".length(), readableBytes, heapBuffer.array(), heapBuffer.arrayOffset()));
        return heapBuffer;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void cipher(final ByteBuf byteBuf, final ByteBuf byteBuf2) throws ShortBufferException {
        final int readableBytes = byteBuf.readableBytes();
        final byte[] func_150502_a = this.func_150502_a(byteBuf);
        final int outputSize = this.cipher.getOutputSize(readableBytes);
        if (this.field_150506_c.length < outputSize) {
            this.field_150506_c = new byte[outputSize];
        }
        byteBuf2.writeBytes(this.field_150506_c, "".length(), this.cipher.update(func_150502_a, "".length(), readableBytes, this.field_150506_c));
    }
    
    protected NettyEncryptionTranslator(final Cipher cipher) {
        this.field_150505_b = new byte["".length()];
        this.field_150506_c = new byte["".length()];
        this.cipher = cipher;
    }
    
    private byte[] func_150502_a(final ByteBuf byteBuf) {
        final int readableBytes = byteBuf.readableBytes();
        if (this.field_150505_b.length < readableBytes) {
            this.field_150505_b = new byte[readableBytes];
        }
        byteBuf.readBytes(this.field_150505_b, "".length(), readableBytes);
        return this.field_150505_b;
    }
}
