package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.scoreboard.*;

public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient>
{
    private int value;
    private Action action;
    private static final String[] I;
    private String objective;
    private String name;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    private static void I() {
        (I = new String[0x7C ^ 0x75])["".length()] = I("", "nPzae");
        S3CPacketUpdateScore.I[" ".length()] = I("", "llXaX");
        S3CPacketUpdateScore.I["  ".length()] = I("", "IBqzT");
        S3CPacketUpdateScore.I["   ".length()] = I("", "XKwnk");
        S3CPacketUpdateScore.I[0x56 ^ 0x52] = I("", "tvFyg");
        S3CPacketUpdateScore.I[0x54 ^ 0x51] = I("", "xjzoe");
        S3CPacketUpdateScore.I[0x8B ^ 0x8D] = I("", "tkqec");
        S3CPacketUpdateScore.I[0xB ^ 0xC] = I("", "WORLC");
        S3CPacketUpdateScore.I[0x65 ^ 0x6D] = I("", "phuUJ");
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S3CPacketUpdateScore() {
        this.name = S3CPacketUpdateScore.I["".length()];
        this.objective = S3CPacketUpdateScore.I[" ".length()];
    }
    
    public String getPlayerName() {
        return this.name;
    }
    
    public Action getScoreAction() {
        return this.action;
    }
    
    public S3CPacketUpdateScore(final Score score) {
        this.name = S3CPacketUpdateScore.I["  ".length()];
        this.objective = S3CPacketUpdateScore.I["   ".length()];
        this.name = score.getPlayerName();
        this.objective = score.getObjective().getName();
        this.value = score.getScorePoints();
        this.action = Action.CHANGE;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.name = packetBuffer.readStringFromBuffer(0x42 ^ 0x6A);
        this.action = packetBuffer.readEnumValue(Action.class);
        this.objective = packetBuffer.readStringFromBuffer(0x2F ^ 0x3F);
        if (this.action != Action.REMOVE) {
            this.value = packetBuffer.readVarIntFromBuffer();
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateScore(this);
    }
    
    public int getScoreValue() {
        return this.value;
    }
    
    public S3CPacketUpdateScore(final String name, final ScoreObjective scoreObjective) {
        this.name = S3CPacketUpdateScore.I[0xC ^ 0xB];
        this.objective = S3CPacketUpdateScore.I[0xA8 ^ 0xA0];
        this.name = name;
        this.objective = scoreObjective.getName();
        this.value = "".length();
        this.action = Action.REMOVE;
    }
    
    public S3CPacketUpdateScore(final String name) {
        this.name = S3CPacketUpdateScore.I[0x12 ^ 0x16];
        this.objective = S3CPacketUpdateScore.I[0x6E ^ 0x6B];
        this.name = name;
        this.objective = S3CPacketUpdateScore.I[0x60 ^ 0x66];
        this.value = "".length();
        this.action = Action.REMOVE;
    }
    
    static {
        I();
    }
    
    public String getObjectiveName() {
        return this.objective;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.name);
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeString(this.objective);
        if (this.action != Action.REMOVE) {
            packetBuffer.writeVarIntToBuffer(this.value);
        }
    }
    
    public enum Action
    {
        REMOVE(Action.I[" ".length()], " ".length()), 
        CHANGE(Action.I["".length()], "".length());
        
        private static final Action[] ENUM$VALUES;
        private static final String[] I;
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u001b\u00056:-\u001d", "XMwtj");
            Action.I[" ".length()] = I("9\u000b\u0017\u001f%.", "kNZPs");
        }
        
        private Action(final String s, final int n) {
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action["  ".length()];
            enum$VALUES["".length()] = Action.CHANGE;
            enum$VALUES[" ".length()] = Action.REMOVE;
            ENUM$VALUES = enum$VALUES;
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
                if (1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
