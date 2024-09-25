/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package skizzle.modules.other;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;

public class Ghost
extends Module {
    public float premod;
    public Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onDisable() {
        Display.setTitle((String)(String.valueOf(Client.name) + Qprot0.0("\u3156\u719a\u0a24\u81c7\u7912\ub4b0\u8c6f") + Client.version));
        Nigga.mc.gameSettings.forceUnicodeFont = true;
        Client.ghostMode = false;
        ModuleManager.discord.onDisable();
    }

    @Override
    public void onEnable() {
        Display.setTitle((String)Qprot0.0("\u313b\u71c2\u0a64\u59d1\u1116\ub4be\u8c2e\u5d55\ua926\uba1f\u3ed0\uaf42\ub3d0"));
        Nigga.mc.gameSettings.forceUnicodeFont = false;
        Client.ghostMode = true;
        ModuleManager.discord.onDisable();
        for (Module Nigga : Client.modules) {
            if (!Nigga.toggled || Nigga.name.equals(Qprot0.0("\u313e\u71fe\u0a4e")) || Nigga.name.equals(Qprot0.0("\u3131\u71c3\u0a65\u59c7\u1101"))) continue;
            Nigga.toggled = false;
        }
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Nigga.isPre();
        }
    }

    public Ghost() {
        super(Qprot0.0("\u3131\u71c3\u0a65\ua7f7\u1b31"), 25, Module.Category.OTHER);
        Ghost Nigga;
        Nigga.premod = Nigga.mc.gameSettings.gammaSetting;
    }

    public static {
        throw throwable;
    }
}

