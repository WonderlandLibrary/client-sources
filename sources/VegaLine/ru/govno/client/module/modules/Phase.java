/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathHelper;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class Phase
extends Module {
    TimerHelper timer = new TimerHelper();
    boolean flagg;

    public Phase() {
        super("Phase", 0, Module.Category.PLAYER);
        this.settings.add(new Settings("Mode", "MatrixSkip", (Module)this, new String[]{"MatrixNotOpaque", "MatrixSkip", "PacketNitro"}));
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (this.currentMode("Mode").equalsIgnoreCase("MatrixNotOpaque")) {
            if (Minecraft.player != null && Phase.mc.world != null) {
                if (!Minecraft.player.isDead && this.actived && event.getPacket() instanceof CPacketConfirmTeleport) {
                    this.flagg = true;
                }
            }
        }
        if (this.currentMode("Mode").equalsIgnoreCase("MatrixSkip")) {
            SPacketPlayerPosLook pos = (SPacketPlayerPosLook)event.getPacket();
            float x = (float)(pos.x - Minecraft.player.posX);
            float y = (float)(pos.y - Minecraft.player.posY);
            float z = (float)(pos.z - Minecraft.player.posZ);
            float distance = MathHelper.sqrt(x * x + y * y + z * z);
            if (distance <= 8.0f) {
                event.setCancelled(true);
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(pos.x, pos.y, pos.z, pos.getYaw(), pos.getPitch(), true));
            }
        }
    }

    @EventTarget
    public void onPUpdate(EventPlayerMotionUpdate event) {
        if (this.currentMode("Mode").equalsIgnoreCase("PacketNitro") && this.actived) {
            boolean opaue = !Phase.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getMaterial().isReplaceable();
            if (!Minecraft.player.isCollidedHorizontally) {
                this.timer.reset();
            }
            if (this.timer.hasReached(150.0) && !opaue) {
                double dst = 0.65;
                double xe = Minecraft.player.posX - Math.sin((double)MoveMeHelp.moveYaw(Minecraft.player.rotationYaw) * 0.017453292) * dst;
                double ye = Minecraft.player.posY;
                double ze = Minecraft.player.posZ + Math.cos((double)MoveMeHelp.moveYaw(Minecraft.player.rotationYaw) * 0.017453292) * dst;
                Minecraft.player.forceUpdatePlayerServerPosition(xe, ye, ze, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch, true);
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xe, ye, ze, true));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xe, ye + 0.1, ze, true));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xe, ye, ze, true));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(xe, ye, ze, true));
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(0.0);
                this.timer.reset();
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.currentMode("Mode").equalsIgnoreCase("MatrixNotOpaque")) {
            for (int i = 0; i < 2; ++i) {
                if (!Minecraft.player.isCollidedHorizontally) continue;
                if (Phase.mc.gameSettings.keyBindForward.pressed) {
                    NetHandlerPlayClient netHandlerPlayClient = Minecraft.player.connection;
                    double d = Minecraft.player.posX;
                    mc.getMinecraft();
                    double d2 = d - Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * 0.25;
                    double d3 = Minecraft.player.posY;
                    double d4 = Minecraft.player.posZ;
                    mc.getMinecraft();
                    netHandlerPlayClient.sendPacket(new CPacketPlayer.Position(d2, d3, d4 + Math.cos(Math.toRadians(Minecraft.player.rotationYaw)) * 0.25, true));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 15000.0, Minecraft.player.posZ, true));
                }
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 9000.0, Minecraft.player.posZ, true));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 20.0, Minecraft.player.posZ, false));
            }
        }
        if (this.currentMode("Mode").equalsIgnoreCase("MatrixSkip")) {
            if ((double)Minecraft.player.fallDistance > 0.08) {
                if (Minecraft.player.posY != (double)((int)Minecraft.player.posY)) {
                    if (Minecraft.player.posY != (double)((float)((int)Minecraft.player.posY) + 0.5f)) {
                        this.toggle(false);
                    }
                }
            }
            if (Minecraft.player.onGround) {
                this.flagg = true;
                double d = Minecraft.player.posX;
                mc.getMinecraft();
                double d5 = d + -Math.sin((float)Math.toRadians(Minecraft.player.rotationYaw)) * (double)1.999f;
                double d6 = Minecraft.player.posY - 1.0E-5;
                double d7 = Minecraft.player.posZ;
                mc.getMinecraft();
                Minecraft.player.setPosition(d5, d6, d7 + Math.cos((float)Math.toRadians(Minecraft.player.rotationYaw)) * (double)1.999f);
                Minecraft.player.motionY = -0.0476;
                Entity.motiony = 0.01f;
            }
        }
    }
}

