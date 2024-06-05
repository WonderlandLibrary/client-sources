/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.Timer;

public class BowFly
extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 100.0, 50.0, 1000.0, 10.0);
    public NumberSetting speedUp = new NumberSetting("Speed Up", 100.0, 100.0, 300.0, 10.0);

    public BowFly() {
        super("Bow Fly", "Extend knockback from bow", 0, Category.Movement);
        this.addSettings(this.speed, this.speedUp);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        S12PacketEntityVelocity packet;
        this.speed.setValue(300.0);
        double horizontal = this.speed.getVal();
        if (e.getPacket() instanceof S12PacketEntityVelocity && (packet = (S12PacketEntityVelocity)e.getPacket()).getEntityID() == BowFly.mc.thePlayer.getEntityId()) {
            if (packet.getEntityID() != BowFly.mc.thePlayer.getEntityId()) {
                return;
            }
            if (horizontal != 0.0) {
                packet.motionX = (int)(horizontal * (double)packet.getMotionX() / 100.0);
                packet.motionZ = (int)(horizontal * (double)packet.getMotionZ() / 100.0);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}

