package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.api.util.math.TimerUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.util.LinkedList;

@ModuleAnnotation(name = "Disabler", category = ModuleCategory.MOVEMENT, clickG = "Disable some verus checks")
public class VerusScaffoldDisabler extends Module {

    private final LinkedList<Packet<?>> packets = new LinkedList<>();
    private boolean teleportState;
    private final TimerUtil timerUtil = new TimerUtil();
    private final LinkedList<Packet<?>> transactions = new LinkedList<>();

    @EventTarget
    public void onPacket(PacketEvent event) {

        if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            {
                packets.add(event.getPacket());
                transactions.add(event.getPacket());
            }

            event.setCancelled(true);

            if (packets.size() > 300) {
                sendPacketUnlogged(packets.poll());
            }
        }

        if (event.getPacket() instanceof C0BPacketEntityAction)
            event.setCancelled(true);


        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) event.getPacket();

            if (MC.thePlayer.ticksExisted % 5 == 0) {
                sendPacketUnlogged(new C0CPacketInput());
            }
            if (MC.thePlayer.ticksExisted % 40 == 0) {
                teleportState = true;

                c03PacketPlayer.y = -0.015625;
                c03PacketPlayer.onGround = false;
            }
        }

        if (event.getPacket() instanceof S08PacketPlayerPosLook && teleportState) {
            final S08PacketPlayerPosLook packetPlayerPosLook = (S08PacketPlayerPosLook) event.getPacket();
            teleportState = false;

            {
                event.setCancelled(true);

                sendPacketUnlogged(new C03PacketPlayer.C06PacketPlayerPosLook(packetPlayerPosLook.getX(), packetPlayerPosLook.getY(), packetPlayerPosLook.getZ(), packetPlayerPosLook.getYaw(), packetPlayerPosLook.getPitch(), true));
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (MC.thePlayer.ticksExisted % 180 == 0) {
            IngameChatLog.INGAME_CHAT_LOG.doLog(transactions.size() + "");

            if (transactions.size() >= 20) {
                for (int i = 0; i < transactions.size(); i++) {
                    sendPacketUnlogged(transactions.poll());
                }
            }
        }
        if (timerUtil.reachedTime(370L)) {
            timerUtil.doReset();

            if (!packets.isEmpty()) {
                sendPacketUnlogged(packets.poll());
            }
        }
    }

    @EventTarget
    public void onPre(EventPreMotion e) {
    }


    @Override
    public void onState() {
        this.transactions.clear();
        this.teleportState = false;
        this.packets.clear();
        this.timerUtil.doReset();
    }

    @Override
    public void onUndo() {

    }
}
