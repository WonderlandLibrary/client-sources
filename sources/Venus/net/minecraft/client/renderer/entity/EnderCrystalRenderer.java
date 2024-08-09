/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class EnderCrystalRenderer
extends EntityRenderer<EnderCrystalEntity> {
    private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
    private static final RenderType field_229046_e_ = RenderType.getEntityCutoutNoCull(ENDER_CRYSTAL_TEXTURES);
    private static final float field_229047_f_ = (float)Math.sin(0.7853981633974483);
    private final ModelRenderer field_229048_g_;
    private final ModelRenderer field_229049_h_;
    private final ModelRenderer field_229050_i_;

    public EnderCrystalRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.5f;
        this.field_229049_h_ = new ModelRenderer(64, 32, 0, 0);
        this.field_229049_h_.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.field_229048_g_ = new ModelRenderer(64, 32, 32, 0);
        this.field_229048_g_.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.field_229050_i_ = new ModelRenderer(64, 32, 0, 16);
        this.field_229050_i_.addBox(-6.0f, 0.0f, -6.0f, 12.0f, 4.0f, 12.0f);
    }

    @Override
    public void render(EnderCrystalEntity enderCrystalEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        float f3 = EnderCrystalRenderer.func_229051_a_(enderCrystalEntity, f2);
        float f4 = ((float)enderCrystalEntity.innerRotation + f2) * 3.0f;
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229046_e_);
        matrixStack.push();
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        matrixStack.translate(0.0, -0.5, 0.0);
        int n2 = OverlayTexture.NO_OVERLAY;
        if (enderCrystalEntity.shouldShowBottom()) {
            this.field_229050_i_.render(matrixStack, iVertexBuilder, n, n2);
        }
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        matrixStack.translate(0.0, 1.5f + f3 / 2.0f, 0.0);
        matrixStack.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0f, field_229047_f_), 60.0f, true));
        this.field_229049_h_.render(matrixStack, iVertexBuilder, n, n2);
        float f5 = 0.875f;
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0f, field_229047_f_), 60.0f, true));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        this.field_229049_h_.render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0f, field_229047_f_), 60.0f, true));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        this.field_229048_g_.render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.pop();
        matrixStack.pop();
        BlockPos blockPos = enderCrystalEntity.getBeamTarget();
        if (blockPos != null) {
            float f6 = (float)blockPos.getX() + 0.5f;
            float f7 = (float)blockPos.getY() + 0.5f;
            float f8 = (float)blockPos.getZ() + 0.5f;
            float f9 = (float)((double)f6 - enderCrystalEntity.getPosX());
            float f10 = (float)((double)f7 - enderCrystalEntity.getPosY());
            float f11 = (float)((double)f8 - enderCrystalEntity.getPosZ());
            matrixStack.translate(f9, f10, f11);
            EnderDragonRenderer.func_229059_a_(-f9, -f10 + f3, -f11, f2, enderCrystalEntity.innerRotation, matrixStack, iRenderTypeBuffer, n);
        }
        super.render(enderCrystalEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    public static float func_229051_a_(EnderCrystalEntity enderCrystalEntity, float f) {
        float f2 = (float)enderCrystalEntity.innerRotation + f;
        float f3 = MathHelper.sin(f2 * 0.2f) / 2.0f + 0.5f;
        f3 = (f3 * f3 + f3) * 0.4f;
        return f3 - 1.4f;
    }

    @Override
    public ResourceLocation getEntityTexture(EnderCrystalEntity enderCrystalEntity) {
        return ENDER_CRYSTAL_TEXTURES;
    }

    @Override
    public boolean shouldRender(EnderCrystalEntity enderCrystalEntity, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return super.shouldRender(enderCrystalEntity, clippingHelper, d, d2, d3) || enderCrystalEntity.getBeamTarget() != null;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((EnderCrystalEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EnderCrystalEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public boolean shouldRender(Entity entity2, ClippingHelper clippingHelper, double d, double d2, double d3) {
        return this.shouldRender((EnderCrystalEntity)entity2, clippingHelper, d, d2, d3);
    }
}

