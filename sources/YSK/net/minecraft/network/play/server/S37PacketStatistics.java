package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.google.common.collect.*;
import net.minecraft.stats.*;
import java.io.*;
import net.minecraft.network.*;
import java.util.*;

public class S37PacketStatistics implements Packet<INetHandlerPlayClient>
{
    private Map<StatBase, Integer> field_148976_a;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        this.field_148976_a = (Map<StatBase, Integer>)Maps.newHashMap();
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < varIntFromBuffer) {
            final StatBase oneShotStat = StatList.getOneShotStat(packetBuffer.readStringFromBuffer(9599 + 14947 - 17064 + 25285));
            final int varIntFromBuffer2 = packetBuffer.readVarIntFromBuffer();
            if (oneShotStat != null) {
                this.field_148976_a.put(oneShotStat, varIntFromBuffer2);
            }
            ++i;
        }
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S37PacketStatistics() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_148976_a.size());
        final Iterator<Map.Entry<StatBase, Integer>> iterator = this.field_148976_a.entrySet().iterator();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<StatBase, Integer> entry = iterator.next();
            packetBuffer.writeString(entry.getKey().statId);
            packetBuffer.writeVarIntToBuffer(entry.getValue());
        }
    }
    
    public S37PacketStatistics(final Map<StatBase, Integer> field_148976_a) {
        this.field_148976_a = field_148976_a;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleStatistics(this);
    }
    
    public Map<StatBase, Integer> func_148974_c() {
        return this.field_148976_a;
    }
}
