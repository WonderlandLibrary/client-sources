/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;

public class Chunk1_8Type
extends PartialType<Chunk, ClientWorld> {
    public Chunk1_8Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }

    @Override
    public Chunk read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
        int n = byteBuf.readInt();
        int n2 = byteBuf.readInt();
        boolean bl = byteBuf.readBoolean();
        int n3 = byteBuf.readUnsignedShort();
        int n4 = Type.VAR_INT.readPrimitive(byteBuf);
        byte[] byArray = new byte[n4];
        byteBuf.readBytes(byArray);
        if (bl && n3 == 0) {
            return new BaseChunk(n, n2, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>());
        }
        return Chunk1_8Type.deserialize(n, n2, bl, clientWorld.getEnvironment() == Environment.NORMAL, n3, byArray);
    }

    @Override
    public void write(ByteBuf byteBuf, ClientWorld clientWorld, Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        byteBuf.writeShort(chunk.getBitmask());
        byte[] byArray = Chunk1_8Type.serialize(chunk);
        Type.VAR_INT.writePrimitive(byteBuf, byArray.length);
        byteBuf.writeBytes(byArray);
    }

    public static Chunk deserialize(int n, int n2, boolean bl, boolean bl2, int n3, byte[] byArray) throws Exception {
        int n4;
        ByteBuf byteBuf = Unpooled.wrappedBuffer(byArray);
        ChunkSection[] chunkSectionArray = new ChunkSection[16];
        int[] nArray = null;
        for (n4 = 0; n4 < chunkSectionArray.length; ++n4) {
            if ((n3 & 1 << n4) == 0) continue;
            chunkSectionArray[n4] = (ChunkSection)Types1_8.CHUNK_SECTION.read(byteBuf);
        }
        for (n4 = 0; n4 < chunkSectionArray.length; ++n4) {
            if ((n3 & 1 << n4) == 0) continue;
            chunkSectionArray[n4].getLight().readBlockLight(byteBuf);
        }
        if (bl2) {
            for (n4 = 0; n4 < chunkSectionArray.length; ++n4) {
                if ((n3 & 1 << n4) == 0) continue;
                chunkSectionArray[n4].getLight().readSkyLight(byteBuf);
            }
        }
        if (bl) {
            nArray = new int[256];
            for (n4 = 0; n4 < 256; ++n4) {
                nArray[n4] = byteBuf.readUnsignedByte();
            }
        }
        byteBuf.release();
        return new BaseChunk(n, n2, bl, false, n3, chunkSectionArray, nArray, new ArrayList<CompoundTag>());
    }

    public static byte[] serialize(Chunk chunk) throws Exception {
        int n;
        ByteBuf byteBuf = Unpooled.buffer();
        for (n = 0; n < chunk.getSections().length; ++n) {
            if ((chunk.getBitmask() & 1 << n) == 0) continue;
            Types1_8.CHUNK_SECTION.write(byteBuf, chunk.getSections()[n]);
        }
        for (n = 0; n < chunk.getSections().length; ++n) {
            if ((chunk.getBitmask() & 1 << n) == 0) continue;
            chunk.getSections()[n].getLight().writeBlockLight(byteBuf);
        }
        for (n = 0; n < chunk.getSections().length; ++n) {
            if ((chunk.getBitmask() & 1 << n) == 0 || !chunk.getSections()[n].getLight().hasSkyLight()) continue;
            chunk.getSections()[n].getLight().writeSkyLight(byteBuf);
        }
        if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
            for (int n2 : chunk.getBiomeData()) {
                byteBuf.writeByte((byte)n2);
            }
        }
        byte[] byArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byArray);
        byteBuf.release();
        return byArray;
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

