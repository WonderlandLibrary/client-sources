/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDStencilOperationExtended {
    public static final int GL_SET_AMD = 34634;
    public static final int GL_REPLACE_VALUE_AMD = 34635;
    public static final int GL_STENCIL_OP_VALUE_AMD = 34636;
    public static final int GL_STENCIL_BACK_OP_VALUE_AMD = 34637;

    protected AMDStencilOperationExtended() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glStencilOpValueAMD);
    }

    public static native void glStencilOpValueAMD(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    static {
        GL.initialize();
    }
}

