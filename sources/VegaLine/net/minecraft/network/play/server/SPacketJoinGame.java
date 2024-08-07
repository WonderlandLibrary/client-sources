/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class SPacketJoinGame
implements Packet<INetHandlerPlayClient> {
    private int playerId;
    private boolean hardcoreMode;
    private GameType gameType;
    private int dimension;
    private EnumDifficulty difficulty;
    private int maxPlayers;
    private WorldType worldType;
    private boolean reducedDebugInfo;

    public SPacketJoinGame() {
    }

    public SPacketJoinGame(int playerIdIn, GameType gameTypeIn, boolean hardcoreModeIn, int dimensionIn, EnumDifficulty difficultyIn, int maxPlayersIn, WorldType worldTypeIn, boolean reducedDebugInfoIn) {
        this.playerId = playerIdIn;
        this.dimension = dimensionIn;
        this.difficulty = difficultyIn;
        this.gameType = gameTypeIn;
        this.maxPlayers = maxPlayersIn;
        this.hardcoreMode = hardcoreModeIn;
        this.worldType = worldTypeIn;
        this.reducedDebugInfo = reducedDebugInfoIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.playerId = buf.readInt();
        int i = buf.readUnsignedByte();
        this.hardcoreMode = (i & 8) == 8;
        this.gameType = GameType.getByID(i &= 0xFFFFFFF7);
        this.dimension = buf.readInt();
        this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
        this.maxPlayers = buf.readUnsignedByte();
        this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
        if (this.worldType == null) {
            this.worldType = WorldType.DEFAULT;
        }
        this.reducedDebugInfo = buf.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeInt(this.playerId);
        int i = this.gameType.getID();
        if (this.hardcoreMode) {
            i |= 8;
        }
        buf.writeByte(i);
        buf.writeInt(this.dimension);
        buf.writeByte(this.difficulty.getDifficultyId());
        buf.writeByte(this.maxPlayers);
        buf.writeString(this.worldType.getWorldTypeName());
        buf.writeBoolean(this.reducedDebugInfo);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleJoinGame(this);
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public boolean isHardcoreMode() {
        return this.hardcoreMode;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public int getDimension() {
        return this.dimension;
    }

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public WorldType getWorldType() {
        return this.worldType;
    }

    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }
}

