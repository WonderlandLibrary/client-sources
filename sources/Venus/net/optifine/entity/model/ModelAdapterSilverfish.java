/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterSilverfish
extends ModelAdapter {
    public ModelAdapterSilverfish() {
        super(EntityType.SILVERFISH, "silverfish", 0.3f);
    }

    @Override
    public Model makeModel() {
        return new SilverfishModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof SilverfishModel)) {
            return null;
        }
        SilverfishModel silverfishModel = (SilverfishModel)model;
        String string2 = "body";
        if (string.startsWith(string2)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(silverfishModel, Reflector.ModelSilverfish_bodyParts);
            if (modelRendererArray == null) {
                return null;
            }
            String string3 = string.substring(string2.length());
            int n = Config.parseInt(string3, -1);
            return --n >= 0 && n < modelRendererArray.length ? modelRendererArray[n] : null;
        }
        String string4 = "wing";
        if (string.startsWith(string4)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(silverfishModel, Reflector.ModelSilverfish_wingParts);
            if (modelRendererArray == null) {
                return null;
            }
            String string5 = string.substring(string4.length());
            int n = Config.parseInt(string5, -1);
            return --n >= 0 && n < modelRendererArray.length ? modelRendererArray[n] : null;
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        SilverfishRenderer silverfishRenderer = new SilverfishRenderer(entityRendererManager);
        silverfishRenderer.entityModel = (SilverfishModel)model;
        silverfishRenderer.shadowSize = f;
        return silverfishRenderer;
    }
}

