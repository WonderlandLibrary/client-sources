package white.floor.features.impl.movement;

import java.util.ArrayList;


import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import clickgui.setting.*;

import net.minecraft.util.text.TextFormatting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdateLiving;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.movement.MovementHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;
import white.floor.helpers.world.InventoryHelper;

public class Flight extends Feature {

    public Flight() {
        super("Flight", "Your bird :)", 0, Category.MOVEMENT);
        ArrayList<String> fly = new ArrayList<String>();
        fly.add("Motion");
        fly.add("Glide");
        fly.add("Sunrise Drop");
        Main.instance.settingsManager.rSetting(new Setting("Flight Mode", this, "Motion", fly));
        Main.instance.settingsManager.rSetting(new Setting("Fly Speed", this, 0.5F, 0.1F, 10.0F, false));
        Main.instance.settingsManager.rSetting(new Setting("UltraSpeed", this, false));

    }

    @EventTarget
    public void onUpdate(EventUpdateLiving e) {
        String mode = Main.instance.settingsManager.getSettingByName("Flight Mode").getValString();
        this.setModuleName(this.name + TextFormatting.GRAY + " [" + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Flight.class), "Fly Speed").getValFloat() + "]");
        if (mode.equalsIgnoreCase("Motion")) {
            if (mc.player.onGround) {
                mc.player.jump();
            } else {
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
                mc.player.motionY = 0;
                mc.player.capabilities.isFlying = true;
                mc.player.capabilities.setFlySpeed(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Flight.class), "Fly Speed").getValFloat());
                mc.player.speedInAir = 0.3f;
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY -= 0.6;
                } else if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionY += 0.6;
                }
            }
        }
        if (mode.equalsIgnoreCase("Glide")) {
            if (mc.player.onGround) {
                mc.player.jump();
                timerHelper.resetwatermark();
            } else if (!mc.player.onGround && timerHelper.check(280)) {
                mc.player.motionY = -0.01;
                mc.player.capabilities.isFlying = true;
                mc.player.capabilities.flySpeed = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(Flight.class), "Fly Speed").getValFloat() / 10;
            }
        }
        if (mode.equalsIgnoreCase("Sunrise Drop")) {
            if (!mc.player.onGround) {
                if (InventoryHelper.hasAnyBlock()) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryHelper.getAnyBlockInHotbar()));

                    mc.player.speedInAir = 0.012F;

                    if (timerHelper.hasReached(100)) {
                        mc.player.jump();
                        timerHelper.reset();
                    }

                    if (timerHelper.check(100)) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        timerHelper.resetwatermark();
                    }
                }
                if (!InventoryHelper.hasAnyBlock()) {
                    return;
                }
            }
        }
    }


    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.speedInAir = 0.02f;
        mc.timer.timerSpeed = 1.0f;
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(0.05F);
    }
}