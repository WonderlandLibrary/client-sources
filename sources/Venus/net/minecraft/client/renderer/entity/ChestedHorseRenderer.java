/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.HorseArmorChestsModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.util.ResourceLocation;

public class ChestedHorseRenderer<T extends AbstractChestedHorseEntity>
extends AbstractHorseRenderer<T, HorseArmorChestsModel<T>> {
    private static final Map<EntityType<?>, ResourceLocation> field_195635_a = Maps.newHashMap(ImmutableMap.of(EntityType.DONKEY, new ResourceLocation("textures/entity/horse/donkey.png"), EntityType.MULE, new ResourceLocation("textures/entity/horse/mule.png")));

    public ChestedHorseRenderer(EntityRendererManager entityRendererManager, float f) {
        super(entityRendererManager, new HorseArmorChestsModel(0.0f), f);
    }

    @Override
    public ResourceLocation getEntityTexture(T t) {
        return field_195635_a.get(((Entity)t).getType());
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((T)((AbstractChestedHorseEntity)entity2));
    }
}

