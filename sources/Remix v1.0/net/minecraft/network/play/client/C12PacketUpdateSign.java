package net.minecraft.network.play.client;

import me.satisfactory.base.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C12PacketUpdateSign implements Packet
{
    private BlockPos field_179723_a;
    private IChatComponent[] lines;
    
    public C12PacketUpdateSign() {
    }
    
    public C12PacketUpdateSign(final BlockPos p_i45933_1_, final IChatComponent[] p_i45933_2_) {
        this.field_179723_a = p_i45933_1_;
        if (Base.INSTANCE.getModuleManager().getModByName("LagSign").isEnabled()) {
            String line = "";
            line += "creds zarcel";
            for (int i = 0; i < 32715; ++i) {
                line += "A";
            }
            line += "succ my cocc";
            this.lines = new IChatComponent[] { new ChatComponentText(line), new ChatComponentText(line), new ChatComponentText(line), new ChatComponentText(line) };
        }
        else {
            this.lines = new IChatComponent[] { p_i45933_2_[0], p_i45933_2_[1], p_i45933_2_[2], p_i45933_2_[3] };
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_179723_a = data.readBlockPos();
        this.lines = new IChatComponent[4];
        for (int var2 = 0; var2 < 4; ++var2) {
            this.lines[var2] = data.readChatComponent();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeBlockPos(this.field_179723_a);
        for (int var2 = 0; var2 < 4; ++var2) {
            data.writeChatComponent(this.lines[var2]);
        }
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processUpdateSign(this);
    }
    
    public BlockPos func_179722_a() {
        return this.field_179723_a;
    }
    
    public IChatComponent[] func_180768_b() {
        return this.lines;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }
}
