package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.mod.ModCategory;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.AxisAlignedBB;

public class Flight
extends Mod {
    public ValueDouble speed = new ValueDouble("Speed", 3.0, 1.0, 5.0, 1);

    public Flight() {
        super("Flight", 33, ModCategory.PLAYER);
        this.addValue(this.speed);
    }

    @Override
    public void onEnable() {
        if (Flight.mc.thePlayer.onGround) {
            Flight.mc.thePlayer.boundingBox.offset(0.0, 1.0, 0.0);
            Flight.mc.thePlayer.onGround = true;
        }
    }

    @EventTarget
    public void onPreUpdate(EventPreUpdate event) {
        float s = (float)Math.abs(this.speed.getValue() - this.speed.getMax());
        s = s < 1.0f ? 1.0f : s;
        Flight.mc.thePlayer.capabilities.allowFlying = false;
        Flight.mc.thePlayer.motionY = 0.0;
        Flight.mc.thePlayer.motionX = 0.0;
        Flight.mc.thePlayer.motionZ = 0.0;
        Flight.mc.thePlayer.jumpMovementFactor = 2.0f / s;
        Flight.mc.thePlayer.landMovementFactor = 2.0f / s;
        if (Flight.mc.gameSettings.keyBindJump.pressed && Flight.mc.inGameHasFocus) {
            Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.isOnLadder() ? (Flight.mc.thePlayer.motionY += 0.28) : (Flight.mc.thePlayer.motionY += (double)(2.0f / s));
        } else if (Flight.mc.gameSettings.keyBindSneak.pressed && Flight.mc.inGameHasFocus) {
            Flight.mc.thePlayer.motionY -= (double)(2.0f / s);
        }
    }
}

