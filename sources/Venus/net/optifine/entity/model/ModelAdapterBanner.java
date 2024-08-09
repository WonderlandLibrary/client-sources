/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.BannerModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterBanner
extends ModelAdapter {
    public ModelAdapterBanner() {
        super(TileEntityType.BANNER, "banner", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new BannerModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BannerModel)) {
            return null;
        }
        BannerModel bannerModel = (BannerModel)model;
        if (string.equals("slate")) {
            return bannerModel.bannerSlate;
        }
        if (string.equals("stand")) {
            return bannerModel.bannerStand;
        }
        return string.equals("top") ? bannerModel.bannerTop : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"slate", "stand", "top"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.BANNER);
        if (!(tileEntityRenderer instanceof BannerTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new BannerTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if (!(model instanceof BannerModel)) {
            Config.warn("Not a banner model: " + model);
            return null;
        }
        BannerModel bannerModel = (BannerModel)model;
        return bannerModel.updateRenderer(tileEntityRenderer);
    }
}

