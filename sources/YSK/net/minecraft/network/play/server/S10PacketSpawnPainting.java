package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import java.io.*;
import net.minecraft.network.*;

public class S10PacketSpawnPainting implements Packet<INetHandlerPlayClient>
{
    private EnumFacing facing;
    private String title;
    private int entityID;
    private BlockPos position;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPainting(this);
    }
    
    public int getEntityID() {
        return this.entityID;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.title = packetBuffer.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
        this.position = packetBuffer.readBlockPos();
        this.facing = EnumFacing.getHorizontal(packetBuffer.readUnsignedByte());
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeString(this.title);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getHorizontalIndex());
    }
    
    public S10PacketSpawnPainting(final EntityPainting entityPainting) {
        this.entityID = entityPainting.getEntityId();
        this.position = entityPainting.getHangingPosition();
        this.facing = entityPainting.facingDirection;
        this.title = entityPainting.art.title;
    }
    
    public String getTitle() {
        return this.title;
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S10PacketSpawnPainting() {
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
}
