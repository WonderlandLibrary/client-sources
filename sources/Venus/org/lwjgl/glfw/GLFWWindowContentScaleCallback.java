/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWWindowContentScaleCallback
extends Callback
implements GLFWWindowContentScaleCallbackI {
    public static GLFWWindowContentScaleCallback create(long l) {
        GLFWWindowContentScaleCallbackI gLFWWindowContentScaleCallbackI = (GLFWWindowContentScaleCallbackI)Callback.get(l);
        return gLFWWindowContentScaleCallbackI instanceof GLFWWindowContentScaleCallback ? (GLFWWindowContentScaleCallback)gLFWWindowContentScaleCallbackI : new Container(l, gLFWWindowContentScaleCallbackI);
    }

    @Nullable
    public static GLFWWindowContentScaleCallback createSafe(long l) {
        return l == 0L ? null : GLFWWindowContentScaleCallback.create(l);
    }

    public static GLFWWindowContentScaleCallback create(GLFWWindowContentScaleCallbackI gLFWWindowContentScaleCallbackI) {
        return gLFWWindowContentScaleCallbackI instanceof GLFWWindowContentScaleCallback ? (GLFWWindowContentScaleCallback)gLFWWindowContentScaleCallbackI : new Container(gLFWWindowContentScaleCallbackI.address(), gLFWWindowContentScaleCallbackI);
    }

    protected GLFWWindowContentScaleCallback() {
        super("(pff)v");
    }

    GLFWWindowContentScaleCallback(long l) {
        super(l);
    }

    public GLFWWindowContentScaleCallback set(long l) {
        GLFW.glfwSetWindowContentScaleCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWWindowContentScaleCallback {
        private final GLFWWindowContentScaleCallbackI delegate;

        Container(long l, GLFWWindowContentScaleCallbackI gLFWWindowContentScaleCallbackI) {
            super(l);
            this.delegate = gLFWWindowContentScaleCallbackI;
        }

        @Override
        public void invoke(long l, float f, float f2) {
            this.delegate.invoke(l, f, f2);
        }
    }
}

