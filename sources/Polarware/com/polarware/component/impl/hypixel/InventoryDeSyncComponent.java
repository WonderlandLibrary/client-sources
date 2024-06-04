package com.polarware.component.impl.hypixel;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.util.chat.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public class InventoryDeSyncComponent extends Component {

    private static boolean active, deSynced;

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> p = event.getPacket();

        if (p instanceof S2DPacketOpenWindow) {
            if (active) {
                event.setCancelled();
                deSynced = true;
                active = false;
            }
        }
    };

    public static void setActive(String command) {
        if (active || deSynced || mc.currentScreen != null) {
            return;
        }

        ChatUtil.send(command);
        active = true;
    }

    public static boolean isDeSynced() {
        return deSynced;
    }
}
