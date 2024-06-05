package net.minecraft.src;

import java.io.*;

public class Packet206SetObjective extends Packet
{
    public String objectiveName;
    public String objectiveDisplayName;
    public int change;
    
    public Packet206SetObjective() {
    }
    
    public Packet206SetObjective(final ScoreObjective par1, final int par2) {
        this.objectiveName = par1.getName();
        this.objectiveDisplayName = par1.getDisplayName();
        this.change = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.objectiveName = Packet.readString(par1DataInputStream, 16);
        this.objectiveDisplayName = Packet.readString(par1DataInputStream, 32);
        this.change = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.objectiveName, par1DataOutputStream);
        Packet.writeString(this.objectiveDisplayName, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.change);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSetObjective(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.objectiveName.length() + 2 + this.objectiveDisplayName.length() + 1;
    }
}
