/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import java.util.ArrayDeque;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventJump;
import skizzle.events.listeners.EventKey;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.MoveUtil;
import skizzle.util.RandomHelper;
import skizzle.util.Timer;

public class Speed
extends Module {
    public int packetos = 0;
    public int serverPackets = 0;
    public boolean lagging;
    public ModeSetting mode;
    public Timer c03PacketTimer;
    public int eeasd = 0;
    public boolean hasJumpered = false;
    public Timer c05PacketTimer;
    public Timer resetTimer;
    public BooleanSetting water;
    public BooleanSetting step;
    public ArrayDeque<Packet> lagbackPackets;
    public ArrayDeque<Packet> c05Packets;
    public NumberSetting speed = new NumberSetting(Qprot0.0("\u2638\u71db\u1d7a\ua7e1\u09dc"), 2.0, 1.0, 15.0, 0.0);
    public Timer jumpTimer;
    public BooleanSetting spacebar = new BooleanSetting(Qprot0.0("\u2625\u71ce\u1d7a\ua7e0\u09cb\ua3f1\u8c1c\u4a56\u5703\ua291\u2999\uaf0e\ua49c\u725f"), false);
    public ArrayDeque<Packet> c03Packets;
    public Timer packetTimer;
    public int cancelable = 0;
    public Timer somethingTimer;

    public void boostJump() {
    }

    public static {
        throw throwable;
    }

    public Speed() {
        super(Qprot0.0("\u2638\u71db\u1d7a\ua7e1\u09dc"), 0, Module.Category.MOVEMENT);
        Speed Nigga;
        Nigga.water = new BooleanSetting(Qprot0.0("\u262f\u71c4\u1d73\ua7f4\u09d0\ua3b8\u8c21"), false);
        Nigga.mode = new ModeSetting(Qprot0.0("\u2626\u71c4\u1d7b\ua7e1"), Qprot0.0("\u2627\u71c4\u1d71\ua7e3\u0995\ua399\u8c20\u4a56"), Qprot0.0("\u2627\u71c4\u1d71\ua7e3\u0995\ua399\u8c20\u4a56"), Qprot0.0("\u263d\u7186\u1d57\ua7eb\u09c8"), Qprot0.0("\u2622\u71c8\u1d7a"), Qprot0.0("\u2627\u71c4\u1d68\ua7a9\u09f0\ua3be\u8c3f"), Qprot0.0("\u2639\u71ce\u1d7b\ua7e1\u09cb\ua3ba\u8c36"), Qprot0.0("\u260f\u71ce\u1d69"), Qprot0.0("\u2629\u7186\u1d57\ua7eb\u09c8"), Qprot0.0("\u262a\u71ea\u1d5c"), Qprot0.0("\u2623\u71d2\u1d6f\ua7ed\u09c0\ua3b4\u8c23"));
        Nigga.step = new BooleanSetting(Qprot0.0("\u2627\u71c4\u1d68\ua7a9\u09f0\ua3be\u8c3f\u4a06\u5731\ua286\u2999\uaf1c"), true);
        Nigga.packetTimer = new Timer();
        Nigga.jumpTimer = new Timer();
        Nigga.c03Packets = new ArrayDeque();
        Nigga.c05Packets = new ArrayDeque();
        Nigga.lagbackPackets = new ArrayDeque();
        Nigga.c03PacketTimer = new Timer();
        Nigga.c05PacketTimer = new Timer();
        Nigga.somethingTimer = new Timer();
        Nigga.resetTimer = new Timer();
        Nigga.addSettings(Nigga.mode, Nigga.speed, Nigga.step, Nigga.spacebar, Nigga.water);
    }

    @Override
    public void onEvent(Event Nigga) {
        Object Nigga2;
        Speed Nigga3;
        if (Nigga instanceof EventJump) {
            Nigga3.mode.getMode().equals(Qprot0.0("\u2627\u71c4\u1d68\ue220\u527b\ua3be\u8c3f"));
        }
        if (Nigga3.mc.thePlayer != null && Nigga3.hasJumpered) {
            Nigga3.jumpTimer.hasTimeElapsed((long)1198655567 ^ 0x47720851L, true);
        }
        if (Nigga instanceof EventPacket) {
            Nigga2 = (EventPacket)Nigga;
            if (((EventPacket)Nigga2).getPacket() instanceof S08PacketPlayerPosLook) {
                ++Nigga3.serverPackets;
                if (Nigga3.serverPackets > 5) {
                    Nigga3.lagging = true;
                }
            }
            if (Nigga3.resetTimer.hasTimeElapsed((long)-23033015 ^ 0xFFFFFFFFFEA0A24DL, true)) {
                Nigga3.serverPackets = 0;
                Nigga3.lagging = false;
                Nigga3.cancelable = 0;
            }
            if (Nigga3.lagging) {
                if (Nigga3.cancelable > RandomHelper.randomInt(15, 20) && RandomHelper.randomChance(0.0)) {
                    Nigga3.lagbackPackets.addLast(((EventPacket)Nigga2).getPacket());
                }
                ++Nigga3.cancelable;
            }
        }
        if (Nigga3.mode.getMode().equals(Qprot0.0("\u260f\u71ce\u1d69")) && Nigga3.mc.thePlayer != null && Nigga3.packetTimer.hasTimeElapsed((long)-1228750826 ^ 0xFFFFFFFFB6C2C024L, true)) {
            Nigga3.mc.getNetHandler().addToSendQueueWithoutEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Nigga3.mc.thePlayer.posX, Nigga3.mc.thePlayer.posY, Nigga3.mc.thePlayer.posZ, Nigga3.mc.thePlayer.rotationYaw, Nigga3.mc.thePlayer.rotationPitch, Nigga3.mc.thePlayer.onGround));
        }
        if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u2625\u71c4\u1d6d\ue260\u5252\ua3bd"))) {
            Nigga3.setSuffix(Nigga3.mode.getMode());
        } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u2625\u71c4\u1d6b\ue265\u525a\ua3bf\u8c28"))) {
            Nigga3.setSuffix(Nigga3.name);
        } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u263f\u71c4\u1d70\ue22d\u527e\ua3a4\u8c2c\u4a4e"))) {
            Nigga3.setSuffix(String.valueOf(Nigga3.name) + Qprot0.0("\u264b\u71e6\u1d25") + Nigga3.mode.getMode() + Qprot0.0("\u264b\u71f8\u1d25") + Nigga3.speed.getValue());
        }
        if (Nigga instanceof EventKey && Nigga3.mode.getMode().equals(Qprot0.0("\u260f\u71ce\u1d69")) && ((EventKey)Nigga).getCode() != Nigga3.mc.gameSettings.keyBindForward.getKeyCode() && ((EventKey)Nigga).getCode() != Nigga3.mc.gameSettings.keyBindRight.getKeyCode() && ((EventKey)Nigga).getCode() != Nigga3.mc.gameSettings.keyBindLeft.getKeyCode()) {
            ((EventKey)Nigga).getCode();
            Nigga3.mc.gameSettings.keyBindBack.getKeyCode();
        }
        if (Nigga instanceof EventMotion && !Client.ghostMode) {
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u2623\u71d2\u1d6f\ue264\u524b\ua3b4\u8c23")) && (Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindBack.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindRight.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindLeft.getIsKeyPressed())) {
                if (Nigga3.mc.thePlayer.isSneaking() || Nigga3.mc.thePlayer.isInWater()) {
                    return;
                }
                if (Nigga3.mc.thePlayer.onGround) {
                    Nigga3.mc.thePlayer.jump();
                    MoveUtil.setSpeed(Float.intBitsToFloat(1.10477824E9f ^ 0x7F4DEECB));
                }
            }
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u2625\u71e8\u1d4f")) && (Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindBack.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindRight.getIsKeyPressed() || Nigga3.mc.gameSettings.keyBindLeft.getIsKeyPressed())) {
                if (Nigga3.mc.thePlayer.isSneaking() || Nigga3.mc.thePlayer.isInWater()) {
                    return;
                }
                if (Nigga3.mc.thePlayer.onGround) {
                    Nigga3.mc.thePlayer.jump();
                    MoveUtil.setSpeed(Float.intBitsToFloat(1.088944E9f ^ 0x7E019103));
                    return;
                }
            }
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u2629\u7186\u1d57\ue262\u5243")) && MoveUtil.isMoving()) {
                MoveUtil.setSpeed(MoveUtil.getSpeed());
                if (Nigga3.mc.thePlayer.onGround) {
                    Nigga3.somethingTimer.hasTimeElapsed((long)-1877281245 ^ 0xFFFFFFFF901AF6B5L, true);
                }
                MoveUtil.setSpeed((float)Nigga3.speed.getValue() / Float.intBitsToFloat(1.06066413E9f ^ 0x7F387326));
            }
            if (Nigga.isPre()) {
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u262a\u71ea\u1d5c"))) {
                    Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.0886135E9f ^ 0x7F62EC9B);
                    if (MoveUtil.isMoving()) {
                        if (Nigga3.mc.thePlayer.onGround) {
                            Nigga3.mc.thePlayer.motionY = 0.0;
                            MoveUtil.strafe(Float.intBitsToFloat(1.10284928E9f ^ 0x7EB49111));
                        }
                    } else {
                        Nigga3.mc.thePlayer.motionX = 0.0;
                        Nigga3.mc.thePlayer.motionZ = 0.0;
                    }
                }
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u2639\u71ce\u1d7b\ue268\u5240\ua3ba\u8c36"))) {
                    if (Nigga3.mc.thePlayer.onGround && MoveUtil.isMoving()) {
                        Nigga3.mc.thePlayer.jump();
                        Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.10697946E9f ^ 0x7E7B2AAF);
                    }
                    if (!Nigga3.mc.thePlayer.onGround && MoveUtil.isMoving()) {
                        Nigga3.mc.timer.timerSpeed = (float)Nigga3.speed.getValue();
                    }
                }
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u260f\u71ce\u1d69")) && Nigga3.mc.thePlayer != null) {
                    if (!MoveUtil.isMoving()) {
                        Nigga3.mc.thePlayer.motionX = 0.0;
                        Nigga3.mc.thePlayer.motionZ = 0.0;
                    }
                    if (Nigga3.mc.thePlayer.moveForward != Float.intBitsToFloat(2.13541235E9f ^ 0x7F47CEB7) || Nigga3.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.1319936E9f ^ 0x7F13A3D1) && Nigga3.mc.thePlayer.onGround) {
                        double Nigga4;
                        Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.08766797E9f ^ 0x7F747EC1);
                        Nigga3.mc.thePlayer.motionX *= Nigga3.speed.getValue() / 0.0;
                        Nigga3.mc.thePlayer.motionZ *= Nigga3.speed.getValue() / 0.0;
                        double Nigga5 = Math.sqrt(Math.pow(Nigga3.mc.thePlayer.motionX, 2.0) + Math.pow(Nigga3.mc.thePlayer.motionZ, 2.0));
                        if (Nigga5 > (Nigga4 = Nigga3.speed.getValue() / 3.5714)) {
                            Nigga3.mc.thePlayer.motionX = Nigga3.mc.thePlayer.motionX / Nigga5 * Nigga4;
                            Nigga3.mc.thePlayer.motionZ = Nigga3.mc.thePlayer.motionZ / Nigga5 * Nigga4;
                        }
                    }
                }
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u263d\u7186\u1d57\ue262\u5243")) && !Nigga3.mode.getMode().equals(Qprot0.0("\u2627\u71c4\u1d71\ue26a\u521e\ua399\u8c20\u4a56"))) {
                    if (Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed() && Nigga3.mode.getMode().equals(Qprot0.0("\u263d\u7186\u1d57\ue262\u5243"))) {
                        Nigga3.mc.thePlayer.moveEntityWithHeading(Float.intBitsToFloat(2.13852301E9f ^ 0x7F7745A1), Float.intBitsToFloat(2.11441472E9f ^ 0x7E076887));
                    }
                    if (Nigga3.spacebar.isEnabled()) {
                        if (Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed() && Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed() && Nigga3.mc.thePlayer.onGround) {
                            Nigga3.mc.thePlayer.setSprinting(true);
                            Nigga3.mc.thePlayer.jump();
                        }
                    } else if (Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed() && Nigga3.mc.thePlayer.onGround) {
                        Nigga3.mc.thePlayer.setSprinting(true);
                        Nigga3.mc.thePlayer.jump();
                    }
                } else if (Nigga3.mode.getMode().equals(Qprot0.0("\u2622\u71c8\u1d7a")) && Nigga3.mc.thePlayer.onGround) {
                    double Nigga6;
                    Nigga2 = Nigga3.mc.thePlayer;
                    ((EntityPlayerSP)Nigga2).motionY += 0.0;
                    ((EntityPlayerSP)Nigga2).motionX *= 1.8;
                    ((EntityPlayerSP)Nigga2).motionZ *= 1.8;
                    double Nigga7 = Math.sqrt(Math.pow(Nigga3.mc.thePlayer.motionX, 2.0) + Math.pow(Nigga3.mc.thePlayer.motionZ, 2.0));
                    if (Nigga7 > (Nigga6 = 0.0)) {
                        Nigga3.mc.thePlayer.motionX = Nigga3.mc.thePlayer.motionX / Nigga7 * Nigga6;
                        Nigga3.mc.thePlayer.motionZ = Nigga3.mc.thePlayer.motionZ / Nigga7 * Nigga6;
                    }
                    Nigga3.mc.thePlayer.posX = Nigga3.mc.thePlayer.lastTickPosX;
                    Nigga3.mc.thePlayer.posY = Nigga3.mc.thePlayer.lastTickPosY;
                    Nigga3.mc.thePlayer.posZ = Nigga3.mc.thePlayer.lastTickPosZ;
                }
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u2627\u71c4\u1d68\ue220\u527b\ua3be\u8c3f")) && Nigga3.mc.thePlayer.onGround && (Nigga3.mc.thePlayer.moveForward != Float.intBitsToFloat(2.13400461E9f ^ 0x7F3253AD) || Nigga3.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.12537766E9f ^ 0x7EAEB067))) {
                    Nigga3.mc.thePlayer.jump();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        Speed Nigga;
        Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.09918285E9f ^ 0x7E043307);
        if (Nigga.mode.getMode().equals(Qprot0.0("\u260f\u71ce\u1d69"))) {
            Nigga.c03Packets.clear();
            Nigga.lagbackPackets.clear();
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u2627\u71c4\u1d68\u81d2\u7669\ua3be\u8c3f"))) {
            Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.09001626E9f ^ 0x7F7853D8);
        }
    }
}

