/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign
extends ModelAdapter {
    public ModelAdapterSign() {
        super(TileEntityType.SIGN, "sign", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new SignTileEntityRenderer.SignModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof SignTileEntityRenderer.SignModel)) {
            return null;
        }
        SignTileEntityRenderer.SignModel signModel = (SignTileEntityRenderer.SignModel)model;
        if (string.equals("board")) {
            return (ModelRenderer)Reflector.ModelSign_ModelRenderers.getValue(signModel, 0);
        }
        return string.equals("stick") ? (ModelRenderer)Reflector.ModelSign_ModelRenderers.getValue(signModel, 1) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"board", "stick"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.SIGN);
        if (!(tileEntityRenderer instanceof SignTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new SignTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!Reflector.TileEntitySignRenderer_model.exists()) {
            Config.warn("Field not found: TileEntitySignRenderer.model");
            return null;
        }
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntitySignRenderer_model, model);
        return tileEntityRenderer;
    }
}

