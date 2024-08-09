/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class GuardianRenderer
extends MobRenderer<GuardianEntity, GuardianModel> {
    private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType field_229107_h_ = RenderType.getEntityCutoutNoCull(GUARDIAN_BEAM_TEXTURE);

    public GuardianRenderer(EntityRendererManager entityRendererManager) {
        this(entityRendererManager, 0.5f);
    }

    protected GuardianRenderer(EntityRendererManager entityRendererManager, float f) {
        super(entityRendererManager, new GuardianModel(), f);
    }

    @Override
    public boolean shouldRender(GuardianEntity guardianEntity, ClippingHelper clippingHelper, double d, double d2, double d3) {
        LivingEntity livingEntity;
        if (super.shouldRender(guardianEntity, clippingHelper, d, d2, d3)) {
            return false;
        }
        if (guardianEntity.hasTargetedEntity() && (livingEntity = guardianEntity.getTargetedEntity()) != null) {
            Vector3d vector3d = this.getPosition(livingEntity, (double)livingEntity.getHeight() * 0.5, 1.0f);
            Vector3d vector3d2 = this.getPosition(guardianEntity, guardianEntity.getEyeHeight(), 1.0f);
            return clippingHelper.isBoundingBoxInFrustum(new AxisAlignedBB(vector3d2.x, vector3d2.y, vector3d2.z, vector3d.x, vector3d.y, vector3d.z));
        }
        return true;
    }

    private Vector3d getPosition(LivingEntity livingEntity, double d, float f) {
        double d2 = MathHelper.lerp((double)f, livingEntity.lastTickPosX, livingEntity.getPosX());
        double d3 = MathHelper.lerp((double)f, livingEntity.lastTickPosY, livingEntity.getPosY()) + d;
        double d4 = MathHelper.lerp((double)f, livingEntity.lastTickPosZ, livingEntity.getPosZ());
        return new Vector3d(d2, d3, d4);
    }

    @Override
    public void render(GuardianEntity guardianEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        super.render(guardianEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        LivingEntity livingEntity = guardianEntity.getTargetedEntity();
        if (livingEntity != null) {
            float f3 = guardianEntity.getAttackAnimationScale(f2);
            float f4 = (float)guardianEntity.world.getGameTime() + f2;
            float f5 = f4 * 0.5f % 1.0f;
            float f6 = guardianEntity.getEyeHeight();
            matrixStack.push();
            matrixStack.translate(0.0, f6, 0.0);
            Vector3d vector3d = this.getPosition(livingEntity, (double)livingEntity.getHeight() * 0.5, f2);
            Vector3d vector3d2 = this.getPosition(guardianEntity, f6, f2);
            Vector3d vector3d3 = vector3d.subtract(vector3d2);
            float f7 = (float)(vector3d3.length() + 1.0);
            vector3d3 = vector3d3.normalize();
            float f8 = (float)Math.acos(vector3d3.y);
            float f9 = (float)Math.atan2(vector3d3.z, vector3d3.x);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((1.5707964f - f9) * 57.295776f));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f8 * 57.295776f));
            boolean bl = true;
            float f10 = f4 * 0.05f * -1.5f;
            float f11 = f3 * f3;
            int n2 = 64 + (int)(f11 * 191.0f);
            int n3 = 32 + (int)(f11 * 191.0f);
            int n4 = 128 - (int)(f11 * 64.0f);
            float f12 = 0.2f;
            float f13 = 0.282f;
            float f14 = MathHelper.cos(f10 + 2.3561945f) * 0.282f;
            float f15 = MathHelper.sin(f10 + 2.3561945f) * 0.282f;
            float f16 = MathHelper.cos(f10 + 0.7853982f) * 0.282f;
            float f17 = MathHelper.sin(f10 + 0.7853982f) * 0.282f;
            float f18 = MathHelper.cos(f10 + 3.926991f) * 0.282f;
            float f19 = MathHelper.sin(f10 + 3.926991f) * 0.282f;
            float f20 = MathHelper.cos(f10 + 5.4977875f) * 0.282f;
            float f21 = MathHelper.sin(f10 + 5.4977875f) * 0.282f;
            float f22 = MathHelper.cos(f10 + (float)Math.PI) * 0.2f;
            float f23 = MathHelper.sin(f10 + (float)Math.PI) * 0.2f;
            float f24 = MathHelper.cos(f10 + 0.0f) * 0.2f;
            float f25 = MathHelper.sin(f10 + 0.0f) * 0.2f;
            float f26 = MathHelper.cos(f10 + 1.5707964f) * 0.2f;
            float f27 = MathHelper.sin(f10 + 1.5707964f) * 0.2f;
            float f28 = MathHelper.cos(f10 + 4.712389f) * 0.2f;
            float f29 = MathHelper.sin(f10 + 4.712389f) * 0.2f;
            float f30 = 0.0f;
            float f31 = 0.4999f;
            float f32 = -1.0f + f5;
            float f33 = f7 * 2.5f + f32;
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229107_h_);
            MatrixStack.Entry entry = matrixStack.getLast();
            Matrix4f matrix4f = entry.getMatrix();
            Matrix3f matrix3f = entry.getNormal();
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f22, f7, f23, n2, n3, n4, 0.4999f, f33);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f22, 0.0f, f23, n2, n3, n4, 0.4999f, f32);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f24, 0.0f, f25, n2, n3, n4, 0.0f, f32);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f24, f7, f25, n2, n3, n4, 0.0f, f33);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f26, f7, f27, n2, n3, n4, 0.4999f, f33);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f26, 0.0f, f27, n2, n3, n4, 0.4999f, f32);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f28, 0.0f, f29, n2, n3, n4, 0.0f, f32);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f28, f7, f29, n2, n3, n4, 0.0f, f33);
            float f34 = 0.0f;
            if (guardianEntity.ticksExisted % 2 == 0) {
                f34 = 0.5f;
            }
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f14, f7, f15, n2, n3, n4, 0.5f, f34 + 0.5f);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f16, f7, f17, n2, n3, n4, 1.0f, f34 + 0.5f);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f20, f7, f21, n2, n3, n4, 1.0f, f34);
            GuardianRenderer.func_229108_a_(iVertexBuilder, matrix4f, matrix3f, f18, f7, f19, n2, n3, n4, 0.5f, f34);
            matrixStack.pop();
        }
    }

    private static void func_229108_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, float f, float f2, float f3, int n, int n2, int n3, float f4, float f5) {
        iVertexBuilder.pos(matrix4f, f, f2, f3).color(n, n2, n3, 255).tex(f4, f5).overlay(OverlayTexture.NO_OVERLAY).lightmap(0xF000F0).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(GuardianEntity guardianEntity) {
        return GUARDIAN_TEXTURE;
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((GuardianEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public boolean shouldRender(MobEntity mobEntity, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((GuardianEntity)mobEntity, clippingHelper, d, d2, d3);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((GuardianEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((GuardianEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((GuardianEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public boolean shouldRender(Entity entity2, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((GuardianEntity)entity2, clippingHelper, d, d2, d3);
    }
}

