/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.optifine.expr.IExpressionBool;

public enum RenderEntityParameterBool implements IExpressionBool
{
    IS_ALIVE("is_alive"),
    IS_BURNING("is_burning"),
    IS_CHILD("is_child"),
    IS_GLOWING("is_glowing"),
    IS_HURT("is_hurt"),
    IS_IN_LAVA("is_in_lava"),
    IS_IN_WATER("is_in_water"),
    IS_INVISIBLE("is_invisible"),
    IS_ON_GROUND("is_on_ground"),
    IS_RIDDEN("is_ridden"),
    IS_RIDING("is_riding"),
    IS_SNEAKING("is_sneaking"),
    IS_SPRINTING("is_sprinting"),
    IS_WET("is_wet");

    private String name;
    private EntityRendererManager renderManager;
    private static final RenderEntityParameterBool[] VALUES;

    private RenderEntityParameterBool(String string2) {
        this.name = string2;
        this.renderManager = Minecraft.getInstance().getRenderManager();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean eval() {
        EntityRenderer entityRenderer = this.renderManager.renderRender;
        if (entityRenderer == null) {
            return true;
        }
        if (entityRenderer instanceof LivingRenderer) {
            LivingRenderer livingRenderer = (LivingRenderer)entityRenderer;
            LivingEntity livingEntity = livingRenderer.renderEntity;
            if (livingEntity == null) {
                return true;
            }
            switch (1.$SwitchMap$net$optifine$entity$model$anim$RenderEntityParameterBool[this.ordinal()]) {
                case 1: {
                    return livingEntity.isAlive();
                }
                case 2: {
                    return livingEntity.isBurning();
                }
                case 3: {
                    return livingEntity.isChild();
                }
                case 4: {
                    return livingEntity.isGlowing();
                }
                case 5: {
                    return livingEntity.hurtTime > 0;
                }
                case 6: {
                    return livingEntity.isInLava();
                }
                case 7: {
                    return livingEntity.isInWater();
                }
                case 8: {
                    return livingEntity.isInvisible();
                }
                case 9: {
                    return livingEntity.isOnGround();
                }
                case 10: {
                    return livingEntity.isBeingRidden();
                }
                case 11: {
                    return livingEntity.isPassenger();
                }
                case 12: {
                    return livingEntity.isCrouching();
                }
                case 13: {
                    return livingEntity.isSprinting();
                }
                case 14: {
                    return livingEntity.isWet();
                }
            }
        }
        return true;
    }

    public static RenderEntityParameterBool parse(String string) {
        if (string == null) {
            return null;
        }
        for (int i = 0; i < VALUES.length; ++i) {
            RenderEntityParameterBool renderEntityParameterBool = VALUES[i];
            if (!renderEntityParameterBool.getName().equals(string)) continue;
            return renderEntityParameterBool;
        }
        return null;
    }

    static {
        VALUES = RenderEntityParameterBool.values();
    }
}

