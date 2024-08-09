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
public class ChunkSectionType1_13
extends Type<ChunkSection> {
    private static final int GLOBAL_PALETTE = 14;

    public ChunkSectionType1_13() {
        super("Chunk Section Type", ChunkSection.class);
    }

    @Override
    public ChunkSection read(ByteBuf byteBuf) throws Exception {
        int n;
        long[] lArray;
        ChunkSectionImpl chunkSectionImpl;
        int n2 = byteBuf.readUnsignedByte();
        if (n2 > 8) {
            n2 = 14;
        } else if (n2 < 4) {
            n2 = 4;
        }
        if (n2 != 14) {
            int n3 = Type.VAR_INT.readPrimitive(byteBuf);
            chunkSectionImpl = new ChunkSectionImpl(true, n3);
            DataPalette dataPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
            for (int i = 0; i < n3; ++i) {
                dataPalette.addId(Type.VAR_INT.readPrimitive(byteBuf));
            }
        } else {
            chunkSectionImpl = new ChunkSectionImpl(true);
        }
        if ((lArray = (long[])Type.LONG_ARRAY_PRIMITIVE.read(byteBuf)).length > 0 && lArray.length == (n = (int)Math.ceil((double)(4096 * n2) / 64.0))) {
            DataPalette dataPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
            CompactArrayUtil.iterateCompactArray(n2, 4096, lArray, n2 == 14 ? dataPalette::setIdAt : dataPalette::setPaletteIndexAt);
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
            n = 14;
        }
        byteBuf.writeByte(n);
        if (n != 14) {
            Type.VAR_INT.writePrimitive(byteBuf, dataPalette.size());
            for (int i = 0; i < dataPalette.size(); ++i) {
                Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(i));
            }
        }
        long[] lArray = CompactArrayUtil.createCompactArray(n, 4096, n == 14 ? dataPalette::idAt : dataPalette::paletteIndexAt);
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

