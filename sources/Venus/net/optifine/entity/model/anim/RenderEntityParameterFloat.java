/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.optifine.expr.IExpressionFloat;

public enum RenderEntityParameterFloat implements IExpressionFloat
{
    LIMB_SWING("limb_swing"),
    LIMB_SWING_SPEED("limb_speed"),
    AGE("age"),
    HEAD_YAW("head_yaw"),
    HEAD_PITCH("head_pitch"),
    HEALTH("health"),
    HURT_TIME("hurt_time"),
    IDLE_TIME("idle_time"),
    MAX_HEALTH("max_health"),
    MOVE_FORWARD("move_forward"),
    MOVE_STRAFING("move_strafing"),
    PARTIAL_TICKS("partial_ticks"),
    POS_X("pos_x"),
    POS_Y("pos_y"),
    POS_Z("pos_z"),
    REVENGE_TIME("revenge_time"),
    SWING_PROGRESS("swing_progress");

    private String name;
    private EntityRendererManager renderManager;
    private static final RenderEntityParameterFloat[] VALUES;

    private RenderEntityParameterFloat(String string2) {
        this.name = string2;
        this.renderManager = Minecraft.getInstance().getRenderManager();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public float eval() {
        EntityRenderer entityRenderer = this.renderManager.renderRender;
        if (entityRenderer == null) {
            return 0.0f;
        }
        if (entityRenderer instanceof LivingRenderer) {
            LivingRenderer livingRenderer = (LivingRenderer)entityRenderer;
            switch (1.$SwitchMap$net$optifine$entity$model$anim$RenderEntityParameterFloat[this.ordinal()]) {
                case 12: {
                    return livingRenderer.renderLimbSwing;
                }
                case 13: {
                    return livingRenderer.renderLimbSwingAmount;
                }
                case 14: {
                    return livingRenderer.renderAgeInTicks;
                }
                case 15: {
                    return livingRenderer.renderHeadYaw;
                }
                case 16: {
                    return livingRenderer.renderHeadPitch;
                }
            }
            LivingEntity livingEntity = livingRenderer.renderEntity;
            if (livingEntity == null) {
                return 0.0f;
            }
            switch (1.$SwitchMap$net$optifine$entity$model$anim$RenderEntityParameterFloat[this.ordinal()]) {
                case 1: {
                    return livingEntity.getHealth();
                }
                case 2: {
                    return livingEntity.hurtTime;
                }
                case 3: {
                    return livingEntity.getIdleTime();
                }
                case 4: {
                    return livingEntity.getMaxHealth();
                }
                case 5: {
                    return livingEntity.moveForward;
                }
                case 6: {
                    return livingEntity.moveStrafing;
                }
                case 7: {
                    return (float)livingEntity.getPosX();
                }
                case 8: {
                    return (float)livingEntity.getPosY();
                }
                case 9: {
                    return (float)livingEntity.getPosZ();
                }
                case 10: {
                    return livingEntity.getRevengeTimer();
                }
                case 11: {
                    return livingEntity.getSwingProgress(livingRenderer.renderPartialTicks);
                }
            }
        }
        return 0.0f;
    }

    public static RenderEntityParameterFloat parse(String string) {
        if (string == null) {
            return null;
        }
        for (int i = 0; i < VALUES.length; ++i) {
            RenderEntityParameterFloat renderEntityParameterFloat = VALUES[i];
            if (!renderEntityParameterFloat.getName().equals(string)) continue;
            return renderEntityParameterFloat;
        }
        return null;
    }

    static {
        VALUES = RenderEntityParameterFloat.values();
    }
}

