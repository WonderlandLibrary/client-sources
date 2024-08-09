/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull
extends ModelAdapter {
    public ModelAdapterWitherSkull() {
        super(EntityType.WITHER_SKULL, "wither_skull", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new GenericHeadModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof GenericHeadModel)) {
            return null;
        }
        GenericHeadModel genericHeadModel = (GenericHeadModel)model;
        return string.equals("head") ? (ModelRenderer)Reflector.ModelGenericHead_skeletonHead.getValue(genericHeadModel) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WitherSkullRenderer witherSkullRenderer = new WitherSkullRenderer(entityRendererManager);
        if (!Reflector.RenderWitherSkull_model.exists()) {
            Config.warn("Field not found: RenderWitherSkull_model");
            return null;
        }
        Reflector.setFieldValue(witherSkullRenderer, Reflector.RenderWitherSkull_model, model);
        witherSkullRenderer.shadowSize = f;
        return witherSkullRenderer;
    }
}

