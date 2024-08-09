/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class MinecartRenderer<T extends AbstractMinecartEntity>
extends EntityRenderer<T> {
    private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("textures/entity/minecart.png");
    protected final EntityModel<T> modelMinecart = new MinecartModel();

    public MinecartRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.7f;
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        super.render(t, f, f2, matrixStack, iRenderTypeBuffer, n);
        matrixStack.push();
        long l = (long)((Entity)t).getEntityId() * 493286711L;
        l = l * l * 4392167121L + l * 98761L;
        float f3 = (((float)(l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float f4 = (((float)(l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float f5 = (((float)(l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        matrixStack.translate(f3, f4, f5);
        double d = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastTickPosX, ((Entity)t).getPosX());
        double d2 = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastTickPosY, ((Entity)t).getPosY());
        double d3 = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastTickPosZ, ((Entity)t).getPosZ());
        double d4 = 0.3f;
        Vector3d vector3d = ((AbstractMinecartEntity)t).getPos(d, d2, d3);
        float f6 = MathHelper.lerp(f2, ((AbstractMinecartEntity)t).prevRotationPitch, ((AbstractMinecartEntity)t).rotationPitch);
        if (vector3d != null) {
            Vector3d vector3d2 = ((AbstractMinecartEntity)t).getPosOffset(d, d2, d3, 0.3f);
            Vector3d vector3d3 = ((AbstractMinecartEntity)t).getPosOffset(d, d2, d3, -0.3f);
            if (vector3d2 == null) {
                vector3d2 = vector3d;
            }
            if (vector3d3 == null) {
                vector3d3 = vector3d;
            }
            matrixStack.translate(vector3d.x - d, (vector3d2.y + vector3d3.y) / 2.0 - d2, vector3d.z - d3);
            Vector3d vector3d4 = vector3d3.add(-vector3d2.x, -vector3d2.y, -vector3d2.z);
            if (vector3d4.length() != 0.0) {
                vector3d4 = vector3d4.normalize();
                f = (float)(Math.atan2(vector3d4.z, vector3d4.x) * 180.0 / Math.PI);
                f6 = (float)(Math.atan(vector3d4.y) * 73.0);
            }
        }
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(-f6));
        float f7 = (float)((AbstractMinecartEntity)t).getRollingAmplitude() - f2;
        float f8 = ((AbstractMinecartEntity)t).getDamage() - f2;
        if (f8 < 0.0f) {
            f8 = 0.0f;
        }
        if (f7 > 0.0f) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.sin(f7) * f7 * f8 / 10.0f * (float)((AbstractMinecartEntity)t).getRollingDirection()));
        }
        int n2 = ((AbstractMinecartEntity)t).getDisplayTileOffset();
        BlockState blockState = ((AbstractMinecartEntity)t).getDisplayTile();
        if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
            matrixStack.push();
            float f9 = 0.75f;
            matrixStack.scale(0.75f, 0.75f, 0.75f);
            matrixStack.translate(-0.5, (float)(n2 - 8) / 16.0f, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
            this.renderBlockState(t, f2, blockState, matrixStack, iRenderTypeBuffer, n);
            matrixStack.pop();
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.setRotationAngles(t, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.modelMinecart.getRenderType(this.getEntityTexture(t)));
        this.modelMinecart.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(T t) {
        return MINECART_TEXTURES;
    }

    protected void renderBlockState(T t, float f, BlockState blockState, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((T)((AbstractMinecartEntity)entity2));
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((T)((AbstractMinecartEntity)entity2), f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

