/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BedTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.BedModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterBed
extends ModelAdapter {
    public ModelAdapterBed() {
        super(TileEntityType.BED, "bed", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new BedModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BedModel)) {
            return null;
        }
        BedModel bedModel = (BedModel)model;
        if (string.equals("head")) {
            return bedModel.headPiece;
        }
        if (string.equals("foot")) {
            return bedModel.footPiece;
        }
        ModelRenderer[] modelRendererArray = bedModel.legs;
        if (modelRendererArray != null) {
            if (string.equals("leg1")) {
                return modelRendererArray[0];
            }
            if (string.equals("leg2")) {
                return modelRendererArray[5];
            }
            if (string.equals("leg3")) {
                return modelRendererArray[5];
            }
            if (string.equals("leg4")) {
                return modelRendererArray[5];
            }
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "foot", "leg1", "leg2", "leg3", "leg4"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.BED);
        if (!(tileEntityRenderer instanceof BedTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new BedTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!(model instanceof BedModel)) {
            Config.warn("Not a BedModel: " + model);
            return null;
        }
        BedModel bedModel = (BedModel)model;
        return bedModel.updateRenderer(tileEntityRenderer);
    }
}

