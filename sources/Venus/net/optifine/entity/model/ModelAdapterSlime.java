/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSlime
extends ModelAdapter {
    public ModelAdapterSlime() {
        super(EntityType.SLIME, "slime", 0.25f);
    }

    @Override
    public Model makeModel() {
        return new SlimeModel(16);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof SlimeModel)) {
            return null;
        }
        SlimeModel slimeModel = (SlimeModel)model;
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.getFieldValue(slimeModel, Reflector.ModelSlime_ModelRenderers, 0);
        }
        if (string.equals("left_eye")) {
            return (ModelRenderer)Reflector.getFieldValue(slimeModel, Reflector.ModelSlime_ModelRenderers, 1);
        }
        if (string.equals("right_eye")) {
            return (ModelRenderer)Reflector.getFieldValue(slimeModel, Reflector.ModelSlime_ModelRenderers, 2);
        }
        return string.equals("mouth") ? (ModelRenderer)Reflector.getFieldValue(slimeModel, Reflector.ModelSlime_ModelRenderers, 3) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "left_eye", "right_eye", "mouth"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        SlimeRenderer slimeRenderer = new SlimeRenderer(entityRendererManager);
        slimeRenderer.entityModel = (SlimeModel)model;
        slimeRenderer.shadowSize = f;
        return slimeRenderer;
    }
}

