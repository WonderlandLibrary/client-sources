/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToMessageDecoder
 */
package de.gerrygames.viarewind.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class ForwardMessageToMessageDecoder
extends MessageToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        out.add(msg);
    }
}

