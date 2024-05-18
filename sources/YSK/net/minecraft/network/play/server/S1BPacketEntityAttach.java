package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;

public class S1BPacketEntityAttach implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private int vehicleEntityId;
    private int leash;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityAttach(this);
    }
    
    public S1BPacketEntityAttach(final int leash, final Entity entity, final Entity entity2) {
        this.leash = leash;
        this.entityId = entity.getEntityId();
        int entityId;
        if (entity2 != null) {
            entityId = entity2.getEntityId();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            entityId = -" ".length();
        }
        this.vehicleEntityId = entityId;
    }
    
    public S1BPacketEntityAttach() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeInt(this.vehicleEntityId);
        packetBuffer.writeByte(this.leash);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.vehicleEntityId = packetBuffer.readInt();
        this.leash = packetBuffer.readUnsignedByte();
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public int getVehicleEntityId() {
        return this.vehicleEntityId;
    }
    
    public int getLeash() {
        return this.leash;
    }
}
