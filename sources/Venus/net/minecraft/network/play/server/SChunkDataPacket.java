/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.Heightmap;
import net.optifine.ChunkDataOF;
import net.optifine.ChunkOF;

public class SChunkDataPacket
implements IPacket<IClientPlayNetHandler> {
    private int chunkX;
    private int chunkZ;
    private int availableSections;
    private CompoundNBT heightmapTags;
    @Nullable
    private int[] biomes;
    private byte[] buffer;
    private List<CompoundNBT> tileEntityTags;
    private boolean fullChunk;
    private Map<String, Object> customData;

    public SChunkDataPacket() {
    }

    public SChunkDataPacket(Chunk chunk, int n) {
        ChunkPos chunkPos = chunk.getPos();
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.fullChunk = n == 65535;
        this.heightmapTags = new CompoundNBT();
        for (Map.Entry<Heightmap.Type, Heightmap> entry : chunk.getHeightmaps()) {
            if (!entry.getKey().isUsageClient()) continue;
            this.heightmapTags.put(entry.getKey().getId(), new LongArrayNBT(entry.getValue().getDataArray()));
        }
        if (this.fullChunk) {
            this.biomes = chunk.getBiomes().getBiomeIds();
        }
        this.buffer = new byte[this.calculateChunkSize(chunk, n)];
        this.availableSections = this.extractChunkData(new PacketBuffer(this.getWriteBuffer()), chunk, n);
        this.tileEntityTags = Lists.newArrayList();
        for (Map.Entry<Object, Object> entry : chunk.getTileEntityMap().entrySet()) {
            BlockPos blockPos = (BlockPos)entry.getKey();
            TileEntity tileEntity = (TileEntity)entry.getValue();
            int n2 = blockPos.getY() >> 4;
            if (!this.isFullChunk() && (n & 1 << n2) == 0) continue;
            CompoundNBT compoundNBT = tileEntity.getUpdateTag();
            this.tileEntityTags.add(compoundNBT);
        }
        this.customData = new HashMap<String, Object>();
        ChunkDataOF chunkDataOF = ChunkOF.makeChunkDataOF(chunk);
        this.customData.put("ChunkDataOF", chunkDataOF);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int n;
        this.chunkX = packetBuffer.readInt();
        this.chunkZ = packetBuffer.readInt();
        this.fullChunk = packetBuffer.readBoolean();
        this.availableSections = packetBuffer.readVarInt();
        this.heightmapTags = packetBuffer.readCompoundTag();
        if (this.fullChunk) {
            this.biomes = packetBuffer.readVarIntArray(BiomeContainer.BIOMES_SIZE);
        }
        if ((n = packetBuffer.readVarInt()) > 0x200000) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
        }
        this.buffer = new byte[n];
        packetBuffer.readBytes(this.buffer);
        int n2 = packetBuffer.readVarInt();
        this.tileEntityTags = Lists.newArrayList();
        for (int i = 0; i < n2; ++i) {
            this.tileEntityTags.add(packetBuffer.readCompoundTag());
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.chunkX);
        packetBuffer.writeInt(this.chunkZ);
        packetBuffer.writeBoolean(this.fullChunk);
        packetBuffer.writeVarInt(this.availableSections);
        packetBuffer.writeCompoundTag(this.heightmapTags);
        if (this.biomes != null) {
            packetBuffer.writeVarIntArray(this.biomes);
        }
        packetBuffer.writeVarInt(this.buffer.length);
        packetBuffer.writeBytes(this.buffer);
        packetBuffer.writeVarInt(this.tileEntityTags.size());
        for (CompoundNBT compoundNBT : this.tileEntityTags) {
            packetBuffer.writeCompoundTag(compoundNBT);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleChunkData(this);
    }

    public PacketBuffer getReadBuffer() {
        return new PacketBuffer(Unpooled.wrappedBuffer(this.buffer), this.customData);
    }

    private ByteBuf getWriteBuffer() {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(this.buffer);
        byteBuf.writerIndex(0);
        return byteBuf;
    }

    public int extractChunkData(PacketBuffer packetBuffer, Chunk chunk, int n) {
        int n2 = 0;
        ChunkSection[] chunkSectionArray = chunk.getSections();
        int n3 = chunkSectionArray.length;
        for (int i = 0; i < n3; ++i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (chunkSection == Chunk.EMPTY_SECTION || this.isFullChunk() && chunkSection.isEmpty() || (n & 1 << i) == 0) continue;
            n2 |= 1 << i;
            chunkSection.write(packetBuffer);
        }
        return n2;
    }

    protected int calculateChunkSize(Chunk chunk, int n) {
        int n2 = 0;
        ChunkSection[] chunkSectionArray = chunk.getSections();
        int n3 = chunkSectionArray.length;
        for (int i = 0; i < n3; ++i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (chunkSection == Chunk.EMPTY_SECTION || this.isFullChunk() && chunkSection.isEmpty() || (n & 1 << i) == 0) continue;
            n2 += chunkSection.getSize();
        }
        return n2;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public int getAvailableSections() {
        return this.availableSections;
    }

    public boolean isFullChunk() {
        return this.fullChunk;
    }

    public CompoundNBT getHeightmapTags() {
        return this.heightmapTags;
    }

    public List<CompoundNBT> getTileEntityTags() {
        return this.tileEntityTags;
    }

    @Nullable
    public int[] func_244296_i() {
        return this.biomes;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

