/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventRender2D;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;

public class Spotify
extends Module {
    public Spotify() {
        super("Spotify", new String[0], Type.RENDER, "No");
    }

    @EventHandler
    public void e(EventRender2D e) {
    }

    @Override
    public void onEnable() {
    }
}

