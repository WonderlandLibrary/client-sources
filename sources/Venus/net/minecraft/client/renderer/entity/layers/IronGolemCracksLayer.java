/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;

public class IronGolemCracksLayer
extends LayerRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
    private static final Map<IronGolemEntity.Cracks, ResourceLocation> field_229134_a_ = ImmutableMap.of(IronGolemEntity.Cracks.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), IronGolemEntity.Cracks.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), IronGolemEntity.Cracks.HIGH, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

    public IronGolemCracksLayer(IEntityRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, IronGolemEntity ironGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        IronGolemEntity.Cracks cracks;
        if (!ironGolemEntity.isInvisible() && (cracks = ironGolemEntity.func_226512_l_()) != IronGolemEntity.Cracks.NONE) {
            ResourceLocation resourceLocation = field_229134_a_.get((Object)cracks);
            IronGolemCracksLayer.renderCutoutModel(this.getEntityModel(), resourceLocation, matrixStack, iRenderTypeBuffer, n, ironGolemEntity, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (IronGolemEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

