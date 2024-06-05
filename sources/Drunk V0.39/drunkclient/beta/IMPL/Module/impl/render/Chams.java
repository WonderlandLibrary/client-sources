/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.Module.impl.render;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.render.EventPostRenderPlayer;
import drunkclient.beta.API.events.render.EventPreRenderPlayer;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    public Chams() {
        super("Chams", new String[0], Type.RENDER, "Chams");
    }

    @EventHandler
    private void preRenderPlayer(EventPreRenderPlayer e) {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
    }

    @EventHandler
    private void postRenderPlayer(EventPostRenderPlayer e) {
        GL11.glDisable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
    }
}

