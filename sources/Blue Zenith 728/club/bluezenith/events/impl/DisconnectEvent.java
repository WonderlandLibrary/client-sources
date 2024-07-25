package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.util.IChatComponent;

public class DisconnectEvent extends Event {
    public IChatComponent reason;

    public DisconnectEvent(IChatComponent reason) {
        this.reason = reason;
    }
}
