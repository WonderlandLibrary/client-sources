/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart
extends ModelAdapter {
    public ModelAdapterMinecart() {
        super(EntityType.MINECART, "minecart", 0.5f);
    }

    protected ModelAdapterMinecart(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new MinecartModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof MinecartModel)) {
            return null;
        }
        MinecartModel minecartModel = (MinecartModel)model;
        ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.ModelMinecart_sideModels.getValue(minecartModel);
        if (modelRendererArray != null) {
            if (string.equals("bottom")) {
                return modelRendererArray[0];
            }
            if (string.equals("back")) {
                return modelRendererArray[5];
            }
            if (string.equals("front")) {
                return modelRendererArray[5];
            }
            if (string.equals("right")) {
                return modelRendererArray[5];
            }
            if (string.equals("left")) {
                return modelRendererArray[5];
            }
            if (string.equals("dirt")) {
                return modelRendererArray[5];
            }
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"bottom", "back", "front", "right", "left", "dirt"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        MinecartRenderer minecartRenderer = new MinecartRenderer(entityRendererManager);
        if (!Reflector.RenderMinecart_modelMinecart.exists()) {
            Config.warn("Field not found: RenderMinecart.modelMinecart");
            return null;
        }
        Reflector.setFieldValue(minecartRenderer, Reflector.RenderMinecart_modelMinecart, model);
        minecartRenderer.shadowSize = f;
        return minecartRenderer;
    }
}

