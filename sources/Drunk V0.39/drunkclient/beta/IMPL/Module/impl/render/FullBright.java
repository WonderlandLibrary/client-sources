/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;

public class FullBright
extends Module {
    public FullBright() {
        super("Brightness", new String[]{"Brightness", "Bright"}, Type.RENDER, "Allow's to see in night");
    }

    @Override
    @EventHandler
    public void onEnable() {
        FullBright.mc.gameSettings.gammaSetting = 100.0f;
    }

    @Override
    @EventHandler
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = 1.0f;
    }

    @EventHandler
    public void a(EventPreUpdate e) {
        this.setSuffix("Gamma");
    }
}

