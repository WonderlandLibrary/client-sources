/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.DimensionType;

public class StructureDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;
    private final Map<DimensionType, Map<String, MutableBoundingBox>> mainBoxes = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, MutableBoundingBox>> subBoxes = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, Boolean>> subBoxFlags = Maps.newIdentityHashMap();

    public StructureDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        ActiveRenderInfo activeRenderInfo = this.minecraft.gameRenderer.getActiveRenderInfo();
        ClientWorld clientWorld = this.minecraft.world;
        DimensionType dimensionType = clientWorld.getDimensionType();
        BlockPos blockPos = new BlockPos(activeRenderInfo.getProjectedView().x, 0.0, activeRenderInfo.getProjectedView().z);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getLines());
        if (this.mainBoxes.containsKey(dimensionType)) {
            for (MutableBoundingBox object : this.mainBoxes.get(dimensionType).values()) {
                if (!blockPos.withinDistance(object.func_215126_f(), 500.0)) continue;
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, (double)object.minX - d, (double)object.minY - d2, (double)object.minZ - d3, (double)(object.maxX + 1) - d, (double)(object.maxY + 1) - d2, (double)(object.maxZ + 1) - d3, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (this.subBoxes.containsKey(dimensionType)) {
            for (Map.Entry entry : this.subBoxes.get(dimensionType).entrySet()) {
                String string = (String)entry.getKey();
                MutableBoundingBox mutableBoundingBox = (MutableBoundingBox)entry.getValue();
                Boolean bl = this.subBoxFlags.get(dimensionType).get(string);
                if (!blockPos.withinDistance(mutableBoundingBox.func_215126_f(), 500.0)) continue;
                if (bl.booleanValue()) {
                    WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, (double)mutableBoundingBox.minX - d, (double)mutableBoundingBox.minY - d2, (double)mutableBoundingBox.minZ - d3, (double)(mutableBoundingBox.maxX + 1) - d, (double)(mutableBoundingBox.maxY + 1) - d2, (double)(mutableBoundingBox.maxZ + 1) - d3, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
                    continue;
                }
                WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, (double)mutableBoundingBox.minX - d, (double)mutableBoundingBox.minY - d2, (double)mutableBoundingBox.minZ - d3, (double)(mutableBoundingBox.maxX + 1) - d, (double)(mutableBoundingBox.maxY + 1) - d2, (double)(mutableBoundingBox.maxZ + 1) - d3, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    public void func_223454_a(MutableBoundingBox mutableBoundingBox, List<MutableBoundingBox> list, List<Boolean> list2, DimensionType dimensionType) {
        if (!this.mainBoxes.containsKey(dimensionType)) {
            this.mainBoxes.put(dimensionType, Maps.newHashMap());
        }
        if (!this.subBoxes.containsKey(dimensionType)) {
            this.subBoxes.put(dimensionType, Maps.newHashMap());
            this.subBoxFlags.put(dimensionType, Maps.newHashMap());
        }
        this.mainBoxes.get(dimensionType).put(mutableBoundingBox.toString(), mutableBoundingBox);
        for (int i = 0; i < list.size(); ++i) {
            MutableBoundingBox mutableBoundingBox2 = list.get(i);
            Boolean bl = list2.get(i);
            this.subBoxes.get(dimensionType).put(mutableBoundingBox2.toString(), mutableBoundingBox2);
            this.subBoxFlags.get(dimensionType).put(mutableBoundingBox2.toString(), bl);
        }
    }

    @Override
    public void clear() {
        this.mainBoxes.clear();
        this.subBoxes.clear();
        this.subBoxFlags.clear();
    }
}

