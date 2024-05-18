package net.minecraft.src;

import java.io.*;

public class Packet208SetDisplayObjective extends Packet
{
    public int scoreboardPosition;
    public String scoreName;
    
    public Packet208SetDisplayObjective() {
    }
    
    public Packet208SetDisplayObjective(final int par1, final ScoreObjective par2ScoreObjective) {
        this.scoreboardPosition = par1;
        if (par2ScoreObjective == null) {
            this.scoreName = "";
        }
        else {
            this.scoreName = par2ScoreObjective.getName();
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.scoreboardPosition = par1DataInputStream.readByte();
        this.scoreName = Packet.readString(par1DataInputStream, 16);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.scoreboardPosition);
        Packet.writeString(this.scoreName, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSetDisplayObjective(this);
    }
    
    @Override
    public int getPacketSize() {
        return 3 + this.scoreName.length();
    }
}
