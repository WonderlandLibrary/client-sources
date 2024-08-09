/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.ArmorStandModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ArmorStandRenderer
extends LivingRenderer<ArmorStandEntity, ArmorStandArmorModel> {
    public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");

    public ArmorStandRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new ArmorStandModel(), 0.0f);
        this.addLayer(new BipedArmorLayer<ArmorStandEntity, ArmorStandArmorModel, ArmorStandArmorModel>(this, new ArmorStandArmorModel(0.5f), new ArmorStandArmorModel(1.0f)));
        this.addLayer(new HeldItemLayer<ArmorStandEntity, ArmorStandArmorModel>(this));
        this.addLayer(new ElytraLayer<ArmorStandEntity, ArmorStandArmorModel>(this));
        this.addLayer(new HeadLayer<ArmorStandEntity, ArmorStandArmorModel>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(ArmorStandEntity armorStandEntity) {
        return TEXTURE_ARMOR_STAND;
    }

    @Override
    protected void applyRotations(ArmorStandEntity armorStandEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f2));
        float f4 = (float)(armorStandEntity.world.getGameTime() - armorStandEntity.punchCooldown) + f3;
        if (f4 < 5.0f) {
            matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.sin(f4 / 1.5f * (float)Math.PI) * 3.0f));
        }
    }

    @Override
    protected boolean canRenderName(ArmorStandEntity armorStandEntity) {
        double d = this.renderManager.squareDistanceTo(armorStandEntity);
        float f = armorStandEntity.isCrouching() ? 32.0f : 64.0f;
        return d >= (double)(f * f) ? false : armorStandEntity.isCustomNameVisible();
    }

    @Override
    @Nullable
    protected RenderType func_230496_a_(ArmorStandEntity armorStandEntity, boolean bl, boolean bl2, boolean bl3) {
        if (!armorStandEntity.hasMarker()) {
            return super.func_230496_a_(armorStandEntity, bl, bl2, bl3);
        }
        ResourceLocation resourceLocation = this.getEntityTexture(armorStandEntity);
        if (bl2) {
            return RenderType.getEntityTranslucent(resourceLocation, false);
        }
        return bl ? RenderType.getEntityCutoutNoCull(resourceLocation, false) : null;
    }

    @Override
    protected boolean canRenderName(LivingEntity livingEntity) {
        return this.canRenderName((ArmorStandEntity)livingEntity);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((ArmorStandEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    @Nullable
    protected RenderType func_230496_a_(LivingEntity livingEntity, boolean bl, boolean bl2, boolean bl3) {
        return this.func_230496_a_((ArmorStandEntity)livingEntity, bl, bl2, bl3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ArmorStandEntity)entity2);
    }

    @Override
    protected boolean canRenderName(Entity entity2) {
        return this.canRenderName((ArmorStandEntity)entity2);
    }
}

