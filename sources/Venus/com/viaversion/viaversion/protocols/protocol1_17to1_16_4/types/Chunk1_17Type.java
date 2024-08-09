/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Chunk1_17Type
extends Type<Chunk> {
    private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
    private final int ySectionCount;

    public Chunk1_17Type(int n) {
        super(Chunk.class);
        Preconditions.checkArgument(n > 0);
        this.ySectionCount = n;
    }

    @Override
    public Chunk read(ByteBuf byteBuf) throws Exception {
        int n = byteBuf.readInt();
        int n2 = byteBuf.readInt();
        BitSet bitSet = BitSet.valueOf((long[])Type.LONG_ARRAY_PRIMITIVE.read(byteBuf));
        CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        int[] nArray = (int[])Type.VAR_INT_ARRAY_PRIMITIVE.read(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        ChunkSection[] chunkSectionArray = new ChunkSection[this.ySectionCount];
        for (int i = 0; i < this.ySectionCount; ++i) {
            if (!bitSet.get(i)) continue;
            short s = byteBuf.readShort();
            ChunkSection chunkSection = (ChunkSection)Types1_16.CHUNK_SECTION.read(byteBuf);
            chunkSection.setNonAirBlocksCount(s);
            chunkSectionArray[i] = chunkSection;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList((Object[])Type.NBT_ARRAY.read(byteBuf)));
        if (byteBuf.readableBytes() > 0) {
            byte[] byArray = (byte[])Type.REMAINING_BYTES.read(byteBuf);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + byArray.length + " more bytes than expected while reading the chunk: " + n + "/" + n2);
            }
        }
        return new BaseChunk(n, n2, true, false, bitSet, chunkSectionArray, nArray, compoundTag, arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        Type.LONG_ARRAY_PRIMITIVE.write(byteBuf, chunk.getChunkMask().toLongArray());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        Type.VAR_INT_ARRAY_PRIMITIVE.write(byteBuf, chunk.getBiomeData());
        ByteBuf byteBuf2 = byteBuf.alloc().buffer();
        try {
            ChunkSection[] chunkSectionArray;
            for (ChunkSection chunkSection : chunkSectionArray = chunk.getSections()) {
                if (chunkSection == null) continue;
                byteBuf2.writeShort(chunkSection.getNonAirBlocksCount());
                Types1_16.CHUNK_SECTION.write(byteBuf2, chunkSection);
            }
            byteBuf2.readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, byteBuf2.readableBytes());
            byteBuf.writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(EMPTY_COMPOUNDS));
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Chunk)object);
    }
}

