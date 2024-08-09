/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class ZombieVillagerRenderer
extends BipedRenderer<ZombieVillagerEntity, ZombieVillagerModel<ZombieVillagerEntity>> {
    private static final ResourceLocation ZOMBIE_VILLAGER_TEXTURES = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");

    public ZombieVillagerRenderer(EntityRendererManager entityRendererManager, IReloadableResourceManager iReloadableResourceManager) {
        super(entityRendererManager, new ZombieVillagerModel(0.0f, false), 0.5f);
        this.addLayer(new BipedArmorLayer(this, new ZombieVillagerModel(0.5f, true), new ZombieVillagerModel(1.0f, true)));
        this.addLayer(new VillagerLevelPendantLayer<ZombieVillagerEntity, ZombieVillagerModel<ZombieVillagerEntity>>(this, iReloadableResourceManager, "zombie_villager"));
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieVillagerEntity zombieVillagerEntity) {
        return ZOMBIE_VILLAGER_TEXTURES;
    }

    @Override
    protected boolean func_230495_a_(ZombieVillagerEntity zombieVillagerEntity) {
        return zombieVillagerEntity.isConverting();
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity mobEntity) {
        return this.getEntityTexture((ZombieVillagerEntity)mobEntity);
    }

    @Override
    protected boolean func_230495_a_(LivingEntity livingEntity) {
        return this.func_230495_a_((ZombieVillagerEntity)livingEntity);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ZombieVillagerEntity)entity2);
    }
}

