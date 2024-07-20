/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.type;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.ChunkSectionType1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;

public final class ChunkType1_20_2
extends Type<Chunk> {
    private final ChunkSectionType1_18 sectionType;
    private final int ySectionCount;

    public ChunkType1_20_2(int ySectionCount, int globalPaletteBlockBits, int globalPaletteBiomeBits) {
        super(Chunk.class);
        Preconditions.checkArgument(ySectionCount > 0);
        this.sectionType = new ChunkSectionType1_18(globalPaletteBlockBits, globalPaletteBiomeBits);
        this.ySectionCount = ySectionCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Chunk read(ByteBuf buffer) throws Exception {
        int chunkX = buffer.readInt();
        int chunkZ = buffer.readInt();
        CompoundTag heightMap = (CompoundTag)Type.NAMELESS_NBT.read(buffer);
        ByteBuf sectionsBuf = buffer.readBytes(Type.VAR_INT.readPrimitive(buffer));
        ChunkSection[] sections = new ChunkSection[this.ySectionCount];
        try {
            for (int i = 0; i < this.ySectionCount; ++i) {
                sections[i] = this.sectionType.read(sectionsBuf);
            }
        } finally {
            sectionsBuf.release();
        }
        int blockEntitiesLength = Type.VAR_INT.readPrimitive(buffer);
        ArrayList<BlockEntity> blockEntities = new ArrayList<BlockEntity>(blockEntitiesLength);
        for (int i = 0; i < blockEntitiesLength; ++i) {
            blockEntities.add((BlockEntity)Types1_20_2.BLOCK_ENTITY.read(buffer));
        }
        return new Chunk1_18(chunkX, chunkZ, sections, heightMap, blockEntities);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf buffer, Chunk chunk) throws Exception {
        buffer.writeInt(chunk.getX());
        buffer.writeInt(chunk.getZ());
        Type.NAMELESS_NBT.write(buffer, chunk.getHeightMap());
        ByteBuf sectionBuffer = buffer.alloc().buffer();
        try {
            for (ChunkSection section : chunk.getSections()) {
                this.sectionType.write(sectionBuffer, section);
            }
            sectionBuffer.readerIndex(0);
            Type.VAR_INT.writePrimitive(buffer, sectionBuffer.readableBytes());
            buffer.writeBytes(sectionBuffer);
        } finally {
            sectionBuffer.release();
        }
        Type.VAR_INT.writePrimitive(buffer, chunk.blockEntities().size());
        for (BlockEntity blockEntity : chunk.blockEntities()) {
            Types1_20_2.BLOCK_ENTITY.write(buffer, blockEntity);
        }
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}

