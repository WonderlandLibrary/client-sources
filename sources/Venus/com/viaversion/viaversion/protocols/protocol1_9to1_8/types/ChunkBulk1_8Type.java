/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkBulkType;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import io.netty.buffer.ByteBuf;

public class ChunkBulk1_8Type
extends PartialType<Chunk[], ClientWorld> {
    private static final int BLOCKS_PER_SECTION = 4096;
    private static final int BLOCKS_BYTES = 8192;
    private static final int LIGHT_BYTES = 2048;
    private static final int BIOME_BYTES = 256;

    public ChunkBulk1_8Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk[].class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkBulkType.class;
    }

    @Override
    public Chunk[] read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
        int n;
        boolean bl = byteBuf.readBoolean();
        int n2 = Type.VAR_INT.readPrimitive(byteBuf);
        Chunk[] chunkArray = new Chunk[n2];
        ChunkBulkSection[] chunkBulkSectionArray = new ChunkBulkSection[n2];
        for (n = 0; n < chunkBulkSectionArray.length; ++n) {
            chunkBulkSectionArray[n] = new ChunkBulkSection(byteBuf, bl);
        }
        for (n = 0; n < chunkArray.length; ++n) {
            ChunkBulkSection chunkBulkSection = chunkBulkSectionArray[n];
            chunkBulkSection.readData(byteBuf);
            chunkArray[n] = Chunk1_8Type.deserialize(ChunkBulkSection.access$000(chunkBulkSection), ChunkBulkSection.access$100(chunkBulkSection), true, bl, ChunkBulkSection.access$200(chunkBulkSection), chunkBulkSection.getData());
        }
        return chunkArray;
    }

    @Override
    public void write(ByteBuf byteBuf, ClientWorld clientWorld, Chunk[] chunkArray) throws Exception {
        boolean bl = false;
        block0: for (Chunk chunk : chunkArray) {
            for (ChunkSection chunkSection : chunk.getSections()) {
                if (chunkSection == null || !chunkSection.getLight().hasSkyLight()) continue;
                bl = true;
                break block0;
            }
        }
        byteBuf.writeBoolean(bl);
        Type.VAR_INT.writePrimitive(byteBuf, chunkArray.length);
        for (Chunk chunk : chunkArray) {
            byteBuf.writeInt(chunk.getX());
            byteBuf.writeInt(chunk.getZ());
            byteBuf.writeShort(chunk.getBitmask());
        }
        for (Chunk chunk : chunkArray) {
            byteBuf.writeBytes(Chunk1_8Type.serialize(chunk));
        }
    }

    @Override
    public void write(ByteBuf byteBuf, Object object, Object object2) throws Exception {
        this.write(byteBuf, (ClientWorld)object, (Chunk[])object2);
    }

    @Override
    public Object read(ByteBuf byteBuf, Object object) throws Exception {
        return this.read(byteBuf, (ClientWorld)object);
    }

    public static final class ChunkBulkSection {
        private final int chunkX;
        private final int chunkZ;
        private final int bitmask;
        private final byte[] data;

        public ChunkBulkSection(ByteBuf byteBuf, boolean bl) {
            this.chunkX = byteBuf.readInt();
            this.chunkZ = byteBuf.readInt();
            this.bitmask = byteBuf.readUnsignedShort();
            int n = Integer.bitCount(this.bitmask);
            this.data = new byte[n * (8192 + (bl ? 4096 : 2048)) + 256];
        }

        public void readData(ByteBuf byteBuf) {
            byteBuf.readBytes(this.data);
        }

        public int getChunkX() {
            return this.chunkX;
        }

        public int getChunkZ() {
            return this.chunkZ;
        }

        public int getBitmask() {
            return this.bitmask;
        }

        public byte[] getData() {
            return this.data;
        }

        static int access$000(ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.chunkX;
        }

        static int access$100(ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.chunkZ;
        }

        static int access$200(ChunkBulkSection chunkBulkSection) {
            return chunkBulkSection.bitmask;
        }
    }
}

