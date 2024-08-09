/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWFramebufferSizeCallback
extends Callback
implements GLFWFramebufferSizeCallbackI {
    public static GLFWFramebufferSizeCallback create(long l) {
        GLFWFramebufferSizeCallbackI gLFWFramebufferSizeCallbackI = (GLFWFramebufferSizeCallbackI)Callback.get(l);
        return gLFWFramebufferSizeCallbackI instanceof GLFWFramebufferSizeCallback ? (GLFWFramebufferSizeCallback)gLFWFramebufferSizeCallbackI : new Container(l, gLFWFramebufferSizeCallbackI);
    }

    @Nullable
    public static GLFWFramebufferSizeCallback createSafe(long l) {
        return l == 0L ? null : GLFWFramebufferSizeCallback.create(l);
    }

    public static GLFWFramebufferSizeCallback create(GLFWFramebufferSizeCallbackI gLFWFramebufferSizeCallbackI) {
        return gLFWFramebufferSizeCallbackI instanceof GLFWFramebufferSizeCallback ? (GLFWFramebufferSizeCallback)gLFWFramebufferSizeCallbackI : new Container(gLFWFramebufferSizeCallbackI.address(), gLFWFramebufferSizeCallbackI);
    }

    protected GLFWFramebufferSizeCallback() {
        super("(pii)v");
    }

    GLFWFramebufferSizeCallback(long l) {
        super(l);
    }

    public GLFWFramebufferSizeCallback set(long l) {
        GLFW.glfwSetFramebufferSizeCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWFramebufferSizeCallback {
        private final GLFWFramebufferSizeCallbackI delegate;

        Container(long l, GLFWFramebufferSizeCallbackI gLFWFramebufferSizeCallbackI) {
            super(l);
            this.delegate = gLFWFramebufferSizeCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2) {
            this.delegate.invoke(l, n, n2);
        }
    }
}

