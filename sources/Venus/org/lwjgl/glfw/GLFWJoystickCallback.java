/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.system.Callback;

public abstract class GLFWJoystickCallback
extends Callback
implements GLFWJoystickCallbackI {
    public static GLFWJoystickCallback create(long l) {
        GLFWJoystickCallbackI gLFWJoystickCallbackI = (GLFWJoystickCallbackI)Callback.get(l);
        return gLFWJoystickCallbackI instanceof GLFWJoystickCallback ? (GLFWJoystickCallback)gLFWJoystickCallbackI : new Container(l, gLFWJoystickCallbackI);
    }

    @Nullable
    public static GLFWJoystickCallback createSafe(long l) {
        return l == 0L ? null : GLFWJoystickCallback.create(l);
    }

    public static GLFWJoystickCallback create(GLFWJoystickCallbackI gLFWJoystickCallbackI) {
        return gLFWJoystickCallbackI instanceof GLFWJoystickCallback ? (GLFWJoystickCallback)gLFWJoystickCallbackI : new Container(gLFWJoystickCallbackI.address(), gLFWJoystickCallbackI);
    }

    protected GLFWJoystickCallback() {
        super("(ii)v");
    }

    GLFWJoystickCallback(long l) {
        super(l);
    }

    public GLFWJoystickCallback set() {
        GLFW.glfwSetJoystickCallback(this);
        return this;
    }

    private static final class Container
    extends GLFWJoystickCallback {
        private final GLFWJoystickCallbackI delegate;

        Container(long l, GLFWJoystickCallbackI gLFWJoystickCallbackI) {
            super(l);
            this.delegate = gLFWJoystickCallbackI;
        }

        @Override
        public void invoke(int n, int n2) {
            this.delegate.invoke(n, n2);
        }
    }
}

