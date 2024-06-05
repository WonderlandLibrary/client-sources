package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3DPacketDisplayScoreboard implements Packet
{
    private int field_149374_a;
    private String field_149373_b;
    
    public S3DPacketDisplayScoreboard() {
    }
    
    public S3DPacketDisplayScoreboard(final int p_i45216_1_, final ScoreObjective p_i45216_2_) {
        this.field_149374_a = p_i45216_1_;
        if (p_i45216_2_ == null) {
            this.field_149373_b = "";
        }
        else {
            this.field_149373_b = p_i45216_2_.getName();
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_149374_a = data.readByte();
        this.field_149373_b = data.readStringFromBuffer(16);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeByte(this.field_149374_a);
        data.writeString(this.field_149373_b);
    }
    
    public void func_180747_a(final INetHandlerPlayClient p_180747_1_) {
        p_180747_1_.handleDisplayScoreboard(this);
    }
    
    public int func_149371_c() {
        return this.field_149374_a;
    }
    
    public String func_149370_d() {
        return this.field_149373_b;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180747_a((INetHandlerPlayClient)handler);
    }
}
