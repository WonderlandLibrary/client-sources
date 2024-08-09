/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.BitSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BitSetType
extends Type<BitSet> {
    private final int length;
    private final int bytesLength;

    public BitSetType(int n) {
        super(BitSet.class);
        this.length = n;
        this.bytesLength = -Math.floorDiv(-n, 8);
    }

    @Override
    public BitSet read(ByteBuf byteBuf) {
        byte[] byArray = new byte[this.bytesLength];
        byteBuf.readBytes(byArray);
        return BitSet.valueOf(byArray);
    }

    @Override
    public void write(ByteBuf byteBuf, BitSet bitSet) {
        Preconditions.checkArgument(bitSet.length() <= this.length, "BitSet of length " + bitSet.length() + " larger than max length " + this.length);
        byte[] byArray = bitSet.toByteArray();
        byteBuf.writeBytes(Arrays.copyOf(byArray, this.bytesLength));
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (BitSet)object);
    }
}

