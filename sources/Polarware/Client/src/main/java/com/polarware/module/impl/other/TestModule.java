package com.polarware.module.impl.other;

import com.polarware.event.annotations.EventLink;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.DevelopmentFeature;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

@DevelopmentFeature
@ModuleInfo(name = "module.other.test.name", description = "module.other.test.description", category = Category.OTHER)
public final class TestModule extends Module {

    @EventLink
    public Listener<PacketSendEvent> onPacketSend = event -> {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08 = (C08PacketPlayerBlockPlacement) event.getPacket();

            PacketUtil.sendNoEvent(
                    c08
            );
        }
    };

}
