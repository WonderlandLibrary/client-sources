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
import net.optifine.entity.model.ChestLargeModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterTrappedChestLarge
extends ModelAdapter {
    public ModelAdapterTrappedChestLarge() {
        super(TileEntityType.TRAPPED_CHEST, "trapped_chest_large", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ChestLargeModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ChestLargeModel)) {
            return null;
        }
        ChestLargeModel chestLargeModel = (ChestLargeModel)model;
        if (string.equals("lid_left")) {
            return chestLargeModel.lid_left;
        }
        if (string.equals("base_left")) {
            return chestLargeModel.base_left;
        }
        if (string.equals("knob_left")) {
            return chestLargeModel.knob_left;
        }
        if (string.equals("lid_right")) {
            return chestLargeModel.lid_right;
        }
        if (string.equals("base_right")) {
            return chestLargeModel.base_right;
        }
        return string.equals("knob_right") ? chestLargeModel.knob_right : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"lid_left", "base_left", "knob_left", "lid_right", "base_right", "knob_right"};
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
        if (!(model instanceof ChestLargeModel)) {
            Config.warn("Not a large chest model: " + model);
            return null;
        }
        ChestLargeModel chestLargeModel = (ChestLargeModel)model;
        return chestLargeModel.updateRenderer(chestTileEntityRenderer);
    }
}

