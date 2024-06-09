package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketConfirmTeleport;

public class PortalGodMode extends Module {
    public PortalGodMode() {
        super("PortalGodmode", Category.PLAYER, "Godmode when you go through a portal");
    }

    public void onEnable() {
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketConfirmTeleport)
            event.cancel();
    });
}
