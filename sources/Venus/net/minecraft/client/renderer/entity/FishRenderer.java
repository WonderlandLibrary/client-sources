/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class FishRenderer
extends EntityRenderer<FishingBobberEntity> {
    private static final ResourceLocation BOBBER = new ResourceLocation("textures/entity/fishing_hook.png");
    private static final RenderType field_229103_e_ = RenderType.getEntityCutout(BOBBER);

    public FishRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(FishingBobberEntity fishingBobberEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        PlayerEntity playerEntity = fishingBobberEntity.func_234606_i_();
        if (playerEntity != null) {
            float f3;
            double d;
            double d2;
            double d3;
            double d4;
            matrixStack.push();
            matrixStack.push();
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            matrixStack.rotate(this.renderManager.getCameraOrientation());
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            MatrixStack.Entry entry = matrixStack.getLast();
            Matrix4f matrix4f = entry.getMatrix();
            Matrix3f matrix3f = entry.getNormal();
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229103_e_);
            FishRenderer.func_229106_a_(iVertexBuilder, matrix4f, matrix3f, n, 0.0f, 0, 0, 1);
            FishRenderer.func_229106_a_(iVertexBuilder, matrix4f, matrix3f, n, 1.0f, 0, 1, 1);
            FishRenderer.func_229106_a_(iVertexBuilder, matrix4f, matrix3f, n, 1.0f, 1, 1, 0);
            FishRenderer.func_229106_a_(iVertexBuilder, matrix4f, matrix3f, n, 0.0f, 1, 0, 0);
            matrixStack.pop();
            int n2 = playerEntity.getPrimaryHand() == HandSide.RIGHT ? 1 : -1;
            ItemStack itemStack = playerEntity.getHeldItemMainhand();
            if (itemStack.getItem() != Items.FISHING_ROD) {
                n2 = -n2;
            }
            float f4 = playerEntity.getSwingProgress(f2);
            float f5 = MathHelper.sin(MathHelper.sqrt(f4) * (float)Math.PI);
            float f6 = MathHelper.lerp(f2, playerEntity.prevRenderYawOffset, playerEntity.renderYawOffset) * ((float)Math.PI / 180);
            double d5 = MathHelper.sin(f6);
            double d6 = MathHelper.cos(f6);
            double d7 = (double)n2 * 0.35;
            double d8 = 0.8;
            if ((this.renderManager.options == null || this.renderManager.options.getPointOfView().func_243192_a()) && playerEntity == Minecraft.getInstance().player) {
                d4 = this.renderManager.options.fov;
                Vector3d vector3d = new Vector3d((double)n2 * -0.36 * (d4 /= 100.0), -0.045 * d4, 0.4);
                vector3d = vector3d.rotatePitch(-MathHelper.lerp(f2, playerEntity.prevRotationPitch, playerEntity.rotationPitch) * ((float)Math.PI / 180));
                vector3d = vector3d.rotateYaw(-MathHelper.lerp(f2, playerEntity.prevRotationYaw, playerEntity.rotationYaw) * ((float)Math.PI / 180));
                vector3d = vector3d.rotateYaw(f5 * 0.5f);
                vector3d = vector3d.rotatePitch(-f5 * 0.7f);
                d3 = MathHelper.lerp((double)f2, playerEntity.prevPosX, playerEntity.getPosX()) + vector3d.x;
                d2 = MathHelper.lerp((double)f2, playerEntity.prevPosY, playerEntity.getPosY()) + vector3d.y;
                d = MathHelper.lerp((double)f2, playerEntity.prevPosZ, playerEntity.getPosZ()) + vector3d.z;
                f3 = playerEntity.getEyeHeight();
            } else {
                d3 = MathHelper.lerp((double)f2, playerEntity.prevPosX, playerEntity.getPosX()) - d6 * d7 - d5 * 0.8;
                d2 = playerEntity.prevPosY + (double)playerEntity.getEyeHeight() + (playerEntity.getPosY() - playerEntity.prevPosY) * (double)f2 - 0.45;
                d = MathHelper.lerp((double)f2, playerEntity.prevPosZ, playerEntity.getPosZ()) - d5 * d7 + d6 * 0.8;
                f3 = playerEntity.isCrouching() ? -0.1875f : 0.0f;
            }
            d4 = MathHelper.lerp((double)f2, fishingBobberEntity.prevPosX, fishingBobberEntity.getPosX());
            double d9 = MathHelper.lerp((double)f2, fishingBobberEntity.prevPosY, fishingBobberEntity.getPosY()) + 0.25;
            double d10 = MathHelper.lerp((double)f2, fishingBobberEntity.prevPosZ, fishingBobberEntity.getPosZ());
            float f7 = (float)(d3 - d4);
            float f8 = (float)(d2 - d9) + f3;
            float f9 = (float)(d - d10);
            IVertexBuilder iVertexBuilder2 = iRenderTypeBuffer.getBuffer(RenderType.getLines());
            Matrix4f matrix4f2 = matrixStack.getLast().getMatrix();
            int n3 = 16;
            for (int i = 0; i < 16; ++i) {
                FishRenderer.func_229104_a_(f7, f8, f9, iVertexBuilder2, matrix4f2, FishRenderer.func_229105_a_(i, 16));
                FishRenderer.func_229104_a_(f7, f8, f9, iVertexBuilder2, matrix4f2, FishRenderer.func_229105_a_(i + 1, 16));
            }
            matrixStack.pop();
            super.render(fishingBobberEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        }
    }

    private static float func_229105_a_(int n, int n2) {
        return (float)n / (float)n2;
    }

    private static void func_229106_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, int n, float f, int n2, int n3, int n4) {
        iVertexBuilder.pos(matrix4f, f - 0.5f, (float)n2 - 0.5f, 0.0f).color(255, 255, 255, 255).tex(n3, n4).overlay(OverlayTexture.NO_OVERLAY).lightmap(n).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    private static void func_229104_a_(float f, float f2, float f3, IVertexBuilder iVertexBuilder, Matrix4f matrix4f, float f4) {
        iVertexBuilder.pos(matrix4f, f * f4, f2 * (f4 * f4 + f4) * 0.5f + 0.25f, f3 * f4).color(0, 0, 0, 255).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(FishingBobberEntity fishingBobberEntity) {
        return BOBBER;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((FishingBobberEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((FishingBobberEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

