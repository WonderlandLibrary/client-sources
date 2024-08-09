/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.math.BlockPos;

public class EntityAIDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft client;
    private final Map<Integer, List<Entry>> field_217685_b = Maps.newHashMap();

    @Override
    public void clear() {
        this.field_217685_b.clear();
    }

    public void func_217682_a(int n, List<Entry> list) {
        this.field_217685_b.put(n, list);
    }

    public EntityAIDebugRenderer(Minecraft minecraft) {
        this.client = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        ActiveRenderInfo activeRenderInfo = this.client.gameRenderer.getActiveRenderInfo();
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        BlockPos blockPos = new BlockPos(activeRenderInfo.getProjectedView().x, 0.0, activeRenderInfo.getProjectedView().z);
        this.field_217685_b.forEach((arg_0, arg_1) -> EntityAIDebugRenderer.lambda$render$0(blockPos, arg_0, arg_1));
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }

    private static void lambda$render$0(BlockPos blockPos, Integer n, List list) {
        for (int i = 0; i < list.size(); ++i) {
            Entry entry = (Entry)list.get(i);
            if (!blockPos.withinDistance(entry.field_217723_a, 160.0)) continue;
            double d = (double)entry.field_217723_a.getX() + 0.5;
            double d2 = (double)entry.field_217723_a.getY() + 2.0 + (double)i * 0.25;
            double d3 = (double)entry.field_217723_a.getZ() + 0.5;
            int n2 = entry.field_217726_d ? -16711936 : -3355444;
            DebugRenderer.renderText(entry.field_217725_c, d, d2, d3, n2);
        }
    }

    public static class Entry {
        public final BlockPos field_217723_a;
        public final int field_217724_b;
        public final String field_217725_c;
        public final boolean field_217726_d;

        public Entry(BlockPos blockPos, int n, String string, boolean bl) {
            this.field_217723_a = blockPos;
            this.field_217724_b = n;
            this.field_217725_c = string;
            this.field_217726_d = bl;
        }
    }
}

