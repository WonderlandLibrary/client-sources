package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;

@ModuleInfo(label = "Inventory Move", category = ModuleCategory.OTHER)
public final class InventoryMove extends Module {

    private final Property<Boolean> cancelPacketProperty = new Property<>("Cancel Packet", false);

    @Listener
    public void onPacketSendEvent(PacketSendEvent event) {
        if (cancelPacketProperty.getValue() &&
                (event.getPacket() instanceof C16PacketClientStatus || event.getPacket() instanceof C0DPacketCloseWindow)) {
            event.setCancelled();
        }
    }
}
