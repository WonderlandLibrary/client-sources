package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.scoreboard.*;
import com.google.common.collect.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S3EPacketTeams implements Packet<INetHandlerPlayClient>
{
    private static final String[] I;
    private int field_149314_f;
    private String field_179816_e;
    private String field_149319_c;
    private String field_149320_a;
    private int field_179815_f;
    private int field_149315_g;
    private String field_149318_b;
    private String field_149316_d;
    private Collection<String> field_149317_e;
    
    public S3EPacketTeams(final ScorePlayerTeam scorePlayerTeam, final Collection<String> collection, final int field_149314_f) {
        this.field_149320_a = S3EPacketTeams.I[0x8C ^ 0x84];
        this.field_149318_b = S3EPacketTeams.I[0x41 ^ 0x48];
        this.field_149319_c = S3EPacketTeams.I[0x28 ^ 0x22];
        this.field_149316_d = S3EPacketTeams.I[0x3D ^ 0x36];
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -" ".length();
        this.field_149317_e = (Collection<String>)Lists.newArrayList();
        if (field_149314_f != "   ".length() && field_149314_f != (0x89 ^ 0x8D)) {
            throw new IllegalArgumentException(S3EPacketTeams.I[0x56 ^ 0x5A]);
        }
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(S3EPacketTeams.I[0x1F ^ 0x12]);
        }
        this.field_149314_f = field_149314_f;
        this.field_149320_a = scorePlayerTeam.getRegisteredName();
        this.field_149317_e.addAll(collection);
        "".length();
        if (-1 == 2) {
            throw null;
        }
    }
    
    public String func_149311_e() {
        return this.field_149319_c;
    }
    
    public int func_179813_h() {
        return this.field_179815_f;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.field_149320_a);
        packetBuffer.writeByte(this.field_149314_f);
        if (this.field_149314_f == 0 || this.field_149314_f == "  ".length()) {
            packetBuffer.writeString(this.field_149318_b);
            packetBuffer.writeString(this.field_149319_c);
            packetBuffer.writeString(this.field_149316_d);
            packetBuffer.writeByte(this.field_149315_g);
            packetBuffer.writeString(this.field_179816_e);
            packetBuffer.writeByte(this.field_179815_f);
        }
        if (this.field_149314_f == 0 || this.field_149314_f == "   ".length() || this.field_149314_f == (0x22 ^ 0x26)) {
            packetBuffer.writeVarIntToBuffer(this.field_149317_e.size());
            final Iterator<String> iterator = this.field_149317_e.iterator();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                packetBuffer.writeString(iterator.next());
            }
        }
    }
    
    public Collection<String> func_149310_g() {
        return this.field_149317_e;
    }
    
    public String func_179814_i() {
        return this.field_179816_e;
    }
    
    static {
        I();
    }
    
    public String func_149309_f() {
        return this.field_149316_d;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTeams(this);
    }
    
    public int func_149308_i() {
        return this.field_149315_g;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S3EPacketTeams() {
        this.field_149320_a = S3EPacketTeams.I["".length()];
        this.field_149318_b = S3EPacketTeams.I[" ".length()];
        this.field_149319_c = S3EPacketTeams.I["  ".length()];
        this.field_149316_d = S3EPacketTeams.I["   ".length()];
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -" ".length();
        this.field_149317_e = (Collection<String>)Lists.newArrayList();
    }
    
    public String func_149306_d() {
        return this.field_149318_b;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149320_a = packetBuffer.readStringFromBuffer(0xD0 ^ 0xC0);
        this.field_149314_f = packetBuffer.readByte();
        if (this.field_149314_f == 0 || this.field_149314_f == "  ".length()) {
            this.field_149318_b = packetBuffer.readStringFromBuffer(0x67 ^ 0x47);
            this.field_149319_c = packetBuffer.readStringFromBuffer(0x21 ^ 0x31);
            this.field_149316_d = packetBuffer.readStringFromBuffer(0xB4 ^ 0xA4);
            this.field_149315_g = packetBuffer.readByte();
            this.field_179816_e = packetBuffer.readStringFromBuffer(0x2F ^ 0xF);
            this.field_179815_f = packetBuffer.readByte();
        }
        if (this.field_149314_f == 0 || this.field_149314_f == "   ".length() || this.field_149314_f == (0x97 ^ 0x93)) {
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            int i = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (i < varIntFromBuffer) {
                this.field_149317_e.add(packetBuffer.readStringFromBuffer(0x4C ^ 0x64));
                ++i;
            }
        }
    }
    
    private static void I() {
        (I = new String[0x48 ^ 0x46])["".length()] = I("", "MIlEe");
        S3EPacketTeams.I[" ".length()] = I("", "HszVV");
        S3EPacketTeams.I["  ".length()] = I("", "PuMHe");
        S3EPacketTeams.I["   ".length()] = I("", "YQDJd");
        S3EPacketTeams.I[0x84 ^ 0x80] = I("", "SrWHW");
        S3EPacketTeams.I[0x6F ^ 0x6A] = I("", "aZQrg");
        S3EPacketTeams.I[0x61 ^ 0x67] = I("", "oHfTq");
        S3EPacketTeams.I[0x16 ^ 0x11] = I("", "FVvXo");
        S3EPacketTeams.I[0x32 ^ 0x3A] = I("", "jWdsj");
        S3EPacketTeams.I[0x70 ^ 0x79] = I("", "gcSun");
        S3EPacketTeams.I[0x74 ^ 0x7E] = I("", "VHHXZ");
        S3EPacketTeams.I[0x96 ^ 0x9D] = I("", "pXPex");
        S3EPacketTeams.I[0x87 ^ 0x8B] = I("\u0007$\u0002>\u0017.a\u001b#\u000b>a\u00143X .\u001f8X%3V:\u001d+7\u0013v\u001e%3V&\u0014+8\u0013$X).\u0018%\f84\u0015\"\u00178", "JAvVx");
        S3EPacketTeams.I[0x7D ^ 0x70] = I("16\u0002\u0000\u0012\u0013)C\u001a\u0016\u000f4\f\rW\u0003?C\u0017\u0002\r6L\u001c\u001a\u0011.\u001a", "aZcyw");
    }
    
    public int func_149307_h() {
        return this.field_149314_f;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S3EPacketTeams(final ScorePlayerTeam scorePlayerTeam, final int field_149314_f) {
        this.field_149320_a = S3EPacketTeams.I[0x16 ^ 0x12];
        this.field_149318_b = S3EPacketTeams.I[0x74 ^ 0x71];
        this.field_149319_c = S3EPacketTeams.I[0x15 ^ 0x13];
        this.field_149316_d = S3EPacketTeams.I[0x9D ^ 0x9A];
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -" ".length();
        this.field_149317_e = (Collection<String>)Lists.newArrayList();
        this.field_149320_a = scorePlayerTeam.getRegisteredName();
        this.field_149314_f = field_149314_f;
        if (field_149314_f == 0 || field_149314_f == "  ".length()) {
            this.field_149318_b = scorePlayerTeam.getTeamName();
            this.field_149319_c = scorePlayerTeam.getColorPrefix();
            this.field_149316_d = scorePlayerTeam.getColorSuffix();
            this.field_149315_g = scorePlayerTeam.func_98299_i();
            this.field_179816_e = scorePlayerTeam.getNameTagVisibility().field_178830_e;
            this.field_179815_f = scorePlayerTeam.getChatFormat().getColorIndex();
        }
        if (field_149314_f == 0) {
            this.field_149317_e.addAll(scorePlayerTeam.getMembershipCollection());
        }
    }
    
    public String func_149312_c() {
        return this.field_149320_a;
    }
}
