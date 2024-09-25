/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.client.Minecraft;
import skizzle.DiscordClient;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class DiscordRPC
extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public float premod;

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Nigga.isPre();
        }
    }

    public DiscordRPC() {
        super(Qprot0.0("\u183e\u71c2\u237d\ua7e7\u3226\u9db2\u8c2b\u7465\u5732\u9940"), 0, Module.Category.OTHER);
        DiscordRPC Nigga;
        Nigga.premod = Nigga.mc.gameSettings.gammaSetting;
        Nigga.toggled = true;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        DiscordClient.getInstance().shutdown();
    }

    @Override
    public void onEnable() {
        DiscordClient.getInstance().init();
    }
}

