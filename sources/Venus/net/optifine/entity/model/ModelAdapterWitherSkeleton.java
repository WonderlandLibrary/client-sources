/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;

public class ModelAdapterWitherSkeleton
extends ModelAdapterBiped {
    public ModelAdapterWitherSkeleton() {
        super(EntityType.WITHER_SKELETON, "wither_skeleton", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new SkeletonModel();
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WitherSkeletonRenderer witherSkeletonRenderer = new WitherSkeletonRenderer(entityRendererManager);
        witherSkeletonRenderer.entityModel = (SkeletonModel)model;
        witherSkeletonRenderer.shadowSize = f;
        return witherSkeletonRenderer;
    }
}

