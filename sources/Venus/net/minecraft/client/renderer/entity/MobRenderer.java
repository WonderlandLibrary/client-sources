/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public abstract class MobRenderer<T extends MobEntity, M extends EntityModel<T>>
extends LivingRenderer<T, M> {
    public MobRenderer(EntityRendererManager entityRendererManager, M m, float f) {
        super(entityRendererManager, m, f);
    }

    @Override
    protected boolean canRenderName(T t) {
        return super.canRenderName(t) && (((LivingEntity)t).getAlwaysRenderNameTagForRender() || ((Entity)t).hasCustomName() && t == this.renderManager.pointedEntity);
    }

    @Override
    public boolean shouldRender(T t, ClippingHelper clippingHelper, double d, double d2, double d3) {
        if (super.shouldRender(t, clippingHelper, d, d2, d3)) {
            return false;
        }
        Entity entity2 = ((MobEntity)t).getLeashHolder();
        return entity2 != null ? clippingHelper.isBoundingBoxInFrustum(entity2.getRenderBoundingBox()) : false;
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        super.render(t, f, f2, matrixStack, iRenderTypeBuffer, n);
        Entity entity2 = ((MobEntity)t).getLeashHolder();
        if (entity2 != null) {
            this.renderLeash(t, f2, matrixStack, iRenderTypeBuffer, entity2);
        }
    }

    private <E extends Entity> void renderLeash(T t, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, E e) {
        if (!Config.isShaders() || !Shaders.isShadowPass) {
            matrixStack.push();
            Vector3d vector3d = e.getLeashPosition(f);
            double d = (double)(MathHelper.lerp(f, ((MobEntity)t).renderYawOffset, ((MobEntity)t).prevRenderYawOffset) * ((float)Math.PI / 180)) + 1.5707963267948966;
            Vector3d vector3d2 = ((Entity)t).func_241205_ce_();
            double d2 = Math.cos(d) * vector3d2.z + Math.sin(d) * vector3d2.x;
            double d3 = Math.sin(d) * vector3d2.z - Math.cos(d) * vector3d2.x;
            double d4 = MathHelper.lerp((double)f, ((MobEntity)t).prevPosX, ((Entity)t).getPosX()) + d2;
            double d5 = MathHelper.lerp((double)f, ((MobEntity)t).prevPosY, ((Entity)t).getPosY()) + vector3d2.y;
            double d6 = MathHelper.lerp((double)f, ((MobEntity)t).prevPosZ, ((Entity)t).getPosZ()) + d3;
            matrixStack.translate(d2, vector3d2.y, d3);
            float f2 = (float)(vector3d.x - d4);
            float f3 = (float)(vector3d.y - d5);
            float f4 = (float)(vector3d.z - d6);
            float f5 = 0.025f;
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getLeash());
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            float f6 = MathHelper.fastInvSqrt(f2 * f2 + f4 * f4) * 0.025f / 2.0f;
            float f7 = f4 * f6;
            float f8 = f2 * f6;
            BlockPos blockPos = new BlockPos(((Entity)t).getEyePosition(f));
            BlockPos blockPos2 = new BlockPos(e.getEyePosition(f));
            int n = this.getBlockLight(t, blockPos);
            int n2 = this.renderManager.getRenderer(e).getBlockLight(e, blockPos2);
            int n3 = ((MobEntity)t).world.getLightFor(LightType.SKY, blockPos);
            int n4 = ((MobEntity)t).world.getLightFor(LightType.SKY, blockPos2);
            if (Config.isShaders()) {
                Shaders.beginLeash();
            }
            MobRenderer.renderSide(iVertexBuilder, matrix4f, f2, f3, f4, n, n2, n3, n4, 0.025f, 0.025f, f7, f8);
            MobRenderer.renderSide(iVertexBuilder, matrix4f, f2, f3, f4, n, n2, n3, n4, 0.025f, 0.0f, f7, f8);
            if (Config.isShaders()) {
                Shaders.endLeash();
            }
            matrixStack.pop();
        }
    }

    public static void renderSide(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, float f, float f2, float f3, int n, int n2, int n3, int n4, float f4, float f5, float f6, float f7) {
        int n5 = 24;
        for (int i = 0; i < 24; ++i) {
            float f8 = (float)i / 23.0f;
            int n6 = (int)MathHelper.lerp(f8, n, n2);
            int n7 = (int)MathHelper.lerp(f8, n3, n4);
            int n8 = LightTexture.packLight(n6, n7);
            MobRenderer.addVertexPair(iVertexBuilder, matrix4f, n8, f, f2, f3, f4, f5, 24, i, false, f6, f7);
            MobRenderer.addVertexPair(iVertexBuilder, matrix4f, n8, f, f2, f3, f4, f5, 24, i + 1, true, f6, f7);
        }
    }

    public static void addVertexPair(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, int n, float f, float f2, float f3, float f4, float f5, int n2, int n3, boolean bl, float f6, float f7) {
        float f8 = 0.5f;
        float f9 = 0.4f;
        float f10 = 0.3f;
        if (n3 % 2 == 0) {
            f8 *= 0.7f;
            f9 *= 0.7f;
            f10 *= 0.7f;
        }
        float f11 = (float)n3 / (float)n2;
        float f12 = f * f11;
        float f13 = f2 > 0.0f ? f2 * f11 * f11 : f2 - f2 * (1.0f - f11) * (1.0f - f11);
        float f14 = f3 * f11;
        if (!bl) {
            iVertexBuilder.pos(matrix4f, f12 + f6, f13 + f4 - f5, f14 - f7).color(f8, f9, f10, 1.0f).lightmap(n).endVertex();
        }
        iVertexBuilder.pos(matrix4f, f12 - f6, f13 + f5, f14 + f7).color(f8, f9, f10, 1.0f).lightmap(n).endVertex();
        if (bl) {
            iVertexBuilder.pos(matrix4f, f12 + f6, f13 + f4 - f5, f14 - f7).color(f8, f9, f10, 1.0f).lightmap(n).endVertex();
        }
    }

    @Override
    protected boolean canRenderName(LivingEntity livingEntity) {
        return this.canRenderName((T)((MobEntity)livingEntity));
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((T)((MobEntity)livingEntity), f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected boolean canRenderName(Entity entity2) {
        return this.canRenderName((T)((MobEntity)entity2));
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((T)((MobEntity)entity2), f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public boolean shouldRender(Entity entity2, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((T)((MobEntity)entity2), clippingHelper, d, d2, d3);
    }
}

