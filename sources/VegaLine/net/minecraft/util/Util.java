/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Logger;

public class Util {
    public static EnumOS getOSType() {
        String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (s.contains("win")) {
            return EnumOS.WINDOWS;
        }
        if (s.contains("mac")) {
            return EnumOS.OSX;
        }
        if (s.contains("solaris")) {
            return EnumOS.SOLARIS;
        }
        if (s.contains("sunos")) {
            return EnumOS.SOLARIS;
        }
        if (s.contains("linux")) {
            return EnumOS.LINUX;
        }
        return s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN;
    }

    @Nullable
    public static <V> V runTask(FutureTask<V> task, Logger logger) {
        try {
            task.run();
            return task.get();
        } catch (ExecutionException executionexception) {
            logger.fatal("Error executing task", (Throwable)executionexception);
        } catch (InterruptedException interruptedexception) {
            logger.fatal("Error executing task", (Throwable)interruptedexception);
        }
        return null;
    }

    public static <T> T getLastElement(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static enum EnumOS {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN;

    }
}

