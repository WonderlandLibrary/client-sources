/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;

public class ModelAdapterZombieHorse
extends ModelAdapterHorse {
    public ModelAdapterZombieHorse() {
        super(EntityType.ZOMBIE_HORSE, "zombie_horse", 0.75f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        UndeadHorseRenderer undeadHorseRenderer = new UndeadHorseRenderer(entityRendererManager);
        undeadHorseRenderer.entityModel = (HorseModel)model;
        undeadHorseRenderer.shadowSize = f;
        return undeadHorseRenderer;
    }
}

