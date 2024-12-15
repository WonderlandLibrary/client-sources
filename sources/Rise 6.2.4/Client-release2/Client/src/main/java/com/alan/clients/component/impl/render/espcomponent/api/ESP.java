package com.alan.clients.component.impl.render.espcomponent.api;

import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.combat.TeleportAura;
import com.alan.clients.util.Accessor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class ESP implements Accessor {
    public ESPColor espColor;
    public Entity target;
    public int tick;
    public static KillAura killAura;
    public static TeleportAura teleportAura;

    public ESP(ESPColor espColor) {
        this.espColor = espColor;
        tick = mc.thePlayer.ticksExisted;
    }

    public void render2D() {
    }

    public void render3D() {
    }

    public void updatePlayerAngles(EntityPlayer entityPlayer, ModelBiped modelBiped) {
    }

    public void updateTargets() {
        if (killAura == null) killAura = getModule(KillAura.class);
        if (teleportAura == null) teleportAura = getModule(TeleportAura.class);

        if (killAura.target != null) {
            target = killAura.target;
        } else if (teleportAura.target != null) {
            target = teleportAura.target;
        } else {
            target = null;
        }
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
