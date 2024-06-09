package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.*;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry", Category.MISC, "lets you carry items in your crafting slots");
    }

    @EventHandler
    private Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if(event.getPacket() instanceof CPacketCloseWindow){
            if(((CPacketCloseWindow)event.getPacket()).windowId == mc.player.inventoryContainer.windowId){
                event.cancel();
            }
        }
    });

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }
}
