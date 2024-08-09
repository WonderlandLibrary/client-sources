/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;

public class HeightMapDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;

    public HeightMapDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        ClientWorld clientWorld = this.minecraft.world;
        RenderSystem.pushMatrix();
        RenderSystem.disableBlend();
        RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();
        BlockPos blockPos = new BlockPos(d, 0.0, d3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        for (int i = -32; i <= 32; i += 16) {
            for (int j = -32; j <= 32; j += 16) {
                IChunk iChunk = clientWorld.getChunk(blockPos.add(i, 0, j));
                for (Map.Entry<Heightmap.Type, Heightmap> entry : iChunk.getHeightmaps()) {
                    Heightmap.Type type = entry.getKey();
                    ChunkPos chunkPos = iChunk.getPos();
                    Vector3f vector3f = this.func_239373_a_(type);
                    for (int k = 0; k < 16; ++k) {
                        for (int i2 = 0; i2 < 16; ++i2) {
                            int n = chunkPos.x * 16 + k;
                            int n2 = chunkPos.z * 16 + i2;
                            float f = (float)((double)((float)clientWorld.getHeight(type, n, n2) + (float)type.ordinal() * 0.09375f) - d2);
                            WorldRenderer.addChainedFilledBoxVertices(bufferBuilder, (double)((float)n + 0.25f) - d, f, (double)((float)n2 + 0.25f) - d3, (double)((float)n + 0.75f) - d, f + 0.09375f, (double)((float)n2 + 0.75f) - d3, vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0f);
                        }
                    }
                }
            }
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }

    private Vector3f func_239373_a_(Heightmap.Type type) {
        switch (1.$SwitchMap$net$minecraft$world$gen$Heightmap$Type[type.ordinal()]) {
            case 1: {
                return new Vector3f(1.0f, 1.0f, 0.0f);
            }
            case 2: {
                return new Vector3f(1.0f, 0.0f, 1.0f);
            }
            case 3: {
                return new Vector3f(0.0f, 0.7f, 0.0f);
            }
            case 4: {
                return new Vector3f(0.0f, 0.0f, 0.5f);
            }
            case 5: {
                return new Vector3f(0.0f, 0.3f, 0.3f);
            }
            case 6: {
                return new Vector3f(0.0f, 0.5f, 0.5f);
            }
        }
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }
}

