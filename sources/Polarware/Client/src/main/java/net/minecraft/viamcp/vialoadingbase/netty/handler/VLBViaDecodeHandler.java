/*
 * This file is part of ViaLoadingBase - https://github.com/FlorianMichael/ViaLoadingBase
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.minecraft.viamcp.vialoadingbase.netty.handler;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.PlayPongC2SPacket;
import net.minecraft.viamcp.vialoadingbase.ViaLoadingBase;

import java.util.List;

@ChannelHandler.Sharable
public class VLBViaDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection user;

    public VLBViaDecodeHandler(UserConnection user) {
        this.user = user;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!user.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }

        int id = Type.VAR_INT.readPrimitive(bytebuf);
        bytebuf = bytebuf.readerIndex(0);

        // TODO: remap packet id with 1.19+ ( je le ferais jamais c'est le 72ème todo dans le client connard )
        if(id == 48 && ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_17) && ViaLoadingBase.getInstance().getTargetVersion().isOlderThan(ProtocolVersion.v1_19)) {
            PacketBuffer packetBuffer = new PacketBuffer(bytebuf.copy());

            int packetId = packetBuffer.readVarIntFromBuffer();
            int parameter = packetBuffer.readInt();

            // scheduled task parce que sinon ça s'envoie pas dans l'ordre
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().getNetHandler().addToSendQueue(new PlayPongC2SPacket(parameter)));
            return;
        }

        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            user.transformIncoming(transformedBuf, CancelDecoderException::generate);

            out.add(transformedBuf.retain());
        } finally {
            transformedBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) return;

        if ((PipelineUtil.containsCause(cause, InformativeException.class)
                && user.getProtocolInfo().getState() != State.HANDSHAKE)
                || Via.getManager().debugHandler().enabled()) {
            cause.printStackTrace();
        }
    }
}