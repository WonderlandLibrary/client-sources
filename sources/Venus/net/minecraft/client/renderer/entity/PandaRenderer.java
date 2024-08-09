/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PandaHeldItemLayer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class PandaRenderer
extends MobRenderer<PandaEntity, PandaModel<PandaEntity>> {
    private static final Map<PandaEntity.Gene, ResourceLocation> field_217777_a = Util.make(Maps.newEnumMap(PandaEntity.Gene.class), PandaRenderer::lambda$static$0);

    public PandaRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new PandaModel(9, 0.0f), 0.9f);
        this.addLayer(new PandaHeldItemLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(PandaEntity pandaEntity) {
        return field_217777_a.getOrDefault((Object)pandaEntity.func_213590_ei(), field_217777_a.get((Object)PandaEntity.Gene.NORMAL));
    }

    @Override
    protected void applyRotations(PandaEntity pandaEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        float f4;
        float f5;
        float f6;
        super.applyRotations(pandaEntity, matrixStack, f, f2, f3);
        if (pandaEntity.rollCounter > 0) {
            float f7;
            int n = pandaEntity.rollCounter;
            int n2 = n + 1;
            f6 = 7.0f;
            float f8 = f7 = pandaEntity.isChild() ? 0.3f : 0.8f;
            if (n < 8) {
                float f9 = (float)(90 * n) / 7.0f;
                float f10 = (float)(90 * n2) / 7.0f;
                float f11 = this.func_217775_a(f9, f10, n2, f3, 8.0f);
                matrixStack.translate(0.0, (f7 + 0.2f) * (f11 / 90.0f), 0.0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-f11));
            } else if (n < 16) {
                float f12 = ((float)n - 8.0f) / 7.0f;
                float f13 = 90.0f + 90.0f * f12;
                float f14 = 90.0f + 90.0f * ((float)n2 - 8.0f) / 7.0f;
                float f15 = this.func_217775_a(f13, f14, n2, f3, 16.0f);
                matrixStack.translate(0.0, f7 + 0.2f + (f7 - 0.2f) * (f15 - 90.0f) / 90.0f, 0.0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-f15));
            } else if ((float)n < 24.0f) {
                float f16 = ((float)n - 16.0f) / 7.0f;
                float f17 = 180.0f + 90.0f * f16;
                float f18 = 180.0f + 90.0f * ((float)n2 - 16.0f) / 7.0f;
                float f19 = this.func_217775_a(f17, f18, n2, f3, 24.0f);
                matrixStack.translate(0.0, f7 + f7 * (270.0f - f19) / 90.0f, 0.0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-f19));
            } else if (n < 32) {
                float f20 = ((float)n - 24.0f) / 7.0f;
                float f21 = 270.0f + 90.0f * f20;
                float f22 = 270.0f + 90.0f * ((float)n2 - 24.0f) / 7.0f;
                float f23 = this.func_217775_a(f21, f22, n2, f3, 32.0f);
                matrixStack.translate(0.0, f7 * ((360.0f - f23) / 90.0f), 0.0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-f23));
            }
        }
        if ((f5 = pandaEntity.func_213561_v(f3)) > 0.0f) {
            matrixStack.translate(0.0, 0.8f * f5, 0.0);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(f5, pandaEntity.rotationPitch, pandaEntity.rotationPitch + 90.0f)));
            matrixStack.translate(0.0, -1.0f * f5, 0.0);
            if (pandaEntity.func_213566_eo()) {
                float f24 = (float)(Math.cos((double)pandaEntity.ticksExisted * 1.25) * Math.PI * (double)0.05f);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(f24));
                if (pandaEntity.isChild()) {
                    matrixStack.translate(0.0, 0.8f, 0.55f);
                }
            }
        }
        if ((f4 = pandaEntity.func_213583_w(f3)) > 0.0f) {
            f6 = pandaEntity.isChild() ? 0.5f : 1.3f;
            matrixStack.translate(0.0, f6 * f4, 0.0);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(f4, pandaEntity.rotationPitch, pandaEntity.rotationPitch + 180.0f)));
        }
    }

    private float func_217775_a(float f, float f2, int n, float f3, float f4) {
        return (float)n < f4 ? MathHelper.lerp(f3, f, f2) : f;
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((PandaEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PandaEntity)entity2);
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(PandaEntity.Gene.NORMAL, new ResourceLocation("textures/entity/panda/panda.png"));
        enumMap.put(PandaEntity.Gene.LAZY, new ResourceLocation("textures/entity/panda/lazy_panda.png"));
        enumMap.put(PandaEntity.Gene.WORRIED, new ResourceLocation("textures/entity/panda/worried_panda.png"));
        enumMap.put(PandaEntity.Gene.PLAYFUL, new ResourceLocation("textures/entity/panda/playful_panda.png"));
        enumMap.put(PandaEntity.Gene.BROWN, new ResourceLocation("textures/entity/panda/brown_panda.png"));
        enumMap.put(PandaEntity.Gene.WEAK, new ResourceLocation("textures/entity/panda/weak_panda.png"));
        enumMap.put(PandaEntity.Gene.AGGRESSIVE, new ResourceLocation("textures/entity/panda/aggressive_panda.png"));
    }
}

