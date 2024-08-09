/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.model.LlamaSpitModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlamaSpit
extends ModelAdapter {
    public ModelAdapterLlamaSpit() {
        super(EntityType.LLAMA_SPIT, "llama_spit", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new LlamaSpitModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof LlamaSpitModel)) {
            return null;
        }
        LlamaSpitModel llamaSpitModel = (LlamaSpitModel)model;
        return string.equals("body") ? (ModelRenderer)Reflector.ModelLlamaSpit_renderer.getValue(llamaSpitModel) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        LlamaSpitRenderer llamaSpitRenderer = new LlamaSpitRenderer(entityRendererManager);
        if (!Reflector.RenderLlamaSpit_model.exists()) {
            Config.warn("Field not found: RenderLlamaSpit.model");
            return null;
        }
        Reflector.setFieldValue(llamaSpitRenderer, Reflector.RenderLlamaSpit_model, model);
        llamaSpitRenderer.shadowSize = f;
        return llamaSpitRenderer;
    }
}

