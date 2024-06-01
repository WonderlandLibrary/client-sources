package com.polarware.module.impl.player;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

@ModuleInfo(name = "module.player.inventorysync.name", description = "module.player.inventorysync.description", category = Category.PLAYER)
public class InventorySyncModule extends Module {

    public short action;

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S32PacketConfirmTransaction) {
            final S32PacketConfirmTransaction wrapper = (S32PacketConfirmTransaction) packet;
            final Container inventory = mc.thePlayer.inventoryContainer;

            if (wrapper.getWindowId() == inventory.windowId) {
                this.action = wrapper.getActionNumber();

                if (this.action > 0 && this.action < inventory.transactionID) {
                    inventory.transactionID = (short) (this.action + 1);
                }
            }
        }
    };
}