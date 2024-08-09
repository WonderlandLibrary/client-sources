/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelRendererUtils;

public class ModelAdapterDolphin
extends ModelAdapter {
    public ModelAdapterDolphin() {
        super(EntityType.DOLPHIN, "dolphin", 0.7f);
    }

    @Override
    public Model makeModel() {
        return new DolphinModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof DolphinModel)) {
            return null;
        }
        DolphinModel dolphinModel = (DolphinModel)model;
        Iterator<ModelRenderer> iterator2 = dolphinModel.getParts().iterator();
        ModelRenderer modelRenderer = ModelRendererUtils.getModelRenderer(iterator2, 0);
        if (modelRenderer == null) {
            return null;
        }
        if (string.equals("body")) {
            return modelRenderer;
        }
        if (string.equals("back_fin")) {
            return modelRenderer.getChild(0);
        }
        if (string.equals("left_fin")) {
            return modelRenderer.getChild(1);
        }
        if (string.equals("right_fin")) {
            return modelRenderer.getChild(2);
        }
        if (string.equals("tail")) {
            return modelRenderer.getChild(3);
        }
        if (string.equals("tail_fin")) {
            return modelRenderer.getChild(3).getChild(0);
        }
        return string.equals("head") ? modelRenderer.getChild(4) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "back_fin", "left_fin", "right_fin", "tail", "tail_fin", "head"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        DolphinRenderer dolphinRenderer = new DolphinRenderer(entityRendererManager);
        dolphinRenderer.entityModel = (DolphinModel)model;
        dolphinRenderer.shadowSize = f;
        return dolphinRenderer;
    }
}

