package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;

public class C12PacketUpdateSign implements Packet<INetHandlerPlayServer>
{
    private BlockPos pos;
    private IChatComponent[] lines;
    
    public BlockPos getPosition() {
        return this.pos;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < (0x15 ^ 0x11)) {
            packetBuffer.writeString(IChatComponent.Serializer.componentToJson(this.lines[i]));
            ++i;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.lines = new IChatComponent[0xC3 ^ 0xC7];
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < (0x79 ^ 0x7D)) {
            this.lines[i] = IChatComponent.Serializer.jsonToComponent(packetBuffer.readStringFromBuffer(98 + 194 - 112 + 204));
            ++i;
        }
    }
    
    public C12PacketUpdateSign(final BlockPos pos, final IChatComponent[] array) {
        this.pos = pos;
        final IChatComponent[] lines = new IChatComponent[0x4A ^ 0x4E];
        lines["".length()] = array["".length()];
        lines[" ".length()] = array[" ".length()];
        lines["  ".length()] = array["  ".length()];
        lines["   ".length()] = array["   ".length()];
        this.lines = lines;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.processUpdateSign(this);
    }
    
    public IChatComponent[] getLines() {
        return this.lines;
    }
    
    public C12PacketUpdateSign() {
    }
}
