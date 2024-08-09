/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlama
extends ModelAdapter {
    public ModelAdapterLlama() {
        super(EntityType.LLAMA, "llama", 0.7f);
    }

    public ModelAdapterLlama(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new LlamaModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof LlamaModel)) {
            return null;
        }
        LlamaModel llamaModel = (LlamaModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 0);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 1);
        }
        if (string.equals("leg1")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 2);
        }
        if (string.equals("leg2")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 3);
        }
        if (string.equals("leg3")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 4);
        }
        if (string.equals("leg4")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 5);
        }
        if (string.equals("chest_right")) {
            return (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 6);
        }
        return string.equals("chest_left") ? (ModelRenderer)Reflector.ModelLlama_ModelRenderers.getValue(llamaModel, 7) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "chest_right", "chest_left"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        LlamaRenderer llamaRenderer = new LlamaRenderer(entityRendererManager);
        llamaRenderer.entityModel = (LlamaModel)model;
        llamaRenderer.shadowSize = f;
        return llamaRenderer;
    }
}

