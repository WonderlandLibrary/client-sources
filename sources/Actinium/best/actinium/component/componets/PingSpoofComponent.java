package best.actinium.component.componets;

import best.actinium.component.Component;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.network.JoinServerEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.util.IAccess;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.io.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;

import java.util.concurrent.ConcurrentLinkedQueue;
//remove useless stuff
public class PingSpoofComponent extends Component implements IAccess {
    public static final ConcurrentLinkedQueue<PacketUtil.TimedPacket> incomingPackets = new ConcurrentLinkedQueue<>();
    public static final ConcurrentLinkedQueue<PacketUtil.TimedPacket> outgoingPackets = new ConcurrentLinkedQueue<>();
    private static final TimerUtil timerUtil = new TimerUtil();
    public static boolean spoofing;
    public static int delay;
    public static boolean normal, teleport, velocity, world, entity, client = true;

    @Callback
    public void onUpdate(UpdateEvent event) {
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
            if (System.currentTimeMillis() > packet.getTime() + (PingSpoofComponent.spoofing ? PingSpoofComponent.delay : 0)) {
                try {
                    PacketUtil.receiveNoEvent(packet.getPacket());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                PingSpoofComponent.incomingPackets.remove(packet);
            }
        }

        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
            if (System.currentTimeMillis() > packet.getTime() + (PingSpoofComponent.spoofing ? PingSpoofComponent.delay : 0)) {
                try {
                    PacketUtil.sendSilent(packet.getPacket());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                PingSpoofComponent.outgoingPackets.remove(packet);
            }
        }

        if (timerUtil.finished(60) || mc.thePlayer.ticksExisted <= 20 || !mc.getNetHandler().doneLoadingTerrain) {
            PingSpoofComponent.spoofing = false;

            for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
                PacketUtil.receiveNoEvent(packet.getPacket());
                PingSpoofComponent.incomingPackets.remove(packet);
            }

            for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
                PacketUtil.sendSilent(packet.getPacket());
                PingSpoofComponent.outgoingPackets.remove(packet);
            }
        }
    }

    @Callback
    public void onServerJoin(JoinServerEvent event) {
        incomingPackets.clear();
        timerUtil.reset();
        PingSpoofComponent.spoofing = false;
    }

    public static void dispatch() {
        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.incomingPackets) {
            try {
                PacketUtil.receiveNoEvent(packet.getPacket());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            PingSpoofComponent.incomingPackets.remove(packet);
        }

        for (final PacketUtil.TimedPacket packet : PingSpoofComponent.outgoingPackets) {
            PacketUtil.sendSilent(packet.getPacket());
            PingSpoofComponent.outgoingPackets.remove(packet);
        }
    }

    public static void setSpoofing(final int delay, final boolean normal, final boolean teleport,
                                   final boolean velocity, final boolean world, final boolean entity) {
        PingSpoofComponent.spoofing = true;
        PingSpoofComponent.delay = delay;
        PingSpoofComponent.normal = normal;
        PingSpoofComponent.teleport = teleport;
        PingSpoofComponent.velocity = velocity;
        PingSpoofComponent.world = world;
        PingSpoofComponent.entity = entity;
        PingSpoofComponent.client = false;

        timerUtil.reset();
    }

    public static void setSpoofing(final int delay, final boolean normal, final boolean teleport,
                                   final boolean velocity, final boolean world, final boolean entity, final boolean client) {
        PingSpoofComponent.spoofing = true;
        PingSpoofComponent.delay = delay;
        PingSpoofComponent.normal = normal;
        PingSpoofComponent.teleport = teleport;
        PingSpoofComponent.velocity = velocity;
        PingSpoofComponent.world = world;
        PingSpoofComponent.entity = entity;
        PingSpoofComponent.client = client;

        timerUtil.reset();
    }

    @Callback
    public void onPacket(PacketEvent event) {
        final Packet<?> packet = event.getPacket();

        switch (event.getType()) {
            case OUTGOING:
                if(PingSpoofComponent.client && PingSpoofComponent.spoofing) {
                    if (packet instanceof C03PacketPlayer || packet instanceof C16PacketClientStatus ||
                            packet instanceof C0DPacketCloseWindow || packet instanceof C0EPacketClickWindow ||
                            packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity ||
                            packet instanceof C0APacketAnimation || packet instanceof C09PacketHeldItemChange ||
                            packet instanceof C18PacketSpectate || packet instanceof C19PacketResourcePackStatus ||
                            packet instanceof C17PacketCustomPayload || packet instanceof C15PacketClientSettings ||
                            packet instanceof C14PacketTabComplete || packet instanceof C07PacketPlayerDigging ||
                            packet instanceof C08PacketPlayerBlockPlacement) {
                        outgoingPackets.add(new PacketUtil.TimedPacket(packet, System.currentTimeMillis()));
                        event.setCancelled(true);
                    }
                }
                break;
            case INCOMING:
                if(event.getPacket() instanceof S07PacketRespawn) {
                    incomingPackets.clear();
                    timerUtil.reset();
                    PingSpoofComponent.spoofing = false;
                }

                if (PingSpoofComponent.spoofing && mc.getNetHandler().doneLoadingTerrain) {
                    if (((packet instanceof S32PacketConfirmTransaction || packet instanceof S00PacketKeepAlive) && normal) ||

                            ((packet instanceof S08PacketPlayerPosLook || packet instanceof S09PacketHeldItemChange) && teleport) ||

                            (((packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).getEntityID() == mc.thePlayer.getEntityId()) ||
                                    packet instanceof S27PacketExplosion) && velocity) ||

                            ((packet instanceof S26PacketMapChunkBulk || packet instanceof S21PacketChunkData ||
                                    packet instanceof S23PacketBlockChange || packet instanceof S22PacketMultiBlockChange) && world) ||

                            ((packet instanceof S13PacketDestroyEntities || packet instanceof S14PacketEntity ||
                                    packet instanceof S18PacketEntityTeleport ||
                                    packet instanceof S20PacketEntityProperties || packet instanceof S19PacketEntityHeadLook) && entity)) {

                        incomingPackets.add(new PacketUtil.TimedPacket(packet, System.currentTimeMillis()));
                        event.setCancelled(true);
                    }
                }
                break;
        }
    }
}
