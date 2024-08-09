/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EndermanEyesLayer;
import net.minecraft.client.renderer.entity.layers.HeldBlockLayer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class EndermanRenderer
extends MobRenderer<EndermanEntity, EndermanModel<EndermanEntity>> {
    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("textures/entity/enderman/enderman.png");
    private final Random rnd = new Random();

    public EndermanRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new EndermanModel(0.0f), 0.5f);
        this.addLayer(new EndermanEyesLayer<EndermanEntity>(this));
        this.addLayer(new HeldBlockLayer(this));
    }

    @Override
    public void render(EndermanEntity endermanEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        BlockState blockState = endermanEntity.getHeldBlockState();
        EndermanModel endermanModel = (EndermanModel)this.getEntityModel();
        endermanModel.isCarrying = blockState != null;
        endermanModel.isAttacking = endermanEntity.isScreaming();
        super.render(endermanEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public Vector3d getRenderOffset(EndermanEntity endermanEntity, float f) {
        if (endermanEntity.isScreaming()) {
            double d = 0.02;
            return new Vector3d(this.rnd.nextGaussian() * 0.02, 0.0, this.rnd.nextGaussian() * 0.02);
        }
        return super.getRenderOffset(endermanEntity, f);
    }

    @Override
    public ResourceLocation getEntityTexture(EndermanEntity endermanEntity) {
        return ENDERMAN_TEXTURES;
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EndermanEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EndermanEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((EndermanEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((EndermanEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public Vector3d getRenderOffset(Entity entity2, float f) {
        return this.getRenderOffset((EndermanEntity)entity2, f);
    }
}

