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

public class ChestModel
extends Model {
    public ModelRenderer lid;
    public ModelRenderer base;
    public ModelRenderer knob;

    public ChestModel() {
        super(RenderType::getEntityCutout);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        ChestTileEntityRenderer chestTileEntityRenderer = new ChestTileEntityRenderer(tileEntityRendererDispatcher);
        this.lid = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 0);
        this.base = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 1);
        this.knob = (ModelRenderer)Reflector.TileEntityChestRenderer_modelRenderers.getValue(chestTileEntityRenderer, 2);
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
            Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
            return null;
        }
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 0, this.lid);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 1, this.base);
        Reflector.TileEntityChestRenderer_modelRenderers.setValue(tileEntityRenderer, 2, this.knob);
        return tileEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

