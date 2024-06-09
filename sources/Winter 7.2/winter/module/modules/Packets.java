/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.module.Module;

public class Packets
extends Module {
    public Packets() {
        super("Fake Lag", Module.Category.Other, -1);
        this.setBind(0);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
    }
}

