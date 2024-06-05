/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Jesus
extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Verus", "Vanila", "Verus");
    public static boolean shouldOffsetPacket;

    public Jesus() {
        super("Jesus", "BECOME THE LORD AND SAVIOR JESUS CHRIST AND WALK ON WATER", 0, Category.Movement);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        switch (this.mode.getMode()) {
            case "Vanilla": {
                if (!e.isOutgoing() || !(e.packet instanceof C03PacketPlayer) || !Jesus.mc.thePlayer.isInWater()) break;
                C03PacketPlayer packet = (C03PacketPlayer)e.packet;
                boolean bl = shouldOffsetPacket = !shouldOffsetPacket;
                if (!shouldOffsetPacket) break;
                C03PacketPlayer c03PacketPlayer = packet;
                c03PacketPlayer.y -= 1.0E-6;
            }
        }
        this.setDisplayName("Jesus\u00a77 " + this.mode.getMode());
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        switch (this.mode.getMode()) {
            case "Verus": {
                if (Jesus.mc.thePlayer.isInWater()) {
                    Jesus.mc.thePlayer.motionY = 5.9;
                }
                Jesus.mc.thePlayer.jumpMovementFactor *= 0.9f;
                if (!Jesus.mc.thePlayer.isInWater()) break;
                Jesus.mc.thePlayer.motionY = 0.2;
            }
        }
    }
}

