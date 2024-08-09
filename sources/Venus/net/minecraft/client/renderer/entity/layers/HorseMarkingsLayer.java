/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.CoatTypes;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class HorseMarkingsLayer
extends LayerRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private static final Map<CoatTypes, ResourceLocation> field_239405_a_ = Util.make(Maps.newEnumMap(CoatTypes.class), HorseMarkingsLayer::lambda$static$0);

    public HorseMarkingsLayer(IEntityRenderer<HorseEntity, HorseModel<HorseEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, HorseEntity horseEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ResourceLocation resourceLocation = field_239405_a_.get((Object)horseEntity.func_234240_eM_());
        if (resourceLocation != null && !horseEntity.isInvisible()) {
            IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityTranslucent(resourceLocation));
            ((HorseModel)this.getEntityModel()).render(matrixStack, iVertexBuilder, n, LivingRenderer.getPackedOverlay(horseEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (HorseEntity)entity2, f, f2, f3, f4, f5, f6);
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(CoatTypes.NONE, null);
        enumMap.put(CoatTypes.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
        enumMap.put(CoatTypes.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
        enumMap.put(CoatTypes.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
        enumMap.put(CoatTypes.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
    }
}

