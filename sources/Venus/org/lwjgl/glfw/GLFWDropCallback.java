/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

public abstract class GLFWDropCallback
extends Callback
implements GLFWDropCallbackI {
    public static GLFWDropCallback create(long l) {
        GLFWDropCallbackI gLFWDropCallbackI = (GLFWDropCallbackI)Callback.get(l);
        return gLFWDropCallbackI instanceof GLFWDropCallback ? (GLFWDropCallback)gLFWDropCallbackI : new Container(l, gLFWDropCallbackI);
    }

    @Nullable
    public static GLFWDropCallback createSafe(long l) {
        return l == 0L ? null : GLFWDropCallback.create(l);
    }

    public static GLFWDropCallback create(GLFWDropCallbackI gLFWDropCallbackI) {
        return gLFWDropCallbackI instanceof GLFWDropCallback ? (GLFWDropCallback)gLFWDropCallbackI : new Container(gLFWDropCallbackI.address(), gLFWDropCallbackI);
    }

    protected GLFWDropCallback() {
        super("(pip)v");
    }

    GLFWDropCallback(long l) {
        super(l);
    }

    public static String getName(long l, int n) {
        return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(l + (long)(Pointer.POINTER_SIZE * n)));
    }

    public GLFWDropCallback set(long l) {
        GLFW.glfwSetDropCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWDropCallback {
        private final GLFWDropCallbackI delegate;

        Container(long l, GLFWDropCallbackI gLFWDropCallbackI) {
            super(l);
            this.delegate = gLFWDropCallbackI;
        }

        @Override
        public void invoke(long l, int n, long l2) {
            this.delegate.invoke(l, n, l2);
        }
    }
}

