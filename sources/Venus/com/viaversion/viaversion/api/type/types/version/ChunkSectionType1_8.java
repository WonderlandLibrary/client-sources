/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkSectionType1_8
extends Type<ChunkSection> {
    public ChunkSectionType1_8() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override
    public ChunkSection read(ByteBuf byteBuf) throws Exception {
        ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(true);
        DataPalette dataPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
        dataPalette.addId(0);
        ByteBuf byteBuf2 = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 4096; ++i) {
            dataPalette.setIdAt(i, byteBuf2.readShort());
        }
        return chunkSectionImpl;
    }

    @Override
    public void write(ByteBuf byteBuf, ChunkSection chunkSection) throws Exception {
        DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
        ByteBuf byteBuf2 = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 4096; ++i) {
            byteBuf2.writeShort(dataPalette.idAt(i));
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (ChunkSection)object);
    }
}

