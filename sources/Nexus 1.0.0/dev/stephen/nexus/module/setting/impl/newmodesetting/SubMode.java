package dev.stephen.nexus.module.setting.impl.newmodesetting;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.Module;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

@Getter
public abstract class SubMode<T extends Module> {

    // global values
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public boolean isNull() {
        return mc.player == null || mc.world == null;
    }

    // submode values

    private final String name;
    private final T parentModule;
    private Boolean registered;

    public SubMode(String name, T parentModule) {
        this.name = name;
        this.registered = false;
        this.parentModule = parentModule;
    }

    public void onEnable() {
        if (getParentModule().isEnabled()) {
            this.registered = true;
            Client.INSTANCE.getEventManager().getBus().subscribe(this);
        }
    }

    public void onDisable() {
        this.registered = false;
        Client.INSTANCE.getEventManager().getBus().unsubscribe(this);
    }
}
