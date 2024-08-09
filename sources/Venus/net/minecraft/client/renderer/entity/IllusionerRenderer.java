/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class IllusionerRenderer
extends IllagerRenderer<IllusionerEntity> {
    private static final ResourceLocation ILLUSIONIST = new ResourceLocation("textures/entity/illager/illusioner.png");

    public IllusionerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new IllagerModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addLayer(new HeldItemLayer<IllusionerEntity, IllagerModel<IllusionerEntity>>(this, (IEntityRenderer)this){
            final IllusionerRenderer this$0;
            {
                this.this$0 = illusionerRenderer;
                super(iEntityRenderer);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, IllusionerEntity illusionerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                if (illusionerEntity.isSpellcasting() || illusionerEntity.isAggressive()) {
                    super.render(matrixStack, iRenderTypeBuffer, n, illusionerEntity, f, f2, f3, f4, f5, f6);
                }
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (IllusionerEntity)livingEntity, f, f2, f3, f4, f5, f6);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (IllusionerEntity)entity2, f, f2, f3, f4, f5, f6);
            }
        });
        ((IllagerModel)this.entityModel).func_205062_a().showModel = true;
    }

    @Override
    public ResourceLocation getEntityTexture(IllusionerEntity illusionerEntity) {
        return ILLUSIONIST;
    }

    @Override
    public void render(IllusionerEntity illusionerEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (illusionerEntity.isInvisible()) {
            Vector3d[] vector3dArray = illusionerEntity.getRenderLocations(f2);
            float f3 = this.handleRotationFloat(illusionerEntity, f2);
            for (int i = 0; i < vector3dArray.length; ++i) {
                matrixStack.push();
                matrixStack.translate(vector3dArray[i].x + (double)MathHelper.cos((float)i + f3 * 0.5f) * 0.025, vector3dArray[i].y + (double)MathHelper.cos((float)i + f3 * 0.75f) * 0.0125, vector3dArray[i].z + (double)MathHelper.cos((float)i + f3 * 0.7f) * 0.025);
                super.render(illusionerEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
                matrixStack.pop();
            }
        } else {
            super.render(illusionerEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        }
    }

    @Override
    protected boolean isVisible(IllusionerEntity illusionerEntity) {
        return false;
    }

    @Override
    public void render(MobEntity mobEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((IllusionerEntity)mobEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected boolean isVisible(LivingEntity livingEntity) {
        return this.isVisible((IllusionerEntity)livingEntity);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((IllusionerEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((IllusionerEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((IllusionerEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

