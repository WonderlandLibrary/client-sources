package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class S1CPacketEntityMetadata implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private List<DataWatcher.WatchableObject> field_149378_b;
    
    public S1CPacketEntityMetadata() {
    }
    
    public S1CPacketEntityMetadata(final int entityId, final DataWatcher dataWatcher, final boolean b) {
        this.entityId = entityId;
        if (b) {
            this.field_149378_b = dataWatcher.getAllWatched();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            this.field_149378_b = dataWatcher.getChanged();
        }
    }
    
    public List<DataWatcher.WatchableObject> func_149376_c() {
        return this.field_149378_b;
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityMetadata(this);
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, packetBuffer);
    }
}
