/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.vector.Matrix4f;
import net.optifine.render.MultiTextureData;
import net.optifine.render.MultiTextureRenderer;
import net.optifine.render.VboRange;
import net.optifine.render.VboRegion;

public class VertexBuffer
implements AutoCloseable {
    private int glBufferId;
    private final VertexFormat vertexFormat;
    private int count;
    private VboRegion vboRegion;
    private VboRange vboRange;
    private int drawModeCustom;
    private MultiTextureData multiTextureData;

    public VertexBuffer(VertexFormat vertexFormat) {
        this.vertexFormat = vertexFormat;
        RenderSystem.glGenBuffers(this::lambda$new$0);
    }

    public void bindBuffer() {
        GlStateManager.bindBuffer(34962, this.glBufferId);
    }

    public void upload(BufferBuilder bufferBuilder) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$upload$1(bufferBuilder));
        } else {
            this.uploadRaw(bufferBuilder);
        }
    }

    public CompletableFuture<Void> uploadLater(BufferBuilder bufferBuilder) {
        if (!RenderSystem.isOnRenderThread()) {
            return CompletableFuture.runAsync(() -> this.lambda$uploadLater$2(bufferBuilder), VertexBuffer::lambda$uploadLater$3);
        }
        this.uploadRaw(bufferBuilder);
        return CompletableFuture.completedFuture(null);
    }

    private void uploadRaw(BufferBuilder bufferBuilder) {
        Pair<BufferBuilder.DrawState, ByteBuffer> pair = bufferBuilder.getNextBuffer();
        this.drawModeCustom = 0;
        BufferBuilder.DrawState drawState = pair.getFirst();
        if (drawState.getDrawMode() != 7) {
            this.drawModeCustom = drawState.getDrawMode();
        }
        if (this.vboRegion != null) {
            ByteBuffer byteBuffer = pair.getSecond();
            this.vboRegion.bufferData(byteBuffer, this.vboRange);
        } else {
            this.multiTextureData = drawState.getMultiTextureData();
            if (this.glBufferId != -1) {
                ByteBuffer byteBuffer = pair.getSecond();
                this.count = byteBuffer.remaining() / this.vertexFormat.getSize();
                this.bindBuffer();
                RenderSystem.glBufferData(34962, byteBuffer, 35044);
                VertexBuffer.unbindBuffer();
            }
        }
    }

    public void draw(Matrix4f matrix4f, int n) {
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matrix4f);
        if (this.drawModeCustom > 0) {
            n = this.drawModeCustom;
        }
        if (this.vboRegion != null) {
            this.vboRegion.drawArrays(n, this.vboRange);
        } else if (this.multiTextureData != null) {
            MultiTextureRenderer.draw(n, this.multiTextureData);
        } else {
            RenderSystem.drawArrays(n, 0, this.count);
        }
        RenderSystem.popMatrix();
    }

    public void draw(int n) {
        if (this.drawModeCustom > 0) {
            n = this.drawModeCustom;
        }
        if (this.vboRegion != null) {
            this.vboRegion.drawArrays(n, this.vboRange);
        } else if (this.multiTextureData != null) {
            MultiTextureRenderer.draw(n, this.multiTextureData);
        } else {
            RenderSystem.drawArrays(n, 0, this.count);
        }
    }

    public static void unbindBuffer() {
        GlStateManager.bindBuffer(34962, 0);
    }

    @Override
    public void close() {
        if (this.glBufferId >= 0) {
            RenderSystem.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }

    public void setVboRegion(VboRegion vboRegion) {
        if (vboRegion != null) {
            this.close();
            this.vboRegion = vboRegion;
            this.vboRange = new VboRange();
        }
    }

    public VboRegion getVboRegion() {
        return this.vboRegion;
    }

    private static void lambda$uploadLater$3(Runnable runnable) {
        RenderSystem.recordRenderCall(runnable::run);
    }

    private void lambda$uploadLater$2(BufferBuilder bufferBuilder) {
        this.uploadRaw(bufferBuilder);
    }

    private void lambda$upload$1(BufferBuilder bufferBuilder) {
        this.uploadRaw(bufferBuilder);
    }

    private void lambda$new$0(Integer n) {
        this.glBufferId = n;
    }
}

