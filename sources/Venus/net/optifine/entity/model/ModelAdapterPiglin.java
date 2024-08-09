/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.model.PiglinModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;

public class ModelAdapterPiglin
extends ModelAdapterBiped {
    public ModelAdapterPiglin() {
        super(EntityType.PIGLIN, "piglin", 0.5f);
    }

    protected ModelAdapterPiglin(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new PiglinModel(0.0f, 64, 64);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (model instanceof PiglinModel) {
            PiglinModel piglinModel = (PiglinModel)model;
            if (string.equals("left_ear")) {
                return piglinModel.field_239115_a_;
            }
            if (string.equals("right_ear")) {
                return piglinModel.field_239116_b_;
            }
        }
        return super.getModelRenderer(model, string);
    }

    @Override
    public String[] getModelRendererNames() {
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(super.getModelRendererNames()));
        arrayList.add("left_ear");
        arrayList.add("right_ear");
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        PiglinRenderer piglinRenderer = new PiglinRenderer(entityRendererManager, false);
        piglinRenderer.entityModel = (PiglinModel)model;
        piglinRenderer.shadowSize = f;
        return piglinRenderer;
    }
}

