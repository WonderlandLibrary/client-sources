/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API.Events.Render;

import gq.vapu.czfclient.API.Event;
import net.shadersmod.client.Shaders;

public class EventRender3D extends Event {
    public static float ticks;
    private final boolean isUsingShaders;

    public EventRender3D() {
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public EventRender3D(float ticks) {
        EventRender3D.ticks = ticks;
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public float getPartialTicks() {
        return ticks;
    }

    public boolean isUsingShaders() {
        return this.isUsingShaders;
    }
}
