/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.MathHelper;

public interface IVertexConsumer
extends IVertexBuilder {
    public VertexFormatElement getCurrentElement();

    public void nextVertexFormatIndex();

    public void putByte(int var1, byte var2);

    public void putShort(int var1, short var2);

    public void putFloat(int var1, float var2);

    @Override
    default public IVertexBuilder pos(double d, double d2, double d3) {
        if (this.getCurrentElement().getType() != VertexFormatElement.Type.FLOAT) {
            throw new IllegalStateException();
        }
        this.putFloat(0, (float)d);
        this.putFloat(4, (float)d2);
        this.putFloat(8, (float)d3);
        this.nextVertexFormatIndex();
        return this;
    }

    @Override
    default public IVertexBuilder color(int n, int n2, int n3, int n4) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getUsage() != VertexFormatElement.Usage.COLOR) {
            return this;
        }
        if (vertexFormatElement.getType() != VertexFormatElement.Type.UBYTE) {
            throw new IllegalStateException();
        }
        this.putByte(0, (byte)n);
        this.putByte(1, (byte)n2);
        this.putByte(2, (byte)n3);
        this.putByte(3, (byte)n4);
        this.nextVertexFormatIndex();
        return this;
    }

    @Override
    default public IVertexBuilder tex(float f, float f2) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getUsage() == VertexFormatElement.Usage.UV && vertexFormatElement.getIndex() == 0) {
            if (vertexFormatElement.getType() != VertexFormatElement.Type.FLOAT) {
                throw new IllegalStateException();
            }
            this.putFloat(0, f);
            this.putFloat(4, f2);
            this.nextVertexFormatIndex();
            return this;
        }
        return this;
    }

    @Override
    default public IVertexBuilder overlay(int n, int n2) {
        return this.texShort((short)n, (short)n2, 1);
    }

    @Override
    default public IVertexBuilder lightmap(int n, int n2) {
        return this.texShort((short)n, (short)n2, 2);
    }

    default public IVertexBuilder texShort(short s, short s2, int n) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getUsage() == VertexFormatElement.Usage.UV && vertexFormatElement.getIndex() == n) {
            if (vertexFormatElement.getType() != VertexFormatElement.Type.SHORT) {
                throw new IllegalStateException();
            }
            this.putShort(0, s);
            this.putShort(2, s2);
            this.nextVertexFormatIndex();
            return this;
        }
        return this;
    }

    @Override
    default public IVertexBuilder normal(float f, float f2, float f3) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getUsage() != VertexFormatElement.Usage.NORMAL) {
            return this;
        }
        if (vertexFormatElement.getType() != VertexFormatElement.Type.BYTE) {
            throw new IllegalStateException();
        }
        this.putByte(0, IVertexConsumer.normalInt(f));
        this.putByte(1, IVertexConsumer.normalInt(f2));
        this.putByte(2, IVertexConsumer.normalInt(f3));
        this.nextVertexFormatIndex();
        return this;
    }

    public static byte normalInt(float f) {
        return (byte)((int)(MathHelper.clamp(f, -1.0f, 1.0f) * 127.0f) & 0xFF);
    }
}

