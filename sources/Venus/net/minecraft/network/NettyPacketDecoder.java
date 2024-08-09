/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketDecoder
extends ByteToMessageDecoder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.NETWORK_PACKETS_MARKER);
    private final PacketDirection direction;

    public NettyPacketDecoder(PacketDirection packetDirection) {
        this.direction = packetDirection;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            int n = packetBuffer.readVarInt();
            IPacket<?> iPacket = channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get().getPacket(this.direction, n);
            if (iPacket == null) {
                throw new IOException("Bad packet id " + n);
            }
            iPacket.readPacketData(packetBuffer);
            if (packetBuffer.readableBytes() > 0) {
                throw new IOException("Packet " + channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get().getId() + "/" + n + " (" + iPacket.getClass().getSimpleName() + ") was larger than I expected, found " + packetBuffer.readableBytes() + " bytes extra whilst reading packet " + n);
            }
            list.add(iPacket);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", (Object)channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), (Object)n, (Object)iPacket.getClass().getName());
            }
        }
    }
}

