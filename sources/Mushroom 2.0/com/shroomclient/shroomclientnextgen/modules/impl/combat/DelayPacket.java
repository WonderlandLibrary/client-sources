package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import com.shroomclient.shroomclientnextgen.util.TimedPacket;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

//@RegisterModule(
//        name = "Delay Packets",
//        description = "Backtrack feat. fakelag",
//        uniqueId = "delaypackets",
//        category = ModuleCategory.Combat
//)
public class DelayPacket extends Module {

    private final ConcurrentLinkedQueue<
        TimedPacket<ClientPlayNetworkHandler>
    > inQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<TimedPacket<?>> outQueue =
        new ConcurrentLinkedQueue<>();

    @ConfigOption(name = "In Delay", min = 0, max = 5000, description = "")
    public Integer inDelay = 0;

    @ConfigOption(name = "Out Delay", min = 0, max = 5000, description = "")
    public Integer outDelay = 225;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        while (!inQueue.isEmpty()) {
            try {
                inQueue.poll().packet.apply(C.mc.getNetworkHandler());
            } catch (Exception ignored) {}
        }

        while (!outQueue.isEmpty()) {
            try {
                PacketUtil.sendPacket(outQueue.poll().packet, false);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onMotion(MotionEvent.Pre e) {
        while (!inQueue.isEmpty() && inQueue.peek().msPassed() > inDelay) {
            try {
                inQueue.poll().packet.apply(C.mc.getNetworkHandler());
            } catch (Exception ignored) {}
        }

        while (!outQueue.isEmpty() && outQueue.peek().msPassed() > outDelay) {
            try {
                PacketUtil.sendPacket(outQueue.poll().packet, false);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive.Pre e) {
        if (inDelay > 0) {
            e.setCancelled(true);
            inQueue.add(
                new TimedPacket<>(
                    (Packet<ClientPlayNetworkHandler>) e.getPacket()
                )
            );
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {
        if (e.isManualSend()) return;

        if (outDelay > 0) {
            e.setCancelled(true);
            outQueue.add(new TimedPacket<>(e.getPacket()));
        }
    }
}
