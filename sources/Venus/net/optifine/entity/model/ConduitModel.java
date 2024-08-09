/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ConduitModel
extends Model {
    public ModelRenderer eye;
    public ModelRenderer wind;
    public ModelRenderer base;
    public ModelRenderer cage;

    public ConduitModel() {
        super(RenderType::getEntityCutout);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        ConduitTileEntityRenderer conduitTileEntityRenderer = new ConduitTileEntityRenderer(tileEntityRendererDispatcher);
        this.eye = (ModelRenderer)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(conduitTileEntityRenderer, 0);
        this.wind = (ModelRenderer)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(conduitTileEntityRenderer, 1);
        this.base = (ModelRenderer)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(conduitTileEntityRenderer, 2);
        this.cage = (ModelRenderer)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(conduitTileEntityRenderer, 3);
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityConduitRenderer_modelRenderers.exists()) {
            Config.warn("Field not found: TileEntityConduitRenderer.modelRenderers");
            return null;
        }
        Reflector.TileEntityConduitRenderer_modelRenderers.setValue(tileEntityRenderer, 0, this.eye);
        Reflector.TileEntityConduitRenderer_modelRenderers.setValue(tileEntityRenderer, 1, this.wind);
        Reflector.TileEntityConduitRenderer_modelRenderers.setValue(tileEntityRenderer, 2, this.base);
        Reflector.TileEntityConduitRenderer_modelRenderers.setValue(tileEntityRenderer, 3, this.cage);
        return tileEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

