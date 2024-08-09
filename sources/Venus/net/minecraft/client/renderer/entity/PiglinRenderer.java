/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PiglinModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.util.ResourceLocation;

public class PiglinRenderer
extends BipedRenderer<MobEntity, PiglinModel<MobEntity>> {
    private static final Map<EntityType<?>, ResourceLocation> field_243503_a = ImmutableMap.of(EntityType.PIGLIN, new ResourceLocation("textures/entity/piglin/piglin.png"), EntityType.ZOMBIFIED_PIGLIN, new ResourceLocation("textures/entity/piglin/zombified_piglin.png"), EntityType.field_242287_aj, new ResourceLocation("textures/entity/piglin/piglin_brute.png"));

    public PiglinRenderer(EntityRendererManager entityRendererManager, boolean bl) {
        super(entityRendererManager, PiglinRenderer.func_239395_a_(bl), 0.5f, 1.0019531f, 1.0f, 1.0019531f);
        this.addLayer(new BipedArmorLayer(this, new BipedModel(0.5f), new BipedModel(1.02f)));
    }

    private static PiglinModel<MobEntity> func_239395_a_(boolean bl) {
        PiglinModel<MobEntity> piglinModel = new PiglinModel<MobEntity>(0.0f, 64, 64);
        if (bl) {
            piglinModel.field_239116_b_.showModel = false;
        }
        return piglinModel;
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        ResourceLocation resourceLocation = field_243503_a.get(mobEntity.getType());
        if (resourceLocation == null) {
            throw new IllegalArgumentException("I don't know what texture to use for " + mobEntity.getType());
        }
        return resourceLocation;
    }

    @Override
    protected boolean func_230495_a_(MobEntity mobEntity) {
        return mobEntity instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)mobEntity).func_242336_eL();
    }

    @Override
    protected boolean func_230495_a_(LivingEntity livingEntity) {
        return this.func_230495_a_((MobEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((MobEntity)entity2);
    }
}

