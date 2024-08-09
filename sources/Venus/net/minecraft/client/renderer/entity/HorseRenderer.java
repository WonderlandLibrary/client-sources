/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HorseMarkingsLayer;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public final class HorseRenderer
extends AbstractHorseRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private static final Map<CoatColors, ResourceLocation> field_239383_a_ = Util.make(Maps.newEnumMap(CoatColors.class), HorseRenderer::lambda$static$0);

    public HorseRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new HorseModel(0.0f), 1.1f);
        this.addLayer(new HorseMarkingsLayer(this));
        this.addLayer(new LeatherHorseArmorLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(HorseEntity horseEntity) {
        return field_239383_a_.get((Object)horseEntity.func_234239_eK_());
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((HorseEntity)entity2);
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(CoatColors.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
        enumMap.put(CoatColors.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
        enumMap.put(CoatColors.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
        enumMap.put(CoatColors.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
        enumMap.put(CoatColors.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
        enumMap.put(CoatColors.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
        enumMap.put(CoatColors.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
    }
}

