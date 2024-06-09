/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.module.Module;

public class AntiBot
extends Module {
    public AntiBot() {
        super("Antibot", Module.Category.Combat, -13970566);
        this.setBind(37);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        this.mode(" Hypixel");
    }
}

