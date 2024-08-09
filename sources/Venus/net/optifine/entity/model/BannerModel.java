/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BannerModel
extends Model {
    public ModelRenderer bannerSlate;
    public ModelRenderer bannerStand;
    public ModelRenderer bannerTop;

    public BannerModel() {
        super(RenderType::getEntityCutoutNoCull);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        BannerTileEntityRenderer bannerTileEntityRenderer = new BannerTileEntityRenderer(tileEntityRendererDispatcher);
        this.bannerSlate = (ModelRenderer)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(bannerTileEntityRenderer, 0);
        this.bannerStand = (ModelRenderer)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(bannerTileEntityRenderer, 1);
        this.bannerTop = (ModelRenderer)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(bannerTileEntityRenderer, 2);
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityBannerRenderer_modelRenderers.exists()) {
            Config.warn("Field not found: TileEntityBannerRenderer.modelRenderers");
            return null;
        }
        Reflector.TileEntityBannerRenderer_modelRenderers.setValue(tileEntityRenderer, 0, this.bannerSlate);
        Reflector.TileEntityBannerRenderer_modelRenderers.setValue(tileEntityRenderer, 1, this.bannerStand);
        Reflector.TileEntityBannerRenderer_modelRenderers.setValue(tileEntityRenderer, 2, this.bannerTop);
        return tileEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

