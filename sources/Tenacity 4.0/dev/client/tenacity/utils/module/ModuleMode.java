package dev.client.tenacity.utils.module;

import dev.client.tenacity.Tenacity;
import net.minecraft.client.Minecraft;

public interface ModuleMode {

    Minecraft mc = Minecraft.getMinecraft();

    default void onEnable() {
        Tenacity.INSTANCE.getEventProtocol().register(this);
    }

    default void onDisable() {
        Tenacity.INSTANCE.getEventProtocol().unregister(this);
    }

}
