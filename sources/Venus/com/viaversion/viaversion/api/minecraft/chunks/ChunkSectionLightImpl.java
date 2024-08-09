/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ChunkSectionLightImpl
implements ChunkSectionLight {
    private NibbleArray blockLight = new NibbleArray(4096);
    private NibbleArray skyLight;

    @Override
    public void setBlockLight(byte[] byArray) {
        if (byArray.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(byArray);
        } else {
            this.blockLight.setHandle(byArray);
        }
    }

    @Override
    public void setSkyLight(byte[] byArray) {
        if (byArray.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(byArray);
        } else {
            this.skyLight.setHandle(byArray);
        }
    }

    @Override
    public byte @Nullable [] getBlockLight() {
        return this.blockLight == null ? null : this.blockLight.getHandle();
    }

    @Override
    public @Nullable NibbleArray getBlockLightNibbleArray() {
        return this.blockLight;
    }

    @Override
    public byte @Nullable [] getSkyLight() {
        return this.skyLight == null ? null : this.skyLight.getHandle();
    }

    @Override
    public @Nullable NibbleArray getSkyLightNibbleArray() {
        return this.skyLight;
    }

    @Override
    public void readBlockLight(ByteBuf byteBuf) {
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(4096);
        }
        byteBuf.readBytes(this.blockLight.getHandle());
    }

    @Override
    public void readSkyLight(ByteBuf byteBuf) {
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(4096);
        }
        byteBuf.readBytes(this.skyLight.getHandle());
    }

    @Override
    public void writeBlockLight(ByteBuf byteBuf) {
        byteBuf.writeBytes(this.blockLight.getHandle());
    }

    @Override
    public void writeSkyLight(ByteBuf byteBuf) {
        byteBuf.writeBytes(this.skyLight.getHandle());
    }

    @Override
    public boolean hasSkyLight() {
        return this.skyLight != null;
    }

    @Override
    public boolean hasBlockLight() {
        return this.blockLight != null;
    }
}

