/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Set;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;

public class VillageSectionsDebugRender
implements DebugRenderer.IDebugRenderer {
    private final Set<SectionPos> field_239375_a_ = Sets.newHashSet();

    VillageSectionsDebugRender() {
    }

    @Override
    public void clear() {
        this.field_239375_a_.clear();
    }

    public void func_239378_a_(SectionPos sectionPos) {
        this.field_239375_a_.add(sectionPos);
    }

    public void func_239379_b_(SectionPos sectionPos) {
        this.field_239375_a_.remove(sectionPos);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        this.func_239376_a_(d, d2, d3);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    private void func_239376_a_(double d, double d2, double d3) {
        BlockPos blockPos = new BlockPos(d, d2, d3);
        this.field_239375_a_.forEach(arg_0 -> VillageSectionsDebugRender.lambda$func_239376_a_$0(blockPos, arg_0));
    }

    private static void func_239380_c_(SectionPos sectionPos) {
        float f = 1.0f;
        BlockPos blockPos = sectionPos.getCenter();
        BlockPos blockPos2 = blockPos.add(-1.0, -1.0, -1.0);
        BlockPos blockPos3 = blockPos.add(1.0, 1.0, 1.0);
        DebugRenderer.renderBox(blockPos2, blockPos3, 0.2f, 1.0f, 0.2f, 0.15f);
    }

    private static void lambda$func_239376_a_$0(BlockPos blockPos, SectionPos sectionPos) {
        if (blockPos.withinDistance(sectionPos.getCenter(), 60.0)) {
            VillageSectionsDebugRender.func_239380_c_(sectionPos);
        }
    }
}

