package com.alan.clients.module.impl.other;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.BlinkComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.packet.custom.impl.PlayPongC2SPacket;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.Vec3;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(aliases = {"module.other.timer.name"}, description = "module.other.timer.description", category = Category.MOVEMENT)
public final class Timer extends Module {
    private boolean attempt;

    public static ConcurrentLinkedQueue<PacketUtil.TimedPacket> packetList = new ConcurrentLinkedQueue<>();
    private int ticksSinceTeleport;

    private long ticks2;
    private boolean teleport;
    private final BoundsNumberValue timer =
            new BoundsNumberValue("Timer", this, 1, 2, 0.1, 20, 0.05);

    public final BooleanValue watchdog = new BooleanValue("Watchdog", this, false);

    @EventLink(value = Priorities.MEDIUM)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(!watchdog.getValue()) {


            mc.timer.timerSpeed = (float) MathUtil.getRandom(timer.getValue().floatValue(), timer.getSecondValue().floatValue());
        } else if (watchdog.getValue()){
            event.setOnGround(true);

            if(attempt && System.currentTimeMillis() > ticks2){
                mc.thePlayer.motionX *= 0;
                mc.thePlayer.motionZ *= 0;
                ticks2 = 0;
                getModule(Timer.class).toggle();


            }




        }
    };

    @EventLink(value = Priorities.HIGH)
    public final Listener<PreUpdateEvent> onHighPreUpdate = event -> {
        if(watchdog.getValue()) {
            PingSpoofComponent.spoof(9999999, false, false, false, false, true);
            mc.thePlayer.safeWalk = true;
        }
    };


    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if(watchdog.getValue() && !attempt) {
            if (mc.thePlayer.onGround) {
                ticksSinceTeleport++;

                double x = mc.thePlayer.posX;
                double y = mc.thePlayer.posY;
                double z = mc.thePlayer.posZ;
                float yaw = mc.thePlayer.rotationYaw;
                float pitch = mc.thePlayer.rotationPitch;
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z,  true));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(x, y-0.0000000000000001, z,  true));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(x, y+ 0.07, z,  true));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z,  true));
                mc.timer.timerSpeed = (float)(timer.getValue().floatValue());
                teleport = true;
            } else if(watchdog.getValue()){
             //   this.mc.timer.timerSpeed = 1;
             //   MoveUtil.stop();

            }
        }
    };

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        if(watchdog.getValue()) {
            if (mc.thePlayer.onGround && ticksSinceTeleport == 0) {
                MoveUtil.stop();
            }
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if(watchdog.getValue()) {

            if (attempt) {
                MoveUtil.stop();
                this.mc.timer.timerSpeed = 1f;
                if (mc.thePlayer.onGround) {
                    packetList.forEach(timedPacket -> PacketUtil.queue(timedPacket.getPacket()));
                    packetList.clear();
                    PingSpoofComponent.dispatch();
                    // mc.thePlayer.jump();
                }

            }
        }
    };



    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if(watchdog.getValue()) {
            if (mc.thePlayer.onGround) {
                Packet<?> packet = event.getPacket();

                if (packet instanceof S32PacketConfirmTransaction) {
                    packetList.add(new PacketUtil.TimedPacket(packet));
                    event.setCancelled();
                }

            } else {
             //   packetList.forEach(timedPacket -> PacketUtil.queue(timedPacket.getPacket()));
             //   packetList.clear();
            }
        }

    };
    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

if(watchdog.getValue()) {
    final Packet<?> p = event.getPacket();
    if (p instanceof S12PacketEntityVelocity) {
        final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;
        event.setCancelled();
    }
    if (p instanceof S08PacketPlayerPosLook) {
        event.setCancelled();
    }
}
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<TeleportEvent> onTeleport = event -> {

    };


    @EventLink
    public final Listener<JumpEvent> onJump = event -> {
        if(watchdog.getValue()) {
            event.setCancelled();
        }

    };
    @Override
    public void onEnable() {
        if(watchdog.getValue()) {
            mc.thePlayer.stepHeight = 0.0F;
            attempt = false;
            ticksSinceTeleport = 0;
            teleport = false;
            ticks2 = 0;
        }
    }

    @Override
    public void onDisable() {
        if (this.mc.timer.timerSpeed != 1) {

            this.mc.timer.timerSpeed = 1;

        }

        if(watchdog.getValue()){
            mc.thePlayer.stepHeight = 0.6F;
            mc.thePlayer.motionX *= 0;
            mc.thePlayer.motionZ *= 0;


        }

    }

    @EventLink
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (watchdog.getValue() && event.getKeyCode() == getModule(Timer.class).getKey() && !attempt) {

            ticks2 = System.currentTimeMillis() + 200;
            attempt = true;
            ChatUtil.display("sent");
            event.setCancelled();



        }

        if (watchdog.getValue() && attempt) {
            event.setCancelled();
            }


    };
}
