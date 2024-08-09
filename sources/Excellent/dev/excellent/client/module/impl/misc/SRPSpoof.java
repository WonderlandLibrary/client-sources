package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.network.play.client.CResourcePackStatusPacket;

@ModuleInfo(name = "SRP Spoof", description = "Заменяет пакеты загрузки ресурспака", category = Category.MISC)
public class SRPSpoof extends Module {
    private final Listener<PacketEvent> onPacket = event -> {
        if (event.getPacket() instanceof CResourcePackStatusPacket wrapper) {
            wrapper.action = CResourcePackStatusPacket.Action.SUCCESSFULLY_LOADED;
        }
    };
}
