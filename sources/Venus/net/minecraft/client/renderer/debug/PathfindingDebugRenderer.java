/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PathfindingDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Map<Integer, Path> pathMap = Maps.newHashMap();
    private final Map<Integer, Float> pathMaxDistance = Maps.newHashMap();
    private final Map<Integer, Long> creationMap = Maps.newHashMap();

    public void addPath(int n, Path path, float f) {
        this.pathMap.put(n, path);
        this.creationMap.put(n, Util.milliTime());
        this.pathMaxDistance.put(n, Float.valueOf(f));
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        if (!this.pathMap.isEmpty()) {
            long l = Util.milliTime();
            for (Integer n : this.pathMap.keySet()) {
                Path path = this.pathMap.get(n);
                float f = this.pathMaxDistance.get(n).floatValue();
                PathfindingDebugRenderer.func_229032_a_(path, f, true, true, d, d2, d3);
            }
            for (Integer n : this.creationMap.keySet().toArray(new Integer[0])) {
                if (l - this.creationMap.get(n) <= 5000L) continue;
                this.pathMap.remove(n);
                this.creationMap.remove(n);
            }
        }
    }

    public static void func_229032_a_(Path path, float f, boolean bl, boolean bl2, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(0.0f, 1.0f, 0.0f, 0.75f);
        RenderSystem.disableTexture();
        RenderSystem.lineWidth(6.0f);
        PathfindingDebugRenderer.func_229034_b_(path, f, bl, bl2, d, d2, d3);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    private static void func_229034_b_(Path path, float f, boolean bl, boolean bl2, double d, double d2, double d3) {
        int n;
        PathfindingDebugRenderer.func_229031_a_(path, d, d2, d3);
        BlockPos blockPos = path.getTarget();
        if (PathfindingDebugRenderer.func_229033_a_(blockPos, d, d2, d3) <= 80.0f) {
            DebugRenderer.renderBox(new AxisAlignedBB((float)blockPos.getX() + 0.25f, (float)blockPos.getY() + 0.25f, (double)blockPos.getZ() + 0.25, (float)blockPos.getX() + 0.75f, (float)blockPos.getY() + 0.75f, (float)blockPos.getZ() + 0.75f).offset(-d, -d2, -d3), 0.0f, 1.0f, 0.0f, 0.5f);
            for (n = 0; n < path.getCurrentPathLength(); ++n) {
                PathPoint pathPoint = path.getPathPointFromIndex(n);
                if (!(PathfindingDebugRenderer.func_229033_a_(pathPoint.func_224759_a(), d, d2, d3) <= 80.0f)) continue;
                float f2 = n == path.getCurrentPathIndex() ? 1.0f : 0.0f;
                float f3 = n == path.getCurrentPathIndex() ? 0.0f : 1.0f;
                DebugRenderer.renderBox(new AxisAlignedBB((float)pathPoint.x + 0.5f - f, (float)pathPoint.y + 0.01f * (float)n, (float)pathPoint.z + 0.5f - f, (float)pathPoint.x + 0.5f + f, (float)pathPoint.y + 0.25f + 0.01f * (float)n, (float)pathPoint.z + 0.5f + f).offset(-d, -d2, -d3), f2, 0.0f, f3, 0.5f);
            }
        }
        if (bl) {
            for (PathPoint pathPoint : path.getClosedSet()) {
                if (!(PathfindingDebugRenderer.func_229033_a_(pathPoint.func_224759_a(), d, d2, d3) <= 80.0f)) continue;
                DebugRenderer.renderBox(new AxisAlignedBB((float)pathPoint.x + 0.5f - f / 2.0f, (float)pathPoint.y + 0.01f, (float)pathPoint.z + 0.5f - f / 2.0f, (float)pathPoint.x + 0.5f + f / 2.0f, (double)pathPoint.y + 0.1, (float)pathPoint.z + 0.5f + f / 2.0f).offset(-d, -d2, -d3), 1.0f, 0.8f, 0.8f, 0.5f);
            }
            for (PathPoint pathPoint : path.getOpenSet()) {
                if (!(PathfindingDebugRenderer.func_229033_a_(pathPoint.func_224759_a(), d, d2, d3) <= 80.0f)) continue;
                DebugRenderer.renderBox(new AxisAlignedBB((float)pathPoint.x + 0.5f - f / 2.0f, (float)pathPoint.y + 0.01f, (float)pathPoint.z + 0.5f - f / 2.0f, (float)pathPoint.x + 0.5f + f / 2.0f, (double)pathPoint.y + 0.1, (float)pathPoint.z + 0.5f + f / 2.0f).offset(-d, -d2, -d3), 0.8f, 1.0f, 1.0f, 0.5f);
            }
        }
        if (bl2) {
            for (n = 0; n < path.getCurrentPathLength(); ++n) {
                PathPoint pathPoint = path.getPathPointFromIndex(n);
                if (!(PathfindingDebugRenderer.func_229033_a_(pathPoint.func_224759_a(), d, d2, d3) <= 80.0f)) continue;
                DebugRenderer.renderText(String.format("%s", new Object[]{pathPoint.nodeType}), (double)pathPoint.x + 0.5, (double)pathPoint.y + 0.75, (double)pathPoint.z + 0.5, -1, 0.02f, true, 0.0f, true);
                DebugRenderer.renderText(String.format(Locale.ROOT, "%.2f", Float.valueOf(pathPoint.costMalus)), (double)pathPoint.x + 0.5, (double)pathPoint.y + 0.25, (double)pathPoint.z + 0.5, -1, 0.02f, true, 0.0f, true);
            }
        }
    }

    public static void func_229031_a_(Path path, double d, double d2, double d3) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < path.getCurrentPathLength(); ++i) {
            PathPoint pathPoint = path.getPathPointFromIndex(i);
            if (PathfindingDebugRenderer.func_229033_a_(pathPoint.func_224759_a(), d, d2, d3) > 80.0f) continue;
            float f = (float)i / (float)path.getCurrentPathLength() * 0.33f;
            int n = i == 0 ? 0 : MathHelper.hsvToRGB(f, 0.9f, 0.9f);
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            bufferBuilder.pos((double)pathPoint.x - d + 0.5, (double)pathPoint.y - d2 + 0.5, (double)pathPoint.z - d3 + 0.5).color(n2, n3, n4, 255).endVertex();
        }
        tessellator.draw();
    }

    private static float func_229033_a_(BlockPos blockPos, double d, double d2, double d3) {
        return (float)(Math.abs((double)blockPos.getX() - d) + Math.abs((double)blockPos.getY() - d2) + Math.abs((double)blockPos.getZ() - d3));
    }
}

