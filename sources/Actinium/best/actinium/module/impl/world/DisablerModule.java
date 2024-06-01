package best.actinium.module.impl.world;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.ModeProperty;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.packet.PlayPongC2SPacket;
import best.actinium.util.player.MoveUtil;
import best.actinium.util.render.ChatUtil;
import best.actinium.util.IAccess;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Float.NaN;

/**
 * @author Nyghtfull & Liticane
 * @since 19/12/2023
 */
@ModuleInfo(
        name = "Disabler",
        description = "Insane Disabler",
        category = ModuleCategory.WORLD
)
public class DisablerModule extends Module {
    public ModeProperty mode = new ModeProperty("Disabler Mode", this, new String[] {"Verus Recode", "Verus Combat", "Sprint", "Watchdog", "Norules Recode"}, "Verus Recode");
    private final TimerUtil stopwatch = new TimerUtil();
    private final ConcurrentLinkedQueue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
    private float verusYaw, verusPitch;
    private final TimerUtil timeUntilFlag = new TimerUtil();
    private Boolean wasInBlock = false;
    private TimerUtil t = new TimerUtil();

    @Override
    public void onEnable() {
        switch (mode.getMode()) {
            case "Verus Recode":
                IAccess.mc.timer.timerSpeed = 1f;
                timeUntilFlag.reset();
                stopwatch.reset();
                PacketUtil.send(new C0BPacketEntityAction(IAccess.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                break;

            case "Norules Recode":
                //1
                IAccess.mc.thePlayer.setPosition(IAccess.mc.thePlayer.posX, IAccess.mc.thePlayer.posY - 2, IAccess.mc.thePlayer.posZ);
                break;
        }

        packetQueue.clear();
        t.reset();
        super.onEnable();
    }

    @Callback
    public void onTick(TickEvent event) {
        //if(mode.is("Watchdog")) {
        //            if (event.getType() == EventType.PRE && !state && t.hasTimeElapsed(1000, false) && !parameters.isEmpty()) {
        //                Collections.shuffle(parameters);
        //                //normmaly this shit should be an int and the packet should be CommonPongC2SPacket on 1.20
        //                ChatUtil.display("asd");
        //                parameters.forEach(parameter -> mc.getNetHandler().addToSendQueue(new PlayPongC2SPacket(parameter)));
        //
        //
        //                parameters.clear();
        //                state = true;
        //            }
        //        }
    }

    @Callback
    public void onMotion(MotionEvent event) {
        switch (mode.getMode()) {
            case "Verus Recode":
                break;
        }
    }

    @Callback
    public void onUpdate(UpdateEvent event) {
        this.setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "Verus Recode":
                if (!packetQueue.isEmpty() && IAccess.mc.thePlayer.ticksExisted % 33 == 0)
                    PacketUtil.sendSilent(packetQueue.poll());

                if (stopwatch.hasTimeElapsed(16600L)) {
                    while (!packetQueue.isEmpty()) {
                        PacketUtil.sendSilent(packetQueue.poll());
                    }

                    PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition());
                    stopwatch.reset();
                }
                break;
            case "Verus Combat":
                if (mc.thePlayer.ticksExisted % 35 == 0) {
                    if (!packetQueue.isEmpty()) {
                        ChatUtil.display("gay");
                        PacketUtil.sendSilent(packetQueue.poll());
                    }
                }
                break;
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        switch (mode.getMode()) {
            case "Verus Recode":
                if (!packetQueue.isEmpty() && IAccess.mc.thePlayer != null && IAccess.mc.thePlayer.ticksExisted <= 11)
                    packetQueue.clear();

                if (event.getType() == EventType.OUTGOING) {
                    if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                        packetQueue.add(event.getPacket());
                        event.setCancelled(true);
                    }
                    if (event.getPacket() instanceof C0BPacketEntityAction) {
                        event.setCancelled(true);
                    }
                    if (event.getPacket() instanceof C03PacketPlayer c03) {
                        double x = IAccess.mc.thePlayer.posX, y = IAccess.mc.thePlayer.posY, z = IAccess.mc.thePlayer.posZ;

                        float yaw = IAccess.mc.thePlayer.rotationYaw;
                        float pitch = IAccess.mc.thePlayer.rotationPitch;

                        boolean ground = c03.onGround;

                        if (c03.isMoving()) {
                            x = c03.getPositionX();
                            y = c03.getPositionY();
                            z = c03.getPositionZ();
                        }

                        if (c03.getRotating()) {
                            yaw = c03.getYaw();
                            pitch = c03.getPitch();
                        }
                        //40
                        //30 = 35S
                        //60 around 45s + respawn
                        if (IAccess.mc.thePlayer.ticksExisted % 40 == 0) {
                            c03.y = -0.911F;
                            c03.onGround = false;
                        } else if (IAccess.mc.thePlayer.ticksExisted % 4 != 0) {
                            event.setCancelled(true);
                        } else if (c03.getRotating() || yaw != verusYaw || pitch != verusPitch) {
                            this.verusYaw = c03.getYaw();
                            this.verusPitch = c03.getPitch();
                            event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, yaw, pitch, ground));
                        } else {
                            event.setPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
                        }
                    }
                }
                break;
            case "Norules Recode":
                IAccess.mc.thePlayer.setPosition(IAccess.mc.thePlayer.posX, IAccess.mc.thePlayer.posY - 2, IAccess.mc.thePlayer.posZ);
                if(event.getType() == EventType.OUTGOING) {
                    if (wasInBlock) {
                        IAccess.mc.thePlayer.motionY = 1;
                    }

                    if (IAccess.mc.thePlayer.isCollidedHorizontally) {
                        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                         ///   ChatUtil.display("Asd");
                            wasInBlock = true;
                            event.setCancelled(true);
                        }
                 }
                }
                break;
            case "Watchdog":
                //  PacketUtil.sendSilent(new C02PacketUseEntity(mc.thePlayer,new Vec3(NaN,NaN,NaN)));
                PacketUtil.sendSilent(new C0FPacketConfirmTransaction(Float.floatToRawIntBits(0x7fc00000), (short) NaN, true));
                //   PacketUtil.sendSilent(new C0FPacketConfirmTransaction(Float.floatToRawIntBits(0x7fc00000), (short) NaN, true));
                break;
            case "Sprint":
                if (event.getPacket() instanceof C0BPacketEntityAction wrapper) {
                    if (wrapper.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING) || wrapper.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
                        event.setCancelled(true);
                    }
                }
                break;
            case "Verus Combat":
                if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                    ChatUtil.display("CANCEL");
                    packetQueue.add(event.getPacket());
                    event.setCancelled(true);
                }
                break;
        }
    }

}
