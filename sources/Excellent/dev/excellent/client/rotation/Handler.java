package dev.excellent.client.rotation;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.game.IMinecraft;

public abstract class Handler implements IMinecraft {
    public Handler() {
        Excellent.getInst().getEventBus().register(this);
    }
}