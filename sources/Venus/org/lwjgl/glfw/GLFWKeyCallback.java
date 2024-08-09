/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWKeyCallback
extends Callback
implements GLFWKeyCallbackI {
    public static GLFWKeyCallback create(long l) {
        GLFWKeyCallbackI gLFWKeyCallbackI = (GLFWKeyCallbackI)Callback.get(l);
        return gLFWKeyCallbackI instanceof GLFWKeyCallback ? (GLFWKeyCallback)gLFWKeyCallbackI : new Container(l, gLFWKeyCallbackI);
    }

    @Nullable
    public static GLFWKeyCallback createSafe(long l) {
        return l == 0L ? null : GLFWKeyCallback.create(l);
    }

    public static GLFWKeyCallback create(GLFWKeyCallbackI gLFWKeyCallbackI) {
        return gLFWKeyCallbackI instanceof GLFWKeyCallback ? (GLFWKeyCallback)gLFWKeyCallbackI : new Container(gLFWKeyCallbackI.address(), gLFWKeyCallbackI);
    }

    protected GLFWKeyCallback() {
        super("(piiii)v");
    }

    GLFWKeyCallback(long l) {
        super(l);
    }

    public GLFWKeyCallback set(long l) {
        GLFW.glfwSetKeyCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWKeyCallback {
        private final GLFWKeyCallbackI delegate;

        Container(long l, GLFWKeyCallbackI gLFWKeyCallbackI) {
            super(l);
            this.delegate = gLFWKeyCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2, int n3, int n4) {
            this.delegate.invoke(l, n, n2, n3, n4);
        }
    }
}

