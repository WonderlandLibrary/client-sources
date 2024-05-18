package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.io.*;

public class S49PacketUpdateEntityNBT implements Packet<INetHandlerPlayClient>
{
    private NBTTagCompound tagCompound;
    private int entityId;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleEntityNBT(this);
    }
    
    public NBTTagCompound getTagCompound() {
        return this.tagCompound;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S49PacketUpdateEntityNBT() {
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S49PacketUpdateEntityNBT(final int entityId, final NBTTagCompound tagCompound) {
        this.entityId = entityId;
        this.tagCompound = tagCompound;
    }
    
    public Entity getEntity(final World world) {
        return world.getEntityByID(this.entityId);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeNBTTagCompoundToBuffer(this.tagCompound);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.tagCompound = packetBuffer.readNBTTagCompoundFromBuffer();
    }
}
