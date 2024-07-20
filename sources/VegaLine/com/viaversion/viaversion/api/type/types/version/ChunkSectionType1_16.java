/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;

public class ChunkSectionType1_16
extends Type<ChunkSection> {
    private static final int GLOBAL_PALETTE = 15;

    public ChunkSectionType1_16() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override
    public ChunkSection read(ByteBuf buffer) throws Exception {
        char valuesPerLong;
        int expectedLength;
        long[] blockData;
        ChunkSectionImpl chunkSection;
        int bitsPerBlock = buffer.readUnsignedByte();
        if (bitsPerBlock > 8) {
            bitsPerBlock = 15;
        } else if (bitsPerBlock < 4) {
            bitsPerBlock = 4;
        }
        if (bitsPerBlock != 15) {
            int paletteLength = Type.VAR_INT.readPrimitive(buffer);
            chunkSection = new ChunkSectionImpl(false, paletteLength);
            DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int i = 0; i < paletteLength; ++i) {
                blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
            }
        } else {
            chunkSection = new ChunkSectionImpl(false);
        }
        if ((blockData = (long[])Type.LONG_ARRAY_PRIMITIVE.read(buffer)).length > 0 && blockData.length == (expectedLength = (4096 + (valuesPerLong = (char)(64 / bitsPerBlock)) - 1) / valuesPerLong)) {
            DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
            CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerBlock, 4096, blockData, bitsPerBlock == 15 ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt);
        }
        return chunkSection;
    }

    @Override
    public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
        int bitsPerBlock = 4;
        DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
        while (blockPalette.size() > 1 << bitsPerBlock) {
            ++bitsPerBlock;
        }
        if (bitsPerBlock > 8) {
            bitsPerBlock = 15;
        }
        buffer.writeByte(bitsPerBlock);
        if (bitsPerBlock != 15) {
            Type.VAR_INT.writePrimitive(buffer, blockPalette.size());
            for (int i = 0; i < blockPalette.size(); ++i) {
                Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
            }
        }
        long[] data = CompactArrayUtil.createCompactArrayWithPadding(bitsPerBlock, 4096, bitsPerBlock == 15 ? blockPalette::idAt : blockPalette::paletteIndexAt);
        Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
    }
}

