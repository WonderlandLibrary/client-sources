/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventMoveFlying;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRender3D;
import skizzle.events.listeners.EventStrafing;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.modules.player.Blink;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.MoveUtil;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class Fly
extends Module {
    public boolean jumped = false;
    public Timer timer = new Timer();
    public float yaw;
    public boolean blink;
    public ArrayList<C03PacketPlayer> packets;
    public double enableY;
    public NumberSetting onlyAirHeight;
    public NumberSetting catapultSpeed;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u5172\u71c4\u6a57\ua7e1"), Qprot0.0("\u517c\u71d9\u6a56\ua7e5\u78f8\ud4ec\u8c39\u3d6f"), Qprot0.0("\u517c\u71d9\u6a56\ua7e5\u78f8\ud4ec\u8c39\u3d6f"), Qprot0.0("\u517c\u71ca\u6a47\ua7e5\u78fc\ud4f0\u8c23\u3d7e"), Qprot0.0("\u5178\u71c7\u6a5a\ua7e0\u78e9"), Qprot0.0("\u5177\u71d2\u6a43\ua7ed\u78f4\ud4e0\u8c23"), Qprot0.0("\u516d\u71ce\u6a57\ua7e1\u78ff\ud4ee\u8c36"), Qprot0.0("\u516d\u71ce\u6a57\ua7e1\u78ff\ud4ee\u8c36\u3d46\u570d\ud3a8\u5ecf\uaf26\ud3a4\u7240\u0cc5"), Qprot0.0("\u516d\u71ce\u6a57\ua7e1\u78ff\ud4ee\u8c36\u3d48\u570e\ud3af\u5ec6\uaf07"), Qprot0.0("\u5172\u71ca\u6a47\ua7f6\u78e5\ud4fd"), Qprot0.0("\u515b\u71ce\u6a45"), Qprot0.0("\u5172\u71c2\u6a5d\ua7e1\u78e4\ud4f0\u8c3b\u3d2a\u5727\ud3be\u5ed8\uaf00\ud3be\u7244\u0cc1"));
    public double enableZ;
    public EntityOtherPlayerMP fakePlayer = null;
    public Timer speedTimer;
    public double enableX;
    public Timer disableTimer;
    public float speedMult;
    public boolean isFlying = false;
    public NumberSetting catapultJumps;
    public Timer enableDelay;
    public BooleanSetting onlyAir;
    public boolean canDisable;

    @Override
    public void onEnable() {
        Fly Nigga;
        if (Nigga.mode.getMode().equals(Qprot0.0("\u5172\u71ca\u6a47\u59c6\u7ed5\ud4fd"))) {
            Nigga.jumped = false;
            Nigga.mc.thePlayer.jump();
            Nigga.enableDelay.reset();
        }
        Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u59d1\u7ecf\ud4ee\u8c36"));
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u59d1\u7ecf\ud4ee\u8c36\u3d46\ua93d\ud598\u5ecf\uaf26\ud3a4\u8c70\u0af5"))) {
            Nigga.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.1385847E9f ^ 0x7F13D7D1);
            Nigga.canDisable = false;
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u59d1\u7ecf\ud4ee\u8c36\u3d48\ua93e\ud59f\u5ec6\uaf07"))) {
            Nigga.enableX = Nigga.mc.thePlayer.posX;
            Nigga.enableY = Nigga.mc.thePlayer.posY + (double)Nigga.mc.thePlayer.getEyeHeight();
            Nigga.enableZ = Nigga.mc.thePlayer.posZ;
            Nigga.fakePlayer = Blink.createFakePlayer(-420);
        }
    }

    public void tacticalNuke() {
        Fly Nigga;
        Nigga.blink = false;
        for (Packet packet : Nigga.packets) {
            if (packet instanceof S08PacketPlayerPosLook) continue;
            Nigga.mc.thePlayer.capabilities.isFlying = true;
        }
        Nigga.packets.clear();
    }

    public Fly() {
        super(Qprot0.0("\u5179\u71c7\u6a4a"), 34, Module.Category.MOVEMENT);
        Fly Nigga;
        Nigga.catapultSpeed = new NumberSetting(Qprot0.0("\u517c\u71ca\u6a47\ua7e5\u78fc\ud4f0\u8c23\u3d7e\u5742\ud395\u5ed8\uaf09\ud3b4\u7249"), 8.0, 1.0, 18.0, 1.0);
        Nigga.catapultJumps = new NumberSetting(Qprot0.0("\u517c\u71ca\u6a47\ua7e5\u78fc\ud4f0\u8c23\u3d7e\u5742\ud38c\u5edd\uaf01\ud3a1\u725e\u0c9a\ucd49"), 1.0, 1.0, 4.0, 0.0);
        Nigga.onlyAir = new BooleanSetting(Qprot0.0("\u5176\u71c5\u6a13\ua7c5\u78e5\ud4f7"), false);
        Nigga.onlyAirHeight = new NumberSetting(Qprot0.0("\u5176\u71c5\u6a13\ua7c5\u78e5\ud4f7\u8c6f\u3d42\u5707\ud3af\u5ecf\uaf04\ud3a5"), 3.0, 1.0, 1000.0, 1.0);
        Nigga.packets = new ArrayList();
        Nigga.disableTimer = new Timer();
        Nigga.speedTimer = new Timer();
        Nigga.yaw = Float.intBitsToFloat(2.1052896E9f ^ 0x7D7C2BBF);
        Nigga.enableDelay = new Timer();
        Nigga.addSettings(Nigga.mode, Nigga.catapultSpeed, Nigga.catapultJumps, Nigga.onlyAir, Nigga.onlyAirHeight);
    }

    @Override
    public boolean canToggle() {
        Fly Nigga;
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\uaed7\u41cd\ud4ee\u8c36"))) {
            return true;
        }
        return true;
    }

    @Override
    public void onDisable() {
        Fly Nigga;
        Nigga.mc.thePlayer.capabilities.isFlying = false;
        Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.09330931E9f ^ 0x7EAA938F);
        if (Nigga.mode.getMode().equals(Qprot0.0("\u515b\u71ce\u6a45"))) {
            Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.0835232E9f ^ 0x7F1540AE);
            Nigga.mc.thePlayer.jumpMovementFactor = Nigga.mc.thePlayer.speedInAir;
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u5172\u71ca\u6a47\u818d\u591c\ud4fd"))) {
            Nigga.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.07554035E9f ^ 0x7CB8A69F);
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u819a\u5906\ud4ee\u8c36"))) {
            Nigga.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.13515622E9f ^ 0x7F0ACB0C);
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u819a\u5906\ud4ee\u8c36\u3d46\u7176\uf251\u5ecf\uaf26\ud3a4\u543b\u2d3c"))) {
            Nigga.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.11324826E9f ^ 0x7EF906CF);
            Client.notifications.notifs.add(new Notification(Qprot0.0("\u5179\u71c7\u6a4a"), Qprot0.0("\u517b\u71c4\u6a13\u8191\u591a\ud4f1\u8c6f\u3d6f\u7177\uf25e\u5eca\uaf00\ud3b4\u5476\u2d39\ucd54\u42fd\ue741\u3177\u4614\ua88d\u01c2\u4173\uce04\u9ef2\uec5f\u2f11\u8404\u6439\ud3cb\ub0bf\u8802\udcd9\u8b48"), Float.intBitsToFloat(1.0709536E9f ^ 0x7F757494), Float.intBitsToFloat(2.11757158E9f ^ 0x7E3793F3), Notification.notificationType.WARNING));
            Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.09760461E9f ^ 0x7EEC1E3F);
            Nigga.mc.thePlayer.motionY = -0.4;
            Nigga.canDisable = false;
        }
        if (Nigga.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\u819a\u5906\ud4ee\u8c36\u3d48\u7175\uf256\u5ec6\uaf07"))) {
            for (C03PacketPlayer Nigga2 : Nigga.packets) {
                Nigga2.onGround = true;
                Nigga.mc.getNetHandler().addToSendQueueWithoutEvent(Nigga2);
                Nigga.mc.getNetHandler().addToSendQueueWithoutEvent(Nigga2);
                Nigga.mc.getNetHandler().addToSendQueueWithoutEvent(Nigga2);
            }
            Nigga.packets.clear();
            Blink.removeFakePlayer(-420);
            Nigga.fakePlayer = null;
        }
        Nigga.disableTimer.reset();
    }

    @Override
    public void onEvent(Event Nigga) {
        Object Nigga2;
        Fly Nigga3;
        if (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d48\u1287\u2e24\u5ec6\uaf07")) && Nigga instanceof EventRender3D) {
            GlStateManager.color(Float.intBitsToFloat(2.1305527E9f ^ 0x7EFDA77B), Float.intBitsToFloat(1.07684109E9f ^ 0x7F5C799C), Float.intBitsToFloat(2.11668134E9f ^ 0x7E29FE6F), Float.intBitsToFloat(1.11553357E9f ^ 0x7DFDB0DF));
            RenderUtil.drawTracer(Nigga3.enableX - Nigga3.mc.getRenderManager().renderPosX, Nigga3.enableY - Nigga3.mc.getRenderManager().renderPosY, Nigga3.enableZ - Nigga3.mc.getRenderManager().renderPosZ, Nigga3.enableY + 1.0);
        }
        if (Nigga instanceof EventMoveFlying) {
            EventMoveFlying cfr_ignored_0 = (EventMoveFlying)Nigga;
        }
        if (Nigga3.mode.getMode().equals(Qprot0.0("\u5177\u71d2\u6a43\ue264\u857f\ud4e0\u8c23"))) {
            if (Nigga3.mc.thePlayer != null && Nigga3.mc.thePlayer.ticksExisted % 2 == 0) {
                Nigga3.mc.thePlayer.motionY = -0.01;
            }
            Nigga3.mc.thePlayer.onGround = false;
        }
        if (Nigga instanceof EventMotion) {
            Nigga2 = (EventMotion)Nigga;
            Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d46\u1284\u2e23\u5ecf\uaf26\ud3a4\u37c9\uf14e"));
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u5172\u71c2\u6a5d\ue268\u856f\ud4f0\u8c3b\u3d2a\u12ae\u2e35\u5ed8\uaf00\ud3be\u37cd\uf14a"))) {
                if (Nigga3.mc.thePlayer.ticksExisted % 2 == 0) {
                    Nigga3.mc.thePlayer.posY = Nigga3.mc.thePlayer.prevPosY;
                    Nigga3.mc.thePlayer.cameraPitch = Nigga3.mc.thePlayer.prevCameraPitch;
                    Nigga3.mc.thePlayer.motionY = 0.0;
                    MoveUtil.setMotion(2.0);
                }
                if (Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                    Nigga3.mc.thePlayer.motionY += 0.0;
                }
                if (Nigga3.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                    Nigga3.mc.thePlayer.motionY -= 0.0;
                }
            }
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u5172\u71ca\u6a47\ue27f\u856e\ud4fd"))) {
                Nigga3.yaw = Nigga3.mc.thePlayer.rotationYaw;
                if (Nigga3.mc.thePlayer.onGround) {
                    Nigga3.jumped = true;
                    ((EventMotion)Nigga2).sneak = true;
                }
                if (!Nigga3.jumped) {
                    return;
                }
                if (!Nigga3.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                    Nigga3.mc.thePlayer.motionY = 0.0;
                }
                if (Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                    Nigga3.mc.thePlayer.motionY = 0.0;
                }
                if (Nigga3.mc.thePlayer.ticksExisted % 2 != 0 && !Nigga3.mc.thePlayer.onGround) {
                    ((EventMotion)Nigga2).sneak = false;
                    Nigga3.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.08801101E9f ^ 0x7EB8FCB7);
                    MoveUtil.setMotion(MoveUtil.getSpeed());
                } else if (!Nigga3.mc.thePlayer.onGround) {
                    ((EventMotion)Nigga2).sneak = true;
                }
            }
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36"))) {
                Nigga3.mc.thePlayer.cameraYaw = Float.intBitsToFloat(-1.05723027E9f ^ 0x7EB73EF9);
                if (Nigga3.timer.hasTimeElapsed((long)-535647224 ^ 0xFFFFFFFFE012ACC0L, false)) {
                    Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.13285325E9f ^ 0x7EC93B39);
                    Nigga3.mc.thePlayer.motionY = 0.0;
                    Nigga3.mc.thePlayer.capabilities.isFlying = true;
                    Nigga3.mc.thePlayer.jumpMovementFactor = Float.intBitsToFloat(1.1085143E9f ^ 0x7F675761);
                    Nigga3.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.12347686E9f ^ 0x7F553246);
                    Nigga3.yaw = Nigga3.mc.thePlayer.rotationYaw;
                    if (Nigga3.timer.hasTimeElapsed((long)-495958585 ^ 0xFFFFFFFFE27046E7L, true)) {
                        Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.08250586E9f ^ 0x7F05BAAD);
                    }
                }
            }
        }
        if (Nigga instanceof EventPacket) {
            Nigga2 = (EventPacket)Nigga;
            if (((Event)Nigga2).isOutgoing() && ((EventPacket)Nigga2).getPacket() instanceof C03PacketPlayer && (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d48\u1287\u2e24\u5ec6\uaf07")) || Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4b6\u8c7e\u3d39\u1280\u2e34")))) {
                ((EventPacket)Nigga2).setCancelled(true);
            }
            if (((EventPacket)Nigga2).getPacket() instanceof C03PacketPlayer && (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d46\u1284\u2e23\u5ecf\uaf26\ud3a4\u37c9\uf14e")) || Nigga3.mode.getMode().equals(Qprot0.0("\u515b\u71ce\u6a45")))) {
                ((C03PacketPlayer)Nigga2.getPacket()).onGround = true;
            }
        }
        if (Nigga instanceof EventStrafing && (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d46\u1284\u2e23\u5ecf\uaf26\ud3a4\u37c9\uf14e")) || Nigga3.mode.getMode().equals(Qprot0.0("\u5172\u71ca\u6a47\ue27f\u856e\ud4fd")) || Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36")))) {
            MoveUtil.applyStrafeToPlayer((EventStrafing)Nigga, Nigga3.yaw);
        }
        if (Nigga instanceof EventUpdate) {
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u515b\u71ce\u6a45"))) {
                Nigga3.mc.timer.timerSpeed = Float.intBitsToFloat(1.0944521E9f ^ 0x7F70CFB0);
                Nigga3.mc.thePlayer.capabilities.isFlying = true;
                MoveUtil.setMotion(0.0);
            }
            if (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d46\u1284\u2e23\u5ecf\uaf26\ud3a4\u37c9\uf14e"))) {
                Nigga3.yaw = Nigga3.mc.thePlayer.rotationYaw;
                if (Nigga3.mc.thePlayer.onGround && Nigga3.canDisable) {
                    Nigga3.toggle();
                    Nigga3.canDisable = false;
                    return;
                }
                if (Nigga3.mc.thePlayer.onGround) {
                    Nigga3.mc.thePlayer.jump();
                    Nigga3.mc.thePlayer.onGround = false;
                    Nigga3.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.13174605E9f ^ 0x7E39DFCF);
                    Nigga3.canDisable = true;
                }
                if (Nigga3.mc.thePlayer.fallDistance > Float.intBitsToFloat(2.1261376E9f ^ 0x7EBA4905)) {
                    if (Nigga3.mc.thePlayer.fallDistance < Float.intBitsToFloat(1.08971456E9f ^ 0x7F73B97F)) {
                        Nigga3.mc.thePlayer.speedInAir = (float)((double)Nigga3.mc.thePlayer.speedInAir * 0.0);
                        Nigga3.mc.thePlayer.motionY *= 0.0;
                    } else if (Nigga3.mc.thePlayer.fallDistance < Float.intBitsToFloat(1.05389389E9f ^ 0x7ED12511)) {
                        Nigga3.mc.thePlayer.speedInAir = (float)((double)Nigga3.mc.thePlayer.speedInAir * 0.0);
                        Nigga3.mc.thePlayer.motionY *= 0.0;
                    } else {
                        Nigga3.mc.thePlayer.speedInAir = Float.intBitsToFloat(1.13358822E9f ^ 0x7F312EC9);
                        Nigga3.mc.thePlayer.motionY *= 0.0;
                    }
                }
                if (Nigga3.mc.thePlayer.motionY > 0.0 && Nigga3.mc.thePlayer.motionY < 0.0) {
                    Nigga3.mc.thePlayer.motionY *= 1.25;
                    Nigga3.mc.thePlayer.speedInAir = (float)((double)Nigga3.mc.thePlayer.speedInAir * 1.21);
                }
            }
            if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u5171\u71c4\u6a41\ue260\u8566\ud4e9"))) {
                Nigga3.setSuffix(Nigga3.mode.getMode());
                if (Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d48\u1287\u2e24\u5ec6\uaf07"))) {
                    Nigga3.setSuffix(String.valueOf(Nigga3.mode.getMode()) + " " + Nigga3.packets.size());
                }
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u5171\u71c4\u6a47\ue265\u856e\ud4eb\u8c28"))) {
                Nigga3.setSuffix(Nigga3.name);
            } else if (ModuleManager.hudModule.infoSetting.getMode().equals(Qprot0.0("\u516b\u71c4\u6a5c\ue22d\u854a\ud4f0\u8c2c\u3d62"))) {
                Nigga3.setSuffix(String.valueOf(Nigga3.name) + Qprot0.0("\u511f\u71e6\u6a09") + Nigga3.mode.getMode() + (Nigga3.onlyAir.isEnabled() ? Qprot0.0("\u511f\u71e3\u6a09") + Nigga3.onlyAirHeight.getValue() : ""));
            }
            if (!Client.ghostMode && Nigga3.checkAir() && Nigga.isPre()) {
                if (Nigga3.mode.getMode() == Qprot0.0("\u517c\u71d9\u6a56\ue26c\u8573\ud4ec\u8c39\u3d6f") || Nigga3.mode.getMode().equals(Qprot0.0("\u516d\u71ce\u6a57\ue268\u8574\ud4ee\u8c36\u3d48\u1287\u2e24\u5ec6\uaf07"))) {
                    Nigga3.mc.thePlayer.capabilities.isFlying = true;
                } else if (Nigga3.mode.getMode() == Qprot0.0("\u517c\u71ca\u6a47\ue26c\u8577\ud4f0\u8c23\u3d7e")) {
                    Nigga3.mc.thePlayer.capabilities.isFlying = false;
                    if (Nigga3.mc.gameSettings.keyBindForward.getIsKeyPressed()) {
                        Nigga2 = Nigga3.catapultJumps.getValue() + 0.0;
                        if (Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                            Nigga2 = 3.81818189;
                        }
                        if (Nigga3.timer.hasTimeElapsed((long)(1000.0 / (Double)Nigga2), true)) {
                            Nigga3.mc.thePlayer.onGround = true;
                            if (Nigga3.mc.thePlayer.moveForward > Float.intBitsToFloat(2.09879283E9f ^ 0x7D19096F) && Nigga3.mc.thePlayer.onGround) {
                                Nigga3.mc.thePlayer.setSprinting(true);
                                int Nigga4 = 0;
                                while ((double)Nigga4 < Nigga3.catapultSpeed.getValue()) {
                                    Nigga3.mc.thePlayer.jump();
                                    ++Nigga4;
                                }
                            }
                        }
                    } else {
                        Nigga3.mc.thePlayer.onGround = true;
                    }
                } else if (Nigga3.mode.getMode().equals(Qprot0.0("\u5178\u71c7\u6a5a\ue269\u8562"))) {
                    if (Nigga3.timer.hasTimeElapsed((long)701880199 ^ 0x29D5D7D7L, true)) {
                        Nigga3.mc.thePlayer.motionY = 0.0;
                    }
                    if (Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        Nigga3.mc.thePlayer.setPosition(Nigga3.mc.thePlayer.posX, Nigga3.mc.thePlayer.posY + 0.0, Nigga3.mc.thePlayer.posZ);
                    }
                }
            } else {
                Nigga3.mc.thePlayer.capabilities.allowFlying = false;
            }
        }
    }

    public static {
        throw throwable;
    }

    public boolean checkAir() {
        Fly Nigga;
        if (Nigga.onlyAir.isEnabled()) {
            int Nigga2 = 0;
            int Nigga3 = 0;
            while (Nigga2 < (int)Nigga.onlyAirHeight.getValue() + 1) {
                BlockPos Nigga4 = new BlockPos(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY, Nigga.mc.thePlayer.posZ);
                if (Minecraft.theWorld.isAirBlock(Nigga4.offsetDown(++Nigga2))) {
                    ++Nigga3;
                }
                if (!((double)Nigga3 > Nigga.onlyAirHeight.getValue())) continue;
                if (!Nigga.isFlying) {
                    Nigga.mc.thePlayer.jump();
                }
                return true;
            }
        }
        if (!Nigga.onlyAir.isEnabled()) {
            return true;
        }
        Nigga.isFlying = false;
        return false;
    }
}

