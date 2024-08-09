/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.client.renderer.entity.model.AbstractTropicalFishModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class TropicalFishRenderer
extends MobRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
    private final TropicalFishAModel<TropicalFishEntity> aModel = new TropicalFishAModel(0.0f);
    private final TropicalFishBModel<TropicalFishEntity> bModel = new TropicalFishBModel(0.0f);

    public TropicalFishRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new TropicalFishAModel(0.0f), 0.15f);
        this.addLayer(new TropicalFishPatternLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(TropicalFishEntity tropicalFishEntity) {
        return tropicalFishEntity.getBodyTexture();
    }

    @Override
    public void render(TropicalFishEntity tropicalFishEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        AbstractTropicalFishModel abstractTropicalFishModel;
        this.entityModel = abstractTropicalFishModel = tropicalFishEntity.getSize() == 0 ? this.aModel : this.bModel;
        float[] fArray = tropicalFishEntity.func_204219_dC();
        abstractTropicalFishModel.func_228257_a_(fArray[0], fArray[1], fArray[2]);
        super.render(tropicalFishEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        abstractTropicalFishModel.func_228257_a_(1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void applyRotations(TropicalFishEntity tropicalFishEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.applyRotations(tropicalFishEntity, matrixStack, f, f2, f3);
        float f4 = 4.3f * MathHelper.sin(0.6f * f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        if (!tropicalFishEntity.isInWater()) {
            matrixStack.translate(0.2f, 0.1f, 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((TropicalFishEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((TropicalFishEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((TropicalFishEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((TropicalFishEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((TropicalFishEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

