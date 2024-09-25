/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;

public class Velocity
extends Module {
    public NumberSetting vertical;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u4f01\u71c4\u7444\ua7e1"), Qprot0.0("\u4f1a\u71ca\u744e\ua7ed\u6ecf\uca9a\u8c2e"), Qprot0.0("\u4f1a\u71ca\u744e\ua7ed\u6ecf\uca9a\u8c2e"), Qprot0.0("\u4f0d\u71ea\u7463"));
    public NumberSetting horizontal = new NumberSetting(Qprot0.0("\u4f04\u71c4\u7452\ua7ed\u6ed9\uca99\u8c21\u236d\u5703\uc585"), 0.0, 0.0, 1.0, 0.0);

    public Velocity() {
        super(Qprot0.0("\u4f1a\u71ce\u744c\ua7eb\u6ec0\uca9f\u8c3b\u2360"), 0, Module.Category.COMBAT);
        Velocity Nigga;
        Nigga.vertical = new NumberSetting(Qprot0.0("\u4f1a\u71ce\u7452\ua7f0\u6eca\uca95\u8c2e\u2375"), 0.0, 0.0, 1.0, 0.0);
        Nigga.addSettings(Nigga.mode, Nigga.horizontal, Nigga.vertical);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        Velocity Nigga2;
        if (Nigga2.mode.getMode().equals(Qprot0.0("\u4f1a\u71ca\u744e\ue264\uab76\uca9a\u8c2e"))) {
            Nigga2.setSuffix(Nigga2.mode.getMode());
        }
        if (Nigga2.mode.getMode().equals(Qprot0.0("\u4f0d\u71ea\u7463"))) {
            Nigga2.setSuffix(Qprot0.0("\u4f04\u7191") + Nigga2.horizontal.getValue() + Qprot0.0("\u4f6c\u71fd\u741a") + Nigga2.vertical.getValue());
        }
        if (Nigga instanceof EventPacket && Nigga2.mc.thePlayer != null) {
            EventPacket Nigga3 = (EventPacket)Nigga;
            if (Nigga2.mc.thePlayer != null && Nigga3.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity Nigga4 = (S12PacketEntityVelocity)Nigga3.getPacket();
                if (Nigga4.entityID == Nigga2.mc.thePlayer.getEntityId()) {
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\u4f0d\u71ea\u7463"))) {
                        Nigga4.motionX = (int)((float)Nigga4.motionX * (float)Nigga2.horizontal.getValue());
                        Nigga4.motionY = (int)((float)Nigga4.motionY * (float)Nigga2.vertical.getValue());
                        Nigga4.motionZ = (int)((float)Nigga4.motionZ * (float)Nigga2.horizontal.getValue());
                    }
                    if (Nigga2.mode.getMode().equals(Qprot0.0("\u4f1a\u71ca\u744e\ue264\uab76\uca9a\u8c2e"))) {
                        Nigga3.setCancelled(true);
                    }
                }
            }
        }
    }
}

