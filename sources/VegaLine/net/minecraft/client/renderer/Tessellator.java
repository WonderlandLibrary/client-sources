/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldVertexBufferUploader;

public class Tessellator {
    public final BufferBuilder worldRenderer;
    public final WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
    private static final Tessellator INSTANCE = new Tessellator(0x200000);

    public static Tessellator getInstance() {
        return INSTANCE;
    }

    public Tessellator(int bufferSize) {
        this.worldRenderer = new BufferBuilder(bufferSize);
    }

    public void draw() {
        this.worldRenderer.finishDrawing();
        this.vboUploader.draw(this.worldRenderer);
    }

    public void draw(int tryCount) {
        for (int i = 0; i < tryCount; ++i) {
            this.vboUploader.draw(this.worldRenderer, i == tryCount);
        }
        this.worldRenderer.finishDrawing();
    }

    public BufferBuilder getBuffer() {
        return this.worldRenderer;
    }
}

