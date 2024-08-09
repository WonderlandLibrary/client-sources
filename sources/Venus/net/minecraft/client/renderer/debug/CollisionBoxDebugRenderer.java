/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.shapes.VoxelShape;

public class CollisionBoxDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;
    private double lastUpdate = Double.MIN_VALUE;
    private List<VoxelShape> collisionData = Collections.emptyList();

    public CollisionBoxDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        Object object;
        double d4 = Util.nanoTime();
        if (d4 - this.lastUpdate > 1.0E8) {
            this.lastUpdate = d4;
            object = this.minecraft.gameRenderer.getActiveRenderInfo().getRenderViewEntity();
            this.collisionData = ((Entity)object).world.func_234867_d_((Entity)object, ((Entity)object).getBoundingBox().grow(6.0), CollisionBoxDebugRenderer::lambda$render$0).collect(Collectors.toList());
        }
        object = iRenderTypeBuffer.getBuffer(RenderType.getLines());
        for (VoxelShape voxelShape : this.collisionData) {
            WorldRenderer.drawVoxelShapeParts(matrixStack, (IVertexBuilder)object, voxelShape, -d, -d2, -d3, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private static boolean lambda$render$0(Entity entity2) {
        return false;
    }
}

