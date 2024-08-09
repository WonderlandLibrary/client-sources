/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TridentRenderer;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterTrident
extends ModelAdapter {
    public ModelAdapterTrident() {
        super(EntityType.TRIDENT, "trident", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new TridentModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof TridentModel)) {
            return null;
        }
        TridentModel tridentModel = (TridentModel)model;
        return string.equals("body") ? (ModelRenderer)Reflector.ModelTrident_tridentRenderer.getValue(tridentModel) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        TridentRenderer tridentRenderer = new TridentRenderer(entityRendererManager);
        if (!Reflector.RenderTrident_modelTrident.exists()) {
            Config.warn("Field not found: RenderTrident.modelTrident");
            return null;
        }
        Reflector.setFieldValue(tridentRenderer, Reflector.RenderTrident_modelTrident, model);
        tridentRenderer.shadowSize = f;
        return tridentRenderer;
    }
}

