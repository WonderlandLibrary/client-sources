/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.TickEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeChanger
extends Module {
    public NumberSetting time = new NumberSetting("Time", 200.0, 0.0, 240.0, 1.0);

    public TimeChanger() {
        super("Time Changer", "Changes the world time", 0, Category.Render);
        this.addSettings(this.time);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        Minecraft.theWorld.setWorldTime((long)this.time.getVal() * 100L);
    }

    @Subscribe
    private void onReceive(PacketEvent event) {
        if (event.getDir() == PacketEvent.DirectionType.Incoming && event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
}

