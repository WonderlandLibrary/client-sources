/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class Notifications
extends Module {
    public NumberSetting speed;
    public NumberSetting maxNotifs = new NumberSetting(Qprot0.0("\ube2e\u71ca\u856f\ua7a4\u91fe\u3bb6\u8c3b\ud247\u5704\u3a93\ub197\uaf0d\u3c81\u7244\ue5e6\u2208\u42fa"), 6.0, 1.0, 30.0, 1.0);
    public NumberSetting time = new NumberSetting(Qprot0.0("\ube2e\u71c4\u8573\ua7f1\u91dc\u3bbc\u8c6f\ud27a\u570b\u3a97\ub191"), 2.0, 1.0, 10.0, 0.0);

    public Notifications() {
        super(Qprot0.0("\ube2d\u71c4\u8563\ua7ed\u91d6\u3bb0\u8c2c\ud24f\u5716\u3a93\ub19b\uaf02\u3c86"), 0, Module.Category.RENDER);
        Notifications Nigga;
        Nigga.speed = new NumberSetting(Qprot0.0("\ube30\u71db\u8572\ua7e1\u91d4"), 1.0, 1.0, 10.0, 1.0);
        Nigga.addSettings(Nigga.maxNotifs, Nigga.time, Nigga.speed);
        Nigga.toggled = true;
    }

    public static {
        throw throwable;
    }
}

