/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BedTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BedModel
extends Model {
    public ModelRenderer headPiece;
    public ModelRenderer footPiece;
    public ModelRenderer[] legs = new ModelRenderer[4];

    public BedModel() {
        super(RenderType::getEntityCutoutNoCull);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        BedTileEntityRenderer bedTileEntityRenderer = new BedTileEntityRenderer(tileEntityRendererDispatcher);
        this.headPiece = (ModelRenderer)Reflector.TileEntityBedRenderer_headModel.getValue(bedTileEntityRenderer);
        this.footPiece = (ModelRenderer)Reflector.TileEntityBedRenderer_footModel.getValue(bedTileEntityRenderer);
        this.legs = (ModelRenderer[])Reflector.TileEntityBedRenderer_legModels.getValue(bedTileEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityBedRenderer_headModel.exists()) {
            Config.warn("Field not found: TileEntityBedRenderer.head");
            return null;
        }
        if (!Reflector.TileEntityBedRenderer_footModel.exists()) {
            Config.warn("Field not found: TileEntityBedRenderer.footModel");
            return null;
        }
        if (!Reflector.TileEntityBedRenderer_legModels.exists()) {
            Config.warn("Field not found: TileEntityBedRenderer.legModels");
            return null;
        }
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntityBedRenderer_headModel, this.headPiece);
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntityBedRenderer_footModel, this.footPiece);
        Reflector.setFieldValue(tileEntityRenderer, Reflector.TileEntityBedRenderer_legModels, this.legs);
        return tileEntityRenderer;
    }
}

