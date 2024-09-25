/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.Minecraft;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class Fullbright
extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public float premod;

    @Override
    public void onEnable() {
        Nigga.mc.gameSettings.gammaSetting = Float.intBitsToFloat(1.07044224E9f ^ 0x7D05A6FF);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Nigga.mc.gameSettings.gammaSetting = Float.intBitsToFloat(1.08974861E9f ^ 0x7F743E91);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Nigga.isPre();
        }
    }

    public Fullbright() {
        super(Qprot0.0("\u95c0\u71de\uae96\ua7e8\u8737\u104e\u8c26\uf9a4\u570a\u2c6b"), 24, Module.Category.RENDER);
        Fullbright Nigga;
        Nigga.premod = Nigga.mc.gameSettings.gammaSetting;
    }
}

