package xyz.vergoclient.modules.impl.miscellaneous.disabler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.event.impl.EventTeleport;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.ServerUtils;
import xyz.vergoclient.util.main.TimerUtil;
import xyz.vergoclient.util.packet.PacketUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WatchdogTest implements OnEventInterface {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static final List<Packet<?>> packet = new ArrayList();
    private static final TimerUtil timer1 = new TimerUtil();

    private static Vec3 initPos;

    public static boolean isSendable;

    public static int keyNew;

    @Override
    public void onEvent(Event e) {

        if(e instanceof EventMove && e.isPre()) {
            if(!ServerUtils.isOnHypixel() || mc.isSingleplayer()) return;

            if(packet.size() > 50 ) {
                while(!packet.isEmpty()) {
                    PacketUtil.sendPacketNoEvent(packet.remove(0));
                }
            }
        }

        if(e instanceof EventSendPacket) {
            EventSendPacket event = (EventSendPacket) e;
            onSendPacket(event);
        }

        if(e instanceof EventTeleport) {
            EventTeleport event = (EventTeleport) e;
            doDisable(event);
        }

    }

    private void onSendPacket(EventSendPacket e) {
        if (!ServerUtils.isOnHypixel() || mc.isSingleplayer()) return;

        final Packet<?> p = e.packet;

        if(p instanceof C03PacketPlayer) {
            final C03PacketPlayer c03 = (C03PacketPlayer) p;

            if(mc.thePlayer.ticksExisted == 1) {
                initPos = new Vec3(c03.x + getRandom(-1000000, 1000000), c03.y + getRandom(-1000000, 1000000), c03.z + getRandom(-1000000, 1000000));
            } else if(mc.thePlayer.sendQueue.doneLoadingTerrain && initPos != null && mc.thePlayer.ticksExisted < 100) {
                c03.x = initPos.xCoord;
                c03.y = initPos.yCoord;
                c03.z = initPos.zCoord;
            }
        }
    }

    public static double getRandom(double min, double max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final double d = min;
            min = max;
            max = d;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    private void doDisable(EventTeleport e) {
        if(!ServerUtils.isOnHypixel() || mc.isSingleplayer()) return;

        if(mc.thePlayer.sendQueue.doneLoadingTerrain) {
            if(mc.thePlayer.ticksExisted < 100) {
                for(int i = 0; i < 10; i++) {
                    PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(e.getPosX(), e.getPosY(), e.getPosZ(), e.getYaw(), e.getPitch(), false));
                }

                PacketUtil.sendPacketNoEvent(e.getResponse());

                if(mc.thePlayer.getDistance(e.getPosX(), e.getPosY(), e.getPosZ()) < 3) {
                    e.setCanceled(true);
                }
            } else {
                e.setPosX(e.getPosX() - Double.MIN_VALUE);
                e.setPosZ(e.getPosZ() + Double.MIN_VALUE);
            }
        }

    }

}