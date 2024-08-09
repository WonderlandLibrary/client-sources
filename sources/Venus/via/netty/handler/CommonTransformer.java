/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.netty.handler;

import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;

public class CommonTransformer {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void decompress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws InvocationTargetException {
        ChannelHandler channelHandler = channelHandlerContext.pipeline().get("decompress");
        ByteBuf byteBuf2 = channelHandler instanceof MessageToMessageDecoder ? (ByteBuf)PipelineUtil.callDecode((MessageToMessageDecoder)channelHandler, channelHandlerContext, (Object)byteBuf).get(0) : (ByteBuf)PipelineUtil.callDecode((ByteToMessageDecoder)channelHandler, channelHandlerContext, (Object)byteBuf).get(0);
        try {
            byteBuf.clear().writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
    }

    public static void compress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer();
        try {
            PipelineUtil.callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext, byteBuf, byteBuf2);
            byteBuf.clear().writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
    }
}

