package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.event.render.EventOverlay;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;

import java.util.Arrays;

@ModuleAnnotation(name = "NoRender", category = Category.RENDER)
public class NoRender extends Module {
    public static MultiBooleanSetting element = new MultiBooleanSetting("Elements",
            Arrays.asList("Fire", "Boss Bar", "Scoreboard", "Totem", "Armor Stand",
                    "Bad Effects", "Skylight", "Explosions", "Particles", "Exp Orbs",
                    "Hurt Cam", "Weather", "Armor", "Camera Smooth"));

    @EventTarget
    public void onOverlayRender(EventOverlay event) {
        if (element.get(0) && event.getOverlayType().equals(EventOverlay.OverlayType.FIRE) ||
                element.get(1) && event.getOverlayType().equals(EventOverlay.OverlayType.BOSS_BAR) ||
                element.get(2) && event.getOverlayType().equals(EventOverlay.OverlayType.SCOREBOARD) ||
                element.get(3) && event.getOverlayType().equals(EventOverlay.OverlayType.TOTEM_ANIMATION) ||
                element.get(11) && event.getOverlayType().equals(EventOverlay.OverlayType.WEATHER) ||
                element.get(6) && event.getOverlayType().equals(EventOverlay.OverlayType.SKYLIGHT) ||
                element.get(10) && event.getOverlayType().equals(EventOverlay.OverlayType.HURT_CAM) ||
                element.get(12) && event.getOverlayType().equals(EventOverlay.OverlayType.ARMOR) ||
                element.get(8) && event.getOverlayType().equals(EventOverlay.OverlayType.PARTICLES)) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (element.get(11)) {
            mc.world.setRainStrength(0);
            mc.world.setThunderStrength(0);
        }

        if (element.get(5) && mc.player.isPotionActive(MobEffects.BLINDNESS) || mc.player.isPotionActive(MobEffects.NAUSEA)) {
            mc.player.removePotionEffect(MobEffects.NAUSEA);
            mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (element.get(13)) {
            NoRender.mc.gameSettings.smoothCamera = false;
        }
        if (element.get(4)) {
            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (!(entity instanceof EntityArmorStand)) continue;
                mc.world.removeEntity(entity);
            }
        }
    }

    @EventTarget
    public void onPacketReceive(EventReceivePacket event) {
        if (element.get(7) && event.getPacket() instanceof SPacketExplosion ||
                element.get(9) && event.getPacket() instanceof SPacketSpawnExperienceOrb) {
            event.setCancelled(true);
        }
    }
}
