/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;

public class Killsay
extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Diablo", "Custom", "Diablo");

    public Killsay() {
        super("Killsay", "Sends a message in chat upon killing a player", 0, Category.Misc);
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
    }
}

