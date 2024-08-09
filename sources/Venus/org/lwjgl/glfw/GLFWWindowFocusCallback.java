/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowFocusCallback
extends Callback
implements GLFWWindowFocusCallbackI {
    public static GLFWWindowFocusCallback create(long l) {
        GLFWWindowFocusCallbackI gLFWWindowFocusCallbackI = (GLFWWindowFocusCallbackI)Callback.get(l);
        return gLFWWindowFocusCallbackI instanceof GLFWWindowFocusCallback ? (GLFWWindowFocusCallback)gLFWWindowFocusCallbackI : new Container(l, gLFWWindowFocusCallbackI);
    }

    @Nullable
    public static GLFWWindowFocusCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowFocusCallback.create(l);
    }

    public static GLFWWindowFocusCallback create(GLFWWindowFocusCallbackI gLFWWindowFocusCallbackI) {
        return gLFWWindowFocusCallbackI instanceof GLFWWindowFocusCallback ? (GLFWWindowFocusCallback)gLFWWindowFocusCallbackI : new Container(gLFWWindowFocusCallbackI.address(), gLFWWindowFocusCallbackI);
    }

    protected GLFWWindowFocusCallback() {
        super("(pi)v");
    }

    GLFWWindowFocusCallback(long l) {
        super(l);
    }

    public GLFWWindowFocusCallback set(long l) {
        GLFW.glfwSetWindowFocusCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowFocusCallback {
        private final GLFWWindowFocusCallbackI delegate;

        Container(long l, GLFWWindowFocusCallbackI gLFWWindowFocusCallbackI) {
            super(l);
            this.delegate = gLFWWindowFocusCallbackI;
        }

        @Override
        public void invoke(long l, boolean bl) {
            this.delegate.invoke(l, bl);
        }
    }
}

