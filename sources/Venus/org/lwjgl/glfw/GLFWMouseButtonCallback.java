/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWMouseButtonCallback
extends Callback
implements GLFWMouseButtonCallbackI {
    public static GLFWMouseButtonCallback create(long l) {
        GLFWMouseButtonCallbackI gLFWMouseButtonCallbackI = (GLFWMouseButtonCallbackI)Callback.get(l);
        return gLFWMouseButtonCallbackI instanceof GLFWMouseButtonCallback ? (GLFWMouseButtonCallback)gLFWMouseButtonCallbackI : new Container(l, gLFWMouseButtonCallbackI);
    }

    @Nullable
    public static GLFWMouseButtonCallback createSafe(long l) {
        return l == 0L ? null : GLFWMouseButtonCallback.create(l);
    }

    public static GLFWMouseButtonCallback create(GLFWMouseButtonCallbackI gLFWMouseButtonCallbackI) {
        return gLFWMouseButtonCallbackI instanceof GLFWMouseButtonCallback ? (GLFWMouseButtonCallback)gLFWMouseButtonCallbackI : new Container(gLFWMouseButtonCallbackI.address(), gLFWMouseButtonCallbackI);
    }

    protected GLFWMouseButtonCallback() {
        super("(piii)v");
    }

    GLFWMouseButtonCallback(long l) {
        super(l);
    }

    public GLFWMouseButtonCallback set(long l) {
        GLFW.glfwSetMouseButtonCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWMouseButtonCallback {
        private final GLFWMouseButtonCallbackI delegate;

        Container(long l, GLFWMouseButtonCallbackI gLFWMouseButtonCallbackI) {
            super(l);
            this.delegate = gLFWMouseButtonCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2, int n3) {
            this.delegate.invoke(l, n, n2, n3);
        }
    }
}

