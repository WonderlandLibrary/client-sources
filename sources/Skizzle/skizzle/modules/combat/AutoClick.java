/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import skizzle.events.Event;
import skizzle.events.listeners.EventChat;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.util.RandomHelper;
import skizzle.util.Timer;

public class AutoClick
extends Module {
    public NumberSetting maxCps = new NumberSetting(Qprot0.0("\u349f\u71ca\u0fde\ua7a4\u6662\ub138\u8c1c"), 10.0, 1.0, 20.0, 1.0);
    public long rndDelay;
    public NumberSetting minCps = new NumberSetting(Qprot0.0("\u349f\u71c2\u0fc8\ua7a4\u6662\ub138\u8c1c"), 10.0, 1.0, 20.0, 1.0);
    public Timer delay = new Timer();

    public static {
        throw throwable;
    }

    public AutoClick() {
        super(Qprot0.0("\u3493\u71de\u0fd2\ua7eb\u6662\ub104\u8c26\u58fc\u5709"), 0, Module.Category.COMBAT);
        AutoClick Nigga;
        Nigga.addSettings(Nigga.maxCps, Nigga.minCps);
    }

    @Override
    public void onEvent(Event Nigga) {
        AutoClick Nigga2;
        if (Nigga instanceof EventUpdate && Nigga2.mc.objectMouseOver.entityHit != null && Nigga2.delay.hasTimeElapsed(Nigga2.rndDelay, true)) {
            Nigga2.rndDelay = 1000 / RandomHelper.randomInt((int)Nigga2.minCps.getValue(), (int)Nigga2.maxCps.getValue());
            Nigga2.mc.thePlayer.swingItem();
            Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(Nigga2.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
        }
        if (Nigga instanceof EventChat) {
            EventChat Nigga3 = (EventChat)Nigga;
            Nigga3.getMessage().contains(Qprot0.0("\u34a6\u71ce\u0fd5\ue279"));
        }
    }
}

