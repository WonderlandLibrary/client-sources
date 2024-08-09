/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.FastThreadLocal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class WebSocketUtil {
    private static final FastThreadLocal<MessageDigest> MD5 = new FastThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() throws Exception {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new InternalError("MD5 not supported on this platform - Outdated?");
            }
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private static final FastThreadLocal<MessageDigest> SHA1 = new FastThreadLocal<MessageDigest>(){

        @Override
        protected MessageDigest initialValue() throws Exception {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new InternalError("SHA-1 not supported on this platform - Outdated?");
            }
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };

    static byte[] md5(byte[] byArray) {
        return WebSocketUtil.digest(MD5, byArray);
    }

    static byte[] sha1(byte[] byArray) {
        return WebSocketUtil.digest(SHA1, byArray);
    }

    private static byte[] digest(FastThreadLocal<MessageDigest> fastThreadLocal, byte[] byArray) {
        MessageDigest messageDigest = fastThreadLocal.get();
        messageDigest.reset();
        return messageDigest.digest(byArray);
    }

    static String base64(byte[] byArray) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(byArray);
        ByteBuf byteBuf2 = Base64.encode(byteBuf);
        String string = byteBuf2.toString(CharsetUtil.UTF_8);
        byteBuf2.release();
        return string;
    }

    static byte[] randomBytes(int n) {
        byte[] byArray = new byte[n];
        for (int i = 0; i < n; ++i) {
            byArray[i] = (byte)WebSocketUtil.randomNumber(0, 255);
        }
        return byArray;
    }

    static int randomNumber(int n, int n2) {
        return (int)(Math.random() * (double)n2 + (double)n);
    }

    private WebSocketUtil() {
    }
}

