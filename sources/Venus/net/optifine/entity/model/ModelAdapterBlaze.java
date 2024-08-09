/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BlazeModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterBlaze
extends ModelAdapter {
    public ModelAdapterBlaze() {
        super(EntityType.BLAZE, "blaze", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new BlazeModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof BlazeModel)) {
            return null;
        }
        BlazeModel blazeModel = (BlazeModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.getFieldValue(blazeModel, Reflector.ModelBlaze_blazeHead);
        }
        String string2 = "stick";
        if (string.startsWith(string2)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(blazeModel, Reflector.ModelBlaze_blazeSticks);
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
        return new String[]{"head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", "stick10", "stick11", "stick12"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        BlazeRenderer blazeRenderer = new BlazeRenderer(entityRendererManager);
        blazeRenderer.entityModel = (BlazeModel)model;
        blazeRenderer.shadowSize = f;
        return blazeRenderer;
    }
}

