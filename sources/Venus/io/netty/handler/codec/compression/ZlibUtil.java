/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.Deflater
 *  com.jcraft.jzlib.Inflater
 *  com.jcraft.jzlib.JZlib
 *  com.jcraft.jzlib.JZlib$WrapperType
 */
package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.handler.codec.compression.CompressionException;
import io.netty.handler.codec.compression.DecompressionException;
import io.netty.handler.codec.compression.ZlibWrapper;

final class ZlibUtil {
    static void fail(Inflater inflater, String string, int n) {
        throw ZlibUtil.inflaterException(inflater, string, n);
    }

    static void fail(Deflater deflater, String string, int n) {
        throw ZlibUtil.deflaterException(deflater, string, n);
    }

    static DecompressionException inflaterException(Inflater inflater, String string, int n) {
        return new DecompressionException(string + " (" + n + ')' + (inflater.msg != null ? ": " + inflater.msg : ""));
    }

    static CompressionException deflaterException(Deflater deflater, String string, int n) {
        return new CompressionException(string + " (" + n + ')' + (deflater.msg != null ? ": " + deflater.msg : ""));
    }

    static JZlib.WrapperType convertWrapperType(ZlibWrapper zlibWrapper) {
        JZlib.WrapperType wrapperType;
        switch (1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()]) {
            case 1: {
                wrapperType = JZlib.W_NONE;
                break;
            }
            case 2: {
                wrapperType = JZlib.W_ZLIB;
                break;
            }
            case 3: {
                wrapperType = JZlib.W_GZIP;
                break;
            }
            case 4: {
                wrapperType = JZlib.W_ANY;
                break;
            }
            default: {
                throw new Error();
            }
        }
        return wrapperType;
    }

    static int wrapperOverhead(ZlibWrapper zlibWrapper) {
        int n;
        switch (1.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()]) {
            case 1: {
                n = 0;
                break;
            }
            case 2: 
            case 4: {
                n = 2;
                break;
            }
            case 3: {
                n = 10;
                break;
            }
            default: {
                throw new Error();
            }
        }
        return n;
    }

    private ZlibUtil() {
    }
}

