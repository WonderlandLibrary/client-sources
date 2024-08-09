/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.client.renderer.entity.model.MagmaCubeModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterMagmaCube
extends ModelAdapter {
    public ModelAdapterMagmaCube() {
        super(EntityType.MAGMA_CUBE, "magma_cube", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new MagmaCubeModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof MagmaCubeModel)) {
            return null;
        }
        MagmaCubeModel magmaCubeModel = (MagmaCubeModel)model;
        if (string.equals("core")) {
            return (ModelRenderer)Reflector.getFieldValue(magmaCubeModel, Reflector.ModelMagmaCube_core);
        }
        String string2 = "segment";
        if (string.startsWith(string2)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(magmaCubeModel, Reflector.ModelMagmaCube_segments);
            if (modelRendererArray == null) {
                return null;
            }
            String string3 = string.substring(string2.length());
            int n = Config.parseInt(string3, -1);
            return --n >= 0 && n < modelRendererArray.length ? modelRendererArray[n] : null;
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        MagmaCubeRenderer magmaCubeRenderer = new MagmaCubeRenderer(entityRendererManager);
        magmaCubeRenderer.entityModel = (MagmaCubeModel)model;
        magmaCubeRenderer.shadowSize = f;
        return magmaCubeRenderer;
    }
}

