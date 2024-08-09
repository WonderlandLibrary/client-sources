/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public class SJoinGamePacket
implements IPacket<IClientPlayNetHandler> {
    private int playerId;
    private long hashedSeed;
    private boolean hardcoreMode;
    private GameType gameType;
    private GameType field_241785_e_;
    private Set<RegistryKey<World>> field_240811_e_;
    private DynamicRegistries.Impl field_240812_f_;
    private DimensionType field_240813_g_;
    private RegistryKey<World> dimension;
    private int maxPlayers;
    private int viewDistance;
    private boolean reducedDebugInfo;
    private boolean enableRespawnScreen;
    private boolean field_240814_m_;
    private boolean field_240815_n_;

    public SJoinGamePacket() {
    }

    public SJoinGamePacket(int n, GameType gameType, GameType gameType2, long l, boolean bl, Set<RegistryKey<World>> set, DynamicRegistries.Impl impl, DimensionType dimensionType, RegistryKey<World> registryKey, int n2, int n3, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        this.playerId = n;
        this.field_240811_e_ = set;
        this.field_240812_f_ = impl;
        this.field_240813_g_ = dimensionType;
        this.dimension = registryKey;
        this.hashedSeed = l;
        this.gameType = gameType;
        this.field_241785_e_ = gameType2;
        this.maxPlayers = n2;
        this.hardcoreMode = bl;
        this.viewDistance = n3;
        this.reducedDebugInfo = bl2;
        this.enableRespawnScreen = bl3;
        this.field_240814_m_ = bl4;
        this.field_240815_n_ = bl5;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.playerId = packetBuffer.readInt();
        this.hardcoreMode = packetBuffer.readBoolean();
        this.gameType = GameType.getByID(packetBuffer.readByte());
        this.field_241785_e_ = GameType.getByID(packetBuffer.readByte());
        int n = packetBuffer.readVarInt();
        this.field_240811_e_ = Sets.newHashSet();
        for (int i = 0; i < n; ++i) {
            this.field_240811_e_.add(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, packetBuffer.readResourceLocation()));
        }
        this.field_240812_f_ = packetBuffer.func_240628_a_(DynamicRegistries.Impl.registryCodec);
        this.field_240813_g_ = packetBuffer.func_240628_a_(DimensionType.DIMENSION_TYPE_CODEC).get();
        this.dimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, packetBuffer.readResourceLocation());
        this.hashedSeed = packetBuffer.readLong();
        this.maxPlayers = packetBuffer.readVarInt();
        this.viewDistance = packetBuffer.readVarInt();
        this.reducedDebugInfo = packetBuffer.readBoolean();
        this.enableRespawnScreen = packetBuffer.readBoolean();
        this.field_240814_m_ = packetBuffer.readBoolean();
        this.field_240815_n_ = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.playerId);
        packetBuffer.writeBoolean(this.hardcoreMode);
        packetBuffer.writeByte(this.gameType.getID());
        packetBuffer.writeByte(this.field_241785_e_.getID());
        packetBuffer.writeVarInt(this.field_240811_e_.size());
        for (RegistryKey<World> registryKey : this.field_240811_e_) {
            packetBuffer.writeResourceLocation(registryKey.getLocation());
        }
        packetBuffer.func_240629_a_(DynamicRegistries.Impl.registryCodec, this.field_240812_f_);
        packetBuffer.func_240629_a_(DimensionType.DIMENSION_TYPE_CODEC, this::lambda$writePacketData$0);
        packetBuffer.writeResourceLocation(this.dimension.getLocation());
        packetBuffer.writeLong(this.hashedSeed);
        packetBuffer.writeVarInt(this.maxPlayers);
        packetBuffer.writeVarInt(this.viewDistance);
        packetBuffer.writeBoolean(this.reducedDebugInfo);
        packetBuffer.writeBoolean(this.enableRespawnScreen);
        packetBuffer.writeBoolean(this.field_240814_m_);
        packetBuffer.writeBoolean(this.field_240815_n_);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleJoinGame(this);
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public long getHashedSeed() {
        return this.hashedSeed;
    }

    public boolean isHardcoreMode() {
        return this.hardcoreMode;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public GameType func_241786_f_() {
        return this.field_241785_e_;
    }

    public Set<RegistryKey<World>> func_240816_f_() {
        return this.field_240811_e_;
    }

    public DynamicRegistries func_240817_g_() {
        return this.field_240812_f_;
    }

    public DimensionType func_244297_i() {
        return this.field_240813_g_;
    }

    public RegistryKey<World> func_240819_i_() {
        return this.dimension;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }

    public boolean func_229743_k_() {
        return this.enableRespawnScreen;
    }

    public boolean func_240820_n_() {
        return this.field_240814_m_;
    }

    public boolean func_240821_o_() {
        return this.field_240815_n_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    private DimensionType lambda$writePacketData$0() {
        return this.field_240813_g_;
    }
}

