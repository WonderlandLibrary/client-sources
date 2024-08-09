/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowCloseCallback
extends Callback
implements GLFWWindowCloseCallbackI {
    public static GLFWWindowCloseCallback create(long l) {
        GLFWWindowCloseCallbackI gLFWWindowCloseCallbackI = (GLFWWindowCloseCallbackI)Callback.get(l);
        return gLFWWindowCloseCallbackI instanceof GLFWWindowCloseCallback ? (GLFWWindowCloseCallback)gLFWWindowCloseCallbackI : new Container(l, gLFWWindowCloseCallbackI);
    }

    @Nullable
    public static GLFWWindowCloseCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowCloseCallback.create(l);
    }

    public static GLFWWindowCloseCallback create(GLFWWindowCloseCallbackI gLFWWindowCloseCallbackI) {
        return gLFWWindowCloseCallbackI instanceof GLFWWindowCloseCallback ? (GLFWWindowCloseCallback)gLFWWindowCloseCallbackI : new Container(gLFWWindowCloseCallbackI.address(), gLFWWindowCloseCallbackI);
    }

    protected GLFWWindowCloseCallback() {
        super("(p)v");
    }

    GLFWWindowCloseCallback(long l) {
        super(l);
    }

    public GLFWWindowCloseCallback set(long l) {
        GLFW.glfwSetWindowCloseCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowCloseCallback {
        private final GLFWWindowCloseCallbackI delegate;

        Container(long l, GLFWWindowCloseCallbackI gLFWWindowCloseCallbackI) {
            super(l);
            this.delegate = gLFWWindowCloseCallbackI;
        }

        @Override
        public void invoke(long l) {
            this.delegate.invoke(l);
        }
    }
}

