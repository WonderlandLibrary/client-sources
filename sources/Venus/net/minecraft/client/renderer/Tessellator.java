/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.optifine.SmartAnimations;

public class Tessellator {
    private final BufferBuilder buffer;
    private static final Tessellator INSTANCE = new Tessellator();

    public static Tessellator getInstance() {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        return INSTANCE;
    }

    public Tessellator(int n) {
        this.buffer = new BufferBuilder(n);
    }

    public Tessellator() {
        this(0x200000);
    }

    public void draw() {
        if (this.buffer.animatedSprites != null) {
            SmartAnimations.spritesRendered(this.buffer.animatedSprites);
        }
        this.buffer.finishDrawing();
        WorldVertexBufferUploader.draw(this.buffer);
    }

    public BufferBuilder getBuffer() {
        return this.buffer;
    }
}

