package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import java.io.*;

public class S01PacketJoinGame implements Packet<INetHandlerPlayClient>
{
    private int dimension;
    private boolean hardcoreMode;
    private WorldSettings.GameType gameType;
    private WorldType worldType;
    private int maxPlayers;
    private EnumDifficulty difficulty;
    private boolean reducedDebugInfo;
    private int entityId;
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public S01PacketJoinGame() {
    }
    
    public boolean isHardcoreMode() {
        return this.hardcoreMode;
    }
    
    public S01PacketJoinGame(final int entityId, final WorldSettings.GameType gameType, final boolean hardcoreMode, final int dimension, final EnumDifficulty difficulty, final int maxPlayers, final WorldType worldType, final boolean reducedDebugInfo) {
        this.entityId = entityId;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gameType = gameType;
        this.maxPlayers = maxPlayers;
        this.hardcoreMode = hardcoreMode;
        this.worldType = worldType;
        this.reducedDebugInfo = reducedDebugInfo;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        final short unsignedByte = packetBuffer.readUnsignedByte();
        int hardcoreMode;
        if ((unsignedByte & (0x4 ^ 0xC)) == (0x8B ^ 0x83)) {
            hardcoreMode = " ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            hardcoreMode = "".length();
        }
        this.hardcoreMode = (hardcoreMode != 0);
        this.gameType = WorldSettings.GameType.getByID(unsignedByte & -(0x70 ^ 0x79));
        this.dimension = packetBuffer.readByte();
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
        this.maxPlayers = packetBuffer.readUnsignedByte();
        this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(0xA1 ^ 0xB1));
        if (this.worldType == null) {
            this.worldType = WorldType.DEFAULT;
        }
        this.reducedDebugInfo = packetBuffer.readBoolean();
    }
    
    public WorldType getWorldType() {
        return this.worldType;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        int id = this.gameType.getID();
        if (this.hardcoreMode) {
            id |= (0x79 ^ 0x71);
        }
        packetBuffer.writeByte(id);
        packetBuffer.writeByte(this.dimension);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.maxPlayers);
        packetBuffer.writeString(this.worldType.getWorldTypeName());
        packetBuffer.writeBoolean(this.reducedDebugInfo);
    }
    
    public int getDimension() {
        return this.dimension;
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleJoinGame(this);
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
}
