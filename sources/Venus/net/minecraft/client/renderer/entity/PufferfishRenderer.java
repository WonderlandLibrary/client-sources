/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PufferFishBigModel;
import net.minecraft.client.renderer.entity.model.PufferFishMediumModel;
import net.minecraft.client.renderer.entity.model.PufferFishSmallModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class PufferfishRenderer
extends MobRenderer<PufferfishEntity, EntityModel<PufferfishEntity>> {
    private static final ResourceLocation PUFFERFISH_TEXTURES = new ResourceLocation("textures/entity/fish/pufferfish.png");
    private int lastPuffState = 3;
    private final PufferFishSmallModel<PufferfishEntity> modelSmall = new PufferFishSmallModel();
    private final PufferFishMediumModel<PufferfishEntity> modelMedium = new PufferFishMediumModel();
    private final PufferFishBigModel<PufferfishEntity> modelLarge = new PufferFishBigModel();

    public PufferfishRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new PufferFishBigModel(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(PufferfishEntity pufferfishEntity) {
        return PUFFERFISH_TEXTURES;
    }

    @Override
    public void render(PufferfishEntity pufferfishEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        int n2 = pufferfishEntity.getPuffState();
        if (n2 != this.lastPuffState) {
            this.entityModel = n2 == 0 ? this.modelSmall : (n2 == 1 ? this.modelMedium : this.modelLarge);
        }
        this.lastPuffState = n2;
        this.shadowSize = 0.1f + 0.1f * (float)n2;
        super.render(pufferfishEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void applyRotations(PufferfishEntity pufferfishEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        matrixStack.translate(0.0, MathHelper.cos(f * 0.05f) * 0.08f, 0.0);
        super.applyRotations(pufferfishEntity, matrixStack, f, f2, f3);
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((PufferfishEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((PufferfishEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((PufferfishEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PufferfishEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((PufferfishEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

