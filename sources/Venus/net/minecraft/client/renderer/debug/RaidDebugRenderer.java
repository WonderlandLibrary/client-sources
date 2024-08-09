/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.math.BlockPos;

public class RaidDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft client;
    private Collection<BlockPos> field_222909_b = Lists.newArrayList();

    public RaidDebugRenderer(Minecraft minecraft) {
        this.client = minecraft;
    }

    public void func_222906_a(Collection<BlockPos> collection) {
        this.field_222909_b = collection;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        BlockPos blockPos = this.func_222904_c().getBlockPos();
        for (BlockPos blockPos2 : this.field_222909_b) {
            if (!blockPos.withinDistance(blockPos2, 160.0)) continue;
            RaidDebugRenderer.func_222903_a(blockPos2);
        }
    }

    private static void func_222903_a(BlockPos blockPos) {
        DebugRenderer.renderBox(blockPos.add(-0.5, -0.5, -0.5), blockPos.add(1.5, 1.5, 1.5), 1.0f, 0.0f, 0.0f, 0.15f);
        int n = -65536;
        RaidDebugRenderer.func_222905_a("Raid center", blockPos, -65536);
    }

    private static void func_222905_a(String string, BlockPos blockPos, int n) {
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getY() + 1.3;
        double d3 = (double)blockPos.getZ() + 0.5;
        DebugRenderer.renderText(string, d, d2, d3, n, 0.04f, true, 0.0f, true);
    }

    private ActiveRenderInfo func_222904_c() {
        return this.client.gameRenderer.getActiveRenderInfo();
    }
}

