/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractZombieRenderer<T extends ZombieEntity, M extends ZombieModel<T>>
extends BipedRenderer<T, M> {
    private static final ResourceLocation field_217771_a = new ResourceLocation("textures/entity/zombie/zombie.png");

    protected AbstractZombieRenderer(EntityRendererManager entityRendererManager, M m, M m2, M m3) {
        super(entityRendererManager, m, 0.5f);
        this.addLayer(new BipedArmorLayer(this, m2, m3));
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieEntity zombieEntity) {
        return field_217771_a;
    }

    @Override
    protected boolean func_230495_a_(T t) {
        return ((ZombieEntity)t).isDrowning();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        return this.getEntityTexture((ZombieEntity)mobEntity);
    }

    @Override
    protected boolean func_230495_a_(LivingEntity livingEntity) {
        return this.func_230495_a_((T)((ZombieEntity)livingEntity));
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ZombieEntity)entity2);
    }
}

