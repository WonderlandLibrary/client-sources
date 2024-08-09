/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

public class NeighborsUpdateDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;
    private final Map<Long, Map<BlockPos, Integer>> lastUpdate = Maps.newTreeMap(Ordering.natural().reverse());

    NeighborsUpdateDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void addUpdate(long l, BlockPos blockPos) {
        Map map = this.lastUpdate.computeIfAbsent(l, NeighborsUpdateDebugRenderer::lambda$addUpdate$0);
        int n = map.getOrDefault(blockPos, 0);
        map.put(blockPos, n + 1);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        Object object;
        long l = this.minecraft.world.getGameTime();
        int n = 200;
        double d4 = 0.0025;
        HashSet<BlockPos> hashSet = Sets.newHashSet();
        HashMap<BlockPos, Integer> hashMap = Maps.newHashMap();
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getLines());
        Iterator<Map.Entry<Long, Map<BlockPos, Integer>>> iterator2 = this.lastUpdate.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Long, Map<BlockPos, Integer>> entry = iterator2.next();
            Long object2 = (Long)entry.getKey();
            object = (Map)entry.getValue();
            long l2 = l - object2;
            if (l2 > 200L) {
                iterator2.remove();
                continue;
            }
            for (Map.Entry entry2 : object.entrySet()) {
                BlockPos blockPos = (BlockPos)entry2.getKey();
                Integer n2 = (Integer)entry2.getValue();
                if (!hashSet.add(blockPos)) continue;
                AxisAlignedBB axisAlignedBB = new AxisAlignedBB(BlockPos.ZERO).grow(0.002).shrink(0.0025 * (double)l2).offset(blockPos.getX(), blockPos.getY(), blockPos.getZ()).offset(-d, -d2, -d3);
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, 1.0f, 1.0f, 1.0f, 1.0f);
                hashMap.put(blockPos, n2);
            }
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            object = (BlockPos)entry.getKey();
            Integer n3 = (Integer)entry.getValue();
            DebugRenderer.renderText(String.valueOf(n3), ((Vector3i)object).getX(), ((Vector3i)object).getY(), ((Vector3i)object).getZ(), -1);
        }
    }

    private static Map lambda$addUpdate$0(Long l) {
        return Maps.newHashMap();
    }
}

