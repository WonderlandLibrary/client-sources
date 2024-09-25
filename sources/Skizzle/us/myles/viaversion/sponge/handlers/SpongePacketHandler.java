/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToMessageEncoder
 */
package us.myles.ViaVersion.sponge.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import us.myles.ViaVersion.api.data.UserConnection;

public class SpongePacketHandler
extends MessageToMessageEncoder {
    private final UserConnection info;

    public SpongePacketHandler(UserConnection info) {
        this.info = info;
    }

    protected void encode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        if (!(o instanceof ByteBuf)) {
            this.info.setLastPacket(o);
            if (this.info.isActive() && this.info.getProtocolInfo().getPipeline().filter(o, list)) {
                return;
            }
        }
        list.add(o);
    }
}

