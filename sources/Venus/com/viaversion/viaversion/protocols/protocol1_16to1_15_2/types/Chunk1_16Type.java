/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types;

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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Chunk1_16Type
extends Type<Chunk> {
    private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];

    public Chunk1_16Type() {
        super(Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf byteBuf) throws Exception {
        int[] nArray;
        int n = byteBuf.readInt();
        int n2 = byteBuf.readInt();
        boolean bl = byteBuf.readBoolean();
        boolean bl2 = byteBuf.readBoolean();
        int n3 = Type.VAR_INT.readPrimitive(byteBuf);
        CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        int[] nArray2 = nArray = bl ? new int[1024] : null;
        if (bl) {
            for (int i = 0; i < 1024; ++i) {
                nArray[i] = byteBuf.readInt();
            }
        }
        Type.VAR_INT.readPrimitive(byteBuf);
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        for (int i = 0; i < 16; ++i) {
            if ((n3 & 1 << i) == 0) continue;
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
        return new BaseChunk(n, n2, bl, bl2, n3, chunkSectionArray, nArray, compoundTag, arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        byteBuf.writeBoolean(chunk.isIgnoreOldLightData());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        if (chunk.isBiomeData()) {
            for (int n : chunk.getBiomeData()) {
                byteBuf.writeInt(n);
            }
        }
        Object object = byteBuf.alloc().buffer();
        try {
            for (int i = 0; i < 16; ++i) {
                ChunkSection chunkSection = chunk.getSections()[i];
                if (chunkSection == null) continue;
                ((ByteBuf)object).writeShort(chunkSection.getNonAirBlocksCount());
                Types1_16.CHUNK_SECTION.write((ByteBuf)object, chunkSection);
            }
            ((ByteBuf)object).readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, ((ByteBuf)object).readableBytes());
            byteBuf.writeBytes((ByteBuf)object);
        } finally {
            object.release();
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

