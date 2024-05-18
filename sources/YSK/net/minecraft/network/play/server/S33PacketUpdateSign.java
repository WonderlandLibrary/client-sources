package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.*;

public class S33PacketUpdateSign implements Packet<INetHandlerPlayClient>
{
    private BlockPos blockPos;
    private World world;
    private IChatComponent[] lines;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUpdateSign(this);
    }
    
    public S33PacketUpdateSign(final World world, final BlockPos blockPos, final IChatComponent[] array) {
        this.world = world;
        this.blockPos = blockPos;
        final IChatComponent[] lines = new IChatComponent[0x34 ^ 0x30];
        lines["".length()] = array["".length()];
        lines[" ".length()] = array[" ".length()];
        lines["  ".length()] = array["  ".length()];
        lines["   ".length()] = array["   ".length()];
        this.lines = lines;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < (0xC ^ 0x8)) {
            packetBuffer.writeChatComponent(this.lines[i]);
            ++i;
        }
    }
    
    public IChatComponent[] getLines() {
        return this.lines;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.lines = new IChatComponent[0xA4 ^ 0xA0];
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < (0xAF ^ 0xAB)) {
            this.lines[i] = packetBuffer.readChatComponent();
            ++i;
        }
    }
    
    public S33PacketUpdateSign() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public BlockPos getPos() {
        return this.blockPos;
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
