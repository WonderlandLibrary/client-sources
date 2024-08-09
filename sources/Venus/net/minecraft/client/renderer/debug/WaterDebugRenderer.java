/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaterDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;

    public WaterDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        FluidState fluidState;
        BlockPos blockPos = this.minecraft.player.getPosition();
        World world = this.minecraft.player.world;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(0.0f, 1.0f, 0.0f, 0.75f);
        RenderSystem.disableTexture();
        RenderSystem.lineWidth(6.0f);
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-10, -10, -10), blockPos.add(10, 10, 10))) {
            fluidState = world.getFluidState(blockPos2);
            if (!fluidState.isTagged(FluidTags.WATER)) continue;
            double d4 = (float)blockPos2.getY() + fluidState.getActualHeight(world, blockPos2);
            DebugRenderer.renderBox(new AxisAlignedBB((float)blockPos2.getX() + 0.01f, (float)blockPos2.getY() + 0.01f, (float)blockPos2.getZ() + 0.01f, (float)blockPos2.getX() + 0.99f, d4, (float)blockPos2.getZ() + 0.99f).offset(-d, -d2, -d3), 1.0f, 1.0f, 1.0f, 0.2f);
        }
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-10, -10, -10), blockPos.add(10, 10, 10))) {
            fluidState = world.getFluidState(blockPos2);
            if (!fluidState.isTagged(FluidTags.WATER)) continue;
            DebugRenderer.renderText(String.valueOf(fluidState.getLevel()), (double)blockPos2.getX() + 0.5, (float)blockPos2.getY() + fluidState.getActualHeight(world, blockPos2), (double)blockPos2.getZ() + 0.5, -16777216);
        }
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}

