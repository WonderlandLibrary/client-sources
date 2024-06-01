package com.polarware.component.impl.render.espcomponent.api;

import com.polarware.module.impl.combat.KillAuraModule;
import com.polarware.module.impl.combat.TeleportAuraModule;
import com.polarware.util.interfaces.InstanceAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

public class  ESP implements InstanceAccess {
    public ESPColor espColor;
    public Entity target;
    public int tick;

    public ESP(ESPColor espColor) {
        this.espColor = espColor;
        tick = mc.thePlayer.ticksExisted;
    }

    public void render2D() {

    }

    public void render3D() {

    }

    public void updateTargets() {
        target = getModule(KillAuraModule.class).target;
        TeleportAuraModule teleportAura = getModule(TeleportAuraModule.class);
        if (teleportAura.isEnabled()) target = getModule(TeleportAuraModule.class).target;
    }

    public Color getColor(EntityLivingBase entity) {
        Color color = espColor.getNormalColor();

        if (entity == null) return color;

        if (entity.hurtTime > 0) {
            color = espColor.getDamageColor();
        } else if (target == entity) {
            color = espColor.getTargetColor();
        }

        return color;
    }
}
