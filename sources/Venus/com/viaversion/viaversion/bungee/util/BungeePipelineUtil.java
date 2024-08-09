/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bungee.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BungeePipelineUtil {
    private static Method DECODE_METHOD;
    private static Method ENCODE_METHOD;

    public static List<Object> callDecode(MessageToMessageDecoder messageToMessageDecoder, ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws InvocationTargetException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try {
            DECODE_METHOD.invoke(messageToMessageDecoder, channelHandlerContext, byteBuf, arrayList);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return arrayList;
    }

    public static ByteBuf callEncode(MessageToByteEncoder messageToByteEncoder, ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws InvocationTargetException {
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer();
        try {
            ENCODE_METHOD.invoke(messageToByteEncoder, channelHandlerContext, byteBuf, byteBuf2);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return byteBuf2;
    }

    public static ByteBuf decompress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        try {
            return (ByteBuf)BungeePipelineUtil.callDecode((MessageToMessageDecoder)channelHandlerContext.pipeline().get("decompress"), channelHandlerContext.pipeline().context("decompress"), byteBuf).get(0);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return channelHandlerContext.alloc().buffer();
        }
    }

    public static ByteBuf compress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        try {
            return BungeePipelineUtil.callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext.pipeline().context("compress"), byteBuf);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
            return channelHandlerContext.alloc().buffer();
        }
    }

    static {
        try {
            DECODE_METHOD = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class);
            DECODE_METHOD.setAccessible(false);
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
        try {
            ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
            ENCODE_METHOD.setAccessible(false);
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
    }
}

