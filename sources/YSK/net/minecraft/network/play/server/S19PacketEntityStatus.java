package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.world.*;

public class S19PacketEntityStatus implements Packet<INetHandlerPlayClient>
{
    private int entityId;
    private byte logicOpcode;
    
    public S19PacketEntityStatus(final Entity entity, final byte logicOpcode) {
        this.entityId = entity.getEntityId();
        this.logicOpcode = logicOpcode;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public byte getOpCode() {
        return this.logicOpcode;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityStatus(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeByte(this.logicOpcode);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.logicOpcode = packetBuffer.readByte();
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    public S19PacketEntityStatus() {
    }
}
