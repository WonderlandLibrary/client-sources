/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.version;

import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.Type;

public class ChunkSectionType1_8
extends Type<ChunkSection> {
    public ChunkSectionType1_8() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override
    public ChunkSection read(ByteBuf buffer) throws Exception {
        ChunkSection chunkSection = new ChunkSection();
        chunkSection.addPaletteEntry(0);
        ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 4096; ++i) {
            short mask = littleEndianView.readShort();
            int type = mask >> 4;
            int data = mask & 0xF;
            chunkSection.setBlock(i, type, data);
        }
        return chunkSection;
    }

    @Override
    public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
        throw new UnsupportedOperationException();
    }
}

