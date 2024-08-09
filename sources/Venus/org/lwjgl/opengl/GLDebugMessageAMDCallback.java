/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import javax.annotation.Nullable;
import org.lwjgl.opengl.GLDebugMessageAMDCallbackI;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

public abstract class GLDebugMessageAMDCallback
extends Callback
implements GLDebugMessageAMDCallbackI {
    public static GLDebugMessageAMDCallback create(long l) {
        GLDebugMessageAMDCallbackI gLDebugMessageAMDCallbackI = (GLDebugMessageAMDCallbackI)Callback.get(l);
        return gLDebugMessageAMDCallbackI instanceof GLDebugMessageAMDCallback ? (GLDebugMessageAMDCallback)gLDebugMessageAMDCallbackI : new Container(l, gLDebugMessageAMDCallbackI);
    }

    @Nullable
    public static GLDebugMessageAMDCallback createSafe(long l) {
        return l == 0L ? null : GLDebugMessageAMDCallback.create(l);
    }

    public static GLDebugMessageAMDCallback create(GLDebugMessageAMDCallbackI gLDebugMessageAMDCallbackI) {
        return gLDebugMessageAMDCallbackI instanceof GLDebugMessageAMDCallback ? (GLDebugMessageAMDCallback)gLDebugMessageAMDCallbackI : new Container(gLDebugMessageAMDCallbackI.address(), gLDebugMessageAMDCallbackI);
    }

    protected GLDebugMessageAMDCallback() {
        super(SIGNATURE);
    }

    GLDebugMessageAMDCallback(long l) {
        super(l);
    }

    public static String getMessage(int n, long l) {
        return MemoryUtil.memUTF8(MemoryUtil.memByteBuffer(l, n));
    }

    private static final class Container
    extends GLDebugMessageAMDCallback {
        private final GLDebugMessageAMDCallbackI delegate;

        Container(long l, GLDebugMessageAMDCallbackI gLDebugMessageAMDCallbackI) {
            super(l);
            this.delegate = gLDebugMessageAMDCallbackI;
        }

        @Override
        public void invoke(int n, int n2, int n3, int n4, long l, long l2) {
            this.delegate.invoke(n, n2, n3, n4, l, l2);
        }
    }
}

