/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;

public class Chunk1_9_1_2Type
extends PartialType<Chunk, ClientWorld> {
    public Chunk1_9_1_2Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
        int[] nArray;
        int n;
        int n2 = byteBuf.readInt();
        int n3 = byteBuf.readInt();
        boolean bl = byteBuf.readBoolean();
        int n4 = Type.VAR_INT.readPrimitive(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        BitSet bitSet = new BitSet(16);
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        for (n = 0; n < 16; ++n) {
            if ((n4 & 1 << n) == 0) continue;
            bitSet.set(n);
        }
        for (n = 0; n < 16; ++n) {
            ChunkSection chunkSection;
            if (!bitSet.get(n)) continue;
            chunkSectionArray[n] = chunkSection = (ChunkSection)Types1_9.CHUNK_SECTION.read(byteBuf);
            chunkSection.getLight().readBlockLight(byteBuf);
            if (clientWorld.getEnvironment() != Environment.NORMAL) continue;
            chunkSection.getLight().readSkyLight(byteBuf);
        }
        int[] nArray2 = nArray = bl ? new int[256] : null;
        if (bl) {
            for (int i = 0; i < 256; ++i) {
                nArray[i] = byteBuf.readByte() & 0xFF;
            }
        }
        return new BaseChunk(n2, n3, bl, false, n4, chunkSectionArray, nArray, new ArrayList<CompoundTag>());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, ClientWorld clientWorld, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        ByteBuf byteBuf2 = byteBuf.alloc().buffer();
        try {
            for (int i = 0; i < 16; ++i) {
                ChunkSection chunkSection = chunk.getSections()[i];
                if (chunkSection == null) continue;
                Types1_9.CHUNK_SECTION.write(byteBuf2, chunkSection);
                chunkSection.getLight().writeBlockLight(byteBuf2);
                if (!chunkSection.getLight().hasSkyLight()) continue;
                chunkSection.getLight().writeSkyLight(byteBuf2);
            }
            byteBuf2.readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, byteBuf2.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
            byteBuf.writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
        if (chunk.isBiomeData()) {
            for (int n : chunk.getBiomeData()) {
                byteBuf.writeByte((byte)n);
            }
        }
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }

    @Override
    public void write(ByteBuf byteBuf, Object object, Object object2) throws Exception {
        this.write(byteBuf, (ClientWorld)object, (Chunk)object2);
    }

    @Override
    public Object read(ByteBuf byteBuf, Object object) throws Exception {
        return this.read(byteBuf, (ClientWorld)object);
    }
}

