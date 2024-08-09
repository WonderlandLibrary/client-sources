/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

public class CaveDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Map<BlockPos, BlockPos> subCaves = Maps.newHashMap();
    private final Map<BlockPos, Float> sizes = Maps.newHashMap();
    private final List<BlockPos> caves = Lists.newArrayList();

    public void addCave(BlockPos blockPos, List<BlockPos> list, List<Float> list2) {
        for (int i = 0; i < list.size(); ++i) {
            this.subCaves.put(list.get(i), blockPos);
            this.sizes.put(list.get(i), list2.get(i));
        }
        this.caves.add(blockPos);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        BlockPos blockPos = new BlockPos(d, 0.0, d3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        for (Map.Entry<BlockPos, BlockPos> object : this.subCaves.entrySet()) {
            BlockPos blockPos2 = object.getKey();
            BlockPos blockPos3 = object.getValue();
            float f = (float)(blockPos3.getX() * 128 % 256) / 256.0f;
            float f2 = (float)(blockPos3.getY() * 128 % 256) / 256.0f;
            float f3 = (float)(blockPos3.getZ() * 128 % 256) / 256.0f;
            float f4 = this.sizes.get(blockPos2).floatValue();
            if (!blockPos.withinDistance(blockPos2, 160.0)) continue;
            WorldRenderer.addChainedFilledBoxVertices(bufferBuilder, (double)((float)blockPos2.getX() + 0.5f) - d - (double)f4, (double)((float)blockPos2.getY() + 0.5f) - d2 - (double)f4, (double)((float)blockPos2.getZ() + 0.5f) - d3 - (double)f4, (double)((float)blockPos2.getX() + 0.5f) - d + (double)f4, (double)((float)blockPos2.getY() + 0.5f) - d2 + (double)f4, (double)((float)blockPos2.getZ() + 0.5f) - d3 + (double)f4, f, f2, f3, 0.5f);
        }
        for (BlockPos blockPos4 : this.caves) {
            if (!blockPos.withinDistance(blockPos4, 160.0)) continue;
            WorldRenderer.addChainedFilledBoxVertices(bufferBuilder, (double)blockPos4.getX() - d, (double)blockPos4.getY() - d2, (double)blockPos4.getZ() - d3, (double)((float)blockPos4.getX() + 1.0f) - d, (double)((float)blockPos4.getY() + 1.0f) - d2, (double)((float)blockPos4.getZ() + 1.0f) - d3, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        tessellator.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}

