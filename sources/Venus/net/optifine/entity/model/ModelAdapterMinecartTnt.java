/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.reflect.Reflector;

public class ModelAdapterMinecartTnt
extends ModelAdapterMinecart {
    public ModelAdapterMinecartTnt() {
        super(EntityType.TNT_MINECART, "tnt_minecart", 0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        TNTMinecartRenderer tNTMinecartRenderer = new TNTMinecartRenderer(entityRendererManager);
        if (!Reflector.RenderMinecart_modelMinecart.exists()) {
            Config.warn("Field not found: RenderMinecart.modelMinecart");
            return null;
        }
        Reflector.setFieldValue(tNTMinecartRenderer, Reflector.RenderMinecart_modelMinecart, model);
        tNTMinecartRenderer.shadowSize = f;
        return tNTMinecartRenderer;
    }
}

