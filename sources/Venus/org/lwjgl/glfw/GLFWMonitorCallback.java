/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWMonitorCallback
extends Callback
implements GLFWMonitorCallbackI {
    public static GLFWMonitorCallback create(long l) {
        GLFWMonitorCallbackI gLFWMonitorCallbackI = (GLFWMonitorCallbackI)Callback.get(l);
        return gLFWMonitorCallbackI instanceof GLFWMonitorCallback ? (GLFWMonitorCallback)gLFWMonitorCallbackI : new Container(l, gLFWMonitorCallbackI);
    }

    @Nullable
    public static GLFWMonitorCallback createSafe(long l) {
        return l == 0L ? null : GLFWMonitorCallback.create(l);
    }

    public static GLFWMonitorCallback create(GLFWMonitorCallbackI gLFWMonitorCallbackI) {
        return gLFWMonitorCallbackI instanceof GLFWMonitorCallback ? (GLFWMonitorCallback)gLFWMonitorCallbackI : new Container(gLFWMonitorCallbackI.address(), gLFWMonitorCallbackI);
    }

    protected GLFWMonitorCallback() {
        super("(pi)v");
    }

    GLFWMonitorCallback(long l) {
        super(l);
    }

    public GLFWMonitorCallback set() {
        GLFW.glfwSetMonitorCallback(this);
        return this;
    }

    private static final class Container
    extends GLFWMonitorCallback {
        private final GLFWMonitorCallbackI delegate;

        Container(long l, GLFWMonitorCallbackI gLFWMonitorCallbackI) {
            super(l);
            this.delegate = gLFWMonitorCallbackI;
        }

        @Override
        public void invoke(long l, int n) {
            this.delegate.invoke(l, n);
        }
    }
}

