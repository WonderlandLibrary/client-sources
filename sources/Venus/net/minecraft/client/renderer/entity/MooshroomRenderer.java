/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class MooshroomRenderer
extends MobRenderer<MooshroomEntity, CowModel<MooshroomEntity>> {
    private static final Map<MooshroomEntity.Type, ResourceLocation> field_217774_a = Util.make(Maps.newHashMap(), MooshroomRenderer::lambda$static$0);

    public MooshroomRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new CowModel(), 0.7f);
        this.addLayer(new MooshroomMushroomLayer<MooshroomEntity>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MooshroomEntity mooshroomEntity) {
        return field_217774_a.get((Object)mooshroomEntity.getMooshroomType());
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((MooshroomEntity)entity2);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(MooshroomEntity.Type.BROWN, new ResourceLocation("textures/entity/cow/brown_mooshroom.png"));
        hashMap.put(MooshroomEntity.Type.RED, new ResourceLocation("textures/entity/cow/red_mooshroom.png"));
    }
}

