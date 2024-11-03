package net.silentclient.client.mods.hypixel.togglechat;

import net.silentclient.client.Client;
import net.silentclient.client.event.impl.EventReceivePacket;

public abstract class ToggleBase {
    private final String name;

    public ToggleBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return Client.getInstance().getSettingsManager().getSettingByClass(ToggleChatMod.class, this.getName()).getValBoolean();
    }

    public abstract boolean shouldToggle(final String message);

    public abstract void onTrigger(final EventReceivePacket event);
}
