/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterIllager;

public class ModelAdapterIllusioner
extends ModelAdapterIllager {
    public ModelAdapterIllusioner() {
        super(EntityType.ILLUSIONER, "illusioner", 0.5f, new String[]{"illusion_illager"});
    }

    @Override
    public Model makeModel() {
        IllagerModel illagerModel = new IllagerModel(0.0f, 0.0f, 64, 64);
        illagerModel.func_205062_a().showModel = true;
        return illagerModel;
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        IllusionerRenderer illusionerRenderer = new IllusionerRenderer(entityRendererManager);
        illusionerRenderer.entityModel = (IllagerModel)model;
        illusionerRenderer.shadowSize = f;
        return illusionerRenderer;
    }
}

