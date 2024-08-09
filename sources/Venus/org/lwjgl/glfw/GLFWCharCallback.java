/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWCharCallback
extends Callback
implements GLFWCharCallbackI {
    public static GLFWCharCallback create(long l) {
        GLFWCharCallbackI gLFWCharCallbackI = (GLFWCharCallbackI)Callback.get(l);
        return gLFWCharCallbackI instanceof GLFWCharCallback ? (GLFWCharCallback)gLFWCharCallbackI : new Container(l, gLFWCharCallbackI);
    }

    @Nullable
    public static GLFWCharCallback createSafe(long l) {
        return l == 0L ? null : GLFWCharCallback.create(l);
    }

    public static GLFWCharCallback create(GLFWCharCallbackI gLFWCharCallbackI) {
        return gLFWCharCallbackI instanceof GLFWCharCallback ? (GLFWCharCallback)gLFWCharCallbackI : new Container(gLFWCharCallbackI.address(), gLFWCharCallbackI);
    }

    protected GLFWCharCallback() {
        super("(pi)v");
    }

    GLFWCharCallback(long l) {
        super(l);
    }

    public GLFWCharCallback set(long l) {
        GLFW.glfwSetCharCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWCharCallback {
        private final GLFWCharCallbackI delegate;

        Container(long l, GLFWCharCallbackI gLFWCharCallbackI) {
            super(l);
            this.delegate = gLFWCharCallbackI;
        }

        @Override
        public void invoke(long l, int n) {
            this.delegate.invoke(l, n);
        }
    }
}

