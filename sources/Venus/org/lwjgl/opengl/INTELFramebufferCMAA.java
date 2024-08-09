/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;

public class INTELFramebufferCMAA {
    protected INTELFramebufferCMAA() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glApplyFramebufferAttachmentCMAAINTEL);
    }

    public static native void glApplyFramebufferAttachmentCMAAINTEL();

    static {
        GL.initialize();
    }
}

