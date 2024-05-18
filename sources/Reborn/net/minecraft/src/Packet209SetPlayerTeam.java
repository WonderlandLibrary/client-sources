package net.minecraft.src;

import java.io.*;
import java.util.*;

public class Packet209SetPlayerTeam extends Packet
{
    public String teamName;
    public String teamDisplayName;
    public String teamPrefix;
    public String teamSuffix;
    public Collection playerNames;
    public int mode;
    public int friendlyFire;
    
    public Packet209SetPlayerTeam() {
        this.teamName = "";
        this.teamDisplayName = "";
        this.teamPrefix = "";
        this.teamSuffix = "";
        this.playerNames = new ArrayList();
        this.mode = 0;
    }
    
    public Packet209SetPlayerTeam(final ScorePlayerTeam par1, final int par2) {
        this.teamName = "";
        this.teamDisplayName = "";
        this.teamPrefix = "";
        this.teamSuffix = "";
        this.playerNames = new ArrayList();
        this.mode = 0;
        this.teamName = par1.func_96661_b();
        this.mode = par2;
        if (par2 == 0 || par2 == 2) {
            this.teamDisplayName = par1.func_96669_c();
            this.teamPrefix = par1.func_96668_e();
            this.teamSuffix = par1.func_96663_f();
            this.friendlyFire = par1.func_98299_i();
        }
        if (par2 == 0) {
            this.playerNames.addAll(par1.getMembershipCollection());
        }
    }
    
    public Packet209SetPlayerTeam(final ScorePlayerTeam par1ScorePlayerTeam, final Collection par2Collection, final int par3) {
        this.teamName = "";
        this.teamDisplayName = "";
        this.teamPrefix = "";
        this.teamSuffix = "";
        this.playerNames = new ArrayList();
        this.mode = 0;
        if (par3 != 3 && par3 != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (par2Collection != null && !par2Collection.isEmpty()) {
            this.mode = par3;
            this.teamName = par1ScorePlayerTeam.func_96661_b();
            this.playerNames.addAll(par2Collection);
            return;
        }
        throw new IllegalArgumentException("Players cannot be null/empty");
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.teamName = Packet.readString(par1DataInputStream, 16);
        this.mode = par1DataInputStream.readByte();
        if (this.mode == 0 || this.mode == 2) {
            this.teamDisplayName = Packet.readString(par1DataInputStream, 32);
            this.teamPrefix = Packet.readString(par1DataInputStream, 16);
            this.teamSuffix = Packet.readString(par1DataInputStream, 16);
            this.friendlyFire = par1DataInputStream.readByte();
        }
        if (this.mode == 0 || this.mode == 3 || this.mode == 4) {
            final short var2 = par1DataInputStream.readShort();
            for (int var3 = 0; var3 < var2; ++var3) {
                this.playerNames.add(Packet.readString(par1DataInputStream, 16));
            }
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.teamName, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.mode);
        if (this.mode == 0 || this.mode == 2) {
            Packet.writeString(this.teamDisplayName, par1DataOutputStream);
            Packet.writeString(this.teamPrefix, par1DataOutputStream);
            Packet.writeString(this.teamSuffix, par1DataOutputStream);
            par1DataOutputStream.writeByte(this.friendlyFire);
        }
        if (this.mode == 0 || this.mode == 3 || this.mode == 4) {
            par1DataOutputStream.writeShort(this.playerNames.size());
            for (final String var3 : this.playerNames) {
                Packet.writeString(var3, par1DataOutputStream);
            }
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSetPlayerTeam(this);
    }
    
    @Override
    public int getPacketSize() {
        return 3 + this.teamName.length();
    }
}
