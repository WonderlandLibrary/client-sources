/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSpider
extends ModelAdapter {
    public ModelAdapterSpider() {
        super(EntityType.SPIDER, "spider", 1.0f);
    }

    protected ModelAdapterSpider(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new SpiderModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof SpiderModel)) {
            return null;
        }
        SpiderModel spiderModel = (SpiderModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 0);
        }
        if (string.equals("neck")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 1);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 2);
        }
        if (string.equals("leg1")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 3);
        }
        if (string.equals("leg2")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 4);
        }
        if (string.equals("leg3")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 5);
        }
        if (string.equals("leg4")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 6);
        }
        if (string.equals("leg5")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 7);
        }
        if (string.equals("leg6")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 8);
        }
        if (string.equals("leg7")) {
            return (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 9);
        }
        return string.equals("leg8") ? (ModelRenderer)Reflector.ModelSpider_ModelRenderers.getValue(spiderModel, 10) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        SpiderRenderer spiderRenderer = new SpiderRenderer(entityRendererManager);
        spiderRenderer.entityModel = (EntityModel)model;
        spiderRenderer.shadowSize = f;
        return spiderRenderer;
    }
}

