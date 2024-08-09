/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.monster.ZombieEntity;

public class ZombieRenderer
extends AbstractZombieRenderer<ZombieEntity, ZombieModel<ZombieEntity>> {
    public ZombieRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new ZombieModel(0.0f, false), new ZombieModel(0.5f, true), new ZombieModel(1.0f, true));
    }
}

