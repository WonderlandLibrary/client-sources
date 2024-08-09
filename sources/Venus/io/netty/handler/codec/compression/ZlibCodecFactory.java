/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.compression.JZlibDecoder;
import io.netty.handler.codec.compression.JZlibEncoder;
import io.netty.handler.codec.compression.JdkZlibDecoder;
import io.netty.handler.codec.compression.JdkZlibEncoder;
import io.netty.handler.codec.compression.ZlibDecoder;
import io.netty.handler.codec.compression.ZlibEncoder;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class ZlibCodecFactory {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
    private static final int DEFAULT_JDK_WINDOW_SIZE = 15;
    private static final int DEFAULT_JDK_MEM_LEVEL = 8;
    private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", PlatformDependent.javaVersion() < 7);
    private static final boolean noJdkZlibEncoder;
    private static final boolean supportsWindowSizeAndMemLevel;

    public static boolean isSupportingWindowSizeAndMemLevel() {
        return supportsWindowSizeAndMemLevel;
    }

    public static ZlibEncoder newZlibEncoder(int n) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder) {
            return new JZlibEncoder(n);
        }
        return new JdkZlibEncoder(n);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder) {
            return new JZlibEncoder(zlibWrapper);
        }
        return new JdkZlibEncoder(zlibWrapper);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper, int n) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder) {
            return new JZlibEncoder(zlibWrapper, n);
        }
        return new JdkZlibEncoder(zlibWrapper, n);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper, int n, int n2, int n3) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder || n2 != 15 || n3 != 8) {
            return new JZlibEncoder(zlibWrapper, n, n2, n3);
        }
        return new JdkZlibEncoder(zlibWrapper, n);
    }

    public static ZlibEncoder newZlibEncoder(byte[] byArray) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder) {
            return new JZlibEncoder(byArray);
        }
        return new JdkZlibEncoder(byArray);
    }

    public static ZlibEncoder newZlibEncoder(int n, byte[] byArray) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder) {
            return new JZlibEncoder(n, byArray);
        }
        return new JdkZlibEncoder(n, byArray);
    }

    public static ZlibEncoder newZlibEncoder(int n, int n2, int n3, byte[] byArray) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibEncoder || n2 != 15 || n3 != 8) {
            return new JZlibEncoder(n, n2, n3, byArray);
        }
        return new JdkZlibEncoder(n, byArray);
    }

    public static ZlibDecoder newZlibDecoder() {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
            return new JZlibDecoder();
        }
        return new JdkZlibDecoder(true);
    }

    public static ZlibDecoder newZlibDecoder(ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
            return new JZlibDecoder(zlibWrapper);
        }
        return new JdkZlibDecoder(zlibWrapper, true);
    }

    public static ZlibDecoder newZlibDecoder(byte[] byArray) {
        if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
            return new JZlibDecoder(byArray);
        }
        return new JdkZlibDecoder(byArray);
    }

    private ZlibCodecFactory() {
    }

    static {
        logger.debug("-Dio.netty.noJdkZlibDecoder: {}", (Object)noJdkZlibDecoder);
        noJdkZlibEncoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibEncoder", false);
        logger.debug("-Dio.netty.noJdkZlibEncoder: {}", (Object)noJdkZlibEncoder);
        supportsWindowSizeAndMemLevel = noJdkZlibDecoder || PlatformDependent.javaVersion() >= 7;
    }
}

