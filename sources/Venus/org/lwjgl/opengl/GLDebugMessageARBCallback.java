/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import javax.annotation.Nullable;
import org.lwjgl.opengl.GLDebugMessageARBCallbackI;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

public abstract class GLDebugMessageARBCallback
extends Callback
implements GLDebugMessageARBCallbackI {
    public static GLDebugMessageARBCallback create(long l) {
        GLDebugMessageARBCallbackI gLDebugMessageARBCallbackI = (GLDebugMessageARBCallbackI)Callback.get(l);
        return gLDebugMessageARBCallbackI instanceof GLDebugMessageARBCallback ? (GLDebugMessageARBCallback)gLDebugMessageARBCallbackI : new Container(l, gLDebugMessageARBCallbackI);
    }

    @Nullable
    public static GLDebugMessageARBCallback createSafe(long l) {
        return l == 0L ? null : GLDebugMessageARBCallback.create(l);
    }

    public static GLDebugMessageARBCallback create(GLDebugMessageARBCallbackI gLDebugMessageARBCallbackI) {
        return gLDebugMessageARBCallbackI instanceof GLDebugMessageARBCallback ? (GLDebugMessageARBCallback)gLDebugMessageARBCallbackI : new Container(gLDebugMessageARBCallbackI.address(), gLDebugMessageARBCallbackI);
    }

    protected GLDebugMessageARBCallback() {
        super(SIGNATURE);
    }

    GLDebugMessageARBCallback(long l) {
        super(l);
    }

    public static String getMessage(int n, long l) {
        return MemoryUtil.memUTF8(MemoryUtil.memByteBuffer(l, n));
    }

    private static final class Container
    extends GLDebugMessageARBCallback {
        private final GLDebugMessageARBCallbackI delegate;

        Container(long l, GLDebugMessageARBCallbackI gLDebugMessageARBCallbackI) {
            super(l);
            this.delegate = gLDebugMessageARBCallbackI;
        }

        @Override
        public void invoke(int n, int n2, int n3, int n4, int n5, long l, long l2) {
            this.delegate.invoke(n, n2, n3, n4, n5, l, l2);
        }
    }
}

