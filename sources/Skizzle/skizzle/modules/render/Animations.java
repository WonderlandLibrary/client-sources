/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class Animations
extends Module {
    public NumberSetting itemZ;
    public NumberSetting itemX = new NumberSetting(Qprot0.0("\u8015\u71df\ubb75\ua7e9\uab93\u05be"), 0.0, -200.0, 200.0, 1.0);
    public NumberSetting itemY = new NumberSetting(Qprot0.0("\u8015\u71df\ubb75\ua7e9\uab93\u05bf"), 0.0, -200.0, 200.0, 1.0);
    public NumberSetting itemRotation;
    public NumberSetting itemScale;

    public static {
        throw throwable;
    }

    public Animations() {
        super(Qprot0.0("\u801d\u71c5\ubb79\ua7e9\uabd2\u0592\u8c26\uec46\u570c\u008a"), 0, Module.Category.RENDER);
        Animations Nigga;
        Nigga.itemZ = new NumberSetting(Qprot0.0("\u8015\u71df\ubb75\ua7e9\uab93\u05bc"), 0.0, -200.0, 200.0, 1.0);
        Nigga.itemScale = new NumberSetting(Qprot0.0("\u8015\u71df\ubb75\ua7e9\uab93\u05b5\u8c2c\uec48\u570e\u009c"), 0.0, -10.0, 200.0, 1.0);
        Nigga.itemRotation = new NumberSetting(Qprot0.0("\u8015\u71df\ubb75\ua7e9\uab93\u05b4\u8c20\uec5d\u5703\u008d\u8fa2\uaf03\u029c\u725e"), 0.0, -10.0, 200.0, 1.0);
        Nigga.addSettings(Nigga.itemX, Nigga.itemY, Nigga.itemZ, Nigga.itemScale, Nigga.itemRotation);
    }
}

