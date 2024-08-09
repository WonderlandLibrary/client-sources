/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowPosCallback
extends Callback
implements GLFWWindowPosCallbackI {
    public static GLFWWindowPosCallback create(long l) {
        GLFWWindowPosCallbackI gLFWWindowPosCallbackI = (GLFWWindowPosCallbackI)Callback.get(l);
        return gLFWWindowPosCallbackI instanceof GLFWWindowPosCallback ? (GLFWWindowPosCallback)gLFWWindowPosCallbackI : new Container(l, gLFWWindowPosCallbackI);
    }

    @Nullable
    public static GLFWWindowPosCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowPosCallback.create(l);
    }

    public static GLFWWindowPosCallback create(GLFWWindowPosCallbackI gLFWWindowPosCallbackI) {
        return gLFWWindowPosCallbackI instanceof GLFWWindowPosCallback ? (GLFWWindowPosCallback)gLFWWindowPosCallbackI : new Container(gLFWWindowPosCallbackI.address(), gLFWWindowPosCallbackI);
    }

    protected GLFWWindowPosCallback() {
        super("(pii)v");
    }

    GLFWWindowPosCallback(long l) {
        super(l);
    }

    public GLFWWindowPosCallback set(long l) {
        GLFW.glfwSetWindowPosCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowPosCallback {
        private final GLFWWindowPosCallbackI delegate;

        Container(long l, GLFWWindowPosCallbackI gLFWWindowPosCallbackI) {
            super(l);
            this.delegate = gLFWWindowPosCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2) {
            this.delegate.invoke(l, n, n2);
        }
    }
}

