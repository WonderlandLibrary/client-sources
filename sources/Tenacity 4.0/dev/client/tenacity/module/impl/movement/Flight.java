package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.module.impl.combat.TargetStrafe;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.player.MovementUtils;
import dev.event.impl.player.MoveEvent;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.util.ArrayList;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public final class Flight extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Vanilla", "AirWalk");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 5, 0, 0.1);
    private float stage;
    private int ticks;
    private boolean doFly;
    private double x, y, z;
    private ArrayList<Packet> packets = new ArrayList<>();
    private boolean hasClipped;
    private double speedStage;

    public Flight() {
        super("Flight", Category.MOVEMENT, "Hovers you in the air");
        speed.addParent(mode, m -> m.is("Vanilla"));
        this.addSettings(mode, speed);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        switch (mode.getMode()) {
            case "Watchdog":
                mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.05f;
                mc.thePlayer.posY = y;
                if (mc.thePlayer.onGround && stage == 0) {
                    mc.thePlayer.motionY = 0.09;
                }
                stage++;
                if (mc.thePlayer.onGround && stage > 2 && !hasClipped) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.15, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.15, mc.thePlayer.posZ, true));
                    hasClipped = true;
                }
                if (doFly) {
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.onGround = true;
                    mc.timer.timerSpeed = 2;
                } else {
                    MovementUtils.setSpeed(0);
                    mc.timer.timerSpeed = 5;
                }
                break;
            case "Vanilla":
                mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? speed.getValue() : mc.gameSettings.keyBindSneak.isKeyDown() ? -speed.getValue() : 0;
                break;
            case "AirWalk":
                mc.thePlayer.motionY = 0;
                mc.thePlayer.onGround = true;
                break;
        }
    };

    private final EventListener<MoveEvent> onMove = e -> {
        if (mode.is("Vanilla")) {
            e.setSpeed(MovementUtils.isMoving() ? speed.getValue() : 0);
        }
        if (!mode.is("Watchdog")) {
            TargetStrafe.strafe(e);
        }
    };

    private final EventListener<PacketSendEvent> onPacketSend = e -> {
    };

    private final EventListener<PacketReceiveEvent> onPacketReceive = e -> {
        if (mode.is("Watchdog")) {
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook) e.getPacket();
                y = s08.getY();
                doFly = true;
            }
        }
    };

    @Override
    public void onEnable() {
        doFly = false;
        ticks = 0;
        stage = 0;
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        hasClipped = false;
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mode.is("Vanilla")) {
            mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;
        }
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }

}
