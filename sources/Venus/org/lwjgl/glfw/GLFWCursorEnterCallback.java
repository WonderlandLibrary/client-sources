/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWCursorEnterCallback
extends Callback
implements GLFWCursorEnterCallbackI {
    public static GLFWCursorEnterCallback create(long l) {
        GLFWCursorEnterCallbackI gLFWCursorEnterCallbackI = (GLFWCursorEnterCallbackI)Callback.get(l);
        return gLFWCursorEnterCallbackI instanceof GLFWCursorEnterCallback ? (GLFWCursorEnterCallback)gLFWCursorEnterCallbackI : new Container(l, gLFWCursorEnterCallbackI);
    }

    @Nullable
    public static GLFWCursorEnterCallback createSafe(long l) {
        return l == 0L ? null : GLFWCursorEnterCallback.create(l);
    }

    public static GLFWCursorEnterCallback create(GLFWCursorEnterCallbackI gLFWCursorEnterCallbackI) {
        return gLFWCursorEnterCallbackI instanceof GLFWCursorEnterCallback ? (GLFWCursorEnterCallback)gLFWCursorEnterCallbackI : new Container(gLFWCursorEnterCallbackI.address(), gLFWCursorEnterCallbackI);
    }

    protected GLFWCursorEnterCallback() {
        super("(pi)v");
    }

    GLFWCursorEnterCallback(long l) {
        super(l);
    }

    public GLFWCursorEnterCallback set(long l) {
        GLFW.glfwSetCursorEnterCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWCursorEnterCallback {
        private final GLFWCursorEnterCallbackI delegate;

        Container(long l, GLFWCursorEnterCallbackI gLFWCursorEnterCallbackI) {
            super(l);
            this.delegate = gLFWCursorEnterCallbackI;
        }

        @Override
        public void invoke(long l, boolean bl) {
            this.delegate.invoke(l, bl);
        }
    }
}

