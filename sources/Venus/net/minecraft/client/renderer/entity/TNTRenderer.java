/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class TNTRenderer
extends EntityRenderer<TNTEntity> {
    public TNTRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.5f;
    }

    @Override
    public void render(TNTEntity tNTEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.5, 0.0);
        if ((float)tNTEntity.getFuse() - f2 + 1.0f < 10.0f) {
            float f3 = 1.0f - ((float)tNTEntity.getFuse() - f2 + 1.0f) / 10.0f;
            f3 = MathHelper.clamp(f3, 0.0f, 1.0f);
            f3 *= f3;
            f3 *= f3;
            float f4 = 1.0f + f3 * 0.3f;
            matrixStack.scale(f4, f4, f4);
        }
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0f));
        matrixStack.translate(-0.5, -0.5, 0.5);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
        TNTMinecartRenderer.renderTntFlash(Blocks.TNT.getDefaultState(), matrixStack, iRenderTypeBuffer, n, tNTEntity.getFuse() / 5 % 2 == 0);
        matrixStack.pop();
        super.render(tNTEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(TNTEntity tNTEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((TNTEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((TNTEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

