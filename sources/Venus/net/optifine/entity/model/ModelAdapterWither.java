/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherRenderer;
import net.minecraft.client.renderer.entity.model.WitherModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterWither
extends ModelAdapter {
    public ModelAdapterWither() {
        super(EntityType.WITHER, "wither", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new WitherModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof WitherModel)) {
            return null;
        }
        WitherModel witherModel = (WitherModel)model;
        String string2 = "body";
        if (string.startsWith(string2)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(witherModel, Reflector.ModelWither_bodyParts);
            if (modelRendererArray == null) {
                return null;
            }
            String string3 = string.substring(string2.length());
            int n = Config.parseInt(string3, -1);
            return --n >= 0 && n < modelRendererArray.length ? modelRendererArray[n] : null;
        }
        String string4 = "head";
        if (string.startsWith(string4)) {
            ModelRenderer[] modelRendererArray = (ModelRenderer[])Reflector.getFieldValue(witherModel, Reflector.ModelWither_heads);
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
        return new String[]{"body1", "body2", "body3", "head1", "head2", "head3"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WitherRenderer witherRenderer = new WitherRenderer(entityRendererManager);
        witherRenderer.entityModel = (WitherModel)model;
        witherRenderer.shadowSize = f;
        return witherRenderer;
    }
}

