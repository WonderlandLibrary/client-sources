package net.minecraft.src;

import java.io.*;

public class Packet207SetScore extends Packet
{
    public String itemName;
    public String scoreName;
    public int value;
    public int updateOrRemove;
    
    public Packet207SetScore() {
        this.itemName = "";
        this.scoreName = "";
        this.value = 0;
        this.updateOrRemove = 0;
    }
    
    public Packet207SetScore(final Score par1, final int par2) {
        this.itemName = "";
        this.scoreName = "";
        this.value = 0;
        this.updateOrRemove = 0;
        this.itemName = par1.func_96653_e();
        this.scoreName = par1.func_96645_d().getName();
        this.value = par1.func_96652_c();
        this.updateOrRemove = par2;
    }
    
    public Packet207SetScore(final String par1) {
        this.itemName = "";
        this.scoreName = "";
        this.value = 0;
        this.updateOrRemove = 0;
        this.itemName = par1;
        this.scoreName = "";
        this.value = 0;
        this.updateOrRemove = 1;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.itemName = Packet.readString(par1DataInputStream, 16);
        this.updateOrRemove = par1DataInputStream.readByte();
        if (this.updateOrRemove != 1) {
            this.scoreName = Packet.readString(par1DataInputStream, 16);
            this.value = par1DataInputStream.readInt();
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.itemName, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.updateOrRemove);
        if (this.updateOrRemove != 1) {
            Packet.writeString(this.scoreName, par1DataOutputStream);
            par1DataOutputStream.writeInt(this.value);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSetScore(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.itemName.length() + 2 + this.scoreName.length() + 4 + 1;
    }
}
