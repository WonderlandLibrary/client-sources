/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BellModel
extends Model {
    public ModelRenderer bellBody;

    public BellModel() {
        super(RenderType::getEntityCutoutNoCull);
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        BellTileEntityRenderer bellTileEntityRenderer = new BellTileEntityRenderer(tileEntityRendererDispatcher);
        this.bellBody = (ModelRenderer)Reflector.TileEntityBellRenderer_modelRenderer.getValue(bellTileEntityRenderer);
    }

    public TileEntityRenderer updateRenderer(TileEntityRenderer tileEntityRenderer) {
        if (!Reflector.TileEntityBellRenderer_modelRenderer.exists()) {
            Config.warn("Field not found: TileEntityBellRenderer.modelRenderer");
            return null;
        }
        Reflector.TileEntityBellRenderer_modelRenderer.setValue(tileEntityRenderer, this.bellBody);
        return tileEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

