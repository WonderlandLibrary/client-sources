/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.EnderCrystalModel;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;

public class ModelAdapterEnderCrystal
extends ModelAdapter {
    public ModelAdapterEnderCrystal() {
        this("end_crystal");
    }

    protected ModelAdapterEnderCrystal(String string) {
        super(EntityType.END_CRYSTAL, string, 0.5f);
    }

    @Override
    public Model makeModel() {
        return new EnderCrystalModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof EnderCrystalModel)) {
            return null;
        }
        EnderCrystalModel enderCrystalModel = (EnderCrystalModel)model;
        if (string.equals("cube")) {
            return enderCrystalModel.cube;
        }
        if (string.equals("glass")) {
            return enderCrystalModel.glass;
        }
        return string.equals("base") ? enderCrystalModel.base : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"cube", "glass", "base"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        EntityRenderer entityRenderer = entityRendererManager.getEntityRenderMap().get(EntityType.END_CRYSTAL);
        if (!(entityRenderer instanceof EnderCrystalRenderer)) {
            Config.warn("Not an instance of RenderEnderCrystal: " + entityRenderer);
            return null;
        }
        EnderCrystalRenderer enderCrystalRenderer = (EnderCrystalRenderer)entityRenderer;
        if (enderCrystalRenderer.getType() == null) {
            enderCrystalRenderer = new EnderCrystalRenderer(entityRendererManager);
        }
        if (!(model instanceof EnderCrystalModel)) {
            Config.warn("Not a EnderCrystalModel model: " + model);
            return null;
        }
        EnderCrystalModel enderCrystalModel = (EnderCrystalModel)model;
        enderCrystalRenderer = enderCrystalModel.updateRenderer(enderCrystalRenderer);
        enderCrystalRenderer.shadowSize = f;
        return enderCrystalRenderer;
    }
}

