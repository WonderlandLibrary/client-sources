/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class WolfRenderer
extends MobRenderer<WolfEntity, WolfModel<WolfEntity>> {
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation ANGRY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public WolfRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new WolfModel(), 0.5f);
        this.addLayer(new WolfCollarLayer(this));
    }

    @Override
    protected float handleRotationFloat(WolfEntity wolfEntity, float f) {
        return wolfEntity.getTailRotation();
    }

    @Override
    public void render(WolfEntity wolfEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (wolfEntity.isWolfWet()) {
            float f3 = wolfEntity.getShadingWhileWet(f2);
            ((WolfModel)this.entityModel).setTint(f3, f3, f3);
        }
        super.render(wolfEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        if (wolfEntity.isWolfWet()) {
            ((WolfModel)this.entityModel).setTint(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(WolfEntity wolfEntity) {
        if (wolfEntity.isTamed()) {
            return TAMED_WOLF_TEXTURES;
        }
        return wolfEntity.func_233678_J__() ? ANGRY_WOLF_TEXTURES : WOLF_TEXTURES;
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WolfEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.handleRotationFloat((WolfEntity)livingEntity, f);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WolfEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((WolfEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((WolfEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

