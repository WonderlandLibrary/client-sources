/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
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
    public void setBlockLight(byte[] data) {
        if (data.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(data);
            return;
        }
        this.blockLight.setHandle(data);
    }

    @Override
    public void setSkyLight(byte[] data) {
        if (data.length != 2048) {
            throw new IllegalArgumentException("Data length != 2048");
        }
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(data);
            return;
        }
        this.skyLight.setHandle(data);
    }

    @Override
    public byte @Nullable [] getBlockLight() {
        if (this.blockLight == null) {
            return null;
        }
        byte[] byArray = this.blockLight.getHandle();
        return byArray;
    }

    @Override
    public @Nullable NibbleArray getBlockLightNibbleArray() {
        return this.blockLight;
    }

    @Override
    public byte @Nullable [] getSkyLight() {
        if (this.skyLight == null) {
            return null;
        }
        byte[] byArray = this.skyLight.getHandle();
        return byArray;
    }

    @Override
    public @Nullable NibbleArray getSkyLightNibbleArray() {
        return this.skyLight;
    }

    @Override
    public void readBlockLight(ByteBuf input) {
        if (this.blockLight == null) {
            this.blockLight = new NibbleArray(4096);
        }
        input.readBytes(this.blockLight.getHandle());
    }

    @Override
    public void readSkyLight(ByteBuf input) {
        if (this.skyLight == null) {
            this.skyLight = new NibbleArray(4096);
        }
        input.readBytes(this.skyLight.getHandle());
    }

    @Override
    public void writeBlockLight(ByteBuf output) {
        output.writeBytes(this.blockLight.getHandle());
    }

    @Override
    public void writeSkyLight(ByteBuf output) {
        output.writeBytes(this.skyLight.getHandle());
    }

    @Override
    public boolean hasSkyLight() {
        if (this.skyLight == null) return false;
        return true;
    }

    @Override
    public boolean hasBlockLight() {
        if (this.blockLight == null) return false;
        return true;
    }
}

