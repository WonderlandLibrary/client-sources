/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard
implements Packet {
    private int field_149374_a;
    private String field_149373_b;
    private static final String __OBFID = "CL_00001325";

    public S3DPacketDisplayScoreboard() {
    }

    public S3DPacketDisplayScoreboard(int p_i45216_1_, ScoreObjective p_i45216_2_) {
        this.field_149374_a = p_i45216_1_;
        this.field_149373_b = p_i45216_2_ == null ? "" : p_i45216_2_.getName();
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.field_149374_a = data.readByte();
        this.field_149373_b = data.readStringFromBuffer(16);
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeByte(this.field_149374_a);
        data.writeString(this.field_149373_b);
    }

    public void func_180747_a(INetHandlerPlayClient p_180747_1_) {
        p_180747_1_.handleDisplayScoreboard(this);
    }

    public int func_149371_c() {
        return this.field_149374_a;
    }

    public String func_149370_d() {
        return this.field_149373_b;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180747_a((INetHandlerPlayClient)handler);
    }
}

