/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class FastEat
extends Module {
    public NumberSetting speed = new NumberSetting(Qprot0.0("\u8331\u71db\ub873\ua7e1\uaad5"), 1.0, 1.0, 5.0, 1.0);

    public FastEat() {
        super(Qprot0.0("\u8324\u71ca\ub865\ua7f0\uaaf4\u06b9\u8c3b"), 0, Module.Category.PLAYER);
        FastEat Nigga;
        Nigga.addSettings(Nigga.speed);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate && Nigga.isPre()) {
            FastEat Nigga2;
            Nigga2.setSuffix("" + Nigga2.speed.getValue());
            if (Nigga2.mc.thePlayer.isUsingItem() && Nigga2.mc.thePlayer.getItemInUse().getItem() instanceof ItemFood) {
                int Nigga3 = 0;
                while ((double)Nigga3 < Nigga2.speed.getValue() * 4.0) {
                    Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                    ++Nigga3;
                }
            }
        }
    }

    public static {
        throw throwable;
    }
}

