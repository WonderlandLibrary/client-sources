/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;

public class ARBTextureBarrier {
    protected ARBTextureBarrier() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glTextureBarrier);
    }

    public static void glTextureBarrier() {
        GL45C.glTextureBarrier();
    }

    static {
        GL.initialize();
    }
}

