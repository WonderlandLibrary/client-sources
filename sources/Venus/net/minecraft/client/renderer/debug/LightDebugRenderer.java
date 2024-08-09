/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;

public class LightDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;

    public LightDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        ClientWorld clientWorld = this.minecraft.world;
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        BlockPos blockPos = new BlockPos(d, d2, d3);
        LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-10, -10, -10), blockPos.add(10, 10, 10))) {
            int n = clientWorld.getLightFor(LightType.SKY, blockPos2);
            float f = (float)(15 - n) / 15.0f * 0.5f + 0.16f;
            int n2 = MathHelper.hsvToRGB(f, 0.9f, 0.9f);
            long l = SectionPos.worldToSection(blockPos2.toLong());
            if (longOpenHashSet.add(l)) {
                DebugRenderer.renderText(clientWorld.getChunkProvider().getLightManager().getDebugInfo(LightType.SKY, SectionPos.from(l)), SectionPos.extractX(l) * 16 + 8, SectionPos.extractY(l) * 16 + 8, SectionPos.extractZ(l) * 16 + 8, 0xFF0000, 0.3f);
            }
            if (n == 15) continue;
            DebugRenderer.renderText(String.valueOf(n), (double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.25, (double)blockPos2.getZ() + 0.5, n2);
        }
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }
}

