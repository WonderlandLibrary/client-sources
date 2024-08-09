/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.PaletteType1_18;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ChunkSectionType1_18
extends Type<ChunkSection> {
    private final PaletteType1_18 blockPaletteType;
    private final PaletteType1_18 biomePaletteType;

    public ChunkSectionType1_18(int n, int n2) {
        super("Chunk Section Type", ChunkSection.class);
        this.blockPaletteType = new PaletteType1_18(PaletteType.BLOCKS, n);
        this.biomePaletteType = new PaletteType1_18(PaletteType.BIOMES, n2);
    }

    @Override
    public ChunkSection read(ByteBuf byteBuf) throws Exception {
        ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl();
        chunkSectionImpl.setNonAirBlocksCount(byteBuf.readShort());
        chunkSectionImpl.addPalette(PaletteType.BLOCKS, this.blockPaletteType.read(byteBuf));
        chunkSectionImpl.addPalette(PaletteType.BIOMES, this.biomePaletteType.read(byteBuf));
        return chunkSectionImpl;
    }

    @Override
    public void write(ByteBuf byteBuf, ChunkSection chunkSection) throws Exception {
        byteBuf.writeShort(chunkSection.getNonAirBlocksCount());
        this.blockPaletteType.write(byteBuf, chunkSection.palette(PaletteType.BLOCKS));
        this.biomePaletteType.write(byteBuf, chunkSection.palette(PaletteType.BIOMES));
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

