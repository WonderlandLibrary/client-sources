package com.alan.clients.module.impl.movement.phase;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.PushOutOfBlockEvent;
import com.alan.clients.event.impl.other.BlockAABBEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.module.impl.movement.Phase;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.nofall.WatchdogNoFall;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockGlass;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import rip.vantage.commons.util.time.StopWatch;

public class WatchdogAutoPhase extends Mode<Phase> {
    private boolean phase;
    private final StopWatch stopWatch = new StopWatch();

    private final WatchdogNoFall  nofall = null;


    public WatchdogAutoPhase(String name, Phase parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (phase && !stopWatch.finished(4000)) PingSpoofComponent.blink();




    };

    @EventLink
    public final Listener<BlockAABBEvent> onBlockAABBEvent = event -> {
        if (phase && PingSpoofComponent.enabled && event.getBlock() instanceof BlockGlass) event.setCancelled();
        if (phase && PingSpoofComponent.enabled && event.getBlock() instanceof BlockBarrier) {
            event.setCancelled();
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = ((S02PacketChat) packet);
            String chat = s02PacketChat.getChatComponent().getUnformattedText();

            switch (chat) {
                case "Cages opened! FIGHT!":
                case "§r§r§r                               §r§f§lSkyWars Duel§r":
                case "§r§eCages opened! §r§cFIGHT!§r":
                    phase = false;
                    break;

                case "The game starts in 3 seconds!":
                case "§r§e§r§eThe game starts in §r§a§r§c3§r§e seconds!§r§e§r":
                case "§r§eCages open in: §r§c3 §r§eseconds!§r":
                    phase = true;
                    stopWatch.reset();
                    break;

                case "The games begin in 3 seconds!":
                    phase = true;
                    stopWatch.reset();
                    break;
            }
        }
    };

        @EventLink
        public final Listener<PushOutOfBlockEvent> onPushOutOfBlock = CancellableEvent::setCancelled;

}