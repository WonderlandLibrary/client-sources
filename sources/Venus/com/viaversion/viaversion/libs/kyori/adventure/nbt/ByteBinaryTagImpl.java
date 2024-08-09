/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.AbstractBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="\"0x\" + Integer.toString(this.value, 16)", hasChildren="false")
final class ByteBinaryTagImpl
extends AbstractBinaryTag
implements ByteBinaryTag {
    private final byte value;

    ByteBinaryTagImpl(byte by) {
        this.value = by;
    }

    @Override
    public byte value() {
        return this.value;
    }

    @Override
    public byte byteValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public short shortValue() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ByteBinaryTagImpl byteBinaryTagImpl = (ByteBinaryTagImpl)object;
        return this.value == byteBinaryTagImpl.value;
    }

    public int hashCode() {
        return Byte.hashCode(this.value);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}

