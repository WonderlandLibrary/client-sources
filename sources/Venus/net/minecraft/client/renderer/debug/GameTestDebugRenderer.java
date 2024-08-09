/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class GameTestDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Map<BlockPos, Marker> field_229020_a_ = Maps.newHashMap();

    public void func_229022_a_(BlockPos blockPos, int n, String string, int n2) {
        this.field_229020_a_.put(blockPos, new Marker(n, string, Util.milliTime() + (long)n2));
    }

    @Override
    public void clear() {
        this.field_229020_a_.clear();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        long l = Util.milliTime();
        this.field_229020_a_.entrySet().removeIf(arg_0 -> GameTestDebugRenderer.lambda$render$0(l, arg_0));
        this.field_229020_a_.forEach(this::func_229023_a_);
    }

    private void func_229023_a_(BlockPos blockPos, Marker marker) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.color4f(0.0f, 1.0f, 0.0f, 0.75f);
        RenderSystem.disableTexture();
        DebugRenderer.renderBox(blockPos, 0.02f, marker.func_229027_a_(), marker.func_229028_b_(), marker.func_229029_c_(), marker.func_229030_d_());
        if (!marker.field_229025_b_.isEmpty()) {
            double d = (double)blockPos.getX() + 0.5;
            double d2 = (double)blockPos.getY() + 1.2;
            double d3 = (double)blockPos.getZ() + 0.5;
            DebugRenderer.renderText(marker.field_229025_b_, d, d2, d3, -1, 0.01f, true, 0.0f, true);
        }
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    private static boolean lambda$render$0(long l, Map.Entry entry) {
        return l > ((Marker)entry.getValue()).field_229026_c_;
    }

    static class Marker {
        public int field_229024_a_;
        public String field_229025_b_;
        public long field_229026_c_;

        public Marker(int n, String string, long l) {
            this.field_229024_a_ = n;
            this.field_229025_b_ = string;
            this.field_229026_c_ = l;
        }

        public float func_229027_a_() {
            return (float)(this.field_229024_a_ >> 16 & 0xFF) / 255.0f;
        }

        public float func_229028_b_() {
            return (float)(this.field_229024_a_ >> 8 & 0xFF) / 255.0f;
        }

        public float func_229029_c_() {
            return (float)(this.field_229024_a_ & 0xFF) / 255.0f;
        }

        public float func_229030_d_() {
            return (float)(this.field_229024_a_ >> 24 & 0xFF) / 255.0f;
        }
    }
}

