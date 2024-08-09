/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerBulletModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet
extends ModelAdapter {
    public ModelAdapterShulkerBullet() {
        super(EntityType.SHULKER_BULLET, "shulker_bullet", 0.0f);
    }

    @Override
    public Model makeModel() {
        return new ShulkerBulletModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof ShulkerBulletModel)) {
            return null;
        }
        ShulkerBulletModel shulkerBulletModel = (ShulkerBulletModel)model;
        return string.equals("bullet") ? (ModelRenderer)Reflector.ModelShulkerBullet_renderer.getValue(shulkerBulletModel) : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"bullet"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ShulkerBulletRenderer shulkerBulletRenderer = new ShulkerBulletRenderer(entityRendererManager);
        if (!Reflector.RenderShulkerBullet_model.exists()) {
            Config.warn("Field not found: RenderShulkerBullet.model");
            return null;
        }
        Reflector.setFieldValue(shulkerBulletRenderer, Reflector.RenderShulkerBullet_model, model);
        shulkerBulletRenderer.shadowSize = f;
        return shulkerBulletRenderer;
    }
}

