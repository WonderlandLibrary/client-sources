/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other.HackerDetector;

import java.util.ArrayList;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.other.HackerDetector.Movement.Fly;
import skizzle.modules.other.HackerDetector.Movement.Speed;
import skizzle.modules.other.HackerDetector.PlayerData;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.Timer;

public class HackerDetector
extends Module {
    public Timer frequencyTimer;
    public float topSpeed;
    public ArrayList<String> hackers;
    public boolean sendMessage = false;
    public NumberSetting frequency = new NumberSetting(Qprot0.0("\ue3f3\u71d9\ud8ac\ua7f5\ucd7f\u666a\u8c21\u8f93\u571b"), 1.0, 0.0, 10.0, 0.0);

    @Override
    public void onEvent(Event Nigga) {
        EntityPlayer Nigga2;
        HackerDetector Nigga3;
        if (Nigga instanceof EventUpdate && Nigga3.mc.thePlayer != null) {
            for (Object Nigga4 : Minecraft.theWorld.playerEntities) {
                if (!(Nigga4 instanceof EntityPlayer)) continue;
                Nigga2 = (EntityPlayer)Nigga4;
                PlayerData Nigga5 = Nigga2.playerData;
                Nigga5.lastTimeAgoCheck = System.currentTimeMillis();
                Nigga5.lastTickMoveSpeed = Nigga5.moveSpeed;
                ++Nigga5.resetTicks;
                if (Nigga5.resetTicks > 10) {
                    Nigga5.flyFlags = 0;
                    Nigga5.speedFlags = 0;
                }
                Nigga5.lastTickMoveX = Nigga5.moveX;
                Nigga5.lastTickMoveY = Nigga5.moveY;
                Nigga5.lastTickMoveZ = Nigga5.moveZ;
                Nigga5.lastTimeAgoCheck = System.currentTimeMillis();
                if (Nigga2.onGround || Minecraft.theWorld.getBlockState(Nigga2.getPosition().offsetDown(1)).getBlock().getMaterial().equals(Material.air)) {
                    Nigga5.onGround = true;
                    ++Nigga5.ticksOnGround;
                    Nigga5.ticksInAir = 0;
                } else {
                    Nigga5.onGround = false;
                    Nigga5.ticksOnGround = 0;
                    ++Nigga5.ticksInAir;
                }
                Nigga5.moveSpeed = Math.sqrt((Nigga2.posX - Nigga2.lastTickPosX) * (Nigga2.posX - Nigga2.lastTickPosX) + (Nigga2.posZ - Nigga2.lastTickPosZ) * (Nigga2.posZ - Nigga2.lastTickPosZ));
                Nigga5.differenceMoveSpeed = Nigga5.moveSpeed - Nigga5.lastTickMoveSpeed;
                Nigga5.moveX = Nigga2.posX - Nigga2.lastTickPosX;
                Nigga5.moveY = Nigga2.posY - Nigga2.lastTickPosY;
                Nigga5.moveZ = Nigga2.posZ - Nigga2.lastTickPosZ;
                Nigga5.differenceMoveX = Nigga5.moveX - Nigga5.lastTickMoveX;
                Nigga5.differenceMoveY = Nigga5.moveY - Nigga5.lastTickMoveY;
                Nigga5.differenceMoveZ = Nigga5.moveZ - Nigga5.lastTickMoveZ;
            }
        }
        if (Nigga3.frequencyTimer.hasTimeElapsed((long)(Nigga3.frequency.getValue() * 1000.0), true) && Nigga3.mc.thePlayer != null) {
            for (Object Nigga4 : Minecraft.theWorld.playerEntities) {
                String Nigga6;
                if (!(Nigga4 instanceof EntityPlayer)) continue;
                Nigga2 = (EntityPlayer)Nigga4;
                double Nigga7 = Math.sqrt((Nigga2.posX - Nigga2.lastTickPosX) * (Nigga2.posX - Nigga2.lastTickPosX) + (Nigga2.posZ - Nigga2.lastTickPosZ) * (Nigga2.posZ - Nigga2.lastTickPosZ));
                if ((double)Nigga3.topSpeed < Nigga7) {
                    Nigga3.topSpeed = (float)Nigga7;
                }
                if ((Nigga6 = HackerDetector.checkPlayer(Nigga2)).equals(Qprot0.0("\ue3db\u71c4\ud8a7\ue268")) || System.currentTimeMillis() - Nigga2.lastSpeedDetect <= ((long)-2133905986 ^ 0xFFFFFFFF80CF247AL)) continue;
                Nigga2.lastSpeedDetect = System.currentTimeMillis();
                if (!Nigga3.hackers.contains(Nigga2)) {
                    Nigga3.hackers.add(Nigga2.getName());
                }
                Client.notifications.notifs.add(new Notification(Qprot0.0("\ue3fd\u71ca\ud8aa\ue266\u1798\u667d\u8c0b\u8f95\u129f\ubcd2\uec41\uaf18"), String.valueOf(Nigga2.getName()) + Qprot0.0("\ue395\u71dc\ud8a8\ue27e\u17dd\u666b\u8c2a\u8f84\u128e\ubcd4\uec56\uaf09\u614f\u3784\u63a2\u7fdf\u42fb\u55f2") + Nigga6, Float.intBitsToFloat(1.05824544E9f ^ 0x7F338B47), Float.intBitsToFloat(2.13110963E9f ^ 0x7F0626FA), Notification.notificationType.WARNING));
            }
        }
    }

    public static {
        throw throwable;
    }

    public static String checkPlayer(EntityPlayer Nigga) {
        if (System.currentTimeMillis() - Nigga.playerData.lastTimeAgoCheck < ((long)-472183535 ^ 0xFFFFFFFFE3DB0D2DL)) {
            if (Speed.check(Nigga) != null) {
                return Speed.check(Nigga);
            }
            if (Fly.check(Nigga)) {
                return Qprot0.0("\ue3f3\u71c7\ud8b0");
            }
        }
        return Qprot0.0("\ue3db\u71c4\ud8a7\u239a");
    }

    public HackerDetector() {
        super(Qprot0.0("\ue3fd\u71ca\ud8aa\ua7ef\ucd6f\u667d\u8c0b\u8f95\u5716\u6625\uec41\uaf18\u6144\u725f"), 0, Module.Category.OTHER);
        HackerDetector Nigga;
        Nigga.topSpeed = Float.intBitsToFloat(2.13186803E9f ^ 0x7F11B97C);
        Nigga.frequencyTimer = new Timer();
        Nigga.hackers = new ArrayList();
        Nigga.addSettings(Nigga.frequency);
    }
}

