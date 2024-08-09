/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.HorseArmorChestsModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.reflect.Reflector;

public class ModelAdapterDonkey
extends ModelAdapterHorse {
    public ModelAdapterDonkey() {
        super(EntityType.DONKEY, "donkey", 0.75f);
    }

    @Override
    public Model makeModel() {
        return new HorseArmorChestsModel(0.87f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof HorseArmorChestsModel)) {
            return null;
        }
        HorseArmorChestsModel horseArmorChestsModel = (HorseArmorChestsModel)model;
        if (string.equals("left_chest")) {
            return (ModelRenderer)Reflector.ModelHorseChests_ModelRenderers.getValue(horseArmorChestsModel, 0);
        }
        return string.equals("right_chest") ? (ModelRenderer)Reflector.ModelHorseChests_ModelRenderers.getValue(horseArmorChestsModel, 1) : super.getModelRenderer(model, string);
    }

    @Override
    public String[] getModelRendererNames() {
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(super.getModelRendererNames()));
        arrayList.add("left_chest");
        arrayList.add("right_chest");
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ChestedHorseRenderer chestedHorseRenderer = new ChestedHorseRenderer(entityRendererManager, 0.87f);
        chestedHorseRenderer.entityModel = (EntityModel)model;
        chestedHorseRenderer.shadowSize = f;
        return chestedHorseRenderer;
    }
}

