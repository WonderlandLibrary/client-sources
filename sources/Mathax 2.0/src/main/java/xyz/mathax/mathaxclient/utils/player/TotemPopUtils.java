package xyz.mathax.mathaxclient.utils.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.TotemPopEvent;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.UUID;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class TotemPopUtils {
    private static String lastWorldName = null;

    private static final Object2IntMap<UUID> totemPopMap = new Object2IntOpenHashMap<>();

    @PreInit
    public static void init() {
        MatHax.EVENT_BUS.subscribe(TotemPopUtils.class);
    }

    @EventHandler
    private static void onGameJoin(GameJoinedEvent event) {
        Config config = Systems.get(Config.class);
        if ((config.totemPopMemoryDeletionSetting.get() == TotemPopMemoryDeletion.Different_Server && !Utils.getWorldName().equals(lastWorldName)) || config.totemPopMemoryDeletionSetting.get() == TotemPopMemoryDeletion.Leave) {
            totemPopMap.clear();
        }

        lastWorldName = Utils.getWorldName();
    }

    @EventHandler
    private static void onReceivePacket(PacketEvent.Receive event) {
        if (!(event.packet instanceof EntityStatusS2CPacket packet)) {
            return;
        }

        if (packet.getStatus() != 35 || !(packet.getEntity(mc.world) instanceof PlayerEntity player)) {
            return;
        }

        MatHax.EVENT_BUS.post(TotemPopEvent.get(player));

        synchronized (totemPopMap) {
            int pops = totemPopMap.getOrDefault(player.getUuid(), 0);
            totemPopMap.put(player.getUuid(), ++pops);
        }
    }

    public static int getPops(UUID uuid) {
        if (!totemPopMap.containsKey(uuid)) {
            return 0;
        }

        return totemPopMap.getOrDefault(uuid, 0);
    }

    public enum TotemPopMemoryDeletion {
        Leave("Leave"),
        Different_Server("Different server"),
        Never("Never");

        private final String name;

        TotemPopMemoryDeletion(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
