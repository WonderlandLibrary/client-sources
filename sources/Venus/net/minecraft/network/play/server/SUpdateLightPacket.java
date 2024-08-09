/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.WorldLightManager;

public class SUpdateLightPacket
implements IPacket<IClientPlayNetHandler> {
    private int chunkX;
    private int chunkZ;
    private int skyLightUpdateMask;
    private int blockLightUpdateMask;
    private int skyLightResetMask;
    private int blockLightResetMask;
    private List<byte[]> skyLightData;
    private List<byte[]> blockLightData;
    private boolean field_241783_i_;

    public SUpdateLightPacket() {
    }

    public SUpdateLightPacket(ChunkPos chunkPos, WorldLightManager worldLightManager, boolean bl) {
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.field_241783_i_ = bl;
        this.skyLightData = Lists.newArrayList();
        this.blockLightData = Lists.newArrayList();
        for (int i = 0; i < 18; ++i) {
            NibbleArray nibbleArray = worldLightManager.getLightEngine(LightType.SKY).getData(SectionPos.from(chunkPos, -1 + i));
            NibbleArray nibbleArray2 = worldLightManager.getLightEngine(LightType.BLOCK).getData(SectionPos.from(chunkPos, -1 + i));
            if (nibbleArray != null) {
                if (nibbleArray.isEmpty()) {
                    this.skyLightResetMask |= 1 << i;
                } else {
                    this.skyLightUpdateMask |= 1 << i;
                    this.skyLightData.add((byte[])nibbleArray.getData().clone());
                }
            }
            if (nibbleArray2 == null) continue;
            if (nibbleArray2.isEmpty()) {
                this.blockLightResetMask |= 1 << i;
                continue;
            }
            this.blockLightUpdateMask |= 1 << i;
            this.blockLightData.add((byte[])nibbleArray2.getData().clone());
        }
    }

    public SUpdateLightPacket(ChunkPos chunkPos, WorldLightManager worldLightManager, int n, int n2, boolean bl) {
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.field_241783_i_ = bl;
        this.skyLightUpdateMask = n;
        this.blockLightUpdateMask = n2;
        this.skyLightData = Lists.newArrayList();
        this.blockLightData = Lists.newArrayList();
        for (int i = 0; i < 18; ++i) {
            NibbleArray nibbleArray;
            if ((this.skyLightUpdateMask & 1 << i) != 0) {
                nibbleArray = worldLightManager.getLightEngine(LightType.SKY).getData(SectionPos.from(chunkPos, -1 + i));
                if (nibbleArray != null && !nibbleArray.isEmpty()) {
                    this.skyLightData.add((byte[])nibbleArray.getData().clone());
                } else {
                    this.skyLightUpdateMask &= ~(1 << i);
                    if (nibbleArray != null) {
                        this.skyLightResetMask |= 1 << i;
                    }
                }
            }
            if ((this.blockLightUpdateMask & 1 << i) == 0) continue;
            nibbleArray = worldLightManager.getLightEngine(LightType.BLOCK).getData(SectionPos.from(chunkPos, -1 + i));
            if (nibbleArray != null && !nibbleArray.isEmpty()) {
                this.blockLightData.add((byte[])nibbleArray.getData().clone());
                continue;
            }
            this.blockLightUpdateMask &= ~(1 << i);
            if (nibbleArray == null) continue;
            this.blockLightResetMask |= 1 << i;
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int n;
        this.chunkX = packetBuffer.readVarInt();
        this.chunkZ = packetBuffer.readVarInt();
        this.field_241783_i_ = packetBuffer.readBoolean();
        this.skyLightUpdateMask = packetBuffer.readVarInt();
        this.blockLightUpdateMask = packetBuffer.readVarInt();
        this.skyLightResetMask = packetBuffer.readVarInt();
        this.blockLightResetMask = packetBuffer.readVarInt();
        this.skyLightData = Lists.newArrayList();
        for (n = 0; n < 18; ++n) {
            if ((this.skyLightUpdateMask & 1 << n) == 0) continue;
            this.skyLightData.add(packetBuffer.readByteArray(2048));
        }
        this.blockLightData = Lists.newArrayList();
        for (n = 0; n < 18; ++n) {
            if ((this.blockLightUpdateMask & 1 << n) == 0) continue;
            this.blockLightData.add(packetBuffer.readByteArray(2048));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.chunkX);
        packetBuffer.writeVarInt(this.chunkZ);
        packetBuffer.writeBoolean(this.field_241783_i_);
        packetBuffer.writeVarInt(this.skyLightUpdateMask);
        packetBuffer.writeVarInt(this.blockLightUpdateMask);
        packetBuffer.writeVarInt(this.skyLightResetMask);
        packetBuffer.writeVarInt(this.blockLightResetMask);
        for (byte[] byArray : this.skyLightData) {
            packetBuffer.writeByteArray(byArray);
        }
        for (byte[] byArray : this.blockLightData) {
            packetBuffer.writeByteArray(byArray);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleUpdateLight(this);
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public int getSkyLightUpdateMask() {
        return this.skyLightUpdateMask;
    }

    public int getSkyLightResetMask() {
        return this.skyLightResetMask;
    }

    public List<byte[]> getSkyLightData() {
        return this.skyLightData;
    }

    public int getBlockLightUpdateMask() {
        return this.blockLightUpdateMask;
    }

    public int getBlockLightResetMask() {
        return this.blockLightResetMask;
    }

    public List<byte[]> getBlockLightData() {
        return this.blockLightData;
    }

    public boolean func_241784_j_() {
        return this.field_241783_i_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

