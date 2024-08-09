/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptionTranslator {
    private final Cipher cipher;
    private byte[] inputBuffer = new byte[0];
    private byte[] outputBuffer = new byte[0];

    protected NettyEncryptionTranslator(Cipher cipher) {
        this.cipher = cipher;
    }

    private byte[] bufToBytes(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (this.inputBuffer.length < n) {
            this.inputBuffer = new byte[n];
        }
        byteBuf.readBytes(this.inputBuffer, 0, n);
        return this.inputBuffer;
    }

    protected ByteBuf decipher(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws ShortBufferException {
        int n = byteBuf.readableBytes();
        byte[] byArray = this.bufToBytes(byteBuf);
        ByteBuf byteBuf2 = channelHandlerContext.alloc().heapBuffer(this.cipher.getOutputSize(n));
        byteBuf2.writerIndex(this.cipher.update(byArray, 0, n, byteBuf2.array(), byteBuf2.arrayOffset()));
        return byteBuf2;
    }

    protected void cipher(ByteBuf byteBuf, ByteBuf byteBuf2) throws ShortBufferException {
        int n = byteBuf.readableBytes();
        byte[] byArray = this.bufToBytes(byteBuf);
        int n2 = this.cipher.getOutputSize(n);
        if (this.outputBuffer.length < n2) {
            this.outputBuffer = new byte[n2];
        }
        byteBuf2.writeBytes(this.outputBuffer, 0, this.cipher.update(byArray, 0, n, this.outputBuffer));
    }
}

