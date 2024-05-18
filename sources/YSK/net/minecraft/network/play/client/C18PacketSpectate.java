package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class C18PacketSpectate implements Packet<INetHandlerPlayServer>
{
    private UUID id;
    
    public C18PacketSpectate() {
    }
    
    public Entity getEntity(final WorldServer worldServer) {
        return worldServer.getEntityFromUuid(this.id);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readUuid();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer netHandlerPlayServer) {
        netHandlerPlayServer.handleSpectate(this);
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUuid(this.id);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayServer)netHandler);
    }
    
    public C18PacketSpectate(final UUID id) {
        this.id = id;
    }
}
