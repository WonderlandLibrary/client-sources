/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.client.renderer.entity.model.WitchModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitch
extends ModelAdapterVillager {
    public ModelAdapterWitch() {
        super(EntityType.WITCH, "witch", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new WitchModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof WitchModel)) {
            return null;
        }
        WitchModel witchModel = (WitchModel)model;
        return string.equals("mole") ? (ModelRenderer)Reflector.getFieldValue(witchModel, Reflector.ModelWitch_mole) : super.getModelRenderer(witchModel, string);
    }

    @Override
    public String[] getModelRendererNames() {
        Object[] objectArray = super.getModelRendererNames();
        return (String[])Config.addObjectToArray(objectArray, "mole");
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        WitchRenderer witchRenderer = new WitchRenderer(entityRendererManager);
        witchRenderer.entityModel = (WitchModel)model;
        witchRenderer.shadowSize = f;
        return witchRenderer;
    }
}

