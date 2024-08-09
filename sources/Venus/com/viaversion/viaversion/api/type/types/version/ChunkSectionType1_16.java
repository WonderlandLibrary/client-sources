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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkSectionType1_16
extends Type<ChunkSection> {
    private static final int GLOBAL_PALETTE = 15;

    public ChunkSectionType1_16() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override
    public ChunkSection read(ByteBuf byteBuf) throws Exception {
        char c;
        long[] lArray;
        int n;
        ChunkSectionImpl chunkSectionImpl;
        int n2 = byteBuf.readUnsignedByte();
        if (n2 > 8) {
            n2 = 15;
        } else if (n2 < 4) {
            n2 = 4;
        }
        if (n2 != 15) {
            int n3 = Type.VAR_INT.readPrimitive(byteBuf);
            chunkSectionImpl = new ChunkSectionImpl(false, n3);
            DataPalette dataPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
            for (n = 0; n < n3; ++n) {
                dataPalette.addId(Type.VAR_INT.readPrimitive(byteBuf));
            }
        } else {
            chunkSectionImpl = new ChunkSectionImpl(false);
        }
        if ((lArray = (long[])Type.LONG_ARRAY_PRIMITIVE.read(byteBuf)).length > 0 && lArray.length == (n = (4096 + (c = (char)(64 / n2)) - 1) / c)) {
            DataPalette dataPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
            CompactArrayUtil.iterateCompactArrayWithPadding(n2, 4096, lArray, n2 == 15 ? dataPalette::setIdAt : dataPalette::setPaletteIndexAt);
        }
        return chunkSectionImpl;
    }

    @Override
    public void write(ByteBuf byteBuf, ChunkSection chunkSection) throws Exception {
        int n = 4;
        DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
        while (dataPalette.size() > 1 << n) {
            ++n;
        }
        if (n > 8) {
            n = 15;
        }
        byteBuf.writeByte(n);
        if (n != 15) {
            Type.VAR_INT.writePrimitive(byteBuf, dataPalette.size());
            for (int i = 0; i < dataPalette.size(); ++i) {
                Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(i));
            }
        }
        long[] lArray = CompactArrayUtil.createCompactArrayWithPadding(n, 4096, n == 15 ? dataPalette::idAt : dataPalette::paletteIndexAt);
        Type.LONG_ARRAY_PRIMITIVE.write(byteBuf, lArray);
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

