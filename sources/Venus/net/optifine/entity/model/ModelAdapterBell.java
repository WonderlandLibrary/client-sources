/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.BellModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterBell
extends ModelAdapter {
    public ModelAdapterBell() {
        super(TileEntityType.BELL, "bell", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new BellModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BellModel)) {
            return null;
        }
        BellModel bellModel = (BellModel)model;
        return string.equals("body") ? bellModel.bellBody : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.BELL);
        if (!(tileEntityRenderer instanceof BellTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new BellTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!(model instanceof BellModel)) {
            Config.warn("Not a bell model: " + model);
            return null;
        }
        BellModel bellModel = (BellModel)model;
        return bellModel.updateRenderer(tileEntityRenderer);
    }
}

