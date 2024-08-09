/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.launch.Main;

final class Agent {
    Agent() {
    }

    public static void agentmain(String string, Instrumentation instrumentation) throws Throwable {
        Agent.runLauncher(string, instrumentation, true);
    }

    public static void premain(String string, Instrumentation instrumentation) throws Throwable {
        Agent.runLauncher(string, instrumentation, false);
    }

    private static void runLauncher(String string, Instrumentation instrumentation, boolean bl) throws Throwable {
        ClassLoader classLoader = Main.getShadowClassLoader();
        try {
            Class<?> clazz = classLoader.loadClass("lombok.core.AgentLauncher");
            Method method = clazz.getDeclaredMethod("runAgents", String.class, Instrumentation.class, Boolean.TYPE, Class.class);
            method.invoke(null, string, instrumentation, bl, Agent.class);
        } catch (InvocationTargetException invocationTargetException) {
            throw invocationTargetException.getCause();
        }
    }
}

