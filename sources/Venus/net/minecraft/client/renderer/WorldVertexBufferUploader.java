/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.optifine.Config;
import net.optifine.render.MultiTextureData;
import net.optifine.render.MultiTextureRenderer;
import net.optifine.shaders.SVertexBuilder;
import org.lwjgl.system.MemoryUtil;

public class WorldVertexBufferUploader {
    public static void draw(BufferBuilder bufferBuilder) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> WorldVertexBufferUploader.lambda$draw$0(bufferBuilder));
        } else {
            Pair<BufferBuilder.DrawState, ByteBuffer> pair = bufferBuilder.getNextBuffer();
            BufferBuilder.DrawState drawState = pair.getFirst();
            WorldVertexBufferUploader.draw(pair.getSecond(), drawState.getDrawMode(), drawState.getFormat(), drawState.getVertexCount(), drawState.getMultiTextureData());
        }
    }

    private static void draw(ByteBuffer byteBuffer, int n, VertexFormat vertexFormat, int n2) {
        WorldVertexBufferUploader.draw(byteBuffer, n, vertexFormat, n2, null);
    }

    private static void draw(ByteBuffer byteBuffer, int n, VertexFormat vertexFormat, int n2, MultiTextureData multiTextureData) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        byteBuffer.clear();
        if (n2 > 0) {
            boolean bl;
            vertexFormat.setupBufferState(MemoryUtil.memAddress(byteBuffer));
            boolean bl2 = bl = Config.isShaders() && SVertexBuilder.preDrawArrays(vertexFormat, byteBuffer);
            if (multiTextureData != null) {
                MultiTextureRenderer.draw(n, multiTextureData);
            } else {
                GlStateManager.drawArrays(n, 0, n2);
            }
            if (bl) {
                SVertexBuilder.postDrawArrays();
            }
            vertexFormat.clearBufferState();
        }
    }

    private static void lambda$draw$0(BufferBuilder bufferBuilder) {
        Pair<BufferBuilder.DrawState, ByteBuffer> pair = bufferBuilder.getNextBuffer();
        BufferBuilder.DrawState drawState = pair.getFirst();
        WorldVertexBufferUploader.draw(pair.getSecond(), drawState.getDrawMode(), drawState.getFormat(), drawState.getVertexCount(), drawState.getMultiTextureData());
    }
}

