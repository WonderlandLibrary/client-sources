/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLHandshakeException;

final class SslUtils {
    static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
    static final String PROTOCOL_SSL_V2 = "SSLv2";
    static final String PROTOCOL_SSL_V3 = "SSLv3";
    static final String PROTOCOL_TLS_V1 = "TLSv1";
    static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
    static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
    static final int SSL_CONTENT_TYPE_ALERT = 21;
    static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
    static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
    static final int SSL_CONTENT_TYPE_EXTENSION_HEARTBEAT = 24;
    static final int SSL_RECORD_HEADER_LENGTH = 5;
    static final int NOT_ENOUGH_DATA = -1;
    static final int NOT_ENCRYPTED = -2;
    static final String[] DEFAULT_CIPHER_SUITES = new String[]{"TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"};

    static void addIfSupported(Set<String> set, List<String> list, String ... stringArray) {
        for (String string : stringArray) {
            if (!set.contains(string)) continue;
            list.add(string);
        }
    }

    static void useFallbackCiphersIfDefaultIsEmpty(List<String> list, Iterable<String> iterable) {
        if (list.isEmpty()) {
            for (String string : iterable) {
                if (string.startsWith("SSL_") || string.contains("_RC4_")) continue;
                list.add(string);
            }
        }
    }

    static void useFallbackCiphersIfDefaultIsEmpty(List<String> list, String ... stringArray) {
        SslUtils.useFallbackCiphersIfDefaultIsEmpty(list, Arrays.asList(stringArray));
    }

    static SSLHandshakeException toSSLHandshakeException(Throwable throwable) {
        if (throwable instanceof SSLHandshakeException) {
            return (SSLHandshakeException)throwable;
        }
        return (SSLHandshakeException)new SSLHandshakeException(throwable.getMessage()).initCause(throwable);
    }

    static int getEncryptedPacketLength(ByteBuf byteBuf, int n) {
        int n2;
        boolean bl;
        int n3 = 0;
        switch (byteBuf.getUnsignedByte(n)) {
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: {
                bl = true;
                break;
            }
            default: {
                bl = false;
            }
        }
        if (bl) {
            n2 = byteBuf.getUnsignedByte(n + 1);
            if (n2 == 3) {
                n3 = SslUtils.unsignedShortBE(byteBuf, n + 3) + 5;
                if (n3 <= 5) {
                    bl = false;
                }
            } else {
                bl = false;
            }
        }
        if (!bl) {
            n2 = (byteBuf.getUnsignedByte(n) & 0x80) != 0 ? 2 : 3;
            short s = byteBuf.getUnsignedByte(n + n2 + 1);
            if (s == 2 || s == 3) {
                int n4 = n3 = n2 == 2 ? (SslUtils.shortBE(byteBuf, n) & Short.MAX_VALUE) + 2 : (SslUtils.shortBE(byteBuf, n) & 0x3FFF) + 3;
                if (n3 <= n2) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return n3;
    }

    private static int unsignedShortBE(ByteBuf byteBuf, int n) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getUnsignedShort(n) : byteBuf.getUnsignedShortLE(n);
    }

    private static short shortBE(ByteBuf byteBuf, int n) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getShort(n) : byteBuf.getShortLE(n);
    }

    private static short unsignedByte(byte by) {
        return (short)(by & 0xFF);
    }

    private static int unsignedShortBE(ByteBuffer byteBuffer, int n) {
        return SslUtils.shortBE(byteBuffer, n) & 0xFFFF;
    }

    private static short shortBE(ByteBuffer byteBuffer, int n) {
        return byteBuffer.order() == ByteOrder.BIG_ENDIAN ? byteBuffer.getShort(n) : ByteBufUtil.swapShort(byteBuffer.getShort(n));
    }

    static int getEncryptedPacketLength(ByteBuffer[] byteBufferArray, int n) {
        ByteBuffer byteBuffer = byteBufferArray[n];
        if (byteBuffer.remaining() >= 5) {
            return SslUtils.getEncryptedPacketLength(byteBuffer);
        }
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
        do {
            if ((byteBuffer = byteBufferArray[n++].duplicate()).remaining() > byteBuffer2.remaining()) {
                byteBuffer.limit(byteBuffer.position() + byteBuffer2.remaining());
            }
            byteBuffer2.put(byteBuffer);
        } while (byteBuffer2.hasRemaining());
        byteBuffer2.flip();
        return SslUtils.getEncryptedPacketLength(byteBuffer2);
    }

    private static int getEncryptedPacketLength(ByteBuffer byteBuffer) {
        int n;
        boolean bl;
        int n2 = 0;
        int n3 = byteBuffer.position();
        switch (SslUtils.unsignedByte(byteBuffer.get(n3))) {
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: {
                bl = true;
                break;
            }
            default: {
                bl = false;
            }
        }
        if (bl) {
            n = SslUtils.unsignedByte(byteBuffer.get(n3 + 1));
            if (n == 3) {
                n2 = SslUtils.unsignedShortBE(byteBuffer, n3 + 3) + 5;
                if (n2 <= 5) {
                    bl = false;
                }
            } else {
                bl = false;
            }
        }
        if (!bl) {
            n = (SslUtils.unsignedByte(byteBuffer.get(n3)) & 0x80) != 0 ? 2 : 3;
            short s = SslUtils.unsignedByte(byteBuffer.get(n3 + n + 1));
            if (s == 2 || s == 3) {
                int n4 = n2 = n == 2 ? (SslUtils.shortBE(byteBuffer, n3) & Short.MAX_VALUE) + 2 : (SslUtils.shortBE(byteBuffer, n3) & 0x3FFF) + 3;
                if (n2 <= n) {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return n2;
    }

    static void handleHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable throwable, boolean bl) {
        channelHandlerContext.flush();
        if (bl) {
            channelHandlerContext.fireUserEventTriggered(new SslHandshakeCompletionEvent(throwable));
        }
        channelHandlerContext.close();
    }

    static void zeroout(ByteBuf byteBuf) {
        if (!byteBuf.isReadOnly()) {
            byteBuf.setZero(0, byteBuf.capacity());
        }
    }

    static void zerooutAndRelease(ByteBuf byteBuf) {
        SslUtils.zeroout(byteBuf);
        byteBuf.release();
    }

    static ByteBuf toBase64(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), true, Base64Dialect.STANDARD, byteBufAllocator);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return byteBuf2;
    }

    private SslUtils() {
    }
}

