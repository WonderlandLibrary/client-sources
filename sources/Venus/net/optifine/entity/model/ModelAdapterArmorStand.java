/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.ArmorStandModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterBiped;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand
extends ModelAdapterBiped {
    public ModelAdapterArmorStand() {
        super(EntityType.ARMOR_STAND, "armor_stand", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ArmorStandModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ArmorStandModel)) {
            return null;
        }
        ArmorStandModel armorStandModel = (ArmorStandModel)model;
        if (string.equals("right")) {
            return (ModelRenderer)Reflector.getFieldValue(armorStandModel, Reflector.ModelArmorStand_ModelRenderers, 0);
        }
        if (string.equals("left")) {
            return (ModelRenderer)Reflector.getFieldValue(armorStandModel, Reflector.ModelArmorStand_ModelRenderers, 1);
        }
        if (string.equals("waist")) {
            return (ModelRenderer)Reflector.getFieldValue(armorStandModel, Reflector.ModelArmorStand_ModelRenderers, 2);
        }
        return string.equals("base") ? (ModelRenderer)Reflector.getFieldValue(armorStandModel, Reflector.ModelArmorStand_ModelRenderers, 3) : super.getModelRenderer(armorStandModel, string);
    }

    @Override
    public String[] getModelRendererNames() {
        Object[] objectArray = super.getModelRendererNames();
        return (String[])Config.addObjectsToArray(objectArray, new String[]{"right", "left", "waist", "base"});
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ArmorStandRenderer armorStandRenderer = new ArmorStandRenderer(entityRendererManager);
        armorStandRenderer.entityModel = (ArmorStandArmorModel)model;
        armorStandRenderer.shadowSize = f;
        return armorStandRenderer;
    }
}

