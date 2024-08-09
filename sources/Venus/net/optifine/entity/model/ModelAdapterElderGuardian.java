/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.EntityType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapterGuardian;

public class ModelAdapterElderGuardian
extends ModelAdapterGuardian {
    public ModelAdapterElderGuardian() {
        super(EntityType.ELDER_GUARDIAN, "elder_guardian", 0.5f);
    }

    @Override
    public IEntityRenderer makeEntityRender(Model model, float f) {
        EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        ElderGuardianRenderer elderGuardianRenderer = new ElderGuardianRenderer(entityRendererManager);
        elderGuardianRenderer.entityModel = (GuardianModel)model;
        elderGuardianRenderer.shadowSize = f;
        return elderGuardianRenderer;
    }
}

