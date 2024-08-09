/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBES31Compatibility {
    protected ARBES31Compatibility() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMemoryBarrierByRegion);
    }

    public static void glMemoryBarrierByRegion(@NativeType(value="GLbitfield") int n) {
        GL45C.glMemoryBarrierByRegion(n);
    }

    static {
        GL.initialize();
    }
}

