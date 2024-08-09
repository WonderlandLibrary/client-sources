/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ShulkerBoxTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox
extends ModelAdapter {
    public ModelAdapterShulkerBox() {
        super(TileEntityType.SHULKER_BOX, "shulker_box", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ShulkerModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ShulkerModel)) {
            return null;
        }
        ShulkerModel shulkerModel = (ShulkerModel)model;
        if (string.equals("base")) {
            return (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 0);
        }
        if (string.equals("lid")) {
            return (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 1);
        }
        return string.equals("head") ? (ModelRenderer)Reflector.ModelShulker_ModelRenderers.getValue(shulkerModel, 2) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"base", "lid", "head"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.SHULKER_BOX);
        if (!(tileEntityRenderer instanceof ShulkerBoxTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new ShulkerBoxTileEntityRenderer((ShulkerModel)model, tileEntityRendererDispatcher);
        }
        if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
            Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
            return null;
        }
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntityShulkerBoxRenderer_model, model);
        return tileEntityRenderer;
    }
}

