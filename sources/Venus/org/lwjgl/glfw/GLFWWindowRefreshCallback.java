/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowRefreshCallback
extends Callback
implements GLFWWindowRefreshCallbackI {
    public static GLFWWindowRefreshCallback create(long l) {
        GLFWWindowRefreshCallbackI gLFWWindowRefreshCallbackI = (GLFWWindowRefreshCallbackI)Callback.get(l);
        return gLFWWindowRefreshCallbackI instanceof GLFWWindowRefreshCallback ? (GLFWWindowRefreshCallback)gLFWWindowRefreshCallbackI : new Container(l, gLFWWindowRefreshCallbackI);
    }

    @Nullable
    public static GLFWWindowRefreshCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowRefreshCallback.create(l);
    }

    public static GLFWWindowRefreshCallback create(GLFWWindowRefreshCallbackI gLFWWindowRefreshCallbackI) {
        return gLFWWindowRefreshCallbackI instanceof GLFWWindowRefreshCallback ? (GLFWWindowRefreshCallback)gLFWWindowRefreshCallbackI : new Container(gLFWWindowRefreshCallbackI.address(), gLFWWindowRefreshCallbackI);
    }

    protected GLFWWindowRefreshCallback() {
        super("(p)v");
    }

    GLFWWindowRefreshCallback(long l) {
        super(l);
    }

    public GLFWWindowRefreshCallback set(long l) {
        GLFW.glfwSetWindowRefreshCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowRefreshCallback {
        private final GLFWWindowRefreshCallbackI delegate;

        Container(long l, GLFWWindowRefreshCallbackI gLFWWindowRefreshCallbackI) {
            super(l);
            this.delegate = gLFWWindowRefreshCallbackI;
        }

        @Override
        public void invoke(long l) {
            this.delegate.invoke(l);
        }
    }
}

