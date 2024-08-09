/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioByteChannel;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.WritableByteChannel;

public abstract class OioByteStreamChannel
extends AbstractOioByteChannel {
    private static final InputStream CLOSED_IN = new InputStream(){

        @Override
        public int read() {
            return 1;
        }
    };
    private static final OutputStream CLOSED_OUT = new OutputStream(){

        @Override
        public void write(int n) throws IOException {
            throw new ClosedChannelException();
        }
    };
    private InputStream is;
    private OutputStream os;
    private WritableByteChannel outChannel;

    protected OioByteStreamChannel(Channel channel) {
        super(channel);
    }

    protected final void activate(InputStream inputStream, OutputStream outputStream) {
        if (this.is != null) {
            throw new IllegalStateException("input was set already");
        }
        if (this.os != null) {
            throw new IllegalStateException("output was set already");
        }
        if (inputStream == null) {
            throw new NullPointerException("is");
        }
        if (outputStream == null) {
            throw new NullPointerException("os");
        }
        this.is = inputStream;
        this.os = outputStream;
    }

    @Override
    public boolean isActive() {
        InputStream inputStream = this.is;
        if (inputStream == null || inputStream == CLOSED_IN) {
            return true;
        }
        OutputStream outputStream = this.os;
        return outputStream != null && outputStream != CLOSED_OUT;
    }

    @Override
    protected int available() {
        try {
            return this.is.available();
        } catch (IOException iOException) {
            return 1;
        }
    }

    @Override
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.attemptedBytesRead(Math.max(1, Math.min(this.available(), byteBuf.maxWritableBytes())));
        return byteBuf.writeBytes(this.is, handle.attemptedBytesRead());
    }

    @Override
    protected void doWriteBytes(ByteBuf byteBuf) throws Exception {
        OutputStream outputStream = this.os;
        if (outputStream == null) {
            throw new NotYetConnectedException();
        }
        byteBuf.readBytes(outputStream, byteBuf.readableBytes());
    }

    @Override
    protected void doWriteFileRegion(FileRegion fileRegion) throws Exception {
        long l;
        OutputStream outputStream = this.os;
        if (outputStream == null) {
            throw new NotYetConnectedException();
        }
        if (this.outChannel == null) {
            this.outChannel = Channels.newChannel(outputStream);
        }
        long l2 = 0L;
        do {
            if ((l = fileRegion.transferTo(this.outChannel, l2)) != -1L) continue;
            OioByteStreamChannel.checkEOF(fileRegion);
            return;
        } while ((l2 += l) < fileRegion.count());
    }

    private static void checkEOF(FileRegion fileRegion) throws IOException {
        if (fileRegion.transferred() < fileRegion.count()) {
            throw new EOFException("Expected to be able to write " + fileRegion.count() + " bytes, but only wrote " + fileRegion.transferred());
        }
    }

    @Override
    protected void doClose() throws Exception {
        InputStream inputStream = this.is;
        OutputStream outputStream = this.os;
        this.is = CLOSED_IN;
        this.os = CLOSED_OUT;
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}

