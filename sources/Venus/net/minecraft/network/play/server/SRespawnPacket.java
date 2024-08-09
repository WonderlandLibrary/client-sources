/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public class SRespawnPacket
implements IPacket<IClientPlayNetHandler> {
    private DimensionType field_240822_a_;
    private RegistryKey<World> dimensionID;
    private long hashedSeed;
    private GameType gameType;
    private GameType field_241787_e_;
    private boolean field_240823_e_;
    private boolean field_240824_f_;
    private boolean field_240825_g_;

    public SRespawnPacket() {
    }

    public SRespawnPacket(DimensionType dimensionType, RegistryKey<World> registryKey, long l, GameType gameType, GameType gameType2, boolean bl, boolean bl2, boolean bl3) {
        this.field_240822_a_ = dimensionType;
        this.dimensionID = registryKey;
        this.hashedSeed = l;
        this.gameType = gameType;
        this.field_241787_e_ = gameType2;
        this.field_240823_e_ = bl;
        this.field_240824_f_ = bl2;
        this.field_240825_g_ = bl3;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleRespawn(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_240822_a_ = packetBuffer.func_240628_a_(DimensionType.DIMENSION_TYPE_CODEC).get();
        this.dimensionID = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, packetBuffer.readResourceLocation());
        this.hashedSeed = packetBuffer.readLong();
        this.gameType = GameType.getByID(packetBuffer.readUnsignedByte());
        this.field_241787_e_ = GameType.getByID(packetBuffer.readUnsignedByte());
        this.field_240823_e_ = packetBuffer.readBoolean();
        this.field_240824_f_ = packetBuffer.readBoolean();
        this.field_240825_g_ = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.func_240629_a_(DimensionType.DIMENSION_TYPE_CODEC, this::lambda$writePacketData$0);
        packetBuffer.writeResourceLocation(this.dimensionID.getLocation());
        packetBuffer.writeLong(this.hashedSeed);
        packetBuffer.writeByte(this.gameType.getID());
        packetBuffer.writeByte(this.field_241787_e_.getID());
        packetBuffer.writeBoolean(this.field_240823_e_);
        packetBuffer.writeBoolean(this.field_240824_f_);
        packetBuffer.writeBoolean(this.field_240825_g_);
    }

    public DimensionType func_244303_b() {
        return this.field_240822_a_;
    }

    public RegistryKey<World> func_240827_c_() {
        return this.dimensionID;
    }

    public long getHashedSeed() {
        return this.hashedSeed;
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public GameType func_241788_f_() {
        return this.field_241787_e_;
    }

    public boolean func_240828_f_() {
        return this.field_240823_e_;
    }

    public boolean func_240829_g_() {
        return this.field_240824_f_;
    }

    public boolean func_240830_h_() {
        return this.field_240825_g_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    private DimensionType lambda$writePacketData$0() {
        return this.field_240822_a_;
    }
}

