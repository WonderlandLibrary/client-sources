package xyz.vergoclient.modules.impl.miscellaneous.disabler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.TimerUtil;
import xyz.vergoclient.util.packet.PacketUtil;

import java.util.ArrayList;

public class HypixelTimer implements OnEventInterface {

    protected static Minecraft mc = Minecraft.getMinecraft();

    private ArrayList<Packet> packets = new ArrayList<>();
    private boolean cancel;
    private TimerUtil timer1 = new TimerUtil(), timer2 = new TimerUtil();

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventSendPacket) {
            EventSendPacket event = (EventSendPacket) e;
            doTimerDisabler(event);
        }

        if(e instanceof EventMove) {
            if(!mc.isSingleplayer()) {
                if (timer1.hasTimeElapsed(10000, true)) {
                    cancel = true;
                    timer2.reset();
                }
            }
        }
    }

    private void doTimerDisabler(EventSendPacket e) {
        if(e.packet instanceof C03PacketPlayer) {
            C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) e.packet;

            // If the player isn't moving, and if the player isn't using an item, cancel the event.
            if(!c03PacketPlayer.isMoving() && !mc.thePlayer.isUsingItem()) {
                e.setCanceled(true);
            }
            if(cancel) {
                if(!timer2.hasTimeElapsed(400, false)) {
                    if(Vergo.config.modScaffold.isDisabled()) {
                        e.setCanceled(true);
                        packets.add(e.packet);
                    }
                } else {
                    packets.forEach(PacketUtil::sendPacketNoEvent);
                    packets.clear();
                    cancel = false;
                }
            }
        }


    }

}
