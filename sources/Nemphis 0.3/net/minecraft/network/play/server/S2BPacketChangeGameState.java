/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState
implements Packet {
    public static final String[] MESSAGE_NAMES = new String[]{"tile.bed.notValid"};
    private int state;
    private float field_149141_c;
    private static final String __OBFID = "CL_00001301";

    public S2BPacketChangeGameState() {
    }

    public S2BPacketChangeGameState(int stateIn, float p_i45194_2_) {
        this.state = stateIn;
        this.field_149141_c = p_i45194_2_;
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.state = data.readUnsignedByte();
        this.field_149141_c = data.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeByte(this.state);
        data.writeFloat(this.field_149141_c);
    }

    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleChangeGameState(this);
    }

    public int func_149138_c() {
        return this.state;
    }

    public float func_149137_d() {
        return this.field_149141_c;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}

