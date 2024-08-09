/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.events.entity.player.JumpVelocityMultiplierEvent;
import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public final class VulcantFDP extends SpeedMode {

    public VulcantFDP() {
        super(SpeedModes.VulcantFDP);
    }

    boolean heheJump = false;
    double timer = 1;
    private boolean jumped = false;
    private int jumpCount = 0;
    private double yMotion = 0.0;

    @Override
    public void onActivate() {
        timer = 1;
    }

    @Override
    public void onDeactivate() {
        MoveUtils.resetMotionXZ();
        super.onDeactivate();
        timer = 1;
    }
    @Override
    public void onTick() {
        Modules.get().get(Timer.class).setOverride(timer);
    }

    // Assuming the original code used Minecraft Forge
// The Fabric API might provide different classes and methods

// Import statements might change based on the specific Fabric API version you're using

// Original import
// import net.minecraft.client.entity.EntityPlayerSP;
// import net.minecraft.network.play.client.C03PacketPlayer;

// Fabric import


    @EventHandler
    private void onJumpVelocityMultiplier(final JumpVelocityMultiplierEvent event) {
        if (heheJump)
            event.multiplier = 0.025f;
    }

        @EventHandler
        public void onUpdate(final SendMovementPacketsEvent event) {
            if (jumped) {
                ClientPlayerEntity player = mc.player;
                if (player != null) {
                    player.setVelocity(player.getVelocity().x, -0.1, player.getVelocity().z);
                    player.setOnGround(false);
                    jumped = false;
                    yMotion = 0.0;
                }
            }

            // Adjustments might be needed for newer versions
            mc.options.jumpKey.setPressed(false);
            mc.player.getJumpBoostVelocityModifier();
            heheJump = true;
            //

            ClientPlayerEntity player = mc.player;
            if (player != null && player.isOnGround() && MoveUtils.isMoving()) {
                if (player.isTouchingWater() || mc.options.jumpKey.isPressed()) {
                    if (!mc.options.jumpKey.isPressed()) {
                        player.jump();
                    }
                    return;
                }
                player.jump();
                player.setVelocity(player.getVelocity().x, 0.0, player.getVelocity().z);
                yMotion = 0.1 + Math.random() * 0.03;
                MoveUtils.strafe(0.48f + jumpCount * 0.001f);
                jumpCount++;
                jumped = true;
            } else if (MoveUtils.isMoving()) {
                MoveUtils.strafe(0.27f + jumpCount * 0.0018f);
            } else {
                MoveUtils.resetMotionXYZ();
            }
        }


       @EventHandler
        public void onPacket(final PacketEvent.Receive event) {
            Packet packet = event.packet;
            if (packet instanceof PlayerMoveC2SPacket p) {
                p.getY(yMotion);
            }
        }

        @EventHandler
        public void onMove(final PlayerMoveEvent event) {
            if ((jumpCount >= settings.boostDelayValue.get()) && settings.boostSpeedValue.get()) {
                event.setX(event.getX() * 1.7181145141919810);
                event.setZ(event.getZ() * 1.7181145141919810);
                jumpCount = 0;
            } else if (!settings.boostSpeedValue.get()) {
                jumpCount = 4;
            }
        }

    private boolean hasSpeed() {
        for (StatusEffectInstance statusEffectInstance : mc.player.getStatusEffects()) {
            if (statusEffectInstance.getEffectType() == StatusEffects.SPEED) return true;
        }
        return false;
    }

}
