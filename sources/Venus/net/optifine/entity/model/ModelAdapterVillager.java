/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.resources.IReloadableResourceManager;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterVillager
extends ModelAdapter {
    public ModelAdapterVillager() {
        super(EntityType.VILLAGER, "villager", 0.5f);
    }

    protected ModelAdapterVillager(EntityType entityType, String string, float f) {
        super(entityType, string, f);
    }

    @Override
    public Model makeModel() {
        return new VillagerModel(0.0f);
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof VillagerModel)) {
            return null;
        }
        VillagerModel villagerModel = (VillagerModel)model;
        if (string.equals("head")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 0);
        }
        if (string.equals("headwear")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 1);
        }
        if (string.equals("headwear2")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 2);
        }
        if (string.equals("body")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 3);
        }
        if (string.equals("bodywear")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 4);
        }
        if (string.equals("arms")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 5);
        }
        if (string.equals("right_leg")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 6);
        }
        if (string.equals("left_leg")) {
            return (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 7);
        }
        return string.equals("nose") ? (ModelRenderer)Reflector.ModelVillager_ModelRenderers.getValue(villagerModel, 8) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"head", "headwear", "headwear2", "body", "bodywear", "arms", "right_leg", "left_leg", "nose"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        IReloadableResourceManager iReloadableResourceManager = (IReloadableResourceManager)Minecraft.getInstance().getResourceManager();
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        VillagerRenderer villagerRenderer = new VillagerRenderer(entityRendererManager, iReloadableResourceManager);
        villagerRenderer.entityModel = (VillagerModel)model;
        villagerRenderer.shadowSize = f;
        return villagerRenderer;
    }
}

