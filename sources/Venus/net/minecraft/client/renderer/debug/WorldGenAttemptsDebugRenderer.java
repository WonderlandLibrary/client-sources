/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

public class WorldGenAttemptsDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final List<BlockPos> locations = Lists.newArrayList();
    private final List<Float> sizes = Lists.newArrayList();
    private final List<Float> alphas = Lists.newArrayList();
    private final List<Float> reds = Lists.newArrayList();
    private final List<Float> greens = Lists.newArrayList();
    private final List<Float> blues = Lists.newArrayList();

    public void addAttempt(BlockPos blockPos, float f, float f2, float f3, float f4, float f5) {
        this.locations.add(blockPos);
        this.sizes.add(Float.valueOf(f));
        this.alphas.add(Float.valueOf(f5));
        this.reds.add(Float.valueOf(f2));
        this.greens.add(Float.valueOf(f3));
        this.blues.add(Float.valueOf(f4));
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < this.locations.size(); ++i) {
            BlockPos blockPos = this.locations.get(i);
            Float f = this.sizes.get(i);
            float f2 = f.floatValue() / 2.0f;
            WorldRenderer.addChainedFilledBoxVertices(bufferBuilder, (double)((float)blockPos.getX() + 0.5f - f2) - d, (double)((float)blockPos.getY() + 0.5f - f2) - d2, (double)((float)blockPos.getZ() + 0.5f - f2) - d3, (double)((float)blockPos.getX() + 0.5f + f2) - d, (double)((float)blockPos.getY() + 0.5f + f2) - d2, (double)((float)blockPos.getZ() + 0.5f + f2) - d3, this.reds.get(i).floatValue(), this.greens.get(i).floatValue(), this.blues.get(i).floatValue(), this.alphas.get(i).floatValue());
        }
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}

