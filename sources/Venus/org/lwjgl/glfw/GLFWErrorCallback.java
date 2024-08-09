/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

public abstract class GLFWErrorCallback
extends Callback
implements GLFWErrorCallbackI {
    public static GLFWErrorCallback create(long l) {
        GLFWErrorCallbackI gLFWErrorCallbackI = (GLFWErrorCallbackI)Callback.get(l);
        return gLFWErrorCallbackI instanceof GLFWErrorCallback ? (GLFWErrorCallback)gLFWErrorCallbackI : new Container(l, gLFWErrorCallbackI);
    }

    @Nullable
    public static GLFWErrorCallback createSafe(long l) {
        return l == 0L ? null : GLFWErrorCallback.create(l);
    }

    public static GLFWErrorCallback create(GLFWErrorCallbackI gLFWErrorCallbackI) {
        return gLFWErrorCallbackI instanceof GLFWErrorCallback ? (GLFWErrorCallback)gLFWErrorCallbackI : new Container(gLFWErrorCallbackI.address(), gLFWErrorCallbackI);
    }

    protected GLFWErrorCallback() {
        super("(ip)v");
    }

    GLFWErrorCallback(long l) {
        super(l);
    }

    public static String getDescription(long l) {
        return MemoryUtil.memUTF8(l);
    }

    public static GLFWErrorCallback createPrint() {
        return GLFWErrorCallback.createPrint(APIUtil.DEBUG_STREAM);
    }

    public static GLFWErrorCallback createPrint(PrintStream printStream) {
        return new GLFWErrorCallback(printStream){
            private Map<Integer, String> ERROR_CODES;
            final PrintStream val$stream;
            {
                this.val$stream = printStream;
                this.ERROR_CODES = APIUtil.apiClassTokens(1::lambda$$0, null, GLFW.class);
            }

            @Override
            public void invoke(int n, long l) {
                String string = 1.getDescription(l);
                this.val$stream.printf("[LWJGL] %s error\n", this.ERROR_CODES.get(n));
                this.val$stream.println("\tDescription : " + string);
                this.val$stream.println("\tStacktrace  :");
                StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
                for (int i = 4; i < stackTraceElementArray.length; ++i) {
                    this.val$stream.print("\t\t");
                    this.val$stream.println(stackTraceElementArray[i].toString());
                }
            }

            private static boolean lambda$$0(Field field, Integer n) {
                return 65536 < n && n < 131072;
            }
        };
    }

    public static GLFWErrorCallback createThrow() {
        return new GLFWErrorCallback(){

            @Override
            public void invoke(int n, long l) {
                throw new IllegalStateException(String.format("GLFW error [0x%X]: %s", n, 2.getDescription(l)));
            }
        };
    }

    public GLFWErrorCallback set() {
        GLFW.glfwSetErrorCallback(this);
        return this;
    }

    private static final class Container
    extends GLFWErrorCallback {
        private final GLFWErrorCallbackI delegate;

        Container(long l, GLFWErrorCallbackI gLFWErrorCallbackI) {
            super(l);
            this.delegate = gLFWErrorCallbackI;
        }

        @Override
        public void invoke(int n, long l) {
            this.delegate.invoke(n, l);
        }
    }
}

