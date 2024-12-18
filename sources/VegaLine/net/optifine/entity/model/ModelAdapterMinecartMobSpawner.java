/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterMinecart;
import optifine.Config;
import optifine.Reflector;

public class ModelAdapterMinecartMobSpawner
extends ModelAdapterMinecart {
    public ModelAdapterMinecartMobSpawner() {
        super(EntityMinecartMobSpawner.class, "spawner_minecart", 0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        RenderMinecartMobSpawner renderminecartmobspawner = new RenderMinecartMobSpawner(rendermanager);
        if (!Reflector.RenderMinecart_modelMinecart.exists()) {
            Config.warn("Field not found: RenderMinecart.modelMinecart");
            return null;
        }
        Reflector.setFieldValue(renderminecartmobspawner, Reflector.RenderMinecart_modelMinecart, modelBase);
        renderminecartmobspawner.shadowSize = shadowSize;
        return renderminecartmobspawner;
    }
}

