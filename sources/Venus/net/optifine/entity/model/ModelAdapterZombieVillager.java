/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.minecraft.resources.IReloadableResourceManager;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;

public class ModelAdapterZombieVillager
extends ModelAdapterBiped {
    public ModelAdapterZombieVillager() {
        super(EntityType.ZOMBIE_VILLAGER, "zombie_villager", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new ZombieVillagerModel(0.0f, false);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        IReloadableResourceManager iReloadableResourceManager = (IReloadableResourceManager)Minecraft.getInstance().getResourceManager();
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ZombieVillagerRenderer zombieVillagerRenderer = new ZombieVillagerRenderer(entityRendererManager, iReloadableResourceManager);
        zombieVillagerRenderer.entityModel = (ZombieVillagerModel)model;
        zombieVillagerRenderer.shadowSize = f;
        return zombieVillagerRenderer;
    }
}

