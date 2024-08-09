/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBCLEvent {
    public static final int GL_SYNC_CL_EVENT_ARB = 33344;
    public static final int GL_SYNC_CL_EVENT_COMPLETE_ARB = 33345;

    protected ARBCLEvent() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glCreateSyncFromCLeventARB);
    }

    public static native long nglCreateSyncFromCLeventARB(long var0, long var2, int var4);

    @NativeType(value="GLsync")
    public static long glCreateSyncFromCLeventARB(@NativeType(value="cl_context") long l, @NativeType(value="cl_event") long l2, @NativeType(value="GLbitfield") int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return ARBCLEvent.nglCreateSyncFromCLeventARB(l, l2, n);
    }

    static {
        GL.initialize();
    }
}

