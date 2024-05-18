package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import java.io.*;

public class S07PacketRespawn implements Packet<INetHandlerPlayClient>
{
    private int dimensionID;
    private EnumDifficulty difficulty;
    private WorldType worldType;
    private WorldSettings.GameType gameType;
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.dimensionID = packetBuffer.readInt();
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.gameType = WorldSettings.GameType.getByID(packetBuffer.readUnsignedByte());
        this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(0xBA ^ 0xAA));
        if (this.worldType == null) {
            this.worldType = WorldType.DEFAULT;
        }
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleRespawn(this);
    }
    
    public int getDimensionID() {
        return this.dimensionID;
    }
    
    public WorldType getWorldType() {
        return this.worldType;
    }
    
    public S07PacketRespawn() {
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.dimensionID);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.gameType.getID());
        packetBuffer.writeString(this.worldType.getWorldTypeName());
    }
    
    public S07PacketRespawn(final int dimensionID, final EnumDifficulty difficulty, final WorldType worldType, final WorldSettings.GameType gameType) {
        this.dimensionID = dimensionID;
        this.difficulty = difficulty;
        this.gameType = gameType;
        this.worldType = worldType;
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
}
