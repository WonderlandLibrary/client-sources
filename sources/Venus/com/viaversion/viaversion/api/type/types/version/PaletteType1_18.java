/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class PaletteType1_18
extends Type<DataPalette> {
    private final int globalPaletteBits;
    private final PaletteType type;

    public PaletteType1_18(PaletteType paletteType, int n) {
        super(DataPalette.class);
        this.globalPaletteBits = n;
        this.type = paletteType;
    }

    @Override
    public DataPalette read(ByteBuf byteBuf) throws Exception {
        long[] lArray;
        int n;
        DataPaletteImpl dataPaletteImpl;
        int n2 = byteBuf.readByte();
        int n3 = n2;
        if (n3 == 0) {
            DataPaletteImpl dataPaletteImpl2 = new DataPaletteImpl(this.type.size(), 1);
            dataPaletteImpl2.addId(Type.VAR_INT.readPrimitive(byteBuf));
            Type.LONG_ARRAY_PRIMITIVE.read(byteBuf);
            return dataPaletteImpl2;
        }
        if (n3 < 0 || n3 > this.type.highestBitsPerValue()) {
            n3 = this.globalPaletteBits;
        } else if (this.type == PaletteType.BLOCKS && n3 < 4) {
            n3 = 4;
        }
        if (n3 != this.globalPaletteBits) {
            int n4 = Type.VAR_INT.readPrimitive(byteBuf);
            dataPaletteImpl = new DataPaletteImpl(this.type.size(), n4);
            for (n = 0; n < n4; ++n) {
                dataPaletteImpl.addId(Type.VAR_INT.readPrimitive(byteBuf));
            }
        } else {
            dataPaletteImpl = new DataPaletteImpl(this.type.size());
        }
        if ((lArray = (long[])Type.LONG_ARRAY_PRIMITIVE.read(byteBuf)).length > 0) {
            n = (char)(64 / n3);
            int n5 = (this.type.size() + n - 1) / n;
            if (lArray.length == n5) {
                CompactArrayUtil.iterateCompactArrayWithPadding(n3, this.type.size(), lArray, n3 == this.globalPaletteBits ? dataPaletteImpl::setIdAt : dataPaletteImpl::setPaletteIndexAt);
            }
        }
        return dataPaletteImpl;
    }

    @Override
    public void write(ByteBuf byteBuf, DataPalette dataPalette) throws Exception {
        int n = dataPalette.size();
        if (n == 1) {
            byteBuf.writeByte(0);
            Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(0));
            Type.VAR_INT.writePrimitive(byteBuf, 0);
            return;
        }
        int n2 = this.type == PaletteType.BLOCKS ? 4 : 1;
        int n3 = Math.max(n2, MathUtil.ceilLog2(n));
        if (n3 > this.type.highestBitsPerValue()) {
            n3 = this.globalPaletteBits;
        }
        byteBuf.writeByte(n3);
        if (n3 != this.globalPaletteBits) {
            Type.VAR_INT.writePrimitive(byteBuf, n);
            for (int i = 0; i < n; ++i) {
                Type.VAR_INT.writePrimitive(byteBuf, dataPalette.idByIndex(i));
            }
        }
        Type.LONG_ARRAY_PRIMITIVE.write(byteBuf, CompactArrayUtil.createCompactArrayWithPadding(n3, this.type.size(), n3 == this.globalPaletteBits ? dataPalette::idAt : dataPalette::paletteIndexAt));
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (DataPalette)object);
    }
}

