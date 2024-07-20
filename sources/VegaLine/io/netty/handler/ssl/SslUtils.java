/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import java.nio.ByteBuffer;

final class SslUtils {
    static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
    static final int SSL_CONTENT_TYPE_ALERT = 21;
    static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
    static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
    static final int SSL_RECORD_HEADER_LENGTH = 5;
    static final int NOT_ENOUGH_DATA = -1;
    static final int NOT_ENCRYPTED = -2;

    static int getEncryptedPacketLength(ByteBuf buffer, int offset) {
        boolean tls;
        int packetLength = 0;
        switch (buffer.getUnsignedByte(offset)) {
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                tls = true;
                break;
            }
            default: {
                tls = false;
            }
        }
        if (tls) {
            short majorVersion = buffer.getUnsignedByte(offset + 1);
            if (majorVersion == 3) {
                packetLength = buffer.getUnsignedShort(offset + 3) + 5;
                if (packetLength <= 5) {
                    tls = false;
                }
            } else {
                tls = false;
            }
        }
        if (!tls) {
            int headerLength = (buffer.getUnsignedByte(offset) & 0x80) != 0 ? 2 : 3;
            short majorVersion = buffer.getUnsignedByte(offset + headerLength + 1);
            if (majorVersion == 2 || majorVersion == 3) {
                packetLength = headerLength == 2 ? (buffer.getShort(offset) & Short.MAX_VALUE) + 2 : (buffer.getShort(offset) & 0x3FFF) + 3;
                if (packetLength <= headerLength) {
                    return -1;
                }
            } else {
                return -2;
            }
        }
        return packetLength;
    }

    private static short unsignedByte(byte b) {
        return (short)(b & 0xFF);
    }

    private static int unsignedShort(short s) {
        return s & 0xFFFF;
    }

    static int getEncryptedPacketLength(ByteBuffer[] buffers, int offset) {
        ByteBuffer buffer = buffers[offset];
        if (buffer.remaining() >= 5) {
            return SslUtils.getEncryptedPacketLength(buffer);
        }
        ByteBuffer tmp = ByteBuffer.allocate(5);
        do {
            if ((buffer = buffers[offset++].duplicate()).remaining() > tmp.remaining()) {
                buffer.limit(buffer.position() + tmp.remaining());
            }
            tmp.put(buffer);
        } while (tmp.hasRemaining());
        tmp.flip();
        return SslUtils.getEncryptedPacketLength(tmp);
    }

    private static int getEncryptedPacketLength(ByteBuffer buffer) {
        boolean tls;
        int packetLength = 0;
        int pos = buffer.position();
        switch (SslUtils.unsignedByte(buffer.get(pos))) {
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                tls = true;
                break;
            }
            default: {
                tls = false;
            }
        }
        if (tls) {
            short majorVersion = SslUtils.unsignedByte(buffer.get(pos + 1));
            if (majorVersion == 3) {
                packetLength = SslUtils.unsignedShort(buffer.getShort(pos + 3)) + 5;
                if (packetLength <= 5) {
                    tls = false;
                }
            } else {
                tls = false;
            }
        }
        if (!tls) {
            int headerLength = (SslUtils.unsignedByte(buffer.get(pos)) & 0x80) != 0 ? 2 : 3;
            short majorVersion = SslUtils.unsignedByte(buffer.get(pos + headerLength + 1));
            if (majorVersion == 2 || majorVersion == 3) {
                packetLength = headerLength == 2 ? (buffer.getShort(pos) & Short.MAX_VALUE) + 2 : (buffer.getShort(pos) & 0x3FFF) + 3;
                if (packetLength <= headerLength) {
                    return -1;
                }
            } else {
                return -2;
            }
        }
        return packetLength;
    }

    static void notifyHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
        ctx.flush();
        ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
        ctx.close();
    }

    static void zeroout(ByteBuf buffer) {
        if (!buffer.isReadOnly()) {
            buffer.setZero(0, buffer.capacity());
        }
    }

    static void zerooutAndRelease(ByteBuf buffer) {
        SslUtils.zeroout(buffer);
        buffer.release();
    }

    static ByteBuf toBase64(ByteBufAllocator allocator, ByteBuf src) {
        ByteBuf dst = Base64.encode(src, src.readerIndex(), src.readableBytes(), true, Base64Dialect.STANDARD, allocator);
        src.readerIndex(src.writerIndex());
        return dst;
    }

    private SslUtils() {
    }
}

