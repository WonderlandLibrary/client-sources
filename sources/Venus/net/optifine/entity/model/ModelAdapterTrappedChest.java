/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ChestModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterTrappedChest
extends ModelAdapter {
    public ModelAdapterTrappedChest() {
        super(TileEntityType.TRAPPED_CHEST, "trapped_chest", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ChestModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ChestModel)) {
            return null;
        }
        ChestModel chestModel = (ChestModel)model;
        if (string.equals("lid")) {
            return chestModel.lid;
        }
        if (string.equals("base")) {
            return chestModel.base;
        }
        return string.equals("knob") ? chestModel.knob : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"lid", "base", "knob"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        ChestTileEntityRenderer chestTileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.TRAPPED_CHEST);
        if (!(chestTileEntityRenderer instanceof ChestTileEntityRenderer)) {
            return null;
        }
        if (chestTileEntityRenderer.getType() == null) {
            chestTileEntityRenderer = new ChestTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!(model instanceof ChestModel)) {
            Config.warn("Not a chest model: " + model);
            return null;
        }
        ChestModel chestModel = (ChestModel)model;
        return chestModel.updateRenderer(chestTileEntityRenderer);
    }
}

