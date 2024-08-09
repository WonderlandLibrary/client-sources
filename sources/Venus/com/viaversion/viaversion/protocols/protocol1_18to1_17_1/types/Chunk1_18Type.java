/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.ChunkSectionType1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Chunk1_18Type
extends Type<Chunk> {
    private final ChunkSectionType1_18 sectionType;
    private final int ySectionCount;

    public Chunk1_18Type(int n, int n2, int n3) {
        super(Chunk.class);
        Preconditions.checkArgument(n > 0);
        this.sectionType = new ChunkSectionType1_18(n2, n3);
        this.ySectionCount = n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Chunk read(ByteBuf byteBuf) throws Exception {
        int n;
        int n2 = byteBuf.readInt();
        int n3 = byteBuf.readInt();
        CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        ByteBuf byteBuf2 = byteBuf.readBytes(Type.VAR_INT.readPrimitive(byteBuf));
        ChunkSection[] chunkSectionArray = new ChunkSection[this.ySectionCount];
        try {
            for (n = 0; n < this.ySectionCount; ++n) {
                chunkSectionArray[n] = this.sectionType.read(byteBuf2);
            }
        } finally {
            byteBuf2.release();
        }
        n = Type.VAR_INT.readPrimitive(byteBuf);
        ArrayList<BlockEntity> arrayList = new ArrayList<BlockEntity>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add((BlockEntity)Types1_18.BLOCK_ENTITY.read(byteBuf));
        }
        return new Chunk1_18(n2, n3, chunkSectionArray, compoundTag, arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        ByteBuf byteBuf2 = byteBuf.alloc().buffer();
        try {
            for (ChunkSection chunkSection : chunk.getSections()) {
                this.sectionType.write(byteBuf2, chunkSection);
            }
            byteBuf2.readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, byteBuf2.readableBytes());
            byteBuf.writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
        Type.VAR_INT.writePrimitive(byteBuf, chunk.blockEntities().size());
        for (BlockEntity blockEntity : chunk.blockEntities()) {
            Types1_18.BLOCK_ENTITY.write(byteBuf, blockEntity);
        }
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

