/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ConduitModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterConduit
extends ModelAdapter {
    public ModelAdapterConduit() {
        super(TileEntityType.CONDUIT, "conduit", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ConduitModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ConduitModel)) {
            return null;
        }
        ConduitModel conduitModel = (ConduitModel)model;
        if (string.equals("eye")) {
            return conduitModel.eye;
        }
        if (string.equals("wind")) {
            return conduitModel.wind;
        }
        if (string.equals("base")) {
            return conduitModel.base;
        }
        return string.equals("cage") ? conduitModel.cage : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"eye", "wind", "base", "cage"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.CONDUIT);
        if (!(tileEntityRenderer instanceof ConduitTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new ConduitTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!(model instanceof ConduitModel)) {
            Config.warn("Not a conduit model: " + model);
            return null;
        }
        ConduitModel conduitModel = (ConduitModel)model;
        return conduitModel.updateRenderer(tileEntityRenderer);
    }
}

