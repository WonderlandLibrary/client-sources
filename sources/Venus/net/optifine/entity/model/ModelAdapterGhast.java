/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelRendererUtils;

public class ModelAdapterGhast
extends ModelAdapter {
    public ModelAdapterGhast() {
        super(EntityType.GHAST, "ghast", 0.5f);
    }

    @Override
    public Model makeModel() {
        return new GhastModel();
    }

    @Override
    public ModelRenderer getModelRenderer(Model model, String string) {
        if (!(model instanceof GhastModel)) {
            return null;
        }
        GhastModel ghastModel = (GhastModel)model;
        Iterator<ModelRenderer> iterator2 = ghastModel.getParts().iterator();
        if (string.equals("body")) {
            return ModelRendererUtils.getModelRenderer(iterator2, 0);
        }
        String string2 = "tentacle";
        if (string.startsWith(string2)) {
            String string3 = string.substring(string2.length());
            int n = Config.parseInt(string3, -1);
            return ModelRendererUtils.getModelRenderer(iterator2, n);
        }
        return null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9"};
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        GhastRenderer ghastRenderer = new GhastRenderer(entityRendererManager);
        ghastRenderer.entityModel = (GhastModel)model;
        ghastRenderer.shadowSize = f;
        return ghastRenderer;
    }
}

