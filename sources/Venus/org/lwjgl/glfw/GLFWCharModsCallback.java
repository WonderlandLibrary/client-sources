/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWCharModsCallback
extends Callback
implements GLFWCharModsCallbackI {
    public static GLFWCharModsCallback create(long l) {
        GLFWCharModsCallbackI gLFWCharModsCallbackI = (GLFWCharModsCallbackI)Callback.get(l);
        return gLFWCharModsCallbackI instanceof GLFWCharModsCallback ? (GLFWCharModsCallback)gLFWCharModsCallbackI : new Container(l, gLFWCharModsCallbackI);
    }

    @Nullable
    public static GLFWCharModsCallback createSafe(long l) {
        return l == 0L ? null : GLFWCharModsCallback.create(l);
    }

    public static GLFWCharModsCallback create(GLFWCharModsCallbackI gLFWCharModsCallbackI) {
        return gLFWCharModsCallbackI instanceof GLFWCharModsCallback ? (GLFWCharModsCallback)gLFWCharModsCallbackI : new Container(gLFWCharModsCallbackI.address(), gLFWCharModsCallbackI);
    }

    protected GLFWCharModsCallback() {
        super("(pii)v");
    }

    GLFWCharModsCallback(long l) {
        super(l);
    }

    public GLFWCharModsCallback set(long l) {
        GLFW.glfwSetCharModsCallback(l, this);
        return this;
    }

    private static final class Container
    extends GLFWCharModsCallback {
        private final GLFWCharModsCallbackI delegate;

        Container(long l, GLFWCharModsCallbackI gLFWCharModsCallbackI) {
            super(l);
            this.delegate = gLFWCharModsCallbackI;
        }

        @Override
        public void invoke(long l, int n, int n2) {
            this.delegate.invoke(l, n, n2);
        }
    }
}

