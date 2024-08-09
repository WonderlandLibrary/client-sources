/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.WitherModel;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class WitherAuraLayer
extends EnergyLayer<WitherEntity, WitherModel<WitherEntity>> {
    private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
    private final WitherModel<WitherEntity> witherModel = new WitherModel(0.5f);

    public WitherAuraLayer(IEntityRenderer<WitherEntity, WitherModel<WitherEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    protected float func_225634_a_(float f) {
        return MathHelper.cos(f * 0.02f) * 3.0f;
    }

    @Override
    protected ResourceLocation func_225633_a_() {
        return WITHER_ARMOR;
    }

    @Override
    protected EntityModel<WitherEntity> func_225635_b_() {
        return this.witherModel;
    }
}

