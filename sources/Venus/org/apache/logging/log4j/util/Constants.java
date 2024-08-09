/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class Constants {
    public static final boolean IS_WEB_APP = PropertiesUtil.getProperties().getBooleanProperty("log4j2.is.webapp", Constants.isClassAvailable("javax.servlet.Servlet"));
    public static final boolean ENABLE_THREADLOCALS = !IS_WEB_APP && PropertiesUtil.getProperties().getBooleanProperty("log4j2.enable.threadlocals", false);
    public static final int JAVA_MAJOR_VERSION = Constants.getMajorVersion();

    private static boolean isClassAvailable(String string) {
        try {
            return LoaderUtil.loadClass(string) != null;
        } catch (Throwable throwable) {
            return true;
        }
    }

    private Constants() {
    }

    private static int getMajorVersion() {
        String string = System.getProperty("java.version");
        String[] stringArray = string.split("-|\\.");
        try {
            boolean bl;
            int n = Integer.parseInt(stringArray[0]);
            boolean bl2 = bl = n != 1;
            if (bl) {
                return n;
            }
            return Integer.parseInt(stringArray[5]);
        } catch (Exception exception) {
            return 1;
        }
    }
}

