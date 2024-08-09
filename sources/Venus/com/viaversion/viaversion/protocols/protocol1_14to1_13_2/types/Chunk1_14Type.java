/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Chunk1_14Type
extends Type<Chunk> {
    public Chunk1_14Type() {
        super(Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf byteBuf) throws Exception {
        int[] nArray;
        Object object;
        int n;
        int n2 = byteBuf.readInt();
        int n3 = byteBuf.readInt();
        boolean bl = byteBuf.readBoolean();
        int n4 = Type.VAR_INT.readPrimitive(byteBuf);
        CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        for (int i = 0; i < 16; ++i) {
            if ((n4 & 1 << i) == 0) continue;
            n = byteBuf.readShort();
            object = (ChunkSection)Types1_13.CHUNK_SECTION.read(byteBuf);
            object.setNonAirBlocksCount(n);
            chunkSectionArray[i] = object;
        }
        int[] nArray2 = nArray = bl ? new int[256] : null;
        if (bl) {
            for (n = 0; n < 256; ++n) {
                nArray[n] = byteBuf.readInt();
            }
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList((Object[])Type.NBT_ARRAY.read(byteBuf)));
        if (byteBuf.readableBytes() > 0) {
            object = (byte[])Type.REMAINING_BYTES.read(byteBuf);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + ((Object)object).length + " more bytes than expected while reading the chunk: " + n2 + "/" + n3);
            }
        }
        return new BaseChunk(n2, n3, bl, false, n4, chunkSectionArray, nArray, compoundTag, arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        ByteBuf byteBuf2 = byteBuf.alloc().buffer();
        try {
            for (int i = 0; i < 16; ++i) {
                ChunkSection chunkSection = chunk.getSections()[i];
                if (chunkSection == null) continue;
                byteBuf2.writeShort(chunkSection.getNonAirBlocksCount());
                Types1_13.CHUNK_SECTION.write(byteBuf2, chunkSection);
            }
            byteBuf2.readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, byteBuf2.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
            byteBuf.writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
        if (chunk.isBiomeData()) {
            for (int n : chunk.getBiomeData()) {
                byteBuf.writeInt(n & 0xFF);
            }
        }
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(new CompoundTag[0]));
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

