/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class OnFalling
extends Module {
    boolean fall = false;
    private double egX;
    private double egY;
    private double egZ;

    public OnFalling() {
        super("OnFalling", 0, Module.Category.PLAYER);
        this.settings.add(new Settings("SneakBack", true, (Module)this));
        this.settings.add(new Settings("BackMode", "Matrix", this, new String[]{"Matrix", "OldGround", "Vulcan"}, () -> this.currentBooleanValue("SneakBack")));
        this.settings.add(new Settings("FallBoost", true, (Module)this));
        this.settings.add(new Settings("NoDamage", true, (Module)this));
        this.settings.add(new Settings("NoDmgMode", "MatrixOld", this, new String[]{"MatrixOld", "MatrixNew", "NCP"}, () -> this.currentBooleanValue("NoDamage")));
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (e.ground) {
            this.egX = Minecraft.player.posX;
            this.egY = Minecraft.player.posY;
            this.egZ = Minecraft.player.posZ;
        }
    }

    public static double getDistanceTofall() {
        for (int i = 0; i < 500; ++i) {
            if (!Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - (double)i, Minecraft.player.posZ)) continue;
            return i;
        }
        return 0.0;
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (this.currentBooleanValue("NoDamage") && this.fall && this.currentMode("NoDmgMode").equalsIgnoreCase("MatrixNew")) {
            CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            this.fall = false;
            packet.onGround = true;
            Minecraft.player.motionY = -0.0199f;
        }
        if (OnFalling.mc.timer.speed == 0.650000243527852 && Minecraft.player.ticksExisted % 2 != 0) {
            OnFalling.mc.timer.speed = 1.0;
        }
    }

    @Override
    public void onUpdate() {
        if (FreeCam.get.actived || Fly.get.actived || ElytraBoost.get.actived) {
            return;
        }
        if (OnFalling.mc.gameSettings.keyBindSneak.isKeyDown() && this.currentBooleanValue("SneakBack") && Minecraft.player.fallDistance >= 3.3f) {
            if (this.currentMode("BackMode").equalsIgnoreCase("OldGround")) {
                Minecraft.player.fallDistance = 0.0f;
                if (this.egX != 0.0 && this.egY != 0.0 && this.egZ != 0.0) {
                    Minecraft.player.setPosition(this.egX, this.egY, this.egZ);
                    this.egX = 0.0;
                    this.egY = 0.0;
                    this.egZ = 0.0;
                    Minecraft.player.motionX = 0.0;
                    Minecraft.player.motionZ = 0.0;
                } else {
                    Minecraft.player.setPosition(Minecraft.player.posX, Minecraft.player.posY + (double)Minecraft.player.height, Minecraft.player.posZ);
                    Minecraft.player.motionY = MoveMeHelp.getBaseJumpHeight();
                    Minecraft.player.motionY += 0.164157;
                }
            } else {
                boolean oldGravity = Minecraft.player.hasNoGravity();
                Minecraft.player.fallDistance = (float)((double)Minecraft.player.fallDistance - 0.2);
                Minecraft.player.onGround = true;
                Entity.motiony = Minecraft.player.motionY = (double)-0.01f;
                Timer.forceTimer(0.2f);
                Minecraft.player.setNoGravity(oldGravity);
            }
        }
        if (OnFalling.mc.gameSettings.keyBindSneak.isKeyDown() && this.currentBooleanValue("SneakBack") && Minecraft.player.fallDistance > 4.0f && this.currentMode("BackMode").equalsIgnoreCase("Vulcan")) {
            Minecraft.player.onGround = true;
            Entity.motiony = -Entity.Getmotiony;
            Minecraft.player.fallDistance = 0.0f;
        }
        if (Minecraft.player.posY > 0.0) {
            if (this.currentBooleanValue("FallBoost") && OnFalling.getDistanceTofall() > 5.0) {
                if ((int)Minecraft.player.fallDistance >= 4 && Minecraft.player.fallDistance < 10.0f) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                    Minecraft.player.fallDistance += 10.0f;
                }
                if (Minecraft.player.fallDistance > 5.0f && Minecraft.player.motionY < 0.0 && Minecraft.player.hurtTime != 0) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
                    Minecraft.player.motionY = -10.0;
                }
            }
            if (this.currentBooleanValue("NoDamage")) {
                float f = Minecraft.player.fallDistance;
                int n = Minecraft.player.getHealth() > 6.0f ? 3 : 2;
                if (f > (float)n && this.currentMode("NoDmgMode").equalsIgnoreCase("MatrixOld")) {
                    Minecraft.player.fallDistance = (float)(Math.random() * 1.0E-12);
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, true));
                    Minecraft.player.jumpMovementFactor = 0.0f;
                }
                if (Minecraft.player.fallDistance > 5.0f && this.currentMode("NoDmgMode").equalsIgnoreCase("MatrixNew")) {
                    Minecraft.player.fallDistance = 0.0f;
                    OnFalling.mc.timer.speed = 0.650000243527852;
                    this.fall = true;
                }
                if (OnFalling.mc.timer.speed == 0.650000243527852 && this.currentMode("NoDmgMode").equalsIgnoreCase("MatrixNew") && Minecraft.player.ticksExisted % 4 == 0) {
                    OnFalling.mc.timer.speed = 1.0;
                }
                if (Minecraft.player.fallDistance >= 3.0f && this.currentMode("NoDmgMode").equalsIgnoreCase("NCP")) {
                    Minecraft.player.onGround = false;
                    Minecraft.player.motionY = 0.02f;
                    for (int i = 0; i < 30; ++i) {
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 110000.0, Minecraft.player.posZ, false));
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 2.0, Minecraft.player.posZ, false));
                    }
                    Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
                    Minecraft.player.fallDistance = 0.0f;
                }
            } else {
                if (OnFalling.mc.timer.speed == 0.650000243527852) {
                    OnFalling.mc.timer.speed = 1.0;
                }
                this.fall = false;
            }
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            this.fall = false;
        }
        super.onToggled(actived);
    }
}

