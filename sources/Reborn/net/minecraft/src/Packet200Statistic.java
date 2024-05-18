package net.minecraft.src;

import java.io.*;

public class Packet200Statistic extends Packet
{
    public int statisticId;
    public int amount;
    
    public Packet200Statistic() {
    }
    
    public Packet200Statistic(final int par1, final int par2) {
        this.statisticId = par1;
        this.amount = par2;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleStatistic(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.statisticId = par1DataInputStream.readInt();
        this.amount = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.statisticId);
        par1DataOutputStream.writeByte(this.amount);
    }
    
    @Override
    public int getPacketSize() {
        return 6;
    }
    
    @Override
    public boolean canProcessAsync() {
        return true;
    }
}
