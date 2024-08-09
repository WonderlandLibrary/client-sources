/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class EnderDragonRenderer
extends EntityRenderer<EnderDragonEntity> {
    public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
    private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    private static final ResourceLocation field_229052_g_ = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    private static final RenderType field_229053_h_ = RenderType.getEntityCutoutNoCull(DRAGON_TEXTURES);
    private static final RenderType field_229054_i_ = RenderType.getEntityDecal(DRAGON_TEXTURES);
    private static final RenderType field_229055_j_ = RenderType.getEyes(field_229052_g_);
    private static final RenderType field_229056_k_ = RenderType.getEntitySmoothCutout(ENDERCRYSTAL_BEAM_TEXTURES);
    private static final float field_229057_l_ = (float)(Math.sqrt(3.0) / 2.0);
    private final EnderDragonModel model = new EnderDragonModel();

    public EnderDragonRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
        this.shadowSize = 0.5f;
    }

    @Override
    public void render(EnderDragonEntity enderDragonEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        float f3 = (float)enderDragonEntity.getMovementOffsets(7, f2)[0];
        float f4 = (float)(enderDragonEntity.getMovementOffsets(5, f2)[1] - enderDragonEntity.getMovementOffsets(10, f2)[1]);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-f3));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f4 * 10.0f));
        matrixStack.translate(0.0, 0.0, 1.0);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.501f, 0.0);
        boolean bl = enderDragonEntity.hurtTime > 0;
        this.model.setLivingAnimations(enderDragonEntity, 0.0f, 0.0f, f2);
        if (enderDragonEntity.deathTicks > 0) {
            float f5 = (float)enderDragonEntity.deathTicks / 200.0f;
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityAlpha(DRAGON_EXPLODING_TEXTURES, f5));
            this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
            IVertexBuilder iVertexBuilder2 = iRenderTypeBuffer.getBuffer(field_229054_i_);
            this.model.render(matrixStack, iVertexBuilder2, n, OverlayTexture.getPackedUV(0.0f, bl), 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229053_h_);
            this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.getPackedUV(0.0f, bl), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229055_j_);
        if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
        }
        Config.getRenderGlobal().renderOverlayEyes = true;
        this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        Config.getRenderGlobal().renderOverlayEyes = false;
        if (Config.isShaders()) {
            Shaders.endSpiderEyes();
        }
        if (enderDragonEntity.deathTicks > 0) {
            float f6 = ((float)enderDragonEntity.deathTicks + f2) / 200.0f;
            float f7 = Math.min(f6 > 0.8f ? (f6 - 0.8f) / 0.2f : 0.0f, 1.0f);
            Random random2 = new Random(432L);
            IVertexBuilder iVertexBuilder3 = iRenderTypeBuffer.getBuffer(RenderType.getLightning());
            matrixStack.push();
            matrixStack.translate(0.0, -1.0, -2.0);
            int n2 = 0;
            while ((float)n2 < (f6 + f6 * f6) / 2.0f * 60.0f) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(random2.nextFloat() * 360.0f));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(random2.nextFloat() * 360.0f));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(random2.nextFloat() * 360.0f));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(random2.nextFloat() * 360.0f));
                matrixStack.rotate(Vector3f.YP.rotationDegrees(random2.nextFloat() * 360.0f));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(random2.nextFloat() * 360.0f + f6 * 90.0f));
                float f8 = random2.nextFloat() * 20.0f + 5.0f + f7 * 10.0f;
                float f9 = random2.nextFloat() * 2.0f + 1.0f + f7 * 2.0f;
                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                int n3 = (int)(255.0f * (1.0f - f7));
                EnderDragonRenderer.func_229061_a_(iVertexBuilder3, matrix4f, n3);
                EnderDragonRenderer.func_229060_a_(iVertexBuilder3, matrix4f, f8, f9);
                EnderDragonRenderer.func_229062_b_(iVertexBuilder3, matrix4f, f8, f9);
                EnderDragonRenderer.func_229061_a_(iVertexBuilder3, matrix4f, n3);
                EnderDragonRenderer.func_229062_b_(iVertexBuilder3, matrix4f, f8, f9);
                EnderDragonRenderer.func_229063_c_(iVertexBuilder3, matrix4f, f8, f9);
                EnderDragonRenderer.func_229061_a_(iVertexBuilder3, matrix4f, n3);
                EnderDragonRenderer.func_229063_c_(iVertexBuilder3, matrix4f, f8, f9);
                EnderDragonRenderer.func_229060_a_(iVertexBuilder3, matrix4f, f8, f9);
                ++n2;
            }
            matrixStack.pop();
        }
        matrixStack.pop();
        if (enderDragonEntity.closestEnderCrystal != null) {
            matrixStack.push();
            float f10 = (float)(enderDragonEntity.closestEnderCrystal.getPosX() - MathHelper.lerp((double)f2, enderDragonEntity.prevPosX, enderDragonEntity.getPosX()));
            float f11 = (float)(enderDragonEntity.closestEnderCrystal.getPosY() - MathHelper.lerp((double)f2, enderDragonEntity.prevPosY, enderDragonEntity.getPosY()));
            float f12 = (float)(enderDragonEntity.closestEnderCrystal.getPosZ() - MathHelper.lerp((double)f2, enderDragonEntity.prevPosZ, enderDragonEntity.getPosZ()));
            EnderDragonRenderer.func_229059_a_(f10, f11 + EnderCrystalRenderer.func_229051_a_(enderDragonEntity.closestEnderCrystal, f2), f12, f2, enderDragonEntity.ticksExisted, matrixStack, iRenderTypeBuffer, n);
            matrixStack.pop();
        }
        super.render(enderDragonEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    private static void func_229061_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, int n) {
        iVertexBuilder.pos(matrix4f, 0.0f, 0.0f, 0.0f).color(255, 255, 255, n).endVertex();
        iVertexBuilder.pos(matrix4f, 0.0f, 0.0f, 0.0f).color(255, 255, 255, n).endVertex();
    }

    private static void func_229060_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, float f, float f2) {
        iVertexBuilder.pos(matrix4f, -field_229057_l_ * f2, f, -0.5f * f2).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229062_b_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, float f, float f2) {
        iVertexBuilder.pos(matrix4f, field_229057_l_ * f2, f, -0.5f * f2).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229063_c_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, float f, float f2) {
        iVertexBuilder.pos(matrix4f, 0.0f, f, 1.0f * f2).color(255, 0, 255, 0).endVertex();
    }

    public static void func_229059_a_(float f, float f2, float f3, float f4, int n, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n2) {
        float f5 = MathHelper.sqrt(f * f + f3 * f3);
        float f6 = MathHelper.sqrt(f * f + f2 * f2 + f3 * f3);
        matrixStack.push();
        matrixStack.translate(0.0, 2.0, 0.0);
        matrixStack.rotate(Vector3f.YP.rotation((float)(-Math.atan2(f3, f)) - 1.5707964f));
        matrixStack.rotate(Vector3f.XP.rotation((float)(-Math.atan2(f5, f2)) - 1.5707964f));
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229056_k_);
        float f7 = 0.0f - ((float)n + f4) * 0.01f;
        float f8 = MathHelper.sqrt(f * f + f2 * f2 + f3 * f3) / 32.0f - ((float)n + f4) * 0.01f;
        int n3 = 8;
        float f9 = 0.0f;
        float f10 = 0.75f;
        float f11 = 0.0f;
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        for (int i = 1; i <= 8; ++i) {
            float f12 = MathHelper.sin((float)i * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            float f13 = MathHelper.cos((float)i * ((float)Math.PI * 2) / 8.0f) * 0.75f;
            float f14 = (float)i / 8.0f;
            iVertexBuilder.pos(matrix4f, f9 * 0.2f, f10 * 0.2f, 0.0f).color(0, 0, 0, 255).tex(f11, f7).overlay(OverlayTexture.NO_OVERLAY).lightmap(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f9, f10, f6).color(255, 255, 255, 255).tex(f11, f8).overlay(OverlayTexture.NO_OVERLAY).lightmap(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f12, f13, f6).color(255, 255, 255, 255).tex(f14, f8).overlay(OverlayTexture.NO_OVERLAY).lightmap(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            iVertexBuilder.pos(matrix4f, f12 * 0.2f, f13 * 0.2f, 0.0f).color(0, 0, 0, 255).tex(f14, f7).overlay(OverlayTexture.NO_OVERLAY).lightmap(n2).normal(matrix3f, 0.0f, -1.0f, 0.0f).endVertex();
            f9 = f12;
            f10 = f13;
            f11 = f14;
        }
        matrixStack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(EnderDragonEntity enderDragonEntity) {
        return DRAGON_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((EnderDragonEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EnderDragonEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    public static class EnderDragonModel
    extends EntityModel<EnderDragonEntity> {
        private final ModelRenderer head;
        private final ModelRenderer spine;
        private final ModelRenderer jaw;
        private final ModelRenderer body;
        private ModelRenderer leftProximalWing;
        private ModelRenderer leftDistalWing;
        private ModelRenderer leftForeThigh;
        private ModelRenderer leftForeLeg;
        private ModelRenderer leftForeFoot;
        private ModelRenderer leftHindThigh;
        private ModelRenderer leftHindLeg;
        private ModelRenderer leftHindFoot;
        private ModelRenderer rightProximalWing;
        private ModelRenderer rightDistalWing;
        private ModelRenderer rightForeThigh;
        private ModelRenderer rightForeLeg;
        private ModelRenderer rightForeFoot;
        private ModelRenderer rightHindThigh;
        private ModelRenderer rightHindLeg;
        private ModelRenderer rightHindFoot;
        @Nullable
        private EnderDragonEntity dragonInstance;
        private float partialTicks;

        public EnderDragonModel() {
            this.textureWidth = 256;
            this.textureHeight = 256;
            float f = -16.0f;
            this.head = new ModelRenderer(this);
            this.head.addBox("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 0.0f, 176, 44);
            this.head.addBox("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, 0.0f, 112, 30);
            this.head.mirror = true;
            this.head.addBox("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
            this.head.addBox("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
            this.head.mirror = false;
            this.head.addBox("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
            this.head.addBox("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
            this.jaw = new ModelRenderer(this);
            this.jaw.setRotationPoint(0.0f, 4.0f, -8.0f);
            this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16, 0.0f, 176, 65);
            this.head.addChild(this.jaw);
            this.spine = new ModelRenderer(this);
            this.spine.addBox("box", -5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f, 192, 104);
            this.spine.addBox("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6, 0.0f, 48, 0);
            this.body = new ModelRenderer(this);
            this.body.setRotationPoint(0.0f, 4.0f, 8.0f);
            this.body.addBox("body", -12.0f, 0.0f, -16.0f, 24, 24, 64, 0.0f, 0, 0);
            this.body.addBox("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12, 0.0f, 220, 53);
            this.body.addBox("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12, 0.0f, 220, 53);
            this.body.addBox("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12, 0.0f, 220, 53);
            this.leftProximalWing = new ModelRenderer(this);
            this.leftProximalWing.mirror = true;
            this.leftProximalWing.setRotationPoint(12.0f, 5.0f, 2.0f);
            this.leftProximalWing.addBox("bone", 0.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
            this.leftProximalWing.addBox("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
            this.leftDistalWing = new ModelRenderer(this);
            this.leftDistalWing.mirror = true;
            this.leftDistalWing.setRotationPoint(56.0f, 0.0f, 0.0f);
            this.leftDistalWing.addBox("bone", 0.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
            this.leftDistalWing.addBox("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
            this.leftProximalWing.addChild(this.leftDistalWing);
            this.leftForeThigh = new ModelRenderer(this);
            this.leftForeThigh.setRotationPoint(12.0f, 20.0f, 2.0f);
            this.leftForeThigh.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
            this.leftForeLeg = new ModelRenderer(this);
            this.leftForeLeg.setRotationPoint(0.0f, 20.0f, -1.0f);
            this.leftForeLeg.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
            this.leftForeThigh.addChild(this.leftForeLeg);
            this.leftForeFoot = new ModelRenderer(this);
            this.leftForeFoot.setRotationPoint(0.0f, 23.0f, 0.0f);
            this.leftForeFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
            this.leftForeLeg.addChild(this.leftForeFoot);
            this.leftHindThigh = new ModelRenderer(this);
            this.leftHindThigh.setRotationPoint(16.0f, 16.0f, 42.0f);
            this.leftHindThigh.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
            this.leftHindLeg = new ModelRenderer(this);
            this.leftHindLeg.setRotationPoint(0.0f, 32.0f, -4.0f);
            this.leftHindLeg.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
            this.leftHindThigh.addChild(this.leftHindLeg);
            this.leftHindFoot = new ModelRenderer(this);
            this.leftHindFoot.setRotationPoint(0.0f, 31.0f, 4.0f);
            this.leftHindFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
            this.leftHindLeg.addChild(this.leftHindFoot);
            this.rightProximalWing = new ModelRenderer(this);
            this.rightProximalWing.setRotationPoint(-12.0f, 5.0f, 2.0f);
            this.rightProximalWing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
            this.rightProximalWing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
            this.rightDistalWing = new ModelRenderer(this);
            this.rightDistalWing.setRotationPoint(-56.0f, 0.0f, 0.0f);
            this.rightDistalWing.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
            this.rightDistalWing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
            this.rightProximalWing.addChild(this.rightDistalWing);
            this.rightForeThigh = new ModelRenderer(this);
            this.rightForeThigh.setRotationPoint(-12.0f, 20.0f, 2.0f);
            this.rightForeThigh.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
            this.rightForeLeg = new ModelRenderer(this);
            this.rightForeLeg.setRotationPoint(0.0f, 20.0f, -1.0f);
            this.rightForeLeg.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
            this.rightForeThigh.addChild(this.rightForeLeg);
            this.rightForeFoot = new ModelRenderer(this);
            this.rightForeFoot.setRotationPoint(0.0f, 23.0f, 0.0f);
            this.rightForeFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
            this.rightForeLeg.addChild(this.rightForeFoot);
            this.rightHindThigh = new ModelRenderer(this);
            this.rightHindThigh.setRotationPoint(-16.0f, 16.0f, 42.0f);
            this.rightHindThigh.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
            this.rightHindLeg = new ModelRenderer(this);
            this.rightHindLeg.setRotationPoint(0.0f, 32.0f, -4.0f);
            this.rightHindLeg.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
            this.rightHindThigh.addChild(this.rightHindLeg);
            this.rightHindFoot = new ModelRenderer(this);
            this.rightHindFoot.setRotationPoint(0.0f, 31.0f, 4.0f);
            this.rightHindFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
            this.rightHindLeg.addChild(this.rightHindFoot);
        }

        @Override
        public void setLivingAnimations(EnderDragonEntity enderDragonEntity, float f, float f2, float f3) {
            this.dragonInstance = enderDragonEntity;
            this.partialTicks = f3;
        }

        @Override
        public void setRotationAngles(EnderDragonEntity enderDragonEntity, float f, float f2, float f3, float f4, float f5) {
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
            float f5;
            matrixStack.push();
            float f6 = MathHelper.lerp(this.partialTicks, this.dragonInstance.prevAnimTime, this.dragonInstance.animTime);
            this.jaw.rotateAngleX = (float)(Math.sin(f6 * ((float)Math.PI * 2)) + 1.0) * 0.2f;
            float f7 = (float)(Math.sin(f6 * ((float)Math.PI * 2) - 1.0f) + 1.0);
            f7 = (f7 * f7 + f7 * 2.0f) * 0.05f;
            matrixStack.translate(0.0, f7 - 2.0f, -3.0);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f7 * 2.0f));
            float f8 = 0.0f;
            float f9 = 20.0f;
            float f10 = -12.0f;
            float f11 = 1.5f;
            double[] dArray = this.dragonInstance.getMovementOffsets(6, this.partialTicks);
            float f12 = MathHelper.rotWrap(this.dragonInstance.getMovementOffsets(5, this.partialTicks)[0] - this.dragonInstance.getMovementOffsets(10, this.partialTicks)[0]);
            float f13 = MathHelper.rotWrap(this.dragonInstance.getMovementOffsets(5, this.partialTicks)[0] + (double)(f12 / 2.0f));
            float f14 = f6 * ((float)Math.PI * 2);
            for (int i = 0; i < 5; ++i) {
                double[] dArray2 = this.dragonInstance.getMovementOffsets(5 - i, this.partialTicks);
                f5 = (float)Math.cos((float)i * 0.45f + f14) * 0.15f;
                this.spine.rotateAngleY = MathHelper.rotWrap(dArray2[0] - dArray[0]) * ((float)Math.PI / 180) * 1.5f;
                this.spine.rotateAngleX = f5 + this.dragonInstance.getHeadPartYOffset(i, dArray, dArray2) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.spine.rotateAngleZ = -MathHelper.rotWrap(dArray2[0] - (double)f13) * ((float)Math.PI / 180) * 1.5f;
                this.spine.rotationPointY = f9;
                this.spine.rotationPointZ = f10;
                this.spine.rotationPointX = f8;
                f9 = (float)((double)f9 + Math.sin(this.spine.rotateAngleX) * 10.0);
                f10 = (float)((double)f10 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
                f8 = (float)((double)f8 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
                this.spine.render(matrixStack, iVertexBuilder, n, n2);
            }
            this.head.rotationPointY = f9;
            this.head.rotationPointZ = f10;
            this.head.rotationPointX = f8;
            double[] dArray3 = this.dragonInstance.getMovementOffsets(0, this.partialTicks);
            this.head.rotateAngleY = MathHelper.rotWrap(dArray3[0] - dArray[0]) * ((float)Math.PI / 180);
            this.head.rotateAngleX = MathHelper.rotWrap(this.dragonInstance.getHeadPartYOffset(6, dArray, dArray3)) * ((float)Math.PI / 180) * 1.5f * 5.0f;
            this.head.rotateAngleZ = -MathHelper.rotWrap(dArray3[0] - (double)f13) * ((float)Math.PI / 180);
            this.head.render(matrixStack, iVertexBuilder, n, n2);
            matrixStack.push();
            matrixStack.translate(0.0, 1.0, 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(-f12 * 1.5f));
            matrixStack.translate(0.0, -1.0, 0.0);
            this.body.rotateAngleZ = 0.0f;
            this.body.render(matrixStack, iVertexBuilder, n, n2);
            float f15 = f6 * ((float)Math.PI * 2);
            this.leftProximalWing.rotateAngleX = 0.125f - (float)Math.cos(f15) * 0.2f;
            this.leftProximalWing.rotateAngleY = -0.25f;
            this.leftProximalWing.rotateAngleZ = -((float)(Math.sin(f15) + 0.125)) * 0.8f;
            this.leftDistalWing.rotateAngleZ = (float)(Math.sin(f15 + 2.0f) + 0.5) * 0.75f;
            this.rightProximalWing.rotateAngleX = this.leftProximalWing.rotateAngleX;
            this.rightProximalWing.rotateAngleY = -this.leftProximalWing.rotateAngleY;
            this.rightProximalWing.rotateAngleZ = -this.leftProximalWing.rotateAngleZ;
            this.rightDistalWing.rotateAngleZ = -this.leftDistalWing.rotateAngleZ;
            this.func_229081_a_(matrixStack, iVertexBuilder, n, n2, f7, this.leftProximalWing, this.leftForeThigh, this.leftForeLeg, this.leftForeFoot, this.leftHindThigh, this.leftHindLeg, this.leftHindFoot);
            this.func_229081_a_(matrixStack, iVertexBuilder, n, n2, f7, this.rightProximalWing, this.rightForeThigh, this.rightForeLeg, this.rightForeFoot, this.rightHindThigh, this.rightHindLeg, this.rightHindFoot);
            matrixStack.pop();
            f5 = -((float)Math.sin(f6 * ((float)Math.PI * 2))) * 0.0f;
            f14 = f6 * ((float)Math.PI * 2);
            f9 = 10.0f;
            f10 = 60.0f;
            f8 = 0.0f;
            dArray = this.dragonInstance.getMovementOffsets(11, this.partialTicks);
            for (int i = 0; i < 12; ++i) {
                dArray3 = this.dragonInstance.getMovementOffsets(12 + i, this.partialTicks);
                f5 = (float)((double)f5 + Math.sin((float)i * 0.45f + f14) * (double)0.05f);
                this.spine.rotateAngleY = (MathHelper.rotWrap(dArray3[0] - dArray[0]) * 1.5f + 180.0f) * ((float)Math.PI / 180);
                this.spine.rotateAngleX = f5 + (float)(dArray3[1] - dArray[1]) * ((float)Math.PI / 180) * 1.5f * 5.0f;
                this.spine.rotateAngleZ = MathHelper.rotWrap(dArray3[0] - (double)f13) * ((float)Math.PI / 180) * 1.5f;
                this.spine.rotationPointY = f9;
                this.spine.rotationPointZ = f10;
                this.spine.rotationPointX = f8;
                f9 = (float)((double)f9 + Math.sin(this.spine.rotateAngleX) * 10.0);
                f10 = (float)((double)f10 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
                f8 = (float)((double)f8 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
                this.spine.render(matrixStack, iVertexBuilder, n, n2);
            }
            matrixStack.pop();
        }

        private void func_229081_a_(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, ModelRenderer modelRenderer, ModelRenderer modelRenderer2, ModelRenderer modelRenderer3, ModelRenderer modelRenderer4, ModelRenderer modelRenderer5, ModelRenderer modelRenderer6, ModelRenderer modelRenderer7) {
            modelRenderer5.rotateAngleX = 1.0f + f * 0.1f;
            modelRenderer6.rotateAngleX = 0.5f + f * 0.1f;
            modelRenderer7.rotateAngleX = 0.75f + f * 0.1f;
            modelRenderer2.rotateAngleX = 1.3f + f * 0.1f;
            modelRenderer3.rotateAngleX = -0.5f - f * 0.1f;
            modelRenderer4.rotateAngleX = 0.75f + f * 0.1f;
            modelRenderer.render(matrixStack, iVertexBuilder, n, n2);
            modelRenderer2.render(matrixStack, iVertexBuilder, n, n2);
            modelRenderer5.render(matrixStack, iVertexBuilder, n, n2);
        }

        @Override
        public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
            this.setLivingAnimations((EnderDragonEntity)entity2, f, f2, f3);
        }

        @Override
        public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
            this.setRotationAngles((EnderDragonEntity)entity2, f, f2, f3, f4, f5);
        }
    }
}

