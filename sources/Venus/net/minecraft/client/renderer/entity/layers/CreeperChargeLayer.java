/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

public class CreeperChargeLayer
extends EnergyLayer<CreeperEntity, CreeperModel<CreeperEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<CreeperEntity> creeperModel = new CreeperModel(2.0f);

    public CreeperChargeLayer(IEntityRenderer<CreeperEntity, CreeperModel<CreeperEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    protected float func_225634_a_(float f) {
        return f * 0.01f;
    }

    @Override
    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    @Override
    protected EntityModel<CreeperEntity> func_225635_b_() {
        return this.creeperModel;
    }
}

