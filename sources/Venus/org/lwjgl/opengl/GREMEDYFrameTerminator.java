/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;

public class GREMEDYFrameTerminator {
    protected GREMEDYFrameTerminator() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glFrameTerminatorGREMEDY);
    }

    public static native void glFrameTerminatorGREMEDY();

    static {
        GL.initialize();
    }
}

