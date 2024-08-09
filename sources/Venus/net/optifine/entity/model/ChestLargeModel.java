/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestLargeModel
extends Model {
    public ModelRenderer lid_left;
    public ModelRenderer base_left;
    public ModelRenderer knob_left;
    public ModelRenderer lid_right;
    public ModelRenderer base_right;
    public ModelRenderer knob_right;

    public ChestLargeModel() {
        super(RenderType::getEntityCutout);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        ChestTileEntityRenderer chestTileEntityRenderer = new ChestTileEntityRenderer(tileEntityRendererDispatcher);
        this.lid_left = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 3);
        this.base_left = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 4);
        this.knob_left = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 5);
        this.lid_right = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 6);
        this.base_right = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 7);
        this.knob_right = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 8);
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
            Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
            return null;
        }
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 3, this.lid_left);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 4, this.base_left);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 5, this.knob_left);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 6, this.lid_right);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 7, this.base_right);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 8, this.knob_right);
        return tileEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

