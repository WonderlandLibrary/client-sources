/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowSizeCallback
extends Callback
implements GLFWWindowSizeCallbackI {
    public static GLFWWindowSizeCallback create(long l) {
        GLFWWindowSizeCallbackI gLFWWindowSizeCallbackI = (GLFWWindowSizeCallbackI)Callback.get(l);
        return gLFWWindowSizeCallbackI instanceof GLFWWindowSizeCallback ? (GLFWWindowSizeCallback)gLFWWindowSizeCallbackI : new Container(l, gLFWWindowSizeCallbackI);
    }

    @Nullable
    public static GLFWWindowSizeCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowSizeCallback.create(l);
    }

    public static GLFWWindowSizeCallback create(GLFWWindowSizeCallbackI gLFWWindowSizeCallbackI) {
        return gLFWWindowSizeCallbackI instanceof GLFWWindowSizeCallback ? (GLFWWindowSizeCallback)gLFWWindowSizeCallbackI : new Container(gLFWWindowSizeCallbackI.address(), gLFWWindowSizeCallbackI);
    }

    protected GLFWWindowSizeCallback() {
        super("(pii)v");
    }

    GLFWWindowSizeCallback(long l) {
        super(l);
    }

    public GLFWWindowSizeCallback set(long l) {
        GLFW.glfwSetWindowSizeCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowSizeCallback {
        private final GLFWWindowSizeCallbackI delegate;

        Container(long l, GLFWWindowSizeCallbackI gLFWWindowSizeCallbackI) {
            super(l);
            this.delegate = gLFWWindowSizeCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2) {
            this.delegate.invoke(l, n, n2);
        }
    }
}

