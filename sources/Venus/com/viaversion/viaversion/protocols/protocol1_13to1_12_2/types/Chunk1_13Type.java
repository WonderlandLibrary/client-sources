/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class Chunk1_13Type
extends PartialType<Chunk, ClientWorld> {
    public Chunk1_13Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
        int[] nArray;
        int n = byteBuf.readInt();
        int n2 = byteBuf.readInt();
        boolean bl = byteBuf.readBoolean();
        int n3 = Type.VAR_INT.readPrimitive(byteBuf);
        ByteBuf byteBuf2 = byteBuf.readSlice(Type.VAR_INT.readPrimitive(byteBuf));
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        for (int i = 0; i < 16; ++i) {
            ChunkSection chunkSection;
            if ((n3 & 1 << i) == 0) continue;
            chunkSectionArray[i] = chunkSection = (ChunkSection)Types1_13.CHUNK_SECTION.read(byteBuf2);
            chunkSection.getLight().readBlockLight(byteBuf2);
            if (clientWorld.getEnvironment() != Environment.NORMAL) continue;
            chunkSection.getLight().readSkyLight(byteBuf2);
        }
        int[] nArray2 = nArray = bl ? new int[256] : null;
        if (bl) {
            if (byteBuf2.readableBytes() >= 1024) {
                for (int i = 0; i < 256; ++i) {
                    nArray[i] = byteBuf2.readInt();
                }
            } else {
                Via.getPlatform().getLogger().log(Level.WARNING, "Chunk x=" + n + " z=" + n2 + " doesn't have biome data!");
            }
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList((Object[])Type.NBT_ARRAY.read(byteBuf)));
        if (byteBuf.readableBytes() > 0) {
            byte[] byArray = (byte[])Type.REMAINING_BYTES.read(byteBuf);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + byArray.length + " more bytes than expected while reading the chunk: " + n + "/" + n2);
            }
        }
        return new BaseChunk(n, n2, bl, false, n3, chunkSectionArray, nArray, arrayList);
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
                Types1_13.CHUNK_SECTION.write(byteBuf2, chunkSection);
                chunkSection.getLight().writeBlockLight(byteBuf2);
                if (!chunkSection.getLight().hasSkyLight()) continue;
                chunkSection.getLight().writeSkyLight(byteBuf2);
            }
            byteBuf2.readerIndex(0);
            Type.VAR_INT.writePrimitive(byteBuf, byteBuf2.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
            byteBuf.writeBytes(byteBuf2);
        } finally {
            byteBuf2.release();
        }
        if (chunk.isBiomeData()) {
            for (int n : chunk.getBiomeData()) {
                byteBuf.writeInt(n);
            }
        }
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(new CompoundTag[0]));
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

