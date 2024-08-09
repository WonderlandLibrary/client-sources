/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class TNTMinecartRenderer
extends MinecartRenderer<TNTMinecartEntity> {
    public TNTMinecartRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    protected void renderBlockState(TNTMinecartEntity tNTMinecartEntity, float f, BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        int n2 = tNTMinecartEntity.getFuseTicks();
        if (n2 > -1 && (float)n2 - f + 1.0f < 10.0f) {
            float f2 = 1.0f - ((float)n2 - f + 1.0f) / 10.0f;
            f2 = MathHelper.clamp(f2, 0.0f, 1.0f);
            f2 *= f2;
            f2 *= f2;
            float f3 = 1.0f + f2 * 0.3f;
            matrixStack.scale(f3, f3, f3);
        }
        TNTMinecartRenderer.renderTntFlash(blockState, matrixStack, iRenderTypeBuffer, n, n2 > -1 && n2 / 5 % 2 == 0);
    }

    public static void renderTntFlash(BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, boolean bl) {
        int n2 = bl ? OverlayTexture.getPackedUV(OverlayTexture.getU(1.0f), 10) : OverlayTexture.NO_OVERLAY;
        if (Config.isShaders() && bl) {
            Shaders.setEntityColor(1.0f, 1.0f, 1.0f, 0.5f);
        }
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, n2);
        if (Config.isShaders()) {
            Shaders.setEntityColor(0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    @Override
    protected void renderBlockState(AbstractMinecartEntity abstractMinecartEntity, float f, BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.renderBlockState((TNTMinecartEntity)abstractMinecartEntity, f, blockState, matrixStack, iRenderTypeBuffer, n);
    }
}

