package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class S0APacketUseBed implements Packet<INetHandlerPlayClient>
{
    private int playerID;
    private BlockPos bedPos;
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleUseBed(this);
    }
    
    public BlockPos getBedPosition() {
        return this.bedPos;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S0APacketUseBed() {
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.playerID = packetBuffer.readVarIntFromBuffer();
        this.bedPos = packetBuffer.readBlockPos();
    }
    
    public S0APacketUseBed(final EntityPlayer entityPlayer, final BlockPos bedPos) {
        this.playerID = entityPlayer.getEntityId();
        this.bedPos = bedPos;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.playerID);
        packetBuffer.writeBlockPos(this.bedPos);
    }
    
    public EntityPlayer getPlayer(final World world) {
        return (EntityPlayer)world.getEntityByID(this.playerID);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
