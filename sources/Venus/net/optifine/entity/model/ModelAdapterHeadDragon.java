/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.Map;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.model.DragonHeadModel;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon
extends ModelAdapter {
    public ModelAdapterHeadDragon() {
        super(TileEntityType.SKULL, "head_dragon", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new DragonHeadModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof DragonHeadModel)) {
            return null;
        }
        DragonHeadModel dragonHeadModel = (DragonHeadModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.getFieldValue(dragonHeadModel, Reflector.ModelDragonHead_head);
        }
        return string.equals("jaw") ? (ModelRenderer)Reflector.getFieldValue(dragonHeadModel, Reflector.ModelDragonHead_jaw) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "jaw"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        Map map;
        TileEntityRendererDispatcher tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
        TileEntityRenderer tileEntityRenderer = tileEntityRendererDispatcher.getRenderer(TileEntityType.SKULL);
        if (!(tileEntityRenderer instanceof SkullTileEntityRenderer)) {
            return null;
        }
        if (tileEntityRenderer.getType() == null) {
            tileEntityRenderer = new SkullTileEntityRenderer(tileEntityRendererDispatcher);
        }
        if ((map = (Map)Reflector.TileEntitySkullRenderer_MODELS.getValue()) == null) {
            Config.warn("Field not found: TileEntitySkullRenderer.MODELS");
            return null;
        }
        map.put(SkullBlock.Types.DRAGON, model);
        return tileEntityRenderer;
    }
}

