/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PipelineUtil {
    private static final Method DECODE_METHOD;
    private static final Method ENCODE_METHOD;
    private static final Method MTM_DECODE;

    public static List<Object> callDecode(ByteToMessageDecoder byteToMessageDecoder, ChannelHandlerContext channelHandlerContext, Object object) throws InvocationTargetException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try {
            DECODE_METHOD.invoke(byteToMessageDecoder, channelHandlerContext, object, arrayList);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return arrayList;
    }

    public static void callEncode(MessageToByteEncoder messageToByteEncoder, ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws InvocationTargetException {
        try {
            ENCODE_METHOD.invoke(messageToByteEncoder, channelHandlerContext, object, byteBuf);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    public static List<Object> callDecode(MessageToMessageDecoder messageToMessageDecoder, ChannelHandlerContext channelHandlerContext, Object object) throws InvocationTargetException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try {
            MTM_DECODE.invoke(messageToMessageDecoder, channelHandlerContext, object, arrayList);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return arrayList;
    }

    public static boolean containsCause(Throwable throwable, Class<?> clazz) {
        while (throwable != null) {
            if (clazz.isAssignableFrom(throwable.getClass())) {
                return false;
            }
            throwable = throwable.getCause();
        }
        return true;
    }

    public static <T> @Nullable T getCause(Throwable throwable, Class<T> clazz) {
        while (throwable != null) {
            if (clazz.isAssignableFrom(throwable.getClass())) {
                return (T)throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }

    public static ChannelHandlerContext getContextBefore(String string, ChannelPipeline channelPipeline) {
        boolean bl = false;
        for (String string2 : channelPipeline.names()) {
            if (bl) {
                return channelPipeline.context(channelPipeline.get(string2));
            }
            if (!string2.equalsIgnoreCase(string)) continue;
            bl = true;
        }
        return null;
    }

    public static ChannelHandlerContext getPreviousContext(String string, ChannelPipeline channelPipeline) {
        String string2 = null;
        for (String string3 : channelPipeline.toMap().keySet()) {
            if (string3.equals(string)) {
                return channelPipeline.context(string2);
            }
            string2 = string3;
        }
        return null;
    }

    static {
        try {
            DECODE_METHOD = ByteToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, ByteBuf.class, List.class);
            DECODE_METHOD.setAccessible(false);
            ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
            ENCODE_METHOD.setAccessible(false);
            MTM_DECODE = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class);
            MTM_DECODE.setAccessible(false);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
    }
}

