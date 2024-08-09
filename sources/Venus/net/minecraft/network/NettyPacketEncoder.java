/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.SkipableEncoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketEncoder
extends MessageToByteEncoder<IPacket<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
    private final PacketDirection direction;

    public NettyPacketEncoder(PacketDirection packetDirection) {
        this.direction = packetDirection;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacket<?> iPacket, ByteBuf byteBuf) throws Exception {
        ProtocolType protocolType = channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
        if (protocolType == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + iPacket);
        }
        Integer n = protocolType.getPacketId(this.direction, iPacket);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", (Object)channelHandlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), (Object)n, (Object)iPacket.getClass().getName());
        }
        if (n == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeVarInt(n);
        try {
            iPacket.writePacketData(packetBuffer);
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
            if (iPacket.shouldSkipErrors()) {
                throw new SkipableEncoderException(throwable);
            }
            throw throwable;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (IPacket)object, byteBuf);
    }
}

