/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;

public class AntiBot
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u1aeb\u71c4\u21be\ua7e1"), Qprot0.0("\u1aed\u71c2\u21ae\ua7dc"), Qprot0.0("\u1aed\u71c2\u21ae\ua7dc"));

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
    }

    public boolean isTarget(EntityLivingBase Nigga) {
        AntiBot Nigga2;
        return !Nigga2.isEnabled() || Nigga.ticksExisted <= 177 || !Nigga2.mode.getMode().equals(Qprot0.0("\u1aed\u71c2\u21ae\uc6f5")) || !Nigga.getName().toLowerCase().startsWith(Qprot0.0("\u1ade\u71ca\u21b9"));
    }

    public AntiBot() {
        super(Qprot0.0("\u1ae7\u71c5\u21ae\ua7ed\u3437\u9f73\u8c3b"), 0, Module.Category.COMBAT);
        AntiBot Nigga;
        Nigga.addSettings(Nigga.mode);
    }
}

